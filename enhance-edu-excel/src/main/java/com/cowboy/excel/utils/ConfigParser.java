package com.cowboy.excel.utils;


import com.cowboy.excel.exception.KyFileExportException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 配置文件解析
 */
public abstract class ConfigParser {
    /**
     * 配置文件解析
     *
     * @param element
     * @param key
     * @return
     * @throws KyFileExportException
     */
    public static String getNodeText(Element element, String key) throws KyFileExportException {
        NodeList nodeList = element.getElementsByTagName(key);
        if (nodeList.getLength() == 0) {
            throw new KyFileExportException("Tag is empty. tag:" + key);
        }

        return nodeList.item(0).getTextContent();
    }
}
