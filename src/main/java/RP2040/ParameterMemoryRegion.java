package RP2040;

import genuf2.MemoryRegion;

public class ParameterMemoryRegion extends MemoryRegion {
	public ParameterMemoryRegion() {
		super(Flash.PARA_BASE, Flash.PARA_SIZE);
	}
}
