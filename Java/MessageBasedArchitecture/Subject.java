package MessageBasedArchitecture;

public interface Subject {
	public void registerObserver(Observer o, byte deviceID);
	public void RemoveObserver(Observer o);
	public void NotifyObservers(msg m);
}
