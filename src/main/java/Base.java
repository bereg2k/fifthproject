import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This program reads a text file from the program's directory.
 * It prints its contents on the console.
 * Then it presents all the words used in the file in alphabet order.
 * Then it presents all the words in the file with their respective quantity.
 * Finally it finds and prints the most frequent word (or words) in the file.
 */
public class Base {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in); //console input
        File file = null; //variable for the future text file
        int fileChoice; //variable for user's choice of different files

        System.out.println("Please choose the file to work with.");
        System.out.println("- Enter 1 for file with number of maxWords = 1.");
        System.out.println("- Enter 2 for file with number of maxWord > 1.");
        fileChoice = scanner.nextInt();

        if (fileChoice == 1) {//choosing the text with only ONE most frequent word
            file = new File("text_with_1_max_word.txt"); //main text file (can be found in project's directory)
        } else if (fileChoice == 2) {//choosing the text with MORE THAN ONE most frequent word
            file = new File("text_with_2+_max_words.txt");
        } else {//invalid input handling
            System.out.println("Invalid input! Program aborted!");
            System.exit(1);
        }

        printTextFile(file); //printing of the text file provided

        //comparator to ignore case while forming unique words list
        Comparator<String> comparator = String::compareToIgnoreCase;

        List<String> wordsUnique = new ArrayList<>(); //collection for all the unique words in the file
        Map<String, Integer> wordsQuantity = new HashMap<>(); //collection "word-quantity" in the file

        findUniqueWordsAndQuantity(file, wordsUnique, wordsQuantity);

        wordsUnique.sort(comparator); //sorting all the words in the list
        removeDuplicatesFromTheList(wordsUnique); //removing all the duplicates to form a unique words list

        System.out.println("\nHere's the list with all the unique words used in the file (in alphabet order): ");
        System.out.println(wordsUnique);

        System.out.println("\nHere's the list for all the words and their respective quantity in the file: ");
        System.out.println(wordsQuantity);

        System.out.println();

        String maxWord = null; //the most frequent word in the file
        int maxQuantity = 1; // the number the most frequent word is present in the file
        int maxCount = 0; //the number of the most frequent words in the file (if there's more than one)
        List<String> maxWordList = new ArrayList<>(); //collection for the most frequent words in the file

        for (Object o : wordsQuantity.entrySet()) { //iterating through collection to find the most frequent word
            Map.Entry pair = (Map.Entry) o;
            if (maxQuantity < (int) pair.getValue()) {
                maxQuantity = (int) pair.getValue();
                maxWord = (String) pair.getKey();
            }
        }

        for (Object o : wordsQuantity.entrySet()) { //iterating through collection to count the most frequent words
            Map.Entry pair = (Map.Entry) o;
            if (maxQuantity == (int) pair.getValue()) {
                maxWordList.add((String) pair.getKey());
                maxCount++;
            }
        }

        //printing the results for the most frequent words in the file
        if (maxCount >= 2) { //if there's more than one
            System.out.println("There's more than one most frequent word in the file!");
            System.out.println("These words are " + maxWordList);
            System.out.println("Each of them can be found " + maxQuantity + " times there.");

        } else { //if there's only one max word
            System.out.println("The most frequent word in the file is \"" + maxWord + "\".");
            System.out.println("It can be found " + maxQuantity + " times there.");
        }
    }

    /**
     * Printing of the generic text file.
     *
     * @param textFilePath path to the text file
     * @throws FileNotFoundException exception in case the file is missing
     */
    private static void printTextFile(File textFilePath) throws FileNotFoundException {

        Scanner scannerToPrint = new Scanner(textFilePath); //buffer variable for file's contents
        System.out.println("Here's the contents of your file \"" + textFilePath.getAbsolutePath() + "\":\n");
        System.out.print("\"");
        while (scannerToPrint.hasNextLine()) { // printing text file's contents on the console
            String line = scannerToPrint.nextLine();
            System.out.print(line);
            if (!scannerToPrint.hasNextLine()) //elegant way to close the end of the text file with another "-sign
                System.out.println("\"");
            else
                System.out.println();
        }
        scannerToPrint.close();
    }

    /**
     * This method collects all the unique words from the text file, sorts it in alphabet order and counts their quantity.
     *
     * @param textFilePath path to the text file
     * @param words        collection for sorted unique words
     * @param quantity     collection "word-quantity"
     */
    private static void findUniqueWordsAndQuantity(File textFilePath, List<String> words, Map<String, Integer> quantity)
            throws FileNotFoundException {
        Scanner scanner = new Scanner(textFilePath); //new buffer variable for file's contents
        while (scanner.hasNext()) {
            String word = scanner.useDelimiter("(\\s+|\\.\\s*|,\\s*)").next();
            Integer count = quantity.get(word);
            if (count == null) {
                count = 0;
            }
            quantity.put(word, ++count); //counting unique words in the file
            words.add(word); // collecting unique words in the file in sorted order
        }
        scanner.close();
    }

    /**
     * This method removes string duplicates from the List collections
     * @param words a string array/list
     */
    private static void removeDuplicatesFromTheList(List<String> words) {
        for (int i = 0; i < words.size(); i++) {
            String nextWord = words.get(i);
            for (int j = 0; j < words.size(); j++) {
                if (i != j && nextWord.equals(words.get(j))) {
                    words.remove(j);
                    j = 0;
                }
            }
        }
    }
}
