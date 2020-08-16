package common.utilities;

import core.Input;

/**
 * TextInput class for capturing keys in runtime to create string.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class TextInput {
	private Runnable runnable;

	private String value = "";
	public int maxCharacter = 10000;

	private String oldText;
	private int oldCursorPos;

	private boolean key_Control = false;
	private boolean key_Shift = false;

	private int cursorIndex;

	private Input input;

	/**
	 * Create new TextInput.
	 * 
	 * @param input
	 */
	public TextInput(Input input) {
		this.input = input;
	}

	/**
	 * Create new TextInput wit runnable. Runnable interface runs when ENTER key
	 * pressed.
	 * 
	 * @param input
	 */
	public TextInput(Input input, Runnable runnable) {
		this(input);
		this.runnable = runnable;
	}

	public final boolean onKeyPressed(int key, char c) {
		if (key == Input.KEY_RIGHT_CONTROL || key == Input.KEY_LEFT_CONTROL) {
			this.key_Control = true;
		} else if (key == Input.KEY_RIGHT_SHIFT || key == Input.KEY_LEFT_SHIFT) {
			this.key_Shift = true;
		}

		if (key_Control == true) {
			if (key == Input.KEY_V) {
				String text = input.getClipboardContent();
				if (text != null) {
					doPaste(text);
				}
				setText(this.value);
				return true;
			}

			else if (key == Input.KEY_Z) {
				doUndo(oldCursorPos, oldText);
				setText(this.value);
				return true;
			}
		}

		if (key == Input.KEY_LEFT) {// move cursor to left
			if (this.cursorIndex > 0) {
				this.cursorIndex--;
			}
		}

		else if (key == Input.KEY_RIGHT) {// move cursor to right
			if (this.cursorIndex < this.value.length()) {
				this.cursorIndex++;
			}
		}

		else if (key == Input.KEY_BACKSPACE) {
			deleteBackward();
			return true;
		}

		else if (key == Input.KEY_DELETE) {
			deleteForward();
			return true;
		}

		else if (key == Input.KEY_SPACE) {
			insertChar(' ');
			return true;
		}

		else if (key == Input.KEY_ENTER && runnable != null) {
			runnable.run();
			return true;
		}

		else if (c > 31 && c < 127 && this.value.length() < maxCharacter) {
			if (key_Shift) {
				insertChar(Character.toUpperCase(c));
			} else {
				insertChar(c);
			}
			setText(this.value);
			return true;
		}
		return false;
	}

	public final boolean onKeyRepeat(int key, char c) {
		if (key == Input.KEY_LEFT) {// move cursor to left
			if (this.cursorIndex > 0) {
				this.cursorIndex--;
				return true;
			}
		}

		else if (key == Input.KEY_RIGHT) {// move cursor to right
			if (this.cursorIndex < this.value.length()) {
				this.cursorIndex++;
				return true;
			}
		}

		else if (key == Input.KEY_BACKSPACE) {
			deleteBackward();
			return true;
		}

		else if (key == Input.KEY_DELETE) {
			deleteForward();
			return true;
		}

		else if (key == Input.KEY_SPACE) {
			insertChar(' ');
			return true;
		}

		else if (c > 31 && c < 127 && this.value.length() < maxCharacter) {
			if (key_Shift) {
				insertChar(Character.toUpperCase(c));
			} else {
				insertChar(c);
			}
			setText(this.value);
			return true;
		}
		return false;
	}

	public final boolean onKeyReleased(int key, char c) {
		if (key == Input.KEY_RIGHT_CONTROL || key == Input.KEY_LEFT_CONTROL) {
			this.key_Control = true;
			return true;
		} else if (key == Input.KEY_RIGHT_SHIFT || key == Input.KEY_LEFT_SHIFT) {
			this.key_Shift = true;
			return true;
		}
		return false;
	}

	private final void deleteBackward() {
		if (this.cursorIndex > 0 && this.value.length() > 0) {
			if (this.cursorIndex < value.length()) {
				value = value.substring(0, cursorIndex - 1) + value.substring(cursorIndex);
			} else {
				value = value.substring(0, cursorIndex - 1);
			}
			cursorIndex--;
		}
	}

	private final void deleteForward() {
		if (value.length() > cursorIndex) {
			value = value.substring(0, cursorIndex) + value.substring(cursorIndex + 1);
		}
	}

	private final void insertChar(char c) {
		if (cursorIndex < value.length()) {
			value = value.substring(0, cursorIndex) + c + value.substring(cursorIndex);
		} else {
			value = value.substring(0, cursorIndex) + c;
		}
		cursorIndex++;
	}

	/**
	 * Set text to given string and move cursor to last character.
	 * 
	 * @param value
	 */
	public final void setText(String value) {
		this.value = value;
		if (cursorIndex > value.length()) {
			cursorIndex = value.length();
		}
	}

	/**
	 * 
	 * @return the text string.
	 */
	public final String getString() {
		return this.value;
	}

	/**
	 * Set the cursor to given point.
	 * 
	 * @param pos new position of the cursor.
	 */
	public final void setCursorPos(int pos) {
		cursorIndex = pos;
		if (cursorIndex > value.length()) {
			cursorIndex = value.length();
		}
	}

	/**
	 * Paste new text to current cursor position.
	 * 
	 * @param text the text string to insert.
	 */
	public final void doPaste(String text) {
		this.oldText = this.value;
		this.oldCursorPos = this.cursorIndex;

		for (int i = 0; i < text.length(); i++) {
			insertChar(text.charAt(i));
		}
	}

	private final void doUndo(int oldCursorPos, String oldText) {
		if (oldText != null) {
			setText(oldText);
			setCursorPos(oldCursorPos);
		}
	}
}
