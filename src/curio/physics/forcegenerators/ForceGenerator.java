package physics.forcegenerators;

import physics.RigidBody2;

public interface ForceGenerator {
	public static float multiplier = 1.0f;

	public void calculate(RigidBody2 rigidBody2D);

}
