import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

public class StateIcon extends JComponent implements StateListener {

	public StateIcon() {
		super();
		setPreferredSize(new Dimension(800, 600));
	}
	@Override
	public void StateHasChanged() {

	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillOval(50, 30, 10, 10);;
	}

}
