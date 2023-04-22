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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.table.AbstractTableModel;
public class DesignationModel extends AbstractTableModel
{
    private List<DesignationInterface> designations;
    private DesignationManagerInterface designationManager;
    private Set<DesignationInterface> blDesignations;
    private String title[];
    public DesignationModel()
    {
        populateDataStructure();
    }
    public void populateDataStructure()
    {
        this.title=new String[3];
        this.title[0]="S.NO";
        this.title[1]="Designation Title";
        this.title[2]="Desigantion Code";
        try
        {
            designationManager=DesignationManager.getDesignationManager();
            blDesignations=designationManager.getDesignations();
            this.designations=new LinkedList<>();
            for(DesignationInterface plDesignations:blDesignations)
            {
                this.designations.add(plDesignations);
            }
        }catch(BLException blException)
        {
            // TODO: handle exception
        }
        Collections.sort(this.designations, new Comparator<DesignationInterface>(){
            public int compare(DesignationInterface left, DesignationInterface right)
                {
                    return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
                }
            });
    }   
    public int getRowCount()
    {
        return this.designations.size();
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
        DesignationInterface designation=designations.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex+1; // Data for column 1
            case 1:
                return designation.getTitle(); // Data for column 2
            case 2:
                return designation.getCode(); // Data for column 3
                default:
                return null;
        }

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
    public void add(DesignationInterface designation) throws BLException
    {
        designationManager.addDesignation(designation);
        this.designations.add(designation);
        Collections.sort(this.designations, new Comparator<DesignationInterface>(){
        public int compare(DesignationInterface left, DesignationInterface right)
        {
            return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
        }
        });
        fireTableDataChanged();    
    }
    public void update(DesignationInterface designation) throws BLException
    {
        designationManager.updateDesignation(designation);
        this.designations.remove(indexOfDesignation(designation));
        this.designations.add(designation);
        Collections.sort(this.designations, new Comparator<DesignationInterface>(){
        public int compare(DesignationInterface left, DesignationInterface right)
        {
            return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
        }
        });
        fireTableDataChanged();    
    }
    public void remove(int code) throws BLException
    {
        designationManager.removeDesignation(code);
        int index=0;
        Iterator<DesignationInterface> iterator=this.designations.iterator();
        while(iterator.hasNext())
        {
            if(iterator.next().getCode()==code)
            {
                break;
            }
            index++;
        }
        if(index==this.designations.size())
        {
            BLException blException=new BLException();
            blException.setGenericException("Invalid Designation : " +code);
            throw blException;
        }
        this.designations.remove(index);
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
            float dataColumnWigth[]={200,400};
           
            Table headerTable=null;
            Table dataTable=null;

            Paragraph headerPara1=new Paragraph("S.No");
            Paragraph headerPara2=new Paragraph("Designation");
           
            DesignationInterface data;
            Paragraph dataPara;
            Paragraph serialNumberPara;

            Image image = new Image(ImageDataFactory.create(this.getClass().getResource("/icons/logo.png")));
            image.scaleToFit(100,100);
            Paragraph logoPara=new Paragraph();
            logoPara.add(image);
            Paragraph designationPara=new Paragraph("Chayan Company").setFontSize(20);
            Paragraph pageNumberPara;

            Paragraph softwarePara=new Paragraph("Software By Chayan Mulewa").setFontSize(15);

            Cell cell;

            int sno,pageSize,pageNumber,x;
            sno=0;
            pageSize=5;
            pageNumber=0;
            boolean newPage=true;
            x=0;

            while(x<this.designations.size())
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
                    cell.add(designationPara);
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
                    newPage=false;
                   
                }
                sno++;

                data=this.designations.get(x);
                serialNumberPara=new Paragraph();
                serialNumberPara.add(String.valueOf(sno));
                cell=new Cell();
                cell.add(serialNumberPara);
                dataTable.addCell(cell);
                dataPara=new Paragraph();
                dataPara.add(data.getTitle());
                cell=new Cell();
                cell.add(dataPara);
                dataTable.addCell(cell);

                x++;
                if(sno%pageSize==0 || x==this.designations.size())
                {
                    document.add(dataTable);
                    document.add(softwarePara);

                    if(x<this.designations.size())
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
    public int indexOfDesignation(DesignationInterface designation) throws BLException
    {
        Iterator<DesignationInterface> iterator=this.designations.iterator();
        DesignationInterface d;
        int index=0;
        while(iterator.hasNext())
        {
            d=iterator.next();
            if(d.equals(designation))
            {
                System.out.println("Returned index : "+index);
                return index;
            }
            index++;
        }
        BLException blException=new BLException();
        blException.setGenericException("Invalid Designation 1: " +designation.getTitle());
        throw blException;
    }
    public int indexOfTitle(String title, boolean partialLeftSearch)throws BLException
    {
        int index=0;
        Iterator<DesignationInterface> iterator=this.designations.iterator();
        DesignationInterface d;
        while(iterator.hasNext())
        {
            d=iterator.next();
            if(partialLeftSearch)
            {
            if(d.getTitle().toUpperCase().startsWith(title.toUpperCase()))
                {
                return index;
                }
            }
            else
            {
                if(d.getTitle().equalsIgnoreCase(title))
                {
                    return index;
                }
            }
            index++;
        }
        BLException blException=new BLException();
        blException.setGenericException("Invalid Designation : " +title);
        throw blException;
    }
    public DesignationInterface getDesignationAt(int index) throws BLException
    {
        if(index<0 || index>=this.designations.size())
        {
            BLException blException=new BLException();
            blException.setGenericException("Invalid Designation : " +index);
            throw blException;
        }
        return this.designations.get(index);
    }
}