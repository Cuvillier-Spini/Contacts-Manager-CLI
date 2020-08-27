import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ContactManagerApplication {
    private static final String contactsFormat = "| %-10s | %10s |\n";

    public static void main(String[] args) {
        welcome();
        showMenu();

    }

    public static void error(String message) {
        System.err.println("An error occured!");
        System.out.println("ERROR: " + message);
    }

//    public static void error() {
//        error("An unknown error occured");
//    }

    public static void welcome() {
        System.out.println("///////");
        System.out.println("| 0  0|");
        System.out.println("|  ^  |");
        System.out.println("|  U  |");
        System.out.println("|_____|");
        System.out.println();
        System.out.println("To the contacts manager!");
        System.out.println();
    }

    public static void showMenu() {
        System.out.println();
        System.out.println("0 - Exit");
        System.out.println("1 - View Contacts");
        System.out.println("2 - Add A Contact");
        System.out.println("3 - Search Contacts");
        System.out.println("4 - Remove A Contact");
        System.out.println();
        System.out.print("Your choice? ");

    }

    public static void showContacts(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("No Contacts Found.");
            return;
        }
        System.out.printf(contactsFormat, "Name", "Number");
        System.out.printf(contactsFormat, "---------", "--------");
        for (Contact contact : contacts) {
            System.out.printf(contactsFormat, contact.getName(), contact.getNumber());
        }
    }

    public static void goodbye() {
        System.out.println("+~~~~~~~~~~~~~~~~~~");
        System.out.println("| Have a great day!");
        System.out.println("+~~~~~~~~~~~~~~~~~~");
        System.out.println("Goodbye!");
        System.out.println();
    }



    public static class CliContactsApplication {
        private final Scanner scanner;
        private final ContactsDao dao;

        public CliContactsApplication(Scanner scanner, ContactsDao dao) {
            this.scanner = scanner;
            this.dao = dao;
        }

        public void start() {
            ContactManagerApplication.welcome();
            showMenu();
        }

        private void showMenu() {
            ContactManagerApplication.showMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "0" -> exit();
                case "1" -> viewAllContacts();
                case "2" -> addContact();
                case "3" -> searchContacts();
                case "4" -> removeContact();
                default -> {
                    ContactManagerApplication.error("Unknown Option: " + choice);
                    showMenu();
                    return;
                }
            }
            showMenu();
        }

        private void removeContact() {
            System.out.print("Contact name to remove? ");
            String name = scanner.nextLine();
            List<Contact> results = dao.search(name);
            for (Contact contact : results) {
                System.out.printf("Are you sure you want to remove %s? [y/N]", contact.getName());
                String confirmation = scanner.nextLine();
                if (confirmation.toLowerCase().startsWith("y")) {
                    dao.remove(contact);
                    System.out.println("Contact Removed!");
                } else {
                    System.out.println("Contact not removed.");
                }
            }
        }

        private void searchContacts() {
            System.out.print("Search Term? ");
            String searchTerm = scanner.nextLine();
            List<Contact> results = dao.search(searchTerm);
            ContactManagerApplication.showContacts(results);
        }

        private void addContact() {
            System.out.print("Name? ");
            String name = scanner.nextLine();
            System.out.print("Number? ");
            String number = scanner.nextLine();

            dao.add(new Contact(name, number));
            System.out.println("Contact Added!");
        }

        private void viewAllContacts() {
            ContactManagerApplication.showContacts(dao.all());
        }

        private void exit() {
            ContactManagerApplication.goodbye();
            System.exit(0);
        }

    }

    public interface ContactsDao {
        List<Contact> all();
        List<Contact> search(String searchTerm);
        void add(Contact contact);
        void remove(Contact contact);
    }
        public static class ContactsFromFile implements ContactsDao {
        private static final String contactsFile = "contacts.txt";

        private Contact fromLine(String line) {
            String[] parts = line.split("\\|");
            return new Contact(parts[0], parts[1]);
        }

        private String toLine(Contact contact) {
            return String.format("%s|%s", contact.getName(), contact.getNumber());
        }

        private void writeToFile(List<Contact> contacts) {
            List<String> lines = new ArrayList<>();
            for (Contact contact : contacts) {
                lines.add(toLine(contact));
            }
            FileHelper.spit(contactsFile, lines);
        }

        @Override
        public List<Contact> all() {
            List<String> lines = FileHelper.slurp(contactsFile);
            List<Contact> contacts = new ArrayList<>();
            for (String line : lines) {
                contacts.add(fromLine(line));
            }
            return contacts;
        }

        @Override
        public List<Contact> search(String searchTerm) {
            List<Contact> searchResults = new ArrayList<>();
            for (Contact contact : all()) {
                boolean isMatch = contact.getName().toLowerCase().contains(searchTerm.toLowerCase());
                if (isMatch) {
                    searchResults.add(contact);
                }
            }
            return searchResults;
        }

        @Override
        public void add(Contact contact) {
            List<Contact> contacts = all();
            contacts.add(contact);
            writeToFile(contacts);
        }

        @Override
        public void remove(Contact contact) {
            List<Contact> contacts = all();
            contacts.remove(contact);
            writeToFile(contacts);
        }
    }

    public static class FileHelper {
        public static List<String> slurp(String filepath) {
            Path path = Paths.get(filepath);
            try {
                return Files.readAllLines(path);
            } catch (IOException e) {
                System.out.printf("Error when trying to slurp %s: %s\n", filepath, e.getMessage());
                System.exit(1);
                return new ArrayList<>();
            }
        }

        public static void spit(String filename, List<String> contents) {
            spit(filename, contents, false);
        }
        public static void spit(String filename, List<String> contents, boolean append) {
            StandardOpenOption option = append ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING;
            Path path = Paths.get(filename);

            if (! Files.exists(path)) {
                try {
                    Files.createDirectories(path);
                    Files.createFile(path);
                } catch (IOException e) {
                    System.out.printf("Error creating file %s: %s\n", path, e.getMessage());
                    System.exit(1);
                }
            }

            try {
                Files.write(path, contents, option);
            } catch (IOException e) {
                System.out.printf("Error writing contents to %s: %s\n", filename, e.getMessage());
                System.exit(1);
            }
        }
    }
}