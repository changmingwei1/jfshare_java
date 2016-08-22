package com.jfshare.product.util;

import com.jfshare.product.model.TbThirdPartyProductWithBLOBs;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.PriceUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by stepyee on 16/5/8.
 */
@Component
public class FileOpUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileOpUtil.class);


    public byte[] getExportExcel(List<TbThirdPartyProductWithBLOBs> partyProductWithBLOBses) {
        String exportTitle = "第三方商品信息";
        String[] colNames = new String[]{"商家名称", "商品名称", "商品在第三方平台的id", "商品在聚分享的id", "当前结算价", "库存", "商品在第三方平台的上架状态", "商品提报状态", "第三方平台商品链接", "聚分享平台商品链接"};
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        CellRangeAddress cra = new CellRangeAddress(0, 0, 0, colNames.length - 1);
        sheet.addMergedRegion(cra);

        try {
            HSSFRow row = sheet.createRow((int) 0);
            row.setHeight((short) 2000);
            row.setHeightInPoints(100);
            HSSFCellStyle style0 = wb.createCellStyle();
            style0.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style0.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            style0.setWrapText(true);
            Font ztFont0 = wb.createFont();
            ztFont0.setFontName("宋体");
            ztFont0.setFontHeightInPoints((short) 11);
            ztFont0.setColor(HSSFColor.GREY_50_PERCENT.index);
            style0.setFont(ztFont0);

            HSSFCell cell = row.createCell(0, Cell.CELL_TYPE_STRING);
            cell.setCellValue(exportTitle);
            cell.setCellStyle(style0);

            //第1行 导出列标题
            row = sheet.createRow(1);
            row.setHeight((short) 242);
            row.setHeightInPoints(12.1f);
            HSSFCellStyle style1 = wb.createCellStyle();
            Font ztFont1 = wb.createFont();
            ztFont1.setFontName("Arial");
            ztFont1.setFontHeightInPoints((short) 11);
            style1.setFont(ztFont1);
            for (int i = 0; i < colNames.length; i++) {
                cell = row.createCell(i, Cell.CELL_TYPE_STRING);
                cell.setCellValue(colNames[i]);
                cell.setCellStyle(style1);
            }

            //第2开始的数据行
            int curRowIndex = 2;
            HSSFCellStyle style2 = wb.createCellStyle();
            Font ztFont2 = wb.createFont();
            ztFont2.setFontName("Arial");
            style2.setFont(ztFont2);
            for (TbThirdPartyProductWithBLOBs partyProductWithBLOBse : partyProductWithBLOBses) {
                row = sheet.createRow(curRowIndex++);
                row.setRowStyle(style2);
                HSSFCell cell0 = row.createCell(0, Cell.CELL_TYPE_STRING);
                // 我买网
                if (partyProductWithBLOBse.getThirdPartyIdentify().intValue() == 1) {
                    cell0.setCellValue("我买网");
                }

                HSSFCell cell1 = row.createCell(1, Cell.CELL_TYPE_STRING);
                cell1.setCellValue(partyProductWithBLOBse.getName());

                HSSFCell cell2 = row.createCell(2, Cell.CELL_TYPE_STRING);
                cell2.setCellValue(partyProductWithBLOBse.getThirdPartyProductId());

                HSSFCell cell3 = row.createCell(3, Cell.CELL_TYPE_STRING);
                cell3.setCellValue(partyProductWithBLOBse.getProductId());

                HSSFCell cell4 = row.createCell(4, Cell.CELL_TYPE_STRING);
                cell4.setCellValue(PriceUtils.intToStr(partyProductWithBLOBse.getPrice()));

                HSSFCell cell5 = row.createCell(5, Cell.CELL_TYPE_STRING);
                cell5.setCellValue(partyProductWithBLOBse.getStockInfo());

                HSSFCell cell6 = row.createCell(6, Cell.CELL_TYPE_STRING);
                cell6.setCellValue(partyProductWithBLOBse.getActiveState());

                HSSFCell cell7 = row.createCell(7, Cell.CELL_TYPE_STRING);
                cell7.setCellValue(partyProductWithBLOBse.getOfferState());

                // 第三方平台商品链接
                HSSFCell cell8 = row.createCell(8, Cell.CELL_TYPE_STRING);
                cell8.setCellValue("第三方平台商品链接");

                // 聚分享平台商品链接
                HSSFCell cell9 = row.createCell(9, Cell.CELL_TYPE_STRING);
                cell9.setCellValue("聚分享平台商品链接");

            }
        } catch (Exception e) {
            logger.error("生成excel列出错！" + e.getMessage(), e);
            return null;
        }
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                wb.write(bos);
            } finally {
                bos.close();
            }
            bytes = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bytes;
    }


    /**
     * 导出文件命名规则
     * @param date
     * @return
     */
    public static String getFileName(int sellerID,Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmm");

        if (date == null) {
            Long cur = Calendar.getInstance().getTimeInMillis();
            return "ExportProduct_" + sellerID+"_" + timeFormat.format(new Date(cur)) + ".xls";
        } else
            return "ExportProduct_" + sellerID+"_" + timeFormat.format(date) + ".xls";
    }


    private static int getInt(Object item, int defValue) {
        try {
            return Integer.parseInt(item.toString());
        } catch (Exception e) {
            return defValue;
        }
    }

    private static BigDecimal getAmount(Object amount, int dotScale) {
        try {
            return new BigDecimal(amount.toString()).setScale(dotScale, RoundingMode.HALF_UP);
        } catch (Exception e) {
            return new BigDecimal(0).setScale(dotScale, RoundingMode.HALF_UP);
        }
    }

    public static BigDecimal getAmount2(Object amount) {
        return getAmount(amount, 2);
    }

    private static BigDecimal getAmount(Object amount) {
        try {
            return new BigDecimal(amount.toString());
        } catch (Exception e) {
            return new BigDecimal(0);
        }
    }


    public static String toFastDFS(byte[] fileBuff, String uploadFileName) throws Exception {
        return FastDFSUtils.uploadFile(fileBuff, uploadFileName);
    }

    public static byte[] fromFastDFS(String inputUrl) throws Exception {
        return FastDFSUtils.downloadFile(inputUrl);
    }

    public static int deleteFastDFS(String fileId) throws Exception {
        return FastDFSUtils.deleteFile(fileId);
    }
}


class FastDFSUtils {
    private static final Logger logger = LoggerFactory.getLogger(FastDFSUtils.class);

    static {
        try {
            String trackerListStr = PropertiesUtil.getProperty("jfx_public_fastdfs", "1.1").trim();

            if (trackerListStr == null || "".equals(trackerListStr)) {
                throw new Exception("从imago上获取tracker配置信息失败！");
            }

            String[] trackerList = trackerListStr.split(",");
            InetSocketAddress[] inetSocketAddressArray = new InetSocketAddress[trackerList.length];
            for(int i = 0; i < trackerList.length; i++){
                logger.info("tracker地址" + (i + 1) + "：" + trackerList[i]);
                String[] trackerIpPort = trackerList[i].split(":");
                inetSocketAddressArray[i] = new InetSocketAddress(trackerIpPort[0], Integer.parseInt(trackerIpPort[1]));
            }

            ClientGlobal.setG_connect_timeout(30000);
            ClientGlobal.setG_network_timeout(30000);
            ClientGlobal.setG_charset("UTF-8");
            ClientGlobal.setG_tracker_group(new TrackerGroup(inetSocketAddressArray));
            logger.info("fastDFS初始化完毕。");
        } catch (Exception e) {
            logger.error("FastDFS配置出错!" + e.getMessage(), e);
        }
    }

    /**
     * 上传文件到fastDFS
     *
     * @param fileBuff
     * @param uploadFileName
     * @return
     * @throws Exception
     */
    public static String uploadFile(byte[] fileBuff, String uploadFileName) throws Exception {
        TrackerServer trackerServer = null;
        String fileId = "";

        try {
            if (uploadFileName.contains(".") == false) {
                logger.error("上传文件的名称不正确！");
                return fileId;
            }

            String fileExtName = uploadFileName.substring(uploadFileName.lastIndexOf(".") + 1);

            // 建立连接
            trackerServer = new TrackerClient().getConnection();
            StorageClient1 client = new StorageClient1(trackerServer, null);

            // 设置元信息
            NameValuePair[] metaList = new NameValuePair[3];
            metaList[0] = new NameValuePair("fileName", uploadFileName);
            metaList[1] = new NameValuePair("fileExtName", fileExtName);
            metaList[2] = new NameValuePair("fileLength", String.valueOf(fileBuff.length));

            // 上传文件
            fileId = client.upload_file1(fileBuff, fileExtName, metaList);
            logger.info("上传文件：" + uploadFileName + "成功，key:" + fileId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传文件：" + uploadFileName + "到fastDFS失败！");
            throw e;
        } finally {
            trackerServer.close();
        }

        return fileId;
    }

    /**
     * 从fastDFS下载文件
     * @param inputUrl
     * @return
     */
    public static byte[] downloadFile(String inputUrl) throws Exception {
        TrackerServer trackerServer = null;
        byte[] fileBuff = null;

        try {
            if (inputUrl.contains(".") == false) {
                logger.error("待下载文件的名称不正确！");
                return fileBuff;
            }

            // 建立连接
            trackerServer = new TrackerClient().getConnection();
            StorageClient1 client = new StorageClient1(trackerServer, null);

            // 下载文件
            fileBuff = client.download_file1(inputUrl);
            logger.info("下载文件成功，key:" + inputUrl);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("下载文件：" + inputUrl + "从fastDFS失败！");
            throw e;
        } finally {
            trackerServer.close();
        }

        return fileBuff;
    }

    public static int deleteFile(String fileId) throws Exception {
        TrackerServer trackerServer = null;
        int ret = 0;
        try {
            if (fileId== null || fileId.trim().isEmpty()) {
                logger.error("待删除文件的名称不能为空！");
                return ret;
            }
            trackerServer = new TrackerClient().getConnection();
            StorageClient1 client = new StorageClient1(trackerServer, null);

            ret = client.delete_file1(fileId);
        } catch (Exception e) {
            ret = -1;
            e.printStackTrace();
            logger.error("删除文件：" + fileId + "从fastDFS失败！");
            throw e;
        } finally {
            trackerServer.close();
        }

        return ret;
    }
}
