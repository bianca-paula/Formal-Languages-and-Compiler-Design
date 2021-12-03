public class Main {
    public static void main(String[] args) {
        Grammar grammar = new Grammar("D:/3rd YEAR/FORMAL LANGUAGES AND COMPILER DESIGN/Lab5/src/files/g1.txt");
        ParserLL1 parser = new ParserLL1(grammar);
        //System.out.println((grammar.getProductionForNonterminal("A")));
        System.out.println(parser.printFirst());
        //System.out.println(parser.printFollow());
        //System.out.println(parser.printParseTable());
    }
}
