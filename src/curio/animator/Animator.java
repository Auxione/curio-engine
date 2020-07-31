package animator;

import animator.equations.AnimEquation;
import animator.looptypes.Looptype;

public abstract class Animator {

	protected void onReset() {
	}

	protected void onRestart() {
	}

	protected abstract void onFixedUpdate(float ratio);

	private float currentTime;
	public float goalTime = 1000;
	private boolean running = false;

	public AnimEquation computeEquation;
	public Looptype looptype;
	private AnimatorCallBack callback;
	private boolean forward = true;

	public Animator(AnimEquation computeEquation) {
		this.computeEquation = computeEquation;
	}

	public final Animator reset() {
		this.currentTime = 0;
		onReset();
		return this;
	}

	public final Animator restart() {
		if (this.forward)
			this.currentTime = 0;
		else
			this.currentTime = this.goalTime;
		onRestart();
		if (callback != null) {
			callback.onStart(this);
		}
		return this;
	}

	public final Animator play() {
		this.running = true;
		return this;
	}

	public final Animator pause() {
		this.running = false;
		return this;
	}

	public final Animator setTime(int time) {
		this.currentTime = 0;
		this.goalTime = time;
		return this;
	}

	public final Animator setDirection(boolean direction) {
		this.forward = direction;
		return this;
	}

	public final Animator setCallBack(AnimatorCallBack callback) {
		this.callback = callback;
		return this;
	}

	public final boolean isFinished() {
		return !this.running;
	}

	public boolean getDirection() {
		return this.forward;
	}

	public Animator reverse() {
		this.forward = !this.forward;
		return this;
	}

	public final void fixedUpdate(float deltaTime) {
		if (!this.running) {
			return;
		}

		float ratio = this.computeEquation.calculate(currentTime / goalTime);

		check(ratio);

		if (this.looptype != null) {
			this.looptype.modify(this);
		}

		if (!this.running) {
			return;
		}

		onFixedUpdate(ratio);

		advanceOneFrame();
	}

	private void check(float time) {
		if (this.forward && time > 1.0f) {
			this.running = false;
			if (callback != null) {
				callback.onFinished(this);
			}
		}

		else if (!this.forward && time < 0.0f) {
			this.running = false;
			if (callback != null) {
				callback.onFinished(this);
			}
		}
	}

	public final Animator advanceOneFrame() {
		if (this.running) {
			if (this.forward)
				this.currentTime++;
			else
				this.currentTime--;
		}
		return this;
	}
}
