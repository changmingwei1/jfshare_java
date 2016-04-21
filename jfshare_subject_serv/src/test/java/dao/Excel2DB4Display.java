package dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jfshare.subject.bean.TbDisplaySubject;
import com.jfshare.subject.dao.DisplaySubjectDao;
import com.jfshare.subject.service.DisplaySubjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/stand-alone.xml")
public class Excel2DB4Display extends TestCase {

	
	@Resource
	private DisplaySubjectService displayService;
	
	@Resource(name = "displaySubjectDaoImpl")
    private DisplaySubjectDao displaySubjectDao;

	private String displayType;
	
	private String fileName;
	
	private int sheetIndex = 0;
	
	/**
	 * 行与包含的类目对照关系，用于查找上级类目
	 */
	private Map<Integer, List<Integer>> rowsDis = new HashMap<Integer, List<Integer>>();
	
	@Override
	@Before
	public void setUp(){
		// 准备后台类目数据
		Map<String, String[]> typeFiles = new HashMap<String, String[]>();
		// 无线与主站
//		typeFiles.put("wireless", new String[]{"无线前后台分类表-4.2.xls","1"});
		typeFiles.put("wireless", new String[]{"无线前后台分类表-4.3.xls","1"});
		typeFiles.put("main", new String[]{"主站2.xls","0"});
		// 获取本次要初始化的数据
		// 无线与主站
		displayType = "wireless";
		fileName = typeFiles.get(displayType)[0];
		sheetIndex = Integer.valueOf(typeFiles.get(displayType)[1]);
	}
	
	@Test
	public void init(){
		initDisplaySubjects();
		updateSortedAndIsLeaf();
	}

	@Test
	public void initDisplaySubjects(){
		System.out.println(">>>>> ready to operation subject : " + displayType + ", file source is " + fileName + ", sheet at index " + sheetIndex);
		HSSFWorkbook hssfWorkbook = null;
		try {
			InputStream is = Excel2DB.class.getResourceAsStream(fileName);
			hssfWorkbook = new HSSFWorkbook(is);
			HSSFSheet sheet = hssfWorkbook.getSheetAt(sheetIndex);
			System.out.println(sheet.getLastRowNum());
			// 按照列循环数据，根据类目级别进行循环,目前只有三级类目
			for(int level = 0; level < 3; level++){
				int sorted = 1;
				for (int rowNum = 1; rowNum < sheet.getLastRowNum(); rowNum++) {
					try {
						HSSFRow curRow = sheet.getRow(rowNum);
						if(curRow == null){
							continue;
						}
						// 根据level获取类目名称
						HSSFCell cell = curRow.getCell(level * 2);
						// 检查名称是否为空
						if(cell == null || StringUtils.trimToEmpty(cell.getStringCellValue()).replaceAll(" ","").equals("")){
							continue;
						}
						// 检查是否与上一行数据重复
						String curFullName = "";
						String lastFullName = "";
						for(int colIndex = 0; colIndex <= level; colIndex++){
							curFullName += curRow.getCell(colIndex * 2).getStringCellValue();
							lastFullName += sheet.getRow(rowNum - 1).getCell(colIndex * 2).getStringCellValue();
						}
						if(StringUtils.trimToEmpty(curFullName).replaceAll(" ","").equals(StringUtils.trimToEmpty(lastFullName).replaceAll(" ",""))){
							List<Integer> lastRowIds =  rowsDis.get(rowNum - 1);
							List<Integer> curRowIds = new ArrayList<Integer>();
							for (Integer integer : lastRowIds) {
								curRowIds.add(integer);
							}
							rowsDis.put(rowNum, curRowIds);
							continue;
						}
						// 获取名称
						String name = StringUtils.trimToEmpty(cell.getStringCellValue()).replaceAll(" ","");
						// 获取对应的后台类目
						String subjectIds = null;
						try {
							subjectIds = StringUtils.trimToEmpty(curRow.getCell(level * 2 + 1).getStringCellValue()).replaceAll(" ","");
						} catch (Exception e) {
							try {
								subjectIds = String.valueOf(curRow.getCell(level * 2 + 1).getNumericCellValue()).replaceAll("\\.0", "");;
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
						TbDisplaySubject subject = new TbDisplaySubject();
						subject.setName(name);
						subject.setCreateTime(new DateTime());
						subject.setUpdateTime(new DateTime());
						// rowsDis
						if(rowsDis.get(rowNum) == null){
							rowsDis.put(rowNum, new ArrayList<Integer>());
						}
						if(rowsDis.get(rowNum).isEmpty()){
							subject.setPid(0);
						}else{
							subject.setPid(rowsDis.get(rowNum).get(rowsDis.get(rowNum).size() - 1).intValue());
						}
//						if(subject.getPid() != rowsDis.get(rowNum - 1).get(rowsDis.get(rowNum - 1).size() - 1))
//							sorted = 1;
						subject.setSorted(sorted++);
						subject.setLevel(level + 1);
						subject.setIsLeaf(0);
						subject.setSubjectIds(subjectIds);
						subject.setType(displayType);
//						subject.setId(startId++);
						displayService.add(subject);
						System.out.println(subject.getId() - subject.getPid());
						rowsDis.get(rowNum).add(subject.getId());
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(level + "~~~~~~~~~" + rowNum);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				hssfWorkbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void updateSortedAndIsLeaf(){
		List<TbDisplaySubject> all = displaySubjectDao.getWholeTreeByType(displayType);
		Map<String, TbDisplaySubject> allMapIdKey = new HashMap<String, TbDisplaySubject>();
		Map<String, TbDisplaySubject> allMapPidKey = new HashMap<String, TbDisplaySubject>();
		List<TbDisplaySubject> roots = new ArrayList<TbDisplaySubject>();
		for (TbDisplaySubject tbDisplaySubject : all) {
			allMapIdKey.put(tbDisplaySubject.getId().toString(), tbDisplaySubject);
			allMapPidKey.put(tbDisplaySubject.getPid().toString(), tbDisplaySubject);
			if(tbDisplaySubject.getLevel() == 1)
				roots.add(tbDisplaySubject);
		}
		// 更新路径
		for (TbDisplaySubject tbDisplaySubject : all) {
			tbDisplaySubject.setPath("" + tbDisplaySubject.getId());
			TbDisplaySubject p = allMapIdKey.get(tbDisplaySubject.getPid().toString());
			while(p	!= null){
				tbDisplaySubject.setPath(p.getId() + "/" + tbDisplaySubject.getPath());
				p = allMapIdKey.get(p.getPid().toString());
			}
		}
		// 更新排序和叶子节点标识
		for (TbDisplaySubject tbDisplaySubject : all) {
			if(allMapPidKey.get(tbDisplaySubject.getId()) == null){
				tbDisplaySubject.setIsLeaf(1);
				displaySubjectDao.update(tbDisplaySubject);
			}
		}
		updateSorted(roots);
	}
	
	private void updateSorted(List<TbDisplaySubject> list){
		int sorted = 1;
		for (TbDisplaySubject tbDisplaySubject : list) {
			tbDisplaySubject.setSorted(sorted++);
			displaySubjectDao.update(tbDisplaySubject);
			if(tbDisplaySubject.getIsLeaf() == 0){
				updateSorted(displaySubjectDao.selectByPid(tbDisplaySubject.getId()));
			}
		}
	}
	
	@Test
	public void export() {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet level1Sheet = wb.createSheet(displayType);
			HSSFRow titleRow = level1Sheet.createRow(0);
			titleRow.createCell(0).setCellValue("ID");
			titleRow.createCell(1).setCellValue("NAME");
			titleRow.createCell(2).setCellValue("后台类目");
			titleRow.createCell(3).setCellValue("ID");
			titleRow.createCell(4).setCellValue("NAME");
			titleRow.createCell(5).setCellValue("后台类目");
			titleRow.createCell(6).setCellValue("ID");
			titleRow.createCell(7).setCellValue("NAME");
			titleRow.createCell(8).setCellValue("后台类目");
			// 行计数器
			List<TbDisplaySubject> all = displaySubjectDao.getWholeTreeByType(displayType);
			Map<Integer, TbDisplaySubject> allMap = new HashMap<Integer, TbDisplaySubject>();
			List<TbDisplaySubject> roots = new ArrayList<TbDisplaySubject>();
			for (TbDisplaySubject tbDisplaySubject : all) {
				allMap.put(tbDisplaySubject.getPid(), tbDisplaySubject);
				if(tbDisplaySubject.getLevel() == 1)
					roots.add(tbDisplaySubject);
			}
			copyDatas(level1Sheet, roots, 1);
			FileOutputStream fileOut = new FileOutputStream("e:\\" + fileName);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int copyDatas(HSSFSheet sheet, List<TbDisplaySubject> subjects,
			int startRow) {
		if(subjects == null || subjects.isEmpty())
			return startRow;
		for (TbDisplaySubject subject : subjects) {
			
			HSSFRow titleRow = sheet.getRow(startRow) == null ?sheet.createRow(startRow) : sheet.getRow(startRow);
			titleRow.createCell(subject.getLevel() * 3 - 3).setCellValue(subject.getId().toString());
			titleRow.createCell(subject.getLevel() * 3 - 2).setCellValue(subject.getName());
			titleRow.createCell(subject.getLevel() * 3 - 1).setCellValue(subject.getSubjectIds());
			fillParent(titleRow, subject);
			List<TbDisplaySubject> subs = displaySubjectDao.selectByPid(subject.getId());
			if(subs == null || subs.isEmpty()){
				// 如果为叶子节点，则行数加1，继续下一个节点
				startRow++;
			}else{
				startRow = copyDatas(sheet, subs, startRow);
			}
		}
		return startRow;
	}
	
	private void fillParent(HSSFRow titleRow, TbDisplaySubject subject){
		if(subject.getLevel() <= 1)
			return;
		TbDisplaySubject pSubject = displayService.getById(subject.getPid());
		titleRow.createCell(pSubject.getLevel() * 3 - 3).setCellValue(pSubject.getId().toString());
		titleRow.createCell(pSubject.getLevel() * 3 - 2).setCellValue(pSubject.getName());
		titleRow.createCell(pSubject.getLevel() * 3 - 1).setCellValue(pSubject.getSubjectIds());
		fillParent(titleRow, pSubject);
	}
}