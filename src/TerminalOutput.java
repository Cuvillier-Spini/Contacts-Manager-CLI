import org.w3c.dom.ls.LSOutput;
    import java.util.List;

    /**
     * A static class to handle displaying information in the terminal
     */
    public class TerminalOutput {
//        private static final String contactsFormat = "| %-10s | %10s |\n";

        public static void error(String message) {
            System.err.println("An error occured!");
            System.out.println("ERROR: " + message);
        }
        public static void error() {
            error("An unknown error occured");
        }

        public static void welcome() {
            System.out.println("///////");
            System.out.println("| 0  0|");
            System.out.println("|  ^  |");
            System.out.println("|  U  |");
            System.out.println("|_____|");
            System.out.println("");
            System.out.println("To the contacts manager!");
            System.out.println("");
        }

        public static void showMenu() {
            System.out.println("");
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

    }
}
