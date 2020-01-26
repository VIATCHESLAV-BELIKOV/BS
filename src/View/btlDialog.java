package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class btlDialog extends JDialog implements ActionListener {

    private JPanel                     dialogPanel;
    private JButton                    dialogBtnOk, dialogBtnCancel;
    private GridBagConstraints         gbc;
    private ArrayList<JComponent>      dialogComponents;
    private ArrayList<String>          retS;

    public btlDialog( Frame frameParent, String sTitle ) {
        super( frameParent, sTitle,true );
        setResizable(false);
        dialogPanel = new JPanel();
        dialogPanel.setLayout( new GridBagLayout() );
        Point pt = frameParent.getLocation();
        setLocation(pt.x+80,pt.y+80 );
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,2);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialogPanel.add( new JLabel(" "), gbc );
        dialogBtnOk = new JButton("Ok" );
        dialogBtnOk.addActionListener(this );
        dialogBtnCancel = new JButton("Cancel" );
        dialogBtnCancel.addActionListener(this );
        dialogComponents = new ArrayList<>();
        retS = new ArrayList<>();
    }

    public void AddElement( String sLabel, JComponent jComponent ) {
//        dialogLabels.add( new JLabel( sLabel ) );
        dialogComponents.add( jComponent );
        int i =  dialogComponents.isEmpty() ? 0: dialogComponents.size() - 1;
        gbc.gridx = 0;
        gbc.gridy = i + 1;
        dialogPanel.add( new JLabel( sLabel ), gbc );
        gbc.gridx = 1;
        gbc.gridy = i + 1;
        gbc.gridwidth = 5;
        dialogPanel.add( dialogComponents.get(i), gbc );
    }

    public void actionPerformed(ActionEvent a) {
        String s;
        if ( a.getSource() == dialogBtnOk ) {
            for ( JComponent component : dialogComponents ) {
                switch (component.getClass().getSimpleName()) {
                    case "JTextField":
                        s = ((JTextField)component).getText();
                        break;
                    case "JComboBox":
                        s = String.valueOf( ((JComboBox)component).getSelectedItem() );
                        break;
                    case "JSpinner":
                        s = ((JSpinner)component).getValue().toString();
                        break;
                    default:
                        s = component.getClass().getSimpleName();
                        break;
                }
                retS.add( s );
            }
        }
//      else {
//          retS = null;
//      }
        dialogComponents.clear();
        dispose();
    }

    public ArrayList<String> run() {
        int i = dialogComponents.isEmpty() ? 0 : dialogComponents.size();
        gbc.gridx = 0;
        gbc.gridy = i + 1;
        dialogPanel.add( new JLabel(" " ), gbc );
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.gridy = i + 2;
        dialogPanel.add( dialogBtnOk, gbc );
        gbc.gridx = 4;
        gbc.gridy = i + 2;
        dialogPanel.add( dialogBtnCancel, gbc );
        getContentPane().add( dialogPanel );
        pack();
        setVisible( true );
        return retS;
    }

}
