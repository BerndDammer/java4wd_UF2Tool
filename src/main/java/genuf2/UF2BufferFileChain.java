package genuf2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.util.LinkedList;

import RP2040.ParameterMemoryRegion;
import javafx.collections.ObservableList;

public class UF2BufferFileChain extends LinkedList<ByteBuffer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static UF2BufferFileChain fromFile(File file) throws IOException {
		UF2BufferFileChain result = new UF2BufferFileChain();
		if (file.length() % UF2Statics.UF2_CHUNK_SIZE != 0) {
			throw new IOException("FileSize not multiple of 512");
		}
		final byte[] allBytes = Files.readAllBytes(file.toPath());

		for (int start = 0; start < allBytes.length; start += UF2Statics.UF2_CHUNK_SIZE) {
			final ByteBuffer bb = ByteBuffer.allocate(UF2Statics.UF2_CHUNK_SIZE);
			bb.put(allBytes, start, UF2Statics.UF2_CHUNK_SIZE);
			result.add(bb);
		}
		return result;
	}

	public void writeFile(File file) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		for (final ByteBuffer b : this) {
			fileOutputStream.write(b.array());
		}
		fileOutputStream.close();
	}

	public void dumpIt(ObservableList<String> value) {
		for (final ByteBuffer b : this) {
			b.order(ByteOrder.LITTLE_ENDIAN);
			b.position(0);
			String s = String.format("%H %H F:%H A:%H SIZE:%H n:%H NB:%H FID:%H\n", //
					b.getInt(), b.getInt(), b.getInt(), b.getInt(), //
					b.getInt(), b.getInt(), b.getInt(), b.getInt());
			value.add(s);
		}
	}

	public ParameterMemoryRegion getParameterMemoryRegion() {
		final ParameterMemoryRegion result = new ParameterMemoryRegion();
		result.getByteBuffer().clear();

		int expectedAddress = result.getBaseAddress();

		for (ByteBuffer bb : this) {

			int addr = bb.getInt(12);
			int size = bb.getInt(16);
			if (addr != expectedAddress)
				return null;

			result.getByteBuffer().put( //
					expectedAddress - result.getBaseAddress(), //
					bb, //
					8 * 4, //
					size //
			);
			expectedAddress += size;

		}
		if (expectedAddress != result.getBaseAddress() + result.getSize())
			return null;
		return result;
	}

	public static UF2BufferFileChain fromMemoryRegion(MemoryRegion memoryRegion) {
		UF2BufferFileChain result = new UF2BufferFileChain();
		result.clear();
		memoryRegion.getByteBuffer().clear(); // reset pointer ... content stays
		final byte[] b256 = new byte[UF2Statics.MEM_CHUNK_SIZE];
		long numBlocks = memoryRegion.getByteBuffer().capacity() / (long) UF2Statics.MEM_CHUNK_SIZE;
		int targetAddr = memoryRegion.getBaseAddress();
		for (int blockNo = 0; blockNo < numBlocks; blockNo++) {
			final ByteBuffer bb = ByteBuffer.allocate(UF2Statics.UF2_CHUNK_SIZE);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			bb.putInt(UF2Statics.UF2_MAGIC_START0);
			bb.putInt(UF2Statics.UF2_MAGIC_START1);
			bb.putInt(UF2Statics.FLAGS_QQQ);
			bb.putInt(targetAddr);
			bb.putInt(UF2Statics.MEM_CHUNK_SIZE);
			bb.putInt(blockNo);
			bb.putInt((int) numBlocks);
			bb.putInt(UF2Statics.FAMILY_ID_RP2040); // reserved
			memoryRegion.getByteBuffer().get(b256);
			bb.put(b256);
			while (bb.remaining() > 4)
				bb.put((byte) 0);
			bb.putInt(UF2Statics.UF2_MAGIC_END);
			result.add(bb);
			targetAddr += UF2Statics.MEM_CHUNK_SIZE;
		}
		return result;
	}
}
