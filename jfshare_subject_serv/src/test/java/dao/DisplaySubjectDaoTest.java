package dao;

import com.jfshare.subject.bean.TbDisplaySubject;
import com.jfshare.subject.dao.DisplaySubjectDao;
import com.jfshare.utils.JsonMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/stand-alone.xml")
public class DisplaySubjectDaoTest {

    @Resource
    private DisplaySubjectDao displaySubjectDao;

    @Test
    public void testGetWholeTreeByType() {
        List<TbDisplaySubject> subjects = displaySubjectDao.getWholeTreeByType("main");
        System.out.println(">>>>>> subjects : " + JsonMapper.toJson(subjects));
    }

    @Test
    public void testSelectTreeById() {
    

    }
}
