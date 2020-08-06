package com.fh.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {

    private static JedisPool jedisPool = null;

    private RedisPool(){

    }

    private static void initRedisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMinIdle(500);
        jedisPoolConfig.setMaxIdle(500);
        jedisPool = new JedisPool(jedisPoolConfig, "192.168.78.137", 6379);
    }


    static{
        initRedisPool();
    }

    public static Jedis getResource(){
        return jedisPool.getResource();
    }
}

