package MessageBasedArchitecture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import Game.SerialPortHandle;

public class Dispatcher implements Runnable, Subject {

	// need to keep track of proxies

	Random r = new Random();
	ArrayList<Observer> proxies;
	HashMap<Byte, Observer> proxyMap = new HashMap<Byte, Observer>(); // hashmap of the proxies with the Byte being the key of the HashMap
	SerialPortHandle sph;

	public Dispatcher(String portName) {
		sph = new SerialPortHandle(portName);
		proxies = new ArrayList<Observer>();
		new Thread(this).start(); // starts Dispatcher thread

	}

	public void setSerialPort(SerialPortHandle sph) {
		this.sph = sph;
	}

	public void send_msg(Proxy proxy, msg m) throws IOException {
		sph.printByte((byte) (m.getPayload() | m.getID()));
	}

	byte decodeID(byte value) {
		byte ID = (byte) (value & (byte) 0b00000011);
		return ID;
	}

	byte decodePayload(byte value) {
		byte payload = (byte) (value & (byte) 0b11111100);
		return payload;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {
			// sph.printByte((byte)0);
			if (proxies.size() > 0) { // if there are proxies registered to the dispatcher
				byte value = sph.readByte(); // read a byte from Xbee
				if (value != -1) { // checks if Xbee is avilable
					msg m = new msg((byte) decodeID(value), (byte) decodePayload(value));
					NotifyObservers(m); // send message to appropriate proxy 
				}
			}
		}

	}

	public SerialPortHandle getSerialPort() {
		return sph;
	}

	@Override
	public void registerObserver(Observer o, byte deviceID) { // registers proxy to dispatcher
		proxies.add(o);
		proxyMap.put(deviceID, o);

	}

	@Override
	public void RemoveObserver(Observer o) { // removes a registered proxy
		proxies.remove(o);
		proxyMap.remove(((Proxy) o).getDeviceID(), o);
	}

	@Override
	public void NotifyObservers(msg m) { // sends message to the appropriate proxy
		Observer p = proxyMap.get(m.getID()); // gets proxy from hashmap
		if (p == null) // checks if proxy exists in hashmap
			System.out.println("Proxy of ID " + m.getID() + " does not exist");
		else
			p.update(m); // sends message to the proxy
	}

}