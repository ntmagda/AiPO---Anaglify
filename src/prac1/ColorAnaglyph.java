/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package prac1;

import java.awt.image.BufferedImage;
import marvin.image.MarvinImage;
/**
 *
 * @author magda
 */
public class ColorAnaglyph {   
    
    void process(MarvinImage img, MarvinImage img1, MarvinImage resultImage)
    {
        
        for ( int x = 0 ; x < img.getWidth();x++)
        {
            for ( int y = 0 ; y < img.getHeight();y++)
            {
                int r = img.getIntComponent0(x,y);
                int g = img.getIntComponent1(x,y);
                int b = img.getIntComponent2(x,y);
                
                
                int r1 = img1.getIntComponent0(x,y);
                int g1 = img1.getIntComponent1(x,y);
                int b1 = img1.getIntComponent2(x,y);
                
                int R = (int)(r*0.299+g*0.587+b*0.144);
                int G = 0;
                int B = (int)(r1*0.299+g1*0.587+b1*0.144);
                
               resultImage.setIntColor(x, y, R,G,B);
               
               
            }
        }
    }
    
     void process(MarvinImage img, MarvinImage resultImage)
    {
        BufferedImage imgbuf = img.getBufferedImage();
        BufferedImage piece1 = imgbuf.getSubimage(0, 0, imgbuf.getWidth()/2, imgbuf.getHeight());
        BufferedImage piece2 = imgbuf.getSubimage(imgbuf.getWidth()/2, 0, imgbuf.getWidth()/2, imgbuf.getHeight());
        
        MarvinImage image1 = new MarvinImage(piece1);
        MarvinImage image2 = new MarvinImage(piece2);
  
        resultImage.resize(img.getWidth()/2, img.getHeight());
        process(image2,image1,resultImage);
    }
}
