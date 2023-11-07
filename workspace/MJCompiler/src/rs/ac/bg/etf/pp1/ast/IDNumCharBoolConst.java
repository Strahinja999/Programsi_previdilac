// generated with ast extension for cup
// version 0.8
// 23/7/2023 22:39:41


package rs.ac.bg.etf.pp1.ast;

public class IDNumCharBoolConst implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String ime;
    private NumCharBoolConst NumCharBoolConst;

    public IDNumCharBoolConst (String ime, NumCharBoolConst NumCharBoolConst) {
        this.ime=ime;
        this.NumCharBoolConst=NumCharBoolConst;
        if(NumCharBoolConst!=null) NumCharBoolConst.setParent(this);
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime=ime;
    }

    public NumCharBoolConst getNumCharBoolConst() {
        return NumCharBoolConst;
    }

    public void setNumCharBoolConst(NumCharBoolConst NumCharBoolConst) {
        this.NumCharBoolConst=NumCharBoolConst;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NumCharBoolConst!=null) NumCharBoolConst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NumCharBoolConst!=null) NumCharBoolConst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NumCharBoolConst!=null) NumCharBoolConst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IDNumCharBoolConst(\n");

        buffer.append(" "+tab+ime);
        buffer.append("\n");

        if(NumCharBoolConst!=null)
            buffer.append(NumCharBoolConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IDNumCharBoolConst]");
        return buffer.toString();
    }
}
