package graphics.renderer3d;

public interface Mesh {

	public void uploadData();

	public void uploadSubData(int VBindex, int IBindex);

	public void bind();

	public int getID();

	public void drawIndexed(int count);
}
