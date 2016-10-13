import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class StateIconMouseListener implements MouseMotionListener, MouseListener {


	@Override
	public void mouseClicked(final MouseEvent e) {
		StateIcon si = ((StateIcon) e.getComponent());
		si.setSelected(!si.isSelected());
		si.repaint();
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		System.out.println("mouse press");
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		System.out.println("mouse release");
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		System.out.println("mouse enter");
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		System.out.println("mouse exit");
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		System.out.println("mouse drag");
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		System.out.println("mouse move");
	}
}
