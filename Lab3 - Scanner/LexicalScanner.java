import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LexicalScanner {
    public String filename;
    public SymbolTable symbolTable;
    public LanguageSymbols languageSymbols;
    public PIF ProgramInternalForm;

    public LexicalScanner(String filename){
        this.filename=filename;
        this.symbolTable = new SymbolTable(20);
        this.ProgramInternalForm = new PIF();
        this.languageSymbols = new LanguageSymbols("src/Files/token.in");
    }

    public int getFileLineNumber(String lineToBeFound){
        File file = new File(this.filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            int lineNum = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineNum++;
                if(line.contains(lineToBeFound)) {
                    return lineNum;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return -1;

    }

    public void parseLine(String line) throws IOException, ParseException {
        this.languageSymbols.readLanguageSymbols();
        // remove all blank spaces
        String[] lineElements = line.split("\\s+");

        for(String element: lineElements) {
            if(element.length() > 0) {
                int i = 0;
                StringBuilder newString = new StringBuilder();

                while(i < element.length()) {
                    if(isCharacterInAlphabet(element.charAt(i))) {
                        newString.append(element.charAt(i));
                    }
                    else {
                        if(newString.length() > 0) {
                            this.symbolTable.addElement(newString.toString());
                            this.ProgramInternalForm.addToPIF(newString.toString(), this.symbolTable.searchElement(newString.toString()));
                        }
                        newString = new StringBuilder();

                        String newToken = Character.toString(element.charAt(i));
                        if(this.languageSymbols.operators.contains(newToken) || this.languageSymbols.separators.contains(newToken)) {
                            ProgramInternalForm.addToPIF(Character.toString(element.charAt(i)),this.symbolTable.searchElement(Character.toString(element.charAt(i))));
                        }
                        else {
                            String errorMessage="lexical error - at line: "+getFileLineNumber(line);
                            throw new ParseException(errorMessage,getFileLineNumber(line));

                        }

                    }
                    i++;
                }

                if (newString.length() > 0 && this.languageSymbols.reservedWords.contains(newString.toString())){
                    this.ProgramInternalForm.addToPIF(newString.toString(), this.symbolTable.searchElement(newString.toString()));
                }

                if (newString.length() > 0 && !(this.languageSymbols.reservedWords.contains(newString.toString()))) {
                        this.symbolTable.addElement(newString.toString());
                        this.ProgramInternalForm.addToPIF(newString.toString(), this.symbolTable.searchElement(newString.toString()));
                    }

            }
        }
    }

    public void parseFile() throws IOException{
        boolean lexicallyCorrectProgram=true;
        File file = new File(this.filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            try {
                parseLine(line);
            } catch (FileNotFoundException | ParseException e) {
                e.printStackTrace();
                lexicallyCorrectProgram = false;
            }
        }
        scanner.close();
        FileWriter myWriter = new FileWriter("src/files/ST.out");
        BufferedWriter bw = new BufferedWriter(myWriter);
        bw.write(symbolTable.getHashTable());
        bw.newLine();
        bw.close();
        FileWriter PIF_FileWriter = new FileWriter("src/files/PIF.out");
        BufferedWriter bufferedWriterPIF = new BufferedWriter(PIF_FileWriter);
        bufferedWriterPIF.write(this.ProgramInternalForm.FormatPIFWriter());
        bufferedWriterPIF.newLine();
        bufferedWriterPIF.close();
        if(lexicallyCorrectProgram){
            System.out.println("Lexically correct! ");
        }
    }

    private boolean isCharacterInAlphabet(Character character) {
        if(character >= 'a' && character <= 'z')
            return true;
        if(character >= 'A' && character <= 'Z')
            return true;
        if(character >= '0' && character <= '9')
            return true;
        if(character.equals('_'))
            return true;
        return false;
    }
}
