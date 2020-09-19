package com.zhangct.example.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

//@Component

//@SpringBootTest(classes = HttpApplication.class)
//@TestPropertySource("classpath:application.yml")

@SpringBootTest
@Slf4j
class WebFileUtilsTest {
    /**
     * 线程最小值
     */
    private static final int MIN_POOL_SIZE = 10;
    /**
     * 线程最大值
     */
    private static final int MAX_POOL_SIZE = 100;
    /**
     * 等待队列大小
     */
    private static final int WAIT_QUEUE_SIZE = 1000;
    /**
     * 线程池
     */
    private static ExecutorService threadPool;

    @Resource
    private WebFileUtils webFileUtils;

    @Resource
    private DownloadTool downloadTool;

    @Test
    void downloadLittleFileToPath1() {
        String path = "/Users/zhangct/下载/";
        String url = "https://download.jetbrains.8686c.com/idea/ideaIC-2020.2.2.dmg";
//        webFileUtils.downloadLittleFileToPath(url,path);
        webFileUtils.downloadBigFileToPath(url,path);
//        downloadTool.downloadByMultiThread(url,path,50);
    }

}