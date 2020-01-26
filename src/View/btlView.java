package View;

import Model.modBoard;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.text.ParseException;

public class btlView {

    private JTextField          bDialogName;
    private JComboBox           bDialogShipType;
    private JComboBox           bDialogShipOrnt;
    private JComboBox           bDialogInterfaces;
//    private JFormattedTextField bDialogClientIP;
    private JTextField          bDialogClientIP;
    private JSpinner            bDialogConnPort;

    private JPopupMenu          bContextPopup;

    private JFrame              bFrame;
    private JMenuBar            bMainMenuBar;
    private JMenu               bMenuGame, bMenuSets, bMSConnect;
    private JMenuItem           bMGExit, bMGBegin;
    private JMenuItem           bMSName, bMSCServer, bMSCClient, bMSCDisconnect;
    private JLabel              bStatusBar;
    private JPanel              bPanelMain;
    private btlMapPanel         bPanelLeftMap, bPanelRightMap;
    private JLabel              bLeftInfoName, bLeftInfoShipsTotal, bLeftInfoShipsKilled;
    private JLabel              bRightInfoName, bRightInfoShipsTotal, bRightInfoShipsKilled;

    public btlView( String sTitle ) {

        bDialogName = new JTextField(15);
        bDialogShipType = new JComboBox();
        bDialogShipOrnt = new JComboBox();
        bDialogInterfaces = new JComboBox();
//        bDialogClientIP = new JFormattedTextField( new MaskFormatter("###.###.###.###") );
        bDialogClientIP = new JTextField();
        bDialogConnPort = new JSpinner( new SpinnerNumberModel(9000, 1025, 65535, 1) );

        bContextPopup = new JPopupMenu();
        bContextPopup.add( "Добавить" );
        bContextPopup.addSeparator();
        bContextPopup.add( "Удалить" );

        bFrame = new JFrame(sTitle);
        bFrame.setSize(640, /*560*/ 560 );
        bFrame.setResizable( false );
        bFrame.setLocationRelativeTo( null );
        bFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        // основное поле
        bPanelMain = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,1,2,1);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // левая панель (info)
        bLeftInfoName = new JLabel("" );
        bLeftInfoShipsTotal = new JLabel("" );
        bLeftInfoShipsKilled = new JLabel("" ); bLeftInfoShipsKilled.setForeground( Color.red );
        gbc.gridx = 0; gbc.gridy = 0;
        bPanelMain.add( InfoPanel( "                  Ваше имя :", bLeftInfoName, bLeftInfoShipsTotal, bLeftInfoShipsKilled ), gbc );
        // правая панель (info)
        bRightInfoName = new JLabel(" " );
        bRightInfoShipsTotal = new JLabel("" );
        bRightInfoShipsKilled = new JLabel("" ); bRightInfoShipsKilled.setForeground( Color.red );
        gbc.gridx = 1; gbc.gridy = 0;
        bPanelMain.add( InfoPanel( "                Противник :", bRightInfoName, bRightInfoShipsTotal, bRightInfoShipsKilled ), gbc );
        // левая панель (карта)
        gbc.gridx = 0; gbc.gridy = 1;
        bPanelLeftMap = MapPanel();
        bPanelMain.add( bPanelLeftMap, gbc );
        // правая панель (карта)
        gbc.gridx = 1; gbc.gridy = 1;
        bPanelRightMap = MapPanel();
        bPanelMain.add( bPanelRightMap, gbc );
        // добавить панель к фрейму
        bFrame.getContentPane().add( bPanelMain );
        bMainMenuBar = BuildMenuBar();
        bFrame.setJMenuBar( bMainMenuBar );
        // Status Bar
        bStatusBar = new JLabel(" " );
        bStatusBar.setBorder(BorderFactory.createEtchedBorder());
        bFrame.add(bStatusBar, BorderLayout.SOUTH);
        bFrame.setVisible(true);
    }

    private JPanel InfoPanel( String sNameLabel, JLabel iName, JLabel iTotal, JLabel iKilled ) {

        JPanel buildPanelInfo = new JPanel();
        SpringLayout spl = new SpringLayout();
        JLabel lbl1 = new JLabel( sNameLabel, SwingConstants.RIGHT );
        JLabel lbl2 = new JLabel("всего и потоплено :", SwingConstants.RIGHT );

        buildPanelInfo.setLayout( spl );
        buildPanelInfo.setBorder( BorderFactory.createEtchedBorder() );
        buildPanelInfo.setPreferredSize( new Dimension(302,50 ) );
        buildPanelInfo.add( lbl1 );
        buildPanelInfo.add( iName );
        buildPanelInfo.add( lbl2 );
        buildPanelInfo.add( iTotal );
        buildPanelInfo.add( iKilled );

        spl.putConstraint( SpringLayout.WEST, lbl1, 5, SpringLayout.WEST, buildPanelInfo );
        spl.putConstraint( SpringLayout.NORTH, lbl1, 4, SpringLayout.NORTH, buildPanelInfo );

        spl.putConstraint( SpringLayout.WEST , iName, 10, SpringLayout.EAST , lbl1 );
        spl.putConstraint( SpringLayout.NORTH, iName, 4, SpringLayout.NORTH, buildPanelInfo );

        spl.putConstraint( SpringLayout.WEST, lbl2, 5, SpringLayout.WEST, buildPanelInfo );
        spl.putConstraint( SpringLayout.NORTH, lbl2, 20, SpringLayout.NORTH, lbl1 );

        spl.putConstraint( SpringLayout.WEST, iTotal, 10, SpringLayout.EAST, lbl2 );
        spl.putConstraint( SpringLayout.NORTH, iTotal, 20, SpringLayout.NORTH, iName );

        spl.putConstraint( SpringLayout.WEST , iKilled, 10, SpringLayout.EAST , iTotal );
        spl.putConstraint( SpringLayout.NORTH, iKilled, 20, SpringLayout.NORTH, iName );

        return buildPanelInfo;
    }

    private btlMapPanel MapPanel() {

        btlMapPanel buildPanelMap = new btlMapPanel();

        buildPanelMap.setBorder( BorderFactory.createEtchedBorder() );
        buildPanelMap.setPreferredSize( new Dimension(302,302 ) );
        return buildPanelMap;
    }

    private JMenuBar BuildMenuBar() {
        JMenuBar buildMenuBar = new JMenuBar();
        //    Игра
        bMenuGame = new JMenu("G Игра" );
        bMenuGame.setMnemonic( KeyEvent.VK_G );
        buildMenuBar.add( bMenuGame );
        //    Игра - Выход
        bMGExit = new JMenuItem("E Выход" );
        bMGExit.setMnemonic( KeyEvent.VK_E );
        bMenuGame.add( bMGExit );
        //    <разделитель>
        bMenuGame.addSeparator();
        //    Игра - Начать игру
        bMGBegin = new JMenuItem("B Начать игру" );
        bMGBegin.setMnemonic( KeyEvent.VK_B );
        bMenuGame.add( bMGBegin );
        //    Настройки
        bMenuSets = new JMenu( "S Настройки" );
        bMenuSets.setMnemonic( KeyEvent.VK_S );
        buildMenuBar.add( bMenuSets );
        //    Настройки - Ваше имя
        bMSName = new JMenuItem("N Ваше имя" );
        bMSName.setMnemonic( KeyEvent.VK_N );
        bMenuSets.add( bMSName );
        //    <разделитель>
        bMenuSets.addSeparator();
        //    Настройки - Соединение
        bMSConnect = new JMenu( "C Соединение" );
        bMSConnect.setMnemonic( KeyEvent.VK_C );
        bMenuSets.add( bMSConnect );
        //    Настройки - Соединение - Сервер
        bMSCServer = new JMenuItem("R Сервер" );
        bMSCServer.setMnemonic( KeyEvent.VK_R );
        bMSConnect.add( bMSCServer );
        //    Настройки - Соединение - Клиент
        bMSCClient = new JMenuItem("L Клиент" );
        bMSCClient.setMnemonic( KeyEvent.VK_L );
        bMSConnect.add( bMSCClient );
        //    <разделитель>
        bMSConnect.addSeparator();
        //    Настройки - Соединение - Клиент
        bMSCDisconnect = new JMenuItem("D Разъединить" );
        bMSCDisconnect.setMnemonic( KeyEvent.VK_D );
        bMSConnect.add( bMSCDisconnect );
        return buildMenuBar;
    }

    public JTextField getDialogName () { return bDialogName; }
    public JPopupMenu getContextPopup () { return bContextPopup; }
    public JComboBox getDialogShipType () { return bDialogShipType; }
    public JComboBox getDialogShipOrnt () { return bDialogShipOrnt; }
    public JComboBox getDialogInterfaces () { return bDialogInterfaces; }
//    public JFormattedTextField getDialogDialogClientIP() { return bDialogClientIP; }
    public JTextField getDialogDialogClientIP() { return bDialogClientIP; }
    public JSpinner getDialogConnPort () { return  bDialogConnPort; }

    public JFrame getFrame () { return bFrame; }

    public btlMapPanel getPanelLeftMap() { return bPanelLeftMap; }
    public btlMapPanel getPanelRightMap() { return bPanelRightMap;}

    public JLabel getLeftInfoName () { return bLeftInfoName; }
    public JLabel getLeftInfoShipsTotal () { return bLeftInfoShipsTotal; }
    public JLabel getLeftInfoShipsKilled () { return bLeftInfoShipsKilled; }
    public JLabel getRightInfoName () { return bRightInfoName; }
    public JLabel getRightInfoShipsTotal () { return bRightInfoShipsTotal; }
    public JLabel getRightInfoShipsKilled () { return bRightInfoShipsKilled; }

    public JMenuItem getMenuExit() { return bMGExit; }
    public JMenuItem getMenuBegin() { return bMGBegin; }
    public JMenuItem getMenuName() { return bMSName; }
    public JMenuItem getMenuServer() { return bMSCServer; }
    public JMenuItem getMenuClient() { return bMSCClient; }
    public JMenuItem getMenuDisconnect() { return bMSCDisconnect; }

    public JLabel getStatusBar() { return bStatusBar; }

}
