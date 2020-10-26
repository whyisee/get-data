package com.whyisee.getdata.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2020/10/23 10:09
 * @version 1.0
 * @Modified By:
 * @used in: WhyiseeBench
 */
@Controller
@RequestMapping("/file")
public class FileController {
    @Value("${file.upload.url}")
    private String uploadFilePath;
    @Value("${file.download.url}")
    private String downloadFilePath;



    @RequestMapping(value="/upload",method = RequestMethod.GET)
    public String upload(){
        return "/fileupload";
    }
    @RequestMapping(value="/upload/batch",method = RequestMethod.GET)
    public String batchUpload(){
        return "/multifileupload";
    }
    @RequestMapping(value="/uploadsingle",method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file")MultipartFile file){
        String message="";
        if (!file.isEmpty()){
            try{
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
                message="上传失败,"+e.getMessage();
            }catch (IOException e){
                e.printStackTrace();
                message="上传失败,"+e.getMessage();
            }
        }else {
            message="上传失败,文件为空";
        }
        return message;
    }

    @RequestMapping(value="/upload/batch",method = RequestMethod.POST)
    public @ResponseBody String batchUpload(HttpServletRequest request){
        String message="";
        List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            if (!file.isEmpty()){
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.flush();
                    stream.close();
                }catch (Exception e){
                    stream = null;
                    message = message+file.getName()+"上传失败,出现异常\n";
                }
            }else {
                message = message+file.getName()+"上传失败,文件为空\n";
            }
        }
        return message;
    }

    @RequestMapping("/upload")
    public @ResponseBody String upload(@RequestParam("files") MultipartFile files[]){
        String message = "";
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getOriginalFilename();
            File dest = new File(uploadFilePath+"/"+fileName);
            if (!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(dest);
            }catch (Exception e){
                message = message+fileName+" error!";
            }
        }

        return message;
    }


    @RequestMapping("/download")
    public @ResponseBody  String fileDownLoad(HttpServletResponse response, @RequestParam("fileName") String fileName){
        File file = new File(downloadFilePath +'/'+ fileName);
        if(!file.exists()){
            return "下载文件不存在";
        }
        //out.clear();
        //out = pageContext.pushBody();

        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();

            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
               // os.close();
            }
        } catch (IOException e) {
            return "下载失败";
        }
        return "下载成功";
    }
}
