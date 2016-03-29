package com.app.bean;

/**
 * Created by lenovo on 2016-02-24.
 */
public class BaseSample {
    private String sId;
    private String addr;
    private String addrLati;
    private String addrLongi;
    private String name;
    private String time;
    private String temperature;
    private String source;
    private String phontoBase64;

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddrLati() {
        return addrLati;
    }

    public void setAddrLati(String addrLati) {
        this.addrLati = addrLati;
    }

    public String getAddrLongi() {
        return addrLongi;
    }

    public void setAddrLongi(String addrLongi) {
        this.addrLongi = addrLongi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String date) {
        this.time = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPhontoBase64() {
        return phontoBase64;
    }

    public void setPhontoBase64(String phontoBase64) {
        this.phontoBase64 = phontoBase64;
    }
}
