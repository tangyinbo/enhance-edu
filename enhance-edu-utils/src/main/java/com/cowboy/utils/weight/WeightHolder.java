package com.cowboy.utils.weight;

/**
 * 权重选举工具类,需要进行权重选举的对象需要继承此类
 *
 * @Auther: Tangyinbo
 * @Date: 2018/7/26 22:35
 * @Description:
 */
public class WeightHolder {
    /**
     * 权重为0不会执行
     */
    private int weight;
    /**
     * 权重处理器
     */
    WeightElectScheduleHandler weightElectScheduleHandler;

    public WeightHolder(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    /**
     * 可动态修改选举对象权重
     *
     * @param weight
     */
    public void setWeight(int weight) {
        if (weightElectScheduleHandler == null) {
            throw new IllegalStateException("请先初始化权重处理器");
        }
        this.weight = weight;
        //如果权重有修改,需重新初始化权重选举调度
        if (this.weight != weight) {
            weightElectScheduleHandler.init();
        }

    }

    public WeightElectScheduleHandler getWeightElectScheduleHandler() {
        return weightElectScheduleHandler;
    }

    public void setWeightElectScheduleHandler(WeightElectScheduleHandler weightElectScheduleHandler) {
        this.weightElectScheduleHandler = weightElectScheduleHandler;
    }
}
