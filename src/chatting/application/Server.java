package chatting.application;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Server implements ActionListener {

    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    Server(){
        f.setLayout(null);

        //Creating green navbar component
        JPanel pannel = new JPanel();
        pannel.setBackground(new Color(7,92,84));
        pannel.setBounds(0,0,450,70);
        pannel.setLayout(null);
        f.add(pannel);

        ImageIcon img_1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image img_2 = img_1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon img_3 = new ImageIcon(img_2);
        JLabel backImg = new JLabel(img_3);
        backImg.setBounds(5,25,25,25);
        pannel.add(backImg);

        //to go back button(top right)<-
        backImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);

            }
        });

        //Profile Image of Gaitonde Bhau
        ImageIcon img_4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image img_5 = img_4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon img_6 = new ImageIcon(img_5);
        JLabel profileImg = new JLabel(img_6);
        profileImg.setBounds(40,10,50,50);
        pannel.add(profileImg);


        //video call Image
        ImageIcon img_7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image img_8 = img_7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon img_9 = new ImageIcon(img_8);
        JLabel videoImg = new JLabel(img_9);
        videoImg.setBounds(300,20,30,30);
        pannel.add(videoImg);

        //phone call image
        ImageIcon img_10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image img_11 = img_10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
        ImageIcon img_12 = new ImageIcon(img_11);
        JLabel phoneImg = new JLabel(img_12);
        phoneImg.setBounds(350,20,30,30);
        pannel.add(phoneImg);


        //:. dot image
        ImageIcon img_13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image img_14 = img_13.getImage().getScaledInstance(10,20,Image.SCALE_DEFAULT);
        ImageIcon img_15 = new ImageIcon(img_14);
        JLabel morevert = new JLabel(img_15);
        morevert.setBounds(400,20,10,25);
        pannel.add(morevert);

        //Profile Name -- Mukesh
        JLabel name = new JLabel("Mukesh");
        name.setBounds(100,15,100,17);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("ITALIC",Font.BOLD,17));
        pannel.add(name);


        //Status -- Active now
        JLabel status = new JLabel("Active now");
        status.setBounds(100,35,100,16);
        status.setForeground(Color.green);
        status.setFont(new Font("ITALIC",Font.BOLD,14));
        pannel.add(status);

        //chat box
        a1 = new JPanel();
        a1.setBounds(20,75,395,570);
        f.add(a1);

        //text box
        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,15));
        f.add(text);

        //send button
        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.white);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF",Font.PLAIN,15));
        f.add(send);


        f.setSize(450,700);
        f.setLocation(200,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);

        f.setVisible(true);

    }
    public static void main(String[] args) {

        new Server();
        try {
            //creates a server
            ServerSocket skt = new ServerSocket(6001);
            //to accept infinite messages
            while(true){
                Socket s = skt.accept();
                DataInputStream din= new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true){
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();

                }

            }
        }catch (Exception e){
            e.printStackTrace();

        }

    }

    public static JPanel formatLabel(String message){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JLabel output_1 = new JLabel("<html><p style=\"width:150px\">"+ message + "</p></html>");
        output_1.setFont(new Font("Tahoma",Font.PLAIN,16));
        output_1.setBackground(new Color(37,211,102));
        output_1.setOpaque(true);
        output_1.setBorder(new EmptyBorder(15,15,15,50));
        panel.add(output_1);

        //time of message
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(calendar.getTime()));
        panel.add(time);


        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            String message = text.getText();

//            JLabel output = new JLabel(message);
            JPanel pannel_2 = formatLabel(message);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(pannel_2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            text.setText("");

            dout.writeUTF(message);


            f.repaint();
            f.invalidate();
            f.validate();
        }catch (Exception et){
            et.printStackTrace();
        }

    }
}
