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
class EmployeeManagerGetEmployeeTestCase
{
    public static void main(String gg[]) 
    {
        
        try
        {
            EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
            Set<EmployeeInterface> employeesSet=employeeManager.getEmployee();
            SimpleDateFormat simpleDateformat=new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("");
            System.out.println("**************************************************");
            for (EmployeeInterface dlEmployee:employeesSet)
            {
                System.out.println("");
                System.out.println("Employee ID : "+dlEmployee.getEmployeeID());
                System.out.println("Name : "+dlEmployee.getName());
                System.out.println("Designation : "+dlEmployee.getDesignation().getCode());
                System.out.println("Date Of Birth : "+simpleDateformat.format(dlEmployee.getDateOfBirth()));
                System.out.println("Geneder : "+dlEmployee.getGender());
                System.out.println("Is Indian : "+dlEmployee.getIsIndian());
                System.out.println("Basic Salary : "+dlEmployee.getBasicSalary());
                System.out.println("Pan Number : "+dlEmployee.getPanNumber());
                System.out.println("Aadhar Number : "+dlEmployee.getAadharNumber());
                System.out.println("");
                System.out.println("**************************************************");
            }
        }
        catch(BLException blException)
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