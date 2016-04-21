package dao.redis;

import com.jfshare.subject.dao.impl.redis.JedisBaseDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Lenovo on 2015/10/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/stand-alone.xml")
public class TestJedisDao {
    @Autowired
    private JedisBaseDao jedisBaseDao;

    @Test
    public void testSet() {
        String result = jedisBaseDao.putKV("test","test");
        System.out.println("$$$$$$$$$$$$$$"+result);
    }
}
