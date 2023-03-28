package RP2040;

public class Flash {
	
	public static final int BASE = 0x10000000;
	public static final int SIZE = 0x00200000;

	public static final int PARA_SIZE = 4096; // one flash page
	public static final int PARA_BASE = BASE + SIZE - PARA_SIZE;
}
