package com.xie.dingdan.Controller;

import com.xie.dingdan.Dto.Maillist;
import com.xie.dingdan.Dto.User;
import com.xie.dingdan.Service.MaillistService;
import com.xie.dingdan.annotation.UserLoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Api("联系人")
@CrossOrigin
@RestController
@RequestMapping("/Maillist")
public class MaillistController {
    @Autowired
    MaillistService maillistService;

    @ApiOperation(value = "查询联系人",notes = "查询联系人")
    @PostMapping("/SelectMaillist")
    @UserLoginToken
    public Object SelectMaillist(@RequestParam() int page,@RequestParam(defaultValue = "10") int rows,
                                 @RequestParam() String kusername){
        int pages = 1;
        if(page==0||page==1){
            return maillistService.SelectMaillist(pages,rows,kusername);
        }else {
            return maillistService.SelectMaillist(page,rows,kusername);
        }

    }

    @ApiOperation(value = "名字条件查询联系人",notes = "用名字条件查询联系人")
    @PostMapping("/SelectMaillistbyname")
    @UserLoginToken
    public Object SelectMaillistbyname(@RequestParam() int page,@RequestParam(defaultValue = "10") int rows,
                                       @RequestParam() String name,@RequestParam() String kusername){
        return maillistService.SelectMaillistbyname(page, rows, name,kusername);
    }

    @ApiOperation(value = "导出联系人",notes = "导出联系人")
    @PostMapping("/DaochuMaillist")
    @UserLoginToken
    public Object DaochuMaillist(@RequestParam() String kusername){
        return maillistService.DaochuMaillist(kusername);
    }

    @ApiOperation(value = "导入联系人",notes = "导入联系人")
    @PostMapping("/DaoruMaillist")
    @UserLoginToken
    public Object DaoruMaillist(@RequestParam("file")MultipartFile file, @RequestParam() String kusername){
        return maillistService.DaoruMaillist(file,kusername);
    }

    @ApiOperation(value = "添加联系人",notes = "通过联系人实体类添加联系人")
    @PostMapping("/InsertMaillist")
    @UserLoginToken
    public Object InsertMaillist(@Valid @RequestBody Maillist maillist){
        return maillistService.InsertMaillist(maillist);
    }

    @ApiOperation(value = "删除联系人",notes = "通过id删除联系人")
    @PostMapping("/DeteleMaillist")
    @UserLoginToken
    public Object DeteleMaillist(@RequestParam() int id){
        return maillistService.DeteleMaillist(id);
    }

    @ApiOperation(value = "修改联系人",notes = "通过联系人实体类修改联系人")
    @PostMapping("/UpdateMaillist")
    @UserLoginToken
    public Object UpdateMaillist(@RequestBody Maillist maillist){
        return maillistService.UpdateMaillist(maillist);
    }

    @ApiOperation(value = "输入联想",notes = "通过字段模糊搜索实现输入联想")
    @PostMapping("/SelectAllByNameOrPhone")
    public Object SelectAllByNameOrPhone(@RequestParam() String keyword,@RequestParam() String kusername){
        return maillistService.SelectAllByNameOrPhone(keyword,kusername);
    }

}
