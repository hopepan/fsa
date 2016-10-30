import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.QuadCurve2D;

import javax.swing.JComponent;

public class TransitionIcon extends JComponent implements TransitionListener {

	private Transition transition;
	
	private boolean isSelected;
	
	private static final double FIX_ARC = Math.PI / 6;
	private static final double SIN_FIX_ARC = Math.sin(FIX_ARC);
	private static final double COS_FIX_ARC = Math.cos(FIX_ARC);
	private static final double TAN_FIX_ARC = Math.tan(FIX_ARC);
	
	private static final double ARROW_ARC = Math.PI / 30;
	private static final double SIN_ARROW_ARC = Math.sin(ARROW_ARC);
	private static final double COS_ARROW_ARC = Math.cos(ARROW_ARC);
	private static final int ARROW_LEN = 10;
	
	private static final double SIN_BOTH =  Math.sin(FIX_ARC + ARROW_ARC);
	private static final double COS_BOTH =  Math.cos(FIX_ARC + ARROW_ARC);
	private static final double SIN_DIFF =  Math.sin(FIX_ARC - ARROW_ARC);
	private static final double COS_DIFF =  Math.cos(FIX_ARC - ARROW_ARC);
	
	private double arc;
	private double xb1 = 0;
	private double yb1 = 0;
	private double xb2 = 0;
	private double yb2 = 0;
	private double xc = 0;
	private double yc = 0;
	private double arrow1x = 0;
	private double arrow1y = 0;
	private double arrow2x = 0;
	private double arrow2y = 0;
	
	public TransitionIcon(final Transition t) {
		this.transition = t;
		this.isSelected = false;
	}
	
	public Transition getTransition() {
		return transition;
	}
	
	public boolean isInside(final int x, final int y) {
		Point p = getLocation();
		int boundXL = p.x;
		int boundYL = p.y;
		int boundXR = boundXL + this.getWidth();
		int boundYR = boundYL + this.getHeight();
		if(x < boundXL || x > boundXR || y < boundYL || y > boundYR) {
			return false;
		}
		return true;
	}
	
	public boolean intersects(final Rectangle rec) {
		return rec.intersects(new Rectangle(this.getLocation().x, this.getLocation().y, this.getWidth(), this.getHeight()));
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
//		setBorder(new LineBorder(Color.BLUE));
		
		// prepare the 3 points for curve
		double b = Math.sqrt(Math.pow(y2-y1, 2) + Math.pow(x2-x1, 2))/2/COS_FIX_ARC;
		int lx = getLocation().x;
		int ly = getLocation().y;
		if(x2 > x1) {
			arc = Math.atan((y2-y1)/(x2-x1));
			double cos1 = Math.cos(FIX_ARC - arc);
			double sin1 = Math.sin(FIX_ARC - arc);
			double cos2 = Math.cos(FIX_ARC + arc);
			double sin2 = Math.sin(FIX_ARC + arc);
			xb1 = x1 - lx + StateIcon.R_CIRCLE * (1 + cos1);
			yb1 = y1 - ly + StateIcon.Y_GAP + StateIcon.R_CIRCLE * (1 - sin1);
			xb2 = x2 - lx + StateIcon.R_CIRCLE * (1 - cos2);
			yb2 = y2 - ly + StateIcon.Y_GAP + StateIcon.R_CIRCLE * (1 - sin2);
			xc = x1 - lx + StateIcon.R_CIRCLE + b * cos1;
			yc = y1 - ly + StateIcon.Y_GAP + StateIcon.R_CIRCLE - b * sin1;
			arrow1x = xb2 - ARROW_LEN * (cos2*COS_ARROW_ARC - sin2*SIN_ARROW_ARC);//Math.cos(FIX_ARC + arc + ARROW_ARC);
			arrow1y = yb2 - ARROW_LEN * (sin2*COS_ARROW_ARC + cos2*SIN_ARROW_ARC);//Math.sin(FIX_ARC + arc + ARROW_ARC);
			arrow2x = xb2 - ARROW_LEN * (cos2*COS_ARROW_ARC + sin2*SIN_ARROW_ARC);//Math.cos(FIX_ARC + arc - ARROW_ARC);
			arrow2y = yb2 - ARROW_LEN * (sin2*COS_ARROW_ARC - cos2*SIN_ARROW_ARC);//Math.sin(FIX_ARC + arc - ARROW_ARC);
		} else if(x2 < x1){
			arc = Math.atan((y2-y1)/(x2-x1));
			double cos1 = Math.cos(FIX_ARC - arc);
			double sin1 = Math.sin(FIX_ARC - arc);
			double cos2 = Math.cos(FIX_ARC + arc);
			double sin2 = Math.sin(FIX_ARC + arc);
			xb1 = x1 - lx + StateIcon.R_CIRCLE * (1 - cos1);
			yb1 = y1 - ly + StateIcon.Y_GAP + StateIcon.R_CIRCLE * (1 + sin1);
			xb2 = x2 - lx + StateIcon.R_CIRCLE * (1 + cos2);
			yb2 = y2 - ly + StateIcon.Y_GAP+ StateIcon.R_CIRCLE * (1 + sin2);
			xc = x1 - lx + StateIcon.R_CIRCLE - b * cos1;
			yc = y1 - ly + StateIcon.Y_GAP + StateIcon.R_CIRCLE + b * sin1;
			arrow1x = xb2 + ARROW_LEN * (cos2*COS_ARROW_ARC - sin2*SIN_ARROW_ARC);//Math.cos(FIX_ARC + arc + ARROW_ARC);
			arrow1y = yb2 + ARROW_LEN * (sin2*COS_ARROW_ARC + cos2*SIN_ARROW_ARC);//Math.sin(FIX_ARC + arc + ARROW_ARC);
			arrow2x = xb2 + ARROW_LEN * (cos2*COS_ARROW_ARC + sin2*SIN_ARROW_ARC);//Math.cos(FIX_ARC + arc - ARROW_ARC);
			arrow2y = yb2 + ARROW_LEN * (sin2*COS_ARROW_ARC - cos2*SIN_ARROW_ARC);//Math.sin(FIX_ARC + arc - ARROW_ARC);
		} else {
			if(y1 < y2) {
				xb1 = x1 - lx + StateIcon.R_CIRCLE * (1 + SIN_FIX_ARC);
				yb1 = y1 - ly + StateIcon.Y_GAP + StateIcon.R_CIRCLE * (1 + COS_FIX_ARC);
				xb2 = x2 - lx + StateIcon.R_CIRCLE * (1 + SIN_FIX_ARC);
				yb2 = y2 - ly + StateIcon.Y_GAP + StateIcon.R_CIRCLE * (1 - COS_FIX_ARC);
				xc = x1 - lx + StateIcon.R_CIRCLE + (y2 - y1) / 2 * TAN_FIX_ARC;
				yc = y1 - ly + StateIcon.Y_GAP + StateIcon.R_CIRCLE +  (y2 - y1) / 2;
				arrow1x = xb2 + ARROW_LEN * SIN_BOTH;
				arrow1y = yb2 - ARROW_LEN * COS_BOTH;
				arrow2x = xb2 + ARROW_LEN * SIN_DIFF;
				arrow2y = yb2 - ARROW_LEN * COS_DIFF;
			} else if(y1 > y2) {
				xb1 = x1 - lx + StateIcon.R_CIRCLE * (1 - SIN_FIX_ARC);
				yb1 = y1 - ly + StateIcon.Y_GAP + StateIcon.R_CIRCLE * (1 - COS_FIX_ARC);
				xb2 = x2 - lx + StateIcon.R_CIRCLE * (1 - SIN_FIX_ARC);
				yb2 = y2 - ly + StateIcon.Y_GAP + StateIcon.R_CIRCLE * (1 + COS_FIX_ARC);
				xc = x1 - lx + StateIcon.R_CIRCLE - (y2 - y1) / 2 * TAN_FIX_ARC;
				yc = y1 - ly + StateIcon.Y_GAP + StateIcon.R_CIRCLE -  (y2 - y1) / 2;
				arrow1x = xb2 - ARROW_LEN * SIN_BOTH;
				arrow1y = yb2 + ARROW_LEN * COS_BOTH;
				arrow2x = xb2 - ARROW_LEN * SIN_DIFF;
				arrow2y = yb2 + ARROW_LEN * COS_DIFF;
			}
		}
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		QuadCurve2D shape = new QuadCurve2D.Double();
		shape.setCurve(xb1, yb1, xc, yc, xb2, yb2);
		if(this.isSelected) {
			g.setColor(Color.YELLOW);
		} else {
			g.setColor(Color.BLACK);
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.draw(shape);
		g.drawString(transition.eventName()==null?"?":transition.eventName(), (int)xc, (int)yc);
		g.drawLine((int)arrow1x, (int)arrow1y, (int)xb2, (int)yb2);
		g.drawLine((int)arrow2x, (int)arrow2y, (int)xb2, (int)yb2);
	}

	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(final boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}
