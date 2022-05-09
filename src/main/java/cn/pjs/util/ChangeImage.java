package cn.pjs.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author pjs
 * @create 2020-11-04   14:51
 */
@Component
public class ChangeImage {
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    @Value("${images.baseImagePath}")
    private String SERVER_LOCATION;

//    private static final String CLIENT_LOCATION = "src\\main\\resources\\static\\images\\";

    public String upload(String userId, String articleId, MultipartFile file) {
        String url = null;
        FileChannel out = null;
        FileInputStream fis = null;
        FileChannel in = null;
        try {
            String name = file.getOriginalFilename();
            assert name != null;
            int i = name.lastIndexOf('.');
            String type = name.substring(i);
            url = SERVER_LOCATION + "/" + userId + "/" + articleId + type;
            File file1 = new File(url);
            if (!file1.exists()) {
                System.out.println(file1.getParentFile().mkdirs() + "==== 创建服务器的用户文件夹" + file1);
                System.out.println(file1.createNewFile() + "==== 创建服务器照片");
            }
            out = FileChannel.open(Paths.get(url),
                    StandardOpenOption.READ, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            fis = (FileInputStream) file.getInputStream();
            in = fis.getChannel();
            while (in.read(byteBuffer) != -1) {
                byteBuffer.flip();
                out.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return url;
    }

    //    public String download(String userId,String articleId,String serverImage,boolean isCover){
//        FileChannel in = null;
//        FileChannel out = null;
//        String type = null;//文件类型
//        try {
//            type = serverImage.substring(serverImage.lastIndexOf('.'));
//            String serverUrl = SERVER_LOCATION + "/"+userId;//服务端图片的用户文件夹
//            String clientUrl = CLIENT_LOCATION + "/" +userId + "/";//客户端用户文件夹
//            String clientImage = clientUrl + articleId + type;//客户端下载的地址
//        /*
//        1.先看客户端有没有下载
//            1.1 没有下载就从服务端下载
//            1.2 下载了就直接返回图片src
//         */
//            File file = new File(clientUrl);
//            file.mkdirs();
//            String[] list = file.list();
//            boolean flag = false;//客户端是否有照片
//            //如果要覆盖就不走下面这个判断
//            if (!isCover){
//                if (list !=null ) {
//                    for (String info : list){
//                        int i = info.lastIndexOf('.');
//                        String name = info.substring(0,i);
//                        if (name.equals(articleId)){
//                            flag = true;
//                            break;
//                        }
//                    }
//                }
//            }
//
//            //是否要下载
//            if (!flag){
//               in = FileChannel.open(Paths.get(serverImage), StandardOpenOption.READ);
//                File file1 = new File(clientImage);
//                System.out.println(file1.createNewFile() +"==== 创建客户端的照片");
//                out = FileChannel.open(Paths.get(clientImage),
//                        StandardOpenOption.READ, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
//                while (in.read(byteBuffer) != -1){
//                    byteBuffer.flip();
//                    out.write(byteBuffer);
//                    byteBuffer.clear();
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//               if (out!=null){
//                   out.close();
//               }
//                if (in!=null) {
//                    in.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return "static/images/"+userId+"/"+articleId+type;
//    }
    public String download(String userId, String articleId, String serverImage, boolean isCover) {
        String type = null;//文件类型
        type = serverImage.substring(serverImage.lastIndexOf('.'));
        return "static/images/" + userId + "/" + articleId + type;
    }

    public boolean deleteUser(String userId) {
        String serverUserPath = SERVER_LOCATION + "/" + userId;
//        String clientUserPath = CLIENT_LOCATION+"/"+userId;
        return delete(serverUserPath);
    }

    //删除文章
    public boolean deleteArticle(String userId, String articleId) {
        String serverArticlePath = SERVER_LOCATION + "/" + userId + "/" + articleId + ".jpg";
//        String clientArticle = CLIENT_LOCATION+"/" + userId+"/" +articleId +".jpg";
        return delete(serverArticlePath);
    }

    public boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return true;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return true;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return true;
        }
    }

    public boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return true;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return true;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return true;
        }
    }
}
