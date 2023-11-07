// generated with ast extension for cup
// version 0.8
// 23/7/2023 22:39:41


package rs.ac.bg.etf.pp1.ast;

public class ReturnStmt extends Statement {

    private Expr01 Expr01;

    public ReturnStmt (Expr01 Expr01) {
        this.Expr01=Expr01;
        if(Expr01!=null) Expr01.setParent(this);
    }

    public Expr01 getExpr01() {
        return Expr01;
    }

    public void setExpr01(Expr01 Expr01) {
        this.Expr01=Expr01;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr01!=null) Expr01.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr01!=null) Expr01.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr01!=null) Expr01.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ReturnStmt(\n");

        if(Expr01!=null)
            buffer.append(Expr01.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ReturnStmt]");
        return buffer.toString();
    }
}
