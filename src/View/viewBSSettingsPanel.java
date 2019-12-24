package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;


public class viewBSSettingsPanel extends JPanel {

    private JTextField viewBSSettings_Name;
    private int        viewBSSettings_Name_length_max;
    private JTextField viewBSSettings_IP;
    private JTextField viewBSSettings_Port;
    private JButton    viewBSSettings_btnConnect;
    private JComboBox  viewBSSettings_cbb_ConnType;
    private JComboBox  viewBSSettings_cbb_ConnInterfaces;
    private JSpinner   viewBSSettings_spn_ConnPort;

    GridBagConstraints gridBC;

    public void Init() throws SocketException {

        JLabel             viewBSSettins_lbl_Name = new JLabel("Ваше имя :");
        JLabel             viewBSSettins_lbl_ConnType = new JLabel("Соединение :");
        Enumeration<NetworkInterface> viewBSSettins_enumInterfaces = NetworkInterface.getNetworkInterfaces();
        String             viewBSSettins_str_ConnType[] = { "сервер", "клиент" };

        viewBSSettings_cbb_ConnType = new JComboBox( viewBSSettins_str_ConnType );
        viewBSSettings_cbb_ConnInterfaces = new JComboBox();
        viewBSSettings_spn_ConnPort = new JSpinner( new SpinnerNumberModel(9000, 1025, 65535, 1) );

        for ( NetworkInterface netInterface : Collections.list(viewBSSettins_enumInterfaces)) {
            if (netInterface.isUp()) {
                for (InterfaceAddress interfaceAddress : netInterface.getInterfaceAddresses()) {
                    if ( !( interfaceAddress.getBroadcast() == null && interfaceAddress.getNetworkPrefixLength() != 8 ) ) {
                        System.out.println(String.format("IPv4: %s; Subnet Mask: %s; Broadcast: %s", interfaceAddress.getAddress(), interfaceAddress.getNetworkPrefixLength(), interfaceAddress.getBroadcast()));
                        viewBSSettings_cbb_ConnInterfaces.addItem(interfaceAddress.getAddress().getHostAddress());
                    } else {
                        System.out.println(String.format("IPv6: %s; Network Prefix Length: %s", interfaceAddress.getAddress(), interfaceAddress.getNetworkPrefixLength()));
                    }
                }
            }
        }
       //     viewBSSettings_cbb_ConnInterfaces.addItem( netInterface.getho );


        GridBagConstraints gridBC;

        setLayout(new GridBagLayout());
        gridBC = new GridBagConstraints();

        gridBC.fill = GridBagConstraints.HORIZONTAL;
        gridBC.gridx = 0;
        gridBC.gridy = 0;
        add(viewBSSettins_lbl_Name, gridBC);

        gridBC.fill = GridBagConstraints.BOTH;
        gridBC.gridx = 0;
        gridBC.gridy = 1;
        gridBC.gridwidth = 2;
        gridBC.gridheight = 1;
        add(viewBSSettins_lbl_ConnType, gridBC);

        gridBC.fill = GridBagConstraints.CENTER;
        gridBC.gridx = 1;
        gridBC.gridy = 2;
        gridBC.insets = new Insets(40, 1, 0, 1);
        add(viewBSSettings_cbb_ConnType, gridBC);

        gridBC.fill = GridBagConstraints.CENTER;
        gridBC.gridx = 2;
        gridBC.gridy = 2;
        gridBC.insets = new Insets(40, 1, 0, 1);
        add(viewBSSettings_cbb_ConnInterfaces, gridBC);

        gridBC.fill = GridBagConstraints.CENTER;
        gridBC.gridx = 3;
        gridBC.gridy = 2;
        gridBC.insets = new Insets(40, 1, 0, 1);
        add(viewBSSettings_spn_ConnPort, gridBC);

    }
}
