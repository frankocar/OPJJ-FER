package hr.fer.zemris.java.p10.udp;

public class ShortUtil {

	public static void shortToBytes(short number, byte[] buf, int offset) {
		buf[offset] = (byte) ((number >> 8) & 0xFF);
		buf[offset + 1] = (byte) (number & 0xFF);
	}
	
	public static short bytesToShort(byte[] buf, int offset) {
		return (short) (
				(buf[offset] & 0xFF) << 8 |
				(buf[offset + 1] & 0xFF));
	}
	
	
}
