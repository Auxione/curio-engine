package gui.utilities;

import core.events.Event;
import graphics.Color;
import graphics.renderer2d.Renderer2D;
import gui.GUIEntity;
import gui.GUIEnviroment;
import gui.entities.Button;
import gui.entities.Container;
import gui.entities.Label;
import gui.layouts.EdgeLayout;

public final class Titlebar extends ContainerAddon implements LayoutBase {
	public static final Color titlebarColor = new Color(Color.lightGray);
	public static final Color labelColor = new Color(Color.white);
	public static final Color buttonColor = new Color(Color.darkGray);
	public static final int entityOffset = 2;
	public static final int height = 20;

	public final RectTransform rectTransform;

	private Button minimizeButton;
	private Label nameLabel;

	private boolean moveState = false;
	private int dx, dy;

	public Titlebar(GUIEnviroment enviroment, Container container, String name) {
		super(enviroment, container);

		
		this.rectTransform = new RectTransform(container.rectTransform().width, height,container.rectTransform());
		this.rectTransform.localPosition.set(0, -height);
		this.rectTransform.update();

		initMinimizeButton();
		initNameLabel(name);
		updateTransform();
	}

	private void initMinimizeButton() {
		this.minimizeButton = new Button(16, 16, new Fill(buttonColor), new GUIEvent() {

			@Override
			public void deactivated(GUIEntity entity) {
			}

			@Override
			public void activated(GUIEntity entity) {
				container.reverseActiveState();
			}
		});
		this.minimizeButton.rectTransform().setParent(rectTransform);
		EdgeLayout.allign(this.getWidth(), this.getHeight(), this.minimizeButton.rectTransform(), 6, entityOffset);
	}

	private void initNameLabel(String name) {
		this.nameLabel = new Label(this.getWidth() - this.minimizeButton.rectTransform().width - entityOffset, height,
				new Fill(labelColor), enviroment.fontData, name, labelColor);
		EdgeLayout.allign(this.getWidth(), this.getHeight(), this.nameLabel.rectTransform(), 4, entityOffset);
		this.nameLabel.rectTransform().setParent(rectTransform);
	}

	public void updateTransform() {
		this.rectTransform.update();
		this.nameLabel.updateTransform();
		this.minimizeButton.updateTransform();
		this.container.updateTransform();
	}

	public final void render(Renderer2D renderer) {
		if (moveState) {
			this.container.rectTransform().localPosition.set(Event.getInput().getMouseX() - dx,
					Event.getInput().getMouseY() - dy);
			updateTransform();
		}

		renderer.fillRect(this.rectTransform.position.x, this.rectTransform.position.y, this.rectTransform.width,
				this.rectTransform.height, titlebarColor);
		this.minimizeButton.render(renderer);
		this.nameLabel.onGUIUpdate(renderer);
	}

	public boolean mousePressed(int button, int x, int y) {
		if (this.minimizeButton.rectTransform().contains(x, y)) {
			this.minimizeButton.mousePressed(button, x, y);
			return true;
		} else if (this.rectTransform.contains(x, y)) {
			this.dx = x - this.container.rectTransform().localPosition.x;
			this.dy = y - this.container.rectTransform().localPosition.y;
			this.moveState = true;
			return true;
		}
		return false;
	}

	public boolean mouseReleased(int button, int x, int y) {
		if (this.minimizeButton.rectTransform().contains(x, y)) {
			this.minimizeButton.mouseReleased(button, x, y);
			return true;
		}
		this.moveState = false;
		return false;
	}

	public Container getContainer() {
		return container;
	}

	@Override
	public int getHeight() {
		return this.rectTransform.height;
	}

	@Override
	public int getWidth() {
		return this.rectTransform.width;
	}

	@Override
	public int getType() {
		return 0;
	}
}
