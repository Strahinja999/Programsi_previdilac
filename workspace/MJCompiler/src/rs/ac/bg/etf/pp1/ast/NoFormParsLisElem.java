// generated with ast extension for cup
// version 0.8
// 23/7/2023 22:39:41


package rs.ac.bg.etf.pp1.ast;

public class NoFormParsLisElem extends FormPars01 {

    public NoFormParsLisElem () {
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
        buffer.append("NoFormParsLisElem(\n");

        buffer.append(tab);
        buffer.append(") [NoFormParsLisElem]");
        return buffer.toString();
    }
}
