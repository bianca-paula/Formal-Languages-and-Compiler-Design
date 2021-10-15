/*
* Statement: Implement the Symbol Table (ST) as the specified data structure, with the corresponding operations
* Symbol Table - hash table
*
* */

public class Main {

    public static void main(String[] args) {
        var symbolTable = new SymbolTable(10);

        System.out.println(symbolTable.addElement("a"));
        System.out.println(symbolTable.addElement("b"));
        System.out.println(symbolTable.addElement("c"));
        System.out.println(symbolTable.addElement("1"));
        System.out.println(symbolTable.addElement("x"));
        System.out.println(symbolTable.addElement("a"));
        System.out.println(symbolTable.addElement("z"));
        System.out.println(symbolTable.addElement("123"));
        System.out.println(symbolTable.addElement("abc"));
        System.out.println(symbolTable.addElement("cba"));

        symbolTable.printSymbolTable();
    }
}