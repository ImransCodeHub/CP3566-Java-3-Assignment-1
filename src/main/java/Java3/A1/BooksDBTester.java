package Java3.A1;

import java.io.PrintStream;
import java.sql.*;

public class BooksDBTester {

    public static void main(String[] args) {
        System.out.println("Fun with testing our Books DB");

        try(Connection conn = DriverManager.getConnection(BooksDBProperties.JAVA_BOOKS_DB_URL);
        ) {
            System.out.println("Print Authors Table");
            printAuthorsTable(conn);
            System.out.println("Print Titles Table");
            printTitlesTable(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Print the authors table
     * @param connection
     * @throws SQLException
     */
    public static void printAuthorsTable(Connection connection) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(BooksDBProperties.QUERY_ALL_AUTHORS);
        printResultSetIntoTable(rs);
    }

    /**
     * Print the titles table
     * @param connection
     * @throws SQLException
     */
    public static void printTitlesTable(Connection connection) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(BooksDBProperties.QUERY_ALL_TITLES);
        printResultSetIntoTable(rs);
    }

    /**
     * Print the result set into a table
     * @param rs
     * @throws SQLException
     */
    private static void printResultSetIntoTable(ResultSet rs) throws SQLException{
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int count = rsMetaData.getColumnCount();
        for(int i = 1; i<=count; i++) {
            //TODO resize tables
            System.out.printf("%-20s ", rsMetaData.getColumnName(i));
        }
        System.out.println();

        while (rs.next()) {
            for (int i = 1; i<=count; i++) {
                System.out.printf("%-20s ", rs.getString(rsMetaData.getColumnName(i)));
            }
            System.out.println();
        }
    }

}


