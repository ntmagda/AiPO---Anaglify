package tools;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinPluginHistory;
import tools.ColorHistogram;
import tools.GrayHistogram;

/**
 * History sample
 *
 * @author Gabriel Ambrosio Archanjo
 *
 */
public abstract class BaseFrame1 extends JFrame {

    // GUI
    protected JButton buttonShowHistory;
    protected JButton buttonApply;
    // Marvin Objects
    protected MarvinPluginHistory history;
    protected MarvinImagePlugin tempPlugin;
    protected MarvinImage originalImage;
    protected MarvinImage resultImage;
    protected MarvinImagePanel imagePanelOriginal,
            imagePanelNew;

    public BaseFrame1(String filename) {
        super("Plug-in History Sample");
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }

        ButtonHandler buttonHandler = new ButtonHandler();
        buttonShowHistory = new JButton("Show History");
        buttonShowHistory.addActionListener(buttonHandler);
        buttonApply = new JButton("Apply");
        buttonApply.addActionListener(buttonHandler);

        JPanel l_panelBottom = new JPanel();
        l_panelBottom.add(buttonApply);
        l_panelBottom.add(buttonShowHistory);

        imagePanelOriginal = new MarvinImagePanel();
        imagePanelNew = new MarvinImagePanel();

        JPanel l_panelTop = new JPanel();
        l_panelTop.add(imagePanelOriginal);
        l_panelTop.add(imagePanelNew);

        Container l_c = getContentPane();
        l_c.setLayout(new BorderLayout());
        l_c.add(l_panelTop, BorderLayout.NORTH);
        l_c.add(l_panelBottom, BorderLayout.SOUTH);

        originalImage = MarvinImageIO.loadImage(filename);
        imagePanelOriginal.setImage(originalImage);
        imagePanelNew.setPreferredSize(imagePanelOriginal.getPreferredSize());

        history = new MarvinPluginHistory();

        setSize(765, 630);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * dodawanie aktualnego stanu zdjęcia do historii
     */
    protected void addToHistory(String name) {
        history.addEntry(name, resultImage, tempPlugin.getAttributes());
    }

    /**
     * przetwarzanie zdjęcia
     */
    protected abstract void process();

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == buttonApply) {
                process();
            } else if (e.getSource() == buttonShowHistory) {
                if (history != null) {
                    history.showThumbnailHistory("History");
                }
            }
        }
    }
}
