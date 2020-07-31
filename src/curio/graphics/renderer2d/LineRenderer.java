package graphics.renderer2d;

import org.joml.Vector2f;

import graphics.Color;

public class LineRenderer {
	public float width;

	public LineRenderer(float width) {
		this.width = width;
	}

	private static Vector2f[] calculate(float width, float x1, float y1, float x2, float y2) {
		float dx = x2 - x1;
		float dy = y2 - y1;

		Vector2f temp = new Vector2f(dx, dy).normalize(width / 2);

		return new Vector2f[] { //
				new Vector2f(x1 + temp.y, y1 - temp.x), //
				new Vector2f(x2 + temp.y, y2 - temp.x), //
				new Vector2f(x2 - temp.y, y2 + temp.x), //
				new Vector2f(x1 - temp.y, y1 + temp.x), //
		};
	}

	public void render(Renderer2D renderer2d, float x1, float y1, float x2, float y2, Color color) {
		Vector2f[] temp = calculate(this.width, x1, y1, x2, y2);
		renderer2d.fillQuad(temp[0], temp[1], temp[2], temp[3], color);
	}

	public void render(Renderer2D renderer2d, Vector2f point1, Vector2f point2, Color color) {
		Vector2f[] temp = calculate(this.width, point1.x, point1.y, point2.x, point2.y);
		renderer2d.fillQuad(temp[0], temp[1], temp[2], temp[3], color);
	}

	public static void render(Renderer2D renderer2d, float width, float x1, float y1, float x2, float y2, Color color) {
		Vector2f[] temp = calculate(width, x1, y1, x2, y2);
		renderer2d.fillQuad(temp[0], temp[1], temp[2], temp[3], color);
	}

	public static void render(Renderer2D renderer2d, float width, Vector2f point1, Vector2f point2, Color color) {
		Vector2f[] temp = calculate(width, point1.x, point1.y, point2.x, point2.y);
		renderer2d.fillQuad(temp[0], temp[1], temp[2], temp[3], color);
	}

}
