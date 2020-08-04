package common.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.lwjgl.system.MemoryUtil;

/**
 * AudioBuffer for playing audio files. Currently only wave files supported.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class AudioBuffer implements Resource<AudioBuffer> {
	private int bufferID;
	private int sampleRate;
	private int size;
	private int frameSize;
	private ByteBuffer data;
	private int channels;
	private int sampleSize;

	/**
	 * Create an empty AudioBuffer.
	 */
	public AudioBuffer() {
	}

	/**
	 * Create audioBuffer from {@link AudioInputStream}
	 * 
	 * @see load
	 */
	public AudioBuffer(AudioInputStream stream) {
		load(stream);
	}

	/**
	 * Load AudioStream from {@link AudioInputStream}
	 * 
	 * @param stream AudioStream source.
	 */
	public final void load(AudioInputStream stream) {
		AudioFormat audioFormat = stream.getFormat();
		this.channels = audioFormat.getChannels();
		this.sampleSize = audioFormat.getSampleSizeInBits();

		this.sampleRate = (int) audioFormat.getSampleRate();
		this.frameSize = audioFormat.getFrameSize();
		this.size = (int) stream.getFrameLength() * this.frameSize;

		this.data = MemoryUtil.memAlloc(this.size);
		try {
			for (int i = 0; i < this.size; i++) {
				this.data.put((byte) stream.read());
			}
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.data.flip();
	}

	/**
	 * 
	 * @return the openAL buffer ID.
	 */
	public final int getID() {
		return bufferID;
	}

	/**
	 * 
	 * @return the audio format of this AudioBuffer.
	 */
	public final int getSampleRate() {
		return sampleRate;
	}

	/**
	 * 
	 * @return audio data in Bytes.
	 */
	public final ByteBuffer getData() {
		return data;
	}

	/**
	 * 
	 * @return size of the buffer.
	 */
	public final long getSize() {
		return size;
	}

	/**
	 * 
	 * @return frameSize of the buffer.
	 */
	public final int getFrameSize() {
		return frameSize;
	}

	public int getChannels() {
		return this.channels;
	}

	public int getSampleSizeInBits() {
		return this.sampleSize;
	}

	@Override
	public AudioBuffer loadFile(File file) throws IOException {
		AudioInputStream audioStream = null;
		try {
			audioStream = AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		load(audioStream);
		return this;
	}

}
