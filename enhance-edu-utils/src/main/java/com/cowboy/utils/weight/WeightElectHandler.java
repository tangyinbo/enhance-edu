package com.cowboy.utils.weight;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 权重选举工具类
 * @Auther: Tangyinbo
 * @Date: 2018/7/26 22:39
 * @Description:
 */
public class WeightElectHandler<E extends WeightElectHolder> {
    private int currentIndex = -1;// 上一次取数角标
    private int currentWeight = 0;// 当前调度的权值
    private int maxWeight = 0; // 最大权重
    private int gcdWeight = 0; //所有服务器权重的最大公约数
    private int serverCount = 0; //总待取数

    private CopyOnWriteArrayList<E> number; //待选举集合
    private boolean isInit;//是否进行了初始化

    private WeightElectHandler(){}

    public static void main(String[] args) {
        CopyOnWriteArrayList<WeightServer> number = new CopyOnWriteArrayList<>();
        number.add(new WeightServer("192.168.100.1",1));
        number.add(new WeightServer("192.168.100.4",4));
        number.add(new WeightServer("192.168.100.8",4));
        WeightElectHandler handler = WeightElectHandler.of(number);

        Map<String,Integer> counts = new HashMap<>();
        for(int i=0;i<100;i++){
            WeightServer server = (WeightServer) handler.get();
            int weight = server.getWeight();

            String key = server.getIp()+":"+server.getWeight();

            if(counts.containsKey(key)){
                counts.put(key,counts.get(key)+1);
            }else{
                counts.put(key,1);
            }
        }

        System.out.println(counts);
    }

    public static <E extends WeightElectHolder> WeightElectHandler of(CopyOnWriteArrayList<E> number){
        if(number == null || number.size() == 0){
            throw new IllegalArgumentException("weight elect number can not be null.");
        }
        WeightElectHandler handler = new WeightElectHandler();
        handler.setNumber(number);
        handler.init();
        return handler;
    }

    /**
     * 添加成员
     */
    public void addNumber(E e){
        checkIsinit();
        this.number.add(e);
        this.init();
    }

    /**
     * 删除成员
     */
    public void removeNumber(E e){
        checkIsinit();
        this.number.remove(e);
        this.init();
    }

    /**
     * 返回最大公约数
     *
     * @param a
     * @param b
     * @return
     */
    protected int gcd(int a, int b) {
        BigInteger b1 = new BigInteger(String.valueOf(a));
        BigInteger b2 = new BigInteger(String.valueOf(b));
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }

    /**
     * 返回所有服务器权重的最大公约数
     *
     * @param serverList
     * @return
     */
    protected int getGCDForServers(List<E> serverList) {
        int w = 0;
        for (int i = 0, len = serverList.size(); i < len - 1; i++) {
            if (w == 0) {
                w = gcd(serverList.get(i).getWeight(), serverList.get(i + 1).getWeight());
            } else {
                w = gcd(w, serverList.get(i + 1).getWeight());
            }
        }
        return w;
    }


    /**
     * 返回所有服务器中的最大权重
     *
     * @param serverList
     * @return
     */
    public int getMaxWeightForServers(List<E> serverList) {
        int w = 0;
        for (int i = 0, len = serverList.size(); i < len - 1; i++) {
            if (w == 0) {
                w = Math.max(serverList.get(i).getWeight(), serverList.get(i + 1).getWeight());
            } else {
                w = Math.max(w, serverList.get(i + 1).getWeight());
            }
        }
        return w;
    }

    /**
     * 算法流程：
     * 假设有一组成员 S = {S0, S1, …, Sn-1}
     * 有相应的权重，变量currentIndex表示上次选择的服务器
     * 权值currentWeight初始化为0，currentIndex初始化为-1 ，当第一次的时候返回 权值取最大的那个服务器，
     * 通过权重的不断递减 寻找 适合的服务器返回，直到轮询结束，权值返回为0
     */
    public E get() {
        while (true) {
            currentIndex = (currentIndex + 1) % serverCount;
            if (currentIndex == 0) {
                currentWeight = currentWeight - gcdWeight;
                if (currentWeight <= 0) {
                    currentWeight = maxWeight;
                    if (currentWeight == 0) {
                        //此场景不会发生
                        return null;
                    }
                }
            }
            if (number.get(currentIndex).getWeight() >= currentWeight) {
                return number.get(currentIndex);
            }
        }
    }



    /**
     * 初始化选举对象
     */
    public void init() {
        isInit = true;
        //上一次选举角标
        currentIndex = -1;
        //当前权重
        currentWeight = 0;
        //成员数目
        serverCount = number.size();
        //获取最大权重
        maxWeight = getMaxWeightForServers(number);
        //获取最大公约数
        gcdWeight = getGCDForServers(number);
    }

    /**
     * 检查是否已初始化
     */
    private void checkIsinit(){
        if(!isInit){
            throw new IllegalStateException("Please first init the object!");
        }
    }

    /**
     * 此方法请不要随意调用
     * @param number
     */
    void setNumber(CopyOnWriteArrayList<E> number) {
        this.number = number;
    }
}
