package Model;

import java.util.ArrayList;
import java.util.List;

public class PIF {
    public List<PIF_Pair> programInternalForm;
    public PIF(){
        this.programInternalForm = new ArrayList<>();
    }

    public void addToProgramInternalForm(String token, Integer tokenPositionInST){
        PIF_Pair element = new PIF_Pair(token, tokenPositionInST);
        this.programInternalForm.add(element);
    }

    public String FormatPIFWriter(){
        String formatOfPIF="";
        for(PIF_Pair element : programInternalForm){
            formatOfPIF+=element.toString();
        }
        return formatOfPIF;
    }

    public void printProgramInternalForm(){
        for(PIF_Pair element : programInternalForm){
            System.out.println(element.toString());
        }
    }

}