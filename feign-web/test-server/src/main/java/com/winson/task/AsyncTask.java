/**
 * @description:hystrix-web
 * @author: winsonxu
 * @createTime:2020/6/2
 */
package com.winson.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Future;

@Component
@Slf4j
public class AsyncTask {
    public static Random random = new Random();

    /**
     * 获取作者信息
     *
     * @param authorId 作者ID
     * @return 作者信息
     */
    @Async
    public Future<AuthorDTO> getAuthor(String authorId) {
        try {
            System.out.println("执行线程的名字：" + Thread.currentThread().getName());
            AuthorDTO authorDTO = new AuthorDTO();
            authorDTO.setId(authorId);
            authorDTO.setAuthorName("Winsonxu");
            return new AsyncResult<>(authorDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 异步调用获取文章信息
     *
     * @param authorId
     */

    public void getAuthorDetailAsync(String authorId) {
        //同步调用获取文章信息
        System.out.println("调用文章信息,文章id:" + authorId);
        //异步调用获取作者信息
        Future<AuthorDTO> authorFuture = getAuthor(authorId);
        Map<String, Object> map = new HashMap<>(10);
//        map.put("article", articleDTO);
        try {
            map.put("author", authorFuture.get());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Async
    public void doTaskOne() throws Exception {
        System.out.println("开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务一，耗时：" + (end - start) + "毫秒");
    }

    @Async
    public void doTaskTwo() throws Exception {
        System.out.println("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    @Async
    public void doTaskThree() throws Exception {
        System.out.println("开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        System.out.println("完成任务三，耗时：" + (end - start) + "毫秒");
    }


}


