import java.util.*;

public class ParserLL1 {
    private final Grammar grammar;
    private HashMap<String, Set<String>> FIRST;
    private HashMap<String, Set<String>> FOLLOW;
    private HashMap<Pair, Pair> parseTable;

    public ParserLL1(Grammar grammar) {
        this.grammar = grammar;
        this.FIRST = new HashMap<>();
        this.FOLLOW = new HashMap<>();
        this.parseTable = new HashMap<>();

        FIRST();
        FOLLOW();
    }

    public void FIRST() {
        for (String nonterminal : grammar.getN()) {
            FIRST.put(nonterminal, new HashSet<>());
            Set<List<String>> productionForNonterminal = grammar.getProductionForNonterminal(nonterminal);

            for (List<String> production : productionForNonterminal) {
                if (grammar.getE().contains(production.get(0)) || production.get(0).equals("epsilon"))
                    FIRST.get(nonterminal).add(production.get(0));
            }
        }
        var changed = true;
        while (changed) {
            changed = false;
            HashMap<String, Set<String>> column = new HashMap<>();

            for (String nonterminal : grammar.getN()) {
                Set<List<String>> productionForNonterminal = grammar.getProductionForNonterminal(nonterminal);
                Set<String> toAdd = new HashSet<>(FIRST.get(nonterminal));
                for (List<String> production : productionForNonterminal) {
                    List<String> rhsNonTerminals = new ArrayList<>();
                    String rhsTerminal = null;
                    for (String symbol : production)
                        if (this.grammar.getN().contains(symbol))
                            rhsNonTerminals.add(symbol);
                        else {
                            rhsTerminal = symbol;
                            break;
                        }
                    toAdd.addAll(multipleConcatenation(FIRST, rhsNonTerminals, rhsTerminal));
                }
                if (!toAdd.equals(FIRST.get(nonterminal))) {
                    changed = true;
                }
                column.put(nonterminal, toAdd);
            }
            FIRST = column;
        }

    }

    private Set<String> multipleConcatenation(HashMap<String, Set<String>> firstSet, List<String> nonTerminals, String terminal) {
        if (nonTerminals.size() == 0)
            return new HashSet<>();
        if (nonTerminals.size() == 1) {
            return firstSet.get(nonTerminals.iterator().next());
        }

        Set<String> concatenation = new HashSet<>();
        var step = 0;
        var allEpsilon = true;

        for (String nonTerminal : nonTerminals) {
            if (!firstSet.get(nonTerminal).contains("epsilon")) {
                allEpsilon = false;
            }
        }
        if (allEpsilon) {
            concatenation.add(Objects.requireNonNullElse(terminal, "epsilon"));
        }

        while (step < nonTerminals.size()) {
            boolean thereIsOneEpsilon = false;
            for (String s : firstSet.get(nonTerminals.get(step)))
                if (s.equals("epsilon"))
                    thereIsOneEpsilon = true;
                else
                    concatenation.add(s);

            if (thereIsOneEpsilon)
                step++;
            else
                break;
        }
        return concatenation;
    }

    public void FOLLOW() {
        for (String nonterminal : grammar.getN()) {
            FOLLOW.put(nonterminal, new HashSet<>());
        }
        FOLLOW.get(grammar.getS()).add("epsilon");
        var changed = true;
        while (changed) {
            changed = false;
            HashMap<String, Set<String>> column = new HashMap<>();
            for (String nonterminal : grammar.getN()) {
                column.put(nonterminal, new HashSet<>());
                var productionsWithNonterminalInRhs = new HashMap<String, Set<List<String>>>();
                var allProductions = grammar.getP();
                allProductions.forEach((k, v) -> {
                    for (var eachProduction : v) {
                        if (eachProduction.contains(nonterminal)) {
                            var key = k.iterator().next();
                            if (!productionsWithNonterminalInRhs.containsKey(key))
                                productionsWithNonterminalInRhs.put(key, new HashSet<>());
                            productionsWithNonterminalInRhs.get(key).add(eachProduction);
                        }
                    }
                });
                var toAdd = new HashSet<>(FOLLOW.get(nonterminal));
                productionsWithNonterminalInRhs.forEach((k, v) -> {
                    for (var production : v) {
                        var productionList = (ArrayList<String>) production;
                        var indexOfNonterminal = productionList.indexOf(nonterminal);
                        if (indexOfNonterminal + 1 == productionList.size()) {
                            toAdd.addAll(FOLLOW.get(k));
                        } else {
                            var followSymbol = productionList.get(indexOfNonterminal + 1);
                            if (grammar.getE().contains(followSymbol))
                                toAdd.add(followSymbol);
                            else {
                                toAdd.addAll(FIRST.get(followSymbol));
                                toAdd.addAll(FOLLOW.get(k));
                            }
                        }
                    }
                });
                if (!toAdd.equals(FOLLOW.get(nonterminal))) {
                    changed = true;
                }
                column.put(nonterminal, toAdd);
            }

            FOLLOW = column;

        }
    }


    public String printFirst() {
        StringBuilder builder = new StringBuilder();
        FIRST.forEach((k, v) -> {
            builder.append(k).append(": ").append(v).append("\n");
        });
        return builder.toString();
    }

    public String printFollow() {
        StringBuilder builder = new StringBuilder();
        FOLLOW.forEach((k, v) -> {
            builder.append(k).append(": ").append(v).append("\n");
        });
        return builder.toString();
    }

    public String printParseTable() {
        StringBuilder builder = new StringBuilder();
        parseTable.forEach((k, v) -> {
            builder.append(k).append(" -> ").append(v).append("\n");
        });
        return builder.toString();
    }

    public List<Integer> parseSequence(List<String> sequence){
        Stack<String> alpha = new Stack<>();
        Stack<String> beta = new Stack<>();
        List<Integer> result = new ArrayList<>();

        return result;
    }
}