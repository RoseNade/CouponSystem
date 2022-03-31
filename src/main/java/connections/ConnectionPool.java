package connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

public class ConnectionPool {

    private static final String URL = "jdbc:mysql://localhost:3306/?createDatabaseIfNotExist=FALSE&useTimezone=TRUE&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "193746825Ss";
    private Stack<Connection> connections = new Stack<>();
    private static final int NUM_OF_CONNECTIONS = 12;

    private static ConnectionPool instance = null;// = new ConnectionPool();

    private ConnectionPool() {
        for (int i = 1; i <= NUM_OF_CONNECTIONS; i++) {
            System.out.println("Creating connection #" + i);
            try {
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                connections.push(conn);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() throws InterruptedException {

        synchronized (connections) {

            if (connections.isEmpty()) {
                connections.wait();
            }

            return connections.pop();
        }
    }

    public void restoreConnection(Connection conn) {

        synchronized (connections) {
            connections.push(conn);
            connections.notify();
        }
    }

    public void closeAllConnection() throws InterruptedException {

        synchronized (connections) {

//            while (connections.size() < NUM_OF_CONNECTIONS) {
//                connections.wait();
//            }
            if (connections.size() == NUM_OF_CONNECTIONS) {
                int i = 1;

                for (Connection conn : connections) {
                    try {
                        System.out.println("Closed Connection #" + i++);
                        conn.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
}