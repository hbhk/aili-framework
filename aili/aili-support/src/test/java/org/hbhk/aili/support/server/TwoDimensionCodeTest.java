package org.hbhk.aili.support.server;
import org.hbhk.aili.support.server.util.QRCodeUtil;

public class TwoDimensionCodeTest {
	public static void main(String[] args) {
        String imgPath = "d:/pr1.png";
        String contents = "你好,李四! welcome to zxing!"
                + "\n李四的博客[http://my.oschina.net/cloudcoder]"
                + "\nEmail[xxx@163.com]";
        // 普通二维码的生成与解析
        QRCodeUtil.encodePR(contents, 300, 300, imgPath);
        System.out.println("生成二维码成功");
        System.out.println(QRCodeUtil.decodePR(imgPath));
 
        // 带图片的二维的生成与解析
        imgPath = "d:/pr2.png";
        String srcPath = "d:/pr1.png";
        QRCodeUtil.encodePR(contents, 300, 300, srcPath,imgPath);
        System.out.println("生成带图片的二维码成功");
        System.out.println(QRCodeUtil.decodePR(imgPath));
 
        // 条形码的生成与解析
        imgPath = "d:/bar.png";
        QRCodeUtil.encodeBar("6923450657713", 105, 50, imgPath);
        System.out.println("生成条形码成功");
        System.out.println(QRCodeUtil.decodeBar(imgPath));
    }
}
