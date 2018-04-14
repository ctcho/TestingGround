package basic;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.Bindings;

public class StringToBoolean {
	public static void main(String[] args) throws ScriptException {
		String s = "I have several cats and dogs, but no frogs. I wish I had a dragon, though...";
		String query = "(cats or dogs) and frogs and not(elephants or dragons)";
		String[] terms = query.split(" and ");
		System.out.println(Boolean.parseBoolean("5 == 5"));
		ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("js");
	    engine.put("s", s);
	    //engine.createBindings();
	    Object result = engine.eval("s.contains(\"dogs\")");
	    System.out.println(Boolean.TRUE.equals(result));
	    System.out.println(Boolean.TRUE.equals(engine.eval("s.contains(\"elephants\") || s.contains(\"dragons\")")));
	    //System.out.println(5 == 5 || 5 == 7);
	    for (String term: terms) {
	    	if (term.contains("not")) {
	    		evaluate(term.substring(4, term.length()- 1), true, manager, engine);
	    	}
	    	else if (term.contains("(")) {
	    		evaluate(term.substring(1, term.length()- 1), false, manager, engine);
	    	}
	    	else {
	    		evaluate(term, false, manager, engine);
	    	}
	    }
	    
	}
	
	public static void evaluate(String term, boolean negate, ScriptEngineManager manager, ScriptEngine engine)
	throws ScriptException {
		String[] parts = term.split(" or ");
		String predicate = "";
		if (negate)
			predicate += "!(";
		for(int i=0; i<parts.length - 1; i++) {
			String part = "term" + i;
			System.out.println(parts[i]);
			engine.put(part, parts[i]);
			predicate += "s.contains(term" + i + ") || ";
		}
		String part = "term" + (parts.length-1);
		engine.put(part, parts[parts.length-1]);
		System.out.println(parts[parts.length-1]);
		predicate += "s.contains(term" + (parts.length-1) + ")";
		if (negate)
			predicate += ")";
		Object result = engine.eval(predicate);
		System.out.println("engine bindings: " + engine);
		System.out.println(predicate);
    	System.out.println(term + " is in the string? " + Boolean.TRUE.equals(result));
	}
}
