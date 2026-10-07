import java.util.ArrayList;
import java.util.List;

public class Main {
    public class Account {
        protected String email;
        protected String password;
        protected String role;

        public Account(String email, String password, String role) {
            this.email = email;
            this.password = password;
            this.role = role;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }

        @Override
        public String toString() {
            return "Account{" +
                    "email='" + email + '\'' +
                    ", role='" + role + '\'' +
                    '}';
        }
    }

    public class Customer extends Account {
        private BookingManager bookingManager;

        public Customer(String email, String password) {
            super(email, password, "Customer");
        }

        public void setBookingManager(BookingManager bookingManager) {
            this.bookingManager = bookingManager;
        }

        public BookingManager getBookingManager() {
            return bookingManager;
        }
    }

    public class BookingManager {
        public void showBookingMenu() {
            System.out.println("BookingManager is ready for booking.");
        }
    }

    public class Authen {
        private List<Account> accounts;
        private BookingManager bookingManager;

        public Authen(BookingManager bookingManager) {
            this.bookingManager = bookingManager;
            this.accounts = new ArrayList<>();

            // ข้อมูลตัวอย่างในระบบ
            accounts.add(new Customer("user@gmail.com", "1234"));
            accounts.add(new Account("admin@gmail.com", "9999", "Admin"));
        }

        public Account logIn(String email, String password) {
            Account account = haveAccount(email, password);

            if (account != null) {
                if (account instanceof Customer) {
                    Customer customer = (Customer) account;
                    customer.setBookingManager(bookingManager);
                }
                return account;
            }

            return null;
        }

        private Account haveAccount(String email, String password) {
            for (Account account : accounts) {
                if (account.getEmail().equals(email) &&
                    account.getPassword().equals(password)) {
                    return account;
                }
            }
            return null;
        }
    }
    public static void main(String[] args) {
        BookingManager bookingManager = new BookingManager();
        Authen authen = new Authen(bookingManager);

        String email = "user@gmail.com";
        String password = "1234";

        Account account = authen.logIn(email, password);

        if (account != null) {
            System.out.println("Login successful");
            System.out.println(account);

            if (account instanceof Customer) {
                Customer customer = (Customer) account;
                customer.getBookingManager().showBookingMenu();
            }
        } else {
            System.out.println("Login failed: invalid email or password");
        }
    }
}
