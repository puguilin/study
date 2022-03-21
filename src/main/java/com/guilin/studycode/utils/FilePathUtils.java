package com.guilin.studycode.utils;

import cn.hutool.core.util.IdUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件路径处理工具
 *
 * @author puguilin
 * @date:2021-10-20
 */
public class FilePathUtils {


    public static final String DOT = ".";
    public static final String SLASH_ONE = "/";
    public static final String SLASH_TWO = "\\";

    /**
     * 获取没有扩展名的文件名
     */
    public static String getWithoutExtension(String fileName) {
        String ext = StringUtils.substring(fileName, 0, StringUtils.lastIndexOf(fileName, DOT));
        return StringUtils.trimToEmpty(ext);
    }

    /**
     * 获取扩展名
     */
    public static String getExtension(String fileName) {
        if (StringUtils.INDEX_NOT_FOUND == StringUtils.indexOf(fileName, DOT)) return StringUtils.EMPTY;
        String ext = StringUtils.substring(fileName, StringUtils.lastIndexOf(fileName, DOT));
        return StringUtils.trimToEmpty(ext);
    }

    /**
     * 判断是否同为扩展名
     */
    public static boolean isExtension(String fileName, String ext) {
        return StringUtils.equalsIgnoreCase(getExtension(fileName), ext);
    }

    /**
     * 判断是否存在扩展名
     */
    public static boolean hasExtension(String fileName) {
        return !isExtension(fileName, StringUtils.EMPTY);
    }

    /**
     * 得到正确的扩展名
     */
    public static String trimExtension(String ext) {
        return getExtension(DOT + ext);
    }

    /**
     * 向path中填充扩展名(如果没有或不同的话)
     */
    public static String fillExtension(String fileName, String ext) {
        fileName = replacePath(fileName + DOT);
        ext = trimExtension(ext);
        if (!hasExtension(fileName)) {
            return fileName + getExtension(ext);
        }
        if (!isExtension(fileName, ext)) {
            return getWithoutExtension(fileName) + getExtension(ext);
        }
        return fileName;
    }


    /**
     * 判断是否是文件PATH
     */
    public static boolean isFile(String fileName) {
        return hasExtension(fileName);
    }

    /**
     * 判断是否是文件夹PATH
     */
    public static boolean isFolder(String fileName) {
        return !hasExtension(fileName);
    }

    public static String replacePath(String path) {
        return StringUtils.replace(StringUtils.trimToEmpty(path), SLASH_ONE, SLASH_TWO);
    }

    /**
     * 链接PATH前处理
     */
    public static String trimLeftPath(String path) {
        if (isFile(path)) return path;
        path = replacePath(path);
        String top = StringUtils.left(path, 1);
        if (StringUtils.equalsIgnoreCase(SLASH_TWO, top)) return StringUtils.substring(path, 1);
        return path;
    }

    /**
     * 链接PATH后处理
     */
    public static String trimRightPath(String path) {
        if (isFile(path)) return path;
        path = replacePath(path);
        String bottom = StringUtils.right(path, 1);
        if (StringUtils.equalsIgnoreCase(SLASH_TWO, bottom))
            return StringUtils.substring(path, 0, path.length() - 2);
        return path + SLASH_TWO;
    }

    /**
     * 链接PATH前后处理，得到准确的链接PATH
     */
    public static String trimPath(String path) {
        path = StringUtils.replace(StringUtils.trimToEmpty(path), SLASH_ONE, SLASH_TWO);
        path = trimLeftPath(path);
        path = trimRightPath(path);
        return path;
    }

    /**
     * 通过数组完整链接PATH
     */
    public static String bulidFullPath(String... paths) {
        StringBuffer sb = new StringBuffer();
        for (String path : paths) {
            sb.append(trimPath(path));
        }
        return sb.toString();
    }


    /**
     * 分类存储
     *
     * @param sourceFile
     * @param businessType
     * @param basePath
     * @throws IOException
     */
    public static Map saveFileByType(File sourceFile, String businessType, String basePath) throws IOException {
        Map resultMap = new HashMap();
        String packagePrimaryId = IdUtil.fastSimpleUUID();
        String aimFileName = UUID.randomUUID().toString();
        File file = new File(basePath);
        //如果路径不存在，新建文件夾
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        String destFileName = basePath + aimFileName;
        String ext = getExtension(sourceFile.getName());
        String finallyFileName = fillExtension(destFileName, ext);
        //存储文件
        File finallyFile = new File(destFileName+ext);
        //替换复制
//        FileUtils.copyFile(sourceFile, finallyFile);
        InputStream is = new FileInputStream(sourceFile);
        FileUtils.copyInputStreamToFile(is, finallyFile);
        resultMap.put("srcFileName",sourceFile.getName()); //原文件名称
        resultMap.put("aimFileName",finallyFile.getName()); //目标文件编码和原文件名称对应
        resultMap.put("primaryId",packagePrimaryId);//文件主键编码
        resultMap.put("fileUrl",basePath);//文件相对路径
        resultMap.put("fileType",ext);//文件类型
        return resultMap;
    }


//    public static void main(String[] args) {
//        String packagePrimaryId = IdUtil.fastSimpleUUID();
//        String aimFileName = UUID.randomUUID().toString();
////      String path = "E:\\opt\\test";
//        String aimPath = "D:/opt/preselection/landapply" + "/" + packagePrimaryId;
//        File file = new File(aimPath);
//        //如果路径不存在，新建
//        if (!file.exists() && !file.isDirectory()) {
//            file.mkdirs();
//        }
////        String fileName = "D:/apache-tomcat-7.0.23/webapps/ROOT/report rets/attachment/report/test/";
//        String fileName = aimPath + "/" + aimFileName;
//        String filepath = FilePathUtils.fillExtension(fileName, "XLS");
//        String[] paths = {"win", "linux", "apple", "pear.txt"};
//    }
}

