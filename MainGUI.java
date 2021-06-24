import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

class MainGUI extends JFrame implements ActionListener
{	
	JLabel username, password;
	JTextField usernametf, passwordtf;
	JButton submit;
	String msg = "";
	static JFrame fobj;	

	MainGUI() 
	{
		Container cobj = this.getContentPane();
		cobj.setBackground(Color.lightGray);
		cobj.setLayout(null);

		username = new JLabel("Username");
		username.setFont(new Font("Halvetica",Font.BOLD,22)); 
		username.setBounds(100,100,150,22);
		cobj.add(username);

		usernametf = new JTextField(15);
		usernametf.setBounds(250,100,150,30);
		cobj.add(usernametf);
		
		
		password = new JLabel("Password");
		password.setFont(new Font("Halvetica",Font.BOLD,22)); 
		password.setBounds(100,200,150,22);
		cobj.add(password);

		passwordtf = new JTextField(15);
		passwordtf.setBounds(250,200,150,30);
		passwordtf.setEditable(true);
		cobj.add(passwordtf);

		submit = new JButton("Submit");
		submit.setFont(new Font("Aerial",Font.BOLD,30));
		submit.setBounds(230,300,150,25);
		cobj.add(submit);

		submit.addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent ae)
	{
			String uname = usernametf.getText();
		String pwd = passwordtf.getText();
		if(ae.getSource() == submit)
		{
			if((uname.equals("prasad")) && (pwd.equals("1234")))
			{
				new PackUnpackGUI(uname);
			
			}
			else
			{
				JOptionPane.showMessageDialog(fobj, "Enter a valid Username/ Password", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	public static void main(String args[])
	{
		MainGUI fobj = new MainGUI();

		
		fobj.setTitle("File Packer & Unpacker");
		
		fobj.setSize(700,500);
		fobj.setVisible(true);
		fobj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}


class PackUnpackGUI extends JFrame implements ActionListener
{	
	JFrame fobj1;
	JButton packer,unpacker;

	PackUnpackGUI(String uname)
	{
		fobj1 = new JFrame("File Packer & Unpacker");
		fobj1.setSize(700,500);
		fobj1.setVisible(true);
		fobj1.setLayout(null);
		fobj1.setBackground(Color.lightGray);
		fobj1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Container c = this.getContentPane();
				
		JLabel welcome = new JLabel("Welcome "+uname+"....");
		welcome.setFont(new Font("Halvetica",Font.BOLD,22));
		welcome.setBounds(50,50,300,30);
		fobj1.add(welcome);

		packer = new JButton("Packer");
		packer.setFont(new Font("Aerial",Font.BOLD,30));
		packer.setBounds(100,250,150,25);
		fobj1.add(packer);

		unpacker = new JButton("UnPacker");
		unpacker.setFont(new Font("Aerial",Font.BOLD,30));
		unpacker.setBounds(300,250,200,25);
		fobj1.add(unpacker);

		packer.addActionListener(this);
		unpacker.addActionListener(this);
		
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == packer)
		{
			new Packer();
		}
		if(ae.getSource() == unpacker)
		{
			new UnPacker();
		}

	}
	
}

class Packer extends JFrame implements ActionListener
{	
	JFrame pobj;
	JLabel dir, file;
	JTextField dirtf, filetf;
	JButton submit, previous;

	Packer()
	{
		pobj = new JFrame("Packer");
		Container c = this.getContentPane();
		
		pobj.setSize(800,600);
		pobj.setVisible(true);
		pobj.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pobj.setLayout(null);
		pobj.setBackground(Color.lightGray);

		dir = new JLabel("Directory path");
		dir.setFont(new Font("Halvetica",Font.BOLD,22)); 
		dir.setBounds(100,100,300,25);
		pobj.add(dir);

		dirtf = new JTextField(15);
		dirtf.setBounds(350,100,150,30);
		pobj.add(dirtf);

		file = new JLabel("File Name");
		file.setFont(new Font("Halvetica",Font.BOLD,22)); 
		file.setBounds(100,150,300,25);
		pobj.add(file);

		filetf = new JTextField(15);
		filetf.setBounds(350,150,150,30);
		pobj.add(filetf);

		submit = new JButton("Submit");
		submit.setFont(new Font("Aerial",Font.BOLD,30));
		submit.setBounds(100,300,150,25);
		pobj.add(submit);

		previous = new JButton("Previous");
		previous.setFont(new Font("Aerial",Font.BOLD,30));
		previous.setBounds(300,300,200,25);
		pobj.add(previous);

		submit.addActionListener(this);
		previous.addActionListener(this);
	
	}
	public void actionPerformed(ActionEvent ae)
	{	
		String dirstr = dirtf.getText();
		String filestr = filetf.getText();
		
		if(ae.getSource() == submit)
		{
			try
			{
				new MyPacker(dirstr,filestr);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		
	}
}

class MyPacker
{
    FileOutputStream outstream = null;
    String ValidExt[] = {".txt",".c",".java",".cpp"};
    

    public MyPacker(String src, String Dest) throws Exception
    {
	    File outfile = new File(Dest);
	    File infile = null;
	    outstream = new FileOutputStream(Dest);
	    
	    File folder = new File(src);
	        
        System.setProperty("user.dir",src); // to make a dafault path for all the files
	        
        listAllFiles(src);
    }
	    
    public void listAllFiles(String path)
    {
        try(Stream<Path> paths = Files.walk(Paths.get(path)))	// Coded try block / inline try
        {
            paths.forEach(filePath ->
            {
                 if (Files.isRegularFile(filePath)) // returns true
                   {
                      try		// simple try block
                      {
                          String name = filePath.getFileName().toString(); // getFileName gives only file name from the absolute path
                          String ext = name.substring(name.lastIndexOf(".")); // return the substring from lastindex till the end i.e txt
	                                      
                          List<String> list = Arrays.asList(ValidExt);	// make a list of validext string

	                      if(list.contains(ext))  
	                      {
	                        File file = new File(filePath.getFileName().toString());
	                                          
	                        Pack(file.getAbsolutePath());
	                      }
	                   }
	                   catch (Exception e)
	                   {
	                       System.out.println(e);
	                   }
	            	}
	        });
	    }
		catch (IOException e)
	    {
	        System.out.println(e);
	    }
	}

	public void Pack(String filePath)
    {
        FileInputStream instream = null;	
        // File handling is checked exception bcoz when we read/write the data from file 
        try
        {
            byte[] buffer = new byte[1024];
            int length;
            byte temp[] = new byte[100];
            
            File fobj = new File(filePath);
            String Header = filePath+" "+fobj.length();
            for (int i = Header.length(); i < 100; i++)
            	Header += " ";
	            
            temp = Header.getBytes(); // wrote the header into the file by converting into the bytes 
	            
            instream = new FileInputStream(filePath);
            outstream.write(temp, 0, temp.length);
	            
            while ((length = instream.read(buffer)) > 0)
            {
                outstream.write(buffer, 0, length);
            }
	        
            instream.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}

class UnPacker extends JFrame implements ActionListener
{	
	JFrame uobj;
	JLabel file;
	JTextField filetf;
	JButton extract;

	UnPacker()
	{
		uobj = new JFrame("UnPacker");

		uobj.setSize(600,400);
		uobj.setVisible(true);
		uobj.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		uobj.setLayout(null);
		uobj.setBackground(Color.lightGray);

		Container c = this.getContentPane();

		file = new JLabel("File Name");
		file.setFont(new Font("Halvetica",Font.BOLD,22)); 
		file.setBounds(100,100,150,25);
		uobj.add(file);

		filetf = new JTextField(15);
		filetf.setBounds(250,100,150,30);
		uobj.add(filetf);

		extract = new JButton("Extract");
		extract.setFont(new Font("Aerial",Font.BOLD,30));
		extract.setBounds(200,250,150,25);
		uobj.add(extract);

		extract.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ae)
	{	
		String filestr = filetf.getText();
		
		if(ae.getSource() == extract)
		{
			try
			{
				new MyUnPacker(filestr);
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}
}

class MyUnPacker
{
    FileOutputStream outstream = null;
    
    public MyUnPacker(String src) throws Exception
    {
        unpack(src);
    }
    
    public void unpack(String filePath) throws Exception 
    {
        try
        {
            FileInputStream instream = new FileInputStream(filePath);
            byte header[]= new byte[100];
            int length = 0;
            
            while((length = instream.read(header,0,100)) > 0) // read 100 bytes from the string
            {
                String str = new String(header);    // converted that 100 bytes into string
                
                String ext = str.substring(str.lastIndexOf("\\")); // get the string after the last '\' i.e a.txt
                ext = ext.substring(1);
                
                String[] words=ext.split("\\s");       // [a.txt, 10] 
                String filename = words[0];     // a.txt
                int size = Integer.parseInt(words[1]);  // 10
                
                byte arr[] = new byte[size];
                instream.read(arr,0,size);
                System.out.println(filename);
                FileOutputStream fout = new FileOutputStream(filename);
                fout.write(arr,0,size);
            }
        }
        
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
