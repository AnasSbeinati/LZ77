package lZW;
/**
 * @author Anoos
 * @since 29:10:2014
 * @version 1.0
 * sub program to make compression
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

import java.awt.Panel;
import java.awt.Color;

import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import java.awt.SystemColor;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileFilter;

import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JScrollPane;

import java.io.*;

public class HufmanForm extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HufmanForm frame = new HufmanForm();
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
	JButton btnNewButton_1,btnNewButton_2,btnDecompress,button,button_1;
	JTextPane textPane_1;
	public HufmanForm() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 357);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 0, 414, 308);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setToolTipText("");
		tabbedPane.addTab("Compress Text", null, panel, null);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 389, 224);
		panel.add(scrollPane);
		
		JTextArea txtrTypeYourText = new JTextArea();
		scrollPane.setViewportView(txtrTypeYourText);
		txtrTypeYourText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				btnNewButton_2.setEnabled(true);
				btnNewButton_1.setEnabled(true);
			}
		});
		txtrTypeYourText.setBackground(new Color(245, 245, 220));
		txtrTypeYourText.setText("Type your text here");
		
	    JButton btnNewButton = new JButton("Clear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtrTypeYourText.setText("");
				btnNewButton_2.setEnabled(false);
				btnNewButton_1.setEnabled(false);
				btnDecompress.setEnabled(false);
				txtrTypeYourText.setEnabled(true);
			}
		});
		btnNewButton.setBounds(10, 246, 70, 23);
		panel.add(btnNewButton);
		
	    btnNewButton_1 = new JButton("Ratio");
	    btnNewButton_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		 long ratio=LZWFunctions.getSizeAfter()/LZWFunctions.getSizeBefore();
	    		 JOptionPane.showMessageDialog(null, String.format("%d",ratio));
	    	}
	    });
		btnNewButton_1.setEnabled(false);
		btnNewButton_1.setBounds(90, 246, 76, 23);
		panel.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Compress");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text=txtrTypeYourText.getText();
				if(text!="")
				{
				    int  index=0;
				    String temp2="";
				    int fileSize=text.length();
				    LZWFunctions.setSizeBefore(fileSize);
				    while(index<fileSize)
				    {
				    	try
				    	{
				    	String temp="";
				    	if(fileSize-index>=1000)
				    	   temp+=LZWFunctions.compressLZW(text.substring(index, index+=1000));
				    	else
				    	{
				    		temp+=LZWFunctions.compressLZW(text);
				    		index=fileSize;
				    	}
				    	temp2+=temp;
				    	}
				    	catch(Exception e)
				    	{
				    		JOptionPane.showMessageDialog(null,e.getLocalizedMessage());
				    	}
				    }
				    txtrTypeYourText.setText(temp2);
				    LZWFunctions.setSizeAfter(temp2.length());
				    //txtrTypeYourText.setEnabled(false);
				    btnDecompress.setEnabled(true);
				    btnNewButton_2.setEnabled(false);
				    JOptionPane.showMessageDialog(null,"test has been Compressed");
				}
			}
		});
		btnNewButton_2.setEnabled(false);
		btnNewButton_2.setBounds(176, 246, 94, 23);
		panel.add(btnNewButton_2);
		
		btnDecompress = new JButton("Decompress");
		btnDecompress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String text=txtrTypeYourText.getText();
				if(text!="")
				{
				    int  index=0;
				    String temp2="";
				    int fileSize=text.length();
				    while(index<fileSize)
				    {
				    	try
				    	{
				    	String temp="";
				    	if(fileSize-index>=1000)
				    	   temp+=LZWFunctions.deCompressLZW(text.substring(index, index+=1000));
				    	else
				    	{
				    		temp+=LZWFunctions.deCompressLZW(text);
				    		index=fileSize;
				    	}
				    	temp2+=temp;
				    	}
				    	catch(Exception e)
				    	{
				    		JOptionPane.showMessageDialog(null,e.getLocalizedMessage());
				    	}
				    }
				    txtrTypeYourText.setText(temp2);
				    txtrTypeYourText.setEnabled(true);
				    btnDecompress.setEnabled(false);
				    btnNewButton_2.setEnabled(true);
				    JOptionPane.showMessageDialog(null,"test has been DeCompressed");
				}
			}
		});
		btnDecompress.setEnabled(false);
		btnDecompress.setBounds(280, 246, 119, 23);
		panel.add(btnDecompress);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(382, 11, 17, 48);
		panel.add(scrollBar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		tabbedPane.addTab("Compress File", null, panel_1, null);
		panel_1.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(textPane_1.getText().toString()!="")
				{
					button.setEnabled(true);
					button_1.setEnabled(true);
				}
				if(textPane.getText()=="")
				{
					button.setEnabled(false);
					button_1.setEnabled(false);
				}
			}
		});
		textPane.setBackground(new Color(255, 248, 220));
		textPane.setBounds(10, 65, 261, 32);
		panel_1.add(textPane);
		JButton btnChooseFileTo = new JButton("Choose File");
		btnChooseFileTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc=new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filter=new FileNameExtensionFilter("txt files only","txt"); 
				fc.setFileFilter(filter);
				int response=fc.showOpenDialog(HufmanForm.this);
				if(response==JFileChooser.APPROVE_OPTION)
					textPane.setText(fc.getSelectedFile().toString());
					
			}
		});
		btnChooseFileTo.setBounds(281, 65, 118, 32);
		panel_1.add(btnChooseFileTo);
		
		JLabel lblFileToCompress = new JLabel("File To Compress :");
		lblFileToCompress.setBounds(10, 31, 157, 23);
		panel_1.add(lblFileToCompress);
		
		textPane_1 = new JTextPane();
		textPane_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(textPane.getText()!="")
				{
					button.setEnabled(true);
					button_1.setEnabled(true);
				}
			}
		});
		textPane_1.setBackground(new Color(255, 248, 220));
		textPane_1.setBounds(10, 171, 261, 32);
		panel_1.add(textPane_1);
		
		JButton btnChooseDirectory = new JButton("Choose Directory");
		btnChooseDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc=new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int response=fc.showOpenDialog(HufmanForm.this);
				if(response==JFileChooser.APPROVE_OPTION)
					textPane_1.setText(fc.getSelectedFile().toString());
				}
		});
		btnChooseDirectory.setBounds(281, 171, 118, 32);
		panel_1.add(btnChooseDirectory);
		
		JLabel lblPathToSave = new JLabel("Path To Save :");
		lblPathToSave.setBounds(10, 137, 157, 23);
		panel_1.add(lblPathToSave);
		
		button = new JButton("Compress");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(new File(textPane.getText()).isFile())
				{
					 LZWFunctions.compressFile(textPane.getText(),textPane_1.getText());
					 JOptionPane.showMessageDialog(null,"File has been Compressed");
				}
				else
					JOptionPane.showMessageDialog(null,textPane.getText()+" it's not a file");
				
			}
		});
		button.setEnabled(false);
		button.setBounds(75, 246, 94, 23);
		panel_1.add(button);
		
		button_1 = new JButton("Decompress");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(new File(textPane.getText()).isFile())
				{
					 LZWFunctions.deCompressFile(textPane.getText(),textPane_1.getText());
					 JOptionPane.showMessageDialog(null,"File has been DeCompressed");
				}
				else
					JOptionPane.showMessageDialog(null,textPane.getText()+" it's not a file");
			}
		});
		button_1.setEnabled(false);
		button_1.setBounds(179, 246, 119, 23);
		panel_1.add(button_1);
	}
}
