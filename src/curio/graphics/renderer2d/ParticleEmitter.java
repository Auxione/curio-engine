package graphics.renderer2d;

import org.joml.Math;
import org.joml.Vector2f;

import common.math.MathUtils;
import core.GameCycle;

/**
 * Provides particle emitting class for curio-engine.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class ParticleEmitter {
	/**
	 * Enable/Disable particle emitting.
	 */
	public boolean active = true;
	/**
	 * Particle spawn cycle in milliseconds.
	 */
	public float emitDelay = 20;
	/**
	 * Particle lifetime in milliseconds.
	 */
	public float startLifetime = 40;

	/**
	 * Particle spawn velocity.
	 */
	public float startVelocity = 15;
	/**
	 * Particle spawn direction.
	 */
	public Vector2f startDirection = new Vector2f(0, 1);
	/**
	 * Particle spawn position.
	 */
	public Vector2f startPosition = new Vector2f(32, 32);

	/**
	 * Position spread range. Randomly generates number from this range and adds to
	 * particle position.
	 */
	public float positionSpread = 0;

	/**
	 * Direction spread angle in degrees. Randomly generates angle between this
	 * angle when spawning particle.
	 */
	public float directionSpreadAngle = 30;
	/**
	 * Number of particles to spawn.
	 */
	public float spawnCount = 1;

	private Particle[] particles;
	private float nextSpawnTime = 0;
	private float simulationTime = 0;

	private Vector2f particleVelocity = new Vector2f(startVelocity);
	private Vector2f particlePosition = new Vector2f(startPosition);

	public ParticleEmitter() {

	}

	/**
	 * Create new particle emitter and replace the settings from source
	 * 
	 * @param source : The source to copy settings.
	 */
	public ParticleEmitter(ParticleEmitter source) {
		this.active = source.active;

		this.emitDelay = source.emitDelay;
		this.startLifetime = source.startLifetime;
		this.startVelocity = source.startVelocity;
		this.startDirection = source.startDirection;
		this.startPosition = source.startPosition;

		this.positionSpread = source.positionSpread;
		this.directionSpreadAngle = source.directionSpreadAngle;

		this.spawnCount = source.spawnCount;
	}

	/**
	 * Run emitter with given particle. Initializes particle pool with given
	 * formula: {@link ParticleEmitter#spawnCount} *
	 * ({@link ParticleEmitter#startLifetime} / {@link ParticleEmitter#emitDelay} +
	 * 0.2f)). Also calls {@link Particle#onCreate()}.
	 * 
	 * @param particle : The particle to emit.
	 */
	public void run(Particle particle) {
		if (emitDelay < 1) {
			emitDelay = 1;
		}
		this.particles = new Particle[(int) (Math.ceil(spawnCount * (startLifetime / emitDelay + 0.2f)))];
		for (int i = 0; i < this.particles.length; i++) {
			this.particles[i] = particle.onCreate();
		}
	}

	/**
	 * Do fixed updates in each particle and emit if necessary.
	 * 
	 * @see GameCycle#fixedUpdate(float)
	 * @param deltaTime
	 */
	public void fixedUpdate(float deltaTime) {
		this.simulationTime += deltaTime;
		if (this.active && this.simulationTime > this.nextSpawnTime) {
			emit();
			this.nextSpawnTime = this.simulationTime + emitDelay;
		}

		for (int i = 0; i < this.particles.length; i++) {
			this.particles[i].fixedUpdate(deltaTime);
		}
	}

	/**
	 * Render each particle on screen.
	 * 
	 */
	public void render(Renderer2D renderer2D) {
		for (int i = 0; i < this.particles.length; i++) {
			this.particles[i].render(renderer2D);
		}
	}

	/**
	 * Emit particles.
	 */
	public void emit() {
		int counter = 0;
		for (int i = 0; i < this.particles.length; i++) {
			if (counter < spawnCount) {
				if (!this.particles[i].active) {
					randomizeSpawn();
					this.particles[i].wake(this.startLifetime, this.particlePosition, this.particleVelocity);
					counter++;
				}
			}
		}
	}

	/**
	 * Reset particle emitter.
	 */
	public void reset() {
		this.nextSpawnTime = 0;
		this.simulationTime = 0;
		for (int i = 0; i < this.particles.length; i++) {
			this.particles[i].active = false;
		}
		this.active = false;
	}

	private void randomizeSpawn() {
		this.particlePosition.set(this.startPosition);
		this.particleVelocity.set(this.startDirection).normalize(this.startVelocity);

		this.particlePosition.add(//
				MathUtils.random(-this.positionSpread, this.positionSpread),
				MathUtils.random(-this.positionSpread, this.positionSpread));

		float spreadAngle = //
				MathUtils.random(-this.directionSpreadAngle, this.directionSpreadAngle);

		float sin = Math.sin(Math.toRadians(spreadAngle));
		float cos = Math.cosFromSin(sin, Math.toRadians(spreadAngle));

		float velx = this.startDirection.x * cos - this.startDirection.y * sin;
		float vely = this.startDirection.x * sin + this.startDirection.y * cos;

		this.particleVelocity.set(velx, vely)
				.mul(this.startVelocity + MathUtils.random(-this.startVelocity / 3, this.startVelocity / 3));
	}

}
