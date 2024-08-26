/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.action;

import com.viettel.database.dao.Mt_allBO;
import com.viettel.framework.interceptor.BaseDAOMDBAction;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 */
public class DownloadDAO extends BaseDAOMDBAction {

    private static final Log log = LogFactory.getLog(DownloadDAO.class);
    private InputStream inputStream;
    private String REPLACE_END_LINE_NUMBER = "_endline_";
    private String REPLACE_BREAK_LINE_NUMBER = "_breakline_";
    private String downloadFileName;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
    }

    public String getContentDisposition() {
        return "attachment;filename=\"" + getDownloadFileName() + "\"";
    }

    public String downloadFile() {
        try {
             SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_DD");
             
            String msisdn  = getRequest().getParameter("msisdn");
            String fromDate  = getRequest().getParameter("from_date");
            String toDate  = getRequest().getParameter("to_date");
            
            String fileName = "winer_" + format.format(new Date())+ (new Random()).nextInt(1000000)+".xls";
//            String sub_service_name = getRequest().getParameter("sub_service_name");
//            File destFileName = new File("");
//            String filePath = destFileName.getAbsolutePath() + "/key/" + "/" + fileName  ;
//            filePath = filePath.replace('\\', '/').replace("/bin", "") ;
            
//                File file = new File(filePath);
//                byte[] bytes = FileUtils.getBytesFromFile(file);
//                byte[] bytes = "hadc".getBytes();
//                setInputStream(new ByteArrayInputStream(bytes));
                setInputStream(getStream(msisdn, fromDate, toDate));
                    
//                setDownloadFileName(file.getName());
                setDownloadFileName(fileName);
        } catch (Exception ex) {
            log.info(ex.getStackTrace(),ex);
            return "error";
        }
        return "download";
    }
    
    public InputStream getStream(String msisdn, String fromDate, String toDate) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            Workbook wb = exportWorkbook( msisdn,  fromDate,  toDate);
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayInputStream in = new ByteArrayInputStream(
                out.toByteArray());
        try {
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }
    
    
    protected Workbook exportWorkbook(String msisdn, String fromDate, String toDate) {
        List<String> fields = new ArrayList<>();
//        fields.add("id");
        fields.add("rank");
        fields.add("msisdn");
        fields.add("point");
        fields.add("win_day");
        fields.add("description");
        fields.add("message_code");
        fields.add("code");
        fields.add("award_code");
        fields.add("game_type");
        fields.add("award_type");
        fields.add("status");

        HSSFWorkbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet");
        sheet.setDefaultColumnWidth(20);
        sheet.setColumnWidth(0, 1200);

        HSSFFont hSSFFont = wb.createFont();
        hSSFFont.setFontName(HSSFFont.FONT_ARIAL);
        hSSFFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        CellStyle headerstyle = wb.createCellStyle();
        headerstyle.setBorderBottom(CellStyle.BORDER_THIN);
        headerstyle.setBorderTop(CellStyle.BORDER_THIN);
        headerstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headerstyle.setBorderLeft(CellStyle.BORDER_THIN);
        headerstyle.setBorderRight(CellStyle.BORDER_THIN);
        headerstyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headerstyle.setFont(hSSFFont);

        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        org.apache.poi.ss.usermodel.Row headerRowTitle = sheet.createRow(0);
        org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(1);

        sheet.addMergedRegion(new CellRangeAddress(
                0, //first row (0-based)
                0, //last row  (0-based)
                0, //first column (0-based)
                fields.size() - 1 //last column  (0-based)
        ));

        /* FIX SHOW TITLE IN FILE EXPORT EXCEL
         * VUONGBD 		
         * 2014 - 06- 25 
         */
        // Create a new font and alter it.
        Font fontTitle = wb.createFont();
        fontTitle.setFontHeightInPoints((short) 24);
        fontTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);
        fontTitle.setFontName("Times New Roman");
	    //fontTitle.setItalic(true);	 

        // Fonts are set into a style so create a new one to use.
        CellStyle styleTitle = wb.createCellStyle();
        styleTitle.setFont(fontTitle);
        styleTitle.setAlignment(CellStyle.ALIGN_CENTER);
        styleTitle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styleTitle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        Cell headerCellTitle = headerRowTitle.createCell(0);
        headerCellTitle.setCellValue("Winner_" + fromDate);
        headerCellTitle.setCellStyle(styleTitle);

        //header excel
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("No");
        headerCell.setCellStyle(headerstyle);

        int k = 1;
        for (String fieldInfo : fields) {
            headerCell = headerRow.createCell(k++);
            headerCell.setCellValue(fieldInfo);
            headerCell.setCellStyle(headerstyle);
        }

        List<Mt_allBO> datas = MtAllAction.getWiners(msisdn, fromDate, toDate);

        int columnCount = 0;
        int row = 2;

        for (Mt_allBO e : datas) {
            columnCount = 0;
            org.apache.poi.ss.usermodel.Row dataRow = sheet.createRow(row);

			//column NO
            Cell cell = dataRow.createCell(columnCount);
            cell.setCellValue(String.valueOf(row - 1));
            cell.setCellStyle(style);

            for (String propertyName : fields) {
                cell = dataRow.createCell(++columnCount);
                
                cell.setCellStyle(style);
                Object val  = "";
                
                try {
                    val = PropertyUtils.getSimpleProperty(e, propertyName) ;
                    if ("status".equals(propertyName) ) {
                        if (String.valueOf(val).equals("2")) {
                            val = "Already Paid";
                        } else if (String.valueOf(val).equals("3")) {
                            val = "Approved";
                        } else if (String.valueOf(val).equals("0")) {
                            val = "Rejected";
                        } else {
                            val = "Draft";
                        }
                    }
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(DownloadDAO.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(DownloadDAO.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(DownloadDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                cell.setCellValue(String.valueOf(val == null ? "" : String.valueOf(val)));
            }

            row++;
        }

        return wb;
    }

   
}
