package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.SocketException;
import java.util.ArrayList;
//import javax.swing.*;

public class MainFrame extends JFrame {

    private JMenuBar MenuBar;
    private JMenu MenuFile, MenuShip, MenuConnect;
    private JLabel StatusBar;
    private bsSplitPane SplitPaneV;
    private viewSplitPaneH SplitPaneH;
    private bsBoard bsLeft, bsRight;
    private viewBSChatPanel bsChat;
    private viewBSSettingsPanel bsSets;

    private bsImages bsImgs;

    public MainFrame() throws SocketException {

        InitUIFrame();

    }

    private void InitUIFrame() throws SocketException {

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
        bsLeft = new bsBoard(bsImgs);
        // правая панель (противник)
        bsRight = new bsBoard(bsImgs);
        bsChat = new viewBSChatPanel();
        bsChat.Init();
        bsSets = new viewBSSettingsPanel();
        bsSets.Init();

    //    bsLeft.setPreferredSize(new Dimension(200, 40));
    //    bsRight.setPreferredSize(new Dimension(200, 400));

       // bsRight.setPreferredSize( new Dimension(200,150) );
       // bsRight.setMinimumSize ( new Dimension(200,150) );
        // сплит-панель

        SplitPaneV = new bsSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        SplitPaneH = new viewSplitPaneH(JSplitPane.VERTICAL_SPLIT);

   //     SplitPaneH.setContinuousLayout(true);
   //     SplitPaneH.setContinuousLayout(true);
    //    SplitPaneH.setOneTouchExpandable(true);

        SplitPaneV.setContinuousLayout(true);
        SplitPaneV.setOneTouchExpandable(true);
        SplitPaneV.setLeftComponent(bsLeft);
//        SplitPaneV.setRightComponent(bsRight);
        SplitPaneV.setRightComponent(bsSets);
        SplitPaneV.setResizeWeight(1.0);
        SplitPaneV.setDividerLocation(338);

        SplitPaneH.setLeftComponent(SplitPaneV);
//        SplitPaneH.setLeftComponent(bsSets);
        SplitPaneH.setRightComponent(bsChat);

        SplitPaneH.setResizeWeight(1.0);
        SplitPaneH.setDividerLocation(335);

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
        this.setSize(696, /*420*/ 560 );
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setJMenuBar(MenuBar);
        this.add(StatusBar, BorderLayout.SOUTH);
     //   Container contentPane = this.getContentPane();

        this.getContentPane().add(SplitPaneH, BorderLayout.CENTER);
      //  System.out.println("size: " + contentPane.getSize().toString());

    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            MainFrame ex = null;
            try {
                ex = new MainFrame();
            } catch (SocketException e) {
                e.printStackTrace();
            }
            ex.setVisible(true);
        });
    }

}
