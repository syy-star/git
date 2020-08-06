package com.fh.util;

public enum redis {
    STRING("字符串","string"),
    HASH("hash","hash"),
    LIST("有序集合","list"),
    SET("无序集合","set"),
    ZSET("排序的无序集合","zset");
    //代表英文
    private String en;
    //代表中文
    private String cn;

    private redis(String en,String cn){
        this.cn=cn;
        this.en=en;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }
}
