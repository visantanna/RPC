
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface OperationsInterface extends Remote {
	void funcaoVoid() throws RemoteException;
	long funcaoLong(long valor) throws RemoteException;
	long soma8longs(long[] arraylong) throws RemoteException;
	String funcaoString(String valor)throws RemoteException;
}
