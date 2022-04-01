package com.guilin.studycode.utils.filewiths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 文件处理解析类
 *
 * @date:2021-10-18
 */
public class FileTypeParseUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileTypeParseUtil.class);

    public static void clearFiles(String workspaceRootPath) {
        File file = new File(workspaceRootPath);
        deleteFile(file);
    }

    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
        }
        file.delete();
    }

    public static void fileWrite(String str, String fileNamePath) throws IOException {
        FileWriter writer = null;
        try {
            File file = new File(fileNamePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            writer = new FileWriter(file, true);
            writer.write(str + System.getProperty("line.separator"));

        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static void changePermission(File dirFile, int mode) throws IOException {
        char[] modes = Integer.toOctalString(mode).toCharArray();
        if (modes.length != 3) {
            return;
        }
        Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
        switch (modes[0]) {
            case '1':
                perms.add(PosixFilePermission.OWNER_EXECUTE);
                break;
            case '2':
                perms.add(PosixFilePermission.OWNER_WRITE);
                break;
            case '4':
                perms.add(PosixFilePermission.OWNER_READ);
                break;
            case '5':
                perms.add(PosixFilePermission.OWNER_READ);
                perms.add(PosixFilePermission.OWNER_EXECUTE);
                break;
            case '6':
                perms.add(PosixFilePermission.OWNER_READ);
                perms.add(PosixFilePermission.OWNER_WRITE);
                break;
            case '7':
                perms.add(PosixFilePermission.OWNER_READ);
                perms.add(PosixFilePermission.OWNER_WRITE);
                perms.add(PosixFilePermission.OWNER_EXECUTE);
                break;

            default:
                break;
        }
        switch (modes[1]) {
            case '1':
                perms.add(PosixFilePermission.GROUP_EXECUTE);
                break;
            case '2':
                perms.add(PosixFilePermission.GROUP_WRITE);
                break;
            case '4':
                perms.add(PosixFilePermission.GROUP_READ);
                break;
            case '5':
                perms.add(PosixFilePermission.GROUP_READ);
                perms.add(PosixFilePermission.GROUP_EXECUTE);
                break;
            case '6':
                perms.add(PosixFilePermission.GROUP_READ);
                perms.add(PosixFilePermission.GROUP_WRITE);
                break;
            case '7':
                perms.add(PosixFilePermission.GROUP_READ);
                perms.add(PosixFilePermission.GROUP_WRITE);
                perms.add(PosixFilePermission.GROUP_EXECUTE);
                break;
            default:
                break;
        }
        switch (modes[2]) {
            case '1':
                perms.add(PosixFilePermission.OTHERS_EXECUTE);
                break;
            case '2':
                perms.add(PosixFilePermission.OTHERS_WRITE);
                break;
            case '4':
                perms.add(PosixFilePermission.OTHERS_READ);
                break;
            case '5':
                perms.add(PosixFilePermission.OTHERS_EXECUTE);
                perms.add(PosixFilePermission.OTHERS_READ);
                break;
            case '6':
                perms.add(PosixFilePermission.OTHERS_READ);
                perms.add(PosixFilePermission.OTHERS_WRITE);
                break;
            case '7':
                perms.add(PosixFilePermission.OTHERS_EXECUTE);
                perms.add(PosixFilePermission.OTHERS_READ);
                perms.add(PosixFilePermission.OTHERS_WRITE);
                break;
            default:
                break;
        }

        try {
            Path path = Paths.get(dirFile.getAbsolutePath());
            Files.setPosixFilePermissions(path, perms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File mkFile(String fileName) {
        File f = new File(fileName);
        try {
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }

    /**
     * 复制目录和文件
     *
     * @param oldPath
     * @param newPath
     * @throws IOException
     */
    public static void copyDirAndFile(String oldPath, String newPath) throws IOException {
        if (!(new File(newPath)).exists()) {
            (new File(newPath)).mkdir();
        }
        File file = new File(oldPath);
        //file name list
        String[] filePaths = file.list();
        for (String filePath : filePaths) {
            String oldFullPath = oldPath + file.separator + filePath;
            String newFullPath = newPath + file.separator + filePath;
            File oldFile = new File(oldFullPath);
            File newFile = new File(newFullPath);

            if (oldFile.isDirectory()) {
                copyDirAndFile(oldFullPath, newFullPath);
            } else if (oldFile.isFile()) {
                copyFile(oldFile, newFile);
            }
        }
    }


    public static void copyFile(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    /**
     * @param srcFile     Unzipped file
     * @param destDirPath Unzipped destination folder
     * @throws RuntimeException
     * @throws IOException
     */
    public static void unZip(MultipartFile srcFile, String destDirPath, String savePath) throws RuntimeException, IOException {
        long startTime = System.currentTimeMillis();

        File file = null;
        logger.info(srcFile.getOriginalFilename());
        logger.info("------------------->>>"+srcFile.getName());

        InputStream ins = srcFile.getInputStream();
        //系统找不到指定的文件
        file = new File(new File(savePath).getAbsolutePath() + srcFile.getOriginalFilename());
        logger.info("MultipartFile transform to File,MultipartFile name:" + srcFile.getOriginalFilename());
        inputStreamToFile(ins, file);

        if (!file.exists()) {
            throw new RuntimeException(file.getPath() + ",file is not found");
        }
        ZipFile zipFile = null;
        try {
//            zipFile = new ZipFile(file); 解決亂碼問題
            zipFile = new ZipFile(file, Charset.forName("GBK"));

            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();

                if (entry.isDirectory()) {
                    String dirPath = destDirPath + File.separator + entry.getName();
                    File dir = new File(dirPath);
                    dir.mkdirs();
                } else {
                    File targetFile = new File(destDirPath + File.separator + entry.getName());
                    targetFile.setExecutable(true);
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.close();
                    is.close();
                }
            }
            long endTime = System.currentTimeMillis();
            logger.info("unZip time-->" + (endTime - startTime) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("unzip error from FileUtil", e);
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //MultipartFile change to file may create a temp file in the project root folder(delete the temp file)
            //删除临时文件zip压缩包
            File del = new File(file.toURI());
            del.delete();
        }
    }

    /**
     * MultipartFile changed to File
     *
     * @return
     */
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
            logger.info("MultipartFile transform to File completed!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 遍历文件
     */
    public static List<File> getSubFiles(String desFile, List<File> fileList) {
        File file = new File(desFile);
        File[] files = file.listFiles();
        for (File fileIndex : files) {
            if (!fileIndex.exists()) {
                throw new NullPointerException("Cannot find " + fileIndex);
            } else if (fileIndex.isFile()) {
                fileList.add(fileIndex);
            } else {
                if (fileIndex.isDirectory()) {
                    getSubFiles(fileIndex.getAbsolutePath(), fileList);
                }
            }
        }
        return fileList;
    }


    /**
     * 获取文件夹和文件名称
     * @param path
     */
    public static boolean getFileDirOrName(String path) {
        File dirFile = new File(path);
        if (dirFile.exists()) {
            File[] files = dirFile.listFiles();
            if (files != null) {
                for (File fileChildDir : files) {
                    //输出文件名或者文件夹名
                    logger.info(fileChildDir.getName());
                    if (fileChildDir.isDirectory()) {
                        if(fileChildDir.getName().contains("附件")){
                            return true;

                        }else {
                            //通过递归的方式,可以把目录中的所有文件全部遍历出来
                            getFileDirOrName(fileChildDir.getAbsolutePath());
                        }
                    }
//                    if (fileChildDir.isFile()) {
//                        System.out.println(" :  此为文件名");
//                    }
                }
            }
        }else{
            logger.info("你想查找的文件不存在");
        }
        return false;
    }

     public static void main(String [] args){

        String  filename = "abc.zip";

         String unzipPackage = filename.substring(0,filename.lastIndexOf("."));
     }


}

