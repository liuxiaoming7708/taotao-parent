package com.taotao.fastdfs;



import com.taotao.common.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * 4.6	图片服务的使用
 官方提供一个jar包
 使用方法：
 1、把FastDFS提供的jar包添加到工程中
 2、初始化全局配置。加载一个配置文件。
 3、创建一个TrackerClient对象。
 4、创建一个TrackerServer对象。
 5、声明一个StorageServer对象，null。
 6、获得StorageClient对象。
 7、直接调用StorageClient对象方法上传文件即可。
 * Created by Administrator on 2017/3/24.
 */

public class FastdfsTest {
        @Test
        public void testUpload() throws Exception{
            //1、把FastDFS提供的jar包添加到工程中
            //2、初始化全局配置。加载一个配置文件。
            ClientGlobal.init("D:\\masterSpring\\taotao-parent\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\properties\\client.conf");
            // 3、创建一个TrackerClient对象。
            TrackerClient trackerClient = new TrackerClient();
            // 4、创建一个TrackerServer对象。(建立连接)
            TrackerServer trackerServer = trackerClient.getConnection();
            // 5、声明一个StorageServer对象，null。
            StorageServer storageServer = null;
            // 6、获得StorageClient对象。
            StorageClient storageClient =new StorageClient(trackerServer,storageServer);
            // 7、直接调用StorageClient对象方法上传文件即可。
            String[] strings = storageClient.upload_file("C:\\Users\\Administrator\\Desktop\\bird.jpg", "jpg", null);
            for (String string :strings){
                System.out.println(string);
            }

        }
    @Test
    public void testFastDfsClient() throws Exception{
        FastDFSClient client1 = new FastDFSClient("D:\\masterSpring\\taotao-parent\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\properties\\client.conf");
        String uploadFile = client1.uploadFile("C:\\Users\\Administrator\\Desktop\\qie.jpg","jpg");
        System.out.println(uploadFile);
    }



}
