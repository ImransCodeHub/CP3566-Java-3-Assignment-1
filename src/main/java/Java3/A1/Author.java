package Java3.A1;
import java.sql.*;
import java.util.List;
import java.sql.Connection;

public class Author {

    public  String FirstName;
    public  String LastName;
    public  String authorID;
    private List<Book> bookList;

    public Author(String authorFirstName, String authorLastName, String authorID) {
        this.FirstName = authorFirstName;
        this.LastName = authorLastName;
        this.authorID = authorID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

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
