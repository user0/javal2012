import java.io.*;
import java.net.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ClientLH extends JFrame implements Runnable {
	Thread selfThread = null;
	InetAddress addr;
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	boolean isActive = true;

	String userS = new String();
	String allFile = new String("<html>");

	String nickColor = new String();
	String textColor = new String();
	String nameServer = new String();
	String nick = new String();
	String name = new String();

	BufferedReader inFile;
	PrintWriter pw;

	// ****************************************************** GUI
	// для общего фрейма
	JEditorPane jepcframe = new JEditorPane(
			"text/html;Content-type=windows-1251", allFile);
	JScrollPane jscframe = new JScrollPane(jepcframe);
	JPanel northPanel = new JPanel();

	// пользовательский ввод
	JTextField jtf = new JTextField(45);
	JButton ok = new JButton("OK");
	JButton clear = new JButton("Очистить");
	JPanel userPanel = new JPanel();

	JTextField status = new JTextField(60);

	JPanel southPanel = new JPanel();

	JMenuBar jmb = new JMenuBar();
	JMenu opt = new JMenu("Меню");
	JMenuItem ops = new JMenuItem("Настройки");

	// ****************************************************** // GUI
	// **************************************************************************
	ClientLH() throws IOException {
		getUserInfo();

		addr = InetAddress.getByName(nameServer); // сервер
		socket = new Socket(addr, ServerLH.PORT);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		init();
		jtf.grabFocus();

		System.out.println("Address: " + addr);

		try {
			System.out.println("Socket: " + socket);
			selfThread = new Thread(this);
			selfThread.start();

			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())), true);
		} finally {
		}
	}

	// ****************************************************************************
	public void run() {
		while (true) {
			try {
				selfThread.sleep(200);
			} catch (InterruptedException e) {
			}

			if (isActive) {
				try {
					String s = in.readLine();

					if (!s.equals("")) {
						allFile = s + allFile;
						jepcframe.setText(allFile);
					}
				} catch (IOException e) {
				}
			} else
				break;
		}
	}

	// ******************************************************************
	public void init() {
		Container c = getContentPane();
		c.setLayout(new BorderLayout(10, 10));

		northPanel.setLayout(new BorderLayout());
		northPanel.add(jscframe, BorderLayout.CENTER);

		ok.setSize(100, 70);
		ok.setActionCommand("ok");
		ok.addActionListener(new Click());

		clear.setSize(60, 20);
		clear.setActionCommand("clear");
		clear.addActionListener(new Click());

		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
		userPanel.add(Box.createRigidArea(new Dimension(10, 1)));
		userPanel.add(jtf);
		userPanel.add(Box.createRigidArea(new Dimension(10, 1)));
		userPanel.add(ok);
		userPanel.add(Box.createRigidArea(new Dimension(10, 1)));
		userPanel.add(clear);
		userPanel.add(Box.createRigidArea(new Dimension(10, 1)));

		jtf.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == e.VK_ENTER) {
					jtf.selectAll();
					String s = jtf.getText();

					out.println("<br><font color=\"" + nickColor + "\">" + nick + "(" + name + "):  </font><font color=\"" + textColor + "\">"
							+ s + "</font>");

					jtf.grabFocus();
				}
			}

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
			}
		});

		status.setEditable(false);
		jepcframe.setEditable(false);
		jepcframe.setDoubleBuffered(true);

		southPanel.setLayout(new BorderLayout(10, 10));
		southPanel.add(userPanel, BorderLayout.CENTER);
		southPanel.add(status, BorderLayout.SOUTH);

		c.add(northPanel, BorderLayout.CENTER);
		c.add(southPanel, BorderLayout.SOUTH);

		jmb.add(opt);
		opt.add(ops);

		ops.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				new Options();
			}
		});

		setTitle("Java Chat, user: " + nick);
		setFont(new Font("MS Sans Serif", Font.PLAIN, 10));
		setJMenuBar(jmb);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setVisible(true);
		addWindowListener(new windowListener());
	}

	// ***********************************************************************
	public void getUserInfo() {
		try {
			inFile = new BufferedReader(new FileReader("user.usr"));

			nameServer = inFile.readLine();
			nick = inFile.readLine();
			name = inFile.readLine();
			nickColor = inFile.readLine();
			textColor = inFile.readLine();
		} catch (Exception e) {
			nameServer = "localhost";
			nick = "Anonimous";
			name = "XXX";
			nickColor = "black";
			textColor = "black";

			System.out.println("Create new user...");
		}
	}

	// **********************************************************************
	public static void main(String args[]) throws IOException {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		new ClientLH();
	}

	// ************************************************************************
	// ************************************************************************
	// ************************************************************************
	class Click implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if (ae.getActionCommand().equals("ok")) {
				jtf.selectAll();
				String s = jtf.getText();

				out.println("<br><font color=\"" + nickColor + "\">" + nick + " (" + name + "):  </font><font color=\"" + textColor + "\">" + s
						+ "</font>");

				jtf.grabFocus();
			}

			if (ae.getActionCommand().equals("clear")) {
				jtf.setText("");
				jtf.grabFocus();
			}
		}
	}

	class windowListener implements WindowListener {
		public void windowOpened(java.awt.event.WindowEvent e) {
		}

		public void windowIconified(java.awt.event.WindowEvent e) {
		}

		public void windowDeiconified(java.awt.event.WindowEvent e) {
		}

		public void windowActivated(java.awt.event.WindowEvent e) {
		}

		public void windowDeactivated(java.awt.event.WindowEvent e) {
		}

		public void windowClosed(java.awt.event.WindowEvent e) {
		}

		public void windowClosing(java.awt.event.WindowEvent e) {
			try {
				System.out.println("Closing...");

				isActive = false;
				out.println("__exit");
				socket.close();

			} catch (Exception ex) {
			}
			dispose();
		}
	}

	// *********************************************************
	class Options extends JFrame implements ActionListener {
		JLabel lServer = new JLabel("Имя сервера: ");
		JLabel lNick = new JLabel("Ваш ник: ");
		JLabel lName = new JLabel("Ваше имя: ");
		JLabel lNickColor = new JLabel("Цвет ника: ");
		JLabel lTextColor = new JLabel("Цвет сообщений: ");

		JTextField tfServer = new JTextField(20);
		JTextField tfNick = new JTextField(20);
		JTextField tfName = new JTextField(20);
		JComboBox cbNickColor = new JComboBox();
		JComboBox cbTextColor = new JComboBox();

		JButton ok = new JButton("OK");
		JFrame f;

		Options() {
			f = new JFrame("Настройки");
			Container c = f.getContentPane();

			c.setLayout(null);

			lServer.setBounds(20, 10, 150, 20);
			lNick.setBounds(20, 35, 150, 20);
			lName.setBounds(20, 60, 150, 20);
			lNickColor.setBounds(20, 85, 150, 20);
			lTextColor.setBounds(20, 110, 150, 20);

			tfServer.setBounds(140, 10, 150, 20);
			tfNick.setBounds(140, 35, 150, 20);
			tfName.setBounds(140, 60, 150, 20);

			cbNickColor.setBounds(140, 85, 150, 20);
			cbNickColor.addItem("Red");
			cbNickColor.addItem("Rose");
			cbNickColor.addItem("Yellow");
			cbNickColor.addItem("Green");
			cbNickColor.addItem("Blue");
			cbNickColor.addItem("Black");
			cbNickColor.addItem("Gray");

			cbTextColor.setBounds(140, 110, 150, 20);
			cbTextColor.addItem("Red");
			cbTextColor.addItem("Rose");
			cbTextColor.addItem("Yellow");
			cbTextColor.addItem("Green");
			cbTextColor.addItem("Blue");
			cbTextColor.addItem("Black");
			cbTextColor.addItem("Gray");

			ok.setBounds(140, 140, 80, 25);
			ok.setActionCommand("OK");
			ok.addActionListener(this);

			c.add(ok);

			c.add(lServer);
			c.add(lNick);
			c.add(lName);
			c.add(lNickColor);
			c.add(lTextColor);

			c.add(tfServer);
			c.add(tfNick);
			c.add(tfName);

			c.add(cbNickColor);
			c.add(cbTextColor);

			f.setSize(330, 200);
			f.setVisible(true);
			f.setResizable(false);

			try {
				inFile = new BufferedReader(new FileReader("user.usr"));

				tfServer.setText(inFile.readLine());
				tfNick.setText(inFile.readLine());
				tfName.setText(inFile.readLine());
				cbNickColor.setSelectedItem(inFile.readLine());
				cbTextColor.setSelectedItem(inFile.readLine());
			} catch (Exception ex) {
			}

		}

		public void actionPerformed(ActionEvent ae) {
			if (ae.getActionCommand().equals("OK")) {
				String nick = tfNick.getText();
				String name = tfName.getText();

				try {
					pw = new PrintWriter(new FileWriter("user.usr"));

					pw.println(tfServer.getText());
					pw.println(nick);
					pw.println(name);
					pw.println((String) cbNickColor.getSelectedItem());
					pw.println((String) cbTextColor.getSelectedItem());

					pw.close();
				} catch (IOException e) {
				}

				f.dispose();
			}
		}
	}
}
