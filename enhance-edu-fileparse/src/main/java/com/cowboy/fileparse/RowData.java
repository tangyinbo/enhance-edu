package com.cowboy.fileparse;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/6/1 09:20
 * @Description:
 */
public class RowData<T> {
    /**
     * value
     */
    private T value;

    private RowData() {
    }

    public static <T> RowData<T> instance(T value) {
        RowData<T> rowData = new RowData<>();
        rowData.setValue(value);
        return rowData;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value+"";
    }
}
