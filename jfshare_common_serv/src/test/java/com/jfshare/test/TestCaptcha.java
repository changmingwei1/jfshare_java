package com.jfshare.test;

import com.jfshare.finagle.thrift.common.Captcha;
import com.jfshare.finagle.thrift.common.CaptchaResult;
import com.jfshare.finagle.thrift.common.CommonServ;
import com.jfshare.finagle.thrift.result.Result;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class TestCaptcha {
    public static void main(String[] args) {
        try {
            TTransport transport;
            transport = new TSocket("120.24.153.155", 1984);
//            transport = new TSocket("localhost", 1984);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(new TFramedTransport(transport));

            Captcha captcha = new Captcha();
            captcha.setId("1234");
            captcha.setValue("cle6");
            CommonServ.Client client = new CommonServ.Client(protocol);
            Result r = client.validateCaptcha(captcha);
            CaptchaResult rc = client.getCaptcha("1234");
            if(rc.result.code!=0) {
                System.out.println("---->失败");
                return;
            }
            System.out.print(r);
            System.out.print(rc);

            createPic(rc.captcha.getCaptchaBytes());



        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void createPic(byte[] bytes) {
        //Before is how to change ByteArray back to Image
        File imageFile = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");
//ImageIO is a class containing static convenience methods for locating ImageReaders
//and ImageWriters, and performing simple encoding and decoding.

            ImageReader reader = (ImageReader) readers.next();
            Object source = bis; // File or InputStream, it seems file is OK

            ImageInputStream iis = ImageIO.createImageInputStream(source);
//Returns an ImageInputStream that will take its input from the given Object

            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();

            Image image = null;
            image = reader.read(0, param);
//got an image file

            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
//bufferedImage is the RenderedImage to be written
            Graphics2D g2 = bufferedImage.createGraphics();
            g2.drawImage(image, null, null);
            imageFile = new File("F:\\testFromThrift.jpg");
            ImageIO.write(bufferedImage, "jpg", imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
//"jpg" is the format of the image
//imageFile is the file to be written to.

        System.out.println(imageFile.getPath());
    }

}
