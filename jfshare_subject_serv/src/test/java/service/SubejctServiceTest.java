package service;

import com.jfshare.subject.bean.TbSubject;
import com.jfshare.subject.model.SubjectTreeModel;
import com.jfshare.subject.service.SubjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/stand-alone.xml")
public class SubejctServiceTest {

    @Resource
    private SubjectService subejctService;
    
    @Test
    public void testGetWholeTree() {
    }
    @Test
    public void testAdd(){
    	String[] subjectsName = new String[]{"其他"};
    	int sorted = 20;
    	for (String subjectName : subjectsName) {
	    	TbSubject subject = new TbSubject();
	    	subject.setName(subjectName);
	    	subject.setPid(1073);
	    	subject.setSorted(sorted++);
	    	subject.setCreator(0);
	    	subject.setLevel(3);
	    	subject.setUpdater(0);
	    	
	    	subejctService.add(subject);
	    	System.out.println(subject.getId());
    	}
    }
    
    @Test
    public void testUpdate(){
    	
    	List<String[]> list = new ArrayList<String[]>();
    	list.add(new String[]{"1109","洗澡机   "}   );
    	list.add(new String[]{"1229","MP4      "}   );
    	list.add(new String[]{"1230","MP5      "}   );
    	list.add(new String[]{"1324","广场服   "}   );
    	list.add(new String[]{"1884","速干T恤  "}   );
    	for (String[] strings : list) {
    		TbSubject subject = subejctService.getById(Integer.valueOf(strings[0]));
    		subject.setName(strings[1].trim());
    		System.out.println(subject.getName() + "  " + subject.getName().equals(strings[1].trim()));
//    		subejctService.update(subject);
		}
    }
    

    @Test
    public void getLesves() {
        for (int i = 0; i < 5; i++) {
            List<SubjectTreeModel> models = new ArrayList<>();
            long start = System.currentTimeMillis();
            models = subejctService.getLeaves(2061);
//            System.out.println(">>>>> models : " + JsonMapper.toJson(models));
            System.out.println(">>>>>>>> models size : " + models.size());
            System.out.println(">>>>>> 耗时 ：" + (System.currentTimeMillis() - start) + " ms!");
        }

    }
}
