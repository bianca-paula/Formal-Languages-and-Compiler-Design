import java.util.ArrayList;
import java.util.List;

public class PIF {
    public List<PIF_Pair> ProgramInternalForm;
    public PIF(){
        this.ProgramInternalForm = new ArrayList<>();
    }

    public void addToPIF(String token, Integer tokenPositionInST){
        PIF_Pair element = new PIF_Pair(token, tokenPositionInST);
        this.ProgramInternalForm.add(element);
    }

    public void printPIF(){
        for(PIF_Pair element : ProgramInternalForm){
            System.out.println(element.toString());
        }
    }

    public String FormatPIFWriter() {
        String formatPIF="";
        for(PIF_Pair element : ProgramInternalForm){
            formatPIF+=element.toString();
        }
        return formatPIF;
    }
}
