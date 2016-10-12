import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.JPanel;

public class FsaPanel extends JPanel implements FsaListener {

	private Fsa fsa;
	
	private Set<State> states;
	
	public FsaPanel() {
		// initialize the states
		states = new CopyOnWriteArraySet<>();
		StateIcon si = new StateIcon();
		this.add(si);
	}

	@Override
	public void statesChanged() {
		System.out.println("state");
		Set<State> changedStates = new CopyOnWriteArraySet<>();
		Set<State> statesInFSA = this.fsa.getStates();
		for(State s : states) {
			if(!statesInFSA.contains(s)) {
				changedStates.add(s);
			}
		}
	}

	@Override
	public void transitionsChanged() {
		System.out.println("transition");
	}

	@Override
	public void otherChanged() {
		// TODO Auto-generated method stub

	}

	/**
	 * @param fsa the fsa to set
	 */
	public void setFsa(final Fsa fsa) {
		this.fsa = fsa;
	}
}
