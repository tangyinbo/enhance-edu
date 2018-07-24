package com.cowboy.excel.export.domain;


import com.cowboy.excel.domain.BaseModel;

/**
 * export excel Cell definition
 */
public class ExportCell extends BaseModel {
    private String title;//导出的标题中文
    private String alias;//对应的别名，映射的字段名

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
