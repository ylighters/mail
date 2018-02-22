package com.qt.mail.common.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

public class ImageUtil {
    private final static int WIDTH = 60;
    private final static int HEIGHT = 60; 
	
	
	public static OutputStream resizeImage(MultipartFile file,File newFile) throws IOException {
		InputStream is =file.getInputStream();
		OutputStream os=new FileOutputStream(newFile);
        BufferedImage prevImage = ImageIO.read(is);
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        graphics.drawImage(prevImage, 0, 0, WIDTH, HEIGHT, null);
        ImageIO.write(image,"png", os);
        os.flush();
        is.close();
        os.close();
        return os;
    }
	
	public static OutputStream resizeImage(File orgin,File dest) throws IOException {
		InputStream is = new FileInputStream(orgin);
		
        OutputStream os = new FileOutputStream(dest);
		BufferedImage prevImage = ImageIO.read(is);
        BufferedImage image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        graphics.drawImage(prevImage, 0, 0, WIDTH, HEIGHT, null);
        ImageIO.write(image, "png", os);
        os.flush();
        is.close();
        os.close();
        return os;
    }
	
	public static OutputStream resizeImage(File orgin,File dest,String width,String heigth) throws IOException {
		InputStream is = new FileInputStream(orgin);
		
        OutputStream os = new FileOutputStream(dest);
		BufferedImage prevImage = ImageIO.read(is);
        BufferedImage image = new BufferedImage(Integer.valueOf(width),Integer.valueOf(heigth), BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        graphics.drawImage(prevImage, 0, 0, Integer.valueOf(width),Integer.valueOf(heigth), null);
        ImageIO.write(image, "png", os);
        os.flush();
        is.close();
        os.close();
        return os;
    }
	
	
	public static OutputStream resizeImage2(InputStream is, OutputStream os, int size, String format) throws IOException {
        BufferedImage prevImage = ImageIO.read(is);
        BufferedImage image = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = image.createGraphics();
        graphics.drawImage(prevImage, 0, 0, WIDTH, HEIGHT, null);
        ImageIO.write(image, "png", os);
        os.flush();
        is.close();
        os.close();
       // ByteArrayOutputStream b = (ByteArrayOutputStream) os;
        return os;
    }
    
    public static void main(String[] args) {
        try {
            InputStream is = new FileInputStream(new File("F:/imageTest/2.jpg"));
            OutputStream os = new FileOutputStream(new File("F:/imageTest/4.jpg"));
            resizeImage2(is, os, 10, "png");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
