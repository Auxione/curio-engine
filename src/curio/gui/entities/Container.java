package gui.entities;

import java.util.ArrayList;

import graphics.renderer2d.Renderer2D;
import gui.GUIEntity;
import gui.utilities.ContainerAddon;
import gui.utilities.Fill;
import gui.utilities.LayoutBase;

public class Container extends GUIEntity implements LayoutBase {
	private final ArrayList<GUIEntity> childs = new ArrayList<GUIEntity>();
	private final ArrayList<ContainerAddon> addons = new ArrayList<ContainerAddon>(4);

	public Container(int width, int height, Fill background) {
		super(width, height, background);
	}

	public final void add(GUIEntity entity) {
		if (!this.childs.contains(entity)) {
			this.childs.add(entity);
			entity.enviroment = this.enviroment;
			entity.rectTransform().setParent(rectTransform());
		}
	}

	public final void removeChild(GUIEntity entity) {
		if (this.childs.contains(entity)) {
			this.childs.remove(entity);
			entity.rectTransform().setParent(null);
		}
	}

	public final void register(ContainerAddon containerAddon) {
		for (ContainerAddon addon : this.addons) {
			if (addon.getType() == containerAddon.getType()) {
				return;
			}
		}
		this.addons.add(containerAddon);
	}

	protected void onGUIUpdate(Renderer2D renderer) {
		for (GUIEntity guiEntity : childs) {
			guiEntity.render(renderer);
		}
	}

	@Override
	protected void onTransformApply() {
		for (GUIEntity guiEntity : childs) {
			guiEntity.updateTransform();
		}
	}

	public final GUIEntity get(int index) {
		return this.childs.get(index);
	}

	@Override
	public final int getHeight() {
		return this.rectTransform().height;
	}

	@Override
	public final int getWidth() {
		return this.rectTransform().width;
	}

	public final ArrayList<ContainerAddon> getAddons() {
		return this.addons;
	}

	public final ArrayList<GUIEntity> getChilds() {
		return this.childs;
	}
}
