package ecsgui;

import ecsgui.components.GUIComponent;

public interface GUIComponentEvent {

	/**
	 * @param source The source of the event
	 */
	public void componentActivated(GUIComponent source);

	/**
	 * @param source The source of the event
	 */
	public void componentDeactivated(GUIComponent source);
}
