package RP2040;

import genuf2.MemoryRegion;

public class ParameterMemoryRegion extends MemoryRegion {
	public ParameterMemoryRegion() {
		super(Flash.PARA_BASE, Flash.PARA_SIZE);

		getByteBuffer().clear();
		while (getByteBuffer().hasRemaining()) {
			getByteBuffer().put((byte) 0XFF);
		}
	}

	public void setSSID(final String ssid) {
		getByteBuffer().clear();

		getByteBuffer().put(ssid.getBytes());
		while (getByteBuffer().position() < 64)
			getByteBuffer().put((byte) 0);
	}

	public String getSSID() {
		getByteBuffer().clear();

		if (getByteBuffer().get(63) != 0)
			return null;
		final StringBuilder sb = new StringBuilder();
		byte b;
		while ((b = getByteBuffer().get()) != 0) {
			sb.append((char) b);
		}
		return sb.toString();
	}

	public void setPWD(final String pwd) {
		getByteBuffer().clear();
		getByteBuffer().position(64);

		getByteBuffer().put(pwd.getBytes());
		while (getByteBuffer().position() < 128)
			getByteBuffer().put((byte) 0);
	}

	public String getPWD() {
		getByteBuffer().clear();
		getByteBuffer().position(64);

		if (getByteBuffer().get(63 + 64) != 0)
			return null;
		final StringBuilder sb = new StringBuilder();
		byte b;
		while ((b = getByteBuffer().get()) != 0) {
			sb.append((char) b);
		}
		return sb.toString();
	}
}
