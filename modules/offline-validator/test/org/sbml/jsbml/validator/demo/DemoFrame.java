package org.sbml.jsbml.validator.demo;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.function.Consumer;

import javax.swing.*;

import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLErrorLog;
import org.sbml.jsbml.SBMLReader;

/**
 * 
 * @author Roman Schulte
 * @version $Rev: 2378 $
 * @since 1.0
 * @date May 9, 2016
 */
public class DemoFrame extends JFrame implements ActionListener
{
  private static final long serialVersionUID = -9006869340783760240L;
  
  private JTextArea onlineTextArea_;
  private JTextArea offlineTextArea_;
  private SBMLDocument document_;
  
  private JButton openButton;
  private JButton validateButton;


  /**
   * Creates the default {@link JFrame} for the demo application.
   * The returned frame is not visible and located relative to {@code null}. Default
   * close operation is {@code EXIT_ON_CLOSE}.
   * @param width
   * @param height
   * @return frame
   */
  public DemoFrame(int width, int height)
  {
    // Setup Frame
    super("JSBML Validation Demo");
    
    this.offlineTextArea_ = new JTextArea();
    offlineTextArea_.setLineWrap(true);
    this.onlineTextArea_  = new JTextArea();
    onlineTextArea_.setLineWrap(true);
    
    this.setSize(width, height);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    Container container = this.getContentPane();
    container.setLayout(new BorderLayout(0, 0));
    
    // Wrapper panel for the panels at the top of the frame
    JPanel topPanel = new JPanel();
//    topPanel.setPreferredSize(new Dimension(100, 60));
    topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
    container.add(topPanel, BorderLayout.PAGE_START);
    
    openButton = new JButton("Choose File");
//    openButton.setPreferredSize(new Dimension(180, 40));
    openButton.addActionListener(this);
    topPanel.add(openButton);
    
    validateButton = new JButton("Validate");
//    validateButton.setPreferredSize(new Dimension(180, 40));
    validateButton.addActionListener(this);
    topPanel.add(validateButton);
    
    
    // Wrapper for the both text fields
    JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));
    centerPanel.add(labelTextArea(offlineTextArea_, "Offline"));
    centerPanel.add(labelTextArea(onlineTextArea_, "Online"));
    container.add(centerPanel, BorderLayout.CENTER);
   
  }
  
  
  private JPanel labelTextArea(JTextArea textArea, String name)
  {
    JPanel wrapperPanel = new JPanel(new BorderLayout(0, 10));
    JLabel label        = new JLabel(name);
    
    label.setHorizontalAlignment(SwingConstants.CENTER);
    wrapperPanel.add(label, BorderLayout.PAGE_START);
    wrapperPanel.add(textArea, BorderLayout.CENTER);
    
    
    return wrapperPanel;
  }
  
  
  
  private void chooseFile()
  {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(this);
    
    if(result == JFileChooser.APPROVE_OPTION)
    {
      try
      {
        File choosenFile = fileChooser.getSelectedFile();
        this.document_ = SBMLReader.read(choosenFile);
      }
      catch(Exception exception)
      {
        System.out.println("ERROR while parsing file. Must be a SBML file");
        exception.printStackTrace();
      }
     
    }
  }
  
  
  
  private void validateDocument()
  {
    if(this.document_ != null)
    {
      onlineTextArea_.setText("Checking...");
      this.document_.checkConsistency();
      SBMLErrorLog errorLog = this.document_.getListOfErrors();
      
      this.printLogInTextArea(errorLog, onlineTextArea_);
   
    }
  }
  
  private void printLogInTextArea(SBMLErrorLog log, final JTextArea textArea)
  {
	  	textArea.setText("Result: \n");
	  
//	  
      log.getValidationErrors().forEach(new Consumer<SBMLError>() {

		@Override
		public void accept(SBMLError error) {
			// TODO Auto-generated method stub
			String message = error.getMessage();
			textArea.append(message + "\n \n");
		}
    	  
	});
  }
  
  
  @Override
  public void actionPerformed(ActionEvent e) 
 	{
	  // TODO Auto-generated method stub
	  if(e.getSource().equals(this.openButton))
	  {
		  this.chooseFile();
	  }
	  else
	  {
		  this.validate();
	  }
	}

  
  
  /**
   * @return the document property (or null)
   */
  public SBMLDocument getDocument() {
    return document_;
  }

  
  
  /**
   * Sets the private document property.
   * @param doc the doc to set
   */
  public void setDocument(SBMLDocument document) {
    this.document_ = document;
  }

}
