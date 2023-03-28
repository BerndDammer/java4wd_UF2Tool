package genuf2;

import java.nio.ByteBuffer;

public class MemoryRegion {
	private final int baseAddress;
	private final ByteBuffer byteBuffer; 

	public int getSize() {
		return byteBuffer.capacity();
	}
	public int getBaseAddress() {
		return baseAddress;
	}

	public MemoryRegion(final int paraBase, final int paraSize) {
		baseAddress = paraBase;
		byteBuffer = ByteBuffer.allocate(paraSize);
	}

	public ByteBuffer getByteBuffer() {
		return byteBuffer;
	}
}
