import com.chayan.mulewa.hr.bl.exceptions.*;
import com.chayan.mulewa.hr.bl.interfaces.manager.*;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
import com.chayan.mulewa.hr.bl.pojo.*;
import com.chayan.mulewa.hr.bl.manager.*;
import java.util.*;
class DesignationManagerUpdateTestCase
{
    public static void main(String gg[]) 
    {

        DesignationInterface designation=new Designation();
        int code=Integer.parseInt(gg[0]);
        String title=gg[1];
        designation.setCode(code);
        designation.setTitle(title);
        try
        {
            DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
            designationManager.updateDesignation(designation);
            System.out.println("");
            System.out.println("**************************************************");
            System.out.println("");
            System.out.println("Designation Updated");
            System.out.println("");
            System.out.println("**************************************************");
        }catch(BLException blException)
        {
             if(blException.hasGenericException())
             {
                System.out.println(blException.getMessage());
             }
             List<String> properties=blException.getProperties();
             for(String property:properties)
             {
                System.out.println(blException.getException(property));
             }
        }
    }
}