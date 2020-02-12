package com.xie.dingdan.Service.Impl;


import com.xie.dingdan.Service.FileService;
import com.xie.dingdan.Tool.FileUtils;
import com.xie.dingdan.result.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileImpl implements FileService {

    @Autowired
    FileUtils fileUtils;


    //添加图片
    @Override
    public Object AddFile(MultipartFile File, String lei) {

        String Filename=fileUtils.FileName(File);

        String FilePath=fileUtils.upload(File,Filename,lei);

        if(FilePath!=null||!FilePath.equals("")){
            return JSONResult.success(FilePath);
        }

        return JSONResult.errorMsg("未知错误");

    }
}




