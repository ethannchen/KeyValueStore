package keyvaluestore.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KeyValueStoreInterface extends Remote {
  String put(String clientId, String key, String value) throws RemoteException;
  String get(String clientId, String key) throws RemoteException;
  String delete(String clientId, String key) throws RemoteException;
}
