<h1 align="center"> <img src="src/resources/DisCompLogo.png" width="40" height="40"> DisComp <img src="src/resources/DisCompLogo.png" style="transform: rotateY(180deg)"; width="40" height="40"> </h1> 

<h2 align="center"> Description </h2>

A combination of Discord and Companion, a very rudimentary automation afk bot. I Designed DisComp to make activity leveling in certain discord channels easier while you are not at your computer. <br>

I designed this application to gain a better understanding of creating a GUI for a java program as I had never done it before. <br>
I also wanted to create something for me and my friends to use for our discord channels. <br>

The UI is straight forward with a text box area to enter your own phrases, a start button, along with a few additional features. <br>

DisComp will select a time between (1) to (3) minutes to send a random message from your list of phrases into the discord channel you are currently active in. <br>

It is currently at a working state, so I also included the application compiled into a DMG with the latest version to be used on MacOs. <br>
Just drag and drop the DisComp app into the Applications folder after mounting. <br>

<img src="src/resources/DisCompVolume.png" width="40" height="40"> 

<h2 align="center"> Features </h2>

* Enter custom phrases
* Troubleshooting Display
* A random emoji preset as well as a phrase preset
* Select previously added phrases and use the delete key to remove them

<h2 align="center"> Example </h2> 


<p align="center"> 
	<img src="src/resources/example.png" width="360" height="360"> 
</p>

<h2 align="center"> Future Goal Checklist </h2> 

1. - [ ] Multitasking support
2. - [ ] Allow users to set the random time interval

<h2 align="center"> What I Learned </h2> 

* Basics of Java Swing to help build the GUI, including Jpanel, JFrame, JList, JButton, etc.
* Thread safety using SwingUtilities.invokeLater to safely update UI elements
* implementing javax.swing.Timer to schedule tasks
* using java.awt.Robot to simulate user actions
* Convert .jar into a .app, then a custom DMG