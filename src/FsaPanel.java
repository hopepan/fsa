import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FsaPanel extends JPanel implements FsaListener, MouseMotionListener, MouseListener {

	private final static int M_CREATING_TRANSITION = -2;
	private final static int M_CREATING_STATE = -1;
	private final static int M_IDLE = 0;
	private final static int M_SELECTING = 1;
	private final static int M_DRAGGING = 2;
	
	private int machineState = M_IDLE;

	private Fsa fsa;
	
	private Map<State, StateIcon> states;
	
	private Map<Transition, TransitionIcon> transitions;
	
	private State creatingState;
	
	private String creatingEventName;
	private StateIcon fromStateIcon;
	private StateIcon toStateIcon;
	
	private int x0;
	private int y0;
	private int x;
	private int y;
	
	public FsaPanel() {
		// initialize the states
		states = new HashMap<>();
		transitions = new HashMap<>();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public void resetPanel() {
		states.clear();
		transitions.clear();
		this.removeAll();
		this.fsa = null;
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		machineState = M_IDLE;
		creatingState = null;
		creatingEventName = null;
		fromStateIcon = null;
		toStateIcon = null;
		x0 = 0;
		y0 = 0;
		x = 0;
		y = 0;
	}

	@Override
	public void statesChanged() {
		Set<State> statesInFSA = this.fsa.getStates();
		int sizeInFSA = statesInFSA.size();
		int size = states.size();
		if(sizeInFSA > size) {
			// added new state in FSA
			for(State s : statesInFSA) {
				if(!states.containsKey(s)) {
					StateIcon si = new StateIcon(s);
//				si.setBorder(new LineBorder(Color.BLACK));
					s.addListener(si);
					// add the state to panel
					this.add(si);
					// update the states
//					states.add(s);
					states.put(s, si);
				}
			}
		} else if(sizeInFSA < size) {
			// removed new state in FSA
			for(State s : states.keySet()) {
				if(!statesInFSA.contains(s)) {
					this.remove(states.get(s));
				}
			}
		}
		repaint();
	}

	@Override
	public void transitionsChanged() {
		Set<State> statesInFSA = this.fsa.getStates();
		int transSize = this.transitions.size();
		int transSizeFSA = 0;
		for(State s : statesInFSA) {
			transSizeFSA += s.transitionsFrom().size();
		}
		if(transSizeFSA > transSize) {
			// new transitions
			for(State s : statesInFSA) {
				Set<Transition> froms = s.transitionsFrom();
				for(Transition t : froms) {
					if(!transitions.containsKey(t)) {
						TransitionIcon ti = new TransitionIcon(t);
						t.addListener(ti);
						this.add(ti);
						ti.TransitionHasChanged();
						transitions.put(t, ti);
					}
				}
			}
		} else if(transSizeFSA < transSize) {
			// remove transitions
			for(Transition t : transitions.keySet()) {
				boolean isDeleted = true;
				for(State s : statesInFSA) {
					Set<Transition> froms = s.transitionsFrom();
					if(froms.contains(t)) {
						isDeleted = false;
						break;
					}
				}
				if(isDeleted) {
					this.remove(transitions.get(t));
				}
			}
		}
		repaint();
	}

	@Override
	public void otherChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		if(machineState == M_CREATING_STATE) {
			this.creatingState = null;
			machineState = M_IDLE;
		} else if(machineState == M_CREATING_TRANSITION) {
			int eX = e.getX();
			int eY = e.getY();
			for(Component c : this.getComponents()) {
				if(c instanceof StateIcon) {
					StateIcon si = (StateIcon) c;
					// the clicked point is inside the stateIcon
					if(si.isInside(eX, eY)) {
						if(fromStateIcon == null) {
							this.fromStateIcon = si;
						} else if(toStateIcon == null) {
							this.toStateIcon = si;
							try {
								this.fsa.newTransition(fromStateIcon.getState(), toStateIcon.getState(), this.creatingEventName);
							} catch (IllegalArgumentException ex) {
					    		JOptionPane.showMessageDialog(this, ex.getMessage(), "New Transition",JOptionPane.WARNING_MESSAGE);
					    	}
							setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							machineState = M_IDLE;
							this.creatingEventName = null;
							this.fromStateIcon = null;
							this.toStateIcon = null;
						}
						break;
					}
				}
			}
			if(fromStateIcon == null && toStateIcon == null) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				machineState = M_IDLE;
				this.creatingEventName = null;
				this.fromStateIcon = null;
				this.toStateIcon = null;
			}
		}
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
		if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1
				&& machineState == M_IDLE) {
			x0 = e.getX();
			y0 = e.getY();
			x = x0;
			y = y0;
			StateIcon selectedSi = null;
			TransitionIcon selectedTi = null;
			for(Component c : this.getComponents()) {
				if(c instanceof StateIcon) {
					StateIcon si = (StateIcon) c;
					// the pressed point is inside the stateIcon
					if(si.isInside(x0, y0)) {
						selectedSi = si;
					}
				} else if(c instanceof TransitionIcon) {
					TransitionIcon ti = (TransitionIcon) c;
					if(ti.isInside(x0, y0)) {
						selectedTi = ti;
					}
				}
			}
			// no state is pressed
			if(selectedSi == null) {
				machineState = M_SELECTING;
				for(Component c : this.getComponents()) {
					if(c instanceof StateIcon) {
						StateIcon si = (StateIcon) c;
						si.setSelected(false);
					}
				}
			} else {
				if(selectedSi.isSelected()) {
					machineState = M_DRAGGING;
				} else {
					for(Component c : this.getComponents()) {
						if(c instanceof StateIcon) {
							StateIcon si = (StateIcon) c;
							si.setSelected(false);
						}
					}
					selectedSi.setSelected(true);
				}
			}
			// no transition is pressed
			if(selectedTi == null) {
//				machineState = M_SELECTING;
				for(Component c : this.getComponents()) {
					if(c instanceof TransitionIcon) {
						TransitionIcon ti = (TransitionIcon) c;
						ti.setSelected(false);
					}
				}
			} else {
				if(!selectedTi.isSelected()) {
					for(Component c : this.getComponents()) {
						if(c instanceof TransitionIcon) {
							TransitionIcon ti = (TransitionIcon) c;
							ti.setSelected(false);
						}
					}
					selectedTi.setSelected(true);
				}
			}
//			this.repaint();
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1
				&& (machineState == M_DRAGGING || machineState == M_SELECTING)) {
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
				} else if(c instanceof TransitionIcon) {
					TransitionIcon ti = (TransitionIcon) c;
					if(ti.intersects(rec)) {
						ti.setSelected(true);
					} else {
						ti.setSelected(false);
					}
				}
			}
			this.repaint();
		} else if(machineState == M_DRAGGING) {
			x = e.getX();
			y = e.getY();
			if(x > 0 && x < this.getWidth() - StateIcon.D_CIRCLE
					&& y > 0 && y < this.getHeight() - StateIcon.D_CIRCLE) {
				for(Component c : this.getComponents()) {
					if(c instanceof StateIcon) {
						StateIcon si = (StateIcon) c;
						if(si.isSelected()) {
							si.moveBy(x-x0, y-y0);
						}
					} else if(c instanceof TransitionIcon) {
						TransitionIcon ti = (TransitionIcon) c;
						ti.TransitionHasChanged();
					}
				}
				x0 = x;
				y0 = y;
			}
			
//			this.repaint();
		}
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		if(machineState == M_CREATING_STATE && this.creatingState != null) {
			this.creatingState.moveBy(e.getX()-this.creatingState.getXpos()-StateIcon.R_CIRCLE, 
					e.getY()-this.creatingState.getYpos()-StateIcon.Y_GAP-StateIcon.R_CIRCLE);
		} else if(machineState == M_CREATING_TRANSITION && fromStateIcon != null) {
			repaint();
		}
	}
	
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		if(machineState == M_SELECTING) {
			g.setColor(Color.black);
			Rectangle rec = buildRec(x0, y0, x, y);
			g.drawRect(rec.x, rec.y, rec.width, rec.height);
		} else if(machineState == M_CREATING_TRANSITION) {
			if(fromStateIcon != null && toStateIcon == null) {
				Point p = fromStateIcon.getLocation();
				Point mousePoint = MouseInfo.getPointerInfo().getLocation();
				g.drawLine(p.x+StateIcon.R_CIRCLE, p.y+StateIcon.R_CIRCLE+StateIcon.Y_GAP, mousePoint.x, mousePoint.y-StateIcon.R_CIRCLE-StateIcon.Y_GAP);
			}
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
	
	public void setMachineStateCreatingState(final State s) {
		this.machineState = M_CREATING_STATE;
		this.creatingState = s;
	}
	
	public void setMachineStateCreatingTransition(final String eventName) {
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		this.machineState = M_CREATING_TRANSITION;
		this.creatingEventName = eventName;
		this.fromStateIcon = null;
		this.toStateIcon = null;
	}

	/**
	 * @param fsa the fsa to set
	 */
	public void setFsa(final Fsa fsa) {
		this.fsa = fsa;
	}
}
