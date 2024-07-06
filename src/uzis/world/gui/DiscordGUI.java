package uzis.world.gui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;

public class DiscordGUI extends JFrame {
	private Robot robot;
	private JList<String> entryList;
	private boolean isStarted = false;
	private java.util.Timer taskTimer;
	private DefaultListModel<String> listModel;
	private JTextField textField, troubleshootingDisplay;
	private JButton loadUziPresetButton, toggleButton, loadPresetButton;
	private Color lighterButtonColor = new Color(82, 85, 89);
	private Color startButtonColor = new Color(52, 231, 24);
	private Color titleBarColor = new Color(114, 137, 218);
	private Color backgroundColor = new Color(30, 33, 36);
	private Dimension buttonSize = new Dimension(100, 25);
	private Color stopButtonColor = new Color(255, 0, 0);
	private Color caretColor = new Color(202, 178, 251);
	private Color buttonColor = new Color(66, 69, 73);
	private Color textColor = new Color(150, 86, 206);
	private String selectedEntry, lastSelectedEntry = null;
	private String[] presetEntries = { "GM", "Good Morning", "Hey", "How are you guys doing today?",
			"I hope everyone is having a great day!", "GM!", "Anyone else tired today?", "Are we ready for the Rocket?",
			"GM fam, ready to ride the Solana wave today?", "Memecoins got me ballin'",
			"Whos holding longer than 24 hours like me?", "GM family", "Remember to drink water", "Drink water",
			"Holding onto my NFTs like they're my babies, lol.", "Solana Meme Season 🔥",
			"Wish I stacked more Sol at $8 LOL", "Where my hodlers at? 🫡🫡🫡",
			"Don't forget to stretch today!", "Drink some H2O"},
			uzisEntries = { ":BRUHBRO", ":sobroll:", ":nolegs_Fire", ":mayo:", ":flushwoah:",
			":Olympic:", ":gigaJAM:", ":mega_1up:", ":RMC_gotem:", ":penguin~1:", ":nurepep:" };

	public DiscordGUI() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			return;
		}
		initializeUI();
		setAlwaysOnTop(true);
		setVisible(true);
		SwingUtilities.invokeLater(() -> textField.requestFocusInWindow());
		setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
	}

	private void initializeUI() {
		setUndecorated(true);
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(backgroundColor);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createLineBorder(backgroundColor, 3));
		mainPanel.setBackground(backgroundColor);

		initializeComponents();

		JScrollPane scrollPane = setupScrollPane();
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		JPanel titleBar = createTitleBar();
		mainPanel.add(titleBar, BorderLayout.NORTH);

		JPanel southPanel = createSouthPanel();
		mainPanel.add(southPanel, BorderLayout.SOUTH);

		setContentPane(mainPanel);
		setVisible(true);
		setupFocusManagement();
	}

	private void initializeComponents() {
		listModel = new DefaultListModel<>();
		entryList = new JList<>(listModel);
		entryList.setCellRenderer(new MyListCellRenderer());
		entryList.setBackground(backgroundColor);
		entryList.setForeground(textColor);
		entryList.setOpaque(false);
		entryList.setFocusable(true);

		entryList.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// Handle both the backspace and delete keys
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE) {
					int selectedIndex = entryList.getSelectedIndex();
					if (selectedIndex != -1) {
						listModel.remove(selectedIndex);
					} else if (!listModel.isEmpty()) {
						listModel.remove(listModel.getSize() - 1);
					}
					textField.requestFocusInWindow(); // Focus back to textField to continue typing
				}
			}
		});

		entryList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		textField = new JTextField();
		textField.setBackground(buttonColor);
		textField.setForeground(Color.WHITE);
		textField.setCaretColor(caretColor);
		textField.addActionListener(this::addEntryToList);

		troubleshootingDisplay = new JTextField("Troubleshooting Display");
		troubleshootingDisplay.setEditable(false);
		troubleshootingDisplay.setForeground(caretColor);
		troubleshootingDisplay.setBackground(buttonColor);
		troubleshootingDisplay.setBorder(BorderFactory.createEmptyBorder());
		troubleshootingDisplay.setHorizontalAlignment(JTextField.CENTER);
	}

	private JScrollPane setupScrollPane() {
		JScrollPane scrollPane = new JScrollPane(entryList) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try (InputStream imgStream = getClass().getResourceAsStream("/resources/uzisworld.png")) {
					if (imgStream != null) {
						Image bgImage = ImageIO.read(imgStream);
						g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		return scrollPane;
	}

	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.setBackground(backgroundColor);

		// Buttons setup
		loadUziPresetButton = new JButton("Uzi's 🔥 Emojis");
		loadPresetButton = new JButton("Preset Phrases");
		toggleButton = new JButton("Start");

		loadPresetButton.setActionCommand("loadPreset");
		loadUziPresetButton.setActionCommand("loadUzis");

		configureButton(loadPresetButton, caretColor, this::loadEntries);
		configureButton(loadUziPresetButton, caretColor, this::loadEntries);
		configureToggleButton();

		// Button panel
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
		buttonPanel.setBackground(buttonColor); // Match the textField background
		buttonPanel.add(loadUziPresetButton);
		buttonPanel.add(toggleButton);
		buttonPanel.add(loadPresetButton);

		// Adding the troubleshooting display and textField to the south panel
		southPanel.add(troubleshootingDisplay, BorderLayout.NORTH);

		textField = new JTextField();
		textField.setBackground(buttonColor);
		textField.setForeground(caretColor);
		textField.setCaretColor(caretColor);
		textField.repaint();
		textField.addActionListener(e -> addEntryToList(e));
		southPanel.add(textField, BorderLayout.CENTER);

		southPanel.add(buttonPanel, BorderLayout.SOUTH);

		return southPanel;
	}

	private JPanel createTitleBar() {
		JPanel titleBar = new JPanel(new BorderLayout());
		titleBar.setBackground(titleBarColor);
		titleBar.setPreferredSize(new Dimension(getWidth(), 30));

		JLabel leftPadding = new JLabel("           "); // Adjust the number of spaces for more or less padding
		titleBar.add(leftPadding, BorderLayout.EAST);
		JLabel titleLabel = new JLabel("Uzi's World Discord Companion", SwingConstants.CENTER);
		titleLabel.setForeground(new Color(255, 255, 255));
		titleBar.add(titleLabel, BorderLayout.CENTER);

		JButton closeButton = closeButton();
		titleBar.add(closeButton, BorderLayout.WEST);

		MouseAdapter ma = new MouseAdapter() {
			Point initialClick;

			@Override
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
				getComponentAt(initialClick);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				int thisX = getLocation().x;
				int thisY = getLocation().y;
				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);
				setLocation(thisX + xMoved, thisY + yMoved);
			}
		};

		// Apply the mouse listeners to the titleBar and its components.
		titleBar.addMouseListener(ma);
		titleBar.addMouseMotionListener(ma);
		titleLabel.addMouseListener(ma);
		titleLabel.addMouseMotionListener(ma);
		closeButton.addMouseListener(ma);
		closeButton.addMouseMotionListener(ma);
		return titleBar;
	}

	private void configureButton(JButton button, Color fgColor, ActionListener actionListener) {
		button.setPreferredSize(buttonSize);
		button.setBackground(lighterButtonColor);
		button.setForeground(fgColor);
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.addActionListener(actionListener);
	}

	private void configureToggleButton() {
		toggleButton = new JButton("Start");
		toggleButton.setPreferredSize(buttonSize);
		toggleButton.setBackground(lighterButtonColor);
		toggleButton.setForeground(textColor);
		toggleButton.setOpaque(true);
		toggleButton.setBorderPainted(false);
		toggleButton.addActionListener(e -> toggleStartStop(e));
		updateToggleButtonVisualState();
	}

	private void updateToggleButtonVisualState() {
		if (isStarted) {
			toggleButton.setText("Stop </3");
			toggleButton.setForeground(stopButtonColor); // Change text color to red for Stop
		} else {
			toggleButton.setText("Start <3");
			toggleButton.setForeground(startButtonColor); // Change text color to green for Start
		}
	}

	private void loadEntries(ActionEvent e) {
		listModel.clear();
		String actionCommand = e.getActionCommand();
		troubleshootingDisplay.setForeground(Color.GREEN);

		if ("loadPreset".equals(actionCommand)) {
			for (String entry : presetEntries) {
				listModel.addElement(entry);
			}
			troubleshootingDisplay.setText("Companion Loaded Preset List UwU <3!");
		} else if ("loadUzis".equals(actionCommand)) {
			for (String entry : uzisEntries) {
				listModel.addElement(entry);
			}
			troubleshootingDisplay.setText("Companion Loaded Uzi's Emojis UwU <3!");
		}
		textField.requestFocusInWindow();
	}

	private void scheduleNextAction() {
		if (!isStarted || listModel.isEmpty())
			return;

		selectedEntry = selectUniqueRandomEntry();
		if (selectedEntry == null) {
			sendDefaultMessage();
			return;
		}

		// This ensures the display is updated with the correct entry and countdown
		lastSelectedEntry = selectedEntry;
		final int randomInterval = timerRandomizer();
		troubleshootingDisplay.setForeground(Color.YELLOW);
		troubleshootingDisplay.setText(
		"Companion will send: \"" + selectedEntry + "\" in: " + (randomInterval / 1000) + " seconds <3");

		Timer timer = new Timer(1000, new ActionListener() {
			int secondsLeft = randomInterval / 1000;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isStarted) {
					// Stop the timer and update the display panel for program end
					((Timer) e.getSource()).stop();
					troubleshootingDisplay.setForeground(Color.RED);
					troubleshootingDisplay.setText("Companion Error: User ended program </3");
					return;
				}

				if (secondsLeft <= 0) {
					// Stops the timer, performs the action and starts the next action
					((Timer) e.getSource()).stop();
					startStopAction();
				} else {
					// Continue the countdown and updates display
					troubleshootingDisplay.setForeground(Color.YELLOW);
					troubleshootingDisplay.setText(
					"Companion will send: \"" + selectedEntry + "\" in: " + secondsLeft + " seconds <3");
					secondsLeft--;
				}
			}
		});
		timer.setInitialDelay(0);
		timer.start();
	}

	private void startStopAction() {
		if (!isStarted) {
			troubleshootingDisplay.setForeground(Color.RED);
			troubleshootingDisplay.setText("Companion Error: Program not started </3");
			return;
		}

		// if the list is empty, or no unique entry, send a default message
		if (listModel.isEmpty() || listModel.getSize() == 1 || lastSelectedEntry == null) {
			sendDefaultMessage();
			return;
		}

		copyEntryToClipboard(lastSelectedEntry);
		troubleshootingDisplay.setForeground(Color.GREEN);
		troubleshootingDisplay.setText("Companion Copied: \"" + lastSelectedEntry + "\" to clipboard <3");

		SwingUtilities.invokeLater(() -> {
			switchToDiscord();
			pasteEntryToDiscord();
		});
	}

	private void sendDefaultMessage() {
		/*
		 * NEEDS WORK / Spams GM
		 */
//		lastSelectedEntry = "GM";
//		copyEntryToClipboard("GM");
//		troubleshootingDisplay.setForeground(Color.RED);
//		troubleshootingDisplay.setText("Error: Companion Copied Duplicate; Sent Default: \"GM\" to clipboard instead <3");
//
//		
//		SwingUtilities.invokeLater(() -> {
//			switchToDiscord();
//			pasteEntryToDiscord();
//		});
	}

	private String selectUniqueRandomEntry() {
		if (listModel.getSize() <= 1) {
			return null;
		}
		String entry;
		int attempts = 0;
		do {
			int index = selectRandomEntry(listModel.getSize());
			entry = listModel.getElementAt(index);
			
			// To prevent a potential infinite loop if all entries are the same
			if (++attempts > listModel.getSize() * 2) {
				return null;
			}
		} while (entry.equals(lastSelectedEntry));

		return entry;
	}

	private void pasteEntryToDiscord() {
		SwingUtilities.invokeLater(() -> {
			try {
				switchToDiscord();
				Thread.sleep(500); // Short delay to ensure Discord has focus, adjust if I need to

				String osName = System.getProperty("os.name").toLowerCase();
				int controlKey = osName.contains("mac") ? KeyEvent.VK_META : KeyEvent.VK_CONTROL;

				robot.keyPress(controlKey);
				Thread.sleep(100); // Adjust delays as necessary
				robot.keyPress(KeyEvent.VK_V);
				Thread.sleep(100);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(controlKey);
				Thread.sleep(100);
				robot.keyPress(KeyEvent.VK_ENTER);
				robot.keyRelease(KeyEvent.VK_ENTER);

				troubleshootingDisplay.setForeground(Color.GREEN);
				troubleshootingDisplay.setText("Companion successfully sent: \"" + selectedEntry + "\" <3");
				scheduleNextAction();
			} catch (Exception e) {
				troubleshootingDisplay.setForeground(Color.RED);
				troubleshootingDisplay.setText("Companion Error: Failed to send: </3");
				e.printStackTrace();
			}
		});
	}

	private void toggleStartStop(ActionEvent e) {
		isStarted = !isStarted;
		if (isStarted) {
			troubleshootingDisplay.setForeground(Color.GREEN);
			troubleshootingDisplay.setText("Companion is Turned On <3 UwU");
			new Timer(2000, evt -> {
				scheduleNextAction();
				((Timer) evt.getSource()).stop();
			}).start();
		} else {
			troubleshootingDisplay.setForeground(Color.RED);
			troubleshootingDisplay.setText("Companion is Stopping </3");
			if (taskTimer != null) {
				taskTimer.cancel();
			}
			updateToggleButtonVisualState();
		}
		SwingUtilities.invokeLater(this::updateToggleButtonVisualState);
	}

	private void copyEntryToClipboard(String text) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(text);
		clipboard.setContents(strSel, null);
	}

	private int selectRandomEntry(int size) {
		return (int) (Math.random() * size);
	}

	private int timerRandomizer() {
		int lowerBound = 10000; // 10 seconds
		int upperBound = 20000; // 30 seconds
		return lowerBound + (int) (Math.random() * (upperBound - lowerBound));
	}

	private void addEntryToList(ActionEvent e) {
		String text = textField.getText().trim();
		if (!text.isEmpty()) {
			listModel.addElement(text);
			textField.setText("");
		}
		textField.requestFocusInWindow();
	}

	private void setupFocusManagement() {
		// Button MouseListeners for immediate window focus
		loadUziPresetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.requestFocusInWindow();
			}
		});

		toggleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.requestFocusInWindow();
			}
		});

		loadPresetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.requestFocusInWindow();
			}
		});

		entryList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Timer delayFocusTimer = new Timer(5000, event -> textField.requestFocusInWindow());
				delayFocusTimer.setRepeats(false);
				delayFocusTimer.start();
			}
		});
	}

	public void switchToDiscord() {
		String osName = System.getProperty("os.name").toLowerCase();
		try {
			if (osName.contains("mac")) {
				String[] command = { "osascript", "-e", "tell application \"Discord\" to activate" };
				Runtime.getRuntime().exec(command);
			} else if (osName.contains("win")) {
				String command = "powershell.exe $w=(Get-Process -Name Discord).MainWindowHandle;"
						+ "[Console]::WriteLine($w);" + "Add-Type -\"$using:System.Runtime.InteropServices\";"
						+ "[DllImport(\"user32.dll\")]$f='public static extern bool ShowWindow(IntPtr hWnd, int nCmdShow);';"
						+ "[DllImport(\"user32.dll\")]$g='public static extern bool SetForegroundWindow(IntPtr hWnd);';"
						+ "ShowWindow($w, 3); SetForegroundWindow($w);";
				Runtime.getRuntime().exec(command);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private JButton closeButton() {
		JButton closeButton = new JButton("🛑");
		closeButton.setPreferredSize(new Dimension(40, 80)); // Set both width and height to 30 for a square button that
															//  matches the title bar height
		closeButton.setOpaque(true);
		closeButton.setBackground(new Color(91, 109, 174));
		closeButton.setFocusPainted(false);
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
		closeButton.setBorder(BorderFactory.createCompoundBorder(lineBorder, padding));

		// Hover effect
		closeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				closeButton.setBackground(new Color(57, 68, 109)); // Hover Color
			}

			@Override
			public void mouseExited(MouseEvent e) {
				closeButton.setBackground(new Color(91, 109, 174)); // Non-Hover Color
			}
		});

		closeButton.addActionListener(e -> System.exit(0));

		return closeButton;
	}
}
