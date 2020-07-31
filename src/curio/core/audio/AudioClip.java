package core.audio;

import common.utilities.AudioBuffer;

public interface AudioClip {

	public void MemUpload();

	public int getFormat();

	public int getBufferID();

	public AudioBuffer getAudioBuffer();
}
