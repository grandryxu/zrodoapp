package com.app.entity;

/**
 * Created by grandry.xu on 15-9-18.
 */
public class Update {
    private String versionCode;
    private boolean isUpdate;

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public String getApkDownloadUrl() {
        return apkDownloadUrl;
    }

    public void setApkDownloadUrl(String apkUrl) {
        this.apkDownloadUrl = apkUrl;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    private String apkDownloadUrl;
}
