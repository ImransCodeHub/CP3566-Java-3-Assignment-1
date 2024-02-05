package Java3.A1;
import java.sql.*;
import java.util.List;
import java.sql.Connection;

public class Author {

    public  String authorName;
    private List<Book> bookList;

    public static void main(String[] args) {
        System.out.println("All the authors");

        try(Connection conn = DriverManager.getConnection(BooksDBProperties.JAVA_BOOKS_DB_URL);
        ) {
            System.out.println("Print Authors");
            printAuthors(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printAuthors(Connection conn)  throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(BooksDBProperties.QUERY_ALL_AUTHORS);
        printResultSetIntoTable(rs);

    }

    public static void printResultSetIntoTable(ResultSet rs) throws SQLException{
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
