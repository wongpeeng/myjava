package mychat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Chat implements Runnable{
    private JFrame frame=new JFrame("wongpeeng's first chat program");
    private JLabel contentLabel=new JLabel("chat content:");
    private JLabel sendLabel=new JLabel("what to say:");
    private JButton saveButton=new JButton("save");
    private JButton sendButton=new JButton("send");
    private JButton disconButton=new JButton("disconnect");
    private JTextArea contentArea=new JTextArea(20,20);//row,col
    private JTextArea sendArea=new JTextArea(20,20);
    private JDialog dialog=new JDialog(frame,"Configuration");
    private Thread netThread=new Thread(this);
    private Config config=new Config();
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
        JLabel dIpLabel=new JLabel("destination IP");
        JLabel dPortLabel=new JLabel("destination port");
        JLabel nicknameL=new JLabel("my nickname");
        JLabel myPortL=new JLabel("my port");
        JTextField dIpField=new JTextField(15);
        JTextField dPort=new JTextField(8);
        JTextField nickNameF=new JTextField(20);
        JTextField myPortF=new JTextField(8);
        dPort.setText("2044");
        JButton connButton=new JButton("Connect");
        dpanel.add(dIpLabel);
        dpanel.add(dIpField);
        dpanel.add(dPortLabel);
        dpanel.add(dPort);
        dpanel.add(myPortL);
        dpanel.add(myPortF);
        dpanel.add(nicknameL);
        dpanel.add(nickNameF);
        dpanel.add(new JLabel("     "));
        dpanel.add(connButton);
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
        
        netThread.start();
/********add listener*****/
        connButton.addActionListener(new ConnListener(dIpField,dPort,myPortF,nickNameF));//directly jtextfield as input,because object is a reference,like pointer,not a copy,they are the same one here.
    }//init

/*******inner actionlistener class****/
class ConnListener implements ActionListener{
        private JTextField dIp;
        private JTextField dPort;
        private JTextField myPort;
        private JTextFiedl myName;
        ConnListener(JTextField toIp,JTextField toPort,,JTextField sPort,JTextField sName){
            myIp=ip;
            myPort=port;
            myName=name;
        }
        public void actionPerformed(ActionEvent e){
            config.set(myIp.getText(),myPort.getText(),myName.getText());
            dialog.setVisible(false);
        }
    }//ConnListerner


/********socket*********/
    public void run(){
        System.out.println("OK");
    }



}
/*******configuration class ******/
class Config{
    private String ipAddr;
    private String inPort;
    private String inNickName;
    public void set(String ip,String port,String name){
        ipAddr=ip;
        inPort=port;
        inNickName=name;
    }   
}
