package com.taotao.service.impl;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.FastDFSClient;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传service
 * Created by Administrator on 2017/3/27.
 */
@Service
public class PictureServiceImpl implements PictureService{

    @Value("${IMAGE_SERVER_BASE_URL}")
    private String IMAGE_SERVER_BASE_URL;

    @Override
    public PictureResult uploadPic(MultipartFile picFile) {
        //判断图片是否为空
        PictureResult result = new PictureResult();
        if(picFile.isEmpty()){
            result.setError(1);
            result.setMessage("图片不存在！");
        }
        //上传到图片服务器
        try {
            //取图片扩展名
            String originaFilename = picFile.getOriginalFilename();
            //扩展名不要“.”
            String extName = originaFilename.substring(originaFilename.indexOf(".")+1);
            FastDFSClient client = new FastDFSClient("classpath:properties/client.conf");
            String url = client.uploadFile(picFile.getBytes(), extName);
           //拼接图片服务器的IP地址
            url = IMAGE_SERVER_BASE_URL + url;
            //把URL响应给客户端
            result.setError(0);
            result.setUrl(url);
        } catch (Exception e) {
            result.setError(1);
            result.setMessage("图片上传失败！");
        }
        return result;
    }
}
