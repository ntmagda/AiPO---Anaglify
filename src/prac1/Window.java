package prac1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;

public class Window extends JFrame {
    
    MarvinImage Image1;
    MarvinImage Image2;
    MarvinImage ImageResult;
    
    boolean done = false;
    boolean OneOrTwo;
    boolean isfullscreen;
    
    JButton Apply;
    JButton OnePicture;
    JButton TwoPictures;
    
    JPanel panelImage;
    JPanel panelButton;
    
    
    public Window()
    {
 
        
        panelImage = new JPanel(){
            
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                if(done)
                {
                     g.drawImage(ImageResult.getBufferedImage(), getWidth() / 2 - (int) ImageResult.getWidth() / 2, getHeight() / 2 - (int) ImageResult.getHeight() / 2, null); 
                     done = false;
                    
                }
                else if(Image1 !=null && Image2 == null)
                {
                    
                    g.drawImage(Image1.getBufferedImage(), getWidth() / 2 - (int) (Image1.getWidth()) / 2, getHeight() / 2 - (int) Image1.getHeight() / 2, null); 
                    
                }
                else if(Image1 != null && Image2 != null)
                {
                    g.drawImage(Image1.getBufferedImage(), getWidth() / 2 - (int) (Image1.getWidth()*2) / 2, getHeight() / 2 - (int) Image1.getHeight() / 2, null); 
                    g.drawImage(Image2.getBufferedImage(), getWidth() / 2 - (int) (Image1.getWidth()*2) / 2 + (int) (Image1.getWidth()*2) / 2, getHeight() / 2 - (int) Image2.getHeight() / 2, null);  
                   
                }                
                                   
            }
        };
        panelButton = new JPanel();
        
               

        OnePicture = new JButton("Wczytaj zdjęcie");
        TwoPictures = new JButton("Wczytaj dwa zdjęcia");
        Apply = new JButton("Zapisz");
   
        this.addMouseListener(new FullScreenListener());
        
        OnePicture.addKeyListener(new KeyAction());
        TwoPictures.addKeyListener(new KeyAction());
        Apply.addKeyListener(new KeyAction());
       
        
        panelButton.add(OnePicture);
        panelButton.add(TwoPictures);
        panelButton.add(Apply);
        
        OnePicture.addActionListener(new TakeOnePicture());
        TwoPictures.addActionListener(new TakeTwoPictures());
        Apply.addActionListener(new SaveAction());
        
        Container l_c = getContentPane();
        l_c.setLayout(new BorderLayout());
        l_c.add(panelImage, BorderLayout.CENTER);
        l_c.add(panelButton, BorderLayout.SOUTH);
        

        setSize(765, 630);
        setVisible(true);
        setBackground(Color.BLACK);
        setTitle("Anaglify");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
   
    }   
    
    class TakeOnePicture implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                Image1 = ImageChooser();
                Image2 = null;
                Image1.update();
                repaint();
            } catch (IOException ex) {
                ex.printStackTrace();
            }        
            OneOrTwo = true;
            
        }
        
    }
    class TakeTwoPictures implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                Image1 = ImageChooser();
                Image1.update();
                repaint();
            } catch (IOException ex) {
                ex.printStackTrace();
            }   
            try {
                Image2 = ImageChooser();
                Image2.update();
                repaint();
            } catch (IOException ex) {
                ex.printStackTrace();
            } 
            
            OneOrTwo = false;
            
        }
        
   }
        
    class SaveAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            
                   JFileChooser fileChooser = new JFileChooser();
                    fileChooser.showSaveDialog(fileChooser);
                     FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
                    fileChooser.setFileFilter(filter);
                    File file = fileChooser.getSelectedFile();
                try {
                    if (ImageResult != null) {
                        MarvinImageIO.saveImage(ImageResult, file.getCanonicalPath());
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
    }       
 
    
    class KeyAction implements KeyListener{

        @Override
        public void keyTyped(KeyEvent ke) {
            
        }
        @Override
        public void keyPressed(KeyEvent ke) {
            
            switch(ke.getKeyChar())
            {
                
                case '1':
                {
                     if(OneOrTwo ==true){
                         ColorAnaglyph temp = new ColorAnaglyph();
                        ImageResult = Image1.clone();
                        temp.process(Image1,ImageResult);
                        ImageResult.update();
                        done = true;
                        repaint();
                
                    }
                    else
                    {
                
                        ColorAnaglyph temp = new ColorAnaglyph();
                        ImageResult = Image1.clone();
                        temp.process(Image1,Image2,ImageResult);
                       ImageResult.update();
                        done = true;
                        repaint();
                            
                    }       
                }
                    break;
                case '2':
                {   
                     if(OneOrTwo ==true){
                        OptimizedAnaglyph temp = new OptimizedAnaglyph();
                        ImageResult = Image1.clone();
                        temp.process(Image1,ImageResult);
                        ImageResult.update();
                        done = true;
                        repaint();
                
                    }
                    else
                    {
                
                        OptimizedAnaglyph temp = new OptimizedAnaglyph();
                        ImageResult = Image1.clone();
                        temp.process(Image1,Image2,ImageResult);
                        ImageResult.update();
                        done = true;
                        repaint();       
                    } 
                }
                    break;
                case '3':
                {
                     if(OneOrTwo ==true){
                        GrayAnaglyph temp = new GrayAnaglyph();
                         ImageResult = Image1.clone();
                        temp.process(Image1,ImageResult);
                        ImageResult.update();
                        done = true;
                        repaint();
                
                    }
                    else
                    {
                
                        GrayAnaglyph temp = new GrayAnaglyph();
                        ImageResult = Image1.clone();
                        temp.process(Image1,Image2,ImageResult);
                        ImageResult.update();
                        done = true;
                        repaint();
                            
                    }
                }
                    break;
                    
                case '4':
                {   
                     if(OneOrTwo ==true){
                        HalfColorAnaglyph temp = new HalfColorAnaglyph();
                         ImageResult = Image1.clone();
                        temp.process(Image1,ImageResult);
                        ImageResult.update();
                        done = true;
                        repaint();
                
                    }
                    else
                    {
                
                        HalfColorAnaglyph temp = new HalfColorAnaglyph();
                       ImageResult = Image1.clone();
                        temp.process(Image1,Image2,ImageResult);
                        ImageResult.update();
                        done = true;
                        repaint();
                            
                    }             
                }
                    break;      
                    
                    default: {
                    if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        System.exit(0);
                    }
                }
                    
            }
            
        }

        @Override
        public void keyReleased(KeyEvent ke) {           
        }  
        
    }
    
    
    MarvinImage ImageChooser() throws IOException
    {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(chooser);
        File imageFile = chooser.getSelectedFile();
        MarvinImage image = MarvinImageIO.loadImage(imageFile.getCanonicalPath());
        return image;
        
    }    
    
     private void FullScreenMode(boolean i) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        dispose();
        if (i) {
            if (gd.isFullScreenSupported()) {
                setUndecorated(true);
                gd.setFullScreenWindow(this);
                this.getContentPane().setBackground(Color.black);
                panelImage.setBackground(Color.black);
            } else {
                System.out.println("Full screen is not supported");
            }
        } else {
            setUndecorated(false);
            gd.setFullScreenWindow(null);
        }
    }

    private class FullScreenListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                isfullscreen = !isfullscreen;
                if (isfullscreen) {
                    FullScreenMode(true);
                    panelButton.setVisible(false);
                } else {
                    FullScreenMode(false);
                    setVisible(true);
                    setLocationRelativeTo(null);
                    panelButton.setVisible(true);
                }
            }
        }

    }    
 
}