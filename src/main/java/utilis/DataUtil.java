package utilis;
import org.testng.annotations.DataProvider;

public class DataUtil {

    @DataProvider(name = "loginData")   // TestNG annotation
    public Object[][] getLoginData() {   // method name
        return new Object[][] {            // returns 2D array data
            {"axe290@gmail.com"},   // valid email
        };
    }

    @DataProvider(name = "searchData")    
    public Object[][] getSearchData() {
        return new Object[][] {
            {"Bluetooth Speaker"},
        };
    }
    
    @DataProvider(name = "product")
    public Object[][] getData() {
        return new Object[][] {
            {"Bluetooth Speaker", "shruthivimal777@gmail.com"},
        };
    }
}
