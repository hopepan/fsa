import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class StateImpl implements State {

	private String name;
	
	private int xPos;
	
	private int yPos;
	
	private boolean isInitial;
	
	private boolean isFinal;
	
	private boolean isCurrent;
	
	private StateListener stateListener;
	
	// the transitionsFrom list
	private Set<Transition> transitionsFrom;
	
	// the transitionsTo list
	private Set<Transition> transitionsTo;
	
	public StateImpl(final String name, final int xPos, final int yPos) {
		super();
		this.name = name;
		this.xPos = xPos;
		this.yPos = yPos;
		this.transitionsFrom = new CopyOnWriteArraySet<>();
		this.transitionsTo = new CopyOnWriteArraySet<>();
	}

	@Override
	public void addListener(final StateListener sl) {
		this.stateListener = sl;
	}

	@Override
	public void removeListener(final StateListener sl) {
		if(this.stateListener != null && this.stateListener.equals(sl)) {
			this.stateListener = null;
		}
	}

	@Override
	public Set<Transition> transitionsFrom() {
		return this.transitionsFrom;
	}

	@Override
	public Set<Transition> transitionsTo() {
		return this.transitionsTo;
	}

	@Override
	public void moveBy(final int dx, final int dy) {
		if(dx != 0 && dy != 0) {
			xPos += dx;
			yPos += dy;
			if(this.stateListener != null) {
				this.stateListener.StateHasChanged();
			}
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getXpos() {
		return this.xPos;
	}

	@Override
	public int getYpos() {
		return this.yPos;
	}

	@Override
	public void setInitial(final boolean b) {
		if(b != this.isInitial && this.stateListener != null) {
			this.stateListener.StateHasChanged();
		}
		this.isInitial = b;
	}

	@Override
	public boolean isInitial() {
		return this.isInitial;
	}

	@Override
	public void setFinal(final boolean b) {
		if(b != this.isFinal && this.stateListener != null) {
			this.stateListener.StateHasChanged();
		}
		this.isFinal = b;
	}

	@Override
	public boolean isFinal() {
		return this.isFinal;
	}

	@Override
	public boolean isCurrent() {
		return this.isCurrent;
	}
	
	public void setCurrent(final boolean current) {
		this.isCurrent = current;
	}

	@Override
	//stateName(xPos,yPos)jk
	public String toString() {
		return name + "(" + xPos + "," + yPos + ")" + (this.isInitial?1:0) + "" + (this.isFinal?1:0);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StateImpl other = (StateImpl) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	} 
	
	
}
