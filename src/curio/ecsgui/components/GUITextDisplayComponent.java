package ecsgui.components;

import ecsgui.GUIEntity;
import graphics.Color;
import graphics.renderer2d.FontData;
import graphics.renderer2d.Renderer2D;
import graphics.renderer2d.StringRenderer;

public class GUITextDisplayComponent extends GUIComponent {
	private String text;
	public Color textColor = Color.black;
	private StringRenderer stringRenderer;

	private int alligmentType = MIDDLE;
	private float[] alligments = new float[2];
	public boolean render = true;

	public final static int TOPRIGHT = 9;
	public final static int TOPMID = 8;
	public final static int TOPLEFT = 7;

	public final static int MIDDLERIGHT = 6;
	public final static int MIDDLE = 5;
	public final static int MIDDLELEFT = 4;

	public final static int BOTTOMRIGHT = 3;
	public final static int BOTTOMMID = 2;
	public final static int BOTTOMLEFT = 1;

	public GUITextDisplayComponent(FontData fontData) {
		this.stringRenderer = new StringRenderer(fontData);
	}

	@Override
	public void applySettings() {
		this.stringRenderer.maxTextWidth = (int) Math.ceil(guiEntity.size.x);
		this.stringRenderer.maxTextHeight = (int) Math.ceil(guiEntity.size.y);
		calculateAllignments(super.getEntity(), text, alligmentType);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getAlligmentType() {
		return alligmentType;
	}

	public void allign(int alligmentType) {
		this.alligmentType = alligmentType;
	}

	private void calculateAllignments(GUIEntity guiEntity, String text, int alligmentType) {
		switch (alligmentType) {
		case TOPLEFT:
			alligments[0] = 0;
			alligments[1] = 0;
			break;
		case TOPMID:
			alligments[0] = guiEntity.size.x / 2 - this.stringRenderer.fontData.getLineWidth(text) / 2;
			alligments[1] = 0;
			break;
		case TOPRIGHT:
			alligments[0] = guiEntity.size.x - this.stringRenderer.fontData.getLineWidth(text);
			alligments[1] = 0;
			break;

		case MIDDLELEFT:
			alligments[0] = 0;
			alligments[1] = guiEntity.size.y / 2 - this.stringRenderer.fontData.getLineHeight(text) / 2;
			break;

		case MIDDLERIGHT:
			alligments[0] = guiEntity.size.x - this.stringRenderer.fontData.getLineWidth(text);
			alligments[1] = guiEntity.size.y / 2 - this.stringRenderer.fontData.getLineHeight(text) / 2;
			break;

		case BOTTOMLEFT:
			alligments[0] = 0;
			alligments[1] = guiEntity.size.y - this.stringRenderer.fontData.getLineHeight(text);
			break;
		case BOTTOMMID:
			alligments[0] = guiEntity.size.x / 2 - this.stringRenderer.fontData.getLineWidth(text) / 2;
			alligments[1] = guiEntity.size.y - this.stringRenderer.fontData.getLineHeight(text);
			break;
		case BOTTOMRIGHT:
			alligments[0] = guiEntity.size.x - this.stringRenderer.fontData.getLineWidth(text);
			alligments[1] = guiEntity.size.y - this.stringRenderer.fontData.getLineHeight(text);
			break;

		default:
			alligments[0] = guiEntity.size.x / 2 - this.stringRenderer.fontData.getLineWidth(text) / 2;
			alligments[1] = guiEntity.size.y / 2 - this.stringRenderer.fontData.getLineHeight(text) / 2;
			break;
		}
	}

	@Override
	public void onUpdate() {
	}

	@Override
	public void onRender2D(Renderer2D renderer2D) {
		if (this.render == true) {
			this.stringRenderer.render(renderer2D, this.text, this.textColor, guiEntity.position.x + alligments[0],
					guiEntity.position.y + alligments[1]);
		}
	}

	@Override
	public boolean onKeyPressed(int key, char c) {
		return false;
	}

	@Override
	public boolean onKeyReleased(int key, char c) {
		return false;
	}

	@Override
	public boolean onKeyRepeat(int key, char c) {
		return false;
	}

	@Override
	public boolean onMouseWheelChanged(int x, int y) {
		return false;
	}

	@Override
	public boolean onMousePressed(int button, int x, int y) {
		return false;
	}

	@Override
	public boolean onMouseReleased(int button, int x, int y) {
		return false;
	}

	@Override
	public boolean onMouseRepeat(int button, int x, int y) {
		return false;
	}
}
