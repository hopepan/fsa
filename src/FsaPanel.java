import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

public class FsaPanel extends JPanel implements FsaListener {

	
	public FsaPanel() {
		super(null);
		StateIcon si = new StateIcon();
		JButton b = new JButton("fdsa");
		this.setBackground(Color.white);
		si.setLocation(100, 100);
		this.add(si);
		this.add(b);
	}

	@Override
	public void statesChanged() {
		System.out.println("state");
	}

	@Override
	public void transitionsChanged() {
		System.out.println("transition");
	}

	@Override
	public void otherChanged() {
		// TODO Auto-generated method stub

	}

}
