package programmableCalculator;


/**
 * <p>
 * Title: ProgramInterface Class.
 * </p>
 * 
 * <p>
 * Description: The Java/FX-based user interface for the Programs window. The class
 * works with Lexer api and tokenizers to perform various computations.
 * </p>
 * 
 * <p>
 * Copyright: Sawan 2019
 * </p>
 * 
 * @author  Sawan Gaba
 * 
 * @version 1.00 2019-03-31 Fist UI for Program window using Lexer api. by sawan
 * @version 2.00 2019-05-05 Fist UI for now supports print and input commands. by Sawan
 * 							
 */




import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import definitionDemo.DefinitionsUserInterface;
import dictionary.Dictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lexer.Lexer;
import lexer.Token;
import lexer.Token.Kind;

public class ProgramInterface {

	Button create = new Button("Create");
	Button Delete = new Button("Delete");
	Button Run = new Button("Run");
	Button Debug = new Button("Debug");
	Button edit = new Button("edit");
	Button save = new Button("save");
	Button load = new Button("List");
	Button loadList = new Button("Load");
	ObservableList<String> List = FXCollections.<String>observableArrayList();
	ListView<String> listView = new ListView<String>(List);
	File fileload= null;
	Stage primaryStage= new Stage();
	Stage c= new Stage();
	String a,b,d;
	String[] e;
	static TextArea debug= new TextArea("");
    HBox hbox = new HBox(listView);
	static TextArea area= new TextArea();
	TextArea area2= new TextArea();
	Label DebugField= new Label("Debug");
	static String debugtest= "No error";
	

	// The following are the attributes that support the scanning and lexing of the input
	private static Scanner theReader ;
	
	private static Lexer lexer;
	
	private static Token current;
	private static Token next;
	
	// The following are the stacks that are used to transform the parse output into a tree
	private static Stack<ExprNode> exprStack = new Stack<>();
	private static Stack<Token> opStack = new Stack<>();
	
	
	public ProgramInterface(Stage Stage2) {
	
		
	area2.setEditable(false);
	area2.setDisable(true);
	area.setDisable(true);
	
			// Create new stage for this pop-up window
			Stage dialog = new Stage();
			Pane thePane = new Pane();
			Stage2.setTitle("Program");
			// Make this window an Application Modal, which blocks all GUI requests to other
			// windows
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.setTitle("Program");
			dialog.initOwner(Stage2);

			Scene dialogScene = new Scene(thePane, 630, 700); // Define the window width and height

			setupButtonUI(create, 100, 50, 65);
			
			setupAreaUI(debug, 150, 400, 450);
			
			debug.setPrefHeight(200);
			 debug.setWrapText(true);
			setupLabelUI(DebugField, "Arial", 15, 50, 450, 430);
			
			setupButtonUI(load, 100, 430, 65);
			
			setupButtonUI(loadList, 40, 325, 215);
			
			setupButtonUI(Run, 100, 50, 375);
			
			setupButtonUI(Debug, 100, 180, 375);
			
			Debug.setOnAction((event) -> { 
				
				debug.setText(debugtest);
				
				String bhb = null;
				boolean xc = false;
				String areatext= area.getText();
				
					
					Pattern pat = Pattern.compile("[:?!@#$%^&,<>~`=']");
					Matcher m = pat.matcher(areatext);
					
					
				
				if(m.find()==true) {
					
					debug.setText("\nExpression:"+ areatext );
					debug.appendText("\nInvalid Symbol Present");
					
					
				}
				else {
					
					
				}
					});
			
			setupButtonUI(edit, 60, 400, 375);
			
			setupButtonUI(Delete, 70, 500, 375);
			
			setupButtonUI(save, 100, 180, 65);
		
			
			edit.setOnAction((event) -> { 
				area.setEditable(true);
					});
			
			save.setOnAction((event) -> { 
				
				save();
					
				});
			
			setupAreaUI(area, 300, 20, 125);
			
			setupAreaUI(area2, 300, 20, 420);
		
			load.setOnAction((event) -> { 
				
				DirectoryChooser directoryChooser = new DirectoryChooser();
				File selectedDirectory = directoryChooser.showDialog(c);

				if(selectedDirectory == null){
				     //No Directory selected
				}else{
				     a= selectedDirectory.getAbsolutePath();
				     listFiles();
				}
				
			});
			
			create.setOnAction((event) -> { 
				area.setText("");
				area.setDisable(false);
			});
			
			Run.setOnAction((event) -> { 
				area2.setDisable(false);
				
				
			try {
				makeMove();
			} catch (NoSuchElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			});
			
			
			
			Delete.setOnAction((event) -> { 
				delete();
			
			});

			loadList.setOnAction((event) -> { 
				area.setDisable(false);
				area.setEditable(false);
				Dictionary<String> d= new Dictionary<String>();
				
				String u= a+"\\"+listView.getSelectionModel().getSelectedItem();
				fileload= new File(u);
				
				 if(fileload != null){
			            area.setText(d.load(u, fileload));
		         }
				 
				});
		
			
			hbox.setLayoutX(382.5);
			hbox.setLayoutY(125);
			hbox.setPrefHeight(227.5);
			hbox.setPrefWidth(200);
			
			thePane.getChildren().addAll(create, Run,DebugField, Debug,debug, edit, Delete, save, area,area2, load,loadList, hbox);

			// We set the scene into dialog for this window
			Stage2.setScene(dialogScene);

			// We show the completed window to the user, making it possible for the user to
			// start
			// clicking on the various GUI widgets in order to make things happen.
			Stage2.show();
	
		
	}
	
	private void setupLabelUI(Label l, String ff, double f, double w, double x, double y) {
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		
		l.setLayoutX(x);
		l.setLayoutY(y);
	}
	
	public void delete() {
		int x= listView.getSelectionModel().getSelectedIndex();		
		
		Dictionary de= new Dictionary();
		String v= a+"\\"+listView.getSelectionModel().getSelectedItem();
		File filedelete= new File(v);
		
		System.out.println(v);
		
		if(de.delete(filedelete)) {
			System.out.println("File Deleted Successfuly");
			List.remove(x);
			area.setText("");
		}
		else {
			System.out.println("File Not Deleted");
		}
	}
	
	
	public void save(){
		FileChooser fileChooser = new FileChooser();
		
		//Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(null);
		Dictionary<String> sd= new Dictionary<String>();
		
		 
		String l= area.getText();
		sd.save(l, file);
		//Set extension filter
	
	}
	
	
	private void setupButtonUI(Button b, double w, double x, double y) {
		b.setMinWidth(w);
		b.setLayoutX(x);
		b.setLayoutY(y);
	}
	private void setupAreaUI(TextArea b, double w, double x, double y) {
		b.setPrefWidth(w);
		b.setLayoutX(x);
		b.setLayoutY(y);
	}

	public void listFiles(){
        File directoryPath = new File(a);
		
		File[] files=directoryPath.listFiles(new FilenameFilter() {
			
			public boolean accept(File dir, String name) {
				return name.endsWith(".txt");
			}
		});
		
		for (File file : files) {
			List.add(file.getName());
			
		}
	}
	

	public void makeMove() throws NoSuchElementException, FileNotFoundException{
	
		area2.setText("");	
			
		// Show the source of the expression
		Scanner j= new Scanner(area.getText());
		
		while(j.hasNextLine()) {
		
		String sd= j.nextLine();
		theReader= new Scanner (sd);
		
		
		 if (sd.contains("print \"") ) {
			
			 @SuppressWarnings("resource")
			Scanner a2= new Scanner(sd);
			 String e2= a2.skip("print \"").nextLine();
			
		
			if(e2.charAt(e2.length() - 1) == '\"') {
			e2 = e2.substring(0, e2.length() - 1);
			area2.appendText(e2 + "\n");
		
			}  
		
		}
		
		else if (sd.contains("print") ) {
			 
			 @SuppressWarnings("resource")
			 Scanner a1= new Scanner(sd);
			 String e= a1.skip("print ").nextLine();
			 
			 DefinitionsUserInterface b = new DefinitionsUserInterface();
				File name = new File( b.getData());

				StringTokenizer k = new StringTokenizer(e);
				
				while(k.hasMoreTokens()) {
					
					String k1 = k.nextToken();
					@SuppressWarnings("resource")
					Scanner scanner = new Scanner(name);
					while(scanner.hasNextLine()) {
					
					String[] tokens = scanner.nextLine().split(" ");
					
					if(k1.matches(tokens[0])) {
				
					e =	e.replaceAll(k1, tokens[2]);
					
					area2.appendText(e + "\n");					
					}					
					}	
				}
		 
		 }
				// Set up the Scanner and the Lexer
		
		else if (sd.contains("input")) {
			area2.setEditable(true);
			area2.requestFocus();
			area2.appendText("");

			area2.setOnKeyPressed((event) -> {

				if (event.getCode() == KeyCode.ENTER) {

					@SuppressWarnings("resource")
					Scanner e3 = new Scanner(area2.getText());
					@SuppressWarnings("resource")
					Scanner e5 = new Scanner(area.getText());

					while (e3.hasNextLine() && e5.hasNextLine()) {

						String k = e5.nextLine();

						String m = e3.nextLine();

						if (m.matches("-?\\d+(\\.\\d+)?") && k.contains("input")) {

							@SuppressWarnings("resource")
							Scanner e8 = new Scanner(k);
							e8.skip("input ");
							@SuppressWarnings("resource")
							Scanner e6 = new Scanner(m);
							String a = e6.nextLine();
							String c = e8.nextLine();
							
							DefinitionsUserInterface b = new DefinitionsUserInterface();
							File theDataFile = null;
							try {
								theDataFile = new File( b.getData());
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							

							try (FileWriter writer = new FileWriter(theDataFile, true)) {

								writer.write( c + " " + "?" + " " + a + " " + "?" + " " + "?");
								area2.appendText("Data is Stored in a File" + "\n");

							} catch (IOException e) {
								e.printStackTrace();
							}

						}
					}
				}
			});

		
		}
		
		 try {
				datafetch(area.getText());
			} catch (NoSuchElementException e) {
				
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
			
		
				lexer = new Lexer(theReader);
				current = lexer.accept();
				next = lexer.accept();

				// Invoke the parser and the tree builder
				boolean isValid = addSubExpr();
				debugtest= "No error";
				if (isValid) {
					
					// Display the expression tree
					ExprNode theTree = exprStack.pop();
					String thenewTree = theTree.toString();
					
					area2.appendText("The expression is valid: " + isValid + "\n"+thenewTree+ "\nThe evaluation of the tree:"+"\nThe resulting value is: " + compute(theTree)+"\n");					
					
				}
				else {
					
					debugtest="Variable/constant not present in definitions";
				}
			}
		}
		
	private static boolean par() {

		// The method assumes the input is a sequence of terms separated by multiplication and/or 
		// division operators
		if (term()) {
			
			// Once an multiplication/division element has been found, it can be followed by a
			// sequence of multiplication or division operators followed by another 
			// multiplication/division element.  Therefore we start by looking for a "*" or a "/"
			while ((current.getTokenKind() == Kind.SYMBOL) && 
					((current.getTokenCode() == 4)		)) {
				
				// When you find a "*" or a "/", push it onto the operator stack
				opStack.push(current);
				
				// Advance to the next input token
				current = next;
				next = lexer.accept();
				
				// Look for the next multiplication/division element
				if (term()) {
					
					// If one is found, pop the two operands and the operator
					ExprNode expr2 = exprStack.pop();
					ExprNode expr1 = exprStack.pop();
					Token oper = opStack.pop();
					
					// Create an Expression Tree node from those three items and push it onto
					// the expression stack
					exprStack.push(new ExprNode(oper, true, expr1, expr2));
				}
				else {
					
					// If we get here, we saw a "*" or a "/", but it was not followed by a valid
					// multiplication/division element
					
					return false;
				}
			}
			
			// Reaching this point indicates that we have processed the sequence of 
			// additive/subtractive elements
			return true;
		}
		else
			
			// This indicates that the first thing found was not a multiplication/division element
			return false;
	}
	
	
	private static boolean addSubExpr() {

		// The method assumes the input is a sequence of additive/subtractive elements separated
		// by addition and/or subtraction operators
		if (mpyDivExpr()) {
			
			// Once an additive/subtractive element has been found, it can be followed by a
			// sequence of addition or subtraction operators followed by another 
			// additive/subtractive element.  Therefore we start by looking for a "+" or a "-"
			while ((current.getTokenKind() == Kind.SYMBOL) && 
					((current.getTokenCode() == 6) ||		// The "+" operator
					 (current.getTokenCode() == 7))) {		// The "-" operator
				
				// When you find a "+" or a "-", push it onto the operator stack
				opStack.push(current);
				
				// Advance to the next input token
				current = next;
				next = lexer.accept();
				
				// Look for the next additive/subtractive element
				if (mpyDivExpr()) {
					
					// If one is found, pop the two operands and the operator
					ExprNode expr2 = exprStack.pop();
					ExprNode expr1 = exprStack.pop();
					Token oper = opStack.pop();
					
					// Create an Expression Tree node from those three items and push it onto
					// the expression stack
					exprStack.push(new ExprNode(oper, true, expr1, expr2));
				}
				else {
					
					// If we get here, we saw a "+" or a "-", but it was not followed by a valid
					// additive/subtractive element
					
					return false;
				}
			}
			
			// Reaching this point indicates that we have processed the sequence of 
			// additive/subtractive elements
			return true;
		}
		else
			
			// This indicates that the first thing found was not an additive/subtractive element
			return false;
	}
	
	/**********
	 * The mpyDiv Expression method parses a sequence of expression elements that are multiplied
	 * together, divided from one another, or a blend of them.
	 * 
	 * @return	The method returns a boolean value indicating if the parse was successful
	 */
	private static boolean mpyDivExpr() {

		// The method assumes the input is a sequence of terms separated by multiplication and/or 
		// division operators
		if (par()) {
			
			// Once an multiplication/division element has been found, it can be followed by a
			// sequence of multiplication or division operators followed by another 
			// multiplication/division element.  Therefore we start by looking for a "*" or a "/"
			while ((current.getTokenKind() == Kind.SYMBOL) && 
					((current.getTokenCode() == 8) ||		// The "*" operator	
					 (current.getTokenCode() == 9))) {		// The "/" operator
				
				// When you find a "*" or a "/", push it onto the operator stack
				opStack.push(current);
				
				// Advance to the next input token
				current = next;
				next = lexer.accept();
				
				// Look for the next multiplication/division element
				if (par()) {
					
					// If one is found, pop the two operands and the operator
					ExprNode expr2 = exprStack.pop();
					ExprNode expr1 = exprStack.pop();
					Token oper = opStack.pop();
					
					// Create an Expression Tree node from those three items and push it onto
					// the expression stack
					exprStack.push(new ExprNode(oper, true, expr1, expr2));
				}
				else {
					
					// If we get here, we saw a "*" or a "/", but it was not followed by a valid
					// multiplication/division element
					
					return false;
				}
			}
			
			// Reaching this point indicates that we have processed the sequence of 
			// additive/subtractive elements
			return true;
		}
		else
			
			// This indicates that the first thing found was not a multiplication/division element
			return false;
	}
	
	/**********
	 * The term Expression method parses constants.
	 * 
	 * @return	The method returns a boolean value indicating if the parse was successful
	 */
	private static boolean term() {
		
		// Parse the term
	if (current.getTokenKind() == Kind.FLOAT ||current.getTokenKind() == Kind.SYMBOL ||
			current.getTokenKind() == Kind.INTEGER) {
			
			// When you find one, push a corresponding expression tree node onto the stack
			exprStack.push(new ExprNode(current, false, null, null));
			
			// Advance to the next input token
			current = next;
			next = lexer.accept();
			
			// Signal that the term was found
			return true;
		}
	else if (current.getTokenKind() == Kind.SYMBOL && ((current.getTokenCode() == 4) || (current.getTokenCode() ==5))) {
			
				// When you find one, push a corresponding expression tree node onto the stack
				exprStack.push(new ExprNode(current, false, null, null));
				
				// Advance to the next input token
				current = next;
				next = lexer.accept();
				
				// Signal that the term was found
				return true;
			}
		else 
			
			// Signal that a term was not found
			return false;
	}
	
	/**********
	 * The compute method is passed a tree as an input parameter and computes the value of the
	 * tree based on the operator nodes and the value node in the tree.  Precedence is encoded
	 * into the tree structure, so there is no need to deal with it during the evaluation.
	 * 
	 * @param r - The input parameter of the expression tree
	 * 
	 * @return  - A double value of the result of evaluating the expression tree
	 */
	public static double compute(ExprNode r) {
		
		// Check to see if this expression tree node is an operator.
		try{
			if ((r.getOp().getTokenKind() == Kind.SYMBOL) && ((r.getOp().getTokenCode() == 7) ||(r.getOp().getTokenCode() == 4) ||(r.getOp().getTokenCode() == 5)||
				(r.getOp().getTokenCode() == 6) || (r.getOp().getTokenCode() == 8) ||
				(r.getOp().getTokenCode() == 9))) {
			
			// if so, fetch the left and right sub-tree references and evaluate them
//			if(r.getLeft() != (r.getOp().getTokenKind() == Kind.SYMBOL)) {
			double leftValue = compute(r.getLeft());
//			else {
//				leftValue = 0.0;
//			}
			
			double rightValue = compute(r.getRight());
			
			// Give the value for the left and the right sub-trees, use the operator code
			// to select the correct operation
			double result = 0;
			boolean x= r.getOp().getTokenKind() == Kind.INTEGER;
			
			switch ((int)r.getOp().getTokenCode()) {
			
			case 4:
				
				for(int i=0;r.getOp().getTokenCode() == 5;i++ ) {
				
					
				if ((r.getOp().getTokenKind() == Kind.SYMBOL) && ((r.getOp().getTokenCode() == 6))) {
					
					result = leftValue + rightValue; break;
					
						
				}
				if ((r.getOp().getTokenCode() == 7)){
					
					result = leftValue - rightValue; break;
						
				}
				if ((r.getOp().getTokenKind() == Kind.SYMBOL) && ((r.getOp().getTokenCode() == 8))) {
					
					result = leftValue * rightValue; break;
						
				}
				if ((r.getOp().getTokenKind() == Kind.SYMBOL) && ((r.getOp().getTokenCode() == 9))) {
					
					result = leftValue / rightValue; break;
						
				}
				
		
				continue;
				}
			case 6: result = leftValue + rightValue; break;
			case 7: result = leftValue - rightValue; break;
			case 8: result = leftValue * rightValue; break;
			case 9: result = leftValue / rightValue; break;
			}
			
			// Display the actual computation working from the leaves up to the root
			
			// Return the result to the 
			return result;
		}
		
		// If the node is not an operator, determine what it is and fetch the value 
		else if (r.getOp().getTokenKind() == Kind.INTEGER) {
			Scanner convertInteger = new Scanner(r.getOp().getTokenText());
			Double result = convertInteger.nextDouble();
			convertInteger.close();
			return result;
		}
		else if (r.getOp().getTokenKind() == Kind.FLOAT) {
			Scanner convertFloat = new Scanner(r.getOp().getTokenText());
			Double result = convertFloat.nextDouble();
			convertFloat.close();
			return result;
		}
		
		
		
		// If it is not a recognized element, treat it as a value of zero
		else return 0.0;
		}
		catch(NullPointerException e) {
			debugtest="Result is not correct because of wrong parenthisis pattern or incorrect operator choice";
		}
		return 0;
		
		
	}
	
	
	/*	Name of method: tableData2()
	 *  Parameters:------
	 *  Return: Void
	 *  
	 *  Description: This method will connect Text area to the Definitions and run button.
	 * 
	 */
	
private void datafetch(String bb) throws NoSuchElementException, FileNotFoundException {
	  // get the area text
	String bh;
	String vbb;
	
	
	char[] ab = bb.toCharArray(); // convert area text in tokens
	
		
		DefinitionsUserInterface b = new DefinitionsUserInterface();
		File name = new File( b.getData());

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(name);
		
		while (scanner.nextLine() != null) {
			while (scanner.hasNextLine()) {

				try {
				String[] tokens = scanner.nextLine().split(" ");
				
				for(int i=0; i<ab.length; i++) {
				String vb=Character.toString(ab[i]);  // convert characters into string and store to work on it
				 vbb=Character.toString(ab[i]);		  // convert characters into string and store another time to compare in future.
				if (vb.matches(tokens[0])) {
					vb=tokens[2];
					ab[i]=vb.charAt(0);
					bh=vb;
					
					
					if(vbb.matches(tokens[0])) {
						
						 bb= bb.replace(vbb, bh);   // replace the old char with its new value taken from definitions
						
						
						
					}
					
					}
				
				
				}
				area.setText(bb);	
				}
				
				catch(ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}

			}
			break;
				

		}
		
		
	}
		
	}
	

