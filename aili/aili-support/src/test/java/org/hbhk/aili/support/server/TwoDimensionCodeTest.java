package org.hbhk.aili.support.server;
import org.hbhk.aili.support.server.util.QRCodeUtil;

public class TwoDimensionCodeTest {
	public static void main(String[] args) {
        String imgPath = "d:/qr-code/pr1.png";
        String contents = "你好,hbhk! welcome to zxing!";
        // 普通二维码的生成与解析
        QRCodeUtil.encodeQRCode(contents, 300, 300, imgPath);
        System.out.println("生成二维码成功");
        System.out.println(QRCodeUtil.decodeQRCode(imgPath));
 
        // 带图片的二维的生成与解析
        imgPath = "d:/qr-code/pr2.png";
        String srcPath = "D:/qr-code/icon.png";
        QRCodeUtil.encodeQRCode(contents, 300, 300, srcPath,imgPath);
        System.out.println("生成带图片的二维码成功");
        System.out.println(QRCodeUtil.decodeQRCode(imgPath));
 
        // 条形码的生成与解析
        imgPath = "d:/qr-code/bar.png";
        QRCodeUtil.encodeBar("6923450657713", 105, 50, imgPath);
        System.out.println("生成条形码成功");
        System.out.println(QRCodeUtil.decodeBar(imgPath));
    }
}
