// generated with ast extension for cup
// version 0.8
// 23/7/2023 22:39:41


package rs.ac.bg.etf.pp1.ast;

public class ConstructorAndMethod1 extends ConstructorAndMethod01 {

    private ConstructorList ConstructorList;
    private MethodList MethodList;

    public ConstructorAndMethod1 (ConstructorList ConstructorList, MethodList MethodList) {
        this.ConstructorList=ConstructorList;
        if(ConstructorList!=null) ConstructorList.setParent(this);
        this.MethodList=MethodList;
        if(MethodList!=null) MethodList.setParent(this);
    }

    public ConstructorList getConstructorList() {
        return ConstructorList;
    }

    public void setConstructorList(ConstructorList ConstructorList) {
        this.ConstructorList=ConstructorList;
    }

    public MethodList getMethodList() {
        return MethodList;
    }

    public void setMethodList(MethodList MethodList) {
        this.MethodList=MethodList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstructorList!=null) ConstructorList.accept(visitor);
        if(MethodList!=null) MethodList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstructorList!=null) ConstructorList.traverseTopDown(visitor);
        if(MethodList!=null) MethodList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstructorList!=null) ConstructorList.traverseBottomUp(visitor);
        if(MethodList!=null) MethodList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstructorAndMethod1(\n");

        if(ConstructorList!=null)
            buffer.append(ConstructorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodList!=null)
            buffer.append(MethodList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstructorAndMethod1]");
        return buffer.toString();
    }
}
