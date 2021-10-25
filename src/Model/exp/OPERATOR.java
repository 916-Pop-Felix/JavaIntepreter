package Model.exp;

public enum OPERATOR{
    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/"),
    AND("&&"),
    OR("||");
    public final String label;
    private OPERATOR(String _sgn){
        this.label=_sgn;
    }
    public String getSign(){return this.label;}
}