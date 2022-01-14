package Controller;

import Model.*;
//import Model.Scanner;

import java.util.*;

public class Service {
    private Parser parser = new Parser();

    public Map<String, Set<String>> getFirstSet() {
        return parser.getFirstSet();
    }

    public Set<String> getNonTerminals() {
        return parser.getGrammar().getN();
    }

    public Set<String> getTerminals() {
        return parser.getGrammar().getE();
    }

    public List<Production> getProductions() {
        return parser.getGrammar().getProductions();
    }

    public List<Production> getProductionsForNonterminal(String nonTerminal) {
        return parser.getGrammar().getProductionListForNonterminal(nonTerminal);
    }

    public String getStartingSymbol() {
        return parser.getGrammar().getS();
    }

    public Map<String, Set<String>> getFollowSet() {
        return parser.getFollowSet();
    }

}
