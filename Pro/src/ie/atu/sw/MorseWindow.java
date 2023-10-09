//BRENDAN MCSHANE G00410478
package ie.atu.sw;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class MorseWindow {
	private Colour[] colours = Colour.values(); // This might come in handy
	private ThreadLocalRandom rand = ThreadLocalRandom.current(); // This will definitely come in handy
	private JFrame win; // The GUI Window
	private JTextArea txtOutput = new JTextArea(); // The text box to output the results to
	private JTextField txtFilePath; // The file name to process

	@SuppressWarnings("unlikely-arg-type")
	public MorseWindow() {

		// Here are my encoding and decoding maps for Morse-code
		// I access this map in my Encoding and Decoding buttons

		// Encoding Map
		// this uses a hashmap for the encoding of the textfile
		Map<Character, String> encode = new HashMap<>();
		encode.put('a', ".-");
		encode.put('b', "-...");
		encode.put('c', "-.-.");
		encode.put('d', "-..");
		encode.put('e', ".");
		encode.put('f', "..-.");
		encode.put('g', "--.");
		encode.put('h', "....");
		encode.put('i', "..");
		encode.put('j', ".---");
		encode.put('k', "-.-");
		encode.put('l', ".-..");
		encode.put('m', "--");
		encode.put('n', "-.");
		encode.put('o', "---");
		encode.put('p', ".--.");
		encode.put('q', "--.-");
		encode.put('r', ".-.");
		encode.put('s', "...");
		encode.put('t', "-");
		encode.put('u', "..-");
		encode.put('v', "...-");
		encode.put('w', ".--");
		encode.put('x', "-..-");
		encode.put('y', "-.--");
		encode.put('z', "--..");

		// Decoding map
		Map<String, Character> decode = new HashMap<>();
		decode.put(".-", 'a');
		decode.put("-...", 'b');
		decode.put("-.-.", 'c');
		decode.put("-..", 'd');
		decode.put(".", 'e');
		decode.put("..-.", 'f');
		decode.put("--.", 'g');
		decode.put("....", 'h');
		decode.put("..", 'i');
		decode.put(".---", 'j');
		decode.put("-.-", 'k');
		decode.put(".-..", 'l');
		decode.put("--", 'm');
		decode.put("-.", 'n');
		decode.put("---", 'o');
		decode.put(".--.", 'p');
		decode.put("--.-", 'q');
		decode.put(".-.", 'r');
		decode.put("...", 's');
		decode.put("-", 't');
		decode.put("..-", 'u');
		decode.put("...-", 'v');
		decode.put(".--", 'w');
		decode.put("-..-", 'x');
		decode.put("-.--", 'y');
		decode.put("--..", 'z');
		/*
		 * Create a window for the application. Building a GUI is an example of
		 * "divide and conquer" in action. A GUI is really a tree. That is why we are
		 * able to create and configure GUIs in XML.
		 */
		win = new JFrame();
		win.setTitle("Data Structures & Algorithms 2023 - Morse Encoder/Decoder");
		win.setSize(650, 400);
		win.setResizable(false);
		win.setLayout(new FlowLayout());

		/*
		 * The top panel will contain the file chooser and encode / decode buttons
		 */
		var top = new JPanel(new FlowLayout(FlowLayout.LEADING));
		top.setBorder(new javax.swing.border.TitledBorder("Select File"));
		top.setPreferredSize(new Dimension(600, 80));

		txtFilePath = new JTextField(20);
		txtFilePath.setPreferredSize(new Dimension(100, 30));

		var chooser = new JButton("Browse...");
		chooser.addActionListener((e) -> {
			var fc = new JFileChooser(System.getProperty("user.dir"));
			var val = fc.showOpenDialog(win);
			if (val == JFileChooser.APPROVE_OPTION) {
				var file = fc.getSelectedFile().getAbsoluteFile();
				txtFilePath.setText(file.getAbsolutePath());
			}
		});

		var btnEncodeFile = new JButton("Encode");
		btnEncodeFile.addActionListener(e -> {
			/*
			 * 
			 */

			// Convert string to Morse code
			String text = "hello world";
			StringBuilder morse = new StringBuilder();
			for (char c : text.toCharArray()) {
				if (encode.containsKey(Character.toLowerCase(c))) {
					morse.append(encode.get(Character.toLowerCase(c))).append(" ");
				} else if (c == ' ') {
					morse.append("  ");
				}
			}
			// System.out.println(morse.toString());
			// Write out a message 10 times when the Encode button is clicked
			for (int i = 0; i < 10; i++) {
				appendText("THIS IS LONDON CALLING. COME IN TOBRUK.\n\n");
				String london = "THIS IS LONDON CALLING. COME IN TOBRUK.\n\n";
				StringBuilder morseLondon = new StringBuilder();
				for (char c : london.toCharArray()) {
					if (encode.containsKey(Character.toLowerCase(c))) {
						morseLondon.append(encode.get(Character.toLowerCase(c))).append(" ");
					} else if (c == ' ') {
						morseLondon.append("  ");
					}
				}
				appendText(morseLondon.toString());

				// Beginning of file reading section
				String path = txtFilePath.getText(); // Call getText() to get the file name
				replaceText("Encoding.....\n\n");
				// try/catch used for file reading
				try {
					File myObj = new File(path);
					Scanner reader = new Scanner(myObj);
					// this is counter for while loop to print 10 lines
					int counter = 1;
					// this is loop for printing and encoding 10 lines
					while (counter < 200) {
						String data = reader.nextLine();

						// this prints the text from the chosen file to console and GUI
						System.out.println(data);

						appendText(data);

						// This wil be done in O(n) -> Linear time
						String textForFile = data;
						StringBuilder morseForFile = new StringBuilder();
						for (char c : textForFile.toCharArray()) {
							if (encode.containsKey(Character.toLowerCase(c))) {
								morseForFile.append(encode.get(Character.toLowerCase(c))).append(" ");
							} else if (c == ' ') {
								morseForFile.append("  ");
							}
						}

						appendText("\n" + morseForFile.toString() + "\n\n");
						System.out.println("\n" + morseForFile.toString() + "\n\n");

						// counter makes sure it reads all the lines from while loop
						counter++;
						// closes reader
					}
					reader.close();

					// this catches in case of an error
					// an error occurs when file isn't chosen
				} catch (FileNotFoundException eeee) {
					System.out.println("An error occurred.");
					eeee.printStackTrace();
				}

			}
		});

		// Decoding button
		var btnDecodeFile = new JButton("Decode");
		btnDecodeFile.addActionListener(e -> {
			/*
			 * 
			 */

			// This is an example of how to convert Morse into plain text

			// String morse = "- .... .. ... / -- --- .-. ... . / -.-. --- -.. .";
			// StringBuilder text = new StringBuilder();
			// for (String word : morse.split(" / ")) {
			// for (String letter : word.split(" ")) {
			// if (deCode.containsKey(letter)) {
			// text.append(deCode.get(letter));
			// }
			// }
			// text.append(" ");
			// }
			// System.out.println(text.toString().trim());

			// Beginning of file reading
			String path = txtFilePath.getText(); // Call getText() to get the file name
			replaceText("Decoding.....\n\n");

			// This wil be done in O(n) -> Linear time

			try {
				File myObj = new File(path);
				Scanner myReader = new Scanner(myObj);
				// this is counter for while loop to print 10 lines
				int counter = 1;
				// this is loop for printing and encoding 10 lines
				while (counter < 200) {

					String data = myReader.nextLine();

					// This prints the text from the chosen file to console and GUI
					System.out.println("[ENCODED] " + data);
					appendText("[ENCODED]  " + data);
					String textForFile = data;
					StringBuilder morseForFile = new StringBuilder();
					for (char c : textForFile.toCharArray()) {
						if (encode.containsKey(Character.toLowerCase(c))) {
							morseForFile.append(encode.get(Character.toLowerCase(c))).append(" ");
						} else if (c == ' ') {
							morseForFile.append("  ");
						}
					}
					System.out.println("[DECODED] " + morseForFile.toString());

					appendText("\n[DECODED]  " + morseForFile.toString() + "\n");

					counter++;
				}
				myReader.close();

				// this catches in case of an error
				// an error occurs when file isn't chosen
			} catch (FileNotFoundException eeee) {
				System.out.println("An error occurred.");
				eeee.printStackTrace();
			}

		});

		// Add all the components to the panel and the panel to the window
		top.add(txtFilePath);
		top.add(chooser);
		top.add(btnEncodeFile);
		top.add(btnDecodeFile);
		win.getContentPane().add(top); // Add the panel to the window

		/*
		 * The middle panel contains the coloured square and the text area for
		 * displaying the outputted text.
		 */
		var middle = new JPanel(new FlowLayout(FlowLayout.LEADING));
		middle.setPreferredSize(new Dimension(600, 200));

		var dot = new JPanel();
		dot.setBorder(new SoftBevelBorder(SoftBevelBorder.RAISED));
		dot.setBackground(getRandomColour());
		dot.setPreferredSize(new Dimension(140, 150));
		dot.addMouseListener(new MouseAdapter() {
			// Can't use a lambda against MouseAdapter because it is not a SAM
			public void mousePressed(MouseEvent e) {
				dot.setBackground(getRandomColour());
			}
		});

		// Add the text area
		txtOutput.setLineWrap(true);
		txtOutput.setWrapStyleWord(true);
		txtOutput.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));

		var scroller = new JScrollPane(txtOutput);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setPreferredSize(new Dimension(450, 150));
		scroller.setMaximumSize(new Dimension(450, 150));

		// Add all the components to the panel and the panel to the window
		middle.add(dot);
		middle.add(scroller);
		win.getContentPane().add(middle);

		/*
		 * The bottom panel contains the clear and quit buttons.
		 */
		var bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottom.setPreferredSize(new java.awt.Dimension(500, 50));

		// Create and add Clear and Quit buttons
		var clear = new JButton("Clear");
		clear.addActionListener((e) -> txtOutput.setText(""));

		var quit = new JButton("Quit");
		quit.addActionListener((e) -> System.exit(0));

		// Add all the components to the panel and the panel to the window
		bottom.add(clear);
		bottom.add(quit);
		win.getContentPane().add(bottom);

		/*
		 * All done. Now show the configured Window.
		 */
		win.setVisible(true);
	}

	private Color getRandomColour() {
		Colour c = colours[rand.nextInt(0, colours.length)];
		return Color.decode(c.hex() + "");
	}

	protected void replaceText(String text) {
		txtOutput.setText(text);
	}

	protected void appendText(String text) {
		txtOutput.setText(txtOutput.getText() + " " + text);
	}
}