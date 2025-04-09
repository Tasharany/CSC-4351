package FindEscape;

public class FindEscape {
	Symbol.Table escEnv = new Symbol.Table();
	private int depth = 0;

	public FindEscape(Absyn.Exp e) {
		traverseExp(depth, e);
	}

	void traverseVar(int depth, Absyn.Var v) {
		if (v instanceof Absyn.SimpleVar)
			traverseVar(depth, (Absyn.SimpleVar)v);
		else if (v instanceof Absyn.FieldVar)
			traverseVar(depth, (Absyn.FieldVar)v);
		else if (v instanceof Absyn.SubscriptVar)
			traverseVar(depth, (Absyn.SubscriptVar)v);
	}

	void traverseVar(int depth, Absyn.SimpleVar v) {
		Escape escape = (Escape)escEnv.get(v.name);
		if (escape != null && depth > escape.depth)
			escape.setEscape();
	}

	void traverseVar(int depth, Absyn.FieldVar v) {
		traverseVar(depth, v.var);
	}

	void traverseVar(int depth, Absyn.SubscriptVar v) {
		traverseVar(depth, v.var);
		traverseExp(depth, v.index);
	}

	void traverseExp(int depth, Absyn.CallExp e) {
		// Mark function as non-leaf
		for (Absyn.ExpList args = e.args; args != null; args = args.tail)
			traverseExp(depth, args.head);
	}

	void traverseDec(int depth, Absyn.VarDec d) {
		traverseExp(depth, d.init);
		d.escape = false; // Initialize to false
		escEnv.put(d.name, new VarEscape(depth, d));
	}

	void traverseDec(int depth, Absyn.FunctionDec d) {
		for (Absyn.FunctionDec f = d; f != null; f = f.next) {
			f.leaf = true; // Initialize to true
			escEnv.beginScope();
			for (Absyn.FieldList params = f.params; params != null; params = params.tail) {
				params.escape = false; // Initialize to false
				escEnv.put(params.name, new FormalEscape(depth + 1, params));
			}
			traverseExp(depth + 1, f.body);
			escEnv.endScope();
		}
	}
}




