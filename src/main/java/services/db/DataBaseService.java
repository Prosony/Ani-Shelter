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
    private TestLog testLog = TestLog.getInstance();

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

            testLog.sendToConsoleMessage("#TEST [class DataBaseService] URL:"+URL+" PASSWORD: "+PASSWORD+" USER: "+USER);
        } catch (IOException e) {
            testLog.sendToConsoleMessage("#TEST [class DataBaseService] [ERROR] get properties");
            e.printStackTrace();
        }
    }

    private Vector<Connection> availableConnections = new Vector<Connection>();
    private Vector<Connection> usedConnections = new Vector<Connection>();

    public synchronized Connection retrieve() throws SQLException {
        Connection newConnection = null;
        if (availableConnections.size() == 0) {
            newConnection = getConnection();
        } else {
            newConnection = (Connection) availableConnections.lastElement();
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
    public String getAvailableConnections() {
        return ("availableConnections: " + availableConnections.size() + ", usedConnections: " + usedConnections.size());
    }
}
