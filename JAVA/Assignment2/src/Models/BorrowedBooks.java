package Models;

public class BorrowedBooks {
    public int id;
    public int book_id;
    public int borrower_id;
    public String borrow_date;
    public String return_date;

    public BorrowedBooks(int id, int book_id, int borrower_id,
                         String borrow_date, String return_date) {
        this.id = id;
        this.book_id = book_id;
        this.borrower_id = borrower_id;
        this.borrow_date = borrow_date;
        this.return_date = return_date;
    }

    public BorrowedBooks(int book_id, int borrower_id,
                         String borrow_date, String return_date) {
        this.book_id = book_id;
        this.borrower_id = borrower_id;
        this.borrow_date = borrow_date;
        this.return_date = return_date;
    }

  public String toString(){
        return this.id + " " + this.book_id + " " + this.borrower_id + " "
                + this.borrow_date + " " + this.return_date ;
  }
}
