package gui.entities;

import graphics.Color;
import graphics.Texture;
import graphics.TextureCoordinate;
import graphics.renderer2d.Renderer2D;
import gui.GUIEntity;
import gui.utilities.Fill;

public class TextureDisplay extends GUIEntity {
	public final Color tint = new Color(Color.white);
	public final TextureCoordinate textureCoordinate;
	public int offset = 0;
	private Texture texture;

	public TextureDisplay(int width, int height, Fill background, Texture texture,
			TextureCoordinate textureCoordinate) {
		super(width, height, background);
		this.texture = texture;
		this.textureCoordinate = textureCoordinate;
	}

	@Override
	protected void onGUIUpdate(Renderer2D renderer2d) {
		renderer2d.fillRect(this.rectTransform().position.x + offset, this.rectTransform().position.y + offset,
				this.rectTransform().width - offset * 2, this.rectTransform().height - offset * 2, this.tint,
				this.texture, this.textureCoordinate);
	}

}
