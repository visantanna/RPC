import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient implements BasicRPCInterface {
	OperationsInterface stub;
	public RMIClient(String publicAddress , int port) {
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(publicAddress, port);
			String name = "OperationsInterface";
			stub = (OperationsInterface)registry.lookup(name);
			
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void funcaoVoid() throws RemoteException {
		stub.funcaoVoid();
	}

	@Override
	public long funcaoLong(long valor) throws RemoteException {
		return stub.funcaoLong(valor);
	}

	@Override
	public long soma8longs(long[] arraylong) throws RemoteException {
		return stub.soma8longs(arraylong);
	}

	@Override
	public String funcaoString(String valor) throws RemoteException {
		return stub.funcaoString(valor);
	}
	
}
