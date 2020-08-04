package examplegame;

import org.joml.Vector2f;

import common.geom.Rectangle;
import common.math.MathUtils;
import common.math.Transform2;
import graphics.Color;

import graphics.renderer2d.Renderer2D;
import physics.Ray2;
import physics.RaycastObject;
import physics.RigidBody2;

public class Asteroid implements RaycastObject {
	// create new dynamic rigidbody.
	public RigidBody2 rigidBody;

	public Rectangle rectangle;
	public Transform2 transform;

	// collision and circle radius of the asteroid
	public int radius = 32;

	private boolean active = false;

	// Asteroid color variants
	private static Color[] variants = { Color.black, Color.gray, Color.darkGray, Color.lightGray };

	// color for alive asteroid.
	private Color currentColor = new Color(Color.white);

	public Asteroid() {
		this.rigidBody = new RigidBody2(RigidBody2.BODYTYPE_DYNAMIC);
		this.rectangle = new Rectangle(radius, radius);
		this.transform = new Transform2();
		Ray2.register(this);
	}

	// Spawn new asteroid with random direction and color.
	public void spawn(float x, float y) {
		this.transform.localPosition.set(x, y);
		this.transform.localRotation.setDEG(MathUtils.random(0, 360));

		this.rigidBody.velocity.set(MathUtils.random(-5, 5), MathUtils.random(-5, 5));
		this.rigidBody.addTorque(MathUtils.random((float) -Math.toRadians(5), (float) Math.toRadians(5)));

		this.currentColor.set((Color) MathUtils.random(variants));
		this.active = true;
	}

	// kill the asteroid and increase score by 1
	public void kill() {
		Main.score++;
		this.active = false;
	}

	public void fixedUpdate(float deltaTime) {
		if (this.active) {
			// calculate the next position of the asteroid
			this.rigidBody.fixedUpdate(deltaTime);
			// add the calculated position.

			this.rigidBody.apply(this.transform);
			this.transform.updateThenApply(this.rectangle);
		}
	}

	public void render(Renderer2D renderer2D) {
		if (this.active) {
			renderer2D.fill(rectangle, this.currentColor);
		}
	}

	@Override
	public boolean contains(Vector2f vector) {
		return this.rectangle.contains(vector);
	}
}
