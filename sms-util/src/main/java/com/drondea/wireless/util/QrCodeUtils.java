package com.drondea.wireless.util;

import java.io.File;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * Created by zhaokai on 2017/6/21.
 */
public class QrCodeUtils {


        public static String getQrCode(String uploadAbPath,String fileName, int width, int height,String content) throws Exception{
                String format = "png";
                Hashtable hints = new Hashtable();
                hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
                char c = File.separatorChar;

                //绝对路径前缀+相对路径+自定义文件名
                if(!new File(uploadAbPath).exists()){
                    new File(uploadAbPath).mkdirs();
                }
                File saveingFile = new File(uploadAbPath+fileName);
                MatrixToImageWriter.writeToFile(bitMatrix, format, saveingFile);
                return fileName;
        }

        public static void generateQRCode(String text, int width, int height, String format,String path){
            try {
            	Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
                hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
                BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
                File outputFile = new File(path);
                MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
    		} catch (Exception e) {
                e.printStackTrace();
    		}
    	}
    public static void main(String[] args) {
//        generateQRCode("", 300, 300, "jpg", "C:\\Users\\99002\\Desktop\\1111\\11\\MyQRCode2.png");
    }
}
