import com.chayan.mulewa.hr.bl.exceptions.*;
import com.chayan.mulewa.hr.bl.interfaces.manager.*;
import com.chayan.mulewa.hr.bl.interfaces.pojo.*;
import com.chayan.mulewa.hr.bl.pojo.*;
import com.chayan.mulewa.hr.bl.manager.*;
import java.util.*;
class DesignationManagerGetDesignationByTitleTestCase
{
    public static void main(String gg[]) 
    {
        String title=gg[0];
        try
        {
            DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
            DesignationInterface designation=designationManager.getDesignationByTitle(title);
            System.out.println("");
            System.out.println("**************************************************");
            System.out.println("");
            System.out.println("Title : "+designation.getTitle()+" And Code : "+designation.getCode());
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