// generated with ast extension for cup
// version 0.8
// 23/7/2023 22:39:41


package rs.ac.bg.etf.pp1.ast;

public class RelEq extends Relop {

    public RelEq () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RelEq(\n");

        buffer.append(tab);
        buffer.append(") [RelEq]");
        return buffer.toString();
    }
}
