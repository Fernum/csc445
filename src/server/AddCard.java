import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.io.*;

public class AddCard extends MessageCard implements ActionListener, FocusListener, DocumentListener {

	JLabel header = new JLabel("<html><center><h1>Enter Data to Add<br></h1></center></html>");
	JButton addButton = new JButton("Add");
	JButton backButton = new JButton("Back");
	JButton importButton = new JButton("Import");
	JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JTextField dateField = new JTextField("Date (format YYYYMMDD)",15);
	JTextField cityField = new JTextField("City (Optional)",15);
	JTextField visitsField = new JTextField("Visits",15);
	JTextField pPVisitField = new JTextField("Pages Per Visit",15);
	JTextField avgDurationField = new JTextField("Average Visit Duration",15);
	JTextField newVisitorsField = new JTextField("Percent New Visitors",15);
	JTextField bounceRateField = new JTextField("Bounce Rate",15);
	//JFileChooser fc = new JFileChooser();

	boolean datePrev = false;
	boolean cityPrev = false;
	boolean visitsPrev = false;
	boolean pPVisitPrev = false;
	boolean avgDurationPrev = false;
	boolean newVisitorsPrev = false;
	boolean bounceRatePrev = false;
	boolean dateFilled = false;
	boolean visitsFilled = false;
	boolean pPVisitFilled = false;
	boolean avgDurationFilled = false;
	boolean newVisitorsFilled = false;
	boolean bounceRateFilled = false;

	//layout not set in stone... i'll play with it if i have time
  public void initializeThisCard() {
    System.out.println("Initializing pane...");
		topPanel.add(header);
		add(topPanel,BorderLayout.NORTH);
		centerPanel.add(dateField);
		centerPanel.add(cityField);
		centerPanel.add(visitsField);
		centerPanel.add(pPVisitField);
		centerPanel.add(avgDurationField);
		centerPanel.add(newVisitorsField);
		centerPanel.add(bounceRateField);
		add(centerPanel,BorderLayout.CENTER);
		buttonPanel.add(addButton);
		buttonPanel.add(backButton);
		add(buttonPanel,BorderLayout.SOUTH);
	//	addButton.setEnabled(false);
		addActionListeners();
  }
  
  public AddCard() {
    super();
		setLayout(new BorderLayout());
  }
  
	public void addActionListeners() {
		addButton.addActionListener(this);
		importButton.addActionListener(this);
		backButton.addActionListener(this);
		dateField.addFocusListener(this);
		cityField.addFocusListener(this);
		visitsField.addFocusListener(this);
		pPVisitField.addFocusListener(this);
		avgDurationField.addFocusListener(this);
		newVisitorsField.addFocusListener(this);
		bounceRateField.addFocusListener(this);
	}

	  //clears preset text from username and password fields when cursor enters said field
	public void focusGained(FocusEvent e) {
		if (dateField.isFocusOwner())
			if (!datePrev) {
				dateField.setText("");
				datePrev = true;
			} else dateField.selectAll();
		if (cityField.isFocusOwner())
			if (!cityPrev) {
				cityField.setText("");
				cityPrev = true;
			} else cityField.selectAll();
		if (visitsField.isFocusOwner())
			if (!visitsPrev) {
				visitsField.setText("");
				visitsPrev = true;
			} else visitsField.selectAll();
		if (pPVisitField.isFocusOwner())
			if (!pPVisitPrev) {
				pPVisitField.setText("");
				pPVisitPrev = true;
			} else pPVisitField.selectAll();
		if (avgDurationField.isFocusOwner())
			if (!avgDurationPrev) {
				avgDurationField.setText("");
				avgDurationPrev = true;
			} else avgDurationField.selectAll();
		if (bounceRateField.isFocusOwner())
			if (!bounceRatePrev) {
				bounceRateField.setText("");
				bounceRatePrev = true;
			} else bounceRateField.selectAll();
		if (newVisitorsField.isFocusOwner())
			if (!newVisitorsPrev) {
				newVisitorsField.setText("");
				newVisitorsPrev = true;
			} else newVisitorsField.selectAll();
	
}

	//not used
	public void focusLost(FocusEvent e) {}

	//controls state of enter button, enabling only when both username and password are filled out
	public void disableIfEmpty(DocumentEvent e) {
		if (e.getDocument() == dateField.getDocument()) dateFilled = e.getDocument().getLength() > 0;
		if (e.getDocument() == visitsField.getDocument()) visitsFilled = e.getDocument().getLength() > 0;
		if (e.getDocument() == pPVisitField.getDocument()) pPVisitFilled = e.getDocument().getLength() > 0;
		if (e.getDocument() == avgDurationField.getDocument()) avgDurationFilled = e.getDocument().getLength() > 0;
		if (e.getDocument() == newVisitorsField.getDocument()) newVisitorsFilled = e.getDocument().getLength() > 0;
		if (e.getDocument() == bounceRateField.getDocument()) bounceRateFilled = e.getDocument().getLength() > 0;
		addButton.setEnabled(dateFilled && visitsFilled && pPVisitFilled && avgDurationFilled && newVisitorsFilled && bounceRateFilled);
	}

	//not used
	public void changedUpdate(DocumentEvent e) { disableIfEmpty(e); }
	public void insertUpdate(DocumentEvent e) { disableIfEmpty(e); }
	public void removeUpdate(DocumentEvent e) { disableIfEmpty(e); }

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addButton) {
				DataSet d = new DataSet();
				d.setCity(cityField.getText());
				d.setVisits(Integer.parseInt(visitsField.getText()));
				d.setPagesPerVisit(Double.parseDouble(pPVisitField.getText()));
				d.setAvgVisitDuration(avgDurationField.getText());
				d.setPercentNewVisits(Double.parseDouble(newVisitorsField.getText()));
				d.setBounceRate(Double.parseDouble(bounceRateField.getText()));
				if (Client.sendDataSet(d)) {
					header.setText("<html><center><h1>Record Added</h1></center></html>");
					dateField.setText("Date (format YYYYMMDD)");
					cityField.setText("City (Optional)");
					visitsField.setText("Visits");
					pPVisitField.setText("Pages Per Visit");
					avgDurationField.setText("Average Visit Duration");
					newVisitorsField.setText("Percent New Visitors");
					bounceRateField.setText("Bounce Rate");
					datePrev = false;
					cityPrev = false;
					pPVisitPrev = false;
					avgDurationPrev = false;
					newVisitorsPrev = false;
					bounceRatePrev = false;
					visitsPrev = false;
					addButton.setEnabled(false);
				} else header.setText("<html><center><h1>Unable to Add Record</h1><br>Check your typing</center></html>");
		}
		if (e.getSource() == backButton) Interface.switchToCard("welcome");
	/*	if (e.getSource() == importButton) {
			try{ //show file selection window
				fc.showOpenDialog(this);
			} catch (HeadlessException ex) {	ex.printStackTrace();	}
			  File selectedFile = fc.getSelectedFile();
				if (selectedFile != null)
					if (Client.sendFile(selectedFile)) header.setText("<html><center><h1>Record Added</h1></center></html>");
					else header.setText("<html><center><h1>Unable to Add Record</h1><br>Check your typing</center></html>");
		}*/									
  }
}
