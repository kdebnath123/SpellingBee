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
 * @author Zach Blick, [ADD YOUR NAME HERE]
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

    // TODO: generate all possible substrings and permutations of the letters.
    //  Store them all in the ArrayList words. Do this by calling ANOTHER method
    //  that will find the substrings recursively.
    public void generate() {
        // YOUR CODE HERE — Call your recursive method!
       gen("", letters);


    }

    public void gen(String word, String letters){

        //base case
        if(letters.equals("")){
            return;
        }

        String newWord;
        String newLetters;
        for(int i = 0; i < letters.length(); i++){

            newWord = word + letters.charAt(i);
            newLetters = letters.substring(0,i) + letters.substring(i + 1);
            words.add(newWord);

            gen(newWord, newLetters);
        }
    }


    // TODO: Apply mergesort to sort all words. Do this by calling ANOTHER method
    //  that will find the substrings recursively.
    public void sort() {

        System.out.println("now sorting");

        // YOUR CODE HERE
        int[] arr = {8,1,3,2,4,5,7,6,9};

        int[] arr1 = {1,3,5};
        int[] arr2 = {2,4,6};

        int[] arr3 = new int[6];

        arr3 = merge(arr1, arr2);

        for (int a: arr3) {
            System.out.println(a);
        }

        //mergeSort(arr, 0 , arr.length);

    }

    public int[] mergeSort(int[] arr, int left, int right) {

        if(left == right){

            int[] theta = new int[1];
            theta[0] = arr[left];

            return theta;
        }

        int mid = (left + right) / 2;

        return merge(mergeSort(arr, left, mid - 1), mergeSort(arr, mid + 1, right));



    }








    /*** takes in 2 presorted arr and merges them ***/
    public int[] merge (int[] arr1, int[] arr2) {

        System.out.println("merging");

        int[] sorted = new int[arr1.length + arr2.length];

        int a = 0;
        int b = 0;
        int c = 0;

        while(a < arr1.length && b < arr2.length){

            if (arr1[a] <= arr2[b]) {
                sorted[c++] = arr1[a++];
            }
            else {
                sorted[c++] = arr2[b++];
            }
        }

        System.out.println("half");

        while(a < arr1.length){
            sorted[c++] = arr1[a++];
        }

        while(b < arr2.length) {
            sorted[c++] = arr2[b++];
        }


        return sorted;
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

    // TODO: For each word in words, use binary search to see if it is in the dictionary.
    //  If it is not in the dictionary, remove it from words.
    public void checkWords() {
        // YOUR CODE HERE
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

        SpellingBee sb = new SpellingBee("");
        //sb.generate();
        sb.sort();


        /***

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

         ***/
    }
}
