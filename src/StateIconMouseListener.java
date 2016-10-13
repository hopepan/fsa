import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class StateIconMouseListener implements MouseMotionListener, MouseListener {


	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouse click");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mouse press");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("mouse release");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("mouse enter");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("mouse exit");
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("mouse drag");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println("mouse move");
	}
}
