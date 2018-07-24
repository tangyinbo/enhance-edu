package com.cowboy.seqno;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/4/26 10:21
 * @Description:
 */
public class Batch2NoSeqno extends PrefixAbleDateCycleSeqNoGenerator {
    @Override
    public String getSeqPrefix() {
        return "MO";
    }
}
