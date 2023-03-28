package consoletest;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import RP2040.ParameterMemoryRegion;

public class ScanTest1 {
	private static String mySSID;
	private static String myPWD;

	public static void main(String[] args) {
		mySSID = args[0];
		myPWD = args[1];
		try {
			doit();
			doPara();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void doit() throws IOException {
		// TODO Auto-generated method stub
		UF2BufferFileChainConsole uf2file = new UF2BufferFileChainConsole();
		uf2file.readFile(new File("test.uf2"));
		System.out.println("");
		uf2file.dumpIt();

	}

	private static void doPara() throws IOException {
		ParameterMemoryRegion parameterMemoryRegion = new ParameterMemoryRegion();
		ByteBuffer b = parameterMemoryRegion.getByteBuffer();
		// b.reset();

		b.put(mySSID.getBytes());
		while (b.position() < 64)
			b.put((byte) 0);

		b.put(myPWD.getBytes());
		while (b.position() < 128)
			b.put((byte) 0);

		UF2BufferFileChainConsole uf = UF2BufferFileChainConsole.fromMemoryRegion(parameterMemoryRegion);
		uf.writeFile(new File("para.uf2"));
	}
}
