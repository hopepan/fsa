import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class FsaPanel extends JPanel implements FsaListener {

	private Fsa fsa;
	
	private Set<State> states;
	
	public FsaPanel() {
		// initialize the states
		states = new CopyOnWriteArraySet<>();
	}
	
	public void resetPanel() {
		states.clear();
		this.removeAll();
		this.fsa = null;
	}

	@Override
	public void statesChanged() {
		System.out.println("state");
		Set<State> changedStates = new CopyOnWriteArraySet<>();
		Set<State> statesInFSA = this.fsa.getStates();
		int sizeInFSA = statesInFSA.size();
		int size = states.size();
		System.out.println("sizeInFSA>>"+sizeInFSA);
		System.out.println("size>>"+size);
		if(sizeInFSA > size) {
			// added new state in FSA
			for(State s : statesInFSA) {
				if(!states.contains(s)) {
					changedStates.add(s);
				}
			}
			System.out.println("changedStates>>"+changedStates);
			// handle the changed states
			for(State s : changedStates) {
				StateIcon si = new StateIcon(s);
				s.addListener(si);
				StateIconMouseListener l = new StateIconMouseListener();
				si.addMouseListener(l);
				si.addMouseMotionListener(l);
				// add the state to panel
				System.out.println(si);
				this.add(si);
				// update the states
				states.add(s);
			}
//			repaint();
			updateUI();
		} else if(sizeInFSA < size) {
			// removed new state in FSA
			for(State s : states) {
				if(!statesInFSA.contains(s)) {
					changedStates.add(s);
				}
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
