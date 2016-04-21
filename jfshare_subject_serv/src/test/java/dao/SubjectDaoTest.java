package dao;

import com.jfshare.subject.bean.TbSubject;
import com.jfshare.subject.dao.SubjectDao;
import com.jfshare.utils.JsonMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/stand-alone.xml")
public class SubjectDaoTest {

    @Resource
    private SubjectDao subjectDao;

    @Test
    public void testGetById() {
        TbSubject tbSubject = subjectDao.getById(1);
        System.out.println(">>>>>> tbSubject : " + JsonMapper.toJson(tbSubject));
    }

    @Test
    public void testSelectTreeById() {


    }

    @Test
    public void testAdd() {

        TbSubject subject = new TbSubject();
        subject.setName("test2");
        subject.setPid(0);
        subject.setSorted(100);
        subject.setLevel(1);
        subject.setIsLeaf(0);
        subject.setDemo("测试");
        subject.setCreator(1207);
        subject.setUpdater(1207);
        long start = System.currentTimeMillis();

        this.subjectDao.add(subject);

        System.out.println(">>>>> id : " + subject.getId());
    }
    
    @Test
    public void testDelete(){
    	int[] ids = new int[]{1350,
 	           1366,
 	           1413,
 	           1414};
    	
    	for (int id : ids) {
    		subjectDao.delete(id);
		}
    }
}
