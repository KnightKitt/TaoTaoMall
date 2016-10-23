package com.taotao.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class RedisService {
    
    @Autowired(required = false) //将RedisService放入到common工程中后会对common工程的使用有要求，必须存在需要注入的对象。因此，将待注入的属性设置为非必须的
                                 //从Spring容器中查找bean，找到就注入，找不到就忽略。
    private ShardedJedisPool shardedJedisPool;
    
    private <T> T execute(Function<T, ShardedJedis> fun){
        ShardedJedis shardedJedis = null;
        try {
            // 从连接池中获取到jedis分片对象
            shardedJedis = shardedJedisPool.getResource();
            return fun.callback(shardedJedis);
        } finally {
            if (null != shardedJedis) {
                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                shardedJedis.close();
            }
        }
    }
    
    /**
     * 执行set操作(优化)
     * 
     * @param key
     * @param value
     * @return
     */
    public String set(final String key, final String value){
        return this.execute(new Function<String, ShardedJedis>() {

            @Override
            public String callback(ShardedJedis e) {
                return e.set(key, value);
            }
            
        });
    }

    /**
     * 执行get操作(优化)
     * 
     * @param key
     * @return
     */
    public String get(final String key){
        return this.execute(new Function<String, ShardedJedis>() {
            
            @Override
            public String callback(ShardedJedis e) {
                return e.get(key);
            }
            
        });
    }
    
    /**
     * 执行del操作(优化)
     * 
     * @param key
     * @return
     */
    public Long del(final String key){
        return this.execute(new Function<Long, ShardedJedis>() {
            
            @Override
            public Long callback(ShardedJedis e) {
                return e.del(key);
            }
            
        });
    }
    
    /**
     * 设置生存时间
     * 
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(final String key, final Integer seconds){
        return this.execute(new Function<Long, ShardedJedis>() {
            
            @Override
            public Long callback(ShardedJedis e) {
                return e.expire(key, seconds);
            }
            
        });
    }
    
    
    /**
     * 执行set操作并且设置生存时间，单位为：秒(优化)
     * 
     * @param key
     * @param value
     * @return
     */
    public String set(final String key, final String value, final Integer seconds){
        return this.execute(new Function<String, ShardedJedis>() {
            @Override
            public String callback(ShardedJedis e) {
                String str = e.set(key, value);
                e.expire(key, seconds);
                return str;
            }
        });
    }
    
    /**
     * 执行set操作
     * 
     * @param key
     * @param value
     * @return
     */
//    public String set(String key, String value){
//        ShardedJedis shardedJedis = null;
//        try {
//            // 从连接池中获取到jedis分片对象
//            shardedJedis = shardedJedisPool.getResource();
//            return shardedJedis.set(key, value);
//        } finally {
//            if (null != shardedJedis) {
//                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
//                shardedJedis.close();
//            }
//        }
//    }

    /**
     * 执行get操作
     * 
     * @param key
     * @return
     */
//    public String get(String key){
//        ShardedJedis shardedJedis = null;
//        try {
//            // 从连接池中获取到jedis分片对象
//            shardedJedis = shardedJedisPool.getResource();
//            return shardedJedis.get(key);
//        } finally {
//            if (null != shardedJedis) {
//                // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
//                shardedJedis.close();
//            }
//        }
//    }
}
