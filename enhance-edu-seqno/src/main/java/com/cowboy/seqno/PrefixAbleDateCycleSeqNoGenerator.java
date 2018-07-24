package com.cowboy.seqno;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 可以添加前缀的按日循环序列号,序列号格式: prefix + 17位日期格式[20180426103507814]+序列号[默认10位)
 * <p>前缀可以自己指定,日期段固定17位,序列号长度可以自行指定
 * <p>默认如:prefix+201804261035078140000000001,prefix+201804261035078140000000002
 * @Auther: Tangyinbo
 * @Date: 2018/4/26 10:13
 * @Description:
 */
public abstract class PrefixAbleDateCycleSeqNoGenerator implements KySeqNoGenerator{
    @Override
    public String get() {
        if(getSeqPrefix() == null){
            throw new IllegalStateException("前缀不能为空");
        }
        if (this.preDate == null) {
            synchronized (lock) {
                if (this.preDate == null) {
                    this.preDate = new Date();
                    this.preDateStr = dateFormat(this.preDate, this.cycleDatePattern);
                }
            }
        }
        Date currDate = new Date();
        String currDateStr = dateFormat(currDate, this.cycleDatePattern);
        if (!currDateStr.equals(preDateStr)) {
            this.preDate = currDate;
            this.preDateStr = dateFormat(this.preDate, this.cycleDatePattern);
            this.atomicInteger.set(1);
        }
        int seqNo = 0;
        synchronized (lock) {
            this.atomicInteger.compareAndSet(Integer.MAX_VALUE, 1);
            seqNo = this.atomicInteger.getAndIncrement();
            //序列号最大长度
            if((seqNo+"").length()>this.maxSeqNoLen){
                this.atomicInteger.set(1);
                seqNo = this.atomicInteger.getAndIncrement();
            }
        }
        //生成日期格式序列号 yyyyMMddHHmmssSSS
        String dateFormatStr = dateFormat(new Date(),this.formatDatePattern);
        return getSeqPrefix()+dateFormatStr+ StringUtils.leftPad(seqNo + "", this.maxSeqNoLen, "0");
    }


    /**
     * 获取序列号前缀
     * @return
     */
    public abstract String getSeqPrefix();


    private Object lock = new Object();

    /**
     * 格式化日期
     * @param date
     * @param dateStr
     * @return 日期字符串
     */
    String dateFormat(Date date, String dateStr){
        return new SimpleDateFormat(dateStr).format(date);
    }

    /**
     * 上一周期时间字符串
     */
    private volatile String preDateStr;

    /**
     * 序列生成器
     */
    AtomicInteger atomicInteger = new AtomicInteger(1);

    /**
     * 日期循环日期格式
     */
    String cycleDatePattern ="yyyyMMdd";
    /**
     * 最终序列号格式化格式
     */
    public String formatDatePattern ="yyyyMMddHHmmssSSS";
    /**
     * 上一周期时间对象
     */
    Date preDate;

    /**
     * 最大编号长度,可以在子类设置这个值改变序列号长度
     */
    protected int maxSeqNoLen = 10;
}
