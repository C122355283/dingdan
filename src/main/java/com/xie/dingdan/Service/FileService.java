package com.xie.dingdan.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {

    Object AddFile(MultipartFile File, String lei);

}
