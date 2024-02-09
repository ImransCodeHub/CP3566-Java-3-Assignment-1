package Java3_A1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for managing the database operations for the Book and Author classes.
 * It uses JDBC to connect to the database and perform CRUD operations.
 * It also manages the relationships between books and authors.
 */
public class BookDatabaseManager {
    static final String JAVA_BOOKS_DB_URL = "jdbc:mariadb://localhost:3308/books?user=root&password=JavaImran2024";
    private Connection connection;

    /**
     * Constructor to create a new BookDatabaseManager and establish a connection to the database.
     */
    public BookDatabaseManager() {
        try {
            this.connection = DriverManager.getConnection(JAVA_BOOKS_DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create operations
    /**
     * Adds a new book to the database.
     * @param book The book to add to the database.
     */
    public void addBook(Book book) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO titles (isbn, title, editionNumber, copyright) VALUES (?, ?, ?, ?)");
            PreparedStatement stmt2 = connection.prepareStatement("INSERT INTO authorISBN (isbn, authorID) VALUES (?, ?)");
            stmt.setString(1, book.getIsbn());
            stmt.setString(2, book.getTitle());
            stmt.setInt(3, book.getEditionNumber());
            stmt.setString(4, book.getCopyright());
            stmt.executeUpdate();

            stmt2.setString(1, book.getIsbn());
            for (Author author : book.getAuthorList()) {
                stmt2.setInt(2, author.getAuthorID());
                stmt2.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new author to the database.
     * @param author The author to add to the database.
     */
    public void addAuthor(Author author) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO authors (firstName, lastName) VALUES (?, ?)");
            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update operations
    /**
     * Updates an existing book in the database.
     * @param book The book to update in the database.
     */
    public void updateBook(Book book) {
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE titles SET title = ?, editionNumber = ?, copyright = ? WHERE isbn = ?");
            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getEditionNumber());
            stmt.setString(3, book.getCopyright());
            stmt.setString(4, book.getIsbn());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No book with ISBN " + book.getIsbn() + " found.");
            } else {
                System.out.println("Book with ISBN " + book.getIsbn() + " updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an existing author in the database.
     * @param author The author to update in the database.
     */
    public void updateAuthor(Author author) {
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE authors SET firstName = ?, lastName = ? WHERE authorID = ?");
            stmt.setString(1, author.getFirstName());
            stmt.setString(2, author.getLastName());
            stmt.setInt(3, author.getAuthorID());
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No author with ID " + author.getAuthorID() + " found.");
            } else {
                System.out.println("Author with ID " + author.getAuthorID() + " updated successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete operations
    /**
     * Deletes a book from the database by its ISBN.
     * @param isbn The ISBN of the book to delete.
     */
    public void deleteBook(String isbn) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM titles WHERE isbn = ?");
            stmt.setString(1, isbn);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No book with ISBN " + isbn + " found.");
            } else {
                System.out.println("Book with ISBN " + isbn + " deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an author from the database by their ID.
     * @param authorID The ID of the author to delete.
     */
    public void deleteAuthor(int authorID) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM authors WHERE authorID = ?");
            stmt.setInt(1, authorID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.out.println("No author with ID " + authorID + " found.");
            } else {
                System.out.println("Author with ID " + authorID + " deleted successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Read operations

    // Operations for getting all books with their authors.
    /**
     * Retrieves all books from the database and populates the author list for each book.
     * @return A list of all books in the database. Each book will have a list of authors.
     */
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

            for (Book book : books) {
                List<Author> authors = getAuthorsForBook(book.getIsbn());
                book.setAuthorList(authors);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    /**
     * Retrieves all authors from the database and populates the book list for each author.
     * @return A list of all authors in the database. Each author will have a list of books.
     */
    private List<Author> getAuthorsForBook(String isbn) {
        List<Author> authors = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT authorID FROM authorISBN WHERE isbn = ?");
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int authorID = rs.getInt("authorID");
                Author author = getAuthorByID(authorID);
                authors.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    /**
     * Retrieves an author from the database by their ID.
     * @param authorID The ID of the author to retrieve.
     * @return The author with the specified ID, or null if no author is found.
     */
    private Author getAuthorByID(int authorID) {
        Author author = null;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM authors WHERE authorID = ?");
            stmt.setInt(1, authorID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                author = new Author(authorID, firstName, lastName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return author;
    }

    // Operations for getting all authors with their books.
    /**
     * Retrieves all authors from the database and populates the book list for each author.
     * @return A list of all authors in the database. Each author will have a list of books.
     */
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

            for (Author author : authors) {
                List<Book> books = getBooksForAuthor(author.getAuthorID());
                author.setBookList(books);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    /**
     * Retrieves all books associated with an author from the database.
     * @param authorID The ID of the author to retrieve books for.
     * @return A list of books associated with the author.
     */
    private List<Book> getBooksForAuthor(int authorID) {
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT isbn FROM authorISBN WHERE authorID = ?");
            stmt.setInt(1, authorID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                Book book = getBookByISBN(isbn);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    /**
     * Retrieves a book from the database by its ISBN.
     * @param isbn The ISBN of the book to retrieve.
     * @return The book with the specified ISBN, or null if no book is found.
     */
    private Book getBookByISBN(String isbn) {
        Book book = null;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM titles WHERE isbn = ?");
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String title = rs.getString("title");
                int editionNumber = rs.getInt("editionNumber");
                String copyright = rs.getString("copyright");
                book = new Book(isbn, title, editionNumber, copyright);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
}



