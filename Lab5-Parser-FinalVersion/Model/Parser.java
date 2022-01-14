package Model;

import java.util.*;

public class Parser {
    private final Grammar grammar;
    private final String filename;
    private Map<String, Set<String>> firstSet;
    private Map<String, Set<String>> followSet;
    private static Stack<List<String>> rules = new Stack<>();
    private Map<Pair<String, List<String>>, Integer> productionsNumbered = new HashMap<>();

    public Parser() {
        this.filename = "C://Users//komsa//FLCD//Laboratory5_Parser//src//Files//grammar.txt";
        this.grammar = new Grammar(filename);
        this.firstSet = new HashMap<>();
        this.followSet = new HashMap<>();
        generateSets();
    }

    private void generateSets() {
        generateFirstSet();
        generateFollowSet();
    }

    private void generateFirstSet() {
        for (String nonTerminal : grammar.getN()) {
            firstSet.put(nonTerminal, this.firstOf(nonTerminal));
        }
    }

    private Set<String> firstOf(String nonTerminal) {
        // verify if the nonterminal was computed
        if (firstSet.containsKey(nonTerminal)) {
            return firstSet.get(nonTerminal);
        }
        // PRODUCTION ==> left side(symbol) -> right side (rule)
        // S -> A B | C D
        // first(S) = first(A) U first(C)
        // getProduction(c)
        // A -> c d
        // F -> d c
        Set<String> temp = new HashSet<>();
        Set<String> terminals = grammar.getE();

        for (Production production : grammar.getProductionListForNonterminal(nonTerminal)) {
            for (List<String> rule : production.getRules()) {
                String firstSymbol = rule.get(0);
                if (firstSymbol.equals("epsilon"))
                    temp.add("ε");
                    // simple case - terminals
                else if (terminals.contains(firstSymbol))
                    temp.add(firstSymbol);
                else
                    // nonterminal case - A, B
                    temp.addAll(firstOf(firstSymbol));
            }
        }
        return temp;
    }

    private void generateFollowSet() {
        for (String nonTerminal : grammar.getN()) {
            followSet.put(nonTerminal, this.followOf(nonTerminal, nonTerminal));
        }
    }

    private Set<String> followOf(String nonTerminal, String initialNonTerminal) {
        if (followSet.containsKey(nonTerminal))
            return followSet.get(nonTerminal);
        Set<String> temp = new HashSet<>();
        Set<String> terminals = grammar.getE();
        // rule 1 -- for start symbol place $ in FOLLOW(S)
        if (nonTerminal.equals(grammar.getS()))
            temp.add("$");
        for (Production production : grammar.getProductionsContainingNonterminal(nonTerminal)) {
            String productionStart = production.getStart();

            for (List<String> rule : production.getRules()) {
                List<String> ruleConflict = new ArrayList<>();
                ruleConflict.add(nonTerminal);
                ruleConflict.addAll(rule);
                if (rule.contains(nonTerminal) && !rules.contains(ruleConflict)) {
                    rules.push(ruleConflict);
                    int indexNonTerminal = rule.indexOf(nonTerminal);
                    temp.addAll(followOperation(nonTerminal, temp, terminals, productionStart, rule, indexNonTerminal, initialNonTerminal));
                    List<String> sublist = rule.subList(indexNonTerminal + 1, rule.size());
                    if (sublist.contains(nonTerminal)) {
                        temp.addAll(followOperation(nonTerminal, temp, terminals, productionStart, rule, indexNonTerminal + 1 + sublist.indexOf(nonTerminal), initialNonTerminal));
                    }
                    rules.pop();

                }
            }
        }

        return temp;
    }

    private Set<String> followOperation(String nonTerminal, Set<String> temp, Set<String> terminals, String productionStart, List<String> rule, int indexNonTerminal, String initialNonTerminal) {
        //rule2 - if A -> alphaB, Follow(B)=Follow(A)
        if (indexNonTerminal == rule.size() - 1) {
            if (productionStart.equals(nonTerminal)) {
                return temp;
            }
            if (!initialNonTerminal.equals(productionStart)){
                temp.addAll(followOf(productionStart, initialNonTerminal));
            }
        }
        else
        {
            //rule 3 - For any production rule A -> alphaBbeta
            String nextSymbol = rule.get(indexNonTerminal + 1);
            //elementary case
            if(nextSymbol.equals("epsilon")){
                return temp;
            }

            //if beta is terminal, we add it
            if (terminals.contains(nextSymbol))
                temp.add(nextSymbol);
            else{
                if (!initialNonTerminal.equals(nextSymbol)) {
                    //get first of beta
                    Set<String> firsts = new HashSet<>(firstSet.get(nextSymbol));
                    //if we have epsilon in FIRST, then {follow(B) = first(beta) -Epsilon} U FOLLOW(A)
                    if (firsts.contains("epsilon")) {
                        temp.addAll(followOf(nextSymbol, initialNonTerminal));
                        firsts.remove("epsilon");
                    }
                    //ALSO ADD FIRST(beta) in both cases
                    temp.addAll(firsts);
                }
            }
        }
        return temp;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public Map<String, Set<String>> getFirstSet() {
        return firstSet;
    }

    public Map<String, Set<String>> getFollowSet() {
        return followSet;
    }

}
