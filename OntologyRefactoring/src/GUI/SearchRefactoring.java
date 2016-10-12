package GUI;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import javax.swing.table.DefaultTableModel;

import com.github.jsonldjava.utils.Obj;

import DAO.DAO_Refactoring;
import DAO.Refactoring;
import Util.createListCheckBox;
import Util.owlUtil;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class SearchRefactoring extends JInternalFrame {
	private DefaultTableModel model;
	private owlUtil owl = new owlUtil();
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchRefactoring frame = new SearchRefactoring();
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
	public SearchRefactoring() {
		// owl.readOwl();
		Collection<JCheckBox> lcbAdvantages = createListCheckBox
				.createListCheckBoxOwlClass(owl.getAllClassLabel("Advantage"));
		Collection<JCheckBox> lcbDomains = createListCheckBox
				.createListCheckBoxOwlClass(owl.getAllClassLabel("Domain"));

		setMaximizable(true);
		setTitle("Search");
		setClosable(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new MigLayout("", "[33%][33%][33%]", "[][][]"));

		JLabel lblNewLabel = new JLabel("Advantages");
		panel.add(lblNewLabel, "cell 0 0,growx");

		JLabel lblNewLabel_1 = new JLabel("Domain");
		panel.add(lblNewLabel_1, "cell 1 0,grow");
		


JCheckBox chckbxReverse = new JCheckBox("Reverse");
chckbxReverse.setHorizontalAlignment(SwingConstants.CENTER);
panel.add(chckbxReverse, "cell 2 2,alignx center,aligny center");
		
				JButton btnExemplos = new JButton("Get Example");
				btnExemplos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(table.getSelectedRow() > -1 ){
							DAO_Refactoring daoRefactoring = new DAO_Refactoring();
							String refactoring = table.getValueAt(table.getSelectedRow(), 0).toString();
							JOptionPane.showMessageDialog(null, refactoring +"\n\n"+ daoRefactoring.getExamplesRefacotringString(refactoring));
						} else {
							JOptionPane.showMessageDialog(null, "Select a table of refactoring.");
						}
						
					}
				});
				
						JButton btnSearch = new JButton("Search");
						btnSearch.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {

								List<String> lAdvantages = new ArrayList<>();
								List<String> lDomains = new ArrayList<>();

								for (JCheckBox chb : lcbDomains) {
									if (chb.isSelected()) {
										lDomains.add(chb.getText());
									}
								}

								for (JCheckBox chb : lcbAdvantages) {
									if (chb.isSelected()) {
										lAdvantages.add(chb.getText());
									}
								}

								DAO_Refactoring daoRefactoring = new DAO_Refactoring();
								Collection<Refactoring> lRefactoringResults = daoRefactoring
										.getRefactoringFilterAdvantegeDomain(lDomains, lAdvantages, chckbxReverse.isSelected());
								pesquisa(lRefactoringResults, chckbxReverse.isSelected());

							}
						});
						
						panel.add(btnSearch, "cell 2 0,alignx center,aligny center");
						panel.add(btnExemplos, "flowy,cell 2 1,alignx center,aligny center");
				
						

		// criando checkbox
		int i = 1;
		for (Iterator<JCheckBox> it = lcbAdvantages.iterator(); it.hasNext(); i++) {
			panel.add(it.next(), "cell 0 " + i + ",grow");
		}

		// criando checkbox
		i = 1;
		for (Iterator<JCheckBox> it = lcbDomains.iterator(); it.hasNext(); i++) {
			panel.add(it.next(), "cell 1 " + i + ",grow");
		}

		// criando checkbox
		// for (i = 1; i <= 4; i++) {
		// JCheckBox chb = new JCheckBox("New CheckBox" + i);
		// panel.add(chb, "cell 2 " + i + ",grow");
		// }

		model = new DefaultTableModel(new Object[][] { { null, null }, }, new String[] { "Name", "Author" }) {
			// Class[] columnTypes = new Class[] { String.class, String.class,
			// String.class };
			//
			// public Class getColumnClass(int columnIndex) {
			// return columnTypes[columnIndex];
			// }
			// boolean[] columnEditables = new boolean[] {
			// false, false, false
			// };
			// public boolean isCellEditable(int row, int column) {
			// return columnEditables[column];
			// }
		};

		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setModel(model);
		scrollPane.setViewportView(table);
	}

	void pesquisa(Collection<Refactoring> lRefacotring, Boolean reverseSelected) {
		model.setNumRows(0);
		Object[] data;
		
		if (lRefacotring.size() > 0) {
			if (reverseSelected) {
				model.setColumnIdentifiers(new String[] { "Name", "Author", "Work Title", "Reverse" });
			} else {
				model.setColumnIdentifiers(new String[] { "Name", "Author", "Work Title" });
			}


			for (Refactoring r : lRefacotring) {

				if (reverseSelected) {
					data = new Object[] { r.getName(), r.getAuthor(), r.getWorkTitle(), r.getReverse() };
				} else {
					data = new Object[] { r.getName(), r.getAuthor(), r.getWorkTitle() };
				}
				// model.addRow(new Object[] { r.getName(), r.getAuthor(),
				// r.getWorkTitle(), r.getReverse() });
				model.addRow(data);
			}
		} else {
			model.setColumnIdentifiers(new String[] { "Message" });
			data = new Object[] { "No results" };
			model.addRow(data);
		}

		

	}
}
