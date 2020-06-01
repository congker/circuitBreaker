/**
 * @description:hystrix-web
 * @author: winsonxu
 * @createTime:2020/6/1
 */
package com.winson.ribbon;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyRibbon {
    @Autowired
    private DiscoveryClient discoveryClient;
    private Map<String, AtomicInteger> reqCountMap = new ConcurrentHashMap<>();

    /**
     * 采用双重检查加锁的方式，创建 reqCount 对象，保证不出现线程安全问题，同时能较快创建对象
     **/
    private AtomicInteger getRegCount(String serverId) {
        AtomicInteger reqCount = reqCountMap.get(serverId);
        if (reqCount == null) {
            synchronized (reqCountMap) {
                reqCount = reqCountMap.get(serverId);
                if (reqCount == null) {
                    reqCount = new AtomicInteger(-1);
                    reqCountMap.put(serverId, reqCount);
                }
            }
        }
        return reqCount;
    }

    /**
     * 获取服务列表，并通过负载均衡选择一个服务地址返回
     **/
    private String getInstances(String serverId) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serverId);
        if (instances == null || instances.size() == 0) {
            return null;
        }
        // 1. 服务集群总数
        int serverCount = instances.size();
        // 2. 轮询算法：通过总请求数与服务集群总数进行取模
        AtomicInteger reqCount = getRegCount(serverId);
        int serviceIndex = reqCount.addAndGet(1) % serverCount;
        return instances.get(serviceIndex).getUri().toString();
    }

    private String doGet(String url) {
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        HttpGet httpGet = new HttpGet(url);

        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            System.out.println("响应状态为:" + response.getStatusLine());
            if (responseEntity != null) {
                String res = EntityUtils.toString(responseEntity);
                System.out.println("响应内容长度为:" + responseEntity.getContentLength());
                System.out.println("响应内容为:" + res);
                return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "error";
    }

    /**
     * 模拟 Ribbon 调用接口
     *
     * @param serverId
     * @param url
     * @return
     */
    public String ribbonGet(String serverId, String url) {
        // 1.获取服务名称对应服务列表
        String host = getInstances(serverId);
        if (host == null) {
            return "No instances available for zookeeper-member-server";
        }
        url = host + url;
        return doGet(url);
    }
}
