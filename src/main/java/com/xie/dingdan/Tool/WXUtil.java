//package com.xie.dingdan.Tool;
//
//public class WXUtil {
//
//    public function getAccessToken(){
//
//        //指定保存文件位置
//        if(!is_dir('./access_token/')){
//            mkdir(iconv("GBK","UTF-8",'./access_token/'),0777,true);
//        }
//        $file = './access_token/token';
//        if(file_exists($file)){
//            $content = file_get_contents($file);
//            $cont = json_decode($content);
//            if( (time()-filemtime($file)) < $cont->expires_in){   //当前时间-文件创建时间<token过期时间
//                return $cont->access_token;
//            }
//        }
//
//        $curl = 'https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid='.$this->appid.'&secret='.$this->appsecret;
//        $content = $this->_request($curl);
//        file_put_contents($file,$content);
//        $cont = json_decode($content);
//        return $cont->access_token;
//
//    }
//
//}
