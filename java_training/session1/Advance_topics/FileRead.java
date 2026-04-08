import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileRead {
    public static void main(String[] args) {

        try {
            // Creates file object
            File file = new File("data.txt");

            //Creates Scanner to read file
            Scanner sc = new Scanner(file);

            //Reads file line by line
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                System.out.println(line);
            }

            //Close scanner
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }
}