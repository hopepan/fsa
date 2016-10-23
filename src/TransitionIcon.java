import java.awt.Graphics;

import javax.swing.JComponent;

public class TransitionIcon extends JComponent implements TransitionListener {

	private Transition transition;
	
	private double FIX_ARC = Math.PI / 6;
	
	private double arc;
	
	public TransitionIcon(final Transition t) {
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
		double xb1 = 0;
		double yb1 = 0;
		double xb2 = 0;
		double yb2 = 0;
		double xc = 0;
		double yc = 0;
		double b = Math.sqrt(Math.pow(y2-y1, 2) + Math.pow(x2-x1, 2))/2/Math.cos(FIX_ARC);
		if(x2 > x1) {
			arc = Math.atan((y2-y1)/(x2-x1));
			xb1 = x1 + StateIcon.R_CIRCLE * (1 + Math.cos(FIX_ARC - arc));
			yb1 = y1 + StateIcon.R_CIRCLE * (1 - Math.sin(FIX_ARC - arc));
			xb2 = x2 + StateIcon.R_CIRCLE * (1 - Math.cos(FIX_ARC + arc));
			yb2 = y2 + StateIcon.R_CIRCLE * (1 - Math.sin(FIX_ARC + arc));
			xc = x1 + StateIcon.R_CIRCLE + b * Math.cos(FIX_ARC - arc);
			yc = y1 + StateIcon.R_CIRCLE - b * Math.sin(FIX_ARC - arc);
		} else if(x2 < x1){
			arc = Math.atan((y2-y1)/(x2-x1));
			xb1 = x1 + StateIcon.R_CIRCLE * (1 - Math.cos(FIX_ARC - arc));
			yb1 = y1 + StateIcon.R_CIRCLE * (1 + Math.sin(FIX_ARC - arc));
			xb2 = x2 + StateIcon.R_CIRCLE * (1 + Math.cos(FIX_ARC + arc));
			yb2 = y2 + StateIcon.R_CIRCLE * (1 + Math.sin(FIX_ARC + arc));
			xc = x1 + StateIcon.R_CIRCLE - b * Math.cos(FIX_ARC - arc);
			yc = y1 + StateIcon.R_CIRCLE + b * Math.sin(FIX_ARC - arc);
		} else {
			if(y1 < y2) {
				xb1 = x1 + StateIcon.R_CIRCLE * (1 + Math.sin(FIX_ARC));
				yb1 = y1 + StateIcon.R_CIRCLE * (1 + Math.cos(FIX_ARC));
				xb2 = x2 + StateIcon.R_CIRCLE * (1 + Math.sin(FIX_ARC));
				yb2 = y2 + StateIcon.R_CIRCLE * (1 - Math.cos(FIX_ARC));
				xc = x1 + StateIcon.R_CIRCLE + (y2 - y1) / 2 * Math.tan(FIX_ARC);
				yc = y1 + StateIcon.R_CIRCLE +  (y2 - y1) / 2;
			} else if(y1 > y2) {
				xb1 = x1 + StateIcon.R_CIRCLE * (1 - Math.sin(FIX_ARC));
				yb1 = y1 + StateIcon.R_CIRCLE * (1 - Math.cos(FIX_ARC));
				xb2 = x2 + StateIcon.R_CIRCLE * (1 - Math.sin(FIX_ARC));
				yb2 = y2 + StateIcon.R_CIRCLE * (1 + Math.cos(FIX_ARC));
				xc = x1 + StateIcon.R_CIRCLE - (y2 - y1) / 2 * Math.tan(FIX_ARC);
				yc = y1 + StateIcon.R_CIRCLE -  (y2 - y1) / 2;
			}
		}
		
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		
	}
}
