package tree;

import com.jfshare.subject.dao.impl.redis.JedisBaseDao;
import com.jfshare.subject.model.SubjectTreeModel;
import com.jfshare.subject.tree.Node;
import com.jfshare.subject.tree.SubjectRefDisplayMgr;
import com.jfshare.subject.tree.TreeManager;
import com.jfshare.utils.JsonMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/stand-alone.xml")
public class TreeTest {

    @Autowired
    private JedisBaseDao jedisBaseDao;

    @Resource
    private TreeManager treeManager;

    @Resource
    private SubjectRefDisplayMgr subjectRefDisplayMgr;

//    @Test
    public void testGetSubjectTree() {
        for(int i = 0; i < 5; i++) {
            long start = System.currentTimeMillis();
            Node<SubjectTreeModel> node = this.treeManager.getSubjectTree().findNode(2);
            SubjectTreeModel model = node.getData();
            System.out.println(">>>>>> model : " + JsonMapper.toJson(model));
            System.out.println(">>>>>> 耗时： " + (System.currentTimeMillis() - start) + "ms !");

            System.out.println(">>>> node path : " + JsonMapper.toJson(node.getPath()));
        }
    }

//    @Test
    public void testGetDisplaySubjectTree() {
        System.out.println(">>>>>> wireless : " + treeManager.getDisplaySubjectTree("wireless").getTreeMap().size());
        System.out.println(">>>>>> main : " + treeManager.getDisplaySubjectTree("main").getTreeMap().size());
        System.out.println(">>>>>> subject : " + treeManager.getSubjectTree().getTreeMap().size());
    }

//    @Test
    public void testSubjectRefDisplayMgr() {
        System.out.println(">>>>> subjectRefDisplay : " + subjectRefDisplayMgr.getSubjectRefDisplayMap().size());
        System.out.println(">>>>> displayRefSubject : " + subjectRefDisplayMgr.getDisplayRefSubjectMap().size());
    }
}
