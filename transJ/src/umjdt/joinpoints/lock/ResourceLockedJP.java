package umjdt.joinpoints.lock;

import org.aspectj.lang.JoinPoint;

import umjdt.joinpoints.EndHoldingResourceEventJP;
import umjdt.joinpoints.StartHoldingResourceEventJP;
import umjdt.joinpoints.TransJP;

public class ResourceLockedJP extends TransJP {

	private JoinPoint resourcelockedJP;
	private StartHoldingResourceEventJP startHoldResourceJp;
	private EndHoldingResourceEventJP endHoldResourceJp;

	public JoinPoint getResourcelockedJP() {
		return resourcelockedJP;
	}

	public void setResourcelockedJP(JoinPoint resourcelockedJP) {
		this.resourcelockedJP = resourcelockedJP;
	}

	public StartHoldingResourceEventJP getStartHoldResourceJp() {
		return startHoldResourceJp;
	}

	public void setStartHoldResourceJp(
			StartHoldingResourceEventJP startHoldResourceJp) {
		this.startHoldResourceJp = startHoldResourceJp;
	}

	public EndHoldingResourceEventJP getEndHoldResourceJp() {
		return endHoldResourceJp;
	}

	public void setEndHoldResourceJp(EndHoldingResourceEventJP endHoldResourceJp) {
		this.endHoldResourceJp = endHoldResourceJp;
	}

}
