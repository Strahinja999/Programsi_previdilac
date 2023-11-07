// generated with ast extension for cup
// version 0.8
// 23/7/2023 22:39:41


package rs.ac.bg.etf.pp1.ast;

public class VarDeclNoErr extends VarDecl {

    private Type Type;
    private VarDeclElemList VarDeclElemList;

    public VarDeclNoErr (Type Type, VarDeclElemList VarDeclElemList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarDeclElemList=VarDeclElemList;
        if(VarDeclElemList!=null) VarDeclElemList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarDeclElemList getVarDeclElemList() {
        return VarDeclElemList;
    }

    public void setVarDeclElemList(VarDeclElemList VarDeclElemList) {
        this.VarDeclElemList=VarDeclElemList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarDeclElemList!=null) VarDeclElemList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarDeclElemList!=null) VarDeclElemList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarDeclElemList!=null) VarDeclElemList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclNoErr(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclElemList!=null)
            buffer.append(VarDeclElemList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclNoErr]");
        return buffer.toString();
    }
}
