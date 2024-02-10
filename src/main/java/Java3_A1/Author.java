package Java3_A1;

import java.util.ArrayList;
import java.util.List;

/**
 * Author class with authorID, firstName, lastName, and bookList attributes
 */
public class Author {
    private int authorID;
    private String firstName;
    private String lastName;
    private List<Book> bookList;

    /**
     * Constructor for Author
     * @param authorID Author's authorID
     * @param firstName Author's firstName
     * @param lastName Author's lastName
     */
    public Author(int authorID, String firstName, String lastName) {
        this.authorID = authorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookList = new ArrayList<>();
    }

    /**
     * Getters and setters for Author attributes
     */
    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    /**
     * Adds a book to the author's book list
     * @param book Book to add
     */
    public void addBook(Book book) {
        bookList.add(book);
    }
}