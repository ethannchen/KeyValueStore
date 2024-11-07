package keyvaluestore.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.UUID;
import keyvaluestore.server.KeyValueStoreInterface;

public class KeyValueStoreClient {
  private static final Logger logger = Logger.getLogger(KeyValueStoreClient.class.getName());
  private static KeyValueStoreInterface stub;
  private static String clientId; // Add clientId variable

  public static void main(String[] args) {
    if (args.length != 2) {
      System.err.println("Usage: java KeyValueStoreClient <host> <port>");
      System.exit(1);
    }

    String host = args[0];
    int port = Integer.parseInt(args[1]);

    try {
      // Generate a unique client ID
      clientId = UUID.randomUUID().toString();
      logger.info("Client ID: " + clientId); // Log client ID

      // Set up logging and log file
      FileHandler fileHandler = new FileHandler("client.log");
      SimpleFormatter formatter = new SimpleFormatter();
      fileHandler.setFormatter(formatter);
      logger.addHandler(fileHandler);

      Registry registry = LocateRegistry.getRegistry(host, port);
      stub = (KeyValueStoreInterface) registry.lookup("KeyValueStore");

      // Pre-populate the store
      populateStore();

      // Perform automatic operations
      performAutomaticOperations();

      // Start interactive mode
      interactiveMode();

    } catch (Exception e) {
      logger.severe("Client exception: " + e.toString());
      e.printStackTrace();
    }
  }

  private static void populateStore() {
    try {
      // Pre-populate the store with the client ID included in the logs
      for (int i = 1; i <= 5; i++) {
        String key = "key" + i;
        String value = "value" + i;
        stub.put(clientId, key, value); // Pass client ID
        logger.info("Client ID: " + clientId + " | Pre-populated: " + key + " -> " + value);
      }
      logger.info("Store pre-populated with 5 key-value pairs by Client ID: " + clientId);
    } catch (Exception e) {
      logger.severe("Client ID: " + clientId + " | Error pre-populating store: " + e.toString());
    }
  }


  private static void performAutomaticOperations() {
    try {
      // 5 PUTs
      for (int i = 6; i <= 10; i++) {
        String key = "key" + i;
        String value = "value" + i;
        stub.put(clientId, key, value); // Pass client ID
        logger.info("Client ID: " + clientId + " | PUT: " + key + " -> " + value);
      }

      // 5 GETs
      for (int i = 1; i <= 5; i++) {
        String key = "key" + i;
        String value = stub.get(clientId, key); // Pass client ID
        logger.info("Client ID: " + clientId + " | GET: " + key + " -> " + value);
      }

      // 5 DELETEs
      for (int i = 6; i <= 10; i++) {
        String key = "key" + i;
        String value = stub.delete(clientId, key); // Pass client ID
        logger.info("Client ID: " + clientId + " | DELETE: " + key + " -> " + value);
      }
    } catch (Exception e) {
      logger.severe("Error performing automatic operations: " + e.toString());
    }
  }

  private static void interactiveMode() {
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.print("Enter command (PUT/GET/DELETE/EXIT): ");
      String command = scanner.nextLine().toUpperCase();

      try {
        switch (command) {
          case "PUT":
            System.out.print("Enter key: ");
            String key = scanner.nextLine();
            System.out.print("Enter value: ");
            String value = scanner.nextLine();
            stub.put(clientId, key, value); // Pass client ID
            logger.info("Client ID: " + clientId + " | PUT: " + key + " -> " + value);
            break;
          case "GET":
            System.out.print("Enter key: ");
            key = scanner.nextLine();
            value = stub.get(clientId, key); // Pass client ID
            logger.info("Client ID: " + clientId + " | GET: " + key + " -> " + value);
            System.out.println("Value: " + value);
            break;
          case "DELETE":
            System.out.print("Enter key: ");
            key = scanner.nextLine();
            value = stub.delete(clientId, key); // Pass client ID
            logger.info("Client ID: " + clientId + " | DELETE: " + key + " -> " + value);
            System.out.println("Deleted value: " + value);
            break;
          case "EXIT":
            logger.info("Client ID: " + clientId + " | Exiting client");
            System.exit(0);
          default:
            System.out.println("Invalid command. Please try again.");
        }
      } catch (Exception e) {
        logger.severe("Client ID: " + clientId + " | Error executing command: " + e.toString());
      }
    }
  }

}

