package Game;

import jssc.SerialPort;
import jssc.SerialPortException;

public class SerialPortHandle {

	SerialPort sp;

	String path;

	public SerialPortHandle(String path) {

		super();

		this.sp = new SerialPort(path);
		;
		this.path = path;
		try {
			sp.openPort();
			sp.setParams(9600, 8, 1, 0);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Open serial port
	}

	public String readLine() {
//		sp.isOpened();
		StringBuffer string = new StringBuffer();
		boolean quit = false;

		while (!quit) {
			byte[] buffer;
			try {
				buffer = sp.readBytes(1);
				// Read 1 bytes from serial port
				if (buffer[0] != 13) {
					string.append((char) (buffer[0]));
				}
				if (buffer[0] == 13) {
					// Read the following 10 character
					sp.readBytes(1);
					quit = true;
				}
			} catch (SerialPortException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return string.toString();
	}

	public char readChar() {
		char c = '\n';
		try {
			if (sp.getInputBufferBytesCount() != 0)
				c = (char) sp.readBytes(1)[0];
		} catch (SerialPortException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return c;
	}

	public byte readByte() {
		byte c = -1; // return -1 if there are no bytes available
		try {
			if (sp.getInputBufferBytesCount() != 0) { // check if there are any bytes available
				c = sp.readBytes(1)[0]; // reads a byte from XBee
			}

		} catch (SerialPortException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return c;
	}

	public byte[] readMessage() {
		byte[] buffer = null;
		try {
			if (sp.getInputBufferBytesCount() > 1)
				buffer = sp.readBytes(2);

		} catch (SerialPortException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return buffer;
	}

	public void printLine(String s) {

		byte byteArray[] = s.getBytes();

		try {
			sp.writeBytes(byteArray);
			sp.writeByte((byte) '\n');
		} catch (SerialPortException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void printChar(char c) {

		try {
			sp.writeByte((byte) c);
		} catch (SerialPortException e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void printByte(byte c) {

		try {
			sp.writeByte(c);
		} catch (SerialPortException e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
