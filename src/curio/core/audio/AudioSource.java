package core.audio;

public interface AudioSource {
	/**
	 * Loop the audio source
	 */
	public void setLooping(boolean value);

	/**
	 * Set pitch of the audio source
	 * 
	 * @param value the value to set pitch.
	 */
	public void setPitch(float value);

	/**
	 * set gain of the audio source.
	 * 
	 */
	public void setGain(float value);

	/**
	 * Set max audible distance of the audio source.
	 */
	public void setMaxDistance(float value);

	/**
	 * Set audio clip of the source
	 * 
	 * @param audioClip the audio clip to set.
	 */
	public void setAudio(AudioClip audioClip);

	/**
	 * Set audio source position.
	 * 
	 * @param x x component of the position vector.
	 * @param y y component of the position vector.
	 * @param z z component of the position vector.
	 */
	public void setPosition(float x, float y, float z);

	/**
	 * Set audio source velocity.
	 * 
	 * @param x x component of the velocity vector.
	 * @param y y component of the velocity vector.
	 * @param z z component of the velocity vector.
	 */
	public void setVelocity(float x, float y, float z);

	/**
	 * Set audio source play direction.
	 * 
	 * @param x x component of the direction vector.
	 * @param y y component of the direction vector.
	 * @param z z component of the direction vector.
	 */
	public void setDirection(float x, float y, float z);

	/**
	 * @return true if audio is currently playing.
	 */
	public boolean isPlaying();

	/**
	 * @return true if audio is looping.
	 */
	public boolean isLooping();

	/**
	 * play the audio
	 */
	public void play();

	/**
	 * pause the audio and rewind to start.
	 */
	public void stop();

	/**
	 * pause the audio.
	 */
	public void pause();

	/**
	 * rewind the audio to start.
	 */
	public void rewind();
}
