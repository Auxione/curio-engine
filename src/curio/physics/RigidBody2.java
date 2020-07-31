package physics;

import java.util.ArrayList;
import org.joml.Vector2f;

import common.Console;
import common.math.Transform2;
import core.debug.DebugObject;
import graphics.Color;
import graphics.renderer2d.Renderer2D;
import physics.forcegenerators.ForceGenerator;

public class RigidBody2 implements DebugObject {
	public static final int BODYTYPE_DYNAMIC = 0;
	public static final int BODYTYPE_KINEMATIC = 1;

	private Vector2f deltaPosition = new Vector2f();
	public Vector2f velocity = new Vector2f();
	public Vector2f acceleration = new Vector2f();
	public Vector2f force = new Vector2f();

	private float deltaAngle = 0.0f;
	public float angularVelocity = 0.0f;
	public float angularAcceleration = 0.0f;
	public float torque = 0.0f;

	public float mass = 1.0f;
	public float inertia = 1.0f;

	private int bodyType = 0;

	private ArrayList<ForceGenerator> forceGenerators = new ArrayList<ForceGenerator>();

	public RigidBody2(int type) {
		this.bodyType = type;
	}

	public final void fixedUpdate() {
		fixedUpdate(1);
	}

	public final void fixedUpdate(float deltaTime) {
		if (this.bodyType == 0) {

			if (this.mass <= 0) {
				Console.warning(this, "Mass cannot be smaller than 0.");
				return;
			}

			if (this.inertia <= 0) {
				Console.warning(this, "Inertia cannot be smaller than 0.");
				return;
			}

			for (int i = 0; i < this.forceGenerators.size(); i++) {
				this.forceGenerators.get(i).calculate(this);
			}

			this.acceleration.x = this.force.x / this.mass;
			this.acceleration.y = this.force.y / this.mass;
			this.angularAcceleration = this.torque / this.inertia;

			this.force.zero();
			this.torque = 0;
		}

		this.velocity.x += this.acceleration.x * deltaTime;
		this.velocity.y += this.acceleration.y * deltaTime;
		this.angularVelocity += this.angularAcceleration * deltaTime;

		this.deltaPosition.x = this.velocity.x * deltaTime;
		this.deltaPosition.y = this.velocity.y * deltaTime;
		this.deltaAngle = this.angularVelocity;
	}

	public final void reset() {
		this.force.zero();
		this.acceleration.zero();
		this.velocity.zero();
		this.angularAcceleration = 0;
		this.angularVelocity = 0;
		this.torque = 0;
	}

	public final void addTorque(float torque) {
		this.torque += torque;
	}

	public final void apply(Transform2 transform) {
		transform.rotate(getDeltaAngle());
		transform.translate(getDeltaPosition());
	}

	public final void addForce(float x, float y) {
		this.force.add(x, y);
	}

	public final void addForce(Vector2f force) {
		this.force.add(force);
	}

	public final void addForce(Vector2f position, Vector2f force) {
		addTorque(position.x * force.y - position.y * force.x);
		addForce(force);
	}

	public final void addForceGenerator(ForceGenerator forceGenerator) {
		this.forceGenerators.add(forceGenerator);
	}

	public final void removeForceGenerator(ForceGenerator forceGenerator) {
		this.forceGenerators.remove(forceGenerator);
	}

	public Vector2f getDeltaPosition() {
		return deltaPosition;
	}

	public float getDeltaAngle() {
		return deltaAngle;
	}

	@Override
	public void debugDraw(Renderer2D renderer2d, Color color) {

	}

	@Override
	public String debugPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("[BType: ").append((this.bodyType == 0) ? " Dynamic " : " Kinematic ").append(" height: ");
		sb.append("][Acc:").append(acceleration).append("][Vel:").append(velocity);
		sb.append("]-[AnAcc:").append(angularAcceleration).append("][AnVel:").append(angularVelocity).append("]");
		return sb.toString();
	}
}
