package com.jfshare.order.util;

import com.jfshare.finagle.thrift.order.DeliverInfo;
import com.jfshare.finagle.thrift.order.Order;
import com.jfshare.finagle.thrift.order.OrderInfo;
import com.jfshare.finagle.thrift.order.PayInfo;
import com.jfshare.order.model.OrderModel;
import com.jfshare.ridge.PropertiesUtil;
import com.jfshare.utils.PriceUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by stepyee on 16/5/8.
 */
@Component
public class FileOpUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileOpUtil.class);

    private static Map<Integer, String> orderState = null;
    private static Map<Integer, String> payState = null;
    private final static int payStateSuccess = 1;
    private static Map<Integer, String> stateType = null;
    static {
        if (orderState == null) {
            orderState = new HashMap<Integer, String>();
        }
        orderState.put(1, "等待买家付款");
        orderState.put(2, "等待发货");
        orderState.put(3, "卖家已发货");
        orderState.put(5, "交易成功");
        orderState.put(7, "交易关闭");
        orderState.put(8, "订单审核中");
        orderState.put(99, "等待返回付款信息");

        if (payState == null) {
            payState =  new HashMap<Integer, String>();
        }
        payState.put(-1, "失败");
        payState.put(0, "未支付");
        payState.put(1, "成功");
        payState.put(2, "未支付"); //正在支付

        if (stateType == null) {
            stateType = new HashMap<Integer, String>();
        }
        stateType.put(101, "支付超时");
        stateType.put(102, "买家取消");
        stateType.put(103, "售后取消");
        stateType.put(104, "维权取消");
    }
    /**
     * 生成订单导出的excel
     * @param orders
     * @return 失败返回null
     */
    @Deprecated
    public byte[] gerExportExcel(List<Order> orders) {
        String exportTitle = "导出订单信息";
        String[] colNames = getExportColNames();

        /*export excel xls*/
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        CellRangeAddress cra=new CellRangeAddress(0, 0, 0, colNames.length - 1);
        sheet.addMergedRegion(cra);

        try {
            // 弟0行 快递标题
            HSSFRow row = sheet.createRow((int) 0);
            row.setHeight((short) 2000);
            row.setHeightInPoints(100);
            HSSFCellStyle style0 = wb.createCellStyle();
            style0.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            style0.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            style0.setWrapText(true);
            Font ztFont0 = wb.createFont();
            ztFont0.setFontName("宋体");
            ztFont0.setFontHeightInPoints((short)11);
            ztFont0.setColor(HSSFColor.GREY_50_PERCENT.index);
            style0.setFont(ztFont0);

            HSSFCell cell = row.createCell(0, Cell.CELL_TYPE_STRING);
            cell.setCellValue(exportTitle);
            cell.setCellStyle(style0);

            //第1行 导出列标题
            row = sheet.createRow((int) 1);
            row.setHeight((short) 242);
            row.setHeightInPoints(12.1f);
            HSSFCellStyle style1 = wb.createCellStyle();
            Font ztFont1 = wb.createFont();
            ztFont1.setFontName("Arial");
            ztFont1.setFontHeightInPoints((short)11);
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
//            ztFont2.setFontHeigInPoints((short)10);
            style2.setFont(ztFont2);
            for (int i = 0; i < orders.size(); i++)
            {
                Order order = orders.get(i);
                DeliverInfo deliverInfo = order.getDeliverInfo();
                PayInfo payInfo = order.getPayInfo();
                List<OrderInfo> productInfos = order.getProductList();
                for (int j = 0; j<productInfos.size(); j++) {
                    OrderInfo productInfo = productInfos.get(j);
                    row = sheet.createRow(curRowIndex++);
                    row.setRowStyle(style2);
                    //----------------------------0-9-----------------------------------------//
                    HSSFCell cell0 = row.createCell(0, Cell.CELL_TYPE_STRING);
                    cell0.setCellValue(convertStr(order.getOrderId()));

                    HSSFCell cell1 = row.createCell(1, Cell.CELL_TYPE_STRING);
                    cell1.setCellValue(convertStr(orderState.get(order.getOrderState())));

                    HSSFCell cell2 = row.createCell(2, Cell.CELL_TYPE_STRING);
                    cell2.setCellValue(convertStr(order.getSellerName()));

                    HSSFCell cell3 = row.createCell(3, Cell.CELL_TYPE_STRING);
                    cell3.setCellValue(convertStr(deliverInfo.getReceiverName()));

                    HSSFCell cell4 = row.createCell(4, Cell.CELL_TYPE_STRING);
                    cell4.setCellValue(convertStr(getTelFormat(deliverInfo.getReceiverMobile(), deliverInfo.getReceiverTele())));

                    HSSFCell cell5 = row.createCell(5, Cell.CELL_TYPE_STRING);
                    cell5.setCellValue(convertStr(deliverInfo.getProvinceName()));

                    HSSFCell cell6 = row.createCell(6, Cell.CELL_TYPE_STRING);
                    cell6.setCellValue(convertStr(deliverInfo.getCityName()));

                    HSSFCell cell7 = row.createCell(7, Cell.CELL_TYPE_STRING);
                    cell7.setCellValue(convertStr(deliverInfo.getCountyName()));

                    HSSFCell cell8 = row.createCell(8, Cell.CELL_TYPE_STRING);
                    cell8.setCellValue(convertStr(deliverInfo.getReceiverAddress()));

                    HSSFCell cell9 = row.createCell(9, Cell.CELL_TYPE_STRING);
                    cell9.setCellValue(convertStr(deliverInfo.getPostCode()));

                    //-----------------------------------10-19----------------------------------//
                    HSSFCell cell10 = row.createCell(10, Cell.CELL_TYPE_STRING);
                    cell10.setCellValue(convertStr(order.getUserName()));

                    HSSFCell cell11 = row.createCell(11, Cell.CELL_TYPE_STRING);
                    cell11.setCellValue(convertStr(productInfo.getProductName()));

                    HSSFCell cell12 = row.createCell(12, Cell.CELL_TYPE_STRING);
                    cell12.setCellValue(convertStr(productInfo.getSkuDesc()).replaceAll(":", ", "));

                    HSSFCell cell13 = row.createCell(13, Cell.CELL_TYPE_STRING);
                    cell13.setCellValue(convertStr(productInfo.getSkuNum()));

                    HSSFCell cell14 = row.createCell(14, Cell.CELL_TYPE_NUMERIC);
                    cell14.setCellValue(getInt(productInfo.getCount(), 0));

                    HSSFCell cell15 = row.createCell(15, Cell.CELL_TYPE_NUMERIC);
                    cell15.setCellValue(getAmount2(productInfo.getCurPrice()).doubleValue());

                    HSSFCell cell16 = row.createCell(16, Cell.CELL_TYPE_NUMERIC);
                    cell16.setCellValue(getAmount2(PriceUtils.intToStr(PriceUtils.strToInt(order.getPostage()))).doubleValue());

                    HSSFCell cell17 = row.createCell(17, Cell.CELL_TYPE_NUMERIC);
                    cell17.setCellValue(getAmount2(PriceUtils.intToStr(PriceUtils.strToInt(order.getClosingPrice()))).doubleValue());

                    HSSFCell cell18 = row.createCell(18, Cell.CELL_TYPE_STRING);
                    cell18.setCellValue(convertStr(order.getCreateTime()));

                    //-----------------------------------20-29----------------------------------//
                    HSSFCell cell19 = row.createCell(19, Cell.CELL_TYPE_STRING);
                    String payTimeDisplay = payInfo.getPayState() == payStateSuccess ? payInfo.getPayTime() : "";
                    cell19.setCellValue(convertStr(payTimeDisplay));

                    HSSFCell cell20 = row.createCell(20, Cell.CELL_TYPE_STRING);
                    cell20.setCellValue(convertStr(order.getDeliverTime()));

                    HSSFCell cell21 = row.createCell(21, Cell.CELL_TYPE_STRING);
                    cell21.setCellValue(convertStr(order.getSuccessTime()));

                    HSSFCell cell22 = row.createCell(22, Cell.CELL_TYPE_STRING);
                    cell22.setCellValue(convertStr(deliverInfo.getExpressName()));

                    HSSFCell cell23 = row.createCell(23, Cell.CELL_TYPE_STRING);
                    cell23.setCellValue(convertStr(deliverInfo.getExpressNo()));

                    HSSFCell cell24 = row.createCell(24, Cell.CELL_TYPE_STRING);
                    cell24.setCellValue(convertStr(order.getBuyerComment()));

                    HSSFCell cell25 = row.createCell(25, Cell.CELL_TYPE_STRING);
                    cell25.setCellValue(convertStr(payState.get(payInfo.getPayState())));

                    String state1 =  stateType.get(order.getActiveState());
                    HSSFCell cell26 = row.createCell(26, Cell.CELL_TYPE_STRING);
                    cell26.setCellValue(convertStr(state1));
                }
            }
        } catch (Exception e) {
            logger.error("生成excel列出错！"+e.getMessage(), e);
            return null;
        }
        // 第六步，将文件存到指定位置
        byte[] bytes = null;
        try
        {
            //test use
//			FileOutputStream fout = new FileOutputStream("E:/tt.xls");
//			wb.write(fout);
//			fout.close();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                wb.write(bos);
            } finally {
                bos.close();
            }
            bytes = bos.toByteArray();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return bytes;
    }

    /*
	 * 获取导出的列名称
	 */
    @Deprecated
    private static String[] getExportColNames() {
        return new String[]{"订单号", "订单状态", "单位名称", "联系人", "联系电话", "省", "市", "区",
                "邮寄地址", "邮编", "买家昵称", "商品名称", "商品规格", "物品数量",
                "单价（元）", "买家支付的邮费", "订单金额", "店铺优惠（元）", "成交时间", "付款时间", "发货时间", "确认收货时间", "快递公司",
                "快递号码", "买家留言", "支付状态", "关闭原因"};
    }

    /*
     * 电话显示格式 132，232 或 132
     */
    private static String getTelFormat(String m, String t) {
        String a1 = convertStr(m);
        String a2 = convertStr(t);
        String splitString = (a1.equals("") || a2.equals("")) ? "": "，";

        return a1 + splitString + a2;
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
            return "ExportOrder_" + sellerID+"_" + timeFormat.format(new Date(cur)) + ".xls";
        } else
            return "ExportOrder_" + sellerID+"_" + timeFormat.format(date) + ".xls";
    }

    private static String convertStr(Object obj) {
        String des = "";
        try {
            des = obj.toString().trim();
        } catch (Exception e) {

        }
        return des;
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
            String trackerListStr = PropertiesUtil.getProperty("trade_public_fastdfs", "1.1").trim();

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
     * @param fileLength
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
