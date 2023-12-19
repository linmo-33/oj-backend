package com.linmo.oj.common.utils;

import cn.hutool.core.io.FileUtil;
import com.linmo.oj.common.api.ResultCode;
import com.linmo.oj.common.exception.BusinessException;
import com.linmo.oj.config.MinioConfig;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * TODO
 *
 * @author ljl
 * @since 2023-12-04 15:56
 */
@Slf4j
@Component
public class MinioUtils {
    @Autowired
    private MinioClient client;
    @Autowired
    private MinioConfig minioProp;

    /**
     * 创建bucket
     */
    public void createBucket(String bucketName) {
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
        MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();
        try {
            if (client.bucketExists(bucketExistsArgs))
                return;
            client.makeBucket(makeBucketArgs);
        } catch (Exception e) {
            log.error("创建桶失败：{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传头像
     */
    public String uploadFile(MultipartFile multipartFile, String filePath) throws Exception {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024 * 1024L * 3;
        if (fileSize > ONE_M) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "文件大小不能超过 3M");
        }
        if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
            throw new BusinessException(ResultCode.PARAMS_ERROR, "文件类型错误");
        }
        PutObjectArgs args = PutObjectArgs.builder().bucket(minioProp.getBucketName()).object(filePath)
                .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1).contentType(multipartFile.getContentType()).build();
        //文件名称相同会覆盖
        client.putObject(args);
        return minioProp.getUrl() + "/" + minioProp.getBucketName() + "/" + filePath;
    }
}
