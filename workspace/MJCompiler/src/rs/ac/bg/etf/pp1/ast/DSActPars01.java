// generated with ast extension for cup
// version 0.8
// 23/7/2023 22:39:41


package rs.ac.bg.etf.pp1.ast;

public class DSActPars01 extends DesignatorStatementList {

    private ActPars01 ActPars01;

    public DSActPars01 (ActPars01 ActPars01) {
        this.ActPars01=ActPars01;
        if(ActPars01!=null) ActPars01.setParent(this);
    }

    public ActPars01 getActPars01() {
        return ActPars01;
    }

    public void setActPars01(ActPars01 ActPars01) {
        this.ActPars01=ActPars01;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActPars01!=null) ActPars01.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActPars01!=null) ActPars01.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActPars01!=null) ActPars01.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DSActPars01(\n");

        if(ActPars01!=null)
            buffer.append(ActPars01.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DSActPars01]");
        return buffer.toString();
    }
}
