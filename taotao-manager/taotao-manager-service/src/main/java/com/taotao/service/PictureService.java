package com.taotao.service;

import com.taotao.common.pojo.PictureResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.WebServiceClient;

/**
 * Created by Administrator on 2017/3/27.
 */
public interface PictureService {
    PictureResult uploadPic(MultipartFile picFile);
}
