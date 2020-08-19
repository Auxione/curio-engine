package gui.utilities;

import gui.GUIEntity;

public interface GUIEvent {
	public void activated(GUIEntity entity);

	public void deactivated(GUIEntity entity);
}
