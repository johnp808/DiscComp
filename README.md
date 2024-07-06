# Discord Companion Bot

## Description:
A very rudimentary automation bot with UI Designed to make activity leveling in certain discord channels easier while you are afk.
The UI is straight forward with a text box area to enter your own phrases and a start button, with a few additional features.

## Features
* Enter your own phrases
* Select previously added phrases and use the delete key to remove them
* A personal emoji preset for myself as well as a default set of "Preset Phrases" for users.
* Troubleshooting Display

## Future Goals
* Allow users to set the random time interval
* Multitasking support, currently the bot requires control of the users keyboard and screen to copy and paste the messages.

## What I Learned
* Basics of Java Swing to help build the GUI, including Jpanel, JFrame, JList, JButton, etc.
* Thread safety using SwingUtilities.invokeLater to safely update UI elements
* using java.awt.Robot to simulate user actions
* implementing javax.swing.Timer to schedule tasks
* 