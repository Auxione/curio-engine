package examplegame;

import common.geom.Rectangle;
import common.math.MathUtils;
import common.math.Rotationf;
import common.math.Transform2;
import core.Input;
import core.events.Event;
import core.scenesys.SceneManager;
import graphics.Color;
import graphics.renderer2d.ParticleEmitter;
import graphics.renderer2d.Renderer2D;
import physics.Ray2;
import physics.RaycastObject;
import physics.RigidBody2;

public class PlayerRocket {
	public RigidBody2 rigidBody;
	public Rectangle rectangle = new Rectangle(64, 16);
	public Transform2 transform, exhaustPortTransform;

	public ParticleEmitter particleEmitterBackThrust;

	public int moveForce = 10;

	private boolean up, down, right, left, shoot;
	private float turnRate = 3;

	public Ray2 laser = new Ray2();

	public PlayerRocket() {
		this.transform = new Transform2();
		this.exhaustPortTransform = new Transform2();

		this.exhaustPortTransform.setParent(transform);
		this.exhaustPortTransform.localPosition.set(-rectangle.width / 2, 0);

		// create new dynamic rigidbody and set its mass to 100
		this.rigidBody = new RigidBody2(RigidBody2.BODYTYPE_DYNAMIC);
		this.rigidBody.mass = 100;

		// create particle emitter for exhaust
		this.particleEmitterBackThrust = new ParticleEmitter();
		this.particleEmitterBackThrust.spawnCount = 100;
		this.particleEmitterBackThrust.emitDelay = 1;
		this.particleEmitterBackThrust.startLifetime = 10;

		this.particleEmitterBackThrust.startVelocity = 5;

		this.particleEmitterBackThrust.positionSpread = 4;
		this.particleEmitterBackThrust.directionSpreadAngle = 10;
		this.particleEmitterBackThrust.active = false;

		this.particleEmitterBackThrust.run(new RocketExhaustParticle());

		// create new event in PlayScene
		new Event(SceneManager.getInstance().get(1)) {
			@Override
			public boolean keyPressed(int key, char c) {
				if (key == Input.KEY_W) {
					up = true;
				}
				if (key == Input.KEY_S) {
					down = true;
				}
				if (key == Input.KEY_A) {
					left = true;
				}
				if (key == Input.KEY_D) {
					right = true;
				}
				if (key == Input.KEY_SPACE) {
					shoot = true;
				}
				return false;
			}

			@Override
			public boolean keyReleased(int key, char c) {
				if (key == Input.KEY_W) {
					up = false;
				}
				if (key == Input.KEY_S) {
					down = false;
				}
				if (key == Input.KEY_A) {
					left = false;
				}
				if (key == Input.KEY_D) {
					right = false;
				}
				if (key == Input.KEY_SPACE) {
					shoot = false;
				}
				return false;
			}
		};

	}

	// Spawn new asteroid with random direction and color.
	public void spawn(float x, float y) {
		this.transform.localPosition.set(x, y);
		this.transform.localRotation = Rotationf.fromDegrees(MathUtils.random(0, 360));

		this.rigidBody.reset();

	}

	public void fixedUpdate(float deltaTime) {
		this.particleEmitterBackThrust.active = up || down;

		if (up) {
			this.transform.direction.mul(10, this.rigidBody.force);
		}

		if (down) {
			this.transform.direction.mul(-10, this.rigidBody.force);
		}

		if (right) {
			this.transform.rotate(Rotationf.fromDegrees(turnRate));
		}

		if (left) {
			this.transform.rotate(Rotationf.fromDegrees(-turnRate));
		}

		if (shoot) {
			this.laser.position.set(this.transform.worldPosition);
			this.laser.direction.set(this.transform.direction);
			RaycastObject obj = this.laser.cast(250);

			if (obj instanceof Asteroid) {
				Asteroid a = (Asteroid) obj;
				a.kill();
				PlayScene.spawnRandom(a);
			}
		}

		this.particleEmitterBackThrust.startPosition.set(this.exhaustPortTransform.worldPosition);
		this.particleEmitterBackThrust.startDirection.set(this.rigidBody.force).normalize(-1);

		this.particleEmitterBackThrust.fixedUpdate(deltaTime);
		this.rigidBody.fixedUpdate(deltaTime);

		this.transform.translate(this.rigidBody.getDeltaPosition());
		this.transform.update(this.rectangle);
		this.exhaustPortTransform.update();

		this.particleEmitterBackThrust.active = false;
	}

	public void render(Renderer2D renderer2D) {
		this.particleEmitterBackThrust.render(renderer2D);
		if (shoot) {
			this.laser.draw(renderer2D, Color.red);
		}
		renderer2D.fill(rectangle, Color.green);

	}
}