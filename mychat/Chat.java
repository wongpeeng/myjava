package mychat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Chat implements Runnable{
    private JFrame frame=new JFrame("wongpeeng's first chat program");
    private JLabel contentLabel=new JLabel("chat content:");
    private JLabel sendLabel=new JLabel("what to say:");
    private JButton saveButton=new JButton("save");
    private JButton sendButton=new JButton("send");
    private JButton disconButton=new JButton("disconnect");
    private JTextArea contentArea=new JTextArea(20,20);//row,col
    private JScrollPane scroll=new JScrollPane(contentArea);
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
        dialog.setLayout(new GridLayout(5,2,10,10));
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
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        contentPanel.add(scroll);
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
        
/********add listener*****/
        connButton.addActionListener(new ConnListener(dIpField,dPort,myPortF,nickNameF));//directly jtextfield as input,because object is a reference,like pointer,not a copy,they are the same one here.
        sendButton.addActionListener(new SendListener());

    }//init

/*******inner actionlistener class****/
class ConnListener implements ActionListener{
        private JTextField dIp;
        private JTextField dPort;
        private JTextField myPort;
        private JTextField myName;
        ConnListener(JTextField toIp,JTextField toPort,JTextField sPort,JTextField sName){
            dIp=toIp;
            dPort=toPort;
            myName=sName;
            myPort=sPort;
        }
        public void actionPerformed(ActionEvent e){
            config.set(dIp.getText(),dPort.getText(),myPort.getText(),myName.getText());
            netThread.start();
            dialog.setVisible(false);
        }
 }//ConnListerner
class SendListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                byte[] buf=sendArea.getText().trim().getBytes();
                DatagramPacket dp=new DatagramPacket(buf,buf.length,InetAddress.getByName(config.getDIp()),Integer.parseInt(config.getDPort()));
                DatagramSocket ds=new DatagramSocket();
                ds.send(dp);
                contentArea.append("I say:"+sendArea.getText()+"\n");
                sendArea.setText("");
                ds.close();
            }
            catch(Exception ee){}
        }
}

/********socket*********/
    public void run(){
        //System.out.println("OK");
        try{
            DatagramSocket ds=new DatagramSocket(Integer.parseInt(config.getMyPort()));
            byte[] buf=new byte[1024];
            DatagramPacket dp=new DatagramPacket(buf,buf.length);
            ds.receive(dp);
            while(true){
                ds.receive(dp);
                String message=new String(dp.getData());
                contentArea.append("the other side says:"+message+"\n");
            }
        }
        catch(Exception e){}
    }//run



}//chat
/*******configuration class ******/
class Config{
    private String dIp;
    private String dPort;
    private String myPort;
    private String myName;
    public void set(String toIp,String toPort,String sPort,String sName){
        dIp=toIp;dPort=toPort;myPort=sPort;myName=sName;
    }
    public String getDIp(){return dIp;} 
    public String getDPort(){return dPort;}
    public String getMyPort(){return myPort;}
    public String getMyName(){return myName;} 
}
