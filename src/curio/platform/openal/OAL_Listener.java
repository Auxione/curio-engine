package platform.openal;

import java.nio.IntBuffer;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.MemoryUtil;

import common.Console;
import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import core.audio.AudioListener;

import static org.lwjgl.openal.AL11.*;
import static org.lwjgl.openal.ALC.*;
import static org.lwjgl.openal.ALC11.*;

public class OAL_Listener implements NativeObject, AudioListener {
	private static OAL_Listener instance;
	private long deviceID;
	private long contextID;

	public static OAL_Listener getInstance() {
		if (instance == null) {
			instance = new OAL_Listener();
		}
		return instance;
	}

	@Override
	public String getDevice() {
		return alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
	}

	private OAL_Listener() {
		this.deviceID = alcOpenDevice(getDevice());

		if (this.deviceID == MemoryUtil.NULL) {
			return;
		}
		this.contextID = alcCreateContext(this.deviceID, (IntBuffer) null);
		alcMakeContextCurrent(this.contextID);

		ALCCapabilities alcCapabilities = createCapabilities(this.deviceID);
		AL.createCapabilities(alcCapabilities);

		checkErrors();

		Console.fine(this, "Initialized.");
		NativeObjectManager.register(this);
	}

	public final void checkErrors() {
		int error = alGetError();
		if (error != AL_NO_ERROR) {
			Console.warning(this, "Error: " + error);
		}
	}

	@Override
	public final void setPosition(float x, float y, float z) {
		alListener3f(AL_POSITION, x, y, z);
		checkErrors();
	}

	@Override
	public final void setVelocity(float x, float y, float z) {
		alListener3f(AL_POSITION, x, y, z);
		checkErrors();
	}

	@Override
	public final void setGain(float gain) {
		alListenerf(AL_GAIN, gain);
		checkErrors();
	}

	@Override
	public void terminate() {
		alcMakeContextCurrent(MemoryUtil.NULL);
		alcDestroyContext(this.contextID);
		alcCloseDevice(this.deviceID);
	}
}
