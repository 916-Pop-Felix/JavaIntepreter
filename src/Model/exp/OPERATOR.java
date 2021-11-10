package Model.exp;

public enum OPERATOR{
    ADD("+"), // arithmetic
    SUB("-"),
    MUL("*"),
    DIV("/"),
    GREATER(">"),  //relational
    GRTOREQUAL(">="),
    EQUAL("=="),
    NOTEQUAL("!="),
    LESSER("<"),
    LESSOREQUAL("<="),
    AND("&&"), //logical
    OR("||");
    public final String label;
    private OPERATOR(String _sgn){
        this.label=_sgn;
    }
    public String getSign(){return this.label;}
}