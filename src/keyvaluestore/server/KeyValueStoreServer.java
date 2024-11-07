package keyvaluestore.server;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class KeyValueStoreServer implements KeyValueStoreInterface {
  private ConcurrentHashMap<String, String> store;
  private static final Logger logger = Logger.getLogger(KeyValueStoreServer.class.getName());

  public KeyValueStoreServer() {
    store = new ConcurrentHashMap<>();
  }

  @Override
  public String put(String clientId, String key, String value) throws RemoteException {
    String oldValue = store.put(key, value);
    logger.info("Client ID: " + clientId + " | PUT request: key=" + key + ", value=" + value);
    return oldValue;
  }

  @Override
  public String get(String clientId, String key) throws RemoteException {
    String value = store.get(key);
    logger.info("Client ID: " + clientId + " | GET request: key=" + key + ", returned value=" + value);
    return value;
  }

  @Override
  public String delete(String clientId, String key) throws RemoteException {
    String value = store.remove(key);
    logger.info("Client ID: " + clientId + " | DELETE request: key=" + key + ", deleted value=" + value);
    return value;
  }

  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Usage: java KeyValueStoreServer <port>");
      System.exit(1);
    }

    int port = Integer.parseInt(args[0]);

    try {
      // Set up logging and log file
      FileHandler fileHandler = new FileHandler("server.log");
      SimpleFormatter formatter = new SimpleFormatter();
      fileHandler.setFormatter(formatter);
      logger.addHandler(fileHandler);

      KeyValueStoreServer server = new KeyValueStoreServer();
      KeyValueStoreInterface stub = (KeyValueStoreInterface) UnicastRemoteObject.exportObject(server, 0);

      // Create and start RMI registry
      Registry registry = LocateRegistry.createRegistry(port);
      registry.bind("KeyValueStore", stub);

      logger.info("Server ready on port " + port);
    } catch (Exception e) {
      logger.severe("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }
}
