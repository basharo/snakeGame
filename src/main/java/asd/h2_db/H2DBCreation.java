package asd.h2_db;

import java.sql.*;

public class H2DBCreation {

    private static org.apache.log4j.Logger dbLogger = org.apache.log4j.Logger.getLogger(H2DBCreation.class);
    private static String tableSQL = "CREATE TABLE IF NOT EXISTS LOG "
            + " (id INTEGER auto_increment,"
            + " class VARCHAR(255), "
            + " text VARCHAR (255), "
            + " PRIMARY KEY (id))";


    /*public static void main(String[] args) throws Exception {
        Connection connection = getDB();
        Statement stmt = tableCreation(tableSQL, connection);
        //insertIntoTable(insertSQL);
        //displayDBEntries(stmt, displaySQL);
    }*/

    public H2DBCreation(){

    }

    private static Connection getDB() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName("org.h2.Driver").newInstance();
        Connection con = DriverManager.getConnection("jdbc:h2:" + "./Database/snakeLogDB", "root", "password");
        dbLogger.info("Connection to DB successful");
        return con;
    }

    private static Statement tableCreation(String sqlCommand, Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        stmt.executeUpdate(sqlCommand);
        dbLogger.info("Table created");
        return stmt;
    }

    public void insertIntoTable(String className, String text) throws Exception {

        String insertSQL = "INSERT INTO LOG (class,text) VALUES('"+className+"','"+text+"'),"
                + "('testClass2','testText2')";
        Statement stmt = tableCreation(tableSQL, getDB());
        int i = stmt.executeUpdate(insertSQL);
        dbLogger.info(i + "inserted ");

        displayDBEntries(stmt);
    }

    private static void displayDBEntries(Statement stmt) throws SQLException {

        String displaySQL = "SELECT * FROM LOG;";

        ResultSet rs = stmt.executeQuery(displaySQL);
        while (rs.next()) {
            dbLogger.info(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3));
        }

    }
}
