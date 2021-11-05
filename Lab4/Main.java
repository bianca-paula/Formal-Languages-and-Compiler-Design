import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Finite automata");
        int option = scanner.nextInt();
        switch(option){
            case 1 -> optionsForDFA();
        }
    }


    private static void printMenu(){
        System.out.println("1. Print states");
        System.out.println("2. Print alphabet");
        System.out.println("3. Print final states");
        System.out.println("4. Print transitions");
        System.out.println("5. Check if sequence is accepted by DFA");
        System.out.println("6. Check if DFA");
        System.out.println("0. Exit");
    }

    private static void optionsForDFA() {
        FiniteAutomata fa = new FiniteAutomata("D:/3rd YEAR/FORMAL LANGUAGES AND COMPILER DESIGN/Laboratory4/src/Files/FA.txt");
        System.out.println("FA.txt read from file.");
        printMenu();
        System.out.println("Your option: ");

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();

        while(option != 0){
            switch(option){
                case 1 -> System.out.println(fa.printStates());
                case 2 -> System.out.println(fa.printAlphabet());
                case 3 -> System.out.println(fa.printFinalStates());
                case 4 -> System.out.println(fa.printTransitions());
                case 5 -> {
                    if(fa.checkIfDFA()){
                        System.out.println("Your sequence: ");
                        Scanner scanner2 = new Scanner(System.in);
                        String sequence = scanner2.nextLine();
                        System.out.println(sequence.length());

                        if (fa.checkIfSequenceIsAccepted(sequence))
                            System.out.println("Sequence is valid");
                        else
                            System.out.println("Invalid sequence");
                    }
                    else{
                        System.out.println("FA.txt is not deterministic");
                    }
                }
                case 6 -> {
                    if(fa.checkIfDFA()) {
                        System.out.println("DFA!");
                    }
                    else {
                        System.out.println("Not DFA!");
                    }
                }
            }
            System.out.println();
            printMenu();
            System.out.println("Your option: ");
            option = scanner.nextInt();
        }
    }



}
