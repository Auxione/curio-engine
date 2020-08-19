package gui.layouts;

import gui.utilities.Allignable;
import gui.utilities.LayoutBase;
import gui.utilities.RectTransform;

public class EdgeLayout implements Layout {
	public final static int TOPRIGHT = 9;
	public final static int TOPMID = 8;
	public final static int TOPLEFT = 7;

	public final static int MIDDLERIGHT = 6;
	public final static int MIDDLE = 5;
	public final static int MIDDLELEFT = 4;

	public final static int BOTTOMRIGHT = 3;
	public final static int BOTTOMMID = 2;
	public final static int BOTTOMLEFT = 1;

	public static void allign(Allignable allignable, LayoutBase layout, int alligmentType, int edgeOffset) {
		allign(layout.getWidth(), layout.getHeight(), allignable.rectTransform(), alligmentType, edgeOffset);
	}

	public static void allign(int width, int height, RectTransform child, int alligmentType, int edgeOffset) {
		switch (alligmentType) {
		case TOPLEFT:
			child.localPosition.x = edgeOffset;
			child.localPosition.y = edgeOffset;
			break;
		case TOPMID:
			child.localPosition.x = width / 2 - child.width / 2;
			child.localPosition.y = edgeOffset;
			break;
		case TOPRIGHT:
			child.localPosition.x = width - child.width - edgeOffset;
			child.localPosition.y = edgeOffset;
			break;

		case MIDDLELEFT:
			child.localPosition.x = edgeOffset;
			child.localPosition.y = height / 2 - child.height / 2;
			break;

		case MIDDLERIGHT:
			child.localPosition.x = width - child.width - edgeOffset;
			child.localPosition.y = height / 2 - child.height / 2;
			break;

		case BOTTOMLEFT:
			child.localPosition.x = edgeOffset;
			child.localPosition.y = height - child.height - edgeOffset;
			break;
		case BOTTOMMID:
			child.localPosition.x = width / 2 - child.width / 2;
			child.localPosition.y = height - child.height;
			break;
		case BOTTOMRIGHT:
			child.localPosition.x = width - child.width - edgeOffset;
			child.localPosition.y = height - child.height - edgeOffset;
			break;

		default:
			child.localPosition.x = width / 2 - child.width / 2;
			child.localPosition.y = height / 2 - child.height / 2;
			break;
		}
	}

	public int alligmentType, edgeOffset = 0;

	public EdgeLayout(int alligmentType) {
		this.alligmentType = alligmentType;
	}

	public EdgeLayout(int alligmentType, int edgeOffset) {
		this.alligmentType = alligmentType;
		this.edgeOffset = edgeOffset;
	}

	@Override
	public void allignMethod(Allignable allignable, LayoutBase layoutBase) {
		allign(allignable, layoutBase, this.alligmentType, this.edgeOffset);
	}

}
