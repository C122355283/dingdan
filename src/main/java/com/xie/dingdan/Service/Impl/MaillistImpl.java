package com.xie.dingdan.Service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xie.dingdan.Dto.Maillist;
import com.xie.dingdan.Mapper.MaillistMapper;
import com.xie.dingdan.Service.MaillistService;
import com.xie.dingdan.Tool.ExcelUtil;
import com.xie.dingdan.Tool.WordToPInYinUtil;
import com.xie.dingdan.result.JSONResult;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaillistImpl implements MaillistService {
    @Autowired
    MaillistMapper maillistMapper;

    //查询联系人
    @Override
    public Object SelectMaillist(int page, int rows,String kusername){
        PageHelper.startPage(page, rows);
        return JSONResult.success(PageInfo.of(maillistMapper.s_allkehu(kusername)));
    }

    //按名字查询联系人
    @Override
    public Object SelectMaillistbyname(int page, int rows,String name,String kusername){
        PageHelper.startPage(page, rows);
        return JSONResult.success(PageInfo.of(maillistMapper.s_kehuwithname(name,kusername)));
    }

    //添加联系人
    @Override
    public Object InsertMaillist(Maillist maillist){
        if(maillist==null){
            return JSONResult.errorMsg("联系人信息不能为空");
        }
        if(maillistMapper.s_byphoneKusername(maillist.getPhone(),maillist.getKusername())!=null){
            return JSONResult.errorMsg("该联系人已存在");
        }
        maillist.setLianxiang(maillist.getName()+WordToPInYinUtil.ToPinYin(maillist.getName()));
        int count = maillistMapper.i_maillist(maillist);
        if(count==1){
            return JSONResult.success();
        }else {
            return JSONResult.errorMsg("未知错误");
        }
    }

    //导出联系人
    @Override
    public Object DaochuMaillist(String kusername){
        if(kusername==null||kusername.equals("")){
            return JSONResult.errorMsg("未知用户");
        }
        List<Maillist> maillists = maillistMapper.s_allkehu(kusername);
        List<Map> list = new ArrayList<>();
        if(maillists.size()<=0){
            Map<String,String> map= new HashMap<>();
            map.put("昵称","");
            map.put("电话","");
            list.add(map);
            return JSONResult.success(list);
        }

        for (int i =0;i<maillists.size();i++){
            Map<String,String> map= new HashMap<>();
            map.put("昵称",maillists.get(i).getName());
            map.put("电话",maillists.get(i).getPhone());
            list.add(map);
        }
        if(list.size()==maillists.size()){
            return JSONResult.success(list);
        }else {
            return JSONResult.errorMsg("数据错误");
        }
    }

    //导入
    @Override
    public Object DaoruMaillist(MultipartFile multipartFile,String kusername){
        String name=multipartFile.getOriginalFilename();
        String str1=name.substring(0, name.indexOf("."));
        String str2=name.substring(str1.length()+1, name.length());
        Boolean cuowu=false;
        if(!str2.equals("xls")){
            if(!str2.equals("xlsx")){
                return JSONResult.errorMsg("文件格式错误");
            }
        }
        List<Map> list = new ArrayList<>();
        try {
            Workbook workbook = WorkbookFactory.create(multipartFile.getInputStream());
            multipartFile.getInputStream().close();
            list = ExcelUtil.excelToShopIdList(workbook);
        }catch (Exception e){
            cuowu=true;
            System.out.println(e);
        }
        for (int i=0;i<list.size();i++){
            Map<String,String> map = list.get(i);
            Maillist maillist = new Maillist();
            maillist.setName(map.get("昵称"));
            maillist.setPhone(map.get("电话"));
            maillist.setKusername(kusername);
            if(maillistMapper.s_byphoneKusername(maillist.getPhone(),maillist.getKusername())!=null){
                if(maillistMapper.s_byphoneKusername(maillist.getPhone(),maillist.getKusername()).getPhone().equals(maillist.getPhone())){
                    maillist.setId(maillistMapper.s_byphoneKusername(maillist.getPhone(),maillist.getKusername()).getId());
                    maillist.setLianxiang(maillist.getName()+WordToPInYinUtil.ToPinYin(maillist.getName()));
                    if(maillistMapper.u_maillist(maillist)==1){
                        continue;
                    }
                }
            }else {
                maillist.setLianxiang(maillist.getName()+WordToPInYinUtil.ToPinYin(maillist.getName()));
            }
            if(maillistMapper.i_maillist(maillist)==1){

            }else {
                return JSONResult.errorMsg("第"+(i+1)+"行保存错误，请核对表格格式");
            }
        }
        if(!cuowu){
            return JSONResult.success();
        }else {
            return JSONResult.errorMsg("你的excel版本目前解析不了,请更换表格重试，请不要使用导出的表格导入");
        }
    }

    //删除联系人
    @Override
    public Object DeteleMaillist(int id){
        if(id==0){
            return JSONResult.errorMsg("联系人id不能为0");
        }
        int count = maillistMapper.d_maillist(id);
        if(count==1){
            return JSONResult.success();
        }else {
            return JSONResult.errorMsg("未知错误");
        }
    }

    //修改联系人
    @Override
    public Object UpdateMaillist(Maillist maillist){
        if(maillist==null){
            return JSONResult.errorMsg("联系人信息不能为空");
        }
        if(maillistMapper.s_byphoneKusername(maillist.getPhone(),maillist.getKusername())!=null){
            return JSONResult.errorMsg("该联系人已存在");
        }
        maillist.setLianxiang(maillist.getName()+WordToPInYinUtil.ToPinYin(maillist.getName()));
        int count = maillistMapper.u_maillist(maillist);
        if(count==1){
            return JSONResult.success();
        }else {
            return JSONResult.errorMsg("未知错误");
        }
    }

    //输入联想
    public Object SelectAllByNameOrPhone(String nameorphone,String kusername){
        if(nameorphone.equals("")||nameorphone==null){
            return JSONResult.success();
        }else {
            List<Maillist> maillists = new ArrayList<>();
            if(maillistMapper.s_allnameorphone(kusername)==null){
                return JSONResult.success();
            }else {
                maillists=maillistMapper.s_allnameorphone(kusername);
                Map[] mohu = new Map[maillists.size()];
                        for (int i =0;i<maillists.size();i++){
                            Map<String,String> maillistList = new HashMap<>();
                            maillistList.put("label",maillists.get(i).getLianxiang());
                            maillistList.put("rgb",maillists.get(i).getName());
                            maillistList.put("hex",maillists.get(i).getPhone());
                            mohu[i]=maillistList;
                        }
                        return JSONResult.success(mohu);
        }

    }
}
}