package com.cowboy.seqno;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 周期序列生成器
 *
 * @Auther: Tangyinbo
 * @Date: 2018/4/13 13:11
 * @Description:
 */
public enum DateTimeCycleSerial implements KySeqNoGenerator {

    /**
     * 天循环,默认号最大长度为10,序列号最终长度为27,如:201804260955484970000000010
     *
     */
    DAY_CYCLE("yyyyMMdd", "yyyyMMddHHmmssSSS",(Integer.MAX_VALUE + "").length()) {
        /**
         * 上一周期时间字符串
         */
        private volatile String preDateStr;

        @Override
        public String get() {
            if (this.preDate == null) {
                synchronized (DAY_CYCLE) {
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
            synchronized (DAY_CYCLE) {
                this.atomicInteger.compareAndSet(Integer.MAX_VALUE, 1);
                seqNo = this.atomicInteger.getAndIncrement();
            }
            //生成日期格式序列号 yyyyMMddHHmmssSSS
            String dateFormatStr = dateFormat(new Date(),this.formatDatePattern);
            return dateFormatStr+StringUtils.leftPad(seqNo + "", this.maxSeqNoLen, "0");
        }
    };

    /**
     *
     * @param cycleDatePattern  循环序列化日期格式
     * @param formatDatePattern 序列号日期格式
     * @param maxSeqNoLen 序列化最大长度
     */
    DateTimeCycleSerial(String cycleDatePattern,String formatDatePattern, int maxSeqNoLen) {
        this.cycleDatePattern = cycleDatePattern;
        this.formatDatePattern = formatDatePattern;
        this.maxSeqNoLen = maxSeqNoLen;
    }

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
     * 序列生成器
     */
    AtomicInteger atomicInteger = new AtomicInteger(1);

    /**
     * 日期循环日期格式
     */
    String cycleDatePattern;
    /**
     * 最终序列号格式化格式
     */
    String formatDatePattern;
    /**
     * 上一周期时间对象
     */
    Date preDate;

    /**
     * 最大编号长度
     */
    int maxSeqNoLen = 10;


}
