package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by 帅虎的电脑 on 2019/1/27.
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
