// generated with ast extension for cup
// version 0.8
// 23/7/2023 22:39:41


package rs.ac.bg.etf.pp1.ast;

public class ForeachStmt extends Statement {

    private Designator Designator;
    private String iteratorName;
    private EqOrGtHelper EqOrGtHelper;
    private Statement Statement;

    public ForeachStmt (Designator Designator, String iteratorName, EqOrGtHelper EqOrGtHelper, Statement Statement) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.iteratorName=iteratorName;
        this.EqOrGtHelper=EqOrGtHelper;
        if(EqOrGtHelper!=null) EqOrGtHelper.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public String getIteratorName() {
        return iteratorName;
    }

    public void setIteratorName(String iteratorName) {
        this.iteratorName=iteratorName;
    }

    public EqOrGtHelper getEqOrGtHelper() {
        return EqOrGtHelper;
    }

    public void setEqOrGtHelper(EqOrGtHelper EqOrGtHelper) {
        this.EqOrGtHelper=EqOrGtHelper;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(EqOrGtHelper!=null) EqOrGtHelper.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(EqOrGtHelper!=null) EqOrGtHelper.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(EqOrGtHelper!=null) EqOrGtHelper.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForeachStmt(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+iteratorName);
        buffer.append("\n");

        if(EqOrGtHelper!=null)
            buffer.append(EqOrGtHelper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForeachStmt]");
        return buffer.toString();
    }
}
