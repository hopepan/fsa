import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

public class StateIcon extends JComponent implements StateListener {

	private State state;
	
	private boolean isSelected;
	
	public StateIcon(final State s) {
		this.state = s;
		// set these 2 lines for paint this component
		setSize(new Dimension(80, 80));
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
		System.out.println("paint>>"+state.getName());
		super.paintComponent(g);
		if(state.isInitial()) {
			g.setColor(Color.GREEN);
		} else if(state.isFinal()) {
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.YELLOW);
		}
		if(isSelected) {
			g.fillOval(0, 0, 50, 50);
		} else {
			g.drawOval(0, 0, 50, 50);
		}
		g.setColor(Color.black);
		int len = state.getName().length();
		g.drawString(state.getName(), (50-len*6)/2, 30);
	}

}
