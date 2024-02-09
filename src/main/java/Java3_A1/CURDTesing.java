package Java3_A1;


public class CURDTesing {
    public static void main(String[] args) {
        try{
            BookDatabaseManager bookDatabaseManager = new BookDatabaseManager();
            BookApplication bookApplication = new BookApplication();

            //create a book
            Book bookTest = new Book("00000000008", "CURD-TEST", 1, "2021");

            //Create an author
            Author authorTest = new Author(999, "Joe", "Dave");

            //Add a book
            bookDatabaseManager.addBook(bookTest);
            bookDatabaseManager.getAllBooks();
            bookApplication.printAllBooks();

            //Add an author
            bookDatabaseManager.addAuthor(authorTest);
            bookDatabaseManager.getAllAuthors();
            bookApplication.printAllAuthors();

            //Update a book
            Book bookTestUpdate = new Book("00000000008", "CURD-TEST-UPDATED", 1, "2021");
            bookDatabaseManager.updateBook(bookTestUpdate);
            bookDatabaseManager.getAllBooks();
            bookApplication.printAllBooks();

            //Update an author
            Author authorTestUpdate = new Author(999, "Joe Updated", "Dave Updated");
            bookDatabaseManager.updateAuthor(authorTestUpdate);
            bookDatabaseManager.getAllAuthors();
            bookApplication.printAllAuthors();

            //Delete a book
            bookDatabaseManager.deleteBook(bookTest.getIsbn());
            bookDatabaseManager.getAllBooks();
            bookApplication.printAllBooks();

            //Delete an author
            bookDatabaseManager.deleteAuthor(authorTest.getAuthorID());
            bookDatabaseManager.getAllAuthors();
            bookApplication.printAllAuthors();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
