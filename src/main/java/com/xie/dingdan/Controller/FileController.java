package com.xie.dingdan.Controller;



import com.xie.dingdan.Service.FileService;
import com.xie.dingdan.annotation.UserLoginToken;
import com.xie.dingdan.result.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api("文件添加")
@CrossOrigin
@RestController
@RequestMapping("/File")
public class FileController {
    @Autowired
    FileService fileService;


    @ApiOperation(value = "文件上传",notes = "上传文件及类名")
    @PostMapping("/AddFile")
    @UserLoginToken
    public Object AddFile(@RequestParam("picture")MultipartFile File, @RequestParam("lei") String lei){

        if(File==null||lei.equals("")){
            return JSONResult.errorMsg("文件或类别不能为空");
        }
        return fileService.AddFile(File,lei);

    }



}
