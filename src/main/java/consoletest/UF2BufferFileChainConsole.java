package consoletest;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.LinkedList;

import genuf2.MemoryRegion;
import genuf2.UF2Statics;

public class UF2BufferFileChainConsole extends LinkedList<ByteBuffer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void readFile(File file) throws IOException {
		if (file.length() % UF2Statics.UF2_CHUNK_SIZE != 0) {
			error("FileSize not multiple of 512");
		}
		FileInputStream fileInputStream = new FileInputStream(file);
		DataInputStream dataInputStream = new DataInputStream(fileInputStream);
		long chunk_count = file.length() / (long) UF2Statics.UF2_CHUNK_SIZE;
		byte[] bs;
		while (chunk_count-- > 0) {
			bs = new byte[UF2Statics.UF2_CHUNK_SIZE];
			dataInputStream.read(bs);
			add(ByteBuffer.wrap(bs));
		}
		dataInputStream.close();
		fileInputStream.close();
	}

	public void writeFile(File file) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		byte[] bs;
		for (final ByteBuffer b : this) {
			fileOutputStream.write(b.array());
		}
		fileOutputStream.close();
	}

	private void error(String string) {
		System.out.println("------------------" + string);
	}

	public void dumpIt() {
		for (final ByteBuffer b : this) {
			b.order(ByteOrder.LITTLE_ENDIAN);
			b.position(0);
			System.out.printf("%H %H F:%H A:%H SIZE:%H n:%H ", b.getInt(), b.getInt(), b.getInt(), b.getInt(),
					b.getInt(), b.getInt());
			System.out.printf("NB:%H FID:%H\n", b.getInt(), b.getInt());
		}
	}

	public static UF2BufferFileChainConsole fromMemoryRegion(MemoryRegion memoryRegion) {
		UF2BufferFileChainConsole result = new UF2BufferFileChainConsole();
		result.clear();
		//memoryRegion.getByteBuffer().reset();
		//memoryRegion.getByteBuffer().flip();
		memoryRegion.getByteBuffer().position(0);
		final byte[] b256 = new byte[UF2Statics.MEM_CHUNK_SIZE];
		long numBlocks = memoryRegion.getByteBuffer().capacity() / (long) UF2Statics.MEM_CHUNK_SIZE;
		int targetAddr = memoryRegion.getBaseAddress();
		for(int blockNo =0; blockNo < numBlocks; blockNo++) 
		{
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
			while(bb.remaining() > 4) bb.put((byte) 0);
			bb.putInt(UF2Statics.UF2_MAGIC_END);
			result.add(bb);
			targetAddr += UF2Statics.MEM_CHUNK_SIZE;
		}
		return result;
	}
}
