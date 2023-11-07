// generated with ast extension for cup
// version 0.8
// 23/7/2023 22:39:41


package rs.ac.bg.etf.pp1.ast;

public class FormParsElem implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private Type Type;
    private String parsName;
    private LeftRightSquareBracket LeftRightSquareBracket;

    public FormParsElem (Type Type, String parsName, LeftRightSquareBracket LeftRightSquareBracket) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.parsName=parsName;
        this.LeftRightSquareBracket=LeftRightSquareBracket;
        if(LeftRightSquareBracket!=null) LeftRightSquareBracket.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getParsName() {
        return parsName;
    }

    public void setParsName(String parsName) {
        this.parsName=parsName;
    }

    public LeftRightSquareBracket getLeftRightSquareBracket() {
        return LeftRightSquareBracket;
    }

    public void setLeftRightSquareBracket(LeftRightSquareBracket LeftRightSquareBracket) {
        this.LeftRightSquareBracket=LeftRightSquareBracket;
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
        if(Type!=null) Type.accept(visitor);
        if(LeftRightSquareBracket!=null) LeftRightSquareBracket.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(LeftRightSquareBracket!=null) LeftRightSquareBracket.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(LeftRightSquareBracket!=null) LeftRightSquareBracket.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsElem(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+parsName);
        buffer.append("\n");

        if(LeftRightSquareBracket!=null)
            buffer.append(LeftRightSquareBracket.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsElem]");
        return buffer.toString();
    }
}
