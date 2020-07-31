package core.audio;

/**
 * Audio Listener interface for curio-engine
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public interface AudioListener {
	/**
	 * 
	 * @return the default audio device name.
	 */
	public String getDevice();

	/**
	 * Set Listener position.
	 * 
	 * @param x x component of the position vector.
	 * @param y y component of the position vector.
	 * @param z z component of the position vector.
	 */
	public void setPosition(float x, float y, float z);

	/**
	 * Set Listener velocity.
	 * 
	 * @param x x component of the velocity vector.
	 * @param y y component of the velocity vector.
	 * @param z z component of the velocity vector.
	 */
	public void setVelocity(float x, float y, float z);

	/**
	 * Set gain of the listener
	 * 
	 * @param gain ratio in 0.0f and 1.0f.
	 */
	public void setGain(float gain);
}
