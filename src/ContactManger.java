import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactManger {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Path p = Paths.get("src", "contacts.txt");

        boolean keepGoing = false;

        System.out.println("What would you like to do?");

            System.out.println("1 - View Contacts.");
            System.out.println("2 - Add a new contact.");
            System.out.println("3 - Search a contact by name.");
            System.out.println("4 - Delete an existing contact.");
            System.out.println("5 - Exit.");

            int userInput = scanner.nextInt();

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
                    TerminalDisplay.error("Unknown Option: " + choice);
                    showMenu();
                    return;
            }
            showMenu();
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

