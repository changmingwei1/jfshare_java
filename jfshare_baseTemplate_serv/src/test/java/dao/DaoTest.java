package dao;

import com.jfshare.baseTemplate.dao.mysql.IPostageTemplateDao;
import com.jfshare.baseTemplate.dao.mysql.IStorehouseDao;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbPostageTemplate;
import com.jfshare.baseTemplate.mybatis.model.automatic.TbStorehouse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by Lenovo on 2016/4/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-context.xml")
public class DaoTest{

    @Resource(name = "storehouseDaoImpl")
    private IStorehouseDao storehouseDao;

    @Resource
    private IPostageTemplateDao postageDao;

    @Test
    public void testAddStorehouse() {

        TbStorehouse storehouse = new TbStorehouse();
        storehouse.setName("2");
        storehouse.setSellerId(123);
        storehouse.setName("name");
        this.storehouseDao.add(storehouse);
    }

    @Test
    public void testAddPostageTemplate() {

        TbPostageTemplate template = new TbPostageTemplate();
        template.setName("2");
        template.setSellerId(123);
        template.setName("name");
        template.setType(1);
        this.postageDao.add(template);
    }

}
