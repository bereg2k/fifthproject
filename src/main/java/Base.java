import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Base {
    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("text.txt");
        Scanner scannerToPrint = new Scanner(file);

        System.out.println("Here's the contents of your file \"" + file.getAbsolutePath() + "\":\n");
        System.out.print("\"");
        while (scannerToPrint.hasNextLine()) {
            String line = scannerToPrint.nextLine();
            System.out.print(line);
            if (!scannerToPrint.hasNextLine()) //elegant way to close the end of the text file with another "-sign
                System.out.println("\"");
            else
                System.out.println();
        }
        scannerToPrint.close();

        Scanner scannerToRead = new Scanner(file);
        Set<String> wordsUnique = new TreeSet<>();
        Map<String, Integer> wordsQuantity = new HashMap<>();

        while (scannerToRead.hasNext()) {
            String word = scannerToRead.useDelimiter("(\\s+|\\.\\s*|,\\s*)").next();
            Integer count = wordsQuantity.get(word);
            if (count == null) {
                count = 0;
            }
            wordsQuantity.put(word, ++count);
            wordsUnique.add(word);
        }

        System.out.println("\nHere's the list with all the unique words used in the file (in alphabet order): ");
        System.out.println(wordsUnique);

        System.out.println("\nHere's the list for all the words and their respective quantity in the file: ");
        System.out.println(wordsQuantity);

        System.out.println();

        int maxQuantity = 1;
        String maxWord = null;

        for (Object o : wordsQuantity.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            if (maxQuantity <= (int) pair.getValue()) {
                maxQuantity = (int) pair.getValue();
                maxWord = (String) pair.getKey();
            }
        }

        int maxCount = 0;
        List<String> maxWordList = new ArrayList<>();
        for (Object o : wordsQuantity.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            if (maxQuantity == (int) pair.getValue()) {
                maxWordList.add((String) pair.getKey());
                maxCount++;
            }
        }

        if (maxCount >= 2) {
            System.out.println("There's more than one most frequent word in the file!");
            System.out.println("These words are " + maxWordList);
            System.out.println("Each of them can be found " + maxQuantity + " times there.");

        } else {
            System.out.println("The most frequent word in the file is \"" + maxWord + "\".");
            System.out.println("It can be found " + maxQuantity + " times there.");
        }
        scannerToRead.close();
    }
}
