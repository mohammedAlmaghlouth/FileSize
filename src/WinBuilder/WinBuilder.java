package WinBuilder;

// import files
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultTreeCellRenderer;


public class WinBuilder {
	private Composite the_root;
	private JFrame the_frame;
	private JTree the_tree;
	private static JTextPane textPane;
	String rootPath = "";

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WinBuilder window = new WinBuilder();
					window.the_frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public WinBuilder() {
		initialize();
	}

	
	public static long calculateTheSize(File Document) {

		if (Document.isFile()) {
			return Document.length();
		}
		
		long docsNum;
		docsNum = 0;
		
		for (File DocumentChild : Document.listFiles()) {
			docsNum = docsNum + calculateTheSize(DocumentChild);
		}
		return docsNum;
	}
	
	
	private void initialize() {
		the_frame = new JFrame();
		the_frame.getContentPane().setBackground(new Color(221, 221, 221));
		the_frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(12, 12, 192, 370);
		the_frame.getContentPane().add(scrollPane);

		JButton nButton = new JButton("Select folder");

		nButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int option = fileChooser.showOpenDialog(the_frame);
				if(option == JFileChooser.APPROVE_OPTION){
					File file = fileChooser.getSelectedFile();
					rootPath = file.getAbsolutePath();
					goingThroughFiles(file);
					the_tree = new JTree( Adapter.ToJTree(the_root) );
					scrollPane.setColumnHeaderView(the_tree);
					
					Icon closingLogo = new ImageIcon("icon_closedFile.png");
					Icon openingLogo = new ImageIcon("icon_openFile.png");
					
					DefaultTreeCellRenderer r = new DefaultTreeCellRenderer();
					r.setLeafIcon(closingLogo);
					r.setClosedIcon(closingLogo);
					r.setOpenIcon(openingLogo);
					the_tree.setCellRenderer(r);
					the_frame.invalidate();
					the_frame.validate();
					the_frame.repaint();
					the_tree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
						public void valueChanged(javax.swing.event.TreeSelectionEvent event) {
							treeValueChanged(event);
						}
					});
				}
			}
		}
				);

		nButton.setBounds(12, 391, 110, 32);
		the_frame.getContentPane().add(nButton);
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(215, 10, 359, 372);
		the_frame.getContentPane().add(textPane);	


		the_frame.setBounds(100, 100, 600, 470);
		the_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void goingThroughFiles(File file) {
		the_root = new Composite(file.getName());
		for (File folder : file.listFiles()) {
			traverseFile(folder, the_root);
		}
	}
	public static void traverseFile(File f, Composite root) {
		// BASE CASE
		if (f.isFile()) {
			Leaf leaf = new Leaf(f.getName(), f.length());
			root.Add(leaf);
		}
		else if(f.isDirectory()){
			Composite composite = new Composite(f.getName());
			for (File folder : f.listFiles()) {
				traverseFile(folder, composite);
				
			}

			root.Add(composite); 
		}
	}


	private void treeValueChanged(javax.swing.event.TreeSelectionEvent evt) {         
		try {
		textPane.setText("");
		String node = evt.getNewLeadSelectionPath().getLastPathComponent().toString();
		
		String mainPath = evt.getPath().toString();
		
		String filepath = rootPath;
		String[] listPath;

		mainPath = mainPath.substring(1, mainPath.length()-1);
		listPath = mainPath.split(",");
		for(int i=1; i<listPath.length; i++) {
			filepath = filepath + "\\" + listPath[i].strip();
		}

		textPane.setText(textPane.getText() + node + " (" + calculateTheSize(new File(filepath)) +" B)");

		recursiveList(filepath, 5);
		}catch(Exception e) {}
	}

	public static void recursiveList(String path, int depth) {
		File theFile_i = new File(path);
		File[] fl = theFile_i.listFiles();
		for (int i = 0; i < fl.length; i++) {
			if (fl[i].isDirectory() && !fl[i].isHidden()) {
				
				textPane.setText(textPane.getText() + "\n");
				for(int j=0; j<depth ; j++) {textPane.setText(textPane.getText() + " ");};
				textPane.setText(textPane.getText() + fl[i].getName() + "  (" + calculateTheSize(fl[i]) + " B) ");
				recursiveList(fl[i].getAbsolutePath(), depth+5 );
			} else {
				textPane.setText(textPane.getText() + "\n");
				for(int j=0; j<depth ; j++) {textPane.setText(textPane.getText() + " ");};
				textPane.setText(textPane.getText() + fl[i].getName() + "  (" + fl[i].length() + "B) ");
			}
		}
	}
}
