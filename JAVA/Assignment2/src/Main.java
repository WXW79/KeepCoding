import Services.LibraryService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] menu = {   "1. Add a new book",
                            "2. List all books",
                            "3. Update book details",
                            "4. Delete a book",
                            "5. Add a new borrower",
                            "6. List all borrowers",
                            "7. Borrow a book",
                            "8. Return a book",
                            "9. View all borrowed books",
                            "0. Exit"
                        };

        for(String menuItem : menu) {
            System.out.println(menuItem);
        }

        int action = 10;
        while(action != 0){
            System.out.print("Enter your action choice: ");
            action = scanner.nextInt();
            switch (action) {
                case 1:
                    LibraryService.addABook(scanner);
                    break;
                case 2:
                    LibraryService.listAllBooks();
                    break;
                case 3:
                    LibraryService.updateBook(scanner);
                    break;
                case 4:
                    LibraryService.deleteBook(scanner);
                    break;
                case 5:
                    LibraryService.addBorrower(scanner);
                    break;
                case 6:
                    LibraryService.listAllBorrowers();
                    break;
                case 7:
                    LibraryService.borrowABook(scanner);
                    break;
                case 8:
                    LibraryService.returnABook(scanner);
                    break;
                case 9:
                    LibraryService.viewAllBorrowedBooks();
                    break;
                  case 0:
                      System.out.println("Goodbye!");
                      break;
                default:
                    System.out.println("Invalid choice");
            }
        };

        scanner.close();
    }
}