package com.app.entity;

/**
 * Created by grandry.xu on 15-9-18.
 */
public class DataResult<T> {
    private T data;

    private Exception e;

    public Status status;
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public enum Status{
        OK,ERROR
    }

    public Exception getE() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }
}
