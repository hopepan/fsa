import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class FsaPanel extends JPanel implements FsaListener, MouseMotionListener, MouseListener {

	private static int M_IDLE = 0;
	private static int M_SELECTING = 1;
	private static int M_DRAGGING = 2;
	
	private Fsa fsa;
	
	private Set<State> states;
	
	private int machineState = M_IDLE;
	
	private int x0;
	private int y0;
	private int x;
	private int y;
	
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
		machineState = M_IDLE;
		x0 = 0;
		y0 = 0;
		x = 0;
		y = 0;
	}

	@Override
	public void statesChanged() {
		Set<State> changedStates = new CopyOnWriteArraySet<>();
		Set<State> statesInFSA = this.fsa.getStates();
		int sizeInFSA = statesInFSA.size();
		int size = states.size();
		if(sizeInFSA > size) {
			// added new state in FSA
			for(State s : statesInFSA) {
				if(!states.contains(s)) {
					changedStates.add(s);
				}
			}
			// handle the changed states
			for(State s : changedStates) {
				StateIcon si = new StateIcon(s);
//				si.setBorder(new LineBorder(Color.BLACK));
				s.addListener(si);
				// add the state to panel
				this.add(si);
				// update the states
				states.add(s);
			}
			repaint();
//			updateUI();
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
//		if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
//			int eX = e.getX();
//			int eY = e.getY();
//			for(Component c : this.getComponents()) {
//				if(c instanceof StateIcon) {
//					StateIcon si = (StateIcon) c;
//					// the clicked point is inside the stateIcon
//					if(si.isInside(eX, eY)) {
//						si.setSelected(!si.isSelected());
//						break;
//					}
//				}
//			}
//			repaint();
//		}
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
			x0 = e.getX();
			y0 = e.getY();
			x = x0;
			y = y0;
			StateIcon selected = null;
			for(Component c : this.getComponents()) {
				if(c instanceof StateIcon) {
					StateIcon si = (StateIcon) c;
					// the pressed point is inside the stateIcon
					if(si.isInside(x0, y0)) {
						selected = si;
					}
				}
			}
			// no state is pressed
			if(selected == null) {
				machineState = M_SELECTING;
				for(Component c : this.getComponents()) {
					if(c instanceof StateIcon) {
						StateIcon si = (StateIcon) c;
						si.setSelected(false);
					}
				}
			} else {
				if(selected.isSelected()) {
					machineState = M_DRAGGING;
				} else {
					for(Component c : this.getComponents()) {
						if(c instanceof StateIcon) {
							StateIcon si = (StateIcon) c;
							si.setSelected(false);
						}
					}
//					selected.setPressed(true);
					selected.setSelected(true);
				}
			}
//			this.repaint();
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
			machineState = M_IDLE;
		}
		this.repaint();
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		if(machineState == M_SELECTING) {
			x = e.getX();
			y = e.getY();
			Rectangle rec = buildRec(x0, y0, x, y);
			for(Component c : this.getComponents()) {
				if(c instanceof StateIcon) {
					StateIcon si = (StateIcon) c;
					if(si.intersects(rec)) {
						si.setSelected(true);
					} else {
						si.setSelected(false);
					}
				}
			}
			this.repaint();
		} else if(machineState == M_DRAGGING) {
			x = e.getX();
			y = e.getY();
			for(Component c : this.getComponents()) {
				if(c instanceof StateIcon) {
					StateIcon si = (StateIcon) c;
					if(si.isSelected()) {
						si.moveBy(x-x0, y-y0);
					}
				}
			}
			x0 = x;
			y0 = y;
//			this.repaint();
		}
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
	}
	
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if(machineState == M_SELECTING) {
			g.setColor(Color.black);
			Rectangle rec = buildRec(x0, y0, x, y);
			g.drawRect(rec.x, rec.y, rec.width, rec.height);
		}
	}
	
	private Rectangle buildRec(final int x1, final int y1, final int x2, final int y2) {
		Rectangle rec = null;
		if(x2-x1 >= 0 && y2-y1 >= 0) {
			rec = new Rectangle(x1, y1, x2-x1, y2-y1);
		} else if(x2-x1 >= 0 && y2-y1 < 0) {
			rec = new Rectangle(x1, y2, x2-x1, y1-y2);
		} else if(x2-x1 < 0 && y2-y1 >= 0) {
			rec = new Rectangle(x2, y1, x1-x2, y2-y1);
		} else {
			rec = new Rectangle(x2, y2, x1-x2, y1-y2);
		}
		return rec;
	}
	
	/**
	 * @param fsa the fsa to set
	 */
	public void setFsa(final Fsa fsa) {
		this.fsa = fsa;
	}
}
