/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.captcha;

/**
 *
 * @author QuyetTC
 */
import com.viettel.utils.Constant;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.Random;
import java.awt.geom.AffineTransform;

public class ImageUtil {

    public ImageUtil() {
    }

    public static Color getFontColor() {
        return fontColor;
    }

    public static void setFontColor(Color fontColor) {
//        this.fontColor = fontColor;
    }

    public static FontEx getFontName() {
        return fontName;
    }

    public static void setFontName(FontEx fontName) {
//        fontName = fontName;
    }

    public static Color getShadowColor() {
        return shadowColor;
    }

    public static void setShadowColor(Color shadowColor) {
//        shadowColor = shadowColor;
    }

    public static BufferedImage drawTextOnImages(String imagesFile, String message)
            throws IOException {
        String message1 = " ";
        for (int i = 0; i < message.length(); i++) {
            message1 = message1 + message.substring(i, i + 1).toString() + "";
        }

        message = message1.substring(0, message1.length() - 2);
        java.io.InputStream in = new FileInputStream(imagesFile);
        BufferedImage bufferedImage = ImageIO.read(in);
        Graphics2D graphics = bufferedImage.createGraphics();
        drawText(graphics, bufferedImage.getWidth(), bufferedImage.getHeight(), message1);
        return bufferedImage;
    }

    public static BufferedImage drawTextOnImages(String imagesFile, String message, int length)
            throws IOException {
        java.io.InputStream in = new FileInputStream(imagesFile);
        BufferedImage bufferedImage = ImageIO.read(in);
        Graphics2D graphics = bufferedImage.createGraphics();
        drawText(graphics, bufferedImage.getWidth(), bufferedImage.getHeight(), message, length);
        return bufferedImage;
    }

    public static BufferedImage drawImageOnImages(String imageBackground, String image, int x, int y)
            throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new FileInputStream(imageBackground));
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(ImageIO.read(new FileInputStream(image)), null, x, y);
        return bufferedImage;
    }

    public static void drawText(Graphics2D g2d, int width, int height, String message)
            throws IOException {
        try {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setPaint(fontColor);
            Random r = new Random();
            String alphabet = message;
            String result = new String();
            int posX = 0;
            int posY = 0;
            int x = 0;
            String nfont = "";
            int[] y = {-1, 1};
            int numheight = 0;
            String[] sfont = {"Kristen ITC", "Roman", "Decorative", "Modern", "respectively", "Georgia",
                "Garamond", "monospace", "Arial", "times", "calibri", "serif", "cursive", "fantasy"};
            nfont = sfont[r.nextInt(sfont.length)].toString();
            fontName = new FontEx(nfont, 2, 30);      //font chữ ngẫu nhiên         
            if (message.length() == 4) {
                fontName.setSize(34);
            } else if (message.length() == 5) {
                fontName.setSize(32);
            } else if (message.length() == 6) {
                fontName.setSize(30);
            } else {
                fontName.setSize(30);//cỡ chữ ngẫu nhiên
            }
            g2d.setFont(fontName);
            for (int i = 0; i < message.length(); i++) // vị trí xoay, nghiêng, hiệu ứng…
            {
                numheight = y[r.nextInt(y.length)];
                result = "" + alphabet.charAt(i);
                posX = (width - getStringWidth(g2d, message)) / 2 + x;
                posY = height / 2 + height / 4 + numheight * 2;
                AffineTransform att = new AffineTransform();
                g2d.setTransform(att);
                g2d.rotate(Math.toRadians(10 * numheight), posX, posY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.drawString(result, posX, posY);
                x = x + 16;
            }
            g2d.dispose();
        } catch (Exception e) {Constant.LogNo(e);
//            System.out.println(e);
//            //e.printStackTrace();
        }
    }

    public static void drawText(Graphics2D g2d, int width, int height, String message, int length)
            throws IOException {
        try {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setPaint(fontColor);
            if (length <= 8) {
                fontName.setSize(30);
            } else if (length <= 10) {
                fontName.setSize(25);
            } else {
                fontName.setSize(20);
            }
            g2d.setFont(fontName);
            int posX = (width - getStringWidth(g2d, message)) / 2;
            int posY = height / 2 + height / 6;
            g2d.drawString(message, posX, posY);
            g2d.dispose();
        } catch (Exception e) {Constant.LogNo(e);
//            System.out.println(e);
//            //e.printStackTrace();
        }
    }

    public static BufferedImage makeImage(String imagesFile, String message)
            throws IOException {
        java.io.InputStream in = new FileInputStream(imagesFile);
        FontMetrics metrics = getFontMetrics(fontName);
        if (message.length() <= 8) {
            fontName.setSize(30);
        } else if (message.length() <= 10) {
            fontName.setSize(20);
        } else {
            fontName.setSize(15);
        }
        int messageWidth = metrics.stringWidth(message);
        int baselineX = messageWidth / 10;
        int height = fontName.getSize() * 3;
        int baselineY = (height * 4) / 5;
        BufferedImage bufferedImage = ImageIO.read(in);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(fontName);
        g2d.translate(baselineX, baselineY);
        g2d.setPaint(shadowColor);
        java.awt.geom.AffineTransform origTransform = g2d.getTransform();
        g2d.shear(-0.80000000000000004D, 0.0D);
        g2d.scale(1.0D, 3D);
        g2d.drawString(message, 0, 0);
        g2d.setTransform(origTransform);
        g2d.setPaint(fontColor);
        g2d.drawString(message, 0, 0);
        return bufferedImage;
    }

    public static BufferedImage makeImage(String message, String fontName, int fontSize) {
        Font font = new Font(fontName, 0, fontSize);
        FontMetrics metrics = getFontMetrics(font);
        int messageWidth = metrics.stringWidth(message);
        int baselineX = messageWidth / 10;
        int width = messageWidth + 2 * (baselineX + fontSize);
        int height = (fontSize * 7) / 2;
        int baselineY = (height * 8) / 10;
        BufferedImage bufferedImage = new BufferedImage(width, height, 1);
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setBackground(Color.white);
        g2d.clearRect(0, 0, width, height);
        g2d.setFont(font);
        g2d.translate(baselineX, baselineY);
        g2d.setPaint(Color.lightGray);
        java.awt.geom.AffineTransform origTransform = g2d.getTransform();
        g2d.shear(-0.94999999999999996D, 0.0D);
        g2d.scale(1.0D, 3D);
        g2d.drawString(message, 0, 0);
        g2d.setTransform(origTransform);
        g2d.setPaint(Color.black);
        g2d.drawString(message, 0, 0);
        return bufferedImage;
    }

    public static void writeJPEG(BufferedImage image, OutputStream out) {
        try {
            ImageIO.write(image, "jpg", out);
        } catch (IOException ioe) {Constant.LogNo(ioe);
            System.err.println("Error outputting JPEG: " + ioe);
        }
    }

    public static void writeJPEG(BufferedImage image, File file) {
        try {
            ImageIO.write(image, "jpg", file);
        } catch (IOException ioe) {Constant.LogNo(ioe);
            System.err.println("Error writing JPEG file: " + ioe);
        }
    }

    public static void writeGIF(BufferedImage image, OutputStream out) {
        try {
            ImageIO.write(image, "gif", out);
        } catch (IOException ioe) {Constant.LogNo(ioe);
//            System.err.println("Error outputting GIF: " + ioe);
        }
    }

    public static void writeGIF(BufferedImage image, File file) {
        try {
            ImageIO.write(image, "gif", file);
        } catch (IOException ioe) {Constant.LogNo(ioe);
//            System.err.println("Error writing GIF file: " + ioe);
        }
    }

    public static String[] getFontNames() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        return env.getAvailableFontFamilyNames();
    }

    private static FontMetrics getFontMetrics(Font font) {
        BufferedImage tempImage = new BufferedImage(1, 1, 1);
        Graphics2D g2d = (Graphics2D) tempImage.getGraphics();
        return g2d.getFontMetrics(font);
    }

    public static int getStringWidth(Graphics2D g, String s) {
        FontMetrics f = g.getFontMetrics(g.getFont());
        return f.stringWidth(s);
    }

    public static int getStringWidth(Component c, String s) {
        FontMetrics f = c.getFontMetrics(c.getFont());
        return f.stringWidth(s);
    }

    public static String mstrImagesURL = "";
    public static String mstrText = "";
    private static Color fontColor;
    private static Color shadowColor;
//    private static FontEx fontName = new FontEx("Kristen ITC", 1, 40);
    private static FontEx fontName = new FontEx("Arial", 1, 30);

    static {
        fontColor = Color.BLACK;
        shadowColor = Color.LIGHT_GRAY;
    }
}
