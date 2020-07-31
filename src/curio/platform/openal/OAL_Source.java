package platform.openal;

import static org.lwjgl.openal.AL11.*;

import common.math.MathUtils;
import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import core.audio.AudioClip;
import core.audio.AudioSource;

//TODO: revise
//TODO: positional audio
public class OAL_Source implements NativeObject, AudioSource {
	private int sourceID;

	public OAL_Source() {
		this.sourceID = alGenSources();
		setPitch(1);
		setGain(1);
		setPosition(0, 0, 0);
		NativeObjectManager.register(this);
	}

	@Override
	public void setLooping(boolean value) {
		if (value == true) {
			alSourcei(this.sourceID, AL_LOOPING, AL_TRUE);
		} else {
			alSourcei(this.sourceID, AL_LOOPING, AL_FALSE);
		}
	}

	@Override
	public final void setPitch(float value) {
		alSourcef(this.sourceID, AL_PITCH, value);
	}

	@Override
	public final void setGain(float value) {
		alSourcef(this.sourceID, AL_GAIN, MathUtils.clamp(value, 0.0f, 1.0f));
	}

	@Override
	public final void setMaxDistance(float value) {
		alSourcef(this.sourceID, AL_MAX_DISTANCE, MathUtils.clamp(value, 0.0f, 1.0f));
	}

	@Override
	public final void setAudio(AudioClip audio) {
		alSourcei(this.sourceID, AL_BUFFER, audio.getBufferID());
	}

	@Override
	public final void setPosition(float x, float y, float z) {
		alSource3f(this.sourceID, AL_POSITION, x, y, z);
	}

	@Override
	public final void setVelocity(float x, float y, float z) {
		alSource3f(this.sourceID, AL_VELOCITY, x, y, z);
	}

	@Override
	public final void setDirection(float x, float y, float z) {
		alSource3f(this.sourceID, AL_DIRECTION, x, y, z);
	}

	@Override
	public final boolean isPlaying() {
		if (alGetSourcei(this.sourceID, AL_SOURCE_STATE) == AL_PLAYING) {
			return true;
		}
		return false;
	}

	@Override
	public final boolean isLooping() {
		if (alGetSourcei(this.sourceID, AL_LOOPING) == AL_TRUE) {
			return true;
		}
		return false;
	}

	@Override
	public final void play() {
		alSourcePlay(this.sourceID);
	}

	@Override
	public final void stop() {
		alSourceStop(this.sourceID);
	}

	@Override
	public final void pause() {
		alSourcePause(this.sourceID);
	}

	@Override
	public final void rewind() {
		alSourceRewind(this.sourceID);
	}

	@Override
	public final void terminate() {
		alDeleteSources(this.sourceID);
	}

}
