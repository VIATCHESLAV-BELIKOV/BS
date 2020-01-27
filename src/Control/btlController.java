package Control;

import Model.modBoard;
import Model.modNetwork;
import Model.modShip;
import View.btlDialog;
import View.btlImages;
import View.btlView;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import org.json.*;                          // взял с https://search.maven.org/search?q=g:org.json%20AND%20a:json&core=gav

public class btlController {

    private btlImages  bImages;
    private modBoard   mBoardLeft;
    private modBoard   mBoardRight;
    private btlView    mView;
    private Point      pt;
    private modNetwork mNetwork;

    public static final boolean BOARD_LEFT = false;
    public static final boolean BOARD_RIGHT = true;

    private static final byte CTL_MENU_EXIT = 0;
    private static final byte CTL_MENU_BEGIN = 1;
    private static final byte CTL_MENU_NAME = 2;
    private static final byte CTL_MENU_CONN_AS_SERVER = 3;
    private static final byte CTL_MENU_CONN_AS_CLIENT = 4;
    private static final byte CTL_MENU_CONN_DISCONNECT = 5;

    private static final byte POPUP_MENU_ADD_SHIP = 6;
    private static final byte POPUP_MENU_REMOVE_SHIP = 7;

    public btlController() throws ParseException {

        mBoardLeft = new modBoard();
        mBoardRight = new modBoard();
        mView = new btlView( "Морской Бой" );
        // элементы поля
        bImages = new btlImages();
        initView();

    }

    public void initView() {

        UIManager.put("OptionPane.yesButtonText"   , "Да"    );
        UIManager.put("OptionPane.noButtonText"    , "Нет"   );
        UIManager.put("OptionPane.cancelButtonText", "Отмена");

        mView.getMenuBegin().setEnabled( false );
        mView.getMenuServer().setEnabled( false );
        mView.getMenuClient().setEnabled( false );
        mView.getMenuDisconnect().setEnabled( false );

    }

    private void ctlChangeStatus( byte btElem ) {
        String  sText = "";
        boolean bArmed = false;
        switch ( btElem ) {
            case CTL_MENU_EXIT:
                bArmed = mView.getMenuExit().isArmed();
                sText = "Выход из программы";
                break;
            case CTL_MENU_BEGIN:
                bArmed = mView.getMenuBegin().isArmed();
                sText = "Начать игру";
                break;
            case CTL_MENU_NAME:
                bArmed = mView.getMenuName().isArmed();
                sText = "Сначала введите ваше имя. Можно ввести только один раз.";
                break;
            case CTL_MENU_CONN_AS_SERVER:
                bArmed = mView.getMenuServer().isArmed();
                sText = "После ввода имени заполните игровое поле кораблями и введите параметры соединения для сервера.";
                break;
            case CTL_MENU_CONN_AS_CLIENT:
                bArmed = mView.getMenuClient().isArmed();
                sText = "После ввода имени заполните игровое поле кораблями и введите параметры соединения для клиента.";
                break;
            case CTL_MENU_CONN_DISCONNECT:
                bArmed = mView.getMenuDisconnect().isArmed();
                sText = "Разорвать соединение.";
                break;
        }
        mView.getStatusBar().setText( bArmed ? sText : "" );
    }

    private byte getShipStateCode( String sState ) {
        byte ret = 0;
        switch ( sState ) {
            case "1-палубный":
                ret = modShip.SHIP_TYPE_1;
                break;
            case "2-палубный":
                ret = modShip.SHIP_TYPE_2;
                break;
            case "3-палубный":
                ret = modShip.SHIP_TYPE_3;
                break;
            case "4-палубный":
                ret = modShip.SHIP_TYPE_4;
                break;
        }
        return ret;
    }

    private void popupActionMenu( byte btElem ) {
        switch ( btElem ) {
            case POPUP_MENU_ADD_SHIP:
                btlDialog dlg = new btlDialog( mView.getFrame(), "Новый корабль" );
                dlg.AddElement("тип судна :", mView.getDialogShipType() );
                dlg.AddElement("ориентир. :", mView.getDialogShipOrnt() );
                ArrayList<String> res = dlg.run();
                if ( !res.isEmpty() ) {
                    mBoardLeft.addShip( pt, ( res.get(1).equals( "Горизонтальная" ) ? modShip.SHIP_ORIENTATION_HORZ : modShip.SHIP_ORIENTATION_VERT ), getShipStateCode( res.get(0) ) );
                    mView.getPanelLeftMap().repaint();
                    mView.getLeftInfoShipsTotal().setText( Integer.toString (mBoardLeft.getBoardShips().size() ) );
                }
                break;
            case POPUP_MENU_REMOVE_SHIP:
                if ( JOptionPane.showConfirmDialog( mView.getPanelLeftMap(),"Удалить корабль?", "Удаление", JOptionPane.YES_NO_OPTION ) == JOptionPane.YES_OPTION ) {
                    int index = mBoardLeft.getShipIndexFromPoint( pt );
                    if ( index > -1 ) {
                        mBoardLeft.removeShip(index);
                        mView.getPanelLeftMap().repaint();
                        mView.getLeftInfoShipsTotal().setText( Integer.toString (mBoardLeft.getBoardShips().size() ) );
                    }
                }
                break;
        }
    }

    public void gotNetworkMessage( JSONObject jsO ) {
        String sOper = (String)jsO.get( "operation" );
        switch ( sOper ) {
            case "close":
                // сгенерировать "disconnect"
                if ( mView.getMenuDisconnect().isEnabled() )
                   for ( ActionListener a: mView.getMenuDisconnect().getActionListeners() ) {
                       a.actionPerformed( new ActionEvent( mView.getMenuDisconnect(), ActionEvent.ACTION_PERFORMED, null ) {});
                   }
                break;
            case "setname":
                mView.getRightInfoName().setText( (String)jsO.get( "name" ) );
                break;
            case "message":
                break;
        }
    }

    public String getGamerName() {
        return mView.getLeftInfoName().getText();
    }

    public void initController() {
        // Network Init
        mNetwork = new modNetwork( this );
        // Dialog Text Box
        mView.getDialogName().addKeyListener( new KeyAdapter() {
            @Override
            public void keyTyped( KeyEvent e ) {
                JTextField tf = (JTextField)e.getSource();
                char c = e.getKeyChar();
                if ( tf.getText().length() == 0 ) {

                }
                else {
                    if ( ( tf.getText().length() < 14 ) && ( ( Character.isDigit(c) || Character.isAlphabetic(c) ) ) )
                        super.keyTyped(e);
                    else
                        e.consume();
                }
            }
        });
        // Dialog Ships ComboBox
        mView.getDialogShipType().addItemListener( new ItemListener() {
            public void itemStateChanged( ItemEvent itemEvent ) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    byte btType = getShipStateCode( itemEvent.getItem().toString() );
                    mView.getDialogShipOrnt().removeAllItems();
                    if ( mBoardLeft.IsAvailableNewShip( pt, btType, modShip.SHIP_ORIENTATION_VERT ) )
                        mView.getDialogShipOrnt().addItem( ( btType == modShip.SHIP_TYPE_1 ) ? "-" : "Вертикальная" );
                    if ( ( btType != modShip.SHIP_TYPE_1 ) && mBoardLeft.IsAvailableNewShip( pt, btType, modShip.SHIP_ORIENTATION_HORZ ) )
                        mView.getDialogShipOrnt().addItem( "Горизонтальная" );
//                    System.out.println(String.format("Combobox: Selected; %s", itemEvent.getItem().toString()));
                }
            }
        });

        // menu actions
        mView.getMenuExit().addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if ( mNetwork != null ) mNetwork.CloseConnect();
                System.exit(0);
            }
        });
        mView.getMenuBegin().addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                // start game
            }
        });
        mView.getMenuName().addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                ArrayList<String> sArrList;
                btlDialog dlg = new btlDialog( mView.getFrame(), "Введите ваше имя" );
                dlg.AddElement("имя :", mView.getDialogName() );
                sArrList = dlg.run();
                if ( sArrList.size() == 1 ) {
                    mView.getLeftInfoName().setText( sArrList.get(0) );
                    mView.getMenuName().setEnabled( false );
                    mView.getMenuServer().setEnabled( true );
                    mView.getMenuClient().setEnabled( true );
                }
            }
        });
        mView.getMenuServer().addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                ArrayList<String> sArrList = modNetwork.EnumNetworkInterfaces();
                mView.getDialogInterfaces().removeAllItems();
                if ( sArrList.size() > 0 ) {
                    sArrList.forEach( (a) -> mView.getDialogInterfaces().addItem( a ) );
                    btlDialog dlg = new btlDialog( mView.getFrame(), "Сервер : Соединение" );
                    dlg.AddElement("интерфейс :",mView.getDialogInterfaces() );
                    dlg.AddElement("     порт :", mView.getDialogConnPort() );
                    sArrList.clear();
                    sArrList = dlg.run();
                    if  ( ( sArrList.size() == 2 ) &&  mNetwork.OpenConnect( true, sArrList.get(0), sArrList.get(1) ) ) {
                        mView.getMenuServer().setEnabled( false );
                        mView.getMenuClient().setEnabled( false );
                        mView.getMenuDisconnect().setEnabled( true );
                    }
                }
            }
        });
        mView.getMenuClient().addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                btlDialog dlg = new btlDialog( mView.getFrame(), "Сервер : Соединение" );
                dlg.AddElement("IP сервера :",mView.getDialogDialogClientIP() );
                dlg.AddElement("     порт  :", mView.getDialogConnPort() );
                ArrayList<String> sArrList = dlg.run();
                if (  sArrList.size() == 2 )
                    if ( mNetwork.OpenConnect( false, sArrList.get(0), sArrList.get(1) ) ) {
                        mView.getMenuServer().setEnabled(false);
                        mView.getMenuClient().setEnabled(false);
                        mView.getMenuDisconnect().setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog( mView.getPanelRightMap(), "Не найден сервер."  );
                    }

            }
        });
        mView.getMenuDisconnect().addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                if ( mNetwork != null ) {
 //System.out.println("socket close?");
                    mNetwork.sendMsg("{ \"operation\" : \"close\" }" );
                    mNetwork.CloseConnect();
                    mView.getMenuServer().setEnabled( true );
                    mView.getMenuClient().setEnabled( true );
                    mView.getMenuDisconnect().setEnabled( false );
                    mView.getRightInfoName().setText( "" );
                    mView.getRightInfoShipsTotal().setText( "" );
                    mView.getRightInfoShipsKilled().setText( "" );
                    mBoardRight.resetBoard();
                    mView.getPanelRightMap().repaint();
                    JOptionPane.showMessageDialog( mView.getPanelRightMap(), "Соединение разорвано."  );
                }
            }
        });

        // status bar
        mView.getMenuExit().addChangeListener( (a) -> ctlChangeStatus( CTL_MENU_EXIT ) );
        mView.getMenuBegin().addChangeListener( (a) -> ctlChangeStatus( CTL_MENU_BEGIN ) );
        mView.getMenuName().addChangeListener( (a) -> ctlChangeStatus( CTL_MENU_NAME ) );
        mView.getMenuServer().addChangeListener( (a) -> ctlChangeStatus( CTL_MENU_CONN_AS_SERVER ) );
        mView.getMenuClient().addChangeListener( (a) -> ctlChangeStatus( CTL_MENU_CONN_AS_CLIENT ) );

        // mouse panel listener (left)
        mView.getPanelLeftMap().addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( MouseEvent e ) {
                super.mouseClicked(e);
                if ( mView.getMenuBegin().isEnabled() ) return;
              //  if ( !( e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 1 ) ) return;
                pt = new Point( (int) Math.floor( e.getX() / bImages.SIZE_X ), (int) Math.floor( e.getY() / bImages.SIZE_Y ) );
                if ( mBoardLeft.getBoardShips().size() < 14 ) {                             // max 14 ships
                    mView.getDialogShipType().removeAllItems();
                    // convert position to map coords
                    if ( mBoardLeft.countShipsByType(modShip.SHIP_TYPE_1) < 5 )
                        if (mBoardLeft.IsAvailableNewShip(pt, modShip.SHIP_TYPE_1, modShip.SHIP_ORIENTATION_HORZ))
                            mView.getDialogShipType().addItem( "1-палубный" );
                    if ( mBoardLeft.countShipsByType( modShip.SHIP_TYPE_2 ) < 4 )
                        if ( mBoardLeft.IsAvailableNewShip( pt, modShip.SHIP_TYPE_2, modShip.SHIP_ORIENTATION_HORZ) || mBoardLeft.IsAvailableNewShip(pt, modShip.SHIP_TYPE_2, modShip.SHIP_ORIENTATION_VERT ) )
                            mView.getDialogShipType().addItem( "2-палубный" );
                    if ( mBoardLeft.countShipsByType( modShip.SHIP_TYPE_3 ) < 3 )
                        if ( mBoardLeft.IsAvailableNewShip( pt, modShip.SHIP_TYPE_3, modShip.SHIP_ORIENTATION_HORZ) || mBoardLeft.IsAvailableNewShip(pt, modShip.SHIP_TYPE_3, modShip.SHIP_ORIENTATION_VERT ) )
                            mView.getDialogShipType().addItem( "3-палубный" );
                    if ( mBoardLeft.countShipsByType( modShip.SHIP_TYPE_4 ) < 2 )
                        if ( mBoardLeft.IsAvailableNewShip( pt, modShip.SHIP_TYPE_4, modShip.SHIP_ORIENTATION_HORZ) || mBoardLeft.IsAvailableNewShip(pt, modShip.SHIP_TYPE_4, modShip.SHIP_ORIENTATION_VERT ) )
                            mView.getDialogShipType().addItem( "4-палубный" );
                }
                mView.getContextPopup().getComponent(0).setEnabled( ( (mView.getDialogShipType().getItemCount() > 0) && (mBoardLeft.getBoardShips().size() < 14) ) ? true : false );
                mView.getContextPopup().getComponent(2 ).setEnabled( ( mBoardLeft.getShipIndexFromPoint( pt ) == -1 ) ? false : true );
                mView.getContextPopup().show( e.getComponent(), e.getX(), e.getY() );
            }
        });

        // Popup menu : add ship
        ((JMenuItem)mView.getContextPopup().getComponent(0 )).addActionListener( (a) -> popupActionMenu( POPUP_MENU_ADD_SHIP ) );

        // Popup menu : remove ship
        ((JMenuItem)mView.getContextPopup().getComponent(2 )).addActionListener( (a) -> popupActionMenu( POPUP_MENU_REMOVE_SHIP ) );

        // Set variables of Map panel (left)
        mView.getPanelLeftMap().setVariables( bImages, BOARD_LEFT, mBoardLeft.getBoardMap() );
        mView.getPanelLeftMap().repaint();
        mView.getLeftInfoShipsTotal().setText( Integer.toString (mBoardLeft.getBoardShips().size() ) );

        // Set variables of Map panel (right)
        mView.getPanelRightMap().setVariables( bImages, BOARD_RIGHT, mBoardRight.getBoardMap() );
        mView.getPanelRightMap().repaint();
    }


}
