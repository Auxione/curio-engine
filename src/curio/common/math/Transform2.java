package common.math;

import java.util.ArrayList;

import org.joml.Matrix3x2f;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import core.debug.DebugManager;
import core.debug.DebugObject;
import graphics.Color;
import graphics.renderer2d.Renderer2D;

/**
 * this class represents 2D affine transformations of the
 * {@link TransformingObject}.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public class Transform2 implements DebugObject {
	public final Vector2f worldPosition = new Vector2f();
	public final Vector2f direction = new Vector2f(1, 0);

	public final Vector2f localPosition = new Vector2f();
	public final Rotationf localRotation = new Rotationf();
	public final Vector2f localScale = new Vector2f(1, 1);

	private Matrix3x2f transform = new Matrix3x2f();

	private ArrayList<Transform2> childs = new ArrayList<Transform2>();
	private Transform2 parent = null;

	public Transform2() {
	}

	public Transform2(Transform2 parent) {
		setParent(parent);
	}

	protected void calculateParent() {
		if (this.parent != null) {
			this.transform.mulLocal(this.parent.transform);
		}
	}

	protected void calculateMatrix() {
		this.transform.identity();
		this.transform.scale(localScale);
		this.transform.rotate(localRotation.getRAD());
		this.transform.setTranslation(localPosition);
	}

	protected void calculateWorldPosition() {
		this.worldPosition.zero();
		this.direction.set(1, 0);
		this.transform.transformPosition(this.worldPosition);
		this.transform.transformDirection(this.direction);
	}

	public void apply(TransformingObject transformingObject) {
		transformingObject.reBuild();
		Vector2f[] points = transformingObject.getDefaultPoints();

		for (int i = 0; i < points.length; i++) {
			this.transform.transformPosition(points[i]);
		}
	}

	public void apply(Vector2f vector2f, Vector2f target) {
		this.transform.transformPosition(vector2f, target);
	}

	public final void update() {
		calculateMatrix();
		calculateParent();
		calculateWorldPosition();
	}

	public final void update(TransformingObject transformingObject) {
		update();
		apply(transformingObject);
	}

	public final void setParent(Transform2 parent) {
		this.parent = parent;
		parent.childs.add(this);
	}

	public void translate(Vector2f position) {
		this.localPosition.add(position);
	}

	public void translate(float x, float y) {
		this.localPosition.add(x, y);
	}

	public void scale(float x, float y) {
		this.localScale.add(x, y);
	}

	public void rotate(float localAngle) {
		this.localRotation.addDEG(localAngle);
	}

	public void rotate(Rotationf rotation) {
		this.localRotation.add(rotation);
	}

	public void reset() {
		this.localScale.set(1, 1);
		this.localRotation.zero();
		this.localPosition.zero();
	}

	@Override
	public void debugDraw(Renderer2D renderer2d, Color color) {
		DebugManager.renderString(renderer2d, debugPrint(), color, this.worldPosition.x, this.worldPosition.y);
		renderer2d.fillRectCentered(this.worldPosition.x, this.worldPosition.y, 2, 2, color);
	}

	@Override
	public String debugPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append("[Local x: ").append(this.localPosition.x).append(" y: ").append(this.localPosition.y)
				.append(" ang: ").append(this.localRotation.getDEG()).append("][World x: ").append(this.worldPosition.x)
				.append(" y: ").append(this.worldPosition.y).append("]");
		return sb.toString();
	}

	public Matrix3x2f getMatrix() {
		return transform;
	}

	public static Matrix4f convert(Matrix3x2f transform, Matrix4f target) {
		target.identity();
		target.m00(transform.m00());
		target.m01(transform.m01());
		target.m10(transform.m10());
		target.m11(transform.m11());
		target.m30(transform.m20());
		target.m31(transform.m21());
		return target;
	}

}
