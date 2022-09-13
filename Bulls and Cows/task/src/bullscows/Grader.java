package bullscows;

import java.util.*;

public class Grader {

    Scanner scanner = new Scanner(System.in);
    private int bulls = 0;
    private int cows = 0;
    private List<Character>userList = new ArrayList<>();
    private Set<Character> generatedSecretCode = new LinkedHashSet<>();
    private final String alphaNumerical = "0123456789abcdefghijklmnopqrstuvwxyz";


    private boolean isGenerateSecreteCode() {
        System.out.println("Please, enter the secret code's length:");
        String initialCodeLength = scanner.nextLine();
        int codeLength = 0;

        try {
            codeLength = Integer.parseInt(initialCodeLength);
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + initialCodeLength +"\" isn't a valid number.");
        }

        if (codeLength > 36 || codeLength < 1) {
            System.out.println("Error: code length should be between 1 and 36");
            return false;
        }

        System.out.println("Input the number of possible symbols in the code:");
        String initialNumberOfCharacters = scanner.nextLine();
        int numberOfCharacters = 0;

        try {
            numberOfCharacters = Integer.parseInt(initialNumberOfCharacters);
        } catch (NumberFormatException e) {
            System.out.println("Error: \"" + initialNumberOfCharacters +"\" isn't a valid number.");
        }

        if (numberOfCharacters < 1 || numberOfCharacters > 36) {
            System.out.println("Error: number of possible symbols should be between 1 and 36");
            return false;
        }

        if (codeLength > numberOfCharacters) {
            System.out.println("Error: it's not possible to generate a code with a length of " + codeLength + " with " + numberOfCharacters + " unique symbols.");
            return false;
        }

        Random random = new Random();
        while (generatedSecretCode.size() < codeLength) {
            generatedSecretCode.add(alphaNumerical.charAt(random.nextInt(numberOfCharacters)));
        }

        printSecretCode(codeLength, numberOfCharacters);
        return true;
    }

    private void updateUserList () {
        String userInput = scanner.nextLine();
        for (int i = 0; i < userInput.length(); i++ ){
            userList.add(userInput.charAt(i));
        }
    }

    private void printSecretCode(int codeLength, int numberOfCharacters) {
        StringBuilder codeToPrint = new StringBuilder("");
        int usefulNumberOfCharacters = numberOfCharacters - 1;
        codeToPrint.append("*".repeat(codeLength));
        if (numberOfCharacters < 11) {
            codeToPrint.append(" (0-" + usefulNumberOfCharacters + ").");
        } else {
            codeToPrint.append(" (0-9, a-" + alphaNumerical.charAt(usefulNumberOfCharacters) + ").");
        }
        System.out.println(codeToPrint);
    }

    private void bullsAndCowsGenerator() {
        if (!isGenerateSecreteCode()) {
            return;
        } else {
            List<Character> secretCode = new ArrayList<>(generatedSecretCode);
            int counter = 1;
            System.out.println("Okay, let's start a game!");

            while (secretCode.size() != bulls) {
                bulls = 0;
                System.out.println("Turn " + counter +":");
                updateUserList();

                for (int i = 0; i < secretCode.size(); i++) {
                    if (Objects.equals(secretCode.get(i), userList.get(i))) {
                        bulls++;
                    }
                    for (int j = 0; j < userList.size(); j++) {
                        if ((Objects.equals(secretCode.get(i), userList.get(j)))) {
                            cows++;
                        }
                    }
                }
                printResult();
                counter++;
                userList.clear();
            }
            System.out.println("Congratulations! You guessed the secret code.");
        }

    }

    private void calculateResults() {
        cows = cows - bulls;
    }

    private void printResult(){
        calculateResults();
        System.out.println("Grade: " + bulls + " bull(s) and " + cows +" cow(s).");
    }

    protected void playBullsAndCows() {
        bullsAndCowsGenerator();

    }
}
