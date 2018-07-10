package Server;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer implements OperationsInterface {

	@Override
	public void funcaoVoid() throws RemoteException {
		return;
	}

	@Override
	public long funcaoLong(long valor) throws RemoteException {
		return valor + 5;
	}

	@Override
	public long soma8longs(long[] arraylong) throws RemoteException {
		long result = 0;
		for(long valor : arraylong) {
			result += valor;
		}
		return result;
	}

	@Override
	public String funcaoString(String valor) throws RemoteException {
		return valor.toUpperCase();
	}
	public static void main(String args[]) {
      
      try {
    	  //System.setProperty("java.rmi.server.hostname","localhost");
    	  RMIServer obj = new RMIServer();
    	  OperationsInterface stub = (OperationsInterface) UnicastRemoteObject.exportObject(obj, 0);
    	  
          // Bind the remote object's stub in the registry
          
    	  //Registry registry = LocateRegistry.createRegistry(1099);
          Naming.rebind("OperationsInterface", stub);
          System.err.println("Server ready");
      } catch (Exception e) {
          System.err.println("Server exception: " + e.toString());
          e.printStackTrace();
      }
	
	}
}
