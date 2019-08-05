package com.leyou.upload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * UploadImageService
 * 上传图片服务接口
 *
 * @author xieweisong
 * @date 2019/8/3 12:12
 */
public interface UploadImageService {
    /**
     * 上传图片
     * @param multipartFile 图片
     * @return
     */
    String uploadImage(MultipartFile multipartFile);
}
