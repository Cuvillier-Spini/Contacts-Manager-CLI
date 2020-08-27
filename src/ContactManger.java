import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ContactManger {
    public static void main(String[] args) {
        Path p = Paths.get("src","contacts.txt");
        List<Contact> contacts;
        List<String> names = new ArrayList<>();

        try {
            names = Files.readAllLines(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(names);
    }
}
