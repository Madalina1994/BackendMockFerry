package dummyBackendTest;
import contracttest.CustomerInterfaceHolder;
import backendMock.DummyCustomerBackend;
import contracttest.CustomerInterfaceTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({CustomerInterfaceTest.class,DummyBackendTest.class})
public class CustomerContractTest {    
    
    @BeforeClass
    public static void setUpClass() {
        CustomerInterfaceHolder.customerInterface =new DummyCustomerBackend();
         }   
   
}
