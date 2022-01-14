package Model;

public class PIF_Pair {
    public String token;
    public Integer positionInST;

    public PIF_Pair(String token, Integer positionInST){
        this.token = token;
        this.positionInST=positionInST;
    }

    @Override
    public String toString(){
        return this.token + "  |  " + this.positionInST.toString() + "\n";
    }

}