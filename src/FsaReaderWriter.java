import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FsaReaderWriter implements FsaIo {

	@Override
	public void read(final Reader r, final Fsa f) throws IOException, FsaFormatException {
		// wrap the reader as BufferReader for the 'readline' function
		BufferedReader br = new BufferedReader(r);
		// set the initial number
		int lineNr = 1;
		String line = null;
		while((line = br.readLine()) != null) {
			if(line.startsWith("state")) {
				// the state format is 'state stateName1 2 3'
				// split the line with ' ', throw exception if the line doesnot contain 4 terms
				String[] words = line.split(" ");
				if(words.length != 4) {
					throw new FsaFormatException(lineNr, "the state format is invalid.");
				}
				try {
					// get the x and y position
					int xPos = Integer.parseInt(words[2]);
					int yPos = Integer.parseInt(words[3]);
					// new state with the parameters in the line
					f.newState(words[1], xPos, yPos);
				} catch (NumberFormatException e) {
					throw new FsaFormatException(lineNr, "the pos is not an integer.");
				} catch (IllegalArgumentException e) {
					throw new FsaFormatException(lineNr, e.getMessage());
				}
			} else if(line.startsWith("transition")) {
				// the state format is 'transition stateName2 event stateName3'
				// split the line with ' ', throw exception if the line doesnot contain 4 terms
				String[] words = line.split(" ");
				if(words.length != 4) {
					throw new FsaFormatException(lineNr, "the transition format is invalid.");
				}
				if(!isValidNaming(words[1])) {
					throw new FsaFormatException(lineNr, "the state name format is invalid.");
				}
				if(!isValidNaming(words[3])) {
					throw new FsaFormatException(lineNr, "the state name format is invalid.");
				}
				
				// transfer the '?' to null reference
				String event = words[2];
				if("?".equals(event)) {
					event = null;
				}
				try {
					// new the transition
					f.newTransition(new StateImpl(words[1], -1, -1), new StateImpl(words[3], -1, -1), event);
				} catch (IllegalArgumentException e) {
					throw new FsaFormatException(lineNr, e.getMessage());
				}
			} else if(line.startsWith("initial")) {
				// the state format is 'initial stateName1'
				// split the line with ' ', throw exception if the line doesnot contain 2 terms
				String[] words = line.split(" ");
				if(words.length != 2) {
					throw new FsaFormatException(lineNr, "the inital format is invalid.");
				}
				// throw exception if the state name doesnot match the naming rule
				if(!isValidNaming(words[1])) {
					throw new FsaFormatException(lineNr, "the state name format is invalid.");
				}
				// search if the state exists or not, throw exception if not
				State state = f.findState(words[1]);
				if(state != null) {
					// set the initial indicator
					state.setInitial(true);
					// set the current indicator
					((StateImpl) state).setCurrent(true); 
				} else {
					throw new FsaFormatException(lineNr, "the state is not defined.");
				}
			} else if(line.startsWith("final")) {
				// the state format is 'final stateName1'
				// split the line with ' ', throw exception if the line doesnot contain 2 terms
				String[] words = line.split(" ");
				if(words.length != 2) {
					throw new FsaFormatException(lineNr, "the final format is invalid.");
				}
				// throw exception if the state name doesnot match the naming rule
				if(!isValidNaming(words[1])) {
					throw new FsaFormatException(lineNr, "the state name format is invalid.");
				}
				// search if the state exists or not, throw exception if not
				State state = f.findState(words[1]);
				if(state != null) {
					// set the final indicator
					state.setFinal(true);
				} else {
					throw new FsaFormatException(lineNr, "the state is not defined.");
				}
			} else if(line.startsWith("#")) {
				// ignore
			} else {
				throw new FsaFormatException(lineNr, "the format of the input line is invalid.");
			}
			// increase the lineNr
			lineNr++;
		}
	}

	@Override
	public void write(final Writer w, final Fsa f) throws IOException {
		// wrap the writer as BufferWriter
		BufferedWriter bw = new BufferedWriter(w);
		StringBuilder sb = new StringBuilder();
		// print out the states
    	for(State s : f.getStates()) {
    		sb.append("state ").append(s.getName()).append(" ").append(s.getXpos()).append(" ").append(s.getYpos()).append("\n");
    	}
    	// print out the transitions
    	for(State s : f.getStates()) {
    		for(Transition t : s.transitionsFrom()) {
    			sb.append("transition ").append(t.fromState().getName()).append(" ").append(t.eventName()==null?"?":t.eventName()).append(" ").append(t.toState().getName()).append("\n");
    		}
    	}
    	// print out the initial states
    	for(State s : f.getInitialStates()) {
    		sb.append("initial ").append(s.getName()).append("\n");
    	}
    	// print out the final states
    	for(State s : f.getFinalStates()) {
    		sb.append("final ").append(s.getName()).append("\n");
    	}
		bw.write(sb.toString());
		bw.flush();
	}
	
	// check if the name is beginning with a letter, followed by zero or more letter, digit, or underscore characters or not
	private boolean isValidNaming(final String name) {
		if(name == null) return false;
		Pattern pattern = Pattern.compile("^[A-Za-z]+[A-Za-z_\\d]*$");
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}
	
}
