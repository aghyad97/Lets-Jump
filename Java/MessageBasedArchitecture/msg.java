package MessageBasedArchitecture;

public class msg {
	byte ID;
	byte payload;

	public msg(byte id, byte Payload) {
		ID = id;
		payload = Payload;
	}

	public msg(byte value) {
		ID = (byte) (value & 0b00000011);
		payload = (byte) (value & 11111100);
	}

	public byte getID() {
		return ID;
	}

	public void setID(byte iD) {
		ID = iD;
	}

	public byte getPayload() {
		return payload;
	}

	public void setPayload(byte value) {
		this.payload = value;
	}

	public void resetMessage() {
		ID = (byte) 0b11111111; // resets the game's current message
		payload = (byte) 0b11111111;
	}
}
