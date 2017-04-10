package org.maiya.free.utils;

import org.n3r.diamond.client.Miner;
import org.n3r.diamond.client.Minerable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.util.JedisClusterCRC16;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by beck on 2016/12/12.
 */

public class RedisCluster {
    static Logger log = LoggerFactory.getLogger(Redis.class);
    static JedisCluster jedisCluster;

    static {
        Minerable config = new Miner().getMiner("fault", "default");
        Set<HostAndPort> nodes = new HashSet<>();
        String[] nodeArr = config.getString("redis.hosts").split("\\;");
        for (String node : nodeArr) {
            String[] ipPort = node.split("\\:");
            nodes.add(new HostAndPort(ipPort[0], Integer.valueOf(ipPort[1])));
        }
        log.info("redis的node为: {}", nodes.toString());
        jedisCluster = new JedisCluster(nodes, 1000, 5000);
    }

    public static void set(String key, String value) {
        try {
            jedisCluster.set(key, value);
            log.debug("set cache key={},value={},slot={}", key, value, JedisClusterCRC16.getSlot(key));
        } catch (Exception e) {
            log.error("=============redis error{}", e);
        }

    }

    public static void setex(String key, String value, long expireTime, TimeUnit timeUnit) {
        try {
            jedisCluster.setex(key, (int) timeUnit.toSeconds(expireTime), value);
            log.debug("setWithExpireTime cache key={},value={},expireTime={},slot={}", key, value, expireTime, JedisClusterCRC16.getSlot(key));
        } catch (Exception e) {
            log.error("=============redis error{}", e);
        }

    }

    public static Long incr(String key) {
        try {
            return jedisCluster.incr(key);
        } catch (Exception e) {
            log.error("=============redis error{}", e);
            return null;
        }

    }

    public static Long incrBy(String key, long incrBy) {
        try {
            return jedisCluster.incrBy(key, incrBy);
        } catch (Exception e) {
            log.error("=============redis error{}", e);
            return null;
        }
    }

    public static String get(String key, String defaultValue) {
        try {
            String value = get(key);
            log.info("======redis get{}", value);
            return value == null ? defaultValue : value;
        } catch (Exception e) {
            log.error("=============redis error{}", e);
            return null;
        }
    }

    public static String get(String key) {
        try {
            String value = jedisCluster.get(key);
            log.info("======redis get{}=====", value);
            return jedisCluster.get(key);
        } catch (Exception e) {
            log.error("=============redis error{}", e);
            return null;
        }

    }

    public static Long del(String key) {
        try {
            return jedisCluster.del(key);
        } catch (Exception e) {
            log.error("=============redis error{}", e);
            return null;
        }
    }

    public static void expire(String key, long expireValue, TimeUnit timeUnit) {
        try {
            jedisCluster.expire(key, (int) timeUnit.toSeconds(expireValue));
        } catch (Exception e) {
            log.error("=============redis error{}", e);
        }
    }

}
