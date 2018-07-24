package com.cowboy.excel.imports;

import com.cowboy.excel.exception.KyFileExportException;
import com.cowboy.excel.imports.domain.ImportCell;
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
 * @Auther: Tangyinbo
 * @Date: 2018/4/20 16:35
 * @Description:
 */
public class ImportMetaDataFactory {
    /**
     * 解析导入文件配置
     * @param inputStream
     * @return
     */
    public static ImportMetaData getImportMetaData(InputStream inputStream) throws KyFileExportException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document document = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(inputStream);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new KyFileExportException(e, "pares xml error");
        }
//        Puts all Text nodes in the full depth of the sub-tree underneath this Node
        document.getDocumentElement().normalize();
        ImportMetaData configuration = new ImportMetaData();

        Element root = document.getDocumentElement();
        NodeList nList = document.getElementsByTagName("cell");
        List<ImportCell> importCells = initElement(nList);

        String startRowNoText = ConfigParser.getNodeText(root, "startRowNo");
        String fileType = ConfigParser.getNodeText(root,"filetype");
        if (StringUtils.isEmpty(startRowNoText) || !StringUtils.isNumeric(startRowNoText)) {
            throw new KyFileExportException("用于导入的xml文档 <number> 为空 或者非数字");
        }
        configuration.setStartRowNo(Integer.valueOf(startRowNoText));
        configuration.setFileType(fileType);
        configuration.setImportCells(importCells);
        return configuration;
    }

    /**
     * 解析导入文件呢配置
     * @param elements
     * @return
     * @throws KyFileExportException
     */
    private static List<ImportCell> initElement(NodeList elements) throws KyFileExportException {

        List<ImportCell> importCells = new ArrayList<ImportCell>(elements.getLength());
        for (int i = 0; i < elements.getLength(); i++) {
            ImportCell importCell = new ImportCell();
            Element node = (Element) elements.item(i);
            String numberText = ConfigParser.getNodeText(node, "number");
            if (StringUtils.isEmpty(numberText) || !StringUtils.isNumeric(numberText)) {
                throw new KyFileExportException("用于导入的xml文档 <number> 为空 或者非数字");
            }
            importCell.setNumber(Integer.valueOf(numberText));


            String keyText = ConfigParser.getNodeText(node, "key");
            if (StringUtils.isEmpty(keyText)) {
                throw new KyFileExportException("用于导入的xml文档 <key> 为空");
            }
            importCell.setKey(keyText);

            String cellTypeText = ConfigParser.getNodeText(node, "cellType");
            importCell.setCellType(getCellType(cellTypeText));

            String nullbleText = ConfigParser.getNodeText(node, "nullble");
            if (StringUtils.isEmpty(nullbleText) || !StringUtils.isNumeric(nullbleText)) {
                throw new KyFileExportException("用于导入的xml文档 <nullble> 为空 或者非数字");
            }
            importCell.setNullAble(ImportCell.NullAble.getNullble(Integer.valueOf(nullbleText)));

            importCells.add(importCell);
        }

        if (importCells.isEmpty()) {
            throw new KyFileExportException("用于导入的xml文档 解析内容为空");
        }

        return importCells;
    }

    /**
     * 获取导入文件配置类型
     * @param typeStr
     * @return
     * @throws KyFileExportException
     */
    private static ImportCell.CellType getCellType(String typeStr) throws KyFileExportException {
        if (StringUtils.isEmpty(typeStr)) {
            throw new KyFileExportException("import config xml cellType is empty");
        }
        if (StringUtils.isNumeric(typeStr)) {
            return ImportCell.CellType.getCellType(Integer.valueOf(typeStr));
        } else {
            return ImportCell.CellType.getCellType(typeStr);

        }

    }
}
