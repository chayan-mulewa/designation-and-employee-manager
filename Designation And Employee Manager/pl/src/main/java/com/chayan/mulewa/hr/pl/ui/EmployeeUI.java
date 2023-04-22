package com.chayan.mulewa.hr.pl.ui;
import com.chayan.mulewa.enums.*;
import com.chayan.mulewa.hr.bl.exceptions.*;
import com.chayan.mulewa.hr.bl.interfaces.manager.*;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
import com.chayan.mulewa.hr.bl.pojo.*;
import com.chayan.mulewa.hr.bl.manager.*;
import com.chayan.mulewa.hr.pl.model.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import javax.swing.table.*;
public class EmployeeUI extends JFrame implements DocumentListener,ListSelectionListener
{
    private JTable table;
    private JTableHeader tableHeader;
    private EmployeeModel employeeModel;
    private JScrollPane scrollPane;
    private JLabel employeeLabel,searchLabel,errorMessage;
    private JTextField searchTextField;
    private JButton searchButton,addDesignati;
    private InnerPanel innerPanel;
    private enum MODEL {VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
    private MODEL model;
    private ImageIcon logo;
    public EmployeeUI()
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
        employeeLabel=new JLabel("Employee");
        employeeLabel.setFont(new Font("Verdana", Font.BOLD,20));
        searchLabel=new JLabel("Search");
        searchLabel.setFont(new Font("Verdana", Font.BOLD,15));
        errorMessage=new JLabel("");
        errorMessage.setForeground(Color.red);
        errorMessage.setFont(new Font("Verdana", Font.BOLD,10));
        searchTextField=new JTextField(20);
        searchButton=new JButton("GO");
        addDesignati=new JButton();
        employeeModel=new EmployeeModel();
        table=new JTable(employeeModel);
        tableHeader=table.getTableHeader();
        tableHeader.setFont(new Font("Verdana", Font.BOLD,10));
        scrollPane=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        innerPanel=new InnerPanel();
    }
    private void setAppearance()
    {
        int leftMargin,topMargin;
        leftMargin=0;
        topMargin=0;
        employeeLabel.setBounds(leftMargin+180,topMargin+10,200,40);
        searchLabel.setBounds(leftMargin+10,topMargin+60,200,40);
        errorMessage.setBounds(leftMargin+340,topMargin+40,200,40);
        searchTextField.setBounds(leftMargin+100,topMargin+70,300,20);
        searchButton.setBounds(leftMargin+410, topMargin+70,40,18);
        addDesignati.setBounds(leftMargin+410, topMargin+10,40,40);
        scrollPane.setBounds(leftMargin+10,topMargin+100,465,200);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(150);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);
        table.getColumnModel().getColumn(7).setPreferredWidth(150);
        table.getColumnModel().getColumn(8).setPreferredWidth(150);
        table.getColumnModel().getColumn(9).setPreferredWidth(150);
        table.setRowSelectionAllowed(true);    
        tableHeader.setResizingAllowed(false);
        tableHeader.setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        innerPanel.setBounds(leftMargin+10,topMargin+310,465,442);
        int width,height;
        width=500;
        height=800;
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
        add(employeeLabel);
        add(searchLabel);
        add(errorMessage);
        add(searchTextField);
        add(searchButton);
        add(addDesignati);
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
        addDesignati.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            }
        });
    }
    public void searchEmployee()
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
            rowIndex=employeeModel.indexOfTitle(searchTitle,true);
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
        searchEmployee();
    }
    public void removeUpdate(DocumentEvent e)
    {
        searchEmployee();
    }
    public void changedUpdate(DocumentEvent e)
    {
        searchEmployee();
    }
    public void valueChanged(ListSelectionEvent e)
    {
        int indexSelectionOfTable=table.getSelectedRow();
        try
        {
            EmployeeInterface employee=employeeModel.getEmployeeAt(indexSelectionOfTable);
            innerPanel.setEmployee(employee);
        } catch (BLException blException)
        {
            innerPanel.clearEmployee();
        }
    }
    public void setViewMode()
    {
        this.model=MODEL.VIEW;
        if(table.getRowCount()>0)
        {
        searchTextField.setEnabled(true);
        searchButton.setEnabled(true);
        addDesignati.setEnabled(true);
        table.setEnabled(true);
        }
        else
        {
            addDesignati.setEnabled(false);
            searchTextField.setEnabled(false);
            searchButton.setEnabled(false);
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
        private JLabel panelEmployee,displaySeletedTableRow,employeeIDLable,employeeNameLable,employeeDesignationCodeLable,employeeDateOFBirthLable,employeeGenderLable,employeeIsIndianLable,employeeBasicSalaryLable,employeePanNumberLable,employeeAadharNumberLable;
        private JTextField employeeIDField,employeeNameField,employeeDesignationCodeField,employeeDateOFBirthField,employeeGenderField,employeeIsIndianField,employeeBasicSalaryField,employeePanNumberField,employeeAadharNumberField;
        private JButton panelAddButton,panelEditButton,panelDeleteButton,panelCancleButton,panelPDFButton;
        private EmployeeInterface employee;
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

            panelEmployee=new JLabel("Employee Editor");
            panelEmployee.setFont(new Font("Verdana", Font.BOLD,20));
            displaySeletedTableRow=new JLabel("");
            displaySeletedTableRow.setFont(new Font("Verdana", Font.BOLD,15));

            employeeIDLable=new JLabel("ID");
            employeeIDLable.setVisible(false);
            employeeIDField=new JTextField(20);
            employeeIDField.setVisible(false);

            employeeNameLable=new JLabel("Name");
            employeeNameLable.setVisible(false);
            employeeNameField=new JTextField(20);
            employeeNameField.setVisible(false);

            employeeDesignationCodeLable=new JLabel("Designation Code");
            employeeDesignationCodeLable.setVisible(false);
            employeeDesignationCodeField=new JTextField(20);
            employeeDesignationCodeField.setVisible(false);

            employeeDateOFBirthLable=new JLabel("Date of Birth");
            employeeDateOFBirthLable.setVisible(false);
            employeeDateOFBirthField=new JTextField(20);
            employeeDateOFBirthField.setVisible(false);

            employeeGenderLable=new JLabel("Gender");
            employeeGenderLable.setVisible(false);
            employeeGenderField=new JTextField(20);
            employeeGenderField.setVisible(false);

            employeeIsIndianLable=new JLabel("Indian");
            employeeIsIndianLable.setVisible(false);
            employeeIsIndianField=new JTextField(20);
            employeeIsIndianField.setVisible(false);

            employeeBasicSalaryLable=new JLabel("Salary");
            employeeBasicSalaryLable.setVisible(false);
            employeeBasicSalaryField=new JTextField(20);
            employeeBasicSalaryField.setVisible(false);

            employeePanNumberLable=new JLabel("Pan Number");
            employeePanNumberLable.setVisible(false);
            employeePanNumberField=new JTextField(20);
            employeePanNumberField.setVisible(false);

            employeeAadharNumberLable=new JLabel("Aadhar Number");
            employeeAadharNumberLable.setVisible(false);
            employeeAadharNumberField=new JTextField(20);
            employeeAadharNumberField.setVisible(false);

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
            panelEmployee.setBounds(leftMargin+130,topMargin+10,210,40);
            displaySeletedTableRow.setBounds(leftMargin+132,topMargin+50,150,50);
           
            employeeIDLable.setBounds(leftMargin+30,topMargin+110,200,20);
            employeeNameLable.setBounds(leftMargin+30,topMargin+140,200,20);
            employeeDesignationCodeLable.setBounds(leftMargin+30,topMargin+170,200,20);
            employeeDateOFBirthLable.setBounds(leftMargin+30,topMargin+200,200,20);
            employeeGenderLable.setBounds(leftMargin+30,topMargin+230,200,20);
            employeeIsIndianLable.setBounds(leftMargin+30,topMargin+260,200,20);
            employeeBasicSalaryLable.setBounds(leftMargin+30,topMargin+290,200,20);
            employeePanNumberLable.setBounds(leftMargin+30,topMargin+320,200,20);
            employeeAadharNumberLable.setBounds(leftMargin+30,topMargin+350,200,20);

            employeeIDField.setBounds(leftMargin+132,topMargin+110,200,20);
            employeeNameField.setBounds(leftMargin+132,topMargin+140,200,20);
            employeeDesignationCodeField.setBounds(leftMargin+132,topMargin+170,200,20);
            employeeDateOFBirthField.setBounds(leftMargin+132,topMargin+200,200,20);
            employeeGenderField.setBounds(leftMargin+132,topMargin+230,200,20);
            employeeIsIndianField.setBounds(leftMargin+132,topMargin+260,200,20);
            employeeBasicSalaryField.setBounds(leftMargin+132,topMargin+290,200,20);
            employeePanNumberField.setBounds(leftMargin+132,topMargin+320,200,20);
            employeeAadharNumberField.setBounds(leftMargin+132,topMargin+350,200,20);

            panelAddButton.setBounds(leftMargin+10,topMargin+383,82,50);
            panelEditButton.setBounds(leftMargin+102,topMargin+383,82,50);
            panelDeleteButton.setBounds(leftMargin+192,topMargin+383,82,50);
            panelCancleButton.setBounds(leftMargin+282,topMargin+383,82,50);
            panelPDFButton.setBounds(leftMargin+372,topMargin+383,82,50);
            add(panelEmployee);
            add(displaySeletedTableRow);

            add(employeeIDLable);
            add(employeeIDField);

            add(employeeNameLable);
            add(employeeNameField);

            add(employeeDesignationCodeLable);
            add(employeeDesignationCodeField);

            add(employeeDateOFBirthLable);
            add(employeeDateOFBirthField);
            
            add(employeeGenderLable);
            add(employeeGenderField);

            add(employeeIsIndianLable);
            add(employeeIsIndianField);

            add(employeeBasicSalaryLable);
            add(employeeBasicSalaryField);

            add(employeePanNumberLable);
            add(employeePanNumberField);

            add(employeeAadharNumberLable);
            add(employeeAadharNumberField);

            add(panelAddButton);
            add(panelEditButton);
            add(panelDeleteButton);
            add(panelCancleButton);
            add(panelPDFButton);
        }
        private void setViewMode()
        {
            
            EmployeeUI.this.setViewMode();
            displaySeletedTableRow.setVisible(true);
            employeeIDLable.setVisible(false);
            employeeNameLable.setVisible(false);
            employeeDesignationCodeLable.setVisible(false);
            employeeDateOFBirthLable.setVisible(false);
            employeeGenderLable.setVisible(false);
            employeeIsIndianLable.setVisible(false);
            employeeBasicSalaryLable.setVisible(false);
            employeePanNumberLable.setVisible(false);
            employeeAadharNumberLable.setVisible(false);

            employeeIDField.setVisible(false);
            employeeNameField.setVisible(false);
            employeeDesignationCodeField.setVisible(false);
            employeeDateOFBirthField.setVisible(false);
            employeeGenderField.setVisible(false);
            employeeIsIndianField.setVisible(false);
            employeeBasicSalaryField.setVisible(false);
            employeePanNumberField.setVisible(false);
            employeeAadharNumberField.setVisible(false);

            panelAddButton.setEnabled(true);
            panelCancleButton.setEnabled(true);
            if(employeeModel.getRowCount()>0)
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
            EmployeeUI.this.setAddMode();
            displaySeletedTableRow.setText("");
            displaySeletedTableRow.setVisible(false);
            
            employeeIDLable.setVisible(true);
            employeeNameLable.setVisible(true);
            employeeDesignationCodeLable.setVisible(true);
            employeeDateOFBirthLable.setVisible(true);
            employeeGenderLable.setVisible(true);
            employeeIsIndianLable.setVisible(true);
            employeeBasicSalaryLable.setVisible(true);
            employeePanNumberLable.setVisible(true);
            employeeAadharNumberLable.setVisible(true);

            employeeIDField.setVisible(true);
            employeeIDField.setEnabled(false);
            employeeNameField.setVisible(true);
            employeeDesignationCodeField.setVisible(true);
            employeeDateOFBirthField.setVisible(true);
            employeeGenderField.setVisible(true);
            employeeIsIndianField.setVisible(true);
            employeeBasicSalaryField.setVisible(true);
            employeePanNumberField.setVisible(true);
            employeeAadharNumberField.setVisible(true);

            employeeIDField.setText("");
            employeeIDField.requestFocus();
            employeeNameField.setText("");
            employeeDesignationCodeField.setText("");
            employeeDateOFBirthField.setText("");
            employeeGenderField.setText("");
            employeeIsIndianField.setText("");
            employeeBasicSalaryField.setText("");
            employeePanNumberField.setText("");
            employeeAadharNumberField.setText("");

            panelAddButton.setEnabled(true);
            panelAddButton.setIcon(save);
            panelEditButton.setEnabled(false);
            panelDeleteButton.setEnabled(false);
            panelCancleButton.setEnabled(true);
            panelPDFButton.setEnabled(false);
        }
        private void setEditMode()
        {
            if(table.getSelectedRow()<0 || table.getSelectedRow()>=employeeModel.getRowCount())
            {
                JOptionPane.showMessageDialog(this,"Selete Employee To Edit");
                return;
            }
            EmployeeUI.this.setEditMode();
            displaySeletedTableRow.setText("");
            displaySeletedTableRow.setVisible(false);        

            employeeIDLable.setVisible(true);
            employeeNameLable.setVisible(true);
            employeeDesignationCodeLable.setVisible(true);
            employeeDateOFBirthLable.setVisible(true);
            employeeGenderLable.setVisible(true);
            employeeIsIndianLable.setVisible(true);
            employeeBasicSalaryLable.setVisible(true);
            employeePanNumberLable.setVisible(true);
            employeeAadharNumberLable.setVisible(true);

            employeeIDField.setVisible(true);
            employeeNameField.setVisible(true);
            employeeDesignationCodeField.setVisible(true);
            employeeDateOFBirthField.setVisible(true);
            employeeGenderField.setVisible(true);
            employeeIsIndianField.setVisible(true);
            employeeBasicSalaryField.setVisible(true);
            employeePanNumberField.setVisible(true);
            employeeAadharNumberField.setVisible(true);

            employeeIDField.setText(employee.getEmployeeID());
            employeeIDField.setEnabled(false);
            employeeNameField.setText(employee.getName());
            employeeDesignationCodeField.setText(String.valueOf(employee.getDesignation().getCode()));

            Date date=employee.getDateOfBirth();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String dateOfBirth=simpleDateFormat.format(date);
            employeeDateOFBirthField.setText(dateOfBirth);
            char gender=employee.getGender();
            employeeGenderField.setText(Character.toString(gender));
            String isIndian=Boolean.toString(employee.getIsIndian());
            employeeIsIndianField.setText(isIndian);
            employeeBasicSalaryField.setText(employee.getBasicSalary().toString());
            employeePanNumberField.setText(employee.getPanNumber());
            employeeAadharNumberField.setText(employee.getAadharNumber());

            panelAddButton.setEnabled(false);
            panelEditButton.setEnabled(true);
            panelEditButton.setIcon(update);
            panelDeleteButton.setEnabled(false);
            panelCancleButton.setEnabled(true);
            panelPDFButton.setEnabled(false);
        }
        private void setDeleteMode()
        {
            if(table.getSelectedRow()<0 || table.getSelectedRow()>=employeeModel.getRowCount())
            {
                JOptionPane.showMessageDialog(this,"Selete Employee To Delete");
                return;
            }
            EmployeeUI.this.setDeleteMode();
            panelAddButton.setEnabled(false);
            panelEditButton.setEnabled(false);
            panelDeleteButton.setEnabled(false);
            panelCancleButton.setEnabled(false);
            panelPDFButton.setEnabled(false);
            deleteEmployee();
            EmployeeUI.this.setViewMode();
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
                        if(addEmployee())
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
                        if(updateEmployee())
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
                            employeeModel.exortTOPDF(file);
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
        private boolean addEmployee()
        {
            panelAddButton.setIcon(add);
            String employeeName=employeeNameField.getText();
            String employeeDesignation=employeeDesignationCodeField.getText();
            String employeeGender=employeeGenderField.getText();
            String employeeDateOfBirth=employeeDateOFBirthField.getText();
            String employeeIsIndian=employeeIsIndianField.getText();
            String employeeSalary=employeeBasicSalaryField.getText();
            String employeePanNumber=employeePanNumberField.getText();
            String employeeAadharNumber=employeeAadharNumberField.getText();
           
            if(employeeName.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Employee Required");
                return false;
            }
            DesignationManagerInterface designationManager;
            try
            {
                designationManager = DesignationManager.getDesignationManager();
                if(employeeDesignation.length()==0)
                {
                    JOptionPane.showMessageDialog(this,"Designation Required");
                    return false;
                }else
                {
                    if(designationManager.designationCodeExists(Integer.parseInt(employeeDesignation))==false)
                    {
                        JOptionPane.showMessageDialog(this,"Designation Not Exists");
                        return false;
                    }
                }
            }catch (BLException e)
            {
                JOptionPane.showMessageDialog(this,"Designation Error");
            }
            if(employeeGender.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Gender Required");
                return false;
            }
            if(employeeDateOfBirth.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Date Of Birth Required");
                return false;
            }
            if(employeeIsIndian.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Indian Required");
                return false;
            }
            if(employeeSalary.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Salary Required");
                return false;
            }
            if(employeePanNumber.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Pan Number Required");
                return false;
            }
            if(employeeAadharNumber.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Aadhar Number Required");
                return false;
            }



            EmployeeInterface employee=new Employee();
            employee.setName(employeeName);
            DesignationInterface designation=new Designation();
            designation.setCode(Integer.parseInt(employeeDesignation));
            employee.setDesignation(designation);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            Date dateOfBirth=null;
            try
            {
                dateOfBirth=simpleDateFormat.parse(employeeDateOfBirth);
            }catch (ParseException parseException)
            {
                System.out.println(parseException.getMessage());
            }
            char gender=employeeGender.charAt(0);
            employee.setDateOfBirth(dateOfBirth);
            employee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
            employee.setIsIndian(Boolean.parseBoolean(employeeIsIndian));
            employee.setBasicSalary(new BigDecimal(employeeSalary));
            employee.setPanNumber(employeePanNumber);
            employee.setAadharNumber(employeeAadharNumber);
            try
            {
                employeeModel.add(employee);
                int rowIndex=0;
                try
                {
                    rowIndex=employeeModel.indexOfEmployee(employee);
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
                    if(blException.hasException("employeeName"))
                    {
                        JOptionPane.showMessageDialog(this,blException.getException("employeeName"));
                    }
                }
                return false;
            }
        }
        private  boolean updateEmployee()
        {
            panelEditButton.setIcon(edit);
            // if(title.length()==0)
            // {
            //     JOptionPane.showMessageDialog(this,"Employee Required");
            //     return false;
            // }
            String employeeID=employeeIDField.getText();
            String employeeName=employeeNameField.getText();
            String employeeDesignation=employeeDesignationCodeField.getText();
            String employeeGender=employeeGenderField.getText();
            String employeeDateOfBirth=employeeDateOFBirthField.getText();
            String employeeIsIndian=employeeIsIndianField.getText();
            String employeeSalary=employeeBasicSalaryField.getText();
            String employeePanNumber=employeePanNumberField.getText();
            String employeeAadharNumber=employeeAadharNumberField.getText();
            if(employeeName.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Employee Required");
                return false;
            }
            DesignationManagerInterface designationManager;
            try
            {
                designationManager = DesignationManager.getDesignationManager();
                if(employeeDesignation.length()==0)
                {
                    JOptionPane.showMessageDialog(this,"Designation Required");
                    return false;
                }else
                {
                    if(designationManager.designationCodeExists(Integer.parseInt(employeeDesignation))==false)
                    {
                        JOptionPane.showMessageDialog(this,"Designation Not Exists");
                        return false;
                    }
                }
            }catch (BLException e)
            {
                JOptionPane.showMessageDialog(this,"Designation Error");
            }
            if(employeeGender.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Gender Required");
                return false;
            }
            if(employeeDateOfBirth.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Date Of Birth Required");
                return false;
            }
            if(employeeIsIndian.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Indian Required");
                return false;
            }
            if(employeeSalary.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Salary Required");
                return false;
            }
            if(employeePanNumber.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Pan Number Required");
                return false;
            }
            if(employeeAadharNumber.length()==0)
            {
                JOptionPane.showMessageDialog(this,"Aadhar Number Required");
                return false;
            }
            EmployeeInterface employee=new Employee();
            employee.setEmployeeID(employeeID);
            employee.setName(employeeName);
            DesignationInterface designation=new Designation();
            designation.setCode(Integer.parseInt(employeeDesignation));
            employee.setDesignation(designation);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            Date dateOfBirth=null;
            try
            {
                dateOfBirth=simpleDateFormat.parse(employeeDateOfBirth);
            }catch (ParseException parseException)
            {
                System.out.println(parseException.getMessage());
            }
            char gender=employeeGender.charAt(0);
            employee.setDateOfBirth(dateOfBirth);
            employee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
            employee.setIsIndian(Boolean.parseBoolean(employeeIsIndian));
            employee.setBasicSalary(new BigDecimal(employeeSalary));
            employee.setPanNumber(employeePanNumber);
            employee.setAadharNumber(employeeAadharNumber);

            try
            {
                employeeModel.update(employee);
                int rowIndex=0;
                try
                {
                    rowIndex=employeeModel.indexOfEmployee(employee);
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
                    if(blException.hasException("employeeName"))
                    {
                        JOptionPane.showMessageDialog(this,blException.getException("employeeName"));
                    }
                }
                return false;
            }
        }
        private void deleteEmployee()
        {
            try
            {
                String employeeID=this.employee.getEmployeeID();
                String employeeName=this.employee.getName();
                int selectedOption=JOptionPane.showConfirmDialog(this,"Do You Want To Delete "+"' "+employeeName+" '"+" ?","Confimaton",JOptionPane.YES_NO_OPTION);
                if(selectedOption==JOptionPane.NO_OPTION || selectedOption==JOptionPane.CLOSED_OPTION)
                {
                    return;
                }
                employeeModel.remove(this.employee.getEmployeeID());
                JOptionPane.showMessageDialog(this,employeeID+" Deleted");
            }catch (BLException blException){
                if(blException.hasGenericException())
                {
                    JOptionPane.showMessageDialog(this,blException.getGenericException());
                }
                else
                {
                    if(blException.hasException("employeeName"))
                    {
                        JOptionPane.showMessageDialog(this,blException.getException("employeeName"));
                    }
                }
            }
        }
        public void setEmployee(EmployeeInterface employee)
        {
            this.employee=employee;
            displaySeletedTableRow.setText(this.employee.getName());
        }
        public void clearEmployee()
        {
            this.employee=null;
            displaySeletedTableRow.setText("");
        }
    }
}