package Java3.A1;


import java.sql.*;
import java.util.List;

public class Book {
    public Book(String isbn, String title, String editionNumber, String copyright) {
        this.isbn = isbn;
        this.title = title;
        this.editionNumber = editionNumber;
        this.copyright = copyright;
    }

    public String isbn;
    public String title;
    public String editionNumber;
    public String copyright;
    private List<Author> authorList;

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getEditionNumber() {
        return editionNumber;
    }

    public String getCopyRight() {
        return copyright;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public static void main(String[] args) {
        System.out.println("All the books");

        try(Connection conn = DriverManager.getConnection(BooksDBProperties.JAVA_BOOKS_DB_URL);
        ) {
            System.out.println("Print Books");
            printBooks(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void printBooks(Connection connection) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(BooksDBProperties.QUERY_ALL_TITLES);
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
