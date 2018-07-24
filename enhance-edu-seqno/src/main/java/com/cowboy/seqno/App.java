package com.cowboy.seqno;

/**
 * Hello world!
 */
public class App {
    //private static final  Logger logger = Logger.getLogger(App.class);
    public static void main(String[] args) throws InterruptedException {
        BatchNoSeqno seq1 = new BatchNoSeqno();
        Batch2NoSeqno seq2 = new Batch2NoSeqno();

        System.out.println(seq1.get());
        System.out.println(seq1.get());
        System.out.println(seq1.get());
        System.out.println(seq1.get());
        System.out.println(seq1.get());
        System.out.println(seq1.get());
        System.out.println(seq1.get());
        System.out.println(seq1.get());
        System.out.println(seq1.get());
        System.out.println(seq1.get());
        System.out.println(seq1.get());
        System.out.println(seq1.get());
        System.out.println(seq1.get());
        System.out.println(seq1.get());
        System.out.println(seq1.get());

        System.out.println("----------------------------");
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());
        System.out.println(seq2.get());

        //logger.info("hello tangyinbo");


    }
}
