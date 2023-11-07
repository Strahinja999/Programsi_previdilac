package rs.ac.bg.etf.pp1;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import jdk.tools.jlink.internal.ImagePluginStack;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor {

	boolean errorDetected = false;
	Obj currentMethod = null;
	Struct lastVisitedType;
	boolean returnFound = false;
	int nVars;
	public static final Struct boolType = new Struct(Struct.Bool);
	boolean insideLoop = false;
	Obj lastVisitedDesignator = null;
	
	public SemanticAnalyzer() {
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
		Tab.chrObj.getLocalSymbols().iterator().next().setFpPos(1);
		Tab.ordObj.getLocalSymbols().iterator().next().setFpPos(1);
		Tab.lenObj.getLocalSymbols().iterator().next().setFpPos(1);
	}

	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public void visit(Program program) {
		Obj mainObj = Tab.currentScope.findSymbol("main");
		if(mainObj != null) {
			if(mainObj.getKind() != Obj.Meth) {
				report_error("Main nije metoda ", program);
			}
			if(mainObj.getType()!= Tab.noType) {
				report_error("Main nije void ", program);
			}
			if(mainObj.getLevel() != 0) {
				report_error("Main ima parametre ", program);
			}
		}else {
			report_error("U programu ne postoji simbol sa imenom main ", program);
		}
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
	}

	public void visit(ProgName progName) {
		if (imePostoji(progName.getName())) {
			progName.obj = Tab.noObj;
			report_error("Simbol sa imenom " + progName.getName() + " se vec nalazi u trenutnom opsegu.", progName);
		}else {
			progName.obj = Tab.insert(Obj.Prog, progName.getName(), Tab.noType);
		}
		
		Tab.openScope();   	
	}

	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen simbol " + type.getTypeName() + " u tabeli simbola", type);
			type.struct = Tab.noType;
		} 
		else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} 
			else {
				report_error("Simbol " + type.getTypeName() + " ne predstavlja tip ", type);
				type.struct = Tab.noType;
			}
		}
		lastVisitedType = type.struct;
	}

	public void visit(MethodDecl methodDecl) {
		if (!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Funkcija " + currentMethod.getName() + " nema return iskaz a nije deklarisana kao VOID", methodDecl);
		}
		
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		
		int level = 0;
		for (Obj obj : currentMethod.getLocalSymbols()) {
			if(obj.getFpPos() > 0) {
				level++;
			}
		}
		currentMethod.setLevel(level);
		
		returnFound = false;
		currentMethod = null;
	}
	
	public static boolean imePostoji(String name) {
		return Tab.currentScope.findSymbol(name) != null;
	}

	public void visit(RetType methodRetType) {
		String methodName =((MethodDecl) methodRetType.getParent()).getMethodName();
		if(imePostoji(methodName)) {
			methodRetType.obj = Tab.noObj;
			
			currentMethod = Tab.noObj;
			report_error("Naziv metode " + currentMethod.getName() + " vec postoji u trenutnom opsegu. ", methodRetType);
		}else {
			currentMethod = Tab.insert(Obj.Meth, methodName, methodRetType.getType().struct);
			methodRetType.obj = currentMethod;
		}
		Tab.openScope();
	}
	
	public void visit (RetVoid methodRetVoid) {
		String methodName = ((MethodDecl) methodRetVoid.getParent()).getMethodName();
		if(imePostoji(methodName)) {
			methodRetVoid.obj = Tab.noObj;
			
			currentMethod = Tab.noObj;
			report_error("Naziv metode " + currentMethod.getName() + " vec postoji u trenutnom opsegu. ", methodRetVoid);
		}else {
			currentMethod = Tab.insert(Obj.Meth, methodName, Tab.noType);
			methodRetVoid.obj = currentMethod;
		}
		Tab.openScope();
	}
	
	public void visit(IDNumCharBoolConst konstante) {
		if(imePostoji(konstante.getIme())) {
			report_error("Konstanta " + konstante.getIme() + " vec postoji u trenutnom opsegu. ", konstante);
		}
		else {
			if(konstante.getNumCharBoolConst() instanceof NumConst) {
				if (lastVisitedType.getKind() != Struct.Int) {
					report_error("Konstanta " + konstante.getIme() + " nije odgovarajuceg tipa. ", konstante);
				}else {
					Tab.insert(Obj.Con, konstante.getIme(), lastVisitedType).setAdr(((NumConst)konstante.getNumCharBoolConst()).getNumber());
				}
			}else if (konstante.getNumCharBoolConst() instanceof CharConst) {
				if(lastVisitedType.getKind() != Struct.Char) {
					report_error("Konstanta " + konstante.getIme() + " nije odgovarajuceg tipa. ", konstante);
				}else {
					Tab.insert(Obj.Con, konstante.getIme(), lastVisitedType).setAdr(((CharConst)konstante.getNumCharBoolConst()).getCharacter());					
				}
			}else if(konstante.getNumCharBoolConst() instanceof BoolConst) {
				if(lastVisitedType.getKind() != Struct.Bool) {
					report_error("Konstanta " + konstante.getIme() + " nije odgovarajuceg tipa. ", konstante);
				}else {
					if(((BoolConst)konstante.getNumCharBoolConst()).getBool()) {
						Tab.insert(Obj.Con, konstante.getIme(), lastVisitedType).setAdr(1);
					}else {
						Tab.insert(Obj.Con, konstante.getIme(), lastVisitedType).setAdr(0);
					}					
				}
			}
		}
	}
	
	public void visit(VarDeclElem variable) {
		if(imePostoji(variable.getVariableName())) {
			report_error("Promenljiva " + variable.getVariableName() + " vec postoji u trenutnom opsegu. ", variable);
		}else {
			if(variable.getLeftRightSquareBracket() instanceof LRSQBracket) {
				Tab.insert(Obj.Var, variable.getVariableName(), new Struct(Struct.Array, lastVisitedType));
			}else if (variable.getLeftRightSquareBracket() instanceof NoLRBSQracket) {
				Tab.insert(Obj.Var, variable.getVariableName(), lastVisitedType);
			}
		}
	}
	
	public void visit(FormParsElem formalParameters) {
		if(imePostoji(formalParameters.getParsName())) {
			report_error("Formalni parametar " + formalParameters.getParsName() + " vec postoji u trenutnom opsegu. ", formalParameters);
		}else {
			if(formalParameters.getLeftRightSquareBracket() instanceof LRSQBracket) {
				Tab.insert(Obj.Var, formalParameters.getParsName(), new Struct(Struct.Array, lastVisitedType)).setFpPos(Tab.currentScope.getnVars());
			}else if (formalParameters.getLeftRightSquareBracket() instanceof NoLRBSQracket) {
				Tab.insert(Obj.Var, formalParameters.getParsName(), lastVisitedType).setFpPos(Tab.currentScope.getnVars());
			}
		}
	}

	public void visit(ReturnStmt returnStmt){
		if (returnStmt.getExpr01() instanceof YesExpr) {
			returnFound = true;
			if (!currentMethod.getType().compatibleWith(((YesExpr) returnStmt.getExpr01()).getExpr().struct)) {
				report_error("Tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije "
						+ currentMethod.getName(), returnStmt);
			} 
		} else {
			if(currentMethod.getType() != Tab.noType) {
				report_error("Return bez izraza unutar funkcije "
						+ currentMethod.getName() + " koja nije void", returnStmt);
			}
		}
	}
	
	public boolean proveriDesigantor(Designator d) {
		return d.obj.getKind() == Obj.Var || d.obj.getKind() == Obj.Fld || d.obj.getKind() == Obj.Elem;
	}
	
	public void visit(DesignatorStatement designatorStmt) {
		if(designatorStmt.getDesignatorStatementList() instanceof DSAssignop) {
			if(!proveriDesigantor(designatorStmt.getDesignator())) {
				report_error("Designator ne oznacava promenljivu, element niza ili polje objekta! ", designatorStmt);
			} 
			DSAssignop assOp = (DSAssignop)designatorStmt.getDesignatorStatementList();
			if(!assOp.getExpr().struct.assignableTo(designatorStmt.getDesignator().obj.getType())) {
				report_error("Designator i dodeljen izraz nisu kompatibilni za dodelu. ", designatorStmt);
			}
		}else if(designatorStmt.getDesignatorStatementList() instanceof DbPlus ||
				designatorStmt.getDesignatorStatementList() instanceof DbMinus) {
			if(!proveriDesigantor(designatorStmt.getDesignator())) {
				report_error("Designator ne oznacava promenljivu, element niza ili polje objekta! ", designatorStmt);
			}
			if(designatorStmt.getDesignator().obj.getType() != Tab.intType) {
				report_error("Designator nije tipa int! ", designatorStmt);
			}
		}else if(designatorStmt.getDesignatorStatementList() instanceof DSActPars01) {
			if(designatorStmt.getDesignator().obj.getKind() != Obj.Meth) {
				report_error("Designator nije nestaticka metoda ili globalna funkcija programa. ", designatorStmt);
			}
		}else if(designatorStmt.getDesignatorStatementList() instanceof DSFindAny) {
			if(designatorStmt.getDesignator().obj.getKind() != Obj.Var) {
				report_error("Designator ne oznacava promenljivu ", designatorStmt);
			} 
			if(designatorStmt.getDesignator().obj.getType() != boolType) {
				report_error("Designator nije tipa boolean ", designatorStmt);
			}
			DSFindAny findAny = (DSFindAny)designatorStmt.getDesignatorStatementList();
			Struct desType = findAny.getDesignator().obj.getType();
			if(desType.getKind() != Struct.Array || desType.getElemType() != Tab.intType && desType.getElemType() != Tab.charType && desType.getElemType() != boolType) {
				report_error("Designator sa desne strane dodele nije niz ugradjenog tipa ", findAny);
			}
			if(!desType.getElemType().equals(findAny.getExpr().struct)) {
				report_error("Vrednost koja se pretrazuje nije istog tipa kao element niza ", findAny);
			}
		}else if(designatorStmt.getDesignatorStatementList() instanceof DSSwap) {
			DSSwap swap = (DSSwap) designatorStmt.getDesignatorStatementList();
			if(designatorStmt.getDesignator().obj.getType().getKind() != Struct.Array) {
				report_error("Designator nije niz", swap);
			}
			if(swap.getExpr().struct != Tab.intType || swap.getExpr1().struct != Tab.intType) {
				report_error("Neki od izraza nije int tipa", swap);
			}
		}
			
	}
	
	public void visit(FactorDesignator designator) {
		designator.struct = designator.getDesignator().obj.getType();							
		if(designator.getLPActParsRP01() instanceof LPActParsRP) {
			LPActParsRP actPars = (LPActParsRP) designator.getLPActParsRP01();
			if(actPars.getActPars01() instanceof YesActPars) {
				if(designator.getDesignator().obj.getKind() != Obj.Meth) {
					report_error("Designator mora biti globalna funkcija(metoda)", designator);
					designator.struct = Tab.noType;
				}
			}
		}
	}

	public void visit(FactorNumConst numConst) {
		numConst.struct = Tab.intType;
	}
	
	public void visit(FactorCharConst charConst) {
		charConst.struct = Tab.charType;
	}
	
	public void visit(FactorBoolConst boolConst) {
		boolConst.struct = boolType;
	}
	
	public void visit(FactorNew newObj) {
		if(newObj.getFactorList() instanceof FactorListExpr) {
			FactorListExpr factorListExpr = (FactorListExpr) newObj.getFactorList();
			if(factorListExpr.getExpr().struct != Tab.intType) {
				report_error("Tip mora biti int", newObj);
				newObj.struct = Tab.noType;
			}else {
				newObj.struct = new Struct(Struct.Array, newObj.getType().struct);								
			}
		}else {
			newObj.struct = Tab.noType;
		}
	}
	public void visit(FactorExpr expr) {
		expr.struct = expr.getExpr().struct;
	}
	
	public void visit(FactorMax max) {
		max.struct = Tab.intType;
		if(max.getDesignator().obj.getType().getKind() != Struct.Array){
			report_error("Designator nije tipa niz", max);
		}
	}
	

	public void visit(MulopFactorList mulop) {
		Struct termS = mulop.getTerm().struct;
		Struct factorS = mulop.getFactor().struct;
		if (termS.equals(factorS) && termS == Tab.intType)
			mulop.struct = termS;
		else {
			report_error("Nekompatibilni tipovi u izrazu za mulop.", mulop);
			mulop.struct = Tab.noType;
		} 
	}
	
	public void visit(NoMulopFactorList noMulop) {
		noMulop.struct = noMulop.getFactor().struct;
	}
	
	public void visit(NoAddopTermList addop) {
		addop.struct = addop.getTerm().struct;
	}
	
	public void visit(NoAddopTermListMinus addop) {
		addop.struct = addop.getTerm().struct;
		if(addop.getTerm().struct != Tab.intType) {
			report_error("Nije int tip", addop);
			addop.struct = Tab.noType;
		}
	}

	public void visit(WhileStmt whileStmt) {
		insideLoop = false;
	}
	
	public void visit(ForeachStmt foreachStmt) {
		Obj designator = foreachStmt.getDesignator().obj;
		if(designator.getType().getKind() != Struct.Array) {
			report_error("Designator ne predstavlja niz!", foreachStmt);
		} else {
			Obj iterator = Tab.find(foreachStmt.getIteratorName());
			if(iterator == null) {
				report_error("Iterator nije definisan.", foreachStmt);
			} else {
				if(iterator.getKind() != Obj.Var || iterator.getFpPos() > 0) {
					report_error("Iterator nije globalna ili lokalna promenljiva.", foreachStmt);
				}
				if(designator.getType().getElemType().getKind() != iterator.getType().getKind()) {
					report_error("Designator i iterator nisu istog tipa.", foreachStmt);
				}
			}
		}
		insideLoop = false;
	}
	
	public void visit(LeftParenHelper lpHelper) {
		insideLoop = true;
	}
	
	public void visit(EqOrGtHelper eqgtHelper) {
		insideLoop = true;
	}
	
	
	public void visit(BreakStmt breakStmt) {
		if(!insideLoop) {
			report_error("Nesipravno koriscenje break-a, ne nalazite se u while/foreach pelji.", breakStmt);
		}
	}
	
	public void visit(ContinueStmt continueStmt) {
		if(!insideLoop) {
			report_error("Nesipravno koriscenje continue-a, ne nalazite se u while/foreach pelji.", continueStmt);
		}
	}
	
	public void visit(CondFact condFact) {
		if(condFact.getRelOpExpr01() instanceof RelOpExpr) {
			RelOpExpr relOp = (RelOpExpr)condFact.getRelOpExpr01();
			if(!relOp.getExpr().struct.compatibleWith(condFact.getExpr().struct)){
				report_error("Izrazi nisu kompatibilni.", condFact);
			} else if(relOp.getExpr().struct.isRefType()
					&& (!(relOp.getRelop() instanceof RelNotEq) &&
							!(relOp.getRelop() instanceof RelEq))) {
				report_error("Koriste se nevazeci relacioni operatori za niz ili klasu.", relOp);
			}
		}
		else if(condFact.getExpr().struct != boolType) {
			report_error("Izraz nije boolean tipa.", condFact);
		}
	}

	public void visit(ReadStmt readStmt) {
		if(!proveriDesigantor(readStmt.getDesignator())) {
			report_error("Designator ne oznacava promenljivu, element niza ili polje objekta! ", readStmt);
		}else {
			if(!(readStmt.getDesignator().obj.getType() == Tab.intType ||
					readStmt.getDesignator().obj.getType() == Tab.charType ||
					readStmt.getDesignator().obj.getType() == boolType)) {
				report_error("Designator nije odgovarajuceg tipa (int, char, bool) ", readStmt);
			}
		}
	}
	
	public void visit(PrintStmt printStmt) {
		if(!(printStmt.getExpr().struct == Tab.intType ||
				printStmt.getExpr().struct == Tab.charType ||
				printStmt.getExpr().struct == boolType)) {
			report_error("Izraz nije odgovarajuceg tipa (int, char, bool) ", printStmt);
		}
	}

	public void visit(AddopTermList addExpr) {
		
		if(addExpr.getAddop() instanceof ArrayAdd) {
			addExpr.struct = Tab.intType;
			ArrayAdd arrAdd = (ArrayAdd)addExpr.getAddop();
			Expr e1 = addExpr.getExpr();
			if(e1.struct.getKind() != Struct.Array) {
				report_error("NIJE NIZ", e1);
			}
		}else {
			Struct te = addExpr.getExpr().struct;
			Struct t = addExpr.getTerm().struct;
			if (te.equals(t) && te == Tab.intType)
				addExpr.struct = te;
			else {
				report_error("Nekompatibilni tipovi u izrazu za addop.", addExpr);
				addExpr.struct = Tab.noType;
			} 
		}
		
		
	}
	
	public void visit(NoActPars noActPars) {
		if(lastVisitedDesignator.getLevel() > 0) {
			report_error("Metoda koja ocekuje parametre se poziva bez parametara ", noActPars);
		}
	}
	
	public void visit(YesActPars yesActPars) {
		//dohvatanje svih tipova stvarnih parametara u listu, poredjani su od kraja ka pocetku!
		ArrayList<Struct> stvarniParametri = new ArrayList<>();
		ActPars actPars = yesActPars.getActPars();
		while(actPars instanceof ExprList) {
			ExprList exprList = (ExprList)actPars;
			stvarniParametri.add(exprList.getExpr().struct);
			actPars = exprList.getActPars();
		}
		ExprListElem exprListElem = (ExprListElem)actPars;
		stvarniParametri.add(exprListElem.getExpr().struct);
		
		Obj methodObj;
		SyntaxNode currNode = yesActPars;
		while (!(currNode.getParent() instanceof FactorDesignator) && !(currNode.getParent() instanceof DesignatorStatement)) {
			currNode = currNode.getParent();
		}
		if (currNode.getParent() instanceof FactorDesignator) {
			methodObj = ((FactorDesignator) currNode.getParent()).getDesignator().obj;
		} else {
			methodObj = ((DesignatorStatement) currNode.getParent()).getDesignator().obj;
		}
		
		
		if(stvarniParametri.size() != methodObj.getLevel()) {
			report_error("Broj stvarnih i formalnih parametara se ne poklapa", yesActPars);
		}else {
			//provera tipova svakog parametra
			ArrayList<Struct> formalniParametri = new ArrayList<>();
			for (Obj obj : methodObj.getLocalSymbols()) {
				if(obj.getFpPos() > 0) {
					formalniParametri.add(obj.getType());
				}
			}
			for (int i = 0; i < methodObj.getLevel(); i++) {
				Struct stvarniParam = stvarniParametri.get(methodObj.getLevel() - 1 - i);
				Struct formalniParam = formalniParametri.get(i);
				if(!stvarniParam.assignableTo(formalniParam)) {
					report_error("Stvarni parametar nije dodeljiv formalnom na poziciji " + (i+1) + " funkcije " + methodObj.getName(), yesActPars);
				}
			}
		}
	}
	
	public void visit(Dummy dummy) {
		dummy.obj = lastVisitedDesignator;
	}

	public void visit(NoDesignatorList designator){
		Obj obj = Tab.find(designator.getName());
		if (obj == Tab.noObj) { 
			report_error("Simbol "+designator.getName()+" nije deklarisan! ", designator);
		} else if(obj.getKind() == Obj.Con || obj.getKind() == Obj.Var) {
			DumpSymbolTableVisitor visitor = new DumpSymbolTableVisitor();
			visitor.visitObjNode(obj);
			String s = "Koriscenje ";
			if(obj.getKind() == Obj.Con) {
				s+="konstante ";
			}else {
				if(obj.getLevel() == 0) {
					s+="globalne ";
				}else {
					s+="lokalne ";
				}
				s+="promenljive ";
			}
			s+=visitor.getOutput();
			report_info(s, designator);
		}
		lastVisitedDesignator = designator.obj = obj;
	}
	
	public void visit(DesignatorListElemExpr designatorListElemExpr) {
		Obj obj = designatorListElemExpr.getDesignatorHelper().getDesignator().obj;
		if(!obj.getType().isRefType()) {
			report_error("Designator mora biti niz.", designatorListElemExpr);
		}
		Expr expr = designatorListElemExpr.getExpr();
		if(expr.struct != Tab.intType) {
			report_error("Izraz mora biti int", designatorListElemExpr);
		}
		lastVisitedDesignator = designatorListElemExpr.obj = new Obj(Obj.Elem, null, obj.getType().getElemType());				
	}
	
	public boolean passed() {
		return !errorDetected;
	}
	
	public void visit(DSAssignopErr error) {
		report_info("Doslo je do oporavka od sintaksne greske prilikom dodele vrednosti do ; ", error);
	}
	
	public void visit(VarDeclarationListErr error) {
		report_info("Doslo je do oporavka od sintaksne greske prilikom deklaracije promenljive do , ", error);
	}
	
	public void visit(VarDeclErr error) {
		report_info("Doslo je do oporavka od sintaksne greske prilikom deklaracije promenljive do ; ", error);
	}

	public void visit(HashTagExpr hastag) {
		hastag.struct = Tab.charType;
		if(hastag.getExpr().struct != Tab.charType) {
			report_error("Prvi parametar nije tipa char ", hastag);
		}
		if(hastag.getExpr1().struct != Tab.intType) {
			report_error("Drugi parametar nije tipa int ", hastag);
		}
	}
	
}

