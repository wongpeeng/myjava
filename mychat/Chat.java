package mychat;

import javax.swing.*;
import java.awt.*;



public class Chat{
    private JFrame frame=new JFrame("wongpeeng's first chat program");
    private JLabel contentLabel=new JLabel("chat content:");
    private JLabel sendLabel=new JLabel("what to say:");
    private JButton saveButton=new JButton("save");
    private JButton sendButton=new JButton("send");
    private JButton disconButton=new JButton("disconnect");
    private JTextArea contentArea=new JTextArea(20,20);//row,col
    private JTextArea sendArea=new JTextArea(20,20);
    private JDialog dialog=new JDialog(frame,"Configuration");
    public Chat(){
       // System.out.println("hello!");
       initGui();
    }

/*****init chat GUI******/
    private void initGui(){
        int w,h;
        w=600;
        h=600;
        frame.setBounds(100,100,w,h);//100,100 screen location, w=width,h=height
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container fpanel=frame.getContentPane();
        //fpanel.setLayout(new BoxLayout(fpanel,BoxLayout.Y_AXIS));
        frame.setLayout(new GridLayout(3,1,10,15));  // grid layout row ,col,hgao,vgap
        frame.setVisible(true);
        frame.setResizable(true);

        dialog.setBounds(200,200,300,250);
        dialog.setLayout(new GridLayout(4,2,10,10));
        Container dpanel=dialog.getContentPane();
        JLabel ipLabel=new JLabel("destination IP");
        JLabel portLabel=new JLabel("destination port");
        JLabel nicknameL=new JLabel("my nickname");
        JTextField ipField=new JTextField(15);
        JTextField port=new JTextField(8);
        JTextField nicknameF=new JTextField(20);
        port.setText("2044");
        JButton sureButton=new JButton("sure");
        dpanel.add(ipLabel);
        dpanel.add(ipField);
        dpanel.add(portLabel);
        dpanel.add(port);
        dpanel.add(nicknameL);
        dpanel.add(nicknameF);
        dpanel.add(new JLabel("     "));
        dpanel.add(sureButton);
        dialog.setVisible(true);//must set visible after adding,not before


        JPanel contentPanel=new JPanel();
        JPanel sendPanel=new JPanel();
        JPanel buttonPanel=new JPanel();
        contentPanel.setLayout(new GridLayout(2,1,10,15));
        contentPanel.add(contentLabel);
        contentPanel.add(contentArea);
        sendPanel.setLayout(new GridLayout(2,1,10,15));
        sendPanel.add(sendLabel);
        sendPanel.add(sendArea);
        buttonPanel.setLayout(new GridLayout(1,3,10,10));
        buttonPanel.add(disconButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(sendButton);

        fpanel.add(contentPanel);
        fpanel.add(sendPanel);
        fpanel.add(buttonPanel);
        frame.setVisible(true);//set true after adding not before

    }
}
