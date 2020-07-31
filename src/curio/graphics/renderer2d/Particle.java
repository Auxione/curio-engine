package graphics.renderer2d;

import org.joml.Vector2f;

/**
 * Particle class to use in {@link ParticleEmitter}
 * 
 * @author Mehmet Cem Zarifoglu
 */
public abstract class Particle {
	/**
	 * active flag for particle.
	 */
	public boolean active = false;
	/**
	 * lifetime of the particle.
	 */
	public float life;
	private float startLifetime;

	/**
	 * Wake the particle.
	 * 
	 * @param startLifetime : Lifetime of the particle.
	 * @param startPosition : Start position of the particle.
	 * @param startVelocity : Start velocity of the particle.
	 */
	public final void wake(float startLifetime, Vector2f startPosition, Vector2f startVelocity) {
		this.startLifetime = startLifetime;
		this.life = this.startLifetime;
		this.onAwake(startPosition, startVelocity);
		this.active = true;
	}

	/**
	 * Render the particle.
	 * 
	 */
	public final void render(Renderer2D renderer2D) {
		if (this.active) {
			this.onRender(renderer2D);
		}
	}

	/**
	 * Do fixedUpdate, calculate remaining life.
	 * 
	 * @param deltaTime
	 */
	public final void fixedUpdate(float deltaTime) {
		if (this.active) {
			this.onFixedUpdate(deltaTime);

			this.life -= deltaTime;
			if (this.life <= 0) {
				onSleep();
				this.active = false;
			}
		}
	}

	/**
	 * @return new Instance of the particle for object pool in
	 *         {@link ParticleEmitter}.
	 */
	public abstract Particle onCreate();

	/**
	 * Wakes the particle by setting active flag to true.
	 * 
	 * @param startPosition : Start position of the particle.
	 * @param startVelocity : Start velocity of the particle.
	 */
	protected abstract void onAwake(Vector2f startPosition, Vector2f startVelocity);

	/**
	 * Particle render abstraction.
	 * 
	 */
	protected abstract void onRender(Renderer2D renderer2D);

	/**
	 * Particle FixedUpdate abstraction.
	 * 
	 */
	protected abstract void onFixedUpdate(float deltaTime);

	/**
	 * Called when particle lifetime hits zero.
	 * 
	 */
	protected void onSleep() {

	}

	/**
	 * @return the ratio of the maximum lifetime and remaining life of the particle.
	 */
	public float getLifeRatio() {
		return life / startLifetime;
	}

}
