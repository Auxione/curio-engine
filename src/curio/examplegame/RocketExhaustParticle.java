package examplegame;

import org.joml.Vector2f;

import graphics.Color;
import graphics.Texture;
import graphics.TextureFactory;
import graphics.renderer2d.Particle;
import graphics.renderer2d.Renderer2D;
import physics.RigidBody2;

public class RocketExhaustParticle extends Particle {
	public static Texture texture = TextureFactory.fillOval(8, 8);
	public static Color defaultColor = Color.red;

	private Color currentColor;
	private Vector2f position = new Vector2f();
	private RigidBody2 rigidBody = new RigidBody2(RigidBody2.BODYTYPE_DYNAMIC);

	public RocketExhaustParticle() {
		currentColor = new Color(defaultColor);
	}

	@Override
	public Particle onCreate() {
		return new RocketExhaustParticle();
	}

	@Override
	protected void onAwake(Vector2f startPosition, Vector2f startVelocity) {
		this.position.set(startPosition);
		this.rigidBody.reset();
		this.rigidBody.velocity.set(startVelocity);
		this.currentColor.set(defaultColor);
	}

	@Override
	protected void onRender(Renderer2D renderer2d) {
		renderer2d.fillRectCentered(position.x, position.y, 8, 8, this.currentColor, texture);
	}

	@Override
	protected void onFixedUpdate(float deltaTime) {
		this.rigidBody.fixedUpdate(deltaTime);
		this.position.add(this.rigidBody.getDeltaPosition());

		this.currentColor.y += 0.1;
	}
}
