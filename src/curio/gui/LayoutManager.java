package gui;

import java.util.HashMap;

import gui.layouts.Layout;
import gui.utilities.Allignable;
import gui.utilities.LayoutBase;

public class LayoutManager {
	private HashMap<Allignable, Layout> layoutMap;
	private LayoutBase layoutBase;

	public LayoutManager(LayoutBase layoutBase) {
		this.layoutBase = layoutBase;
		this.layoutMap = new HashMap<Allignable, Layout>();
	}

	public LayoutManager(int width, int height) {
		this.layoutBase = new LayoutBase() {

			@Override
			public int getWidth() {
				return width;
			}

			@Override
			public int getHeight() {
				return height;
			}

			@Override
			public void updateTransform() {

			}
		};
		this.layoutMap = new HashMap<Allignable, Layout>();
	}

	public void put(Allignable allignable, Layout layout) {
		this.layoutMap.put(allignable, layout);
	}

	public void updateLayouts() {
		for (Allignable allignable : layoutMap.keySet()) {
			Layout layout = layoutMap.get(allignable);
			layout.allignMethod(allignable, this.layoutBase);
		}
		this.layoutBase.updateTransform();
	}

}
