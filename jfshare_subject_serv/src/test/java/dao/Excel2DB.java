package dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.TestCase;

import org.apache.commons.lang3.StringUtils;
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
import com.jfshare.subject.bean.TbSubject;
import com.jfshare.subject.dao.SubjectDao;
import com.jfshare.subject.service.DisplaySubjectService;
import com.jfshare.subject.service.SubjectService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/stand-alone.xml")
public class Excel2DB extends TestCase {

	@Resource
	private SubjectService service;
	
	@Resource
	private DisplaySubjectService displayService;

	@Resource
	private SubjectDao dao;
	
	private String displayType;
	
	private String fileName;
	
	@Override
	@Before
	public void setUp(){
		// 准备后台类目数据
		Map<String, String> typeFiles = new HashMap<String, String>();
		// 无线与主站
		typeFiles.put("wszz", "无线与主站分类表.xls");
		// 获取本次要初始化的数据
		// 无线与主站
		displayType = "wszz";
		fileName = typeFiles.get(displayType);
		System.out.println(">>>>> ready to operation subject : " + displayType + ", file source is " + fileName);
		
	}

	@Test
	public void initSubjects() {
		HSSFWorkbook hssfWorkbook = null;
		try {
			InputStream is = Excel2DB.class.getResourceAsStream("subjects.xls");
			hssfWorkbook = new HSSFWorkbook(is);
			HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
			// 构建一级类目
			Map<String, TbSubject> storedSubjects = new HashMap<String, TbSubject>();
			int sorted = 1;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				String name = sheet.getRow(i).getCell(1).getStringCellValue();
				name = StringUtils.trimToEmpty(name).replaceAll(" ", "");
				if (!name.equals("")) {
					TbSubject tbSubject = new TbSubject();
					tbSubject.setName(name);
					tbSubject.setCreateTime(new DateTime());
					tbSubject.setUpdateTime(new DateTime());
					tbSubject.setPid(0);
					tbSubject.setSorted(sorted++);
					tbSubject.setLevel(1);
					tbSubject.setIsLeaf(0);
					service.add(tbSubject);
					storedSubjects.put(i + "_" + 1, tbSubject);
					System.out.println(tbSubject.getName() + ":"
							+ tbSubject.getSorted());
				}
			}
			// 二级目录
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				String name = sheet.getRow(i).getCell(2).getStringCellValue();
				name = StringUtils.trimToEmpty(name).replaceAll(" ", "");
				if (!name.equals("")) {
					TbSubject tbSubject = new TbSubject();
					tbSubject.setName(name);
					tbSubject.setCreateTime(new DateTime());
					tbSubject.setUpdateTime(new DateTime());
					TbSubject parent = storedSubjects.get(i + "_" + 1);
					if (parent != null) {
						sorted = 1;
					} else {
						int reduce = 0;
						while (parent == null) {
							parent = storedSubjects.get((i - reduce++) + "_"
									+ 1);
						}
					}
					tbSubject.setPid(parent.getId());
					tbSubject.setSorted(sorted++);
					tbSubject.setLevel(2);
					if (sheet.getRow(i).getCell(3).getStringCellValue().trim()
							.equals("")) {
						tbSubject.setIsLeaf(1);
					} else {
						tbSubject.setIsLeaf(0);
					}
					service.add(tbSubject);
					System.out
							.println(parent.getName() + ":"
									+ tbSubject.getName() + ":"
									+ tbSubject.getSorted());
					storedSubjects.put(i + "_" + 2, tbSubject);
				}
			}
			// 三级目录
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				sorted = 1;
				String[] names = sheet.getRow(i).getCell(3)
						.getStringCellValue().trim().replaceAll(" ", "")
						.split(",");
				for (String name : names) {
					if (name.trim().equals(""))
						continue;
					TbSubject tbSubject = new TbSubject();
					tbSubject.setName(name);
					tbSubject.setCreateTime(new DateTime());
					tbSubject.setUpdateTime(new DateTime());
					tbSubject.setPid(storedSubjects.get(i + "_" + 2).getId());
					// 计算排序号
					tbSubject.setSorted(sorted++);
					tbSubject.setLevel(3);
					// 设置为叶子节点
					tbSubject.setIsLeaf(1);
					System.out.println(storedSubjects.get(i + "_" + 2)
							.getName()
							+ ":"
							+ tbSubject.getName()
							+ ":"
							+ tbSubject.getSorted());
					// System.out.println(nameToId.get(pSubject));
					service.add(tbSubject);
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
	public void initDisplaySubjects(){
		HSSFWorkbook hssfWorkbook = null;
		try {
			InputStream is = Excel2DB.class.getResourceAsStream("subjects.xls");
			hssfWorkbook = new HSSFWorkbook(is);
			HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
			Map<String, TbDisplaySubject> storedSubjects = new HashMap<String, TbDisplaySubject>();
			int sorted = 1;
			// 构建一级类目
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				String name = sheet.getRow(i).getCell(1).getStringCellValue();
				name = StringUtils.trimToEmpty(name).replaceAll(" ", "");
				if (!name.equals("")) {
					TbDisplaySubject tbSubject = new TbDisplaySubject();
					tbSubject.setName(name);
					tbSubject.setCreateTime(new DateTime());
					tbSubject.setUpdateTime(new DateTime());
					tbSubject.setPid(0);
					tbSubject.setSorted(sorted++);
					tbSubject.setLevel(1);
					tbSubject.setIsLeaf(0);
					displayService.add(tbSubject);
					storedSubjects.put(i + "_" + 1, tbSubject);
					System.out.println(tbSubject.getName() + ":"
							+ tbSubject.getSorted());
				}
			}
			// 二级目录
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				String name = sheet.getRow(i).getCell(2).getStringCellValue();
				name = StringUtils.trimToEmpty(name).replaceAll(" ", "");
				if (!name.equals("")) {
					TbDisplaySubject tbSubject = new TbDisplaySubject();
					tbSubject.setName(name);
					tbSubject.setCreateTime(new DateTime());
					tbSubject.setUpdateTime(new DateTime());
					TbDisplaySubject parent = storedSubjects.get(i + "_" + 1);
					if (parent != null) {
						sorted = 1;
					} else {
						int reduce = 0;
						while (parent == null) {
							parent = storedSubjects.get((i - reduce++) + "_"
									+ 1);
						}
					}
					tbSubject.setPid(parent.getId());
					tbSubject.setSorted(sorted++);
					tbSubject.setLevel(2);
					if (sheet.getRow(i).getCell(3).getStringCellValue().trim()
							.equals("")) {
						tbSubject.setIsLeaf(1);
					} else {
						tbSubject.setIsLeaf(0);
					}
					displayService.add(tbSubject);
					System.out
							.println(parent.getName() + ":"
									+ tbSubject.getName() + ":"
									+ tbSubject.getSorted());
					storedSubjects.put(i + "_" + 2, tbSubject);
				}
			}
			// 三级目录
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				sorted = 1;
				String[] names = sheet.getRow(i).getCell(3)
						.getStringCellValue().trim().replaceAll(" ", "")
						.split(",");
				for (String name : names) {
					if (name.trim().equals(""))
						continue;
					TbDisplaySubject tbSubject = new TbDisplaySubject();
					tbSubject.setName(name);
					tbSubject.setCreateTime(new DateTime());
					tbSubject.setUpdateTime(new DateTime());
					tbSubject.setPid(storedSubjects.get(i + "_" + 2).getId());
					// 计算排序号
					tbSubject.setSorted(sorted++);
					tbSubject.setLevel(3);
					// 设置为叶子节点
					tbSubject.setIsLeaf(1);
					System.out.println(storedSubjects.get(i + "_" + 2)
							.getName()
							+ ":"
							+ tbSubject.getName()
							+ ":"
							+ tbSubject.getSorted());
					// System.out.println(nameToId.get(pSubject));
					displayService.add(tbSubject);
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
	public void export() {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet level1Sheet = wb.createSheet("level 1");
			HSSFRow titleRow = level1Sheet.createRow(0);
			titleRow.createCell(0).setCellValue("ID");
			titleRow.createCell(1).setCellValue("NAME");
			titleRow.createCell(2).setCellValue("ID");
			titleRow.createCell(3).setCellValue("NAME");
			titleRow.createCell(4).setCellValue("ID");
			titleRow.createCell(5).setCellValue("NAME");
			// 行计数器
			List<TbSubject> roots = dao.selectByPid(0);
//			for (int i = 0; i < subjects.size(); i++) {
//				TbSubject subject = subjects.get(i);
//			}
			copyDatas(level1Sheet, roots, 1);
			// 合并单元格
			// 合并一级ID列
//			for(int i = 0; i < level1Sheet.getLastRowNum())
//			int rowCount = level1Sheet.getLastRowNum();
//			int startRow = 1;
//			HSSFCellStyle cellStyle = wb.createCellStyle();
//			cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
//			for(int i = startRow + 1; i < rowCount; i++){
//				// 合并一级ID列
//				if(level1Sheet.getRow(i).getCell(0) != null){
//					level1Sheet.getRow(startRow).getCell(0).setCellStyle(cellStyle);
//					level1Sheet.getRow(startRow).getCell(1).setCellStyle(cellStyle);
////					level1Sheet.addMergedRegion(new CellRangeAddress(startRow,i - 1,0,0));
////					level1Sheet.addMergedRegion(new CellRangeAddress(startRow,i - 1,1,1));
//					startRow = i;
//				}
//			}
//			startRow = 1;
//			for(int i = startRow + 1; i < rowCount; i++){
//				// 合并一级ID列
//				if(level1Sheet.getRow(i).getCell(2) != null){
//					level1Sheet.getRow(startRow).getCell(2).setCellStyle(cellStyle);
//					level1Sheet.getRow(startRow).getCell(3).setCellStyle(cellStyle);
////					level1Sheet.addMergedRegion(new CellRangeAddress(startRow,i - 1,2,2));
////					level1Sheet.addMergedRegion(new CellRangeAddress(startRow,i - 1,3,3));
//					startRow = i;
//				}
//			}
//						level1Sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));
			FileOutputStream fileOut = new FileOutputStream("e:\\workbook.xls");
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int copyDatas(HSSFSheet sheet, List<TbSubject> subjects,
			int startRow) {
		if(subjects == null || subjects.isEmpty())
			return startRow;
		for (TbSubject subject : subjects) {
			
			HSSFRow titleRow = sheet.getRow(startRow) == null ?sheet.createRow(startRow) : sheet.getRow(startRow);
			titleRow.createCell(subject.getLevel() * 2 - 2).setCellValue(subject.getId().toString());
			titleRow.createCell(subject.getLevel() * 2 - 1).setCellValue(subject.getName());
			fillParent(titleRow, subject);
			List<TbSubject> subs = dao.selectByPid(subject.getId());
			if(subs == null || subs.isEmpty()){
				// 如果为叶子节点，则行数加1，继续下一个节点
				startRow++;
			}else{
				startRow = copyDatas(sheet, subs, startRow);
			}
		}
		return startRow;
	}
	
	private void fillParent(HSSFRow titleRow, TbSubject subject){
		if(subject.getLevel() <= 1)
			return;
		TbSubject pSubject = service.getById(subject.getPid());
		titleRow.createCell(pSubject.getLevel() * 2 - 2).setCellValue(pSubject.getId().toString());
		titleRow.createCell(pSubject.getLevel() * 2 - 1).setCellValue(pSubject.getName());
		fillParent(titleRow, pSubject);
	}
}