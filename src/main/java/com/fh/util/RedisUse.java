package com.fh.util;

import com.alibaba.fastjson.JSONObject;
import com.fh.model.Area;
import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisUse {

    public static void set(String key,String value){
        Jedis jedis = RedisUtils.getJedis();
        jedis.set(key,value);
        RedisUtils.returnJedis(jedis);
    }

    public static void set(String key,String value,int seconds){
        Jedis jedis = RedisUtils.getJedis();
        jedis.setex(key,seconds,value);
        RedisUtils.returnJedis(jedis);
    }

    public static String get(String key){
        Jedis jedis = RedisUtils.getJedis();
        String value=jedis.get(key);
        RedisUtils.returnJedis(jedis);
        return value;
    }

    public static void hset(String key,String filed,String value){
        Jedis jedis = RedisUtils.getJedis();
        Long hset = jedis.hset(key, filed, value);
        RedisUtils.returnJedis(jedis);
    }

    public static void hdel(String key,String filed){
        Jedis jedis= RedisUtils.getJedis();
        jedis.hdel(key,filed);
        RedisUtils.returnJedis(jedis);
    }


    public static long hlen(String key){
        Jedis jedis = RedisUtils.getJedis();
        Long hlen = jedis.hlen(key);
        RedisUtils.returnJedis(jedis);
        return hlen;
    }

    public static String hget(String key,String filed){
        Jedis jedis = RedisUtils.getJedis();
        String hget = jedis.hget(key, filed);
        RedisUtils.returnJedis(jedis);
        return hget;
    }

    public static List<String> hvals(String key){
        Jedis jedis = RedisUtils.getJedis();
        List<String> hvals = jedis.hvals(key);
        RedisUtils.returnJedis(jedis);
        return hvals;
    }

    public static  String getAreaNames(String areaIds){
        Jedis jedis= RedisUtils.getJedis();
        StringBuffer sb=new StringBuffer();
        String[] split = areaIds.split(",");
        for (int i = 0; i <split.length ; i++) {
            String areaId = split[i];
            String area = jedis.hget("area", areaId);
            //将json字符串转为对象
            Area areas = JSONObject.parseObject(area, Area.class);
            sb.append(areas.getAreaName()).append(",");
        }
        RedisUtils.returnJedis(jedis);
        return sb.toString();
    }

    public static Boolean exists(String key){
        Jedis jedis= RedisUtils.getJedis();
        Boolean exists = jedis.exists(key);
        RedisUtils.returnJedis(jedis);
        return exists;
    }



}
