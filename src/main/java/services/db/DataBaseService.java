package services.db;


import test.TestLog;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;


public class DataBaseService {


    private String URL;
    private String USER;
    private String PASSWORD;
    private TestLog log = TestLog.getInstance();

    private final static DataBaseService instance = new DataBaseService();

    public static DataBaseService getInstance(){
        return instance;
    }

    private DataBaseService(){
        Properties propertiesDB = new Properties();
        try {
            String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
            String appConfigPath = rootPath + "db.properties";
            FileInputStream fis = new FileInputStream(appConfigPath);
            propertiesDB.load(fis);

            USER = propertiesDB.getProperty("user");
            PASSWORD = propertiesDB.getProperty("password");
            URL = propertiesDB.getProperty("url");

            log.sendToConsoleMessage("#TEST [class DataBaseService] URL:"+URL+" PASSWORD: "+PASSWORD+" USER: "+USER);
            //TODO create demons thread that's follow for connections and print available
        } catch (IOException e) {
            log.sendToConsoleMessage("#TEST [class DataBaseService] [ERROR] get properties");
            e.printStackTrace();
        }
    }

    private Vector<Connection> availableConnections = new Vector<>();
    private Vector<Connection> usedConnections = new Vector<>();

    public synchronized Connection retrieve() {
        Connection newConnection = null;
        if (availableConnections.size() == 0) {
            newConnection = getConnection();
        } else {
            newConnection = availableConnections.lastElement();
            availableConnections.removeElement(newConnection);
        }
        usedConnections.addElement(newConnection);
        return newConnection;
    }

    public synchronized void putback(Connection connection) throws NullPointerException {
        if (connection != null) {
            if (usedConnections.removeElement(connection)) {
                availableConnections.addElement(connection);
            } else {
                throw new NullPointerException("Connection not in the usedConnections");
            }
        }
    }
    /********************************************************************************
     *                   Methods for connect to DataBase                            *
     * ******************************************************************************/
    private Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
    private void connectionClose(){
//        try {
//            connection.close();
//            if (connection.isClosed()) {
//                System.out.println("Connection is closed");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
    /**
     * @return the number of available connections
     */
    public void getAvailableConnections() {
       log.sendToConsoleMessage("#INFO [DataBaseService][getAvailableConnections] availableConnections: " + availableConnections.size() + ", usedConnections: " + usedConnections.size());
    }
}
