import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;


public class ContactMangerApplication {
    private static Scanner scanner;
    private final ContactTemple temple;

    public ContactMangerApplication(Scanner scanner, ContactTemple temple) {
        this.scanner = scanner;
        this.temple = temple;
    }

    static void main(String[] args) {
        Path p = Paths.get("src", "contacts.txt");

        boolean keepGoing = false;

//        System.out.println("What would you like to do?");
//
//            System.out.println("1 - View Contacts.");
//            System.out.println("2 - Add a new contact.");
//            System.out.println("3 - Search a contact by name.");
//            System.out.println("4 - Delete an existing contact.");
//            System.out.println("5 - Exit.");

//            String userInput = scanner.nextLine(0,4);


        public void start() {
            TerminalOutput.welcome();
            showMenu();
        }

        private void showMenu() {
            TerminalOutput.showMenu();
            String userInput = scanner.nextLine()

            switch(userInput) {

                case "1":
                    viewAllContacts();
                    break;
                case "2":
                    addContact();
                    break;
                case "3":
                    searchContacts();
                    break;
                case "4":
                    removeContact();
                    break;
                default:
                    TerminalOutput.error("Unknown Option: " + choice);
                    showMenu();
                    return;
            }
            showMenu();
        }
    private void removeContact() {
        System.out.print("Contact name to remove? ");
        String name = scanner.nextLine();
        List<Contact> results = ContactTemple.search(name);
        for (Contact contact : results) {
            System.out.printf("Are you sure you want to remove %s? [y/N]", contact.getName());
            String confirmation = scanner.nextLine();
            if (confirmation.toLowerCase().startsWith("y")) {
                ContactTemple.remove(contact);
                System.out.println("Contact Removed!");
            } else {
                System.out.println("Contact not removed.");
            }
        }
    }

    private void searchContacts() {
        System.out.print("Search Term? ");
        String searchTerm = scanner.nextLine();
        List<Contact> results = ContactTemple.search(searchTerm);
        TerminalOutput.showContacts(results);
    }

    private void addContact() {
        System.out.print("Name? ");
        String name = scanner.nextLine();
        System.out.print("Number? ");
        String number = scanner.nextLine();

        ContactTemple.add(new Contact(name, number));
        System.out.println("Contact Added!");
    }

    private void viewAllContacts() {
        TerminalOutput.showContacts(ContactTemple.all());
    }




        //            List<Contact> contacts = null;
//            List<String> names = new ArrayList<>();
//
//            try {
//                names = Files.readAllLines(p);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(names);

    }

