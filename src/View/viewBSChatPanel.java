package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class viewBSChatPanel extends JPanel {


    private JTextArea  viewBSChat_textArea;
    private JTextField viewBSChat_inputTextField;


 //   private JButton sendButton;

    public void Init() {

        JLabel             viewBSChat_label;
        JScrollPane        viewBSChat_scrollPane;
        GridBagConstraints gridBC;

        setLayout(new GridBagLayout());
        gridBC = new GridBagConstraints();

    //    EmptyBorder emptyBorder = new EmptyBorder(new Insets(10, 10, 10, 10));

        viewBSChat_textArea = new JTextArea(5, 50);
  //      viewBSChat_textArea.setBorder(emptyBorder);
        viewBSChat_textArea.setLineWrap(true);
        viewBSChat_textArea.setWrapStyleWord(true);
        viewBSChat_textArea.setEditable(false);
        viewBSChat_scrollPane = new JScrollPane( viewBSChat_textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        viewBSChat_scrollPane.setBounds(0,0, 300, 50);

        gridBC.fill = GridBagConstraints.HORIZONTAL;
        gridBC.gridx = 1;
        gridBC.gridy = 0;
        add(viewBSChat_scrollPane, gridBC);

        viewBSChat_label = new JLabel(">> : ");
        gridBC.fill = GridBagConstraints.HORIZONTAL;
        gridBC.gridx = 0;
        gridBC.gridy = 1;
        add(viewBSChat_label, gridBC);

        viewBSChat_inputTextField = new JTextField(50);
        viewBSChat_inputTextField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
           //     String fromUser = userInputField.getText();
           //     if (fromUser != null) {
                viewBSChat_textArea.append("Asim: " + viewBSChat_inputTextField.getText() + "\n");
                viewBSChat_inputTextField.setText("");
                }
            });
        viewBSChat_inputTextField.setFocusable(true);
        gridBC.fill = GridBagConstraints.HORIZONTAL;
        gridBC.weightx = 1.0;
        gridBC.gridx = 1;
        gridBC.gridy = 1;
        add(viewBSChat_inputTextField, gridBC);
    }
}
