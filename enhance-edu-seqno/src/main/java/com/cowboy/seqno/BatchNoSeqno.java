package com.cowboy.seqno;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/4/26 10:19
 * @Description:
 */
public class BatchNoSeqno extends PrefixAbleDateCycleSeqNoGenerator {
    public BatchNoSeqno() {
        this.maxSeqNoLen = 2;
    }

    @Override
    public String getSeqPrefix() {
        return "DO";
    }
}
