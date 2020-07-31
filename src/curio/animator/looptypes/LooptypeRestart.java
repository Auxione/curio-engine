package animator.looptypes;

import animator.Animator;

public final class LooptypeRestart implements Looptype {
	@Override
	public void modify(Animator animator) {
		if (animator.isFinished()) {
			animator.restart().play();
		}
	}
}
