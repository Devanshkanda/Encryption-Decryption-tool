package CryptPackage;

import java.io.*;

import javax.swing.JOptionPane;

public class EncryptFile {
    
    public EncryptFile(File file , int key)
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

            JOptionPane.showMessageDialog(null,"Encryption Completed");

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
