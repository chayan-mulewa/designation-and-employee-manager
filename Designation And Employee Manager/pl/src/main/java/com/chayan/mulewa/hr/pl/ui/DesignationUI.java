package com.chayan.mulewa.hr.pl.ui;
import com.chayan.mulewa.hr.bl.exceptions.*;
import com.chayan.mulewa.hr.bl.interfaces.manager.*;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
import com.chayan.mulewa.hr.bl.pojo.*;
import com.chayan.mulewa.hr.bl.manager.*;
import com.chayan.mulewa.hr.pl.model.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.table.*;
public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener
{
    private JTable table;
    private JTableHeader tableHeader;
    private DesignationModel designationModel;
    private JScrollPane scrollPane;
    private JLabel designationLabel,searchLabel,errorMessage;
    private JTextField searchTextField;
    private JButton searchButton,addEmployee;
    private InnerPanel innerPanel;
    private enum MODEL {VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
    private MODEL model;
    private ImageIcon logo;
    public DesignationUI()
    {
        initComponents();
        setAppearance();
        addListeners();
        setViewMode();
        innerPanel.setViewMode();
    }
    private void initComponents()
    {
        logo=new ImageIcon(this.getClass().getResource("/icons/logo.png"));
        designationLabel=new JLabel("Designation");
        designationLabel.setFont(new Font("Verdana", Font.BOLD,20));
        searchLabel=new JLabel("Search");
        searchLabel.setFont(new Font("Verdana", Font.BOLD,15));
        errorMessage=new JLabel("");
        errorMessage.setForeground(Color.red);
        errorMessage.setFont(new Font("Verdana", Font.BOLD,10));
        searchTextField=new JTextField(20);
        searchButton=new JButton("GO");
        addEmployee=new JButton();
        designationModel=new DesignationModel();
        table=new JTable(designationModel);
        tableHeader=table.getTableHeader();
        tableHeader.setFont(new Font("Verdana", Font.BOLD,10));
        scrollPane=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        innerPanel=new InnerPanel();
    }
    private void setAppearance()
    {
        int leftMargin,topMargin;
        leftMargin=0;
        topMargin=0;
        designationLabel.setBounds(leftMargin+180,topMargin+10,200,40);
        searchLabel.setBounds(leftMargin+10,topMargin+60,200,40);
        errorMessage.setBounds(leftMargin+340,topMargin+40,200,40);
        searchTextField.setBounds(leftMargin+100,topMargin+70,300,20);
        searchButton.setBounds(leftMargin+410, topMargin+70,40,18);
        addEmployee.setBounds(leftMargin+410, topMargin+10,40,40);
        scrollPane.setBounds(leftMargin+10,topMargin+100,465,200);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.setRowSelectionAllowed(true);    
        tableHeader.setResizingAllowed(false);
        tableHeader.setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        innerPanel.setBounds(leftMargin+10,topMargin+310,465,242);
        int width,height;
        width=500;
        height=600;
        Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(width,height);
        setLocation((dimension.width/2)-(width/2),(dimension.height/2)-(height/2));
        setIconImage(logo.getImage());
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        if(table.getRowCount()==0)
        {
            searchTextField.setEnabled(false);
            searchButton.setEnabled(false);
        }
        add(designationLabel);
        add(searchLabel);
        add(addEmployee);
        add(errorMessage);
        add(searchTextField);
        add(searchButton);
        add(scrollPane);
        add(innerPanel);
    }
    private void addListeners()
    {
        searchTextField.getDocument().addDocumentListener(this);
        searchButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                searchTextField.setText("");
                searchTextField.requestFocus();
            }
        });
        table.getSelectionModel().addListSelectionListener(this);
        addEmployee.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                EmployeeUI employeeUI=new EmployeeUI();
                employeeUI.setVisible(true);
                setVisible(false);                     
            }
        });
    }
    public void searchDesignation()
    {
        errorMessage.setText("");
        String searchTitle=searchTextField.getText().trim();
        if(searchTitle.length()==0)
        {
            return;
        }
        int rowIndex;
        try
        {
            rowIndex=designationModel.indexOfTitle(searchTitle,true);
        }catch(BLException blException)
        {
            errorMessage.setText("Not Found");
            return;
        }
        table.setRowSelectionInterval(rowIndex, rowIndex);
        Rectangle rectangle=table.getCellRect(rowIndex,0,true);
        table.scrollRectToVisible(rectangle);
    }
    public void insertUpdate(DocumentEvent e)
    {
        searchDesignation();
    }
    public void removeUpdate(DocumentEvent e)
    {
        searchDesignation();
    }
    public void changedUpdate(DocumentEvent e)
    {
        searchDesignation();
    }
    public void valueChanged(ListSelectionEvent e)
    {
        int indexSelectionOfTable=table.getSelectedRow();
        try
        {
            DesignationInterface designation=designationModel.getDesignationAt(indexSelectionOfTable);
            innerPanel.setDesignation(designation);
        } catch (BLException blException)
        {
            innerPanel.clearDesignation();
        }
    }
    public void setViewMode()
    {
        this.model=MODEL.VIEW;
        if(table.getRowCount()>0)
        {

            searchTextField.setEnabled(true);
            searchButton.setEnabled(true);
            addEmployee.setEnabled(true);
            table.setEnabled(true);
        }
        else
        {
            searchTextField.setEnabled(false);
            searchButton.setEnabled(false);
            addEmployee.setEnabled(false);
            table.setEnabled(false);
        }
    }
    public void setAddMode()
    {
        this.model=MODEL.ADD;
        searchTextField.setEnabled(false);
        searchButton.setEnabled(false);
        table.setEnabled(false);
    }
    public void setEditMode()
    {
        this.model=MODEL.EDIT;
        searchTextField.setEnabled(false);
        searchButton.setEnabled(false);
        table.setEnabled(true);
    }
    public void setDeleteMode()
    {
        this.model=MODEL.DELETE;
        searchTextField.setEnabled(false);
        searchButton.setEnabled(false);
        table.setEnabled(false);
    }
    public void setExportToPDFMode()
    {
        this.model=MODEL.EXPORT_TO_PDF;
        searchTextField.setEnabled(false);
        searchButton.setEnabled(false);
        table.setEnabled(false);
    }
    class InnerPanel extends JPanel
    {
        private JLabel panelDesignation,displaySeletedTableRow,designationTitleLabel,designationCodeLabel;
        private JTextField designationTitleField,designationCodeField;
        private JButton panelAddButton,panelEditButton,panelDeleteButton,panelCancleButton,panelPDFButton;
        private DesignationInterface designation;
        private ImageIcon add,update,delete,edit,save,pdf,cancle;
        InnerPanel()
        {
            initComponents();
            setAppearance();
            addListeners();
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        public void initComponents()
        {

            panelDesignation=new JLabel("Designation Editor");
            panelDesignation.setFont(new Font("Verdana", Font.BOLD,20));
            displaySeletedTableRow=new JLabel("");
            displaySeletedTableRow.setFont(new Font("Verdana", Font.BOLD,15));
            designationTitleLabel=new JLabel("Title");
            designationTitleLabel.setVisible(false);
            designationTitleField=new JTextField(20);
            designationTitleField.setVisible(false);
            designationCodeLabel=new JLabel("Code");
            designationCodeLabel.setVisible(false);
            designationCodeField=new JTextField(20);
            designationCodeField.setVisible(false);
            add=new ImageIcon(this.getClass().getResource("/icons/add.png"));
            save=new ImageIcon(this.getClass().getResource("/icons/save.png"));
            edit=new ImageIcon(this.getClass().getResource("/icons/edit.png"));
            update=new ImageIcon(this.getClass().getResource("/icons/update.png"));
            delete=new ImageIcon(this.getClass().getResource("/icons/delete.png"));
            cancle=new ImageIcon(this.getClass().getResource("/icons/cancle.png"));
            pdf=new ImageIcon(this.getClass().getResource("/icons/pdf.png"));
            panelAddButton=new JButton(add);
            panelEditButton=new JButton(edit);
            panelDeleteButton=new JButton(delete);
            panelCancleButton=new JButton(cancle);
            panelPDFButton=new JButton(pdf);
        }
        public void setAppearance()
        {
            int leftMargin,topMargin;
            leftMargin=0;
            topMargin=0;
            setLayout(null);
            panelDesignation.setBounds(leftMargin+130,topMargin+10,210,40);
            displaySeletedTableRow.setBounds(leftMargin+132,topMargin+50,150,50);
            
            designationTitleField.setBounds(leftMargin+132,topMargin+140,200,20);
            designationCodeField.setBounds(leftMargin+132,topMargin+110,200,20);

            designationTitleLabel.setBounds(leftMargin+80,topMargin+140,200,20);
            designationCodeLabel.setBounds(leftMargin+80,topMargin+110,200,20);

            panelAddButton.setBounds(leftMargin+10,topMargin+183,82,50);
            panelEditButton.setBounds(leftMargin+102,topMargin+183,82,50);
            panelDeleteButton.setBounds(leftMargin+192,topMargin+183,82,50);
            panelCancleButton.setBounds(leftMargin+282,topMargin+183,82,50);
            panelPDFButton.setBounds(leftMargin+372,topMargin+183,82,50);
            add(panelDesignation);
            add(displaySeletedTableRow);
            add(designationTitleField);
            add(designationCodeField);
            add(designationTitleLabel);
            add(designationCodeLabel);
            add(panelAddButton);
            add(panelEditButton);
            add(panelDeleteButton);
            add(panelCancleButton);
            add(panelPDFButton);
        }
        private void setViewMode()
        {
            
            DesignationUI.this.setViewMode();
            displaySeletedTableRow.setVisible(true);
            designationTitleField.setVisible(false);
            designationCodeField.setVisible(false);

            designationTitleLabel.setVisible(false);
            designationCodeLabel.setVisible(false);

            panelAddButton.setEnabled(true);
            panelCancleButton.setEnabled(true);
            if(designationModel.getRowCount()>0)
            {

                panelCancleButton.setEnabled(false);
                panelEditButton.setEnabled(true);
                panelDeleteButton.setEnabled(true);
                panelPDFButton.setEnabled(true);
            }
            else
            {
                panelEditButton.setEnabled(false);
                panelDeleteButton.setEnabled(false);
                panelPDFButton.setEnabled(false);
            }
        }
        private void setAddMode()
        {
            DesignationUI.this.setAddMode();
            displaySeletedTableRow.setText("");
            displaySeletedTableRow.setVisible(false);
            designationTitleLabel.setVisible(true);
            designationCodeLabel.setVisible(true);
            designationTitleField.setText("");
            designationTitleField.setVisible(true);
            designationCodeField.setVisible(true);
            designationCodeField.setEnabled(false);
            designationCodeField.setText("");
            panelAddButton.setEnabled(true);
            panelAddButton.setIcon(save);
            panelEditButton.setEnabled(false);
            panelDeleteButton.setEnabled(false);
            panelCancleButton.setEnabled(true);
            panelPDFButton.setEnabled(false);
        }
        private void setEditMode()
        {
            if(table.getSelectedRow()<0 || table.getSelectedRow()>=designationModel.getRowCount())
            {
                JOptionPane.showMessageDialog(this,"Selete Designation To Edit");
                return;
            }
            DesignationUI.this.setEditMode();
            displaySeletedTableRow.setText("");
            displaySeletedTableRow.setVisible(false);
            designationTitleLabel.setVisible(true);
            designationCodeLabel.setVisible(true);
            designationTitleField.setText(designation.getTitle());
            designationTitleField.setVisible(true);
            designationCodeField.requestFocus();
            designationCodeField.setVisible(true);
            designationCodeField.setText(String.valueOf(designation.getCode()));
            designationCodeField.setEnabled(false);
            panelAddButton.setEnabled(false);
            panelEditButton.setEnabled(true);
            panelEditButton.setIcon(update);
            panelDeleteButton.setEnabled(false);
            panelCancleButton.setEnabled(true);
            panelPDFButton.setEnabled(false);
        }
        private void setDeleteMode()
        {
            if(table.getSelectedRow()<0 || table.getSelectedRow()>=designationModel.getRowCount())
            {
                JOptionPane.showMessageDialog(this,"Selete Designation To Delete");
                return;
            }
            DesignationUI.this.setDeleteMode();
            panelAddButton.setEnabled(false);
            panelEditButton.setEnabled(false);
            panelDeleteButton.setEnabled(false);
            panelCancleButton.setEnabled(false);
            panelPDFButton.setEnabled(false);
            deleteDesignation();
            DesignationUI.this.setViewMode();
            setViewMode();
        }
        public void addListeners()
        {
            panelAddButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev)
                {
                    if(model==MODEL.VIEW)
                    {
                        setAddMode();
                    }
                    else
                    {
                        if(addDesignation())
                        {
                        setViewMode();
                        }
                    }
                }
            });
            panelEditButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev)
                {
                    if(model==MODEL.VIEW)
                    {
                        setEditMode();
                    }
                    else
                    {
                        if(updateDesigantion())
                        {
                        setViewMode();
                        }
                    }

                }
            });
            panelDeleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ev)
                {
                    setDeleteMode();
                }
            });
            panelCancleButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev)
                {
                    panelAddButton.setIcon(add);
                    panelEditButton.setIcon(edit);
                    setViewMode();
                }
            });
            panelPDFButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ev)
                {
                    FileNameExtensionFilter fileNameExtensionFilter=new FileNameExtensionFilter("PDF File","pdf");
                    JFileChooser fileChooser=new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    fileChooser.addChoosableFileFilter(fileNameExtensionFilter);
                    int selectedOption=fileChooser.showSaveDialog(InnerPanel.this);
                    if(selectedOption==JFileChooser.APPROVE_OPTION)
                    {
                        try
                        {
                            File seletedFile=fileChooser.getSelectedFile();
                            String path=seletedFile.getAbsolutePath();
                            if(path.endsWith("."))
                            {
                                path=path+"pdf";
                            }
                            else
                            {
                                if(path.endsWith(".pdf")==false)
                                {
                                    path=path+".pdf";
                                }
                            }
                            File file=new File(path);
                            File parant=new File(file.getParent());
                            if(parant.exists()==false)
                            {
                                JOptionPane.showMessageDialog(InnerPanel.this,"Invalid Path 1 "+file.getAbsolutePath());
                                return;
                            }
                            designationModel.exortTOPDF(file);
                        }catch(BLException blException)
                        {
                            if(blException.hasGenericException())
                            {
                                JOptionPane.showMessageDialog(InnerPanel.this,"Exception From BL Layer : "+blException.getGenericException());
                            }
                        }
                        catch(Exception e)
                        {
                            System.out.println(e);
                        }
                    }
                }
            });
        }
        private boolean addDesignation()
        {
            panelAddButton.setIcon(add);
            String title=designationTitleField.getText();
            if(title.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Designation Required");
                return false;
            }
            DesignationInterface addDesignation=new Designation();
            addDesignation.setTitle(title);
            try
            {
                designationModel.add(addDesignation);
                int rowIndex=0;
                try
                {
                    rowIndex=designationModel.indexOfDesignation(addDesignation);
                }catch(BLException blException)
                {
                    // do nothing
                }
                table.setRowSelectionInterval(rowIndex, rowIndex);
                Rectangle rectangle=table.getCellRect(rowIndex,0,true);
                table.scrollRectToVisible(rectangle);
                return true;
            }catch(BLException blException)
            {
                if(blException.hasGenericException())
                {
                    JOptionPane.showMessageDialog(this,blException.getGenericException());
                }
                else
                {
                    if(blException.hasException("title"))
                    {
                        JOptionPane.showMessageDialog(this,blException.getException("title"));
                    }
                }
                return false;
            }
        }
        private  boolean updateDesigantion()
        {
            panelEditButton.setIcon(edit);
            String title=designationTitleField.getText();
            if(title.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Designation Required");
                return false;
            }
            DesignationInterface d=new Designation();
            d.setTitle(title);
            d.setCode(designation.getCode());
            try
            {
                designationModel.update(d);
                int rowIndex=0;
                try
                {
                    rowIndex=designationModel.indexOfDesignation(designation);
                }catch(BLException blException)
                {
                    return false;
                }
                table.setRowSelectionInterval(rowIndex, rowIndex);
                Rectangle rectangle=table.getCellRect(rowIndex,0,true);
                table.scrollRectToVisible(rectangle);
                return true;
            }catch(BLException blException)
            {
                if(blException.hasGenericException())
                {
                    JOptionPane.showMessageDialog(this,blException.getGenericException());
                }
                else
                {
                    if(blException.hasException("title"))
                    {
                        JOptionPane.showMessageDialog(this,blException.getException("title"));
                    }
                }
                return false;
            }
        }
        private void deleteDesignation()
        {
            try
            {
                String title=this.designation.getTitle();
                int selectedOption=JOptionPane.showConfirmDialog(this,"Do You Want To Delete "+"' "+title+" '"+" ?","Confimaton",JOptionPane.YES_NO_OPTION);
                if(selectedOption==JOptionPane.NO_OPTION || selectedOption==JOptionPane.CLOSED_OPTION)
                {
                    return;
                }
                designationModel.remove(this.designation.getCode());
                JOptionPane.showMessageDialog(this,title+" Deleted");
            }catch (BLException blException){
                if(blException.hasGenericException())
                {
                    JOptionPane.showMessageDialog(this,blException.getGenericException());
                }
                else
                {
                    if(blException.hasException("title"))
                    {
                        JOptionPane.showMessageDialog(this,blException.getException("title"));
                    }
                }
            }
        }
        public void setDesignation(DesignationInterface designation)
        {
            this.designation=designation;
            displaySeletedTableRow.setText(this.designation.getTitle());
        }
        public void clearDesignation()
        {
            this.designation=null;
            displaySeletedTableRow.setText("");
        }
    }
}