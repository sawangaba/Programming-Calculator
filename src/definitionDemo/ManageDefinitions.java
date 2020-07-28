package definitionDemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import definitionDemo.DefinitionsUserInterface.Quantity;
import dictionary.Dictionary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;



/**
 * <p>
 * Title: ManageDefinitions Class.
 * </p>
 * 
 * <p>
 * Description: The Java/FX-based controller for the Definitions. The class
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
 * @version 1.00 2019-02-26 Controller class for Definitions using tableView
 * 
 * 							
 */







public class ManageDefinitions {

	public static Dictionary<String> dictionary;

	private TableView<Quantity> table = new TableView<>();

	private ObservableList<Quantity> tableData = FXCollections.observableArrayList();



	public static void Create(TableView<Quantity> t, Scanner FileN) {
		
		
		List<List<String>> list = new ArrayList<>();
		Quantity data = new Quantity();
		for (int i = 0; i < t.getItems().size(); i++) {
			data = t.getItems().get(i);
			list.add(new ArrayList<>());
			
			list.get(i).add("\n" + data.nameValue.get());
			list.get(i).add(" " + data.isConstantValue.get());
			list.get(i).add(" " + data.measureValue.get());
			list.get(i).add(" " + data.errorTermValue.get());
			list.get(i).add(" " + data.unitsValue.get() );
		}
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).size(); j++) {
				
			}
		}
		dictionary = new Dictionary<String>();
		dictionary.saveDictionary(list, FileN);

	}

	public static void Read(Scanner Reader) throws IOException {
		Dictionary<String> reader = new Dictionary<String>();

		reader.loadDictionary(Reader);
	}

	

	public String toString() {
		return null;

	}

	public ManageDefinitions() {
	}

}
