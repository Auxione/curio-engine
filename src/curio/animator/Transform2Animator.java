package animator;

import org.joml.Vector2f;

import animator.equations.AnimEquation;
import animator.looptypes.Looptype;
import common.math.Rotationf;
import common.math.Transform2;

public final class Transform2Animator extends Animator {
	private Transform2 transform;

	private Vector2f startPoint = new Vector2f();
	private Vector2f distance = new Vector2f();

	private Rotationf startRotation = new Rotationf();
	private Rotationf deltaRotation = new Rotationf();

	private Vector2f startScale = new Vector2f();
	private Vector2f deltaScaling = new Vector2f();

	private boolean move = false;
	private boolean rotate = false;
	private boolean scale = false;

	public Transform2Animator(Transform2 transform, AnimEquation computeEquation) {
		super(computeEquation);
		this.transform = transform;
	}

	public Transform2Animator(Transform2 transform, AnimEquation computeEquation, Looptype looptype) {
		super(computeEquation);
		super.looptype = looptype;
		this.transform = transform;
	}

	public final void move(Vector2f end) {
		move(end.x, end.y);
	}

	public final void move(float x, float y) {
		this.startPoint.set(this.transform.localPosition);
		this.distance.set(x, y);
		move = true;
	}

	public final void moveTo(Vector2f distance) {
		this.startPoint.set(this.transform.localPosition);
		distance.sub(startPoint, this.distance);
		move = true;
	}

	public final void rotate(Rotationf rotation) {
		this.startRotation.set(this.transform.localRotation);
		this.deltaRotation.set(rotation);
		rotate = true;
	}

	public final void rotateTo(Rotationf targetRotation) {
		this.startRotation.set(this.transform.localRotation);
		targetRotation.sub(this.startRotation, this.deltaRotation);
		rotate = true;
	}

	public final void scale(Vector2f scale) {
		scale(scale.x, scale.y);
	}

	public final void scale(float x, float y) {
		this.startScale.set(this.transform.localScale);
		this.deltaScaling.set(x, y);
		scale = true;
	}

	@Override
	public void onFixedUpdate(float time) {
		if (move) {
			this.transform.localPosition.set(this.startPoint);
			this.transform.localPosition.add(this.distance.x * time, this.distance.y * time);
		}
		if (rotate) {
			this.transform.localRotation.set(this.startRotation);
			this.transform.localRotation.addRAD(deltaRotation.getRAD() * time);
		}
		if (scale) {
			this.transform.localScale.set(this.startScale);
			this.transform.localScale.add(this.deltaScaling.x * time, this.deltaScaling.y * time);
		}
	}

}
