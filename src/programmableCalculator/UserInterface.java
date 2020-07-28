
package programmableCalculator;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;

import java.util.NoSuchElementException;
import java.util.Scanner;



import definitionDemo.DefinitionsUserInterface;

import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javafx.stage.Stage;
import programmableCalculator.BusinessLogic;

/**
 * <p>
 * Title: UserInterface Class.
 * </p>
 * 
 * <p>
 * Description: The Java/FX-based user interface for the calculator. The class
 * works with String objects and passes work to other classes to deal with all
 * other aspects of the computation.
 * </p>
 * 
 * <p>
 * Copyright: Lynn Robert Carter � 2017
 * </p>
 * 
 * @author Lynn Robert Carter Sumit Singh and Shivam Singhal and Sawan Gaba
 * 
 * @version 4.30 2018-02-25 The JavaFX-based GUI for the implementation of a
 *          calculator
 * 
 * @version 4.40 2018-03-23 The JavaFX-based GUI for the implementation of a
 *          calculator along with error term values
 * 
 * @version 4.50 2018-10-06 The JavaFX-based GUI for the implementation of a
 *          calculator showing result value and error term in single text field.
 * 
 * @version 4.60 2018-11-24 Implementing the JavaFX-based GUI for selecting the
 *          unit, so that calculation can be done by using the units.
 * 
 * @version 4.70 2019-02-13 Baseline for Project 1(Data Structures). +
 * 
 * @version 4.80 2019-02-26 Enhanced Version with definitions support with added another window for definitions
 * 							
 */

public class UserInterface {

	/**********************************************************************************************
	 * 
	 * Attributes
	 * 
	 **********************************************************************************************/

	/*
	 * Constants used to parameterize the graphical user interface. We do not use a
	 * layout manager for this application. Rather we manually control the location
	 * of each graphical element for exact control of the look and feel.
	 */
	public DefinitionsUserInterface definitions;
	public ProgramInterface Program;

	private final double BUTTON_WIDTH = 60;
	private final double BUTTON_OFFSET = BUTTON_WIDTH / 2;

	// These are the application values required by the user interface
	private Label label_DoubleCalculator = new Label("Science and Engineering Calculator with Units");
	private Label label_Operand1 = new Label("First operand");
	private Label label_Operand1PlusMinus = new Label("\u00B1"); // The plus minus symbol for opernd1: \u00B1
	private Label units = new Label("Units");
	private TextField text_Operand1 = new TextField();
	private TextField text_Operand1Errorterm = new TextField();
	private Label label_Operand2 = new Label("Second operand");
	private Label label_Operand2PlusMinus = new Label("\u00B1"); // The plus minus symbol for operand2: \u00B1
	private TextField text_Operand2 = new TextField();
	private TextField text_Operand2Errorterm = new TextField();
	private Label label_ResultPlusMinus = new Label("\u00B1"); // The plus minus symbol for operand2: \u00B1
	private Label label_Result = new Label("Result");
	private TextField text_Result = new TextField();
	private TextField text_ResultErrorterm = new TextField();
	private Button button_load = new Button("Load");
	private Button button_Add = new Button("+");
	private Button button_Sub = new Button("-");
	private Button button_Mpy = new Button("\u00D7"); // The multiply symbol: \u00D7
	private Button button_Div = new Button("\u00F7"); // The divide symbol: \u00F7
	private Button button_Sqrt = new Button("\u221A"); // The root symbol: \u221A
	private Label label_errOperand1 = new Label(""); // Label to display specific
	private Label label_errOperand2 = new Label(""); // error messages
	private Label label_errOperand1X = new Label(""); // Label to display a error message
	private Label label_errOperand2X = new Label(""); // when user tries to perform any function
	private Label label_errResult = new Label("");
	private TextFlow err1;
	private Text operand1ErrPart1 = new Text();
	private Text operand1ErrPart2 = new Text();
	private TextFlow err2;
	private Text operand2ErrPart1 = new Text();
	private Text operand2ErrPart2 = new Text();
	DefinitionsUserInterface forlabel = new DefinitionsUserInterface();
	
	String flabel= forlabel.getData();
	private Label label_errOperand1ETerm = new Label(null);
	private Label label_errOperand2ETerm = new Label(null);

	private TextFlow errErrorTerm_Operand1;
	private Text errOperand1_ETPart1 = new Text();
	private Text errOperand1_ETPart2 = new Text();

	private TextFlow errErrorTerm_Operand2;
	private Text errOperand2_ETPart1 = new Text();
	private Text errOperand2_ETPart2 = new Text();

	private String[] operand_units = { "m", "km", "s", "min", "h", "day", "No Unit" };
	private ComboBox<String> cmbox_operand1_unit = new ComboBox<String>();
	private ComboBox<String> cmbox_operand2_unit = new ComboBox<String>();
	private ComboBox<String> cmbox_result_unit = new ComboBox<String>();

	private double buttonSpace; // This is the white space between the operator buttons.

	/* This is the link to the business logic */
	public BusinessLogic perform = new BusinessLogic();
	public CalculatorValue check = new CalculatorValue();

	private Button button_Def = new Button("Open Definations");
	private Button button_Pr = new Button("      Program");
	public CheckBox cbox = new CheckBox ("Save Result");
	DefinitionsUserInterface a = new DefinitionsUserInterface();
	File name = new File (a.getData());
	private Label Df= new Label("");
	/**********************************************************************************************
	 * 
	 * Constructors
	 * 
	 **********************************************************************************************/

	/**********
	 * This method initializes all of the elements of the graphical user interface.
	 * These assignments determine the location, size, font, color, and change and
	 * event handlers for each GUI object.
	 * @throws FileNotFoundException 
	 */

	public UserInterface(Pane theRoot) throws FileNotFoundException {

		// There are five gaps. Compute the button space accordingly.
		buttonSpace = Calculator.WINDOW_WIDTH / 6;

		// Label theScene with the name of the calculator, centered at the top of the
		// pane
		setupLabelUI(label_DoubleCalculator, "Arial", 24, Calculator.WINDOW_WIDTH, Pos.CENTER, 0, 5);
		
		setupLabelUI(Df, "Arial", 20, Calculator.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 30, 500);
		// Label the first operand just above it, left aligned
		setupLabelUI(label_Operand1, "Arial", 15, Calculator.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 10, 75);
		// Label the first operand plus minus sign, center alignedlabel_ResultPlusMinus
		setupLabelUI(label_Operand1PlusMinus, "Arial", 20, Calculator.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 393, 70);
		// Label the first operand error term just, right aligned
		setupLabelUI(units, "Arial", 18, Calculator.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, Calculator.WINDOW_WIDTH - 100,
				40);

		// Establish the first text input operand field and when anything changes in
		// operand 1,
		// process both fields to ensure that we are ready to perform as soon as
		// possible.
		setupTextUI(text_Operand1, "Arial", 18, 260, Pos.BASELINE_LEFT, 130, 70, true);
		text_Operand1.textProperty().addListener((observable, oldValue, newValue) -> {
			setOperand1();
		});

		// Calling a function to regular validate the error term for operand 1
		setupTextUI(text_Operand1Errorterm, "Arial", 18, 280, Pos.BASELINE_LEFT, 410, 70, true);
		text_Operand1Errorterm.textProperty().addListener((observable, oldValue, newValue) -> {
			setOperand1();
		});

		// Setting up combobox for selecting the units for operand 1
		cmbox_operand1_unit.setLayoutX(730);
		cmbox_operand1_unit.setLayoutY(70);

		// Move focus to the second operand when the user presses the enter (return) key
		text_Operand1.setOnAction((event) -> {
			setOperand1();
			text_Operand1Errorterm.requestFocus();
		});

		// Establish an error message for the first operand just above it with, left
		// aligned
		setupLabelUI(label_errOperand1X, "Arial", 18, Calculator.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 180, 45);
		label_errOperand1X.setTextFill(Color.RED);

		// Bottom proper error message
		label_errOperand1.setTextFill(Color.RED);
		label_errOperand1.setAlignment(Pos.BASELINE_RIGHT);
		setupLabelUI(label_errOperand1, "Arial", 14, Calculator.WINDOW_WIDTH - 150 - 10, Pos.BASELINE_LEFT, 25, 128);

		// Error message for error term of operand 1
		label_errOperand1ETerm.setTextFill(Color.RED);
		label_errOperand1ETerm.setAlignment(Pos.BASELINE_RIGHT);
		setupLabelUI(label_errOperand1ETerm, "Arial", 14, Calculator.WINDOW_WIDTH - 150 - 10, Pos.BASELINE_LEFT, 420,
				128);

		// Label the second operand just above it, left aligned
		setupLabelUI(label_Operand2, "Arial", 15, Calculator.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 10, 180);
		// Label the second operand plus minus sign, center aligned
		setupLabelUI(label_Operand2PlusMinus, "Arial", 20, Calculator.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 393, 180);

		// Establish the second text input operand field and when anything changes in
		// operand 2,
		// process both fields to ensure that we are ready to perform as soon as
		// possible.
		setupTextUI(text_Operand2, "Arial", 18, 260, Pos.BASELINE_LEFT, 130, 180, true);

		// Setting up the change of focus
		text_Operand1Errorterm.setOnAction((event) -> {
			text_Operand2.requestFocus();
		});

		// Calling a function to regular validate the error term for operand 2
		text_Operand2.textProperty().addListener((observable, oldValue, newValue) -> {
			setOperand2();
		});

		setupTextUI(text_Operand2Errorterm, "Arial", 18, 280, Pos.BASELINE_LEFT, 410, 180, true);
		text_Operand2Errorterm.textProperty().addListener((observable, oldValue, newValue) -> {
			setOperand2();
		});

		// Setting up combobox for selecting the units for operand 2
		cmbox_operand2_unit.setLayoutX(730);
		cmbox_operand2_unit.setLayoutY(180);
		// Move the focus to the result when the user presses the enter (return) key
		text_Operand2.setOnAction((event) -> {
			text_Operand2Errorterm.requestFocus();
		});

		// Establish an error message for the second operand just above it with, left
		// aligned
		setupLabelUI(label_errOperand2X, "Arial", 18, Calculator.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 180, 155);
		label_errOperand2X.setTextFill(Color.RED);
		
		
	Df.setText("Definations loaded from file: "+ flabel);
		// Bottom proper error message
		label_errOperand2.setTextFill(Color.RED);
		label_errOperand2.setAlignment(Pos.BASELINE_RIGHT);
		setupLabelUI(label_errOperand2, "Arial", 14, Calculator.WINDOW_WIDTH - 150 - 10, Pos.BASELINE_LEFT, 22, 233);
		label_errOperand2.setTextFill(Color.RED);

		label_errOperand2ETerm.setTextFill(Color.RED);
		label_errOperand2ETerm.setAlignment(Pos.BASELINE_RIGHT);
		setupLabelUI(label_errOperand2ETerm, "Arial", 14, Calculator.WINDOW_WIDTH - 150 - 10, Pos.BASELINE_LEFT, 420,
				237);

		// Label the result just above the result output field, left aligned
		setupLabelUI(label_Result, "Arial", 15, Calculator.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 10, 360);
		// Establish the result output field. It is not editable, so the text can be
		// selected and copied,
		// but it cannot be altered by the user. The text is left aligned.
		setupTextUI(text_Result, "Arial", 18, 270, Pos.BASELINE_LEFT, 110, 350, false);
		// Label the Result plus minus sign, center aligned
		setupLabelUI(label_ResultPlusMinus, "Arial", 20, Calculator.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 393, 350);
		// Establish the result output field. It is not editable, so the text can be
		// selected and copied,
		// but it cannot be altered by the user. The text is left aligned.
		setupTextUI(text_ResultErrorterm, "Arial", 18, 280, Pos.BASELINE_LEFT, 410, 350, false);

		// Establish an error message for the second operand just above it with, left
		// aligned
		setupLabelUI(label_errResult, "Arial", 18, Calculator.WINDOW_WIDTH - 10, Pos.BASELINE_LEFT, 400, 195);
		label_errResult.setTextFill(Color.RED);

		// Setting up combobox for selecting the units for operand 2
		cmbox_result_unit.setLayoutX(730);
		cmbox_result_unit.setLayoutY(350);

		// Establish the ADD "+" button, position it, and link it to methods to
		// accomplish its work
		setupButtonUI(button_Add, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, 1 * buttonSpace - BUTTON_OFFSET, 250);
		button_Add.setOnAction((event) -> {
			
			
			if(cbox.isSelected()) {
				try {
					addOperands();

					save();
				} catch (IOException e) {
				e.printStackTrace();
				}
				}
			else {
				addOperands();
			}
		});

		// Establish the SUB "-" button, position it, and link it to methods to
		// accomplish its work
		setupButtonUI(button_Sub, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, 2 * buttonSpace - BUTTON_OFFSET, 250);
		button_Sub.setOnAction((event) -> {
			
			if(cbox.isSelected()) {
				try {
					subOperands();
					save();
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				}
			else {
				subOperands();
			}
		});

		// Establish the MPY "x" button, position it, and link it to methods to
		// accomplish its work
		setupButtonUI(button_Mpy, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, 3 * buttonSpace - BUTTON_OFFSET, 250);
		
		
		
		button_Mpy.setOnAction((event) -> {
			
			if(cbox.isSelected()) {
				try {
					mpyOperands();
					save();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			else {
				mpyOperands();
			}
		});

		// Establish the DIV "/" button, position it, and link it to methods to
		// accomplish its work
		setupButtonUI(button_Div, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, 4 * buttonSpace - BUTTON_OFFSET, 250);
		button_Div.setOnAction((event) -> {
			
			if(cbox.isSelected()) {
				try {
					divOperands();
					save();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				}
			else {
				divOperands();
			}
		});

		// Establish the SQRT "root" button, position it, and link it to methods to
		// accomplish its work
		setupButtonUI(button_Sqrt, "Symbol", 32, BUTTON_WIDTH, Pos.BASELINE_LEFT, 5 * buttonSpace - BUTTON_OFFSET, 250);
		button_Sqrt.setOnAction((event) -> {
			if(cbox.isSelected()) {
			try {
				save();
				sqrtOperands();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			else {
				sqrtOperands();

			}
		});
		button_Pr.setPrefWidth(162.5);
		// Establish the ADD "+" button, position it, and link it to methods to
		// accomplish its work
		setupButtonUI(button_Def, "Symbol", 18,50, Pos.BASELINE_LEFT, 700, 490);
		setupButtonUI(button_Pr, "Symbol", 18,50, Pos.BASELINE_LEFT, 700, 430);
		button_Def.setOnAction((event) -> {
			Stage newStage = new Stage();
			definitions = new DefinitionsUserInterface(newStage);
		});
		
		button_Pr.setOnAction((event) -> {
			Stage Stage2 = new Stage();
			Program = new ProgramInterface(Stage2);
		});

		// Adding units in combox
		for (int i = 0; i < operand_units.length; i++) {
			cmbox_operand1_unit.getItems().add(operand_units[i]);
			cmbox_operand2_unit.getItems().add(operand_units[i]);
		}

		cmbox_operand1_unit.getSelectionModel().select(operand_units.length - 1);
		;
		cmbox_operand2_unit.getSelectionModel().select(operand_units.length - 1);
		;

		// Error Message for the Measured Value for operand 1
		operand1ErrPart1.setFill(Color.BLACK);
		operand1ErrPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
		operand1ErrPart2.setFill(Color.RED);
		operand1ErrPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 24));
		err1 = new TextFlow(operand1ErrPart1, operand1ErrPart2);
		err1.setMinWidth(Calculator.WINDOW_WIDTH - 10);
		err1.setLayoutX(140);
		err1.setLayoutY(100);

		// Error Message for the Measured Value for operand 2
		operand2ErrPart1.setFill(Color.BLACK);
		operand2ErrPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
		operand2ErrPart2.setFill(Color.RED);
		operand2ErrPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 24));
		err2 = new TextFlow(operand2ErrPart1, operand2ErrPart2);
		err2.setMinWidth(Calculator.WINDOW_WIDTH - 10);
		err2.setLayoutX(140);
		err2.setLayoutY(210);

		// Error Message for the Error Term for operand 1
		errOperand1_ETPart1.setFill(Color.BLACK);
		errOperand1_ETPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
		errOperand1_ETPart2.setFill(Color.RED);
		errOperand1_ETPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 24));
		errErrorTerm_Operand1 = new TextFlow(errOperand1_ETPart1, errOperand1_ETPart2);
		// Establish an error message for the second operand just above it with, left
		// aligned
		errErrorTerm_Operand1.setMinWidth(Calculator.WINDOW_WIDTH - 10);
		errErrorTerm_Operand1.setLayoutX(422);
		errErrorTerm_Operand1.setLayoutY(100);

		// Error Message for the Error Term for operand 2
		errOperand2_ETPart1.setFill(Color.BLACK);
		errOperand2_ETPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));
		errOperand2_ETPart2.setFill(Color.RED);
		errOperand2_ETPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 24));
		errErrorTerm_Operand2 = new TextFlow(errOperand2_ETPart1, errOperand2_ETPart2);
		// Establish an error message for the second operand just above it with, left
		// aligned
		errErrorTerm_Operand2.setMinWidth(Calculator.WINDOW_WIDTH - 10);
		errErrorTerm_Operand2.setLayoutX(422);
		errErrorTerm_Operand2.setLayoutY(210);
		
		cbox.setLayoutX(850);
		cbox.setLayoutY(355);
		cbox.setOnAction((event) ->{

		}
				
				
				);
		
	
		// Place all of the just-initialized GUI elements into the pane
		theRoot.getChildren().addAll(label_DoubleCalculator, label_Operand1, text_Operand1, label_errOperand1,
				label_Operand2, text_Operand2, label_errOperand2, label_Result, text_Result, label_errResult,
				button_Add, button_Def, button_Pr, button_Sub, button_Mpy, button_Div, button_Sqrt, err1, err2, label_errOperand1X,
				label_errOperand2X, text_Operand1Errorterm, text_Operand2Errorterm, units, label_ResultPlusMinus,
				errErrorTerm_Operand1, label_errOperand1ETerm, errErrorTerm_Operand2, text_ResultErrorterm,
				label_errOperand2ETerm, label_Operand1PlusMinus, label_Operand2PlusMinus, cmbox_operand1_unit,
				cmbox_operand2_unit, cmbox_result_unit, cbox,Df);

	}



	/*******
	 * This public methods invokes the methods of Calculator class and generate a
	 * specific error message when the user enters the value of operand1 and error
	 * term of operand1
	 * 
	 */
	public void message1() {
		String errMessage = CalculatorValue.checkMeasureValue(text_Operand1.getText());
		if (errMessage != "") {
			label_errOperand1.setText(CalculatorValue.measuredValueErrorMessage);
			if (CalculatorValue.measuredValueIndexofError <= -1)
				return;
			String input = CalculatorValue.measuredValueInput;
			operand1ErrPart1.setText(input.substring(0, CalculatorValue.measuredValueIndexofError));
			operand1ErrPart2.setText("\u21EB");
			errOperand1_ETPart1.setText("");
			errOperand1_ETPart2.setText("");
			label_errOperand1ETerm.setText("");
		}

		else {
			// Clearing any previous error message
			label_errOperand1ETerm.setText("");
			errOperand1_ETPart1.setText("");
			errOperand1_ETPart2.setText("");

			// Checking is there any new error message
			errMessage = CalculatorValue.checkErrorTerm(text_Operand1Errorterm.getText());
			if (errMessage != "") {
				label_errOperand1.setText("");
				label_errOperand1ETerm.setText(CalculatorValue.errorTermErrorMessage);
				String input = CalculatorValue.errorTermInput;
				if (CalculatorValue.errorTermIndexofError <= -1)
					return;
				errOperand1_ETPart1.setText(input.substring(0, CalculatorValue.errorTermIndexofError));
				errOperand1_ETPart2.setText("\u21EB");
				operand1ErrPart1.setText("");
				operand1ErrPart2.setText("");
			}

		}
	}

	/*******
	 * This public methods invokes the methods of Calculator class and generate a
	 * specific error message when the user enters the value of operand2 and error
	 * term of operand1
	 * 
	 */
	public void message2() {
		String errMessage = CalculatorValue.checkMeasureValue(text_Operand2.getText());
		if (errMessage != "") {
			label_errOperand2.setText(CalculatorValue.measuredValueErrorMessage);
			if (CalculatorValue.measuredValueIndexofError <= -1)
				return;
			String input = CalculatorValue.measuredValueInput;
			operand2ErrPart1.setText(input.substring(0, CalculatorValue.measuredValueIndexofError));
			operand2ErrPart2.setText("\u21EB");
			errOperand2_ETPart1.setText("");
			errOperand2_ETPart2.setText("");
			label_errOperand2ETerm.setText("");

		}

		else {
			// Clearing any previous error message
			label_errOperand2ETerm.setText("");
			errOperand2_ETPart1.setText("");
			errOperand2_ETPart2.setText("");

			// Checking is there any new error message
			errMessage = CalculatorValue.checkErrorTerm(text_Operand2Errorterm.getText());
			if (errMessage != "") {
				label_errOperand2.setText("");
				label_errOperand2ETerm.setText(CalculatorValue.errorTermErrorMessage);
				String input = CalculatorValue.errorTermInput;
				if (CalculatorValue.errorTermIndexofError <= -1)
					return;
				errOperand2_ETPart1.setText(input.substring(0, CalculatorValue.errorTermIndexofError));
				errOperand2_ETPart2.setText("\u21EB");
				operand2ErrPart1.setText("");
				operand2ErrPart2.setText("");
			}

		}
	}

	/**********
	 * Private local method to initialize the standard fields for a label
	 */
	private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y) {
		l.setFont(Font.font(ff, f));
		l.setMinWidth(w);
		l.setAlignment(p);
		l.setLayoutX(x);
		l.setLayoutY(y);
	}

	/**********
	 * Private local method to initialize the standard fields for a text field
	 */
	private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e) {
		t.setFont(Font.font(ff, f));
		t.setMinWidth(w);
		t.setMaxWidth(w);
		t.setAlignment(p);
		t.setLayoutX(x);
		t.setLayoutY(y);
		t.setEditable(e);
	}

	/**********
	 * Private local method to initialize the standard fields for a button
	 */
	private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y) {
		b.setFont(Font.font(ff, f));
		b.setMinWidth(w);
		b.setAlignment(p);
		b.setLayoutX(x);
		b.setLayoutY(y);
	}

	/**********************************************************************************************
	 * 
	 * User Interface Actions
	 * 
	 **********************************************************************************************/

	/**********
	 * Private local method to set the value of the first operand given a text
	 * value. The method uses the business logic class to perform the work of
	 * checking the string to see it is a valid value and if so, saving that value
	 * internally for future computations. If there is an error when trying to
	 * convert the string into a value, the called business logic method returns
	 * false and actions are taken to display the error message appropriately.
	 */
	
	
	private void setOperand2() {
		text_Result.setText(""); // See setOperand1's comments. The logic is the same!
		label_Result.setText("Result");
		label_errResult.setText("");

		message2();
		
        boolean a = true;
			String p = new String(text_Operand2.getText());

	        a = p.matches("-?\\d+(\\.\\d+)?");

	       
if (a)
{

		if (perform.setOperand2(text_Operand2.getText(), text_Operand2Errorterm.getText())) {
			label_errOperand2.setText("");
			label_errOperand2X.setText("");
			operand2ErrPart1.setText("");
			operand2ErrPart2.setText("");
			if (text_Operand2.getText().length() == 0)
				label_errOperand2.setText("");
		}
	}
	
	else {
		
		text_Operand2.setOnAction((event) -> {
			try {
				tableData2();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			text_Operand2Errorterm.requestFocus();
		});
		
	}
	}
	private void setOperand1() {
		text_Result.setText(""); // Any change of an operand probably invalidates
		label_Result.setText("Result"); // the result, so we clear the old result.
		label_errResult.setText("");
		message1();
		boolean a = true;
		String p = new String(text_Operand1.getText());
		a = p.matches("-?\\d+(\\.\\d+)?");

	       
if (a)
{
	if (perform.setOperand1(text_Operand1.getText(), text_Operand1Errorterm.getText())) { // Set the operand and see
		// if there was an error
label_errOperand1.setText(""); // If no error, clear this operands error
label_errOperand1X.setText("");
operand1ErrPart1.setText(""); // Clear the first term of error part
operand1ErrPart2.setText(""); // Clear the second term of error part

if (text_Operand2.getText().length() == 0) // If the other operand is empty, clear its error
label_errOperand2.setText(""); // as well.
}
	
	
}
	
else {
	
	text_Operand1.setOnAction((event) -> {
		
		try {

			tableData();
		
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		text_Operand1Errorterm.requestFocus();
	
	});
	
}

	}
	
	/**********
	 * Private local method to set the value of the second operand given a text
	 * value. The logic is exactly the same as used for the first operand, above.
	 */


	/**********
	 * This method is called when an binary operation (expect square root) button
	 * has been pressed. It assesses if there are issues with either of the binary
	 * operands or they are not defined. If not return false (there are no issues)
	 * 
	 * @return True if there are any issues that should keep the calculator from
	 *         doing its work.
	 */

	private boolean binaryOperandIssues() {
		String errorMessage1 = perform.getOperand1ErrorMessage(); // Fetch the error messages, if there are any
		String errorMessage2 = perform.getOperand2ErrorMessage();
		if (errorMessage1.length() > 0) { // Check the first. If the string is not empty
			label_errOperand1X.setText(errorMessage1); // there's an error message, so display it.
			if (errorMessage2.length() > 0) { // Check the second and display it if there is
				label_errOperand2X.setText(errorMessage2); // and error with the second as well.
				return true; // Return true when both operands have errors
			} else {
				return true; // Return true when only the first has an error
			}
		} else if (errorMessage2.length() > 0) { // No error with the first, so check the second
			label_errOperand2X.setText(errorMessage2); // operand. If non-empty string, display the error
			return true; // message and return true... the second has an error
		} // Signal there are issues

		// If the code reaches here, neither the first nor the second has an error
		// condition. The following code
		// check to see if the operands are defined.
		if (!perform.getOperand1Defined()) { // Check to see if the first operand is defined
			label_errOperand1X.setText("No value found"); // If not, this is an issue for a binary operator
			if (!perform.getOperand2Defined()) { // Now check the second operand. It is is also
				label_errOperand2X.setText("No value found"); // not defined, then two messages should be displayed
				return true; // Signal there are issues
			}
			return true;
		} else if (!perform.getOperand2Defined()) { // If the first is defined, check the second. Both
			label_errOperand2X.setText("No value found"); // operands must be defined for a binary operator.
			return true; // Signal there are issues
		}

		return false; // Signal there are no issues with the operands
	}

	/**********
	 * This method is called when an binary operation (expect square root) button
	 * has been pressed. It assesses if there are issues with either of the binary
	 * operands or they are not defined. If not return false (there are no issues)
	 * 
	 * @return True if there are any issues that should keep the calculator from
	 *         doing its work.
	 */
	private boolean binaryOperandIssuesET() {

		if (label_errOperand1ETerm == null) { // If operand1 error term is having an error message
			return false; // return false
		}

		if (errErrorTerm_Operand2 != null) { // If operand1 error term is having an error message
			return false; // return false
		}

		return true; // Signal there are no issues with the operands
	}

	/**********
	 * This method is called when square root button has been pressed. It assesses
	 * if there are issues with either of the binary operand1 or it is not defined.
	 * If not return false (there are no issues) As to perform square root, we only
	 * need operand1 thus any value added in the second field is automatically
	 * cleared when square root button is pressed
	 * 
	 * @return True if there are any issues that should keep the calculator from
	 *         doing its work.
	 */

	private boolean binaryOperandIssuesForSqrt() {

		String errorMessage1 = perform.getOperand1ErrorMessage(); // Fetch the error messages, if there are any
		if (errorMessage1.length() > 0) { // Check the first. If the string is not empty
			label_errOperand1X.setText(errorMessage1); // there's an error message, so display it.
			return true;
		}
		text_Operand2.setText(""); // if anything is in second operand field
		text_Operand2Errorterm.setText("");
		label_errOperand2X.setText(""); // then clear it

		// If the code reaches here, neither the first nor the second has an error
		// condition. The following code
		// check to see if the operands are defined.
		if (!perform.getOperand1Defined()) { // Check to see if the first operand is defined
			label_errOperand1X.setText("No value found"); // If not, this is an issue for a binary operator
			text_Result.setText("");
			return true;
		}

		return false; // Signal there are no issues with the operand1
	}

	/*****************
	 * This private method is to check, if the error term are left empty then this
	 * should not effect the wrking of calculator. A default value of 0.5 is set in
	 * both the error terms
	 */

	private void errorTermCheck() {
		if (text_Operand1Errorterm.getLength() == 0) {
			text_Operand1Errorterm.setText("0.05");
		}

		if (text_Operand2Errorterm.getLength() == 0) {
			text_Operand2Errorterm.setText("0.05");
		}
	}

	/*******************************************************************************************************
	 * This portion of the class defines the actions that take place when the
	 * various calculator buttons (add, subtract, multiply, divide and square root)
	 * are pressed.
	 */

	/**********
	 * This is the add routine
	 * 
	 */

	private void addOperands() {

		cmbox_result_unit.getItems().clear();

		int ndx = cmbox_operand1_unit.getSelectionModel().getSelectedIndex();
		int ndx2 = cmbox_operand2_unit.getSelectionModel().getSelectedIndex();

		check.setIndexofUnits(ndx, ndx2);

		errorTermCheck();

		// Check to see if both operands are defined and valid
		if (binaryOperandIssues() || binaryOperandIssuesET() || !perform.unitsCheck()) { // If there are issues with the
																							// operands, return
			text_Result.setText(""); // without doing the computation
			return;
		}

		// If the operands are defined and valid, request the business logic method to
		// do the addition and return the
		// result as a String. If there is a problem with the actual computation, an
		// empty string is returned
		String theAnswer = perform.addition(); // Call the business logic addition method
		String theAnswerET = perform.errorTerm();
		label_errResult.setText(""); // Reset any result error messages from before
		if (theAnswer.length() > 0) { // Check the returned String to see if it is okay
			label_errOperand1X.setText("");
			label_errOperand2X.setText("");
			text_Result.setText(theAnswer); // If okay, display it in the result field and
			text_ResultErrorterm.setText(theAnswerET);

			// Adding the units in the result combo-box

			cmbox_result_unit.getSelectionModel().select(perform.unit());
			cmbox_result_unit.getSelectionModel().select(0);
			label_Result.setText("Sum"); // change the title of the field to "Sum"
		} else { // Some error occurred while doing the addition.
			text_Result.setText(""); // Do not display a result if there is an error.
			label_Result.setText("Result"); // Reset the result label if there is an error.
			label_errResult.setText(perform.getResultErrorMessage()); // Display the error message.
		}
	}

	/**********
	 * This is the subtract routine
	 * 
	 */
	private void subOperands() {

		cmbox_result_unit.getItems().clear();

		int ndx = cmbox_operand1_unit.getSelectionModel().getSelectedIndex();
		int ndx2 = cmbox_operand2_unit.getSelectionModel().getSelectedIndex();

		check.setIndexofUnits(ndx, ndx2);

		errorTermCheck();

		// Check to see if both operands are defined and valid
		if (binaryOperandIssues() || binaryOperandIssuesET() || !perform.unitsCheck()) { // If there are issues with the
																							// operands, return
			text_Result.setText("");
			return;
		}
		// without doing the computation

		// If the operands are defined and valid, request the business logic method to
		// do the subtraction and return the
		// result as a String. If there is a problem with the actual computation, an
		// empty string is returned
		String theAnswer = perform.subtraction(); // Call the business logic subtraction method
		String theAnswerET = perform.errorTerm();
		label_errResult.setText(""); // Reset any result error messages from before
		if (theAnswer.length() > 0) { // Check the returned String to see if it is okay
			label_errOperand1X.setText("");
			label_errOperand2X.setText("");
			text_Result.setText(theAnswer); // If okay, display it in the result field and
			text_ResultErrorterm.setText(theAnswerET);
			// Adding the units in the result combox
			cmbox_result_unit.getSelectionModel().select(perform.unit());

			cmbox_result_unit.getSelectionModel().select(0);
			label_Result.setText("Difference"); // change the title of the field to "Difference"
			cmbox_result_unit.setOnAction((event) -> {

			});
		} else { // Some error occurred while doing the subtraction.
			text_Result.setText(""); // Do not display a result if there is an error.
			label_Result.setText("Result"); // Reset the result label if there is an error.
			label_errResult.setText(perform.getResultErrorMessage()); // Display the error message.
		}

	}

	/**********
	 * This is the multiply routine
	 * 
	 */
	private void mpyOperands() {

		cmbox_result_unit.getItems().clear();

		int ndx = cmbox_operand1_unit.getSelectionModel().getSelectedIndex();
		int ndx2 = cmbox_operand2_unit.getSelectionModel().getSelectedIndex();

		check.setIndexofUnits(ndx, ndx2);

		errorTermCheck();
		// Check to see if both operands are defined and valid
		if (binaryOperandIssues() || binaryOperandIssuesET()) { // If there are issues with the operands, return
			text_Result.setText("");
			return;
		} // without doing the computation

		// If the operands are defined and valid, request the business logic method to
		// do the multiplication and return the
		// result as a String. If there is a problem with the actual computation, an
		// empty string is returned
		String theAnswer = perform.multiplication(); // Call the business logic multiplication method
		String theAnswerET = perform.errorTerm();
		label_errResult.setText(""); // Reset any result error messages from before
		if (theAnswer.length() > 0) { // Check the returned String to see if it is okay
			label_errOperand1X.setText("");
			label_errOperand2X.setText("");
			text_Result.setText(theAnswer); // If okay, display it in the result field and

			cmbox_result_unit.getSelectionModel().select(perform.unit());

			text_ResultErrorterm.setText(theAnswerET);
			label_Result.setText("Product"); // change the title of the field to "Product"

			cmbox_result_unit.getSelectionModel().select(0);
		} else { // Some error occurred while doing the multiplication.
			text_Result.setText(""); // Do not display a result if there is an error.
			label_Result.setText("Result"); // Reset the result label if there is an error.
			label_errResult.setText(perform.getResultErrorMessage()); // Display the error message.
		}

	}

	/**********
	 * This is the divide routine. If the divisor is zero, the divisor is declared
	 * to be invalid.
	 * 
	 */
	private void divOperands() {

		cmbox_result_unit.getItems().clear();

		int ndx = cmbox_operand1_unit.getSelectionModel().getSelectedIndex();
		int ndx2 = cmbox_operand2_unit.getSelectionModel().getSelectedIndex();

		check.setIndexofUnits(ndx, ndx2);

		errorTermCheck();
		if (binaryOperandIssues() || binaryOperandIssuesET()) { // If there are issues with the operands, return
			text_Result.setText("");
			return;
		} // without doing the computation

		double operand2 = Double.parseDouble(text_Operand2.getText()); // Fetching the value of operand2 and saving it
																		// in
		// double data type

		// Check to see if both operands are defined and valid

		if (operand2 != 0f) { // Check if the operand2 is zero or not
			// 0f defines it for all float values like, 0.0, 0.00 .... etc
			// If the operands are defined and valid, request the business logic method to
			// do the divide and return the
			// result as a String. If there is a problem with the actual computation, an
			// empty string is returned
			String theAnswer = perform.division(); // Call the business logic division method
			String theAnswerET = perform.errorTerm();
			label_errResult.setText(""); // Reset any result error messages from before
			// Check the returned String to see if it is okay
			label_errOperand1X.setText("");
			label_errOperand2X.setText("");

			cmbox_result_unit.getSelectionModel().select(perform.unit());

			text_Result.setText(theAnswer); // If okay, display it in the result field and
			text_ResultErrorterm.setText(theAnswerET);
			label_Result.setText("Division"); // change the title of the field to "Division"

			cmbox_result_unit.getSelectionModel().select(0);
			;

		} else { // Some error occurred while doing the division.
			text_Result.setText(""); // Do not display a result if there is an error.
			label_Result.setText("Result"); // Reset the result label if there is an error.
			label_errResult.setText(perform.getResultErrorMessage()); // Display the error message.
			label_errOperand2X.setText("Divisor is invalid"); // Display error message if second operand is zero
		}
	}

	/**********
	 * This is the square root routine.
	 * 
	 */
	private void sqrtOperands() {
		cmbox_result_unit.getItems().clear();
		// cmbox_operand2_unit.getItems().clear();
		int ndx = cmbox_operand1_unit.getSelectionModel().getSelectedIndex();

		check.setIndexofUnits(ndx, ndx);

		if (text_Operand1.getText().trim().startsWith("-")) {
			label_errOperand1X.setText("***Error*** Invalid value");
			label_errOperand1.setText("Square root of negative number is not real");
			operand1ErrPart2.setText("\u21EB");
			return;
		}
		if (text_Operand1Errorterm.getLength() == 0) {
			text_Operand1Errorterm.setText("0.05");
		}
		// Check to see if both operands are defined and valid
		if (binaryOperandIssuesForSqrt() || label_errOperand1ETerm == null) { // If there are issues with the operands,
																				// return
			text_Result.setText("");
			return;
		} // without doing the computation

		// If the operands are defined and valid, request the business logic method to
		// do the square root and return the
		// result as a String. If there is a problem with the actual computation, an
		// empty string is returned
		String theAnswer = perform.squareroot(); // Call the business logic square root method // Reset any result error
													// messages from before
		String theAnswerET = perform.errorTerm();

		// Reset any result error messages from before
		if (theAnswer.length() > 0) { // Check the returned String to see if it is okay
			label_errOperand1X.setText("");
			label_errOperand2X.setText("");

			cmbox_result_unit.getSelectionModel().select(perform.unit());

			text_Result.setText(theAnswer); // If okay, display it in the result field and
			text_ResultErrorterm.setText(theAnswerET);

			label_Result.setText("Square root"); // change the title of the field to "Square root"
		} else { // Some error occurred while doing the square root.
			text_Result.setText(" "); // Do not display a result if there is an error.
			label_Result.setText("Result"); // Reset the result label if there is an error.
			label_errResult.setText(perform.getResultErrorMessage()); // Display the error message.
		}
	
	try {
		save();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
	



private void tableData() throws NoSuchElementException, FileNotFoundException {
		
	

		


		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(name);
		
		
		while (scanner.nextLine() != null) {
			while (scanner.hasNextLine()) {

				try {
				String[] tokens = scanner.nextLine().split(" ");

				if (text_Operand1.getText().matches(tokens[0])) {
					text_Operand1.setText(tokens[2]);

					text_Operand1Errorterm.setText(tokens[3]);
					cmbox_operand1_unit.getSelectionModel().select(tokens[4]);
				}
				}
				catch(ArrayIndexOutOfBoundsException e ) {
					e.printStackTrace();
				}

			}
			break;
				
		}
		}
		
	


	
		

	
	
private void tableData2() throws NoSuchElementException, FileNotFoundException {
		
		
		DefinitionsUserInterface b = new DefinitionsUserInterface();
		File name = new File( b.getData());

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(name);
		while (scanner.nextLine() != null) {
			while (scanner.hasNextLine()) {

				try {
				String[] tokens = scanner.nextLine().split(" ");

				if (text_Operand2.getText().matches(tokens[0])) {
					text_Operand2.setText(tokens[2]);

					text_Operand2Errorterm.setText(tokens[3]);
					cmbox_operand2_unit.getSelectionModel().select(tokens[4]);
				}
				}
				catch(ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}

			}
			break;
				

		}

	}

private void save() throws IOException {
	
	
	    String op1 = text_Operand1.getText();
	    String op1_err = text_Operand1Errorterm.getText();
	    String op1_unit = cmbox_operand1_unit.getSelectionModel().getSelectedItem();
	    String op2 = text_Operand2.getText();
	    String op2_err = text_Operand2Errorterm.getText();
	    String op2_unit = cmbox_operand2_unit.getSelectionModel().getSelectedItem();
		String result = text_Result.getText();
		String result_err = text_ResultErrorterm.getText();
		String result_unit = cmbox_result_unit.getSelectionModel().getSelectedItem();
		
		
		File savefile = new File ("ResultOut/Result.txt");
		try (FileWriter writer = new FileWriter(savefile,true)) {
			writer .append("Measured Value" + "              " + "Error Term" + "                   "+ "Units" + "\n");
			writer.append(op1 + "                              " + op1_err + "                           " + op1_unit + "\n" + op2 + "                              "+ op2_err + "                           "  + op2_unit + "\n" + result + " " + result_err + "     " + result_unit + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	
	return;
	
}
	

}
