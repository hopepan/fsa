import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FsaImpl implements Fsa, FsaSim {

	private FsaListener fsaListener;
	
	// the field containing all the states in FSA
	private Set<State> states;
	
	
	public FsaImpl() {
		super();
		// initialize the states
		states = new CopyOnWriteArraySet<>();
	}

	@Override
	public void reset() {
		// reset all the states to NonCurrent
		for(State s : getStates()) {
			((StateImpl) s).setCurrent(false);
		}
		// mark the initial states as current states
		for(State s : getInitialStates()) {
			((StateImpl) s).setCurrent(true);
		}
	}

	@Override
	public void step(final String event) {
		// if the event is not epsilon-transition
		if(event != null) {
			// get all the current states
			Set<State> ss = getCurrentStates();
			// return if no current states
			if(ss.isEmpty()) return;
			for(State s : ss) {
				// set the state as non current state
				((StateImpl) s).setCurrent(false);
				// for loop all the transitions in each state
				for(Transition t : s.transitionsFrom()) {
					// set the state as current if its event name is equals to the event input parameter
					if(event.equals(t.eventName())) {
						((StateImpl) t.toState()).setCurrent(true);
					}
				}
			}
		} else {
			// seach all the epsilon-transitions from all the states, and mark them as current states
			for(State s : getStates()) {
				for(Transition t : s.transitionsFrom()) {
					if(t.eventName() == null) {
						((StateImpl) t.toState()).setCurrent(true);
					}
				}
			}
		}
	}

	@Override
	public boolean isRecognised() {
		// get all the current states
		Set<State> cs = getCurrentStates();
		// return false if no current
		if(cs.isEmpty()) return false;
		// mark true only when the current states are all in the final
		return getFinalStates().containsAll(cs);
	}

	//Create a new State and add it to this FSA
    //Returns the new state
    //Throws IllegalArgumentException if:
    //the name is not valid or is the same as that
    //of an existing state
	@Override
	public State newState(final String name, final int x, final int y) throws IllegalArgumentException {
		// throw exception if the naming is invalid
		if(!isValidNaming(name)) {
			throw new IllegalArgumentException("state name is invalid");
		}
		// check if the state exists or not
		if(findState(name) != null) {
			throw new IllegalArgumentException("state exists already");
		}
		// new a state and store it
		State state = new StateImpl(name, x, y);
		states.add(state);
		if(this.fsaListener != null) {
			this.fsaListener.statesChanged();
		}
		return state;
	}

	//Remove a state from the FSA
    //If the state does not exist, returns without error
	@Override
	public void removeState(final State s) {
		// seach if the input state exists or not
		State state = findState(s.getName());
		if(state != null) {
			// remove all the transtions from this state
			for(Transition t : state.transitionsFrom()) {
				removeTransition(t);
			}
			// remove all the transtions to this state
			for(Transition t : state.transitionsTo()) {
				removeTransition(t);
			}
			// remove the state
			states.remove(s);
			if(this.fsaListener != null) {
				this.fsaListener.statesChanged();
			}
		}
	}

	//Find and return the State with the given name
    //If no state exists with given name, return NULL
	@Override
	public State findState(final String stateName) {
		// search all the states
		for(State state : states) {
			// check if the name match or not
			if(state.getName().equals(stateName)) {
				return state;
			}
		}
		return null;
	}

	//Return a set containing all the states in this Fsa
	@Override
	public Set<State> getStates() {
		return states;
	}

	//Create a new Transition and add it to this FSA
    //Returns the new transition.
    //eventName==null specifies an epsilon-transition
    //Throws IllegalArgumentException if:
    //  The fromState or toState does not exist or
    //  The eventName is invalid or
    //  An identical transition already exists
	@Override
	public Transition newTransition(final State fromState, final State toState, final String eventName) throws IllegalArgumentException {
		// throw exception if the event name is not null and not a letter format
		if(eventName != null && !isLetter(eventName)) {
			throw new IllegalArgumentException("event name is invalid");
		}
		
		// search the from state in FSA
		State fromStateInFSA = findState(fromState.getName());
		// throw exception if not exist
		if(fromStateInFSA == null) {
			throw new IllegalArgumentException("from state does not exist");
		}
		
		// search the to state in FSA
		State toStateInFSA = findState(toState.getName());
		// throw exception if not exist
		if(toStateInFSA == null) {
			throw new IllegalArgumentException("to state does not exist");
		}
		
		// new a transtion with the input parameters
		Transition transition = new TransitionImpl(fromStateInFSA, toStateInFSA, eventName);
		// compare this new state with all the states in FSA, throw exception if exist already
		for(Transition t : fromStateInFSA.transitionsFrom()) {
			if(transition.equals(t)) {
				throw new IllegalArgumentException("transition exists already");
			}
		}
		
		// add this new state in the fromState transitionsFrom list
		fromStateInFSA.transitionsFrom().add(transition);
		// add this new state in the toState transitionsTo list
		toStateInFSA.transitionsTo().add(transition);
		if(this.fsaListener != null) {
			this.fsaListener.transitionsChanged();
		}
		return transition;
	}

	 //Remove a transition from the FSA
    //If the transition does not exist, returns without error
	@Override
	public void removeTransition(final Transition t) {
		// return if the transition is invalid
		if(t == null || t.fromState() == null || t.toState() == null) {
			return;
		}
		
		// return if the fromState not exist
		State fromState = findState(t.fromState().getName());
		if(fromState == null) {
			return;
		}
		
		// return if the toState not exist
		State toState = findState(t.toState().getName());
		if(toState == null) {
			return;
		}
		
		// remove this input transition from the fromState transitionsFrom list
		if(fromState.transitionsFrom().remove(t)) {
			// remove this input transition from the toState transitionsTo list
			toState.transitionsTo().remove(t);
			if(this.fsaListener != null) {
				this.fsaListener.transitionsChanged();
			}
		}
	}

	//Find all the transitions between two states
    //Throws IllegalArgumentException if:
    //  The fromState or toState does not exist
	@Override
	public Set<Transition> findTransition(final State fromState, final State toState) {
		State fromStateInFSA = null;
		State toStateInFSA = null;
		// search the input fromState exists not or
		if(fromState == null || (fromStateInFSA = findState(fromState.getName())) == null) {
			throw new IllegalArgumentException("from state can't be found");
		}
		// search the input toState exists not or
		if(toState == null || (toStateInFSA = findState(toState.getName())) == null) {
			throw new IllegalArgumentException("to state can't be found");
		}
		Set<Transition> ts = new CopyOnWriteArraySet<>();
		// search all the transitionsFrom in the fromState of FSA
		for(Transition t : fromStateInFSA.transitionsFrom()) {
			if(t.toState().equals(toStateInFSA)) {
				// add it if it exists in FSA
				ts.add(t);
			}
		}
		return ts;
	}

	//Return the set of initial states of this Fsa
	@Override
	public Set<State> getInitialStates() {
		Set<State> s = new CopyOnWriteArraySet<>();
		for(State state : states) {
			if(state.isInitial()) {
				s.add(state);
			}
		}
		return s;
	}

	//Return the set of final states of this Fsa
	@Override
	public Set<State> getFinalStates() {
		Set<State> s = new CopyOnWriteArraySet<>();
		for(State state : states) {
			if(state.isFinal()) {
				s.add(state);
			}
		}
		return s;
	}

	//Returns a set containing all the current states of this FSA
	@Override
	public Set<State> getCurrentStates() {
		Set<State> s = new CopyOnWriteArraySet<>();
		for(State state : states) {
			if(state.isCurrent()) {
				s.add(state);
			}
		}
		return s;
	}

	//Add a listener to this FSA
	@Override
	public void addListener(final FsaListener fl) {
		this.fsaListener = fl;
	}

	//Remove a listener from this FSA
	@Override
	public void removeListener(final FsaListener fl) {
		if(fl.equals(this.fsaListener)) {
			this.fsaListener = null;
		}
	}

	//Return a string describing this Fsa
    //Returns a string that contains (in this order):
    //for each state in the FSA, a line (terminated by \n) containing
    //  STATE followed the toString result for that state
    //for each transition in the FSA, a line (terminated by \n) containing
    //  TRANSITION followed the toString result for that transition
    //for each initial state in the FSA, a line (terminated by \n) containing
    //  INITIAL followed the name of the state
    //for each final state in the FSA, a line (terminated by \n) containing
    //  FINAL followed the name of the state
    @Override
	public String toString() {
    	StringBuilder sb = new StringBuilder();
    	for(State s : states) {
    		sb.append("STATE ").append(s.toString()).append("\n");
    	}
    	for(State s : states) {
    		for(Transition t : s.transitionsFrom()) {
    			sb.append("TRANSITION ").append(t.toString()).append("\n");
    		}
    	}
    	for(State s : getInitialStates()) {
    		sb.append("INITIAL ").append(s.getName()).append("\n");
    	}
    	for(State s : getFinalStates()) {
    		sb.append("FINAL ").append(s.getName()).append("\n");
    	} 
    	return sb.toString();
    }
    
    // check if the name is beginning with a letter, followed by zero or more letter, digit, or underscore characters or not
    private boolean isValidNaming(final String name) {
    	if(name == null) return false;
		Pattern pattern = Pattern.compile("^[A-Za-z]+[A-Za-z_\\d]*$");
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}
	
    // check if the name is character string consisting only of letters
	private boolean isLetter(final String name) {
		Pattern p = Pattern.compile("^[A-Za-z]+$");
		Matcher m = p.matcher(name);
		return m.matches();
	}
	
}
