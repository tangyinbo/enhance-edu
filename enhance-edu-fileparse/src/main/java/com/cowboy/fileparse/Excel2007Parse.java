package com.cowboy.fileparse;

import com.cowboy.fileparse.facade.KYFileParse;
import com.cowboy.fileparse.facade.RowDataProcess;
import com.cowboy.fileparse.facade.RowFileter;
import com.cowboy.fileparse.facade.RowReader;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 抽象Excel2007读取器，excel2007的底层数据结构是xml文件，采用SAX的事件驱动的方法解析
 * xml，需要继承DefaultHandler，在遇到文件内容时，事件会触发，这种做法可以大大降低
 * 内存的耗费，特别使用于大数据量的文件。
 *
 * @Auther: Tangyinbo
 * @Date: 2018/5/30 16:06
 * @Description:
 */
public class Excel2007Parse extends DefaultHandler implements KYFileParse {
    /**
     * 支持的其格式化
     */
    //private UnmodifiableList dateFormts = (UnmodifiableList) UnmodifiableList.unmodifiableList(Arrays.asList("yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyyMMdd", "yyyyMMddHHmmss"));

    //共享字符串表
    private SharedStringsTable sst;
    //上一次的内容
    private String lastContents;
    private boolean nextIsString;

    private int sheetIndex = -1;
    private List<String> rowlist = new ArrayList<String>();
    //当前行
    private int curRow = 0;
    //当前列
    private int curCol = 0;
    //日期标志
    private boolean dateFlag;
    //数字标志
    private boolean numberFlag;

    private boolean isTElement;
    /**
     * 行阅读
     */
    private RowReader<String> rowReader;

    /**
     * 行数据过滤器
     */
    private List<RowFileter> rowFilters;

    /**
     * 行数据处理器
     */
    private RowDataProcess rowDataProcess;

    public void setRowReader(RowReader rowReader){
        this.rowReader = rowReader;
    }

    @Override
    public void setRowFilters(List<RowFileter> rowFilters) {
        this.rowFilters = rowFilters;
    }

    @Override
    public void addRowFilter(RowFileter rowFileter) {
        if (this.rowFilters != null) {
            this.rowFilters.add(rowFileter);
        } else {
            synchronized (this) {
                if (this.rowFilters == null) {
                    this.rowFilters = new ArrayList<>();
                    rowFilters.add(rowFileter);
                }
            }
        }
    }


    @Override
    public void setRowDataProcess(RowDataProcess rowDataProcess) {
        this.rowDataProcess = rowDataProcess;
    }

    /**
     * 只遍历一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3
     *
     * @param filename
     * @param sheetId
     * @throws Exception
     */
    public void processOneSheet(String filename, int sheetId) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);

        // 根据 rId# 或 rSheet# 查找sheet
        InputStream sheet2 = r.getSheet("rId" + sheetId);
        sheetIndex++;
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
    }

    /**
     * 遍历工作簿中所有的电子表格
     *
     * @param filename
     * @throws Exception
     */
    @Override
    public void process(String filename) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);
        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst)
            throws SAXException {
        XMLReader parser = XMLReaderFactory
                .createXMLReader("org.apache.xerces.parsers.SAXParser");
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }

    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {

        // c => 单元格
        if ("c".equals(name)) {
            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
            String cellType = attributes.getValue("t");
            if ("s".equals(cellType)) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
            //日期格式
            String cellDateType = attributes.getValue("s");
            if ("1".equals(cellDateType)) {
                dateFlag = true;
            } else {
                dateFlag = false;
            }
            String cellNumberType = attributes.getValue("s");
            if ("2".equals(cellNumberType)) {
                numberFlag = true;
            } else {
                numberFlag = false;
            }

        }
        //当元素为t时
        if ("t".equals(name)) {
            isTElement = true;
        } else {
            isTElement = false;
        }

        // 置空
        lastContents = "";
    }

    public void endElement(String uri, String localName, String name)
            throws SAXException {

        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (nextIsString) {
            try {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx))
                        .toString();
            } catch (Exception e) {

            }
        }
        //t元素也包含字符串
        if (isTElement) {
            String value = lastContents.trim();
            rowlist.add(curCol, value);
            curCol++;
            isTElement = false;
            // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
            // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
        } else if ("v".equals(name)) {
            String value = lastContents.trim();
            value = value.equals("") ? " " : value;
            //日期格式处理
           /* if (dateFlag) {

                Date date = HSSFDateUtil.getJavaDate(Double.valueOf(value));
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "dd/MM/yyyy");
                value = dateFormat.format(date);
            }
            //数字类型处理
            if (numberFlag) {
                BigDecimal bd = new BigDecimal(value);
                value = bd.setScale(3, BigDecimal.ROUND_UP).toString();
            }*/
            rowlist.add(curCol, value);
            curCol++;
        } else {
            //如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if (name.equals("row")) {
                // 每行结束时， 调用getRows() 方法
                RowData<String> rowData = rowReader.getRows(sheetIndex,curRow, rowlist);

                //过滤器处理结果,默认true
                boolean filterResult = true;
                if(this.rowFilters != null){
                    for(RowFileter fileter:rowFilters){
                        if(fileter == null){
                            continue;
                        }
                        //过滤数据
                        filterResult = fileter.filter(rowData);
                        //如果返回false不继续后面的处理
                        if(!filterResult){
                            filterResult = false;
                            break;
                        }
                    }
                }
                //处理数据
                if(rowDataProcess != null && filterResult){
                    rowDataProcess.process(rowData);
                }
                rowlist.clear();
                curRow++;
                curCol = 0;
            }/*else if ("c".equals(name)) {// <c>标签结束
                //
            //} else if (isTextTag(name)) {// 文本单元格结束标签
                //
            } else if ("row".equals(name)) {// 行结束标签
                //
            } else if ("f".equals(name)) {// </f>标签
                //
            } else if ("is".equals(name)) {// </is>标签
                //
            }*/ else if ("worksheet".equals(name)) {// Sheet读取完成
                System.out.println("finish read...............");
                rowDataProcess.finish();
            }
        }

    }

  /*  private String formatStr(Date date) {
        for (int i = 0; i < dateFormts.size(); i++) {
            try {
                String format = (String) dateFormts.get(i);
                return DateFormatUtils.format(date,format);
            } catch (Exception e) {
                if(i == dateFormts.size() -1){
                    throw e;
                }
            }
        }
        return null;
    }*/



    public void characters(char[] ch, int start, int length)
            throws SAXException {
        //得到单元格内容的值
        lastContents += new String(ch, start, length);
    }


}