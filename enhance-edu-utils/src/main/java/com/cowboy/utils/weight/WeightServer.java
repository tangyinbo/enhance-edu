package com.cowboy.utils.weight;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/7/26 23:08
 * @Description:
 */
public class WeightServer extends WeightElectHolder {
    private String ip;

    public WeightServer(String ip, int weight) {
        super(weight);
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
