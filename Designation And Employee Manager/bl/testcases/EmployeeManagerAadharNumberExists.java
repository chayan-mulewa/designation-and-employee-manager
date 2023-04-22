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
class EmployeeManagerAadharNumberExists
{
    public static void main(String gg[]) 
    {
        
        try
        {
            String aadharNumber=gg[0];
            EmployeeManagerInterface employeeManager=EmployeeManager.getEmployeeManager();
            boolean aadharNumberExists=employeeManager.aadharNumberExists(aadharNumber);
            System.out.println("");
            System.out.println("**************************************************");
            System.out.println("");
            System.out.println(aadharNumberExists);
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