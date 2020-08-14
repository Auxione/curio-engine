package graphics;

public final class CameraSettings {
	public int width;
	public int height;
	public FrameBuffer renderTarget = null;
	public Color backgroundColor = Color.black;

	public CameraSettings(int width, int height, FrameBuffer renderTarget) {
		this.width = width;
		this.height = height;
		this.renderTarget = renderTarget;
	}

	public CameraSettings(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
