package com.leyou.upload.controller;

import com.leyou.upload.service.UploadImageService;
import com.leyou.upload.service.UploadImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * UploadController
 *
 * @author xieweisong
 * @date 2019/8/3 11:10
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadImageService uploadImageService;

    /**
     * 上传图片
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile multipartFile) {
        String url=uploadImageService.uploadImage(multipartFile);
        return ResponseEntity.ok(url);
    }
}
