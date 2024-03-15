import javax.annotation.processing.SupportedSourceVersion;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Spelling Bee
 *
 * This program accepts an input of letters. It prints to an output file
 * all English words that can be generated from those letters.
 *
 * For example: if the user inputs the letters "doggo" the program will generate:
 * do
 * dog
 * doggo
 * go
 * god
 * gog
 * gogo
 * goo
 * good
 *
 * It utilizes recursion to generate the strings, mergesort to sort them, and
 * binary search to find them in a dictionary.
 *
 * @author Zach Blick, Kirin Debnath
 *
 * Written on March 5, 2023 for CS2 @ Menlo School
 *
 * DO NOT MODIFY MAIN OR ANY OF THE METHOD HEADERS.
 */
public class SpellingBee {

    private String letters;
    private ArrayList<String> words;
    public static final int DICTIONARY_SIZE = 143091;
    public static final String[] DICTIONARY = new String[DICTIONARY_SIZE];

    public SpellingBee(String letters) {
        this.letters = letters;
        words = new ArrayList<String>();
    }


    public void generate() {
        generateWords("", letters);
    }


     /*** Generates all possible substrings and permutations of letters ***/
    public void generateWords(String word, String letters) {

        // Base case when no letters remain
        if(letters.equals("")) {
            return;
        }

        String newWord;
        String newLetters;

        // Recursive branch for each remaining letter
        for(int i = 0; i < letters.length(); i++) {

            // Append current letter to word
            newWord = word + letters.charAt(i);
            words.add(newWord);

            // Remove used letter from remaining letters
            newLetters = letters.substring(0,i) + letters.substring(i + 1);

            // Recurse to explore further branches
            generateWords(newWord, newLetters);

        }
    }


    /*** Sorts the word list ***/
    public void sort() {
        this.setWords(mergeSort(words, 0, words.size() - 1));
    }


    /*** Implementation of merge sort on an Array List of Strings ***/
    public ArrayList<String> mergeSort(ArrayList<String> arr, int left, int right) {

        // Base case when only one element
        if (right - left == 0) {
            ArrayList<String> newArr = new ArrayList<String>();
            newArr.add(arr.get(left));
            return newArr;
        }

        int med = (right + left) / 2;
        // Recurse on left and right side of arr to further divide
        ArrayList<String> arrLeft = mergeSort(arr, left, med);
        ArrayList<String> arrRight = mergeSort(arr, med + 1, right);

        // Merge sorted left/right sides
        return merge(arrLeft, arrRight);
    }

    /*** Merges two sorted Array List of Strings into sorted Array List of Strings ***/
    public ArrayList<String> merge(ArrayList<String> arr1, ArrayList<String> arr2) {

        ArrayList<String> merged = new ArrayList<String>();

        int a = 0, b = 0;

        // Compare and add elements to merged Array List while both lists have unmerged elements
        while(a < arr1.size() && b < arr2.size()) {
            // Add earlier element to Array List
            if (arr1.get(a).compareTo(arr2.get(b)) <= 0) {
                merged.add(arr1.get(a++));
            }
            else {
                merged.add(arr2.get(b++));
            }

        }

        // Add remaining list to merged ArrayList
        while(a < arr1.size()) {
            merged.add(arr1.get(a++));
        }

        while(b < arr2.size()) {
            merged.add(arr2.get(b++));
        }

        return merged;
    }

    // Removes duplicates from the sorted list.
    public void removeDuplicates() {
        int i = 0;
        while (i < words.size() - 1) {
            String word = words.get(i);
            if (word.equals(words.get(i + 1)))
                words.remove(i + 1);
            else
                i++;
        }
    }


     /***
     * Check that each generated string is in the dictionary
     * If not remove it from words
     ***/
    public void checkWords() {

        int i = 0;
        while(i < words.size()) {

            if(binarySearch(words.get(i), 0, DICTIONARY_SIZE - 1)) {
                i++;
            }
            else {
                words.remove(i);
            }
        }
    }

    /*** Implementation of binary search to find target word in the dictionary ***/
    public boolean binarySearch(String target, int low, int high) {

        if(low > high) {
            return false;
        }

        int mid = (high + low) / 2;


        if(DICTIONARY[mid].equals(target)) {
            return true;
        }

        // Recurse to examine the side where target is located
        if(DICTIONARY[mid].compareTo(target) < 0) {
            low = mid + 1;
        }
        else {
            high = mid - 1;
        }
        return binarySearch(target, low, high);
    }

    // Prints all valid words to wordList.txt
    public void printWords() throws IOException {
        File wordFile = new File("Resources/wordList.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(wordFile, false));
        for (String word : words) {
            writer.append(word);
            writer.newLine();
        }
        writer.close();
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public SpellingBee getBee() {
        return this;
    }

    public static void loadDictionary() {
        Scanner s;
        File dictionaryFile = new File("Resources/dictionary.txt");
        try {
            s = new Scanner(dictionaryFile);
        } catch (FileNotFoundException e) {
            System.out.println("Could not open dictionary file.");
            return;
        }
        int i = 0;
        while(s.hasNextLine()) {
            DICTIONARY[i++] = s.nextLine();
        }
    }

    public static void main(String[] args) {
        // Prompt for letters until given only letters.
        Scanner s = new Scanner(System.in);
        String letters;
        do {
            System.out.print("Enter your letters: ");
            letters = s.nextLine();
        }
        while (!letters.matches("[a-zA-Z]+"));

         // Load the dictionary
         SpellingBee.loadDictionary();

        // Generate and print all valid words from those letters.
        SpellingBee sb = new SpellingBee(letters);
        sb.generate();
        sb.sort();
        sb.removeDuplicates();
        sb.checkWords();
        try {
            sb.printWords();
        } catch (IOException e) {
            System.out.println("Could not write to output file.");
        }

        s.close();
    }
}
