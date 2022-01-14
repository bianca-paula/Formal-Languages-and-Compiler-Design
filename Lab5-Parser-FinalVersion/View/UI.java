package View;

import Controller.Service;
import Model.*;
import java.util.*;

public class UI {
    private Service service;

    public UI(Service service) {
        this.service = service;
    }

    public void start() {
        System.out.println();
        System.out.println("0 - Exit");
        System.out.println("1 - Grammar");
        System.out.println("2 - Parser");

        Scanner inScanner = new Scanner(System.in);
        int option = Integer.parseInt(inScanner.next().trim());
        switch (option) {
            case 0:
                System.exit(0);
            case 1:
                fileMenuGrammar();
                break;
            case 2:
                fileMenuParser();
                break;
            default:
                start();
        }
    }

    private void fileMenuParser() {
        System.out.println();
        System.out.println("0 - Back");
        System.out.println("1 - Get FIRST set");
        System.out.println("2 - Get FOLLOW set");

        Scanner inScanner = new Scanner(System.in);
        int option = Integer.parseInt(inScanner.next().trim());
        switch (option) {
            case 0:
                start();
                break;
            case 1:
                service.getFirstSet().forEach(this::displaySet);
                System.out.println();
                fileMenuParser();
                break;
            case 2:
                service.getFollowSet().forEach(this::displaySet);
                System.out.println();
                fileMenuParser();
                break;
            default:
                start();
        }
    }

    private void displaySet(String key, Set<String> value) {
        StringBuilder sb = new StringBuilder(key + " = { ");
        for (String symbol : value)
            sb.append(symbol).append(", ");
        sb.append("}");
        sb.replace(sb.length() - 3, sb.length() - 2, "");
        System.out.println(sb);
    }

    private void fileMenuGrammar() {
        System.out.println();
        System.out.println("0 - Back");
        System.out.println("1 - Non-terminals");
        System.out.println("2 - Terminals");
        System.out.println("3 - Productions");
        System.out.println("4 - Productions of a non-terminal");
        System.out.println("5 - Starting Symbol");

        Scanner inScanner = new Scanner(System.in);
        int option = Integer.parseInt(inScanner.next().trim());
        switch (option) {
            case 0:
                start();
                break;
            case 1:
                System.out.println(service.getNonTerminals());
                System.out.println();
                fileMenuGrammar();
                break;
            case 2:
                System.out.println(service.getTerminals());
                System.out.println();
                fileMenuGrammar();
                break;
            case 3:
                System.out.println("P: {");
                for (Production production: service.getProductions())
                    System.out.println("    " + production);
                System.out.println("}");
                System.out.println();
                fileMenuGrammar();
                break;
            case 4:
                System.out.println(service.getProductionsForNonterminal(promptForNonTerminal()));
                System.out.println();
                fileMenuGrammar();
                break;
            case 5:
                System.out.println(service.getStartingSymbol());
                System.out.println();
                fileMenuGrammar();
                break;
            default:
                start();
        }
    }

    private List<String> promptForSequence() {
        Scanner inScanner = new Scanner(System.in);
        return Arrays.asList(inScanner.nextLine().replace("\n", "").split(" "));
    }

    private String promptForNonTerminal() {
        Scanner inScanner = new Scanner(System.in);
        return inScanner.next().trim();
    }
}
