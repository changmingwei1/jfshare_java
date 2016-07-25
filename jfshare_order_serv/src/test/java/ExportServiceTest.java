import com.jfshare.finagle.thrift.order.OrderQueryConditions;
import com.jfshare.order.service.ExportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by bq on 16/6/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/stand-alone.xml")
public class ExportServiceTest {
    @Autowired
    private ExportService exportService;

    @Test
    public void testExcute(){
        OrderQueryConditions conditions = new OrderQueryConditions();
        conditions.setOrderId("58870112");
        exportService.asyncExport(conditions, "testKey", null);
    }
}
