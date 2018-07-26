package com.cowboy.utils.weight;

/**
 * 权重选举工具类,需要进行权重选举的对象需要继承此类
 * @Auther: Tangyinbo
 * @Date: 2018/7/26 22:35
 * @Description:
 */
public class WeightElectHolder {
    /**
     * 权重
     */
    private int weight;

    public WeightElectHolder(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
