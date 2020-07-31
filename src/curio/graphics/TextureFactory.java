package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import common.utilities.ImageBuffer;

public class TextureFactory {
	public static Texture drawRectangle(int width, int height) {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();
		graphics.setPaint(new Color(255, 255, 255, 255));
		graphics.drawRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
		return Texture.createInstance(new ImageBuffer(bufferedImage));
	}

	public static Texture fillRectangle(int width, int height) {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();
		graphics.setPaint(new Color(255, 255, 255, 255));
		graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
		return Texture.createInstance(new ImageBuffer(bufferedImage));
	}

	public static Texture drawOval(int width, int height) {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();
		graphics.setColor(new Color(255, 255, 255, 255));
		graphics.drawOval(0, 0, bufferedImage.getWidth() - 1, bufferedImage.getHeight() - 1);
		return Texture.createInstance(new ImageBuffer(bufferedImage));
	}

	public static Texture fillOval(int width, int height) {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = bufferedImage.createGraphics();
		graphics.setColor(new Color(255, 255, 255, 255));
		graphics.fillOval(0, 0, bufferedImage.getWidth() - 1, bufferedImage.getHeight() - 1);
		return Texture.createInstance(new ImageBuffer(bufferedImage));
	}

	public static BufferedImage createCharImage(Font awtfont, char fontChar) {
		// Create a temporary image to extract the character's size
		BufferedImage charImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D awtGraphics2D = (Graphics2D) charImage.getGraphics();

		awtGraphics2D.setFont(awtfont);
		awtGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		FontMetrics awtfontMetrics = awtGraphics2D.getFontMetrics();

		int charwidth = awtfontMetrics.charWidth(fontChar);
		if (charwidth <= 0) {
			charwidth = 1;
		}
		int charheight = awtfontMetrics.getHeight();
		if (charheight <= 0) {
			charheight = awtfont.getSize();
		}

		// Create another image holding the character we are creating
		charImage = new BufferedImage(charwidth, charheight, BufferedImage.TYPE_INT_ARGB);
		awtGraphics2D = (Graphics2D) charImage.getGraphics();
		awtGraphics2D.setFont(awtfont);
		awtGraphics2D.setColor(java.awt.Color.WHITE);
		awtGraphics2D.drawString(String.valueOf(fontChar), 0, awtfontMetrics.getAscent());

		return charImage;
	}
}
