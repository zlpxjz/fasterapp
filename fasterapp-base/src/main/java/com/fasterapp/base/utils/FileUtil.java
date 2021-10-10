package com.fasterapp.base.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static InputStream getResourceAsStream(String name) throws Exception{
        return FileUtil.class.getClassLoader().getResourceAsStream(name) ;
    }

    /**
     * 获取文件名
     * @param file
     * @return
     * @throws Exception
     */
    public static String getFileName(String file) throws Exception{
        if(StringUtil.isNullOrBlank(file)) return null;

        if(file.lastIndexOf("/") > 0){
            file = file.substring(file.lastIndexOf("/") + 1);
        }

        if(file.lastIndexOf("\\") > 0){
            file = file.substring(file.lastIndexOf("\\") + 1);
        }

        return file;
    }

    public static String getMD5(String file) throws Exception{
        return getMD5(new FileInputStream(new File(file)));
    }

    public static String getMD5(InputStream is) throws Exception{
        return DigestUtils.md5Hex(is);
    }

    /**
     * 获取文件后缀
     * @param file
     * @return
     */
    public static String getSuffix(File file) {
        return getSuffix(file.getName());
    }

    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 创建目录
     * @param dirPath
     * @return
     * @throws Exception
     */
    public static File  mkdir(String dirPath) throws Exception{
        File dir = new File(dirPath);
        if(! dir.exists()){
            dir.mkdirs();
        }

        return dir;
    }

    /**
     * 创建以时间为分类的目录
     * @param basePath
     * @return
     * @throws Exception
     */
    public static File mkdir(String basePath, boolean isDateDir) throws Exception{
        StringBuffer fullPath = new StringBuffer(basePath);
        if(isDateDir) {
            fullPath.append(File.separator).append(DateUtil.format(DateUtil.getSysDate(), "yyyyMMdd"));
        }

        return mkdir(fullPath.toString());
    }

    /**
     *
     * @param source
     * @param target
     * @throws Exception
     */
    public static void copy(File source, String target) throws Exception{
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            File dest = new File(target);
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    /**
     * 上传文件保存
     * @param basePath
     * @param files
     * @return
     * @throws Exception
     */
    public static List<String> save(String basePath, MultipartFile[] files) throws Exception{
        List<String> fms = new ArrayList<>(files.length);

        for(MultipartFile file : files){
            fms.add(save(basePath, file));
        }

        return fms;
    }

    /**
     * 上传文件保存
     * @param basePath
     * @param file
     * @return
     * @throws Exception
     */
    public static String save(String basePath, MultipartFile file) throws Exception{
        String originName = file.getOriginalFilename(), fileType = getSuffix(originName), fileName = StringUtil.getUUID() + "." +fileType;

        StringBuffer relativePath = new StringBuffer();
        relativePath.append(File.separator).append(DateUtil.format(DateUtil.getSysDate(), "yyyyMMdd"));
        StringBuffer fullPath = new StringBuffer(basePath).append(relativePath);
        File dir = new File(fullPath.toString());
        if(!dir.exists()){
            dir.mkdirs();
        }

        file.transferTo(new File(fullPath.toString() + File.separator + fileName));

        return relativePath.toString() + File.separator + fileName;
    }

    /**
     * Excel 流下载
     * @param fileName
     * @param workbook
     * @param response
     * @throws Exception
     */
    public static void exportAsExcel(String fileName, Workbook workbook, HttpServletResponse response) throws Exception{
        OutputStream out=response.getOutputStream();
        response.setHeader("Content-Type","application/vnd.ms-excel");
        response.addHeader("Content-Disposition","attachment;filename=" + StringUtil.encode(fileName));
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");

        try{
            workbook.write(out);
            out.flush();
            out.close();
        }finally{
            out.flush();
            out.close();
        }
    }

    /**
     * PDF 输出
     * @param fileName
     * @param outputStream
     * @param response
     * @throws Exception
     */
    public static void exportAsPdf(String fileName, ByteArrayOutputStream outputStream , HttpServletResponse response) throws Exception{
        OutputStream out=response.getOutputStream();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition","attachment;filename=" + StringUtil.encode(fileName));

        try{
            response.getOutputStream().write(outputStream.toByteArray());
        }finally{
            out.flush();
            out.close();
        }
    }

    public static void main(String[] args) throws Exception{
        System.out.println(getFileName("C:/sers/DELL/Pictures/����.jpg"));
    }
}
