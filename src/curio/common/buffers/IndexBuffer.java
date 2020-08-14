package common.buffers;

import java.nio.IntBuffer;
import org.lwjgl.system.MemoryUtil;

import common.utilities.NativeObject;
import common.utilities.NativeObjectManager;
import graphics.Indexable;

public class IndexBuffer implements NativeObject {
	private IntBuffer intBuffer;
	private int maxIndexNumber = 0;
	private int currentIndexOffset = 0;

	public IndexBuffer(int capacity) {
		this.intBuffer = MemoryUtil.memAllocInt(capacity);
		NativeObjectManager.register(this);
	}

	public IndexBuffer(int[] indices) {
		this(indices.length);
		putIndexed(indices);
	}

	public IndexBuffer(Indexable indexable) {
		this(indexable.getIndexArray());
	}

	public void put(int index) {
		this.intBuffer.put(index);
	}

	public void put(int[] indices) {
		this.intBuffer.put(indices);
	}

	public void put(Indexable indexable) {
		put(indexable.getIndexArray());
	}

	public void putIndexed(int[] indices) {
		this.maxIndexNumber = 0;
		for (int i = 0; i < indices.length; i++) {
			if (this.maxIndexNumber < indices[i]) {
				this.maxIndexNumber = indices[i];
			}

			this.intBuffer.put(indices[i] + this.currentIndexOffset);
		}
		this.currentIndexOffset += maxIndexNumber + 1;
	}

	public void putIndexed(int index) {
		index += this.currentIndexOffset;
		this.intBuffer.put(index);
		this.currentIndexOffset += 1;
	}

	public IntBuffer getData() {
		return intBuffer;
	}

	public void clear() {
		this.intBuffer.clear();
		this.currentIndexOffset = 0;
	}

	public void flip() {
		this.intBuffer.flip();
	}

	public int limit() {
		return this.intBuffer.limit();
	}

	public int capacity() {
		return this.intBuffer.capacity();
	}

	@Override
	public void terminate() {
		MemoryUtil.memFree(this.intBuffer);
	}

}
