import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class ATest {

	public static void main(final String[] args) {
		File input = new File(args[0]);
		File output = new File(args[1]);
		FsaIo io = new FsaReaderWriter();
		Fsa f = new FsaImpl();
		Reader r = null;
		Writer w = null;
		try {
			r = new FileReader(input);
			w = new FileWriter(output);
			io.read(r, f);
//			Iterator<State> it = f.getStates().iterator();
//			while (it.hasNext()) {
//				State s = it.next();
//				f.removeState(s);
//			}
//			f.getStates().clear();
//			System.out.println(f.findState("stateName1"));
//			System.out.println(f.findState("stateName1").transitionsFrom());
//			System.out.println(f.findState("stateName1").transitionsTo());
//			f.removeState(f.findState("stateName2"));
//			for(Transition t : f.findTransition(f.findState("stateName1"), f.findState("stateName2"))) {
//				f.removeTransition(t);
//			}
//			State s1 = f.findState("stateName1");
//			System.out.println(s1.transitionsTo());
//			s1.transitionsFrom().clear();
//			State s2 = f.findState("stateName1");
////			s2.transitionsTo().clear();
//			Set<Transition> ts = f.findTransition(s1, s2);
//			Transition t = null;
//			for(Transition tr : ts) {
////				if(tr.eventName() == null) {
//				if(tr.eventName() != null && tr.eventName().equals("null")) {
//					t = tr;
//				}
//			}
//			f.removeTransition(t);
			
//			State s1 = f.newState("state1", 10, 22);
//			io.write(w, f);
//			io = new FsaReaderWriter();
//			s1.moveBy(11, 12);
//			State s2 = f.newState("state2", 110, 2);
//			State s3 = f.newState("state3", 115, 23);
//			State s4 = f.newState("state4", 115, 22);
//			Transition t=f.newTransition(s1, s1, null);
//			for(Transition t : f.findTransition(s1, s1))
//			f.removeTransition(t);
//			f.newTransition(s1, s1, null);
//			f.newTransition(s2, s4, "f");
//			f.newTransition(s2, s4, null);
//			f.newTransition(s1, s4, "fa");
//			f.removeState(s1);
//			io.read(r, f);
//			io.write(w, f);
			System.out.println(f.getCurrentStates());
			((FsaSim)f).step("event");
			System.out.println(f.getCurrentStates());
			System.out.println(((FsaSim)f).isRecognised());
			((FsaSim)f).reset();
			System.out.println(f.getCurrentStates());
			((FsaSim)f).step(null);
			System.out.println(((FsaSim)f).isRecognised());
			io.write(w, f);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				r.close();
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
