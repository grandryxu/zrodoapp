package com.app.common;

/**
 * Created by grandry.xu on 15-11-9.
 * 数据包装对象基本类，包装数据统计中的“检测站点”，“莱克多巴胺”，“沙丁胺醇”，“克伦特罗”，“合计”等信息
 */
public abstract class DataPack {

    private String detectStation;
    private String laiKe;
    private String shaDing;

    public String getDetectStation() {
        return detectStation;
    }

    public String getLaiKe() {
        return laiKe;
    }

    public String getShaDing() {
        return shaDing;
    }

    public String getKeLun() {
        return keLun;
    }

    public String getTotal() {
        return total;
    }

    private String keLun;
    private String total;
}
