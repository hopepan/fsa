import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.JPanel;

public class FsaPanel extends JPanel implements FsaListener, MouseMotionListener, MouseListener {

	private Fsa fsa;
	
	private Set<State> states;
	
	public FsaPanel() {
		// initialize the states
		states = new CopyOnWriteArraySet<>();
		addMouseListener(this);
		addMouseMotionListener(this);
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
//				si.setBorder(new LineBorder(Color.BLACK));
				s.addListener(si);
//				StateIconMouseListener l = new StateIconMouseListener();
//				si.addMouseListener(l);
//				si.addMouseMotionListener(l);
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

	@Override
	public void mouseClicked(final MouseEvent e) {
		// mouse left key single click
		if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
			int eX = e.getX();
			int eY = e.getY();
			for(Component c : this.getComponents()) {
				if(c instanceof StateIcon) {
					StateIcon si = (StateIcon) c;
					// the clicked point is inside the stateIcon
					if(si.isInside(eX, eY)) {
						si.setSelected(!si.isSelected());
						break;
					}
				}
			}
			repaint();
		}
	}

	@Override
	public void mousePressed(final MouseEvent e) {
//		System.out.println("mouse press");
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
//		System.out.println("mouse release");
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
//		System.out.println("mouse enter");
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
//		System.out.println("mouse drag");
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
	}
	
	/**
	 * @param fsa the fsa to set
	 */
	public void setFsa(final Fsa fsa) {
		this.fsa = fsa;
	}
}
