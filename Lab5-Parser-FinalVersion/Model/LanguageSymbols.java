package Model;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;


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
        for(int i=2; i<=19; i++){
            String currentToken = Files.readAllLines(Paths.get(this.filename)).get(i);
            this.reservedWords.add(currentToken);

        }

        for(int i=20; i<30; i++){
            String currentToken = Files.readAllLines(Paths.get(this.filename)).get(i);
            this.operators.add(currentToken);
        }

        for(int i=30; i<41; i++){
            String currentToken = Files.readAllLines(Paths.get(this.filename)).get(i);
            this.separators.add(currentToken);
        }

    }
}