import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class StateIcon extends JComponent implements StateListener {

	public static final int Y_GAP = 25;
	public static final int D_CIRCLE = 50;
	public static final int R_CIRCLE = D_CIRCLE/2;
	
	private State state;
	
	private boolean isSelected;
	
	private boolean isPressed;
	
	public StateIcon(final State s) {
		this.state = s;
		// set these 2 lines for paint this component
		setSize(new Dimension(D_CIRCLE, D_CIRCLE+Y_GAP));
		setLocation(state.getXpos(), state.getYpos());
	}
	
	@Override
	public void StateHasChanged() {
		setLocation(state.getXpos(), state.getYpos());
		repaint();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		System.out.println("icon");
		super.paintComponent(g);
		
		// draw selection
		if(isSelected || isPressed) {
			g.setColor(Color.YELLOW);
			g.fillOval(0, Y_GAP, D_CIRCLE, D_CIRCLE);
			isPressed = false;
		} else {
			g.setColor(Color.GRAY);
			g.fillOval(0, Y_GAP, D_CIRCLE, D_CIRCLE);
		}
		
		// draw state
		g.setColor(Color.black);
		if(state.isInitial()) {
			// draw lightning strike
		    g.drawLine(0, 0, 10, 20);
		    g.drawLine(10, 20, 10, 5);
		    g.drawLine(10, 5, 20, 25);
//			int[] xPoints = {20, 12, 20};
//			int[] yPoints = {25, 12, 20};
//			g.drawPolygon(xPoints, yPoints, xPoints.length);
		    g.drawOval(0, Y_GAP, D_CIRCLE, D_CIRCLE);
		} 
		if(state.isFinal()) {
			// draw double-circle
			g.drawOval(0, Y_GAP, D_CIRCLE, D_CIRCLE);
			g.drawOval(5, Y_GAP+5, D_CIRCLE-10, D_CIRCLE-10);
		}
		if(!state.isInitial() && !state.isFinal()) {
			g.drawOval(0, Y_GAP, D_CIRCLE, D_CIRCLE);
		}
		
		// draw naming
		g.setColor(Color.black);
		int len = state.getName().length();
		g.drawString(state.getName(), (D_CIRCLE-len*6)/2, Y_GAP+R_CIRCLE+5);
	}

	public boolean isInside(final int x, final int y) {
		double circleX = getX() + R_CIRCLE;
		double circleY = getY() + Y_GAP + R_CIRCLE;
		double ar = Math.sqrt(Math.pow(Math.abs(x - circleX), 2)
				+ Math.pow(Math.abs(y - circleY), 2));
		if (ar >= R_CIRCLE) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean intersects(final Rectangle rec) {
		return rec.intersects(new Rectangle(this.getLocation().x, this.getLocation().y+Y_GAP, this.getWidth(), this.getHeight()-Y_GAP));
	}
	
	public void moveBy(final int dx, final int dy) {
		this.state.moveBy(dx, dy);
//		setLocation(state.getXpos()+dx, state.getYpos()+dy);
	}
	
	/**
	 * @return the state
	 */
	public State getState() {
		return state;
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

	/**
	 * @return the isPressed
	 */
	public boolean isPressed() {
		return isPressed;
	}

	/**
	 * @param isPressed the isPressed to set
	 */
	public void setPressed(final boolean isPressed) {
		this.isPressed = isPressed;
	}

}
