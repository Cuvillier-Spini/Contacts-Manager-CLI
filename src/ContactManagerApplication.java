import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

public class ContactManagerApplication {
    static Hashtable<String,contact> phonebook;
    public static void main(String[] args){

        phonebook=readList(); //read phonebook
        int ch;
        char con='y';
        Scanner sc=new Scanner(System.in); //create scanner object to receive choice input

        while(con=='y'){
            showMenu(); //show menu
            System.out.println("Enter your choice:");
            ch=sc.nextInt();
            switch (ch) {
                case 1 -> viewAll();
                case 2 -> addToPhoneBook();
                case 3 -> searchByName();
                case 4 -> deleteFromPhonebook();
                case 5 -> System.exit(0);
                default -> System.out.println("Invalid choice");
            }

            try{
                //prompt for continuing the program
                InputStreamReader isr=new InputStreamReader(System.in);
                System.out.println("Press y to continue:");
                con=(char)isr.read();
            }catch(IOException ie) {
                ie.printStackTrace();
            }
        }
    }
    //This method display options menu
    public static void showMenu() {
        System.out.println("1. View all phonebook entries");
        System.out.println("2. Add to phonebook");
        System.out.println("3. Find an entry");
        System.out.println("4. Remove from phonebook");
        System.out.println("5. Exit");
    }

    //The viewAll method displays all entries in the phonebook
    public static void viewAll(){
        if(phonebook!=null){
            for(Enumeration<String> e=phonebook.keys(); e.hasMoreElements();){
                contact entry=phonebook.get(e.nextElement());
                entry.printInfo();
            }

        }

    }

    //The addToPhoneBook method is able to add each entry to the phonebook
    public static void addToPhoneBook(){
        //If the phonebook null, allocate memory for it so it is ready to get the new item
        if(phonebook==null) phonebook= new Hashtable<>();
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter name:");
            String name=br.readLine();
            System.out.println("Enter phone:");
            String phone=br.readLine();
            contact st=new contact(name,phone);
            phonebook.put(name,st); //add new entry to the phonebook
            writeIt(phonebook); //save the update phonebook
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    //The deleteFromPhonebook method is able to delete an entry when the name
    //is correctly input
    public static void deleteFromPhonebook(){
        if(phonebook!=null){
            int si=phonebook.size(); //number of entries in the phonebook before an entry is removed
            try{
                BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Name:");
                String key=br.readLine();
                phonebook.remove(key); //remove the contact
                if(phonebook.size()<si){ //removing is successful
                    writeIt(phonebook);
                    System.out.println("The entry has been deleted.");
                }
                else
                    System.out.println("Wrong name");
            }catch(IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    //The searchByName method has code to find a phonebook entry by name in the list
    public static void searchByName(){
        if(phonebook!=null){
            try{
                BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Search by name:");
                String key=br.readLine();
                contact cu=phonebook.get(key);
                if(cu!=null)
                    cu.printInfo();

                else
                    System.out.println("Not found");
            }catch(IOException ie) {
                ie.printStackTrace();
            }
        }
    }

    //Write the Hashtable object representing the phonebook to the file
    public static void writeIt(Hashtable<String,contact> obj){
        try{
            FileOutputStream fos=new FileOutputStream("directory.txt");
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
        }catch(IOException ie) {
            ie.printStackTrace();
        }

    }

    //The readList method has code to read phonebook from the file
    public static Hashtable<String,contact> readList(){
        Hashtable<String,contact> phonebook=null;
        try{
            FileInputStream fis=new FileInputStream("directory.txt");
            ObjectInputStream ois=new ObjectInputStream(fis);
            phonebook=(Hashtable<String,contact>)ois.readObject();
            ois.close();

        }catch(Exception ie) {
            ie.printStackTrace();
        }
        return phonebook;
    }
}