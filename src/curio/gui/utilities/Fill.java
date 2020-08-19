package gui.utilities;

import graphics.Color;
import graphics.Texture;
import graphics.TextureCoordinate;
import graphics.renderer2d.Renderer2D;

public final class Fill {
	public final Color color = new Color(Color.white);
	public Texture texture;
	public TextureCoordinate textureCoordinate = new TextureCoordinate();;

	public Fill(Texture texture) {
		this.texture = texture;
	}

	public Fill(Color color) {
		this(Renderer2D.defaultWhiteTexture);
		this.color.set(color);
	}

	public Fill(Texture texture, Color tint) {
		this(texture);
		this.color.set(tint);
	}

	public Fill(Texture texture, TextureCoordinate textureCoordinate, Color tint) {
		this(texture);
		this.color.set(color);
		this.textureCoordinate.set(textureCoordinate);
	}
}
