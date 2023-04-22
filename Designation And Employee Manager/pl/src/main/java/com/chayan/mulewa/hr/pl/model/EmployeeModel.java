package com.chayan.mulewa.hr.pl.model;
import com.chayan.mulewa.hr.bl.exceptions.*;
import com.chayan.mulewa.hr.bl.interfaces.manager.*;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
import com.chayan.mulewa.hr.bl.pojo.*;
import com.chayan.mulewa.hr.bl.manager.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.borders.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.image.*;
import com.itextpdf.io.font.constants.*;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.table.AbstractTableModel;
public class EmployeeModel extends AbstractTableModel
{
    private List<EmployeeInterface> employees;
    private EmployeeManagerInterface employeeManager;
    private Set<EmployeeInterface> blEmployee;
    private String title[];
    public EmployeeModel()
    {
        populateDataStructure();
    }
    public void populateDataStructure()
    {
        this.title=new String[10];
        this.title[0]="S.NO";
        this.title[1]= "Employee ID";
        this.title[2]="Employee Name";
        this.title[3]="Designation Code";
        this.title[4]="Date Of Birth";
        this.title[5]="Gender";
        this.title[6]="Indian";
        this.title[7]="Salary";
        this.title[8]="Pan Number";
        this.title[9]="Aadhar Number";
        try
        {
            employeeManager=EmployeeManager.getEmployeeManager();
            blEmployee=employeeManager.getEmployee();
            this.employees=new LinkedList<>();
            for(EmployeeInterface plEmployee:blEmployee)
            {
                this.employees.add(plEmployee);
            }
        }catch(BLException blException)
        {
            // TODO: handle exception
        }
        Collections.sort(this.employees, new Comparator<EmployeeInterface>(){
            public int compare(EmployeeInterface left, EmployeeInterface right)
                {
                    return left.getName().toUpperCase().compareTo(right.getName().toUpperCase());
                }
            });
    }   
    public int getRowCount()
    {
        return this.employees.size();
    }
    public int getColumnCount()
    {
        return this.title.length;
    }
    public String getColumnName(int columnIndex)
    {
        return this.title[columnIndex];
    }
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        EmployeeInterface employee=employees.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex+1; // Data for column 1
            case 1:
                return employee.getEmployeeID(); // Data for column 2
            case 2:
                return employee.getName(); // Data for column 3
            case 3:
                return employee.getDesignation().getCode(); // Data for column 4
            case 4:
                return employee.getDateOfBirth(); // Data for column 5
            case 5:
                return employee.getGender(); // Data for column 6
            case 6:
                return employee.getIsIndian(); // Data for column 7
            case 7:
                return employee.getBasicSalary(); // Data for column 8
            case 8:
                return employee.getPanNumber(); // Data for column 9
            case 9:
                return employee.getAadharNumber(); // Data for column 10
            default:
                return null;
        }
        // if(columnIndex==0)
        // {
        //     return rowIndex+1;
        // }
        // return this.employees.get(rowIndex).getName();
    }
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }
    public Class getColumnClass(int columnIndex)
    {
        if(columnIndex==0)
        {
            return String.class;
        }
        return String.class;
    }
    public void add(EmployeeInterface employee) throws BLException
    {
        employeeManager.addEmployee(employee);
        this.employees.add(employee);
        Collections.sort(this.employees, new Comparator<EmployeeInterface>(){
            public int compare(EmployeeInterface left, EmployeeInterface right)
                {
                    return left.getName().toUpperCase().compareTo(right.getName().toUpperCase());
                }
            });
        fireTableDataChanged();    
    }
    public void update(EmployeeInterface employee) throws BLException
    {
        employeeManager.updateEmployee(employee);
        this.employees.remove(indexOfEmployee(employee));
        this.employees.add(employee);
        Collections.sort(this.employees, new Comparator<EmployeeInterface>(){
            public int compare(EmployeeInterface left, EmployeeInterface right)
                {
                    return left.getName().toUpperCase().compareTo(right.getName().toUpperCase());
                }
            });
        fireTableDataChanged();    
    }
    public void remove(String employeeID) throws BLException
    {
        employeeManager.removeEmployee(employeeID);
        int index=0;
        Iterator<EmployeeInterface> iterator=this.employees.iterator();
        while(iterator.hasNext())
        {
            if(iterator.next().getEmployeeID()==String.valueOf(employeeID))
            {
                break;
            }
            index++;
        }
        if(index==this.employees.size())
        {
            BLException blException=new BLException();
            blException.setGenericException("Invalid Employee : " +employeeID);
            throw blException;
        }
        this.employees.remove(index);
        fireTableDataChanged();    
    }
    public void exortTOPDF(File file) throws BLException
    {
        try
        {
            if(file.exists())
            {
                file.delete();
            }
            PdfWriter pdfWriter=new PdfWriter(file);
            PdfDocument pdfDocument=new PdfDocument(pdfWriter);
            Document document=new Document(pdfDocument);
            float headerColumnWigth[]={150,250,200};
            float dataColumnWigth[]={50,50,50,50,50,50,50,50,50,50};
           
            Table headerTable=null;
            Table dataTable=null;

            Paragraph headerPara1=new Paragraph("S.No");
            Paragraph headerPara2=new Paragraph("Employee ID");
            Paragraph headerPara3=new Paragraph("Employee Name");
            Paragraph headerPara4=new Paragraph("Designation Code");
            Paragraph headerPara5=new Paragraph("Date Of Birth");
            Paragraph headerPara6=new Paragraph("Gender");
            Paragraph headerPara7=new Paragraph("Indian");
            Paragraph headerPara8=new Paragraph("Salary");
            Paragraph headerPara9=new Paragraph("Pan Number");
            Paragraph headerPara10=new Paragraph("Aadhar Number");
           
            EmployeeInterface data;
            Paragraph dataPara;
            Paragraph serialNumberPara;

            Image image = new Image(ImageDataFactory.create(this.getClass().getResource("/icons/logo.png")));
            image.scaleToFit(100,100);
            Paragraph logoPara=new Paragraph();
            logoPara.add(image);
            Paragraph employeePara=new Paragraph("Chayan Company").setFontSize(20);
            Paragraph pageNumberPara;

            Paragraph softwarePara=new Paragraph("Software By Chayan Mulewa").setFontSize(15);

            Cell cell;

            int sno,pageSize,pageNumber,x;
            sno=0;
            pageSize=5;
            pageNumber=0;
            boolean newPage=true;
            x=0;

            while(x<this.employees.size())
            {
                if(newPage==true)
                {
                    pageNumber++;
                    headerTable=new Table(headerColumnWigth);
                    cell=new Cell();
                    cell.add(logoPara);
                    cell.setBorder(Border.NO_BORDER);
                    headerTable.addCell(cell);
                    cell=new Cell();
                    cell.add(employeePara);
                    cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    cell.setBorder(Border.NO_BORDER);
                    headerTable.addCell(cell);
                    cell=new Cell();
                    pageNumberPara=new Paragraph();
                    pageNumberPara.add("Page : "+String.valueOf(pageNumber));
                    cell.add(pageNumberPara);
                    cell.setTextAlignment(TextAlignment.RIGHT);
                    cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
                    cell.setBorder(Border.NO_BORDER);
                    headerTable.addCell(cell);
                    document.add(headerTable);
                    dataTable=new Table(dataColumnWigth);
                    cell=new Cell();
                    cell.add(headerPara1);
                    dataTable.addHeaderCell(cell);
                    cell=new Cell();
                    cell.add(headerPara2);
                    dataTable.addHeaderCell(cell);


                    cell=new Cell();
                    cell.add(headerPara3);
                    dataTable.addHeaderCell(cell);

                    cell=new Cell();
                    cell.add(headerPara4);
                    dataTable.addHeaderCell(cell);

                    cell=new Cell();
                    cell.add(headerPara5);
                    dataTable.addHeaderCell(cell);

                    cell=new Cell();
                    cell.add(headerPara6);
                    dataTable.addHeaderCell(cell);

                    cell=new Cell();
                    cell.add(headerPara7);
                    dataTable.addHeaderCell(cell);

                    cell=new Cell();
                    cell.add(headerPara8);
                    dataTable.addHeaderCell(cell);

                    cell=new Cell();
                    cell.add(headerPara9);
                    dataTable.addHeaderCell(cell);
                    
                    cell=new Cell();
                    cell.add(headerPara10);
                    dataTable.addHeaderCell(cell);


                    newPage=false;
                   
                }
                sno++;

                data=this.employees.get(x);
                serialNumberPara=new Paragraph();
                serialNumberPara.add(String.valueOf(sno));
                cell=new Cell();
                cell.add(serialNumberPara);
                dataTable.addCell(cell);

                dataPara=new Paragraph();
                dataPara.add(data.getEmployeeID());
                cell=new Cell();
                cell.add(dataPara);
                dataTable.addCell(cell);
                
                dataPara=new Paragraph();
                dataPara.add(data.getName());
                cell=new Cell();
                cell.add(dataPara);
                dataTable.addCell(cell);
                

                dataPara=new Paragraph();
                dataPara.add(String.valueOf(data.getDesignation().getCode()));
                cell=new Cell();
                cell.add(dataPara);
                dataTable.addCell(cell);
                

                // Date date=(Date)data.getDateOfBirth();
                // SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
                dataPara=new Paragraph();
                dataPara.add("Date");
                cell=new Cell();
                cell.add(dataPara);
                dataTable.addCell(cell);
                
                dataPara=new Paragraph();
                dataPara.add(String.valueOf(data.getGender()));
                cell=new Cell();
                cell.add(dataPara);
                dataTable.addCell(cell);
                
                dataPara=new Paragraph();
                dataPara.add(Boolean.toString(data.getIsIndian()));
                cell=new Cell();
                cell.add(dataPara);
                dataTable.addCell(cell);
                
                dataPara=new Paragraph();
                dataPara.add(data.getBasicSalary().toString());
                cell=new Cell();
                cell.add(dataPara);
                dataTable.addCell(cell);
                
                dataPara=new Paragraph();
                dataPara.add(data.getPanNumber());
                cell=new Cell();
                cell.add(dataPara);
                dataTable.addCell(cell);
                
                dataPara=new Paragraph();
                dataPara.add(data.getAadharNumber());
                cell=new Cell();
                cell.add(dataPara);
                dataTable.addCell(cell);
                

                x++;
                if(sno%pageSize==0 || x==this.employees.size())
                {
                    document.add(dataTable);
                    document.add(softwarePara);

                    if(x<this.employees.size())
                    {
                        document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                        newPage=true;
                    }
                }
            }
            document.close();
            // System.out.println("PDF Created");
        }catch (Exception exception)
        {
            BLException blException=new BLException();
            blException.setGenericException(exception.getMessage());
            throw blException;
        }
    }
    public int indexOfEmployee(EmployeeInterface employee) throws BLException
    {
        Iterator<EmployeeInterface> iterator=this.employees.iterator();
        EmployeeInterface e;
        int index=0;
        while(iterator.hasNext())
        {
            e=iterator.next();
            if(e.equals(employee))
            {
                return index;
            }
            index++;
        }
        BLException blException=new BLException();
        blException.setGenericException("Invalid Employee 1: " +employee.getName());
        throw blException;
    }
    public int indexOfTitle(String title, boolean partialLeftSearch)throws BLException
    {
        int index=0;
        Iterator<EmployeeInterface> iterator=this.employees.iterator();
        EmployeeInterface e;
        while(iterator.hasNext())
        {
            e=iterator.next();
            if(partialLeftSearch)
            {
            if(e.getName().toUpperCase().startsWith(title.toUpperCase()))
                {
                return index;
                }
            }
            else
            {
                if(e.getName().equalsIgnoreCase(title))
                {
                    return index;
                }
            }
            index++;
        }
        BLException blException=new BLException();
        blException.setGenericException("Invalid Employee : " +title);
        throw blException;
    }
    public EmployeeInterface getEmployeeAt(int index) throws BLException
    {
        if(index<0 || index>=this.employees.size())
        {
            BLException blException=new BLException();
            blException.setGenericException("Invalid Employee : " +index);
            throw blException;
        }
        return this.employees.get(index);
    }
}