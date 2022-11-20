package kdeUI;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.util.List;
import org.apache.commons.compress.utils.Lists;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.SystemColor;

public class UI extends JFrame {

	private JPanel contentPane;
    String result="";
    private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UI() {
		setBackground(new Color(0, 0, 0));
		setResizable(false);
        String result="";
		String Title="GroupD";
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100, 900, 587);
		setTitle(Title);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.textHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("run");
		contentPane.add(btnNewButton);
		btnNewButton.setBounds(762, 7, 114, 23);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"query 1", "query 2", "query 3", "query 4", "query 5", "query 6", "query 7", "query 8", "query 9", "query 10"}));
		comboBox.setBounds(10, 7, 742, 22);
		contentPane.add(comboBox);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(10, 87, 866, 453);
		contentPane.add(textArea);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 45, 866, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String choices = comboBox.getSelectedItem().toString();
				choices=query(choices);
				textArea.setText(search(choices));
			}
		});
		
	}
	private String query(String s) {
		switch(s) {
		case "query 1":
			s="PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
					+ "PREFIX groupD:  <http://www.semanticweb.org/liaosiyu/ontologies/2022/10/untitled-ontology-6/>\r\n"
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
					+ "PREFIX : <http://ontologies.geohive.ie/osi#>\r\n"
					+ "\r\n"
					+ "#SELECT (?value*?pop_num as ?number)\r\n"
					+ "SELECT ?value\r\n"
					+ "WHERE {\r\n"
					+ "?a a groupD:UnemploymentRegion.\r\n"
					+ "?a groupD:hasEthnicityType ?ethnicity;\r\n"
					+ "   groupD:hasTime ?time;\r\n"
					+ "   groupD:hasValue ?value;\r\n"
					+ "   groupD:hasRegion ?region.\r\n"
					+ "   {\r\n"
					+ "        Select ?region{\r\n"
					+ "            ?x groupD:hasRegion ?region;\r\n"
					+ "   			groupD:hasEthnicityType ?ethnicity;\r\n"
					+ "   			groupD:hasPopulationNum ?population;\r\n"
					+ "   			groupD:hasValue ?value. \r\n"
					+ "    		FILTER(?ethnicity=\"Asian\")\r\n"
					+ "		}\r\n"
					+ "		ORDER BY ?population\r\n"
					+ "		LIMIT 1\r\n"
					+ "	}\r\n"
					+ "	FILTER(?ethnicity=\"Asian\"&&?time=2019)\r\n"
					+ "}";
			textField.setText("The unemployment rate in the region with smallest Asian population.");
			//System.out.println(s);
			break;
		case "query 2": 
			s="PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
					+ "PREFIX groupD:  <http://www.semanticweb.org/liaosiyu/ontologies/2022/10/untitled-ontology-6/>\r\n"
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
					+ "\r\n"
					+ "#SELECT (?value*?pop_num as ?number)\r\n"
					+ "SELECT distinct ?number\r\n"
					+ "WHERE {\r\n"
					+ " ?a a groupD:UnemploymentRegion.\r\n"
					+ "    ?x a groupD:Population.\r\n"
					+ " ?x groupD:Contains1 ?region.\r\n"
					+ " ?x groupD:Contains2 ?ethnicity.\r\n"
					+ " ?x groupD:hasPopulationNum ?pop_num.\r\n"
					+ " ?a groupD:hasValue ?value.\r\n"
					+ " ?a groupD:hasRegion ?region1.\r\n"
					+ " ?a groupD:hasTime ?time.\r\n"
					+ " ?a groupD:hasEthnicityType ?ethnicity2.\r\n"
					+ "    FILTER(?region1 = \"London\" && ?ethnicity2 = \"White\" && ?time = 2010)\r\n"
					+ "    FILTER(?region = ?a && ?ethnicity = ?a)\r\n"
					+ "    bind(xsd:int(?pop_num*?value*0.01) As ?number) \r\n"
					+ "}";
			textField.setText("The number of unemployed white people living in the London area.");
			//System.out.println(s);
			break;
		case "query 3": 
			s="PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
					+ "PREFIX groupD:  <http://www.semanticweb.org/liaosiyu/ontologies/2022/10/untitled-ontology-6/>\r\n"
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
					+ "\r\n"
					+ "#SELECT (?value*?pop_num as ?number)\r\n"
					+ "SELECT distinct ?number\r\n"
					+ "WHERE {\r\n"
					+ " ?a a groupD:Unemployment.\r\n"
					+ " ?x a groupD:Ethnicity.\r\n"
					+ " ?x groupD:HasAPopulationOf ?local1.\r\n"
					+ " ?x groupD:hasValue ?value.\r\n"
					+ " ?x groupD:hasEthnicityType ?ethnicity1.\r\n"
					+ " ?a groupD:hasEthnicityType ?ethnicity2.\r\n"
					+ " ?a groupD:hasLocal ?local2.\r\n"
					+ " ?a groupD:hasTime ?time.\r\n"
					+ " ?a groupD:hasNum ?num\r\n"
					+ "    FILTER(?local2 = \"Newham\" && ?ethnicity2 = \"All\" && ?time = 2004 && ?ethnicity1 = \"Asian/Asian British: Chinese\")\r\n"
					+ "    FILTER(?local1 = ?a)\r\n"
					+ "    bind(xsd:int(?num*?value*0.01) As ?number) \r\n"
					+ "}";
			textField.setText("Based on the unemployment data of Newham in 2004, predict the number of unemployed Chinese in Newham in 2011");
			//System.out.println(s);
			break;
		case "query 4": 
			s="PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
					+ "PREFIX groupD:  <http://www.semanticweb.org/liaosiyu/ontologies/2022/10/untitled-ontology-6/>\r\n"
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
					+ "\r\n"
					+ "#SELECT (?value*?pop_num as ?number)\r\n"
					+ "SELECT distinct ?number\r\n"
					+ "WHERE {\r\n"
					+ " ?a a groupD:Population.\r\n"
					+ " ?x a groupD:Region.\r\n"
					+ " ?x groupD:Are_composed ?region1.\r\n"
					+ " ?a groupD:hasEthnicityType ?ethnicity1.\r\n"
					+ " ?a groupD:hasPopulationNum ?num.\r\n"
					+ " ?x groupD:hasEthnicityType ?ethnicity2.\r\n"
					+ " ?x groupD:hasRegion ?region2.\r\n"
					+ " ?x groupD:hasValue ?value.\r\n"
					+ " ?x groupD:hasUrbanRural ?loc.\r\n"
					+ "    FILTER(?region2 = \"North East\" && ?ethnicity2 = \"Black\" && ?loc = \"Rural\" && ?ethnicity1 = \"Black\")\r\n"
					+ "    FILTER(?region1 = ?a)\r\n"
					+ "    bind(xsd:int(?num*?value*0.01) As ?number) \r\n"
					+ "}";
			textField.setText("How many black people in North East of England live in rural areas in 2011？");
			//System.out.println(s);
			break;
		case "query 5": 
			s="PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
					+ "PREFIX groupD:  <http://www.semanticweb.org/liaosiyu/ontologies/2022/10/untitled-ontology-6/>\r\n"
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
					+ "\r\n"
					+ "#SELECT (?value*?pop_num as ?number)\r\n"
					+ "SELECT distinct ?number\r\n"
					+ "WHERE {\r\n"
					+ " ?a a groupD:Population.\r\n"
					+ " ?x a groupD:Household.\r\n"
					+ " ?a groupD:Have_a ?ethnicity1.\r\n"
					+ " ?a groupD:hasRegion ?region.\r\n"
					+ " ?a groupD:hasPopulationNum ?num.\r\n"
					+ " ?x groupD:hasEthnicityType ?ethnicity2.\r\n"
					+ " ?x groupD:hasIncome ?income.\r\n"
					+ " ?x groupD:hasValue ?value.\r\n"
					+ " ?x groupD:hasTime ?time.\r\n"
					+ "    FILTER(?income = \"GBP 2,000 or more\" && ?ethnicity2 = \"Black\" && ?time = 2011 && ?region = \"London\")\r\n"
					+ "    FILTER(?ethnicity1 = ?x)\r\n"
					+ "    bind(xsd:int(?num*?value*0.01) As ?number) \r\n"
					+ "}";
			textField.setText("How many black people in London have a average weekly household income over GBP 2000");
			//System.out.println(s);
			break;
		case "query 6": 
			s="PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
					+ "PREFIX groupD:  <http://www.semanticweb.org/liaosiyu/ontologies/2022/10/untitled-ontology-6/>\r\n"
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
					+ "\r\n"
					+ "#SELECT (?value*?pop_num as ?number)\r\n"
					+ "SELECT distinct ?num\r\n"
					+ "WHERE {\r\n"
					+ " ?a a groupD:Region.\r\n"
					+ " ?x a groupD:UnemploymentRegion.\r\n"
					+ " ?a groupD:lives_in ?ethnicity1.\r\n"
					+ " ?a groupD:hasUrbanRural ?loc.\r\n"
					+ " ?a groupD:hasValue ?value.\r\n"
					+ " ?x groupD:hasEthnicityType ?ethnicity2.\r\n"
					+ " ?x groupD:hasNumerator ?num.\r\n"
					+ " ?x groupD:hasRegion ?region.\r\n"
					+ " ?x groupD:hasTime ?time.\r\n"
					+ "    FILTER(?region = \"London\" && ?ethnicity2 = \"Asian\" && ?time = 2004 && ?loc = \"Rural\")\r\n"
					+ "    FILTER(?ethnicity1 = ?x)\r\n"
					+ "    bind(xsd:int(?num*?value*0.01) As ?number) \r\n"
					+ "}";
			textField.setText("predict the number of unemployed asian people who live in rural areas of London in 2011");
			//System.out.println(s);
			break;
		case "query 7": 
			s="PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
					+ "PREFIX groupD:  <http://www.semanticweb.org/liaosiyu/ontologies/2022/10/untitled-ontology-6/>\r\n"
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
					+ "\r\n"
					+ "#SELECT (?value*?pop_num as ?number)\r\n"
					+ "SELECT distinct ?number\r\n"
					+ "WHERE {\r\n"
					+ " ?a a groupD:Ethnicity.\r\n"
					+ " ?x a groupD:PopWhole.\r\n"
					+ " ?a groupD:hasEthnicityType ?ethnicity1.\r\n"
					+ " ?a groupD:hasLocal ?region.\r\n"
					+ " ?a groupD:hasValue ?value1.\r\n"
					+ " ?x groupD:hasEthnicityType ?ethnicity2.\r\n"
					+ " ?x groupD:hasValueType ?valueType.\r\n"
					+ " ?x groupD:hasValue ?value2.\r\n"
					+ " ?x groupD:hasTime ?time.\r\n"
					+ "    FILTER(?region = \"Manchester\" && ?ethnicity1 = \"White: Irish\" && ?time = 2011 && ?ethnicity2 = \"White - Irish\" && ?valueType = \"% of pop\")\r\n"
					+ "    bind(xsd:double(?value1 - ?value2) As ?number) \r\n"
					+ "}";
			textField.setText("the proportion of white Irish people in Manchester differ from the proportion of white Irish people in England and Wales");
			//System.out.println(s);
			break;
		case "query 8": 
			s="PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
					+ "PREFIX groupD:  <http://www.semanticweb.org/liaosiyu/ontologies/2022/10/untitled-ontology-6/>\r\n"
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
					+ "\r\n"
					+ "#SELECT (?value*?pop_num as ?number)\r\n"
					+ "SELECT distinct ?number\r\n"
					+ "WHERE {\r\n"
					+ " ?a a groupD:HourlyAvgPay.\r\n"
					+ " ?x a groupD:PopWhole.\r\n"
					+ " ?a groupD:hasEthnicityType ?ethnicity1.\r\n"
					+ " ?a groupD:hasTime ?time.\r\n"
					+ " ?a groupD:hasValue ?value1.\r\n"
					+ " ?x groupD:hasEthnicityType ?ethnicity2.\r\n"
					+ " ?x groupD:hasValueType ?valueType.\r\n"
					+ " ?x groupD:hasValue ?value2.\r\n"
					+ "    FILTER(?ethnicity1 = \"White British\" && ?time = 2013 && ?ethnicity2 = \"White - British\" && ?valueType = \"count\")\r\n"
					+ "    bind(xsd:int(?value1 * ?value2) As ?number) \r\n"
					+ "}";
			textField.setText("How much earning by puting all the white British people in England and Wales work an hour.");
			//System.out.println(s);
			break;
		case "query 9": 
			s="PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
					+ "PREFIX groupD:  <http://www.semanticweb.org/liaosiyu/ontologies/2022/10/untitled-ontology-6/>\r\n"
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
					+ "\r\n"
					+ "#SELECT (?value*?pop_num as ?number)\r\n"
					+ "SELECT distinct ?number\r\n"
					+ "WHERE {\r\n"
					+ " ?a a groupD:Unemployment.\r\n"
					+ " ?x a groupD:UnemploymentRegion.\r\n"
					+ " ?a groupD:hasEthnicityType ?ethnicity1.\r\n"
					+ " ?a groupD:hasTime ?time1.\r\n"
					+ " ?a groupD:hasLocal ?loc.\r\n"
					+ " ?a groupD:hasValue ?value1.\r\n"
					+ " ?x groupD:hasEthnicityType ?ethnicity2.\r\n"
					+ " ?x groupD:hasTime ?time2.\r\n"
					+ " ?x groupD:hasRegion ?region.\r\n"
					+ " ?x groupD:hasValue ?value2.\r\n"
					+ "    FILTER(?ethnicity1 = \"White\" && ?time1 = 2004 && ?ethnicity2 = \"White\" && ?time2 = 2004 && ?region = \"North East\" && ?loc = \"Liverpool\")\r\n"
					+ "    bind(xsd:int(?value1 - ?value2) As ?number) \r\n"
					+ "}";
			textField.setText("unemployment rate of white people in Liverpool differ from the unemployment rate of white people in the whole North East of England");
			//System.out.println(s);
			break;
		case "query 10": 
			s="PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n"
					+ "PREFIX groupD:  <http://www.semanticweb.org/liaosiyu/ontologies/2022/10/untitled-ontology-6/>\r\n"
					+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\r\n"
					+ "\r\n"
					+ "#SELECT (?value*?pop_num as ?number)\r\n"
					+ "SELECT distinct ?number\r\n"
					+ "WHERE {\r\n"
					+ " ?a a groupD:HourlyAvgPay.\r\n"
					+ " ?x a groupD:Unemployment.\r\n"
					+ " ?a groupD:hasEthnicityType ?ethnicity1.\r\n"
					+ " ?a groupD:hasTime ?time1.\r\n"
					+ " ?a groupD:hasValue ?value1.\r\n"
					+ " ?x groupD:hasEthnicityType ?ethnicity2.\r\n"
					+ " ?x groupD:hasTime ?time2.\r\n"
					+ " ?x groupD:hasLocal ?region.\r\n"
					+ " ?x groupD:hasNum ?num.\r\n"
					+ "    FILTER(?ethnicity1 = \"White\" && ?time1 = 2013 && ?ethnicity2 = \"White\" && ?time2 = 2013 && ?region = \"Adur\")\r\n"
					+ "    bind(xsd:int(?value1 * ?num) As ?number) \r\n"
					+ "}";
			textField.setText("How much earning by  put all the unemployment white people in Adur  together working an hour?");
			//System.out.println(s);
			break;	
		}
		return s;
	}
	private String search(String param) {
        Model model = ModelFactory.createDefaultModel();
        model.read("target/final_new.ttl");
        //FileManager.get().addLocatorClassLoader(QueryDemo.class.getClassLoader());
        //@SuppressWarnings("deprecation")
		//Model model = FileManager.get().loadModel("final_new.ttl");
        String queryString =param;
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        try {
            ResultSet results = qexec.execSelect();
//            while(results.hasNext()) {
//            	QuerySolution soln=results.nextSolution();
//            	//RDFNode number=soln.get("number");
//            	Resource x = soln.getResource("number");
//            	System.out.println(x.toString());
//            }
            result=ResultSetFormatter.asXMLString(results);
//            System.out.println(result);
        } finally {
            qexec.close();
        }
        return result;
        //textField.setText(param);
    }
}


