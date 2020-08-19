package gui;

import java.util.ArrayList;
import java.util.HashMap;

import core.events.Event;
import core.events.EventContainer;
import graphics.renderer2d.FontData;
import graphics.renderer2d.Renderer2D;
import gui.entities.Container;
import gui.utilities.ContainerAddon;
import gui.utilities.Frame;
import gui.utilities.Titlebar;

public final class GUIEnviroment extends Event {
	private static final HashMap<EventContainer, GUIEnviroment> GUIEnviromentMap = new HashMap<EventContainer, GUIEnviroment>();

	public static GUIEnviroment createInstance(EventContainer container, FontData fontData) {
		if (!GUIEnviromentMap.containsKey(container)) {
			GUIEnviromentMap.put(container, new GUIEnviroment(container, fontData));
		}
		return GUIEnviromentMap.get(container);
	}

	public static GUIEnviroment getInstance(EventContainer container) {
		return GUIEnviromentMap.get(container);
	}

	private final ArrayList<Container> containerList;
	private GUIEntity focusedGUIEntity;
	public final FontData fontData;

	protected GUIEnviroment(EventContainer container, FontData fontData) {
		super(container);
		this.containerList = new ArrayList<Container>();
		this.fontData = fontData;
	}

	public final void register(Container container) {
		if (!this.containerList.contains(container)) {
			this.containerList.add(container);
			container.enviroment = this;
		}
	}

	public final Container getContainer(int index) {
		return this.containerList.get(index);
	}

	public final void addTitlebar(Container container, String label) {
		if (this.containerList.contains(container)) {
			Titlebar t = new Titlebar(this, container, label);
			container.register(t);

			if (container.rectTransform().hasParent())
				container.rectTransform().removeParent();
		}
	}

	public final void addFrame(Container container) {
		if (this.containerList.contains(container)) {
			Frame f = new Frame(this, container);
			container.register(f);
		}
	}

	public final void render(Renderer2D renderer) {
		for (Container container : this.containerList) {
			for (ContainerAddon addon : container.getAddons()) {
				addon.render(renderer);
			}

			container.render(renderer);
		}
	}

	@Override
	protected boolean keyPressed(int key, char c) {
		if (this.focusedGUIEntity != null) {
			this.focusedGUIEntity.keyPressed(key, c);
			return true;
		}
		return false;
	}

	@Override
	protected boolean keyReleased(int key, char c) {
		if (this.focusedGUIEntity != null) {
			this.focusedGUIEntity.keyReleased(key, c);
			return true;
		}
		return false;
	}

	@Override
	protected boolean keyRepeat(int key, char c) {
		if (this.focusedGUIEntity != null) {
			this.focusedGUIEntity.keyRepeat(key, c);
			return true;
		}
		return false;
	}

	@Override
	protected boolean mousePressed(int button, int x, int y) {
		for (Container container : this.containerList) {
			for (ContainerAddon addon : container.getAddons()) {
				if (addon.mousePressed(button, x, y)) {
					return true;
				}
			}
		}

		containerLoop: for (Container container : this.containerList) {
			for (GUIEntity guiEntity : container.getChilds()) {
				if (guiEntity.contains(x, y) && guiEntity.isActive()) {
					this.focusedGUIEntity = guiEntity;
					break containerLoop;
				}
			}
		}

		if (this.focusedGUIEntity != null && this.focusedGUIEntity.contains(x, y)) {
			this.focusedGUIEntity.mousePressed(button, x, y);
			return true;
		}
		return false;
	}

	@Override
	protected boolean mouseReleased(int button, int x, int y) {
		for (Container container : this.containerList) {
			for (ContainerAddon addon : container.getAddons()) {
				if (addon.mouseReleased(button, x, y)) {
					return true;
				}
			}
		}

		if (this.focusedGUIEntity != null) {
			this.focusedGUIEntity.mouseReleased(button, x, y);
			return true;
		}
		return false;
	}

	@Override
	protected boolean mouseRepeat(int button, int x, int y) {
		if (this.focusedGUIEntity != null && this.focusedGUIEntity.contains(x, y) && this.focusedGUIEntity.isActive()) {
			this.focusedGUIEntity.mouseRepeat(button, x, y);
			return true;
		}
		return false;
	}

	@Override
	protected boolean mouseWheelChanged(int x, int y) {
		if (this.focusedGUIEntity != null) {
			this.focusedGUIEntity.mouseWheelChanged(x, y);
		}
		return false;
	}

	public void updateTransforms() {
		for (Container container : this.containerList) {
			container.updateTransform();
		}
	}

	public GUIEntity getFocusedGUIEntity() {
		return focusedGUIEntity;
	}

}
