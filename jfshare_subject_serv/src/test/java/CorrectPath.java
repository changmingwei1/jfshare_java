import com.jfshare.finagle.thrift.subject.DisplaySubjectParam;
import com.jfshare.subject.bean.TbDisplaySubject;
import com.jfshare.subject.bean.TbSubject;
import com.jfshare.subject.dao.DisplaySubjectDao;
import com.jfshare.subject.dao.SubjectDao;
import com.jfshare.subject.dao.impl.redis.JedisBaseDao;
import com.jfshare.subject.server.Handler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/stand-alone.xml")
public class CorrectPath {

    @Resource(name = "subjectDaoImpl")
    private SubjectDao subjectDao;

    @Resource(name = "displaySubjectDaoImpl")
    private DisplaySubjectDao displaySubjectDao;

    @Resource(name = "subjectHandler")
    private Handler handler;

    @Resource
    private JedisBaseDao jedisBaseDao;


    @Test
    public void correctSubjectPath() {
        List<TbSubject> subjectList = subjectDao.getWholeTree();
        for (TbSubject tbSubject : subjectList) {
            this.handler.update(this.handler.rConvertSubject(tbSubject));
        }
    }

    @Test
    public void correctDisSubjectPath() {
        List<TbDisplaySubject> subjectList = displaySubjectDao.getWholeTree();
        for (TbDisplaySubject tbSubject : subjectList) {
            DisplaySubjectParam param = new DisplaySubjectParam();
            param.setDisplaySubjectInfo(this.handler.rConvertDisSubject(tbSubject));
            param.setFlag(tbSubject.getType());
            this.handler.update4dis(param);
        }
    }

    /**
     * 清除缓存数据
     */
    @Test
    public void testDeleteRedisCache() throws Exception {
        /*List<TbSubject> subjects = this.subjectDao.getWholeTree();
        for (TbSubject subject : subjects) {
            this.jedisBaseDao.removeKV("subject:info:" + subject.getId());
            System.out.println(">>>>> remove " + "subject:info:" + subject.getId());
        }*/

        this.jedisBaseDao.removeKV("subject:info:" + 1000);
        System.out.println(">>>>> remove " + "subject:info:" + 1000);
    }

}
