package animator.looptypes;

import animator.Animator;

public final class LooptypeYoyo implements Looptype {
	boolean forward = true;

	@Override
	public void modify(Animator animator) {
		if (animator.isFinished()) {
			animator.reverse().restart().play();
		}
	}

}
