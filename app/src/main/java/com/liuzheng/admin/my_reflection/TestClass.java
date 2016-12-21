package com.liuzheng.admin.my_reflection;

import android.util.Log;

/**
 * 作者：刘正
 * 时间：2016/12/21 0021
 * 作用：
 */

public class TestClass {

    @BindAddress("http://www.google.com.cn")
    private String address;

    @BindPort("8888")
    private String port;

    private int number;

    @BindGet("mike")
    void getHttp(String param) {
        String url = "http://www.baidu.com/?username" + param;
        Log.d("MainActivity", "get------->" + url);
    }

    public void printInfo() {
        Log.d("MainActivity", "info is " + address + ":" + port);
    }

    private void myMethod(int number, String sex) {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
