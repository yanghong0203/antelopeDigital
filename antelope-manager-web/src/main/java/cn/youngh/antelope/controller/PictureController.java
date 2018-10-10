package cn.youngh.antelope.controller;

import cn.youngh.antelope.common.utils.FastDFSClient;
import cn.youngh.antelope.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PictureController {
    @Value("${img.server.url}")
    private String imgUrl;
    @RequestMapping(value = "/pic/upload",produces = MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile){
        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
            String fileName = uploadFile.getOriginalFilename();
            String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
            String url = fastDFSClient.uploadFile(uploadFile.getBytes(),fileExtensionName);
            url = imgUrl+url;
            Map result = new HashMap();
            result.put("error",0);
            result.put("url",url);
            return JsonUtils.objectToJson(result);
        } catch (Exception e) {
            Map result = new HashMap();
            result.put("error",1);
            result.put("message","图片上传失败");
            return JsonUtils.objectToJson(result);
        }

    }
}
