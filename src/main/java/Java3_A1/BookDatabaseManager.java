package Java3_A1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3308/books";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "JavaImran2024";

    private Connection connection;

    public BookDatabaseManager() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle connection failure
        }
    }

    // Other methods for CRUD operations, managing relationships, etc.

    public void addBook(Book book) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO titles (isbn, title, editionNumber, copyright) VALUES (?, ?, ?, ?)");
            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setInt(3, book.getEditionNumber());
            stmt.setString(4, book.getCopyright());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    public void addAuthor(Author author) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO authors (firstName, lastName) VALUES (?, ?)");
            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM titles");
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                int editionNumber = rs.getInt("editionNumber");
                String copyright = rs.getString("copyright");
                Book book = new Book(isbn, title, editionNumber, copyright);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return books;
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM authors");
            while (rs.next()) {
                int authorID = rs.getInt("authorID");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                Author author = new Author(authorID, firstName, lastName);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
        return authors;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }
}



