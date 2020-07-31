package physics.forcegenerators;

import org.joml.Vector2f;

import physics.RigidBody2;

public class DirectionalForce implements ForceGenerator {
	public Vector2f direction = new Vector2f(0, 1);
	public float force = 100.0f;

	public DirectionalForce() {
		direction.normalize(force);
	}

	@Override
	public void calculate(RigidBody2 rigidBody2D) {
		rigidBody2D.addForce(direction);
	}
}
