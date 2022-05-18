
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

public class Cryptograph{

    private JFrame f1 = new JFrame("Encrypter Decryptor tool");
    private JFrame f2 = new JFrame("List Of Files Selected");
    private JFrame f3 = new JFrame("Enter Password");
    private JButton b1 = new JButton("Encrypt");
    private JButton b2 = new JButton("Decrypt");
    private JTextArea ta = new JTextArea();
    private JTextField tf = new JTextField(20);
    private JFileChooser fileChooser = new JFileChooser();
    private File selectedFile;
    private String check;
    private Font font = new Font("Roboto",Font.BOLD,20);

    Cryptograph()
    {
        prepairGUI();
        buttonProperties(); 
    }

    void prepairGUI() // 1st Window
    {
        f1.setLayout(new FlowLayout(FlowLayout.CENTER,80,80));
        JLabel l = new JLabel("ENCRYPTION DECRYPTION TOOL");

        l.setFont(font);
        l.setBounds(80,70,40,50);
        f1.setSize(600,600);
        f1.setVisible(true);
        f1.setLocationRelativeTo(null);
        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tf.setVisible(false);
        f1.add(l);
        f1.add(tf);
    }

    void listFileWindow() //2nd window
    {
        f2.setLayout(new FlowLayout());
        f2.setSize(600,500);
        f2.setBackground(Color.BLACK);
        JLabel l = new JLabel("List of selected files are ");
        l.setBounds(30,20,80,20);

        ta.setRows(20);
        ta.setColumns(30);
        ta.setBounds(20, 80, 100, 200);
        ta.setEditable(false);

        JButton encryptB = new JButton("Encrypt");
        encryptB.setSize(80,20);
        encryptB.setFont(font);

        encryptB.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent ae)
            {

                setPasswordWindow();
                f2.setVisible(false);
            }
        });

        JButton addMoreFiles = new JButton("ADD MORE FILES");
        addMoreFiles.setSize(90,20);
        addMoreFiles.setFont(font);

        addMoreFiles.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent ae)
            {
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(f2);
                

                if(result == JFileChooser.APPROVE_OPTION)
                {
                    selectedFile = fileChooser.getSelectedFile();
                    ta.append("\n\n");
                    ta.append("\nFile Name : " + selectedFile.getName());
                    ta.append("\nFile Path : " + selectedFile.getAbsolutePath());
                    
                }
            }
        });

        f2.add(l);
        f2.add(new JScrollPane(ta));
        f2.add(encryptB);
        f2.add(addMoreFiles);
        f2.setLocationRelativeTo(null);
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.setVisible(true);
    }

    void setPasswordWindow() // 3rd Window after selecting the file to encrypt from file chooser
    {
        f3.setLayout(new FlowLayout(FlowLayout.CENTER,80,80));
        f3.setSize(600,600);
        f3.getContentPane().setBackground(Color.LIGHT_GRAY);

        JPasswordField pf1 = new JPasswordField(20);
        JPasswordField pf2 = new JPasswordField(20);
        JLabel l3 = new JLabel();
        
        l3.setBounds(20,20,30,30);
        f3.add(l3);

        pf1.addKeyListener(new KeyAdapter(){

            
        });

        JLabel l1 = new JLabel("Enter your Password !!");
        JLabel l2 = new JLabel("Re-Enter your Password !!");
        JButton proceedB = new JButton("PROCCED");

        //proceed button functionality
        proceedB.addActionListener(e->{

            try{

            String text = String.valueOf(pf1.getPassword());
            System.out.println("button clicked");
            

            System.out.println("password is : " + text);

            int key = Integer.parseInt(text);
            System.out.println("Password after converting in int : " + key);
            Encrypt(selectedFile , key);

            }catch(NumberFormatException ex)
            {
                ex.printStackTrace();
            }


            pf1.setText("");
            pf2.setText("");

        });

        l1.setBounds(20,20,30,30);
        f3.add(l1);
        f3.add(pf1);

        l2.setBounds(20,20,30,30);
        f3.add(l2);
        f3.add(pf2);

        
        f3.add(l3);

        proceedB.setBounds(20,20,30,30);
        proceedB.setFont(font);
        f3.add(proceedB);
        f3.setLocationRelativeTo(null);
        f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f3.setVisible(true);
        
    }


    void Encrypt(File file , int key)
    {
        try
        {
            FileInputStream fis = new FileInputStream(file);
            byte data[] = new byte[fis.available()];
            fis.read(data);
            int i=0;
            for(byte b:data)
            {
                data[i] = (byte) (b^key);
                System.out.println(b);
                i++;
            }

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
            fis.close();

            if(check == "E")
            JOptionPane.showMessageDialog(null,"Encryption Completed");

            else
            JOptionPane.showMessageDialog(null,"Decryption Completed");


        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    void buttonProperties()  //first window button functionality
    {
        b1.setBounds(40,50,80,20);
        b2.setBounds(40,100,80,20);
        f1.add(b1);
        f1.add(b2);
        b1.setFont(font);
        b2.setFont(font);

        b1.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){

                check = "E";

                f1.getContentPane().setBackground(Color.pink);
                f1.setVisible(false);
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(f3);
                

                if(result == JFileChooser.APPROVE_OPTION)
                {
                    selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file is : " + selectedFile.getName());
                    System.out.println("Full path : " + selectedFile.getAbsolutePath());
                    ta.append("\nFile Name : " + selectedFile.getName());
                    ta.append("\nFile Path : " + selectedFile.getAbsolutePath());
                    listFileWindow();

                }
                

            }
        });

        //decrypt button
        b2.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e){

                //f1.getContentPane().setBackground(Color.blue);
                f1.setVisible(false);
                check = "D";

                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(f2);
                

                if(result == JFileChooser.APPROVE_OPTION)
                {
                    selectedFile = fileChooser.getSelectedFile();
                    ta.append("\n\n");
                    ta.append("\nFile Name : " + selectedFile.getName());
                    ta.append("\nFile Path : " + selectedFile.getAbsolutePath());
                
                }
                if(result == JFileChooser.CANCEL_OPTION)
                {
                    prepairGUI();
                }

                setPasswordWindow();
            }

            

            }
        );
    }

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
                new Cryptograph();
            }
        });
    }
}