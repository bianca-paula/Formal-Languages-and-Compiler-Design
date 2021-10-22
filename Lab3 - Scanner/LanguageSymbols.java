import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class LanguageSymbols {
    public String filename;
    public List<String> reservedWords;
    public List<String> separators;
    public List<String> operators;

    public LanguageSymbols(String filename){
        this.filename = filename;
        this.reservedWords = new ArrayList<>();
        this.separators = new ArrayList<>();
        this.operators = new ArrayList<>();
    }

    public void readLanguageSymbols() throws IOException {
        for(int i=2; i<=20; i++){
            String currentToken = Files.readAllLines(Paths.get(this.filename)).get(i);
            //System.out.println(currentToken);
            this.reservedWords.add(currentToken);

        }
        //System.out.println("Reserved words: ");
        //System.out.println(reservedWords);

        for(int i=21; i<33; i++){
            String currentToken = Files.readAllLines(Paths.get(this.filename)).get(i);
            //System.out.println(currentToken);
            this.separators.add(currentToken);

        }
        //System.out.println("Separators: ");
        //System.out.println(separators);
        for(int i=33; i<=42; i++){
            String currentToken = Files.readAllLines(Paths.get(this.filename)).get(i);
            //System.out.println(currentToken);
            this.operators.add(currentToken);

        }
        //System.out.println("Operators: ");
        //System.out.println(operators);


    }
}
