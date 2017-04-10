package org.maiya.free.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.bingoohuang.utils.net.FileUploader;
import org.n3r.diamond.client.Miner;
import org.n3r.diamond.client.Minerable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by shuxun on 16/9/22.
 */
public class FaultFileUpload {
    private static final Logger log = LoggerFactory.getLogger(FaultFileUpload.class);

    public static boolean uploadImg(@RequestParam("image") final MultipartFile imageFile, String imageName) {
        Minerable uploadUrl = new Miner().getMiner("fault", "default");
        String uploadBasePath = uploadUrl.getString("uploadBasePath"); // 上传服务器基础路径
        String uploadUrlPrefix = uploadUrl.getString("uploadUrlPrefix"); // 上传服务器上传请求前缀
        log.info("=========================开始上传======================");
        try {
            uploadFile(imageFile, uploadUrlPrefix, uploadBasePath, imageName);
            return true;
        } catch (Exception e) {
            log.error("FaultFileUpload", e);
            return false;
        }
    }


    private static String uploadFile(@RequestParam("image") MultipartFile imageFile, String uploadUrlPrefix, String fileDir, String fileName) throws Exception {
        log.info("===============uploadUrlPrefix:{}, fileDir:{}, fileName:{}", uploadUrlPrefix, fileDir, fileName);
        FileUploader fileUploader = new FileUploader(uploadUrlPrefix + fileDir);
        fileUploader.addFilePart(fileName, imageFile.getInputStream());
        String finish = fileUploader.finish();
        log.info("===============结束上传===============, finish:{}", finish);
        JSONObject jsonObject = JSON.parseObject(finish);
        int error = jsonObject.getIntValue("error");
        if (error != 0)
            throw new Exception(jsonObject.getString("message"));
        log.info("===============结束===============,jsonObject:{}", jsonObject);
        return "";
    }
}
