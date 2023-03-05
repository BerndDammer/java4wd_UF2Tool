package gen;

import java.nio.ByteBuffer;

public class MemoryRegion {
	private int baseAddress;
	private int size;
	private ByteBuffer byteBuffer; 

	public MemoryRegion(final int paraBase, final int paraSize) {
		baseAddress = paraBase;
		size = paraSize;
		byteBuffer = ByteBuffer.allocate(size);
		//byteBuffer.reset();
	}

	public ByteBuffer getByteBuffer() {
		return byteBuffer;
	}

	public int getBaseAddress() {
		return baseAddress;
	}

}
