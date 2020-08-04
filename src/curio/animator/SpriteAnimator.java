package animator;

import animator.equations.AnimEquation;
import animator.looptypes.Looptype;
import graphics.TextureAtlas;
import graphics.renderer2d.Image;

public final class SpriteAnimator extends Animator {
	private TextureAtlas textureAtlas;
	private Image image;

	private int length;

	public SpriteAnimator(TextureAtlas textureAtlas, Image image, AnimEquation computeEquation) {
		super(computeEquation);
		this.textureAtlas = textureAtlas;
		this.image = image;
		this.length = this.textureAtlas.size() - 1;
		this.textureAtlas.set(this.image, 0);
	}

	public SpriteAnimator(TextureAtlas textureAtlas, Image image, AnimEquation computeEquation, Looptype looptype) {
		this(textureAtlas, image, computeEquation);
		super.looptype = looptype;
	}

	@Override
	public void onFixedUpdate(float ratio) {
		this.textureAtlas.set(this.image, (int) (length * ratio));
	}

	public final TextureAtlas getTextureAtlas() {
		return textureAtlas;
	}

	public final Image getImage() {
		return image;
	}

	@Override
	protected void onReset() {
		this.textureAtlas.set(this.image, 0);
	}
}
