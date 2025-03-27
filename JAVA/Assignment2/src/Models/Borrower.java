package Models;

public class Borrower {
    public int id;
    public String name;
    public String email;

    public Borrower(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Borrower(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String toString() {
        return id + " " + name + " " + email;
    }
}
