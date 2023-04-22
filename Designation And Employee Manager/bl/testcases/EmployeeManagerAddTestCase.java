import com.chayan.mulewa.hr.bl.exceptions.*;
import com.chayan.mulewa.hr.bl.interfaces.manager.*;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
import com.chayan.mulewa.hr.bl.pojo.*;
import com.chayan.mulewa.enums.*;
import com.chayan.mulewa.hr.bl.manager.*;
import java.math.BigDecimal;
import java.util.*;
import java.math.*;
import java.text.*;
class EmployeeManagerAddTestCase
{
    public static void main(String gg[]) 
    {
        
        try
        {
            String name=gg[0];
            int designationCode=Integer.parseInt(gg[1]);
            DesignationInterface designation=new Designation();
            designation.setCode(designationCode);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            Date dateOfBirth=null;
            try
            {
                dateOfBirth=simpleDateFormat.parse(gg[2]);
            }catch (ParseException parseException)
            {
                System.out.println(parseException.getMessage());
            }
            char gender=gg[3].charAt(0);
            boolean isIndian=Boolean.parseBoolean(gg[4]);
            BigDecimal basicSalary=new BigDecimal(gg[5]);
            String panNumber=gg[6];
            String aadharNumber=gg[7];
            Employee employee=new Employee();
            employee.setName(name);
            employee.setDesignation(designation);
            employee.setDateOfBirth(dateOfBirth);
            if (gender=='M')
            {
                employee.setGender(GENDER.MALE);
            }
            if (gender=='F')
            {
                employee.setGender(GENDER.FEMALE);
            }
            employee.setDateOfBirth(dateOfBirth);
            employee.setBasicSalary(basicSalary);
            employee.setIsIndian(isIndian);
            employee.setPanNumber(panNumber);
            employee.setAadharNumber(aadharNumber);
            EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
            employeeManager.addEmployee(employee);
            System.out.println("");
            System.out.println("**************************************************");
            System.out.println("");
            System.out.println("Employee Added With ID : "+employee.getEmployeeID());
            System.out.println("");
            System.out.println("**************************************************");
        }catch(BLException blException)
        {
             if(blException.hasGenericException())
             {
                System.out.println(blException.getGenericException());
             }
             List<String> properties=blException.getProperties();
             for(String property:properties)
             {
                System.out.println(blException.getException(property));
             }
        }
    }
}