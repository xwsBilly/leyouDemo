package com.leyou.upload.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * UploadImageService
 * 上传图片服务实现
 *
 * @author xieweisong
 * @date 2019/8/3 12:11
 */
@Service
@Slf4j
public class UploadImageServiceImpl implements UploadImageService {
    public static final List<String> ALLOW_TYPES = Arrays.asList("image/jpeg", "image/png", "image/jpg", "image/bmp");

    @Override
    public String uploadImage(MultipartFile multipartFile) {
        try {
            // 校验文件类型
            String contentType = multipartFile.getContentType();
            if (!ALLOW_TYPES.contains(contentType)) {
                throw new MyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            // 校验文件内容
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            if (image==null||image.getHeight()<=0||image.getWidth()<=0){
                throw new MyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            File dest = new File("C://Users/Billy/IdeaProjects/leyou/upload/", Objects.requireNonNull(multipartFile.getOriginalFilename()));
            // 保存文件到本地
            multipartFile.transferTo(dest);
            // 返回路径
            return "http://image.leyou.com/"+multipartFile.getOriginalFilename();

        } catch (IOException e) {
            log.error("上传图片失败!", e);
            throw new MyException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }
    }
}
