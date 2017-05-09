package com.taotao.jedis;

import com.taotao.rest.component.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/4/6.
 */
public class JedisTest {
    //单机版测试
    @Test
    public void testJedisSingle() throws Exception{
        //创建一个Jedis对象
        Jedis jedis = new Jedis("172.30.101.129",6379);
        jedis.set("test","Hello Jedis");
        String string = jedis.get("test");
        System.out.println(string);
        jedis.close();
    }
    @Test
    public void testJedisPool() throws Exception{
        //创建一个连接池对象
        //系统中应该是单例的
        JedisPool jedisPool = new JedisPool("172.30.101.129",6379);
        //从连接池中获得一个连接
        Jedis jedis = jedisPool.getResource();
        String string = jedis.get("test");
        System.out.println(string);
        //jedis必须关闭
        jedis.close();
        //系统关闭时关闭连接池
        jedisPool.close();
    }


    //连接redis集群
    @Test
    public void testJedisCluster() throws Exception{
        //创建一个Jediscluster对象
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("172.30.101.129",7001));
        nodes.add(new HostAndPort("172.30.101.129",7002));
        nodes.add(new HostAndPort("172.30.101.129",7003));
        nodes.add(new HostAndPort("172.30.101.129",7004));
        nodes.add(new HostAndPort("172.30.101.129",7005));
        nodes.add(new HostAndPort("172.30.101.129",7006));
        //在nodes中指定每个节点的地址
        //JedisCluster在系统中是单例的
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("name","zhangSan");
        jedisCluster.set("value","100");

        System.out.println(jedisCluster.get("name"));
        System.out.println(jedisCluster.get("value"));

        //当系统关闭时关闭
        jedisCluster.close();
    }

    //直接从spring配置文件中获取Jedis单机版对象
    @Test
    public void testJedisClientSpring() throws Exception{
        //创建一个spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        //从容器中取得jedisClient对象
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        //jedisClient操作Jedis
        jedisClient.set("client1","1000");
        System.out.println(jedisClient.get("client1"));
    }
}
