package Model;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
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
        this.languageSymbols = new LanguageSymbols("C:/Users/komsa/FLCD/Laboratory3-final/Laboratory3-final/src/Files/token.in");
    }

    public int getFileLineNumber(String lineToBeFound){
        File file = new File(this.filename);
        Scanner scannerFile = null;
        try {
            scannerFile = new Scanner(file);
            int lineNumber = 0;
            while (scannerFile.hasNextLine()) {
                String line = scannerFile.nextLine();
                lineNumber++;
                if(line.contains(lineToBeFound)) {
                    return lineNumber;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void parseLine(String line) throws IOException, ParseException {
        List<String> auxiliarArray = new ArrayList<>();
        this.languageSymbols.readLanguageSymbols();
        String[] lineElements = line.split("\\s+");
        for(String element: lineElements) {
            if(element.length() > 0) {
                int index = 0;
                StringBuilder newString = new StringBuilder();
                while(index < element.length()) {

                    if(isInAlphabet(element.charAt(index))) {
                        newString.append(element.charAt(index));
                    }
                    else {
                        if(newString.length() > 0) {
                            this.symbolTable.addElement(newString.toString());
                            this.ProgramInternalForm.addToProgramInternalForm(newString.toString(), this.symbolTable.searchElement(newString.toString()));
                        }
                        newString = new StringBuilder();
                        String newToken = Character.toString(element.charAt(index));
                        if(this.languageSymbols.operators.contains(newToken) || this.languageSymbols.separators.contains(newToken)) {
                            ProgramInternalForm.addToProgramInternalForm(Character.toString(element.charAt(index)),this.symbolTable.searchElement(Character.toString(element.charAt(index))));
                        }
                        else {
                            String errorMessage="Lexical error - at line: "+getFileLineNumber(line);
                            throw new ParseException(errorMessage,getFileLineNumber(line));
                        }
                    }
                    index++;
                }
                if (newString.length() > 0 && this.languageSymbols.reservedWords.contains(newString.toString())){
                    this.ProgramInternalForm.addToProgramInternalForm(newString.toString(), this.symbolTable.searchElement(newString.toString()));
                }

                if (newString.length() > 0 && !(this.languageSymbols.reservedWords.contains(newString.toString())) && !(this.languageSymbols.operators.contains(newString.toString())) ) {

                    this.symbolTable.addElement(newString.toString());
                    this.ProgramInternalForm.addToProgramInternalForm(newString.toString(), this.symbolTable.searchElement(newString.toString()));
                }

            }
        }
    }

    public void parseFile() throws IOException{
        boolean lexicallyCorrectProgram=true;
        File file = new File(this.filename);
        Scanner scannerFile = null;
        try {
            scannerFile = new Scanner(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

        while(scannerFile.hasNextLine()) {
            String line = scannerFile.nextLine();
            try {
                parseLine(line);
            } catch (FileNotFoundException | ParseException e) {
                e.printStackTrace();
                lexicallyCorrectProgram = false;
            }
        }
        scannerFile.close();
        FileWriter myWriter = new FileWriter("C:/Users/komsa/FLCD/Laboratory3-final/Laboratory3-final/src/Files/ST.out");
        BufferedWriter bw = new BufferedWriter(myWriter);
        bw.write(symbolTable.getHashTable());
        bw.newLine();

        bw.close();
        this.ProgramInternalForm.printProgramInternalForm();

        FileWriter PIF_FileWriter = new FileWriter("C:/Users/komsa/FLCD/Laboratory3-final/Laboratory3-final/src/Files/PIF.out");
        BufferedWriter bufferedWriterPIF = new BufferedWriter(PIF_FileWriter);
        bufferedWriterPIF.write(this.ProgramInternalForm.FormatPIFWriter());
        bufferedWriterPIF.newLine();

        bufferedWriterPIF.close();

        if(lexicallyCorrectProgram){
            System.out.println("Program is lexically correct! ");
        }
    }

    private boolean isInAlphabet(Character character) {
        if(character >= 'a' && character <= 'z')
            return true;
        if(character >= 'A' && character <= 'Z')
            return true;
        if(character >= '0' && character <= '9')
            return true;
        if(character.equals('_'))
            return true;
        if(character.equals('<'))
            return true;
        if(character.equals('>'))
            return true;
        if(character.equals('/'))
            return true;
        return false;
    }

}