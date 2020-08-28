import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class ContactManagerApplication {
    static Hashtable<String, Contact> phonebook;

    public static void main(String[] args) {

        phonebook = readList();
        int ch;
        char con = 'y';
        Scanner sc = new Scanner(System.in);

        while (con == 'y') {
            showMenu();
            System.out.println("Enter your choice:");
            ch = sc.nextInt();
            switch (ch) {
                case 1:
                    viewAll();
                    break;
                case 2:
                    addToPhoneBook();
                    break;
                case 3:
                    searchByName();
                    break;
                case 4:
                    deleteFromPhonebook();
                    break;
                case 5:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
                    break;
            }

            try {
                InputStreamReader isr = new InputStreamReader(System.in);
                System.out.println("Press y to continue:");
                con = (char) isr.read();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    public static void showMenu() {
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
    }


    public static void viewAll() {
        if (phonebook != null) {
            for (Enumeration<String> e = phonebook.keys(); e.hasMoreElements(); ) {
                Contact entry = phonebook.get(e.nextElement());
                entry.printInfo();
            }
        }
    }

    public static void addToPhoneBook() {
        if (phonebook == null) phonebook = new Hashtable<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter name:");
            String name = br.readLine();
            System.out.println("Enter phone number:");
            String phone = br.readLine();
            Contact st = new Contact(name, phone);
            phonebook.put(name, st);
            writeIt(phonebook);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void searchByName() {
        if (phonebook != null) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Search by name:");
                String key = br.readLine();
                Contact cu = phonebook.get(key);
                if (cu != null)
                    cu.printInfo();

                else
                    System.out.println("Not found");
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    public static void deleteFromPhonebook() {
        if (phonebook != null) {
            int si = phonebook.size();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Name:");
                String key = br.readLine();
                phonebook.remove(key);
                if (phonebook.size() < si) {
                    writeIt(phonebook);
                    System.out.println("The contact has been deleted.");
                } else
                    System.out.println("Wrong name");
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    public static void writeIt(Hashtable<String, Contact> obj) {
        try {
            Path p = Paths.get("directory.txt");
            ArrayList<String> something = new ArrayList<>();
            obj.forEach((k, v) -> something.add(String.format("%s:%s", v.getName(), v.getNumber())));
            Files.write(p, something);
//            FileOutputStream fos=new FileOutputStream("directory.txt");
//            ObjectOutputStream oos=new ObjectOutputStream(fos);
//            oos.writeObject(obj);
//            oos.flush();
//            oos.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

//        public static Hashtable<String, Contact> readList() {
////    public static Hashtable<String, Contact> readList(Hashtable<String, Contact> obj) {
//        Hashtable<String, Contact> phonebook = null;
//        try {
////            Path p = Paths.get("directory.txt");
////            ArrayList<String> something = new ArrayList<>();
////            obj.forEach((k, v) -> something.remove(String.format("%s:%s", v.getName(), v.getNumber())));
////            Files.write(p, something);
//
//
//            FileInputStream fis=new FileInputStream("directory.txt");
//            ObjectInputStream ois=new ObjectInputStream(fis);
//            phonebook=(Hashtable<String,Contact>)ois.readObject();
//            ois.close();
//
//        } catch (Exception ie) {
//            ie.printStackTrace();
//        }
//        return phonebook;

    public static Hashtable<String, Contact> readList() {
        Hashtable<String, Contact> phonebook = null;
        try {
            FileInputStream fis = new FileInputStream("directory.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            phonebook = (Hashtable<String, Contact>) ois.readObject();
            ois.close();
        } catch (Exception ie) {
            ie.printStackTrace();
        }
        return phonebook;
    }
}