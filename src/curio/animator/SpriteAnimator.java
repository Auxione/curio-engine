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
		init(textureAtlas, image);
	}

	public SpriteAnimator(TextureAtlas textureAtlas, Image image, AnimEquation computeEquation, Looptype looptype) {
		super(computeEquation);
		super.looptype = looptype;
		init(textureAtlas, image);
	}

	private final void init(TextureAtlas textureAtlas, Image image) {
		this.textureAtlas = textureAtlas;
		this.image = image;
		this.length = this.textureAtlas.size() - 1;
		this.textureAtlas.set(this.image, 0);
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

}
