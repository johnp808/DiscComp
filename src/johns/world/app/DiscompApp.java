package johns.world.app;

import javax.swing.SwingUtilities;

import johns.world.gui.DiscompGUI;

public class DiscompApp {
	public static void main(String[] args) {
		DiscompApp dApp = new DiscompApp();
		dApp.launch();
	}

	public void launch() {
		System.out.println("John's World Up And Running");
		SwingUtilities.invokeLater(() -> {
			DiscompGUI gui = new DiscompGUI();
			gui.setVisible(true);
		});
	}
}