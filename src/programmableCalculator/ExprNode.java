package programmableCalculator;

/**
 * <p>
 * Title: ExprNode Class.
 * </p>
 * 
 * <p>
 * Description: The Java/FX-based controller class for the Programs window.
 * </p>
 * 
 * <p>
 * Copyright: Lynn Robert Carter 2019
 * </p>
 * 
 * @author  Lynn Robert Carter
 * 
 * @version 1.00 2019-03-31 The Java/FX-based controller class for the Programs window.
 * 
 * 							
 */


import lexer.Token;
import lexer.Token.Kind;

public class ExprNode {
	
	private ExprNode left;
	private ExprNode right;
	private Token op;
	private boolean isBinary;
	
	public ExprNode(Token o, boolean b, ExprNode l, ExprNode r) {
		op = o;
		isBinary = b;
		left = l;
		right = r;
	}
	
	public Token getOp() {
		return op;
	}

	public ExprNode getLeft() {
		
		return left;
	}
	

	public ExprNode getRight() {
		return right;
	}
	
	private String toString(String indent) {
		if (op.getTokenKind() == Kind.SYMBOL)
			
		if(left==null) {
				return indent + "Leaf: " + op;
			}
			
		else if (isBinary)
				return indent + "Op: " + op + "\n" + 
				left.toString(indent + "   ") + "\n" + right.toString(indent + "   ");
			
			else
				return indent + "Op: " + op + "\n" + right.toString(indent + "   ");
		else
			return indent + "Leaf: " + op;
	}
	
	public String toString() {
		return this.toString("");
	}
}
