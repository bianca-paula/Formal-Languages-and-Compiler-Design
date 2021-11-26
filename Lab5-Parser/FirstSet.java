import java.util.*;
import java.util.stream.Collectors;

public class FirstSet {
    private Map<String, Set<String>> firstSets;
    private final Grammar grammar;


    private Set<String> getFirstTerminalsForNonterminal(String nonterminal) {
        return this.grammar.getProductionsForNonterminal(nonterminal)
                .stream()
                .filter(rhs -> this.grammar.getE().contains(rhs.get(0)))
                .map(rhs -> rhs.get(0))
                .collect(Collectors.toSet());
    }

    public Set<String> getPreviousFirst(String element) {
        if (this.grammar.getE().contains(element) || "epsilon".equals(element)) {
            Set<String> newSet = new HashSet<>();
            newSet.add(element);
            return newSet;
        }
        return this.firstSets.get(element);
    }


    public FirstSet(Grammar grammar) {
        this.grammar = grammar;
        this.computeFirstSets();
    }

    public Map<String, Set<String>> getFirstSets() {
        return this.firstSets;
    }
}