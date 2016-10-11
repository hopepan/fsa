import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class StateIcon extends JComponent implements StateListener {

	@Override
	public void StateHasChanged() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		System.out.println("g>>"+g);
		g.setColor(Color.GREEN);
		g.fillOval(100, 100, 200, 200);
	}

}
