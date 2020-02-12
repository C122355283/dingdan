package com.xie.dingdan.Tool;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by F
 * 2018/11/9 17:10
 * 文件上传工具
 */
@Component
public class FileUtils {

    @Value("${web.upload-path}")//读取配置里的路径名
    private String uploadPath;

//     private static final Logger logger = LoggerFactory.getLogger(CentralController.class);

    public void createUploadDir(String modelName) {
        //绝对路径名
        String dirPath = uploadPath + modelName;
        File dirFile = new File(dirPath);
        if(!dirFile.exists()) {
            dirFile.mkdirs();
        }
    }

    public static String deleteFile(String path){
        File Oldfile = new File(path);
        if(Oldfile.exists()){
            Oldfile.delete();
            return "true";
        }
        return "false";
    }



    public String FileName(MultipartFile file){
        String REGEX_CHINESE = "[\u4e00-\u9fa5]";

        //获取文件名
        String fileName = file.getOriginalFilename();
        //去除中文
        Pattern pat = Pattern.compile(REGEX_CHINESE);
        Matcher mat = pat.matcher(fileName);
        fileName=mat.replaceAll("");
        //获取文件名后缀
        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));
        //生成新的文件名
        String newPath = "/" + UUID.randomUUID().toString().replace("-","");

        return newPath;
    }


//    public String AddFolder(String Folder){
//
//
//
//    }




    /**
     * 保存文件，并返回文件路径名
     * @param file
     * @return
     */
    public String upload(MultipartFile file, String targetFileName, String modelName){

        //创建模块目录
        this.createUploadDir(modelName);

        //创建文件路径
        String returnFile = modelName +  targetFileName;
        String destFilePath = this.uploadPath + returnFile;
        File dest = new File(destFilePath);

        //保存文件
        try {
            file.transferTo(dest);//将上传文件上传到服务器上指定的文件
            return returnFile;
        }
        catch (IllegalStateException e) {
            // 该异常表示，当前对客户端的响应已经结束，不能在响应已经结束（或说消亡）后再向
            //客户端（实际上是缓冲区）输出任何内容。
            e.printStackTrace();
            return returnFile;
        } catch (IOException e) {
            e.printStackTrace();
            return returnFile;
        }
    }


    /**
     * 读取文件
     * @param filePath
     * @return
     */
    public BufferedInputStream fileRead(String filePath){
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bis;
    }

    public String getUploadFile() {
        return this.uploadPath;
    }
}
