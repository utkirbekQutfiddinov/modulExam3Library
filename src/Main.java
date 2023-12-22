import bean.ApiResponce;
import bean.BookBean;
import bean.UserBean;
import db.DB;
import resource.AuthResource;
import resource.BookResource;

import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scI = new Scanner(System.in);
    static Scanner scS = new Scanner(System.in);

    public static void main(String[] args) {
        showMenu();
    }

    private static void showMenu() {

        System.out.println("0. Exit");

        if (DB.session == null) {
            System.out.println("1. Register");
            System.out.println("2. Login");
        } else {
            System.out.println("3. Logout");

            if (DB.session.getRole().equals("reader")) {
                System.out.println("4. Get book");
                System.out.println("5. Return book");
            } else {
                System.out.println("6. Add book");
                System.out.println("7. Show books");
            }

        }


        int choice = scI.nextInt();

        switch (choice) {
            case 0 -> {
                return;
            }
            case 1 -> {
                register();
            }
            case 2 -> {
                login();
            }
            case 3 -> {
                DB.session = null;
            }
            case 4 -> {
                getBook();
            }
            case 5 -> {
                returnBook();
            }
        }

        showMenu();
    }

    private static void returnBook() {
        showMyBooks();
        System.out.println("Enter book id: ");
        int bookId = scI.nextInt();

        BookResource api = new BookResource();

        ApiResponce resp = api.returnBook(bookId);
        System.out.println(resp.getMessage());
    }

    private static void showMyBooks() {
        BookResource api = new BookResource();
        ApiResponce mybooks = api.getUsersBooks();

        List<BookBean> data = (List) mybooks.getData();

        System.out.println(mybooks.getMessage());

        if (data.size() <= 0) {
            System.out.println("There is no any available books in your library!");
            return;
        }

        for (BookBean book : data) {
            System.out.printf("Id: %d, name: %s, author: %s", book.getId(), book.getName(), book.getAuthor());
        }
    }

    private static void showAvailableBooks() {
        BookResource api = new BookResource();
        ApiResponce availableBooks = api.getAvailableBooks();

        List<BookBean> data = (List) availableBooks.getData();

        System.out.println(availableBooks.getMessage());

        if (data.size() <= 0) {
            System.out.println("There is no any available books in the library!");
            return;
        }

        for (BookBean book : data) {
            System.out.printf("Id: %d, name: %s, author: %s", book.getId(), book.getName(), book.getAuthor());
        }
    }

    private static void getBook() {
        showAvailableBooks();
        System.out.println("Enter book id: ");
        int bookId = scI.nextInt();

        BookResource api = new BookResource();

        ApiResponce resp = api.get(bookId);
        System.out.println(resp.getMessage());
    }


    private static void login() {
        System.out.println("Enter login: ");
        String login = scS.next();
        System.out.println("Enter password: ");
        String pass = scS.next();
        AuthResource auth = new AuthResource();
        ApiResponce reader = auth.login(new UserBean(login, pass));
        System.out.println(reader.getMessage());
        if (reader.getCode().equals(200)) {
            UserBean user = (UserBean) reader.getData();
            DB.session = user;
        }
    }

    private static void register() {
        System.out.println("Enter login: ");
        String login = scS.next();
        System.out.println("Enter password: ");
        String pass = scS.next();
        AuthResource auth = new AuthResource();
        ApiResponce reader = auth.register(new UserBean(login, pass, "reader"));
        System.out.println(reader.getMessage());
        if (reader.getCode().equals(200)) {
            UserBean user = (UserBean) reader.getData();
            DB.session = user;
        }
    }
}
