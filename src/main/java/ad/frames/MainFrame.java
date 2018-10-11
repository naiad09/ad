package ad.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import ad.Application;

public class MainFrame extends JFrame {

	private Box verticalBox;

	public MainFrame() {
		super("Hello, World!");

		setSize(new Dimension(1300, 768));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
				super.windowClosing(e);
				Application.quit();
			}
		});
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JButton mntmAllForums = new JButton("Forums");
		mntmAllForums.addActionListener(e -> Application.forumController().displayAll());
		menuBar.add(mntmAllForums);

		JButton mntmAllClients = new JButton("Clients");
		mntmAllClients.addActionListener(e -> Application.getClientController().displayAll());
		menuBar.add(mntmAllClients);

		JButton mntmAllTasks = new JButton("Tasks");
		mntmAllTasks.addActionListener(e -> Application.taskController().displayAll());
		menuBar.add(mntmAllTasks);

		JButton btnBanners = new JButton("Banners");
		btnBanners.addActionListener(e -> Application.bannerController().displayAll());
		menuBar.add(btnBanners);

		getContentPane().setLayout(new BorderLayout(0, 0));

		verticalBox = Box.createVerticalBox();
		getContentPane().add(verticalBox, BorderLayout.CENTER);
	}

	public void openFrame(String title, JComponent component) {
		for (Component comp : verticalBox.getComponents()) {
			verticalBox.remove(comp);
		}

		JLabel titleLabel = buildHeader1(title);
		verticalBox.add(titleLabel);

		verticalBox.add(component, BorderLayout.CENTER);

		validate();
		repaint();
	}

	public void openFrame(JComponent component) {
		for (Component comp : verticalBox.getComponents()) {
			verticalBox.remove(comp);
		}

		verticalBox.add(component, BorderLayout.CENTER);

		validate();
		repaint();
	}

	public static JLabel buildHeader1(String title) {
		JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
		titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		return titleLabel;
	}

	public static JTextArea buildTextarea(String code) {
		JTextArea textAreaCode = new JTextArea(code);
		textAreaCode.setFont(new Font("Consolas", Font.PLAIN, 12));
		textAreaCode.setBorder(new LineBorder(Color.GRAY));
		return textAreaCode;
	}
}
