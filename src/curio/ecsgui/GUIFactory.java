package ecsgui;

import core.Input;
import core.events.EventContainer;
import ecsgui.components.GUIButtonComponent;
import ecsgui.components.GUIFillComponent;
import ecsgui.components.GUITextDisplayComponent;
import ecsgui.components.GUITextfieldComponent;
import graphics.Color;
import graphics.Texture;
import graphics.renderer2d.FontData;

public class GUIFactory {
	public static GUIEntity createButton(EventContainer eventContainer, GUIComponentEvent guiEvent, Color background,
			FontData fontData, String text, int textAllignment) {
		GUIEntity out = new GUIEntity(eventContainer);
		out.add(new GUIFillComponent(background));
		out.add(new GUIButtonComponent(guiEvent));

		GUITextDisplayComponent gUITextDisplayComponent = new GUITextDisplayComponent(fontData);
		gUITextDisplayComponent.allign(textAllignment);
		gUITextDisplayComponent.setText(text);
		out.add(gUITextDisplayComponent);

		out.applySettings();
		return out;
	}

	public static GUIEntity createButton(EventContainer eventContainer, GUIComponentEvent guiEvent, Texture background,
			FontData fontData, String text, int textAllignment) {
		GUIEntity out = new GUIEntity(eventContainer);
		out.add(new GUIFillComponent(background));
		out.add(new GUIButtonComponent(guiEvent));

		GUITextDisplayComponent gUITextDisplayComponent = new GUITextDisplayComponent(fontData);
		out.add(gUITextDisplayComponent);
		gUITextDisplayComponent.allign(textAllignment);
		gUITextDisplayComponent.setText(text);

		out.applySettings();
		return out;
	}

	public static GUIEntity createTextDisplay(EventContainer eventContainer, Color backgroundColor, FontData fontData,
			String text, int textAllignment) {
		GUIEntity out = new GUIEntity(eventContainer);
		GUITextDisplayComponent gUITextDisplayComponent = new GUITextDisplayComponent(fontData);
		gUITextDisplayComponent.allign(textAllignment);
		gUITextDisplayComponent.setText(text);

		out.add(new GUIFillComponent(backgroundColor));
		out.add(gUITextDisplayComponent);

		out.applySettings();
		return out;
	}

	public static GUIEntity createTextDisplay(EventContainer eventContainer, Texture background, FontData fontData,
			String text, int textAllignment) {
		GUIEntity out = new GUIEntity(eventContainer);
		out.add(new GUIFillComponent(background));

		GUITextDisplayComponent gUITextDisplayComponent = new GUITextDisplayComponent(fontData);
		gUITextDisplayComponent.allign(textAllignment);
		out.add(gUITextDisplayComponent);

		out.applySettings();
		return out;
	}

	public static GUIEntity createTextField(EventContainer eventContainer, Input input, Color background,
			FontData fontData, String text, int textAllignment) {
		GUIEntity out = createTextDisplay(eventContainer, background, fontData, text, textAllignment);

		GUITextfieldComponent guiTextfieldComponent = new GUITextfieldComponent(eventContainer);
		out.add(guiTextfieldComponent);
		guiTextfieldComponent.setText(text);
		out.applySettings();
		return out;
	}

	public static GUIEntity createTextField(EventContainer eventContainer, Input input, Runnable runnable,
			Color background, FontData fontData, String text, int textAllignment) {
		GUIEntity out = createTextDisplay(eventContainer, background, fontData, text, textAllignment);

		GUITextfieldComponent guiTextfieldComponent = new GUITextfieldComponent(eventContainer, runnable);
		out.add(guiTextfieldComponent);
		out.applySettings();
		return out;
	}
}
