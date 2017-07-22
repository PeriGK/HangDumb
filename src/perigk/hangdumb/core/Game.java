package perigk.hangdumb.core;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Game {

    private final int totalAttempts;
    private Player player;
    private Word solution;
    private static ArrayList<String> wordList = new ArrayList<String>();
    private String initialWord;
    private final String fileName;
    private String currentTarget;

    public Game(Player p, String fileName, int totalAttempts) {
        this.fileName = fileName;
        this.populateWordList(this.fileName);
        this.solution = this.getRandomWord();

        // We picked a word to play and we need to not pick it up again
        // in this series of games.
        Game.wordList.remove(this.solution.getText());
        this.player = p;
        this.currentTarget = "";
        this.initialWord = initialiseTargetWord();
        this.totalAttempts = totalAttempts;
    }


    /***
     * Print the end with all the intermediate fields masked with "_"
     * @return the masked word
     */
    public String initialiseTargetWord() {
        String targetWord = "";
        int solutionLength = this.solution.getText().length();
        char first = this.solution.getText().charAt(0);
        char last = this.solution.getText().charAt(solutionLength - 1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < solutionLength - 2; i++) {
            builder.append("_");
        }
        targetWord =  first + builder.toString() + last;
        System.out.println("Printing target word: " + targetWord);
        this.currentTarget = targetWord;
        return targetWord;
    }

    /***
     * From the populated list of all the candidate words, pick a random one
     * @return
     */
    private Word getRandomWord() {
        Random rand = new Random();
        int idx = rand.nextInt(Game.wordList.size());
        return new Word(Game.wordList.get(idx));
    }

    /***
     * Read the vocabulary file and load it in an ArrayList to pick a word from there, in every round
     * @param fileName
     */
    public void populateWordList(String fileName) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line = null;

        try {
            line = br.readLine();
            while (line != null) {
                Game.wordList.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Main loop of the game
     */
    public void start() {
        boolean gameHasEnded = false;
        int remainingAttempts = this.totalAttempts;
        ArrayList<Character> usedLetters = new ArrayList<>();

        while (!gameHasEnded) {
            if (usedLetters.size() != 0) {
                System.out.println("You have already used the following letters " + usedLetters);
            }


            char guess = parseGuess();
            checkLetterAlreadyUsed(usedLetters, guess);
            System.out.println("=============================");
            System.out.println("Guess is: " + guess);

            boolean isCorrectGuess = checkCorrectGuess(guess);

            if (isCorrectGuess) {
                handleCorrectGuess(guess);
            } else {
                remainingAttempts = handleErroneousGuess(remainingAttempts, guess);
            }
            System.out.println("Current target " + this.currentTarget);
            gameHasEnded = this.currentTarget.equals(this.solution.getText()) || remainingAttempts == 0;
        }

        checkReasonForGameEnd();

        playerWantsToPlayMore();
    }

    /***
     * Simple prompt to check if the player wants to play another round
     */
    private void playerWantsToPlayMore() {
        System.out.println("Wanna play again?");
        Scanner s = new Scanner(System.in);
        String answer = s.next();
        if (answer.equalsIgnoreCase("Y")) {
            new Game(this.player, this.fileName, this.totalAttempts).start();
        } else {
            System.out.println("Thank you " + this.player.getName() +", goodbye");
        }
    }

    /***
     * There are two cases why the game ended. The first one is the user found the word and the other one is
     * that he failed to find it during a set number of attempts
     */
    private void checkReasonForGameEnd() {
        if (this.currentTarget.equals(this.solution.getText())) {
            System.out.println("You won mothafucka :)");
        } else {
            System.out.println("Unfortunately, you lost " + this.player.getName() + " :'( The word you had to guess was: " + this.solution.getText());
        }
    }

    /***
     * Handles the case where the player gave a wrong input
     * @param remainingAttempts
     * @param guess
     * @return
     */
    private int handleErroneousGuess(int remainingAttempts, char guess) {
        System.out.println(guess + " is not a correct letter. Please try again");
        remainingAttempts--;
        System.out.println("You still have " + remainingAttempts  + " chances");
        return remainingAttempts;
    }

    /***
     * Handles the case where the player gave a correct input
     * @param guess
     */
    private void handleCorrectGuess(char guess) {
        this.currentTarget = replacePlaceHoldersWithLetters(guess);
    }

    /***
     * Keeps track of all the letters used till now, so that the user can recall and not lose attempts for such
     * petty reason
     * @param usedLetters
     * @param guess
     * @return
     */
    private boolean checkLetterAlreadyUsed(ArrayList<Character> usedLetters, char guess) {
        while (usedLetters.contains(guess)) {
            System.out.println(guess + " is already used, please try a letter you have not tried");
            guess = parseGuess();
        }

        usedLetters.add(guess);
        return true;
    }

    /***
     * If a correct guess has happened, replace the underscores with the respective letters
     * @param guess
     * @return
     */
    private String replacePlaceHoldersWithLetters(char guess) {
        char[] solution_array = null;
        String word = this.solution.getText();
        solution_array = this.currentTarget.toCharArray();

        int index = word.indexOf(guess);
        while (index >= 0) {
            solution_array[index] = guess;
            index = word.indexOf(guess, index + 1);
        }
        return new String(solution_array);
    }

    /***
     * Is the letter guessed contained in the target string?
     * We need to check both the solution one and the current, as the first/last letter might be
     * repeated in the hidden letters
     * @param guess
     * @return
     */
    private boolean checkCorrectGuess(char guess) {
        return this.solution.getText().contains(guess+"") && !this.currentTarget.substring(1, this.currentTarget.length() - 2).contains(guess+"");
    }

    /***
     * Gets the user input from the console
     * @return
     */
    private char parseGuess() {
        Scanner s = new Scanner(System.in);
        String guess = "";
        guess = s.next();
        return guess.charAt(0);
    }
}
