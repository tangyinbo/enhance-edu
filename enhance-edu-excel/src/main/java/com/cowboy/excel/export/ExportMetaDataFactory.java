package com.cowboy.excel.export;

import com.cowboy.excel.exception.KyFileExportException;
import com.cowboy.excel.export.domain.ExportCell;
import com.cowboy.excel.export.domain.ExportType;
import com.cowboy.excel.utils.ConfigParser;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出配置文件解析
 */
public class ExportMetaDataFactory {
    /**
     * 解析配置文件信息
     *
     * @param inputStream
     * @return
     * @throws KyFileExportException
     */
    public static ExportMetaData getExportMetaData(InputStream inputStream) throws KyFileExportException {
        return getExportCells(inputStream);
    }

    /**
     * 解析配置文件信息
     *
     * @param inputStream
     * @return
     * @throws KyFileExportException
     */
    private static ExportMetaData getExportCells(InputStream inputStream) throws KyFileExportException {

        ExportMetaData ExportMetaData = new ExportMetaData();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document document = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(inputStream);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new KyFileExportException(e, "pares xml error");
        }


        Element root = document.getDocumentElement();
        NodeList elements = root.getElementsByTagName("cell");
        List<ExportCell> exportCells = initElement(elements);

        String fileName = "";
        String exportType1 = "";
        try {
            fileName = ConfigParser.getNodeText(root, "fileName");
            exportType1 = ConfigParser.getNodeText(root, "exportType");
        } catch (KyFileExportException e) {
            throw new KyFileExportException(e);
        }
        if (StringUtils.isEmpty(fileName)) {
            throw new KyFileExportException("用于导出的xml文档 <fileName> 为空");
        }

        if (StringUtils.isEmpty(exportType1) || !StringUtils.isNumeric(exportType1)) {
            throw new KyFileExportException("用于导出的xml文档 <exportType> 为空");
        }

        ExportMetaData.setFileName(fileName);
        ExportType exportType = ExportType.getExportType(Integer.valueOf(exportType1));
        if (exportType == null) {
            throw new KyFileExportException("找不到相应的ExportType 解析xml得到的exportType 是" + exportType1);
        }
        ExportMetaData.setExportType(exportType);
        ExportMetaData.setExportCells(exportCells);

        return ExportMetaData;
    }

    private static List<ExportCell> initElement(NodeList elements) throws KyFileExportException {

        List<ExportCell> exportCells = new ArrayList<ExportCell>(elements.getLength());
        for (int i = 0; i < elements.getLength(); i++) {
            ExportCell exportCell = new ExportCell();
            Element node = (Element) elements.item(i);
            String titleText = "";
            String aliasText = "";
            try {
                titleText = ConfigParser.getNodeText(node, "title");
                aliasText = ConfigParser.getNodeText(node, "alias");
            } catch (KyFileExportException e) {
                throw new KyFileExportException(e);
            }
            if (StringUtils.isEmpty(titleText)) {
                throw new KyFileExportException("用于导出的xml文档 <title> 为空");
            }
            exportCell.setTitle(titleText);

            if (StringUtils.isEmpty(aliasText)) {
                throw new KyFileExportException("用于导出的xml文档 <alias> 为空");
            }
            exportCell.setAlias(aliasText);

            exportCells.add(exportCell);
        }

        if (exportCells.isEmpty()) {
            throw new KyFileExportException("用于导出的xml文档解析内容为空");
        }

        return exportCells;
    }
}
