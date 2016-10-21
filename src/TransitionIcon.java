import java.awt.Graphics;

import javax.swing.JComponent;

public class TransitionIcon extends JComponent implements TransitionListener {

	private Transition transition;
	
	private double FIX_ARC = Math.PI / 6;
	
	private double arc;
	
	public TransitionIcon(Transition t) {
		this.transition = t;
	}
	
	@Override
	public void TransitionHasChanged() {
		State fromState = transition.fromState();
		State toState = transition.toState();
		int x1 = fromState.getXpos();
		int y1 = fromState.getYpos();
		int x2 = toState.getXpos();
		int y2 = toState.getYpos();
		setSize(Math.abs(x2 - x1)+StateIcon.D_CIRCLE, Math.abs(y2 - y1)+StateIcon.D_CIRCLE);
		setLocation(x1<x2?x1:x2, y1<y2?y1:y2);
		
		// prepare the 3 points for curve
		if(x1 == x2) {
			arc = 0;
		} else {
			arc = Math.atan((y2-y1)/(x2-x1));
		}
		double p1x = 0;
		double p1y = 0;
		if(x2 >= x1) {
			p1x = x1 + StateIcon.R_CIRCLE + StateIcon.R_CIRCLE * Math.cos(FIX_ARC - arc);
			p1y = y1 + StateIcon.R_CIRCLE - StateIcon.R_CIRCLE * Math.sin(FIX_ARC - arc);
		} else {
			p1x = x1 + StateIcon.R_CIRCLE - StateIcon.R_CIRCLE * Math.cos(FIX_ARC - arc);
			p1y = y1 + StateIcon.R_CIRCLE + StateIcon.R_CIRCLE * Math.sin(FIX_ARC - arc);
		}
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}
}
