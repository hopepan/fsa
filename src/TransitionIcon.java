import java.awt.Graphics;

import javax.swing.JComponent;

public class TransitionIcon extends JComponent implements TransitionListener {

	private Transition transition;
	
	
	
	@Override
	public void TransitionHasChanged() {
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}
}
