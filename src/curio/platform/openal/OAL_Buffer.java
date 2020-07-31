package platform.openal;

import static org.lwjgl.openal.AL11.*;

import common.utilities.AudioBuffer;
import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import core.audio.AudioClip;

public class OAL_Buffer implements NativeObject, AudioClip {
	private int bufferID;
	private AudioBuffer buffer;

	private int format;

	/**
	 * Create an empty AudioBuffer.
	 */
	public OAL_Buffer() {
		this.bufferID = alGenBuffers();
		NativeObjectManager.register(this);
	}

	/**
	 * Create audioBuffer from {@link AudioInputStream}
	 * 
	 * @see load
	 */
	public OAL_Buffer(AudioBuffer buffer) {
		this.bufferID = alGenBuffers();
		this.buffer = buffer;
		NativeObjectManager.register(this);
		MemUpload();
	}

	/**
	 * Upload this buffer to openAL.
	 */
	@Override
	public final void MemUpload() {
		this.format = getFormat(buffer.getChannels(), buffer.getSampleSizeInBits());
		alBufferData(getBufferID(), this.format, buffer.getData(), buffer.getSampleRate());
	}

	/**
	 * Returns the audio format.
	 * 
	 * @param channels      audioChannels. 1 for Mono or 2 for Stereo etc.
	 * @param bitsPerSample the size of a sample.
	 * @return the audio format.
	 */
	public static final int getFormat(int channels, int bitsPerSample) {
		if (channels == 1) {
			return bitsPerSample == 8 ? AL_FORMAT_MONO8 : AL_FORMAT_MONO16;
		} else {
			return bitsPerSample == 8 ? AL_FORMAT_STEREO8 : AL_FORMAT_STEREO16;
		}
	}

	/**
	 * Terminate this openAL buffer.
	 */
	@Override
	public final void terminate() {
		alDeleteBuffers(this.bufferID);
	}

	/**
	 * 
	 * @return the audio format of this AudioBuffer.
	 */
	@Override
	public final int getFormat() {
		return format;
	}

	/**
	 * 
	 * @return the openAL buffer ID.
	 */
	@Override
	public int getBufferID() {
		return bufferID;
	}

	@Override
	public AudioBuffer getAudioBuffer() {
		return buffer;
	}
}
