package definitionDemo;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Comparator;

import java.util.NoSuchElementException;
import java.util.Scanner;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * <p>
 * Title: DefinitionsUserInterface Class.
 * </p>
 * 
 * <p>
 * Description: The Java/FX-based user interface for the Definitions. The class
 * works with String objects and tableView that passes work to other classes to deal with all
 * other aspects of the computation.
 * </p>
 * 
 * <p>
 * Copyright: Sawan 2019
 * </p>
 * 
 * @author  Sawan Gaba
 * 
 * @version 1.00 2019-02-26 Fist UI for Definitions using tableView
 * 
 * 							
 */






public class DefinitionsUserInterface {

	public ManageDefinitions perform;

	private Label lbl_EditingGuidance = // A Label used to guide the user
			new Label("Editing a Table Cell!  When finished, press <enter> or <return> to commit the change.");

	private static boolean whenSorting = false; // A flag to signal when to ignore case

	private Button Save = new Button("Save");
	private TextField SaveField = new TextField();
	private Button Load = new Button("Load");
	public static TextField LoadField = new TextField();

	
	public Label Save_Green = new Label("File has been saved Successfully");

	public TableView<Quantity> table = new TableView<>();
	public ObservableList<Quantity> TD = // The list of values being defined
			FXCollections.observableArrayList();

	public TableColumn<Quantity, String> col_NameValue = new TableColumn<Quantity, String>("Variable/Constant\nName");

	public TableColumn<Quantity, String> col_IsConstantValue = new TableColumn<Quantity, String>("Is a\nConstant");

	public TableColumn<Quantity, String> col_MeasureValue = new TableColumn<Quantity, String>("Measure or Value");

	public TableColumn<Quantity, String> col_UnitsValue = new TableColumn<Quantity, String>("Units");

	public TableColumn<Quantity, String> col_ErrorValue = new TableColumn<Quantity, String>("Error Term");

	String f;
	/**********
	 * This inner class is used to define the various fields required by the
	 * variable/constant definitions.
	 * 
	 * @author lrcarter
	 *
	 */
	
	public static class Quantity {
		SimpleStringProperty nameValue = new SimpleStringProperty(); // The name of the value
		SimpleStringProperty isConstantValue = new SimpleStringProperty(); // Specifies if this is a constant
		SimpleStringProperty measureValue = new SimpleStringProperty(); // The measured value
		SimpleStringProperty errorTermValue = new SimpleStringProperty(); // Error term, if there is one
		SimpleStringProperty unitsValue = new SimpleStringProperty(); // Units, if there is one

		public final StringProperty nameProperty() {
			return this.nameValue;
		}

		public final StringProperty isConstantProperty() {
			return this.isConstantValue;
		}

		public final StringProperty measureProperty() {
			return this.measureValue;
		}

		public final StringProperty errorTermProperty() {
			return this.errorTermValue;
		}

		public final StringProperty unitsProperty() {
			return this.unitsValue;
		}

		/*****
		 * This fully-specified constructor establishes all of the fields of a Quantity
		 * object
		 * 
		 * @param n - A String that specifies the name of the constant or variable
		 * @param c - A String that serves as a T or F flag as to where or not this is a
		 *          constant
		 * @param m - A String that specifies the measured value / value, if there is
		 *          one
		 * @param e - A String that specifies the error term, if there is one
		 * @param u - A String that specifies the units definition, if there is one
		 */
		public Quantity(String n, String c, String m, String e, String u) {
			this.nameValue = new SimpleStringProperty(n);
			this.isConstantValue = new SimpleStringProperty(c);
			this.measureValue = new SimpleStringProperty(m);
			this.errorTermValue = new SimpleStringProperty(e);
			this.unitsValue = new SimpleStringProperty(u);
		}

		public Quantity() {
			nameValue = null;
			isConstantValue = null;
			measureValue = null;
			errorTermValue = null;
			unitsValue = null;
		}

		/*****
		 * This getter gets the value of the variable / constant name field - If the
		 * whenSorting flag is true, this method return the String converted to lower
		 * case - otherwise, it return the String as is
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this
		 * code generates method calls to these routines given the name of the field, it
		 * follows this naming pattern.
		 * 
		 * @return String - of the name of the variable / constant
		 */
		public String getNameValue() {
			if (whenSorting)
				return nameValue.get().toLowerCase();
			else
				return nameValue.get();
		}

		/*****
		 * This Setter sets the value of the variable / constant name field
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this
		 * code generates method calls to these routines given the name of the field, it
		 * follows this naming pattern.
		 */
		public void setNameValue(String n) {
			nameValue.set(n);
		}

		/*****
		 * This getter gets the value of the isConstant flag field - If this field is
		 * true, the item being defined is a constant and the calculator will not be
		 * allowed to alter the value (but the calculator's user may editing the value
		 * of this item).
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this
		 * code generates method calls to these routines given the name of the field, it
		 * follows this naming pattern.
		 * 
		 * @return String - Either a "T" or an "F" String
		 */
		public String getIsConstantValue() {
			return isConstantValue.get();
		}

		/*****
		 * This Setter sets the value of the isConstant flag field - If the parameter c
		 * starts with a "T" or a "t", the field is set to "T", else it is set to "F".
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this
		 * code generates method calls to these routines given the name of the field, it
		 * follows this naming pattern.
		 * 
		 * @param c String - The first letter is used to determine if this is a "T" or
		 *          "F"
		 */
		public void setIsConstantValue(String c) {
			if (c.startsWith("T") || c.startsWith("t"))
				isConstantValue.set("T");
			else
				isConstantValue.set("F");
		}

		/*****
		 * This getter gets the value of the measureValue field.
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this
		 * code generates method calls to these routines given the name of the field, it
		 * follows this naming pattern.
		 * 
		 * @return String - A String of the measuredValue specification is returned
		 */
		public String getMeasureValue() {
			return measureValue.get();
		}

		/*****
		 * This Setter sets the value of the measuredValue field
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this
		 * code generates method calls to these routines given the name of the field, it
		 * follows this naming pattern.
		 * 
		 * @param c String - The value is assumed to be a value numeric string. It must
		 *          be checked before this routine is used.
		 */
		public void setMeasureValue(String m) {
			measureValue.set(m);
		}

		/*****
		 * This getter gets the value of the errorTerm field.
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this
		 * code generates method calls to these routines given the name of the field, it
		 * follows this naming pattern.
		 * 
		 * @return String - A String of the errorTerm specification is returned
		 */
		public String getErrorTermValue() {
			return errorTermValue.get();
		}

		/*****
		 * This Setter sets the value of the errorTerm field
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this
		 * code generates method calls to these routines given the name of the field, it
		 * follows this naming pattern.
		 * 
		 * @param e String - The value is assumed to be a value numeric string. It must
		 *          be checked before this routine is used.
		 */
		public void setErrorTermValue(String e) {
			errorTermValue.set(e);
		}

		/*****
		 * This getter gets the value of the units field.
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this
		 * code generates method calls to these routines given the name of the field, it
		 * follows this naming pattern.
		 * 
		 * @return String - A String of the units specification is returned
		 */
		public String getUnitsValue() {
			return unitsValue.get();
		}

		/*****
		 * This Setter sets the value of the unitsValue field
		 * 
		 * NOTE: Be very careful with the name, especially the capitalization as this
		 * code generates method calls to these routines given the name of the field, it
		 * follows this naming pattern.
		 * 
		 * @param u String - The value is assumed to be a value units string. It must be
		 *          checked before this routine is used.
		 */
		public void setUnitsValue(String u) {
			unitsValue.set(u);
		}
	}

	public DefinitionsUserInterface(Stage definitionStage) {

		// Create new stage for this pop-up window
		Stage dialog = new Stage();
		Pane thePane = new Pane();
		definitionStage.setTitle("Definitions");
		// Make this window an Application Modal, which blocks all GUI requests to other
		// windows
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setTitle("Variable / Constant Definition Table");
		dialog.initOwner(definitionStage);

		// Establish a new window pane and a TableView widget for that pane

		Scene dialogScene = new Scene(thePane, 630, 700); // Define the window width and height

		// **********//
		// Define each of the columns in the table view and set up the handlers to
		// support editing

		// This is the column that support the Name column. When the name of a
		// definition is changed
		// this code will cause the table of data to be re-sorted and rearranged so the
		// rows will
		// shown in the table as sorted.
		col_NameValue.setMinWidth(130);
		col_NameValue.setCellValueFactory(new PropertyValueFactory<>("nameValue"));
		col_NameValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

		// When one starts editing a Name column, a message is displayed giving guidance
		// on how to
		// commit the change when done.
		col_NameValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
			lbl_EditingGuidance.setVisible(true);
		});

		// When the user commits the change, the editing guidance message is once again
		// hidden and
		// the system sorts the data in the table so the data will always appear sorted
		// in the table
		col_NameValue.setOnEditCommit((CellEditEvent<Quantity, String> t) -> {
			((Quantity) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNameValue(t.getNewValue());
			TD.sort(Comparator.comparing(Quantity::getNameValue));
			lbl_EditingGuidance.setVisible(false);
		});

		// **********//
		// This is the column that supports the IsConstantValue field.
		col_IsConstantValue.setMinWidth(75);
		col_IsConstantValue.setCellValueFactory(new PropertyValueFactory<>("isConstantValue"));
		col_IsConstantValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

		// When one starts editing the IsConstantValue column, a message is displayed
		// giving
		// guidance on how to commit the change when done.
		col_IsConstantValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
			lbl_EditingGuidance.setVisible(true);
		});

		// When the user commits the change, the editing guidance message is once again
		// hidden and
		// the system sorts the data in the table so the data will always appear sorted
		// in the table
		col_IsConstantValue.setOnEditCommit((CellEditEvent<Quantity, String> t) -> {
			((Quantity) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setIsConstantValue(t.getNewValue());
			lbl_EditingGuidance.setVisible(false);
		});

		// **********//
		// This is the column that supports the MeasureValue field.
		col_MeasureValue.setMinWidth(175);
		col_MeasureValue.setCellValueFactory(new PropertyValueFactory<>("measureValue"));
		col_MeasureValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

		// When one starts editing the MeasureValue column, a message is displayed
		// giving
		// guidance on how to commit the change when done.
		col_MeasureValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
			lbl_EditingGuidance.setVisible(true);
		});

		// When the user commits the change, the editing guidance message is once again
		// hidden and
		// the system sorts the data in the table so the data will always appear sorted
		// in the table
		col_MeasureValue.setOnEditCommit((CellEditEvent<Quantity, String> t) -> {
			((Quantity) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setMeasureValue(t.getNewValue());
			lbl_EditingGuidance.setVisible(false);
		});

		// **********//
		// This is the column that supports the ErrorTermValue field.
		col_ErrorValue.setMinWidth(100);
		col_ErrorValue.setCellValueFactory(new PropertyValueFactory<>("errorTermValue"));
		col_ErrorValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

		// When one starts editing the ErrorTermValue column, a message is displayed
		// giving
		// guidance on how to commit the change when done.
		col_ErrorValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
			lbl_EditingGuidance.setVisible(true);
		});

		// When the user commits the change, the editing guidance message is once again
		// hidden and
		// the system sorts the data in the table so the data will always appear sorted
		// in the table
		col_ErrorValue.setOnEditCommit((CellEditEvent<Quantity, String> t) -> {
			((Quantity) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setErrorTermValue(t.getNewValue());
			lbl_EditingGuidance.setVisible(false);
		});

		// **********//
		// This is the column that supports the UnitsValue field.
		col_UnitsValue.setMinWidth(100);
		col_UnitsValue.setCellValueFactory(new PropertyValueFactory<>("unitsValue"));
		col_UnitsValue.setCellFactory(TextFieldTableCell.<Quantity>forTableColumn());

		// When one starts editing the UnitsValue column, a message is displayed giving
		// guidance on how to commit the change when done.
		col_UnitsValue.setOnEditStart((CellEditEvent<Quantity, String> t) -> {
			lbl_EditingGuidance.setVisible(true);
		});

		// When the user commits the change, the editing guidance message is once again
		// hidden and
		// the system sorts the data in the table so the data will always appear sorted
		// in the table
		col_UnitsValue.setOnEditCommit((CellEditEvent<Quantity, String> t) -> {
			((Quantity) t.getTableView().getItems().get(t.getTablePosition().getRow())).setUnitsValue(t.getNewValue());
			lbl_EditingGuidance.setVisible(false);
		});

		// **********//
		// The follow sets up the editing guidance text,. positions it below the table,
		// sets the
		// text red, and hides the text so it is only shown during the edit process.
		lbl_EditingGuidance.setMinWidth(600);
		lbl_EditingGuidance.setLayoutX(20);
		lbl_EditingGuidance.setLayoutY(470);
		lbl_EditingGuidance.setTextFill(Color.RED);
		lbl_EditingGuidance.setVisible(false);

		// The right-most three columns are grouped into a single column as they define
		// the value
		// elements of the definition.
		TableColumn<Quantity, String> col_ValueGroup = new TableColumn<Quantity, String>("Value");
		col_ValueGroup.getColumns().add(col_MeasureValue);
		col_ValueGroup.getColumns().add(col_ErrorValue);
		col_ValueGroup.getColumns().add(col_UnitsValue);

		TD.sort(Comparator.comparing(Quantity::getNameValue));
		// This loads the data from the ObservableList into the table, so the TableView
		// code can
		// display it and provide all of the functions that it provides
		table.setItems(TD);

		// This calls add the three major column titles into the table. Notice that the
		// right most
		// column is a composite of the three value fields (measure, error term, and
		// units)
		table.getColumns().add(col_NameValue);
		table.getColumns().add(col_IsConstantValue);
		table.getColumns().add(col_ValueGroup);

		

		Save_Green.setLayoutX(200);
		Save_Green.setLayoutY(670);
		Save_Green.setMaxSize(1000, 100);
		Save_Green.setStyle("-fx-font-weight: bold;");
		Save_Green.setTextFill(Color.GREEN);
		Save_Green.setVisible(false);

		// Establish a button to close this pop-up window
		LoadField.setLayoutX(20);
		LoadField.setLayoutY(15);
		
		LoadField.setMinWidth(450);
		try {
			load2();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		LoadField.setOnAction((event) -> {
		});
		// Establish a button to close this pop-up window
		setupButtonUI(Load, 100, 500, 15);
		Load.setOnAction((event) -> {

			try {
				load1();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		// Establish a button to close this pop-up window
		Button btn_Close = new Button("Close");
		setupButtonUI(btn_Close, 100, 20, 65);
		btn_Close.setOnAction((event) -> {
			dialog.close();
		});

		// Establish a button to add a new row into the TableView into the set of
		// definitions
		Button btn_Add = new Button("Add a new Item");
		setupButtonUI(btn_Add, 150, 140, 65);
		Button btn_Delete = new Button("Delete an Item");
		btn_Add.setOnAction((event) -> {

		

			// Create a new row after last row in the table
			Quantity q = new Quantity("?", "F", "?", "?", "?");
			TD.add(q);
			btn_Delete.setDisable(false);
			int row = TD.size() - 1;

			// Select the row that was just created
			table.requestFocus();
			table.getSelectionModel().select(row);
			table.getFocusModel().focus(row);
		});

		// Establish a button to delete a row in the TableView into the set of
		// definitions
		setupButtonUI(btn_Delete, 150, 310, 65);

		SaveField.setLayoutX(20);
		SaveField.setLayoutY(565);
		SaveField.setMinWidth(450);

		// Establish a button to close this pop-up window
		setupButtonUI(Save, 100, 500, 565);
		Save.setOnAction((event) -> {
			Savev();
			
		});

		// If there is no data in the table, then disable the Delete Button else enable
		// it
		if (TD.size() <= 0)
			btn_Delete.setDisable(true);
		else
			btn_Delete.setDisable(false);

		// This button handler deals with the various cases that arise when deleting a
		// table row
		btn_Delete.setOnAction((event) -> {
			// Get selected row and delete
			int ix = table.getSelectionModel().getSelectedIndex();
			if (ix <= -1)
				return;
			TD.remove(ix);
			if (table.getItems().size() == 0) {
				btn_Delete.setDisable(true);
				return;
			}
			if (ix != 0) {
				ix = ix - 1;
			}
			table.requestFocus();
			table.getSelectionModel().select(ix);
			table.getFocusModel().focus(ix);
		});

		// Make the table editable and position it in the pop-up window
		table.setEditable(true);
		table.setLayoutX(20);
		table.setLayoutY(120);

		// With all of the GUI elements defined and initialized, we add them to the
		// window pane
		thePane.getChildren().addAll(btn_Close, btn_Add, btn_Delete, table, Save, SaveField, Load, LoadField,
				lbl_EditingGuidance,  Save_Green);

		// We set the scene into dialog for this window
		definitionStage.setScene(dialogScene);

		// We show the completed window to the user, making it possible for the user to
		// start
		// clicking on the various GUI widgets in order to make things happen.
		definitionStage.show();

	}
	public DefinitionsUserInterface() {
		// TODO Auto-generated constructor stub
	}
	public void Savev() {
		f=SaveField.getText();
		Save_Green.setVisible(true);
		final Scanner file = new Scanner(SaveField.getText());
		ManageDefinitions.Create(table, file);
		
		
		    String str = SaveField.getText();
		    BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter("fileName.txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		    try {
				writer.write(str);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		    try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
public String getData() throws FileNotFoundException {
	FileReader file1 = new FileReader("Filename.txt");
	Scanner scanner1 = new Scanner(file1);
String h= new String ("RepositoryOut/"  + scanner1.nextLine() + ".txt");

	return h;
}

/**********
 * Private local method to initialize the standard fields for a button
 */
private void setupButtonUI(Button b, double w, double x, double y) {
	b.setMinWidth(w);
	b.setLayoutX(x);
	b.setLayoutY(y);
}
	
public String getData2() {

	f=SaveField.getText();
return f;
}


public boolean get_table() {
	if(TD.isEmpty());
	return false;
}

	public void load1() throws IOException {
	
		File file = new File("RepositoryOut/" + LoadField.getText() + ".txt");

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(file);
		try {
		while (scanner.nextLine() != null) {
			
			
			while (scanner.hasNextLine()) {

				String[] tokens = scanner.nextLine().split(" ");

				

				int p = 0;

				try {

					Quantity q = new Quantity(tokens[p++], tokens[p++], tokens[p++], tokens[p++], tokens[p++]);
					TD.add(q);
				} catch (ArrayIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					break;
				}

			}
		}
		}
		catch(NoSuchElementException e) {
			
		}

		return;

	}
	
	
	
	
	public void load2() throws IOException {

		File file1 = new File("Filename.txt");

		Scanner scanner1 = new Scanner(file1);

		String scannername = scanner1.nextLine();
		
		File file = new File("RepositoryOut/" + scannername + ".txt");

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(file);
		try {
		while (scanner.nextLine() != null) {
			
			
			while (scanner.hasNextLine()) {

				String[] tokens = scanner.nextLine().split(" ");

				

				int p = 0;

				try {

					Quantity q = new Quantity(tokens[p++], tokens[p++], tokens[p++], tokens[p++], tokens[p++]);
					TD.add(q);
				} catch (ArrayIndexOutOfBoundsException e) {
				
					break;
				}

			}
		}
		}
		catch(NoSuchElementException e) {
			
		}

		return;

	}
		
	}


