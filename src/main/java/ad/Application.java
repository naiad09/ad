package ad;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ad.controllers.BannerController;
import ad.controllers.ForumController;
import ad.controllers.TaskController;
import ad.domain.dao.util.HibernateUtil;
import ad.frames.MainFrame;

public class Application implements Runnable {

	private static MainFrame mainFrame;

	private static ForumController forumController = new ForumController();
	private static ForumController clientController = new ForumController(true);
	private static TaskController taskController = new TaskController();
	private static BannerController bannerController = new BannerController();

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException
		        | UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}

		SwingUtilities.invokeLater(new Application());
	}

	@Override
	public void run() {
		mainFrame = new MainFrame();
		new ForumController().displayAll();
		mainFrame.setVisible(true);
	}

	public static MainFrame mainFrame() {
		return mainFrame;
	}

	public static ForumController forumController() {
		return forumController;
	}

	public static ForumController getClientController() {
		return clientController;
	}

	public static TaskController taskController() {
		return taskController;
	}

	public static BannerController bannerController() {
		return bannerController;
	}

	public static void quit() {
		HibernateUtil.shutdown();
		System.out.println("Closing window");
	}
}
