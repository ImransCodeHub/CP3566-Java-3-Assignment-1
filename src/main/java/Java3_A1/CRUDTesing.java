package Java3_A1;


public class CRUDTesing {
    public static void main(String[] args) {
        try{
            BookDatabaseManager bookDatabaseManager = new BookDatabaseManager();
            BookApplication bookApplication = new BookApplication();

            //create a book
            Book bookTest = new Book("00000000008", "CRUD-TEST", 1, "2021");

            //Create an author
            Author authorTest = new Author(999, "IMRAN", "MOIN");

            //Add a book
            System.out.println("++++++++++++++++++++++ ADDED A BOOK ++++++++++++++++++++++");
            bookDatabaseManager.addBook(bookTest);
            bookDatabaseManager.getAllBooks();
            bookApplication.printAllBooks();

            //Add an author
            System.out.println("++++++++++++++++++++++ ADDED A AUTHOR ++++++++++++++++++++++");
            bookDatabaseManager.addAuthor(authorTest);
            bookDatabaseManager.getAllAuthors();
            bookApplication.printAllAuthors();

            //Update a book
            System.out.println("++++++++++++++++++++++ UPDATED A BOOK ++++++++++++++++++++++");
            Book bookTestUpdate = new Book("00000000008", "CRUD-TEST-UPDATED", 1, "2021");
            bookDatabaseManager.updateBook(bookTestUpdate);
            bookDatabaseManager.getAllBooks();
            bookApplication.printAllBooks();

            //Update an author
            System.out.println("++++++++++++++++++++++ UPDATED A AUTHOR ++++++++++++++++++++++");
            Author authorTestUpdate = new Author(999, "IMRAN Updated", "MOIN Updated");
            bookDatabaseManager.updateAuthor(authorTestUpdate);
            bookDatabaseManager.getAllAuthors();
            bookApplication.printAllAuthors();

            //Delete a book
            System.out.println("++++++++++++++++++++++ DELETED A BOOK ++++++++++++++++++++++");
            bookDatabaseManager.deleteBook(bookTest.getIsbn());
            bookDatabaseManager.getAllBooks();
            bookApplication.printAllBooks();

            //Delete an author
            System.out.println("++++++++++++++++++++++ DELETED A AUTHOR ++++++++++++++++++++++");
            bookDatabaseManager.deleteAuthor(authorTest.getAuthorID());
            bookDatabaseManager.getAllAuthors();
            bookApplication.printAllAuthors();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
