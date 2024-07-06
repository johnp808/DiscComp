package uzis.world.app;

import javax.swing.SwingUtilities;

import uzis.world.gui.DiscordGUI;

public class DiscordApp {
	public static void main(String[] args) {
		DiscordApp dApp = new DiscordApp();
		dApp.launch();
	}

	public void launch() {
		System.out.println("Uzi's World Up And Running");
		SwingUtilities.invokeLater(() -> {
			DiscordGUI gui = new DiscordGUI();
			gui.setVisible(true);
		});
	}
}