package MessageBasedArchitecture;

import java.io.IOException;

import Game.Game;

public class Proxy implements Observer {

	Dispatcher d;
	byte deviceID;
	Game g;
	volatile msg m;

	public Proxy(Dispatcher d, byte deviceID) throws IOException {

		this.d = d;
		this.deviceID = deviceID;
		m = new msg((byte) 0b11111111, (byte) 0b11111111);
		d.registerObserver(this, deviceID);

	}

	public void send_msg(msg m) throws IOException {
		// tell the dispatcher to send your message
		d.send_msg(this, m);
	}

	byte decodeID(byte value) { // decode the message to get ID
		byte ID = (byte) (value & (byte) 0b00000011);
		return ID;
	}

	byte decodePayload(byte value) { // decode the message to get payload
		byte payload = (byte) (value & (byte) 0b11111100);
		return payload;
	}

	public void setGame(Game g) {
		this.g = g;
	}

	public void update(msg msg) {
		Game.m = msg;
		m = msg;
	}

	public byte getDeviceID() {
		return deviceID;
	}

	public msg getMessage() {
		return m;
	}

	public void setMessage(msg m) {
		this.m = m;
	}

}