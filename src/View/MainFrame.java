package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
//import javax.swing.*;

public class MainFrame extends JFrame {

    private JMenuBar MenuBar;
    private JMenu MenuFile, MenuShip, MenuConnect;
    private JLabel StatusBar;
    private JSplitPane SplitPane;
    private bsBoard bsLeft, bsRight;

    private bsImages bsImgs;

    public MainFrame() {

        InitUIFrame();

    }

    private void InitUIFrame() {

        // загрузить изображения клеток
        bsImgs = new bsImages();

        // Menu
        MenuBar = new JMenuBar();
        MenuFile = new JMenu("(F1) Игра" );
        MenuFile.setMnemonic(KeyEvent.VK_F1);
        MenuShip = new JMenu("(F2) Корабль" );
        MenuShip.setMnemonic(KeyEvent.VK_F2);
        MenuConnect = new JMenu("(F3) Сеть" );
        MenuConnect.setMnemonic(KeyEvent.VK_F3);
        MenuBar.add(MenuFile);
        MenuBar.add(MenuShip);
        MenuBar.add(MenuConnect);

        // Status Bar
        StatusBar = new JLabel("Инициализация...");
        StatusBar.setBorder(BorderFactory.createEtchedBorder());

        // левая панель (игрок)
        bsLeft = new bsBoard();
        bsLeft.SetImages(bsImgs);

        //bsLeft.setPreferredSize( new Dimension(150,150) );
        //bsLeft.setMinimumSize ( new Dimension(150,150) );
        //bsLeft.setMaximumSize( new Dimension(150,150) );
        // правая панель (противник)
        bsRight = new bsBoard();
        bsRight.SetImages(bsImgs);

    //    bsLeft.setPreferredSize(new Dimension(200, 40));
    //    bsRight.setPreferredSize(new Dimension(200, 400));

       // bsRight.setPreferredSize( new Dimension(200,150) );
       // bsRight.setMinimumSize ( new Dimension(200,150) );
        // сплит-панель
        bsSplitPane SplitPane = new bsSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        SplitPane.setContinuousLayout(true);
        SplitPane.setOneTouchExpandable(true);
        SplitPane.setLeftComponent(bsLeft);
        SplitPane.setRightComponent(bsRight);
        SplitPane.setResizeWeight(1.0);
        SplitPane.setDividerLocation(338);
      //  SplitPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));

   //     SplitPane.setEnabled(false);


   /*     PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent changeEvent) {
                JSplitPane sourceSplitPane = (JSplitPane) changeEvent
                        .getSource();
                String propertyName = changeEvent.getPropertyName();
                if (propertyName
                        .equals(JSplitPane.LAST_DIVIDER_LOCATION_PROPERTY)) {
                    int current = sourceSplitPane.getDividerLocation();
                //   System.out.println("Current: " + current);
                    Integer last = (Integer) changeEvent.getNewValue();
                //    System.out.println("Last: " + last);
                    Integer priorLast = (Integer) changeEvent.getOldValue();
                //    System.out.println("Prior last: " + priorLast);
                }
            }
        };
        SplitPane.addPropertyChangeListener(propertyChangeListener);*/

        // Main Frame init
        this.setTitle("Морской бой");
        this.setSize(696, 420 );
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setJMenuBar(MenuBar);
        this.add(StatusBar, BorderLayout.SOUTH);
     //   Container contentPane = this.getContentPane();

        this.getContentPane().add(SplitPane, BorderLayout.CENTER);
      //  System.out.println("size: " + contentPane.getSize().toString());

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var ex = new MainFrame();
            ex.setVisible(true);
        });
    }

}
