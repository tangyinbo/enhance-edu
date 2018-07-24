package com.cowboy.excel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/4/19 14:02
 * @Description:
 */
public class ExportEntity {
    private int index;
    private Date date;
    private String greet;
    private float floats;
    private BigDecimal dd;

    public BigDecimal getDd() {
        return dd;
    }

    public void setDd(BigDecimal dd) {
        this.dd = dd;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGreet() {
        return greet;
    }

    public void setGreet(String greet) {
        this.greet = greet;
    }

    public float getFloats() {
        return floats;
    }

    public void setFloats(float floats) {
        this.floats = floats;
    }


    public int getIndex() {

        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
