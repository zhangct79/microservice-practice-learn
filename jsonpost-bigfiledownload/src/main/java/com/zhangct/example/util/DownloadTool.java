package com.zhangct.example.util;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * Created by yangzheng03 on 2018/1/16. https://www.dubby.cn/
 */
@Component
@Slf4j
public class DownloadTool {

    /**
     * 使用自定义的httpclient的restTemplate
     */
    @Resource(name = "httpClientTemplate")
    private RestTemplate httpClientTemplate;

    @Resource
    private WebFileUtils webFileUtils;

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

    private static final int ONE_KB_SIZE = 1024;
    /**
     * 大于20M的文件视为大文件,采用流下载
     */
    private static final int BIG_FILE_SIZE = 20 * 1024 * 1024;
    private static String prefix = String.valueOf(System.currentTimeMillis());


    public void downloadByMultiThread(String url, String targetPath, Integer threadNum) {

        long startTimestamp = System.currentTimeMillis();
        //开启线程
        threadNum = threadNum == null ? MIN_POOL_SIZE : threadNum;
        Assert.isTrue(threadNum > 0, "线程数不能为负数");
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("http-down-%d").build();
        threadPool = new ThreadPoolExecutor(
                threadNum, MAX_POOL_SIZE, 0, TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(WAIT_QUEUE_SIZE), factory);
        boolean isBigFile;

        //调用head方法,只获取头信息,拿到文件大小
        long contentLength = httpClientTemplate.headForHeaders(url).getContentLength();
        Assert.isTrue(contentLength > 0,"获取文件大小异常");
        isBigFile = contentLength >= BIG_FILE_SIZE;

        if (contentLength > 1024 * ONE_KB_SIZE) {
            log.info("[多线程下载] Content-Length\t{} ({})", contentLength, (contentLength / 1024 / 1024) + "MB");
        } else if (contentLength > ONE_KB_SIZE) {
            log.info("[多线程下载] Content-Length\t{} ({})", contentLength, (contentLength / 1024) + "KB");
        } else {
            log.info("[多线程下载] Content-Length\t" + (contentLength) + "B");
        }


        ArrayList<CompletableFuture<DownloadTemp>> futures = Lists.newArrayListWithCapacity(threadNum);
        String fileFullPath;
        RandomAccessFile resultFile;
        try {
            fileFullPath = webFileUtils.getAndCreateDownloadDir(url, targetPath);
            //创建目标文件
            resultFile = new RandomAccessFile(fileFullPath, "rw");
            log.info("[多线程下载] Download started, url:{}\tfileFullPath:{}", url, fileFullPath);

            //每个线程下载的大小
            long tempLength = (contentLength - 1) / threadNum + 1;
            long start, end;

            int totalSize = 0;
            for (int i = 0; i < threadNum && totalSize < contentLength; ++i) {
                //累加
                start = i * tempLength;
                end = start + tempLength - 1;
                totalSize += tempLength;
                log.info("[多线程下载] start:{}\tend:{}", start, end);

                DownloadThread thread = new DownloadThread(httpClientTemplate, i, start, end, url, fileFullPath, isBigFile);
                CompletableFuture<DownloadTemp> future = CompletableFuture.supplyAsync(thread::call, threadPool);
                futures.add(future);
            }
        } catch (Exception e) {
            log.error("[多线程下载] 下载出错", e);
            return;
        } finally {
            threadPool.shutdown();
        }

        //合并文件
        futures.forEach(f -> {
            try {
                f.thenAccept(o -> {
                    try {
                        log.info("[多线程下载] {} 开始合并,文件:{}", o.threadName, o.filename);
                        RandomAccessFile tempFile = new RandomAccessFile(o.filename, "rw");
                        tempFile.getChannel().transferTo(0, tempFile.length(), resultFile.getChannel());
                        tempFile.close();
                        File file = new File(o.filename);
                        boolean b = file.delete();
                        log.info("[多线程下载] {} 删除临时文件:{}\t结果:{}", o.threadName, o.filename, b);
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.error("[多线程下载] {} 合并出错", o.threadName, e);
                    }
                }).get();
            } catch (Exception e) {
                log.error("[多线程下载] 合并出错", e);
            } finally {
                threadPool.shutdown();
            }
        });
        try {
            resultFile.close();
        } catch (IOException e) {
            log.error("关闭文件流失败: ",e);
        }

        long completedTimestamp = System.currentTimeMillis();
        log.info("=======下载完成======,耗时{}",
                isBigFile ? (completedTimestamp - startTimestamp) / 1000 + "s" : (completedTimestamp - startTimestamp) + "ms");
    }

    public static class DownloadThread implements Callable<DownloadTemp> {

        private int index;
        private String filePath;
        private long start, end;
        private String urlString;
        private RestTemplate httpClientTemplate;
        private boolean isBigFile;

        DownloadThread(RestTemplate restTemplate, int index, long start, long end, String url, String fileFullPath, boolean isBigFile) {
            this.httpClientTemplate = restTemplate;
            this.urlString = url;
            this.index = index;
            this.start = start;
            this.end = end;
            this.isBigFile = isBigFile;
            Assert.hasText(fileFullPath, "文件下载路径不能为空");
            this.filePath = String.format("%s-%s-%d", fileFullPath, prefix, index);
        }

        @Override
        public DownloadTemp call() {
            //定义请求头的接收类型
            try {
                if (isBigFile) {
                    downloadBigFile();
                } else {
                    downloadLittleFile();
                }
            } catch (Exception e) {
                log.error("[线程下载] 下载失败:", e);
            }
            DownloadTemp downloadTemp = new DownloadTemp();
            downloadTemp.index = index;
            downloadTemp.filename = filePath;
            downloadTemp.threadName = Thread.currentThread().getName();
            log.info("[线程下载] \tcompleted.");
            return downloadTemp;
        }

        /**
         * 下载小文件
         *
         * @throws IOException
         */
        private void downloadLittleFile() throws IOException {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.RANGE, "bytes=" + start + "-" + end);
            headers.setAccept(Collections.singletonList(MediaType.ALL));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            ResponseEntity<byte[]> rsp = httpClientTemplate.exchange(urlString, HttpMethod.GET, new HttpEntity<>(headers), byte[].class);
            log.info("[线程下载] 返回状态码:{}", rsp.getStatusCode());
            Files.write(Paths.get(filePath), Objects.requireNonNull(rsp.getBody(), "未获取到下载文件"));
        }

        /**
         * 下载大文件
         *
         * @throws IOException
         */
        private void downloadBigFile() {
            RequestCallback requestCallback = request -> {
                HttpHeaders headers = request.getHeaders();
                headers.set(HttpHeaders.RANGE, "bytes=" + start + "-" + end);
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            };
            // getForObject会将所有返回直接放到内存中,使用流来替代这个操作
            ResponseExtractor<Void> responseExtractor = response -> {
                // Here I write the response to a file but do what you like
                Files.copy(response.getBody(), Paths.get(filePath));
                log.info("[线程下载] 返回状态码:{}", response.getStatusCode());
                return null;
            };
            httpClientTemplate.execute(urlString, HttpMethod.GET, requestCallback, responseExtractor);
        }
    }

    private static class DownloadTemp {
        private int index;
        private String filename;
        private String threadName;
    }
}