package Java3.A1;

import java.sql.*;
import java.util.*;


public class BookDatabaseManager {
    private Connection connection;

    public static void main(String[] args) {
        System.out.println("All the books");

        try(Connection conn = DriverManager.getConnection(BooksDBProperties.JAVA_BOOKS_DB_URL);
        ) {
            BookDatabaseManager bookDatabaseManager = new BookDatabaseManager(BooksDBProperties.JAVA_BOOKS_DB_URL);

            //Add a book
            Book book = new Book("1238888999", "The Book exist", "2", "2021");
            bookDatabaseManager.addBook(book);
            System.out.println("Print All Books");
            bookDatabaseManager.getAllBooks();

            //Add an author
            //Author author = new Author("John", "Doe", "123456");
           // bookDatabaseManager.addAuthor(author);
            //System.out.println("Print All Authors");
            //bookDatabaseManager.getAllAuthors();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BookDatabaseManager(String url) throws SQLException {
        this.connection = DriverManager.getConnection(BooksDBProperties.JAVA_BOOKS_DB_URL);
    }

    // Create operations



    public void addBook(Book book) {
        try {
            // Check if the book already exists in the database
            PreparedStatement checkStmt = connection.prepareStatement("SELECT * FROM titles WHERE isbn = ?");
            checkStmt.setString(1, book.getIsbn());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("Book already exists in the database");
                return; // Exit the method if the book already exists
            }

            // Insert the book into the database
            PreparedStatement insertStmt = connection.prepareStatement("INSERT INTO titles (isbn, title, editionNumber, copyright) VALUES (?, ?, ?, ?)");
            insertStmt.setString(1, book.getIsbn());
            insertStmt.setString(2, book.getTitle());
            insertStmt.setString(3, book.getEditionNumber());
            insertStmt.setString(4, book.getCopyRight());
            insertStmt.executeUpdate();

            // Close resources
            rs.close();
            checkStmt.close();
            insertStmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void addAuthor(Author author) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO authors (authorID, firstName, lastName)"
                    + "VALUES (?, ?, ?)");
            stmt.setString(1, author.getAuthorID());
            stmt.setString(2, author.getFirstName());
            stmt.setString(3, author.getLastName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Read operations
    public void getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(BooksDBProperties.QUERY_ALL_TITLES);
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String editionNumber = rs.getString("editionNumber");
                String copyRight = rs.getString("copyRight");
                Book book = new Book(isbn, title, editionNumber, copyRight);
                books.add(book);
                printResultSetIntoTable(rs);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllAuthors() {
        List<Author> authors = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(BooksDBProperties.QUERY_ALL_AUTHORS);
            while (rs.next()) {
                String authorID = rs.getString("authorID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                Author author = new Author(firstName, lastName, authorID);
                authors.add(author);
                printResultSetIntoTable(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Update operations
    public void updateBook(Book book) {
        // Implement updating a book in the database
    }

    public void updateAuthor(Author author) {
        // Implement updating an author in the database
    }

    // Delete operations
    public void deleteBook(int id) {
        // Implement deleting a book from the database
    }

    public void deleteAuthor(int id) {
        // Implement deleting an author from the database
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
