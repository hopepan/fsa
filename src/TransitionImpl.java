
public class TransitionImpl implements Transition {

	private State fromState;
	
	private State toState;
	
	private String eventName;
	
	private TransitionListener transitionListener;
	

	public TransitionImpl(final State fromState, final State toState, final String eventName) {
		super();
		this.fromState = fromState;
		this.toState = toState;
		this.eventName = eventName;
	}

	@Override
	public void addListener(final TransitionListener tl) {
		this.transitionListener = tl;
	}

	@Override
	public void removeListener(final TransitionListener tl) {
		if(tl.equals(this.transitionListener)) {
			this.transitionListener = null;
		}
	}

	@Override
	public State fromState() {
		return this.fromState;
	}

	@Override
	public State toState() {
		return this.toState;
	}

	@Override
	public String eventName() {
		return this.eventName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventName == null) ? 0 : eventName.hashCode());
		result = prime * result + ((fromState == null) ? 0 : ((StateImpl)fromState).hashCode());
		result = prime * result + ((toState == null) ? 0 : ((StateImpl)toState).hashCode());
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
		TransitionImpl other = (TransitionImpl) obj;
		if (fromState == null) {
			if (other.fromState != null)
				return false;
		} else if (!fromState.equals(other.fromState))
			return false;
		if (toState == null) {
			if (other.toState != null)
				return false;
		} else if (!toState.equals(other.toState))
			return false;
		if (eventName == null) {
			if (other.eventName != null)
				return false;
		} else if (!eventName.equals(other.eventName))
			return false;
		return true;
	} 
	
	 //Return a string containing information about this transition 
    //in the form (without quotes, of course!):
    //"fromStateName(eventName)toStateName"
    @Override
	public String toString() {
    	String outputEvent = this.eventName == null?"":this.eventName;
    	return this.fromState.getName() + "(" + outputEvent + ")" + this.toState.getName();
    }
}
