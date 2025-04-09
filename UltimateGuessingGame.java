//Base code (Normal)
// import java.util.Scanner;
// import java.util.Random;

// public class GuessingGame {
//     public static void main(String[] args) {
//         Random random = new Random();
//         int secretNumber = random.nextInt(100) + 1;
//         Scanner scanner = new Scanner(System.in);
//         int attempts = 0;
//         boolean hasGuessedCorrectly = false;
        
//         System.out.println("I'm thinking of a number between 1 and 100. Can you guess it?");
        
//         while (!hasGuessedCorrectly) {
//             System.out.print("Your guess: ");
//             int userGuess = scanner.nextInt();
//             attempts++;
            
//             if (userGuess == secretNumber) {
//                 hasGuessedCorrectly = true;
//                 System.out.println("Congratulations! You guessed it in " + attempts + " tries!");
//             } else if (userGuess > secretNumber) {
//                 System.out.println("Too high! Try again.");
//             } else {
//                 System.out.println("Too low! Try again.");
//             }
//         }
        
//         scanner.close();
//     }
// }
//x------------------------------------------------------- x ---------------------------------------- x
//                                                 Code with Jframe
//x------------------------------------------------------- x ---------------------------------------- x
// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;
// import java.util.Random;

// public class GuessingGameGUI {
//     private int secretNumber;
//     private int attempts;
//     private JFrame frame;
//     private JTextField guessField;
//     private JLabel messageLabel;
//     private JLabel attemptsLabel;

//     public GuessingGameGUI() {
//         // Initialize game
//         Random random = new Random();
//         secretNumber = random.nextInt(100) + 1; // Number between 1-100
//         attempts = 0;

//         // Create main window
//         frame = new JFrame("Number Guessing Game");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(350, 200);
//         frame.setLayout(new FlowLayout());

//         // Create components
//         JLabel titleLabel = new JLabel("Guess a number between 1 and 100:");
//         guessField = new JTextField(10);
//         JButton guessButton = new JButton("Guess");
//         messageLabel = new JLabel("Enter your first guess!");
//         attemptsLabel = new JLabel("Attempts: 0");

//         // Add action listener for the button
//         guessButton.addActionListener(new ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//                 checkGuess();
//             }
//         });

//         // Also check guess when Enter is pressed in text field
//         guessField.addActionListener(new ActionListener() {
//             public void actionPerformed(ActionEvent e) {
//                 checkGuess();
//             }
//         });

//         // Add components to the frame
//         frame.add(titleLabel);
//         frame.add(guessField);
//         frame.add(guessButton);
//         frame.add(messageLabel);
//         frame.add(attemptsLabel);

//         // Display the window
//         frame.setVisible(true);
//     }

//     private void checkGuess() {
//         try {
//             int userGuess = Integer.parseInt(guessField.getText());
//             attempts++;
//             attemptsLabel.setText("Attempts: " + attempts);

//             if (userGuess == secretNumber) {
//                 messageLabel.setText("Correct! You won in " + attempts + " attempts!");
//                 guessField.setEnabled(false);
//             } else if (userGuess > secretNumber) {
//                 messageLabel.setText("Too high! Try again.");
//             } else {
//                 messageLabel.setText("Too low! Try again.");
//             }
//         } catch (NumberFormatException e) {
//             messageLabel.setText("Please enter a valid number!");
//         }
        
//         guessField.setText(""); // Clear the field for next guess
//         guessField.requestFocus(); // Put cursor back in field
//     }

//     public static void main(String[] args) {
//         // Create the GUI on the Event Dispatch Thread
//         SwingUtilities.invokeLater(new Runnable() {
//             public void run() {
//                 new GuessingGameGUI();
//             }
//         });
//     }
// }

//x------------------------------------------------------- x ---------------------------------------- x
//                                          Code with Advanced Jframe (AI USED)
//x------------------------------------------------------- x ---------------------------------------- x
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
// import java.awt.event.*;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
// import javax.sound.sampled.*;
// import java.io.*;

public class UltimateGuessingGame extends JFrame {
    // Game state variables
    private int secretNumber;
    private int attempts;
    private int maxNumber = 100;
    private int gamesPlayed = 0;
    private int gamesWon = 0;
    private int bestScore = Integer.MAX_VALUE;
    private boolean isMultiplayer = false;
    private int currentPlayer = 1;
    private int player1Attempts = 0;
    private int player2Attempts = 0;
    private List<String> gameHistory = new ArrayList<>();
    
    // UI Components
    private JLabel titleLabel;
    private JLabel messageLabel;
    private JLabel attemptsLabel;
    private JLabel statsLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JButton newGameButton;
    private JButton hintButton;
    private JComboBox<String> difficultyCombo;
    private JComboBox<String> themeCombo;
    private JToggleButton multiplayerToggle;
    private JLabel playerIndicator;
    private JTextArea historyArea;
    private JProgressBar progressBar;
    
    // Colors
    private Color primaryColor = new Color(70, 130, 180);
    private Color secondaryColor = new Color(255, 255, 255);
    private Color backgroundColor = new Color(240, 240, 240);
    private Color textColor = new Color(50, 50, 50);

    public UltimateGuessingGame() {
        // Setup main window
        setTitle("Ultimate Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setMinimumSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        
        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(backgroundColor);
        
        // Create game panel (center)
        JPanel gamePanel = createGamePanel();
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        
        // Create control panel (south)
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        
        // Create info panel (east)
        JPanel infoPanel = createInfoPanel();
        mainPanel.add(infoPanel, BorderLayout.EAST);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Start new game
        startNewGame();
        
        // Set visible
        setVisible(true);
    }
    
    private JPanel createGamePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(primaryColor, 2),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        // Title label
        titleLabel = new JLabel("ULTIMATE GUESSING GAME");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(primaryColor);
        
        // Player indicator
        playerIndicator = new JLabel("Player 1's turn");
        playerIndicator.setFont(new Font("Arial", Font.BOLD, 16));
        playerIndicator.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerIndicator.setForeground(primaryColor);
        playerIndicator.setVisible(false);
        
        // Message label
        messageLabel = new JLabel("Guess a number between 1 and " + maxNumber);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setForeground(textColor);
        
        // Attempts label
        attemptsLabel = new JLabel("Attempts: 0");
        attemptsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        attemptsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        attemptsLabel.setForeground(textColor);
        
        // Progress bar
        progressBar = new JProgressBar(1, maxNumber);
        progressBar.setStringPainted(true);
        progressBar.setString("");
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        progressBar.setForeground(primaryColor);
        
        // Guess field
        guessField = new JTextField(10);
        guessField.setFont(new Font("Arial", Font.PLAIN, 18));
        guessField.setMaximumSize(new Dimension(200, 30));
        guessField.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessField.addActionListener(e -> checkGuess());
        
        // Guess button
        guessButton = new JButton("Submit Guess");
        styleButton(guessButton);
        guessButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessButton.addActionListener(e -> checkGuess());
        
        // Add components to panel
        panel.add(Box.createVerticalStrut(10));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(playerIndicator);
        panel.add(Box.createVerticalStrut(10));
        panel.add(messageLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(attemptsLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(progressBar);
        panel.add(Box.createVerticalStrut(20));
        panel.add(guessField);
        panel.add(Box.createVerticalStrut(15));
        panel.add(guessButton);
        
        return panel;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBackground(backgroundColor);
        
        // First row
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        row1.setBackground(backgroundColor);
        
        // Difficulty combo
        difficultyCombo = new JComboBox<>(new String[]{"Easy (1-50)", "Medium (1-100)", "Hard (1-200)", "Expert (1-500)"});
        difficultyCombo.setSelectedIndex(1);
        difficultyCombo.addActionListener(e -> updateDifficulty());
        styleComboBox(difficultyCombo);
        
        // Theme combo
        themeCombo = new JComboBox<>(new String[]{"Light Theme", "Dark Theme"});
        themeCombo.addActionListener(e -> changeTheme());
        styleComboBox(themeCombo);
        
        // Multiplayer toggle
        multiplayerToggle = new JToggleButton("Multiplayer: OFF");
        styleToggleButton(multiplayerToggle);
        multiplayerToggle.addActionListener(e -> toggleMultiplayer());
        
        row1.add(difficultyCombo);
        row1.add(themeCombo);
        row1.add(multiplayerToggle);
        
        // Second row
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        row2.setBackground(backgroundColor);
        
        // New game button
        newGameButton = new JButton("New Game");
        styleButton(newGameButton);
        newGameButton.addActionListener(e -> startNewGame());
        
        // Hint button
        hintButton = new JButton("Get Hint (3 left)");
        styleButton(hintButton);
        hintButton.addActionListener(e -> giveHint());
        
        row2.add(newGameButton);
        row2.add(hintButton);
        
        panel.add(row1);
        panel.add(row2);
        
        return panel;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Game Info"),
            new EmptyBorder(5, 5, 5, 5)
        ));
        
        // Stats label
        statsLabel = new JLabel("<html>Games Played: 0<br>Games Won: 0<br>Best Score: -</html>");
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statsLabel.setForeground(textColor);
        
        // History area
        historyArea = new JTextArea(10, 20);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        historyArea.setForeground(textColor);
        historyArea.setBackground(backgroundColor);
        JScrollPane scrollPane = new JScrollPane(historyArea);
        
        panel.add(statsLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Style methods for different components
    private void styleButton(JButton button) {
        // Use a more vibrant blue that will be visible in both themes
        Color buttonColor = new Color(41, 98, 255);  // A more vibrant blue
        
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(buttonColor);
        button.setForeground(Color.BLACK);  // black text for better contrast
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(buttonColor.darker(), 2),
            new EmptyBorder(5, 15, 5, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Make sure the button shows its background color (important on some look and feels)
        button.setContentAreaFilled(true);
        button.setOpaque(true);
    }
    
    private void styleToggleButton(JToggleButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(secondaryColor);
        button.setForeground(primaryColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(primaryColor.darker(), 2),
            new EmptyBorder(5, 15, 5, 15)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void styleComboBox(JComboBox<String> combo) {
        combo.setFont(new Font("Arial", Font.PLAIN, 14));
        combo.setBackground(secondaryColor);
        combo.setForeground(textColor);
    }
    
    // Game logic methods
    private void startNewGame() {
        // Reset game state
        attempts = 0;
        player1Attempts = 0;
        player2Attempts = 0;
        currentPlayer = 1;
        
        // Set secret number based on current difficulty
        Random random = new Random();
        secretNumber = random.nextInt(maxNumber) + 1;
        
        // Reset UI
        attemptsLabel.setText("Attempts: 0");
        messageLabel.setText("Guess a number between 1 and " + maxNumber);
        guessField.setText("");
        guessField.requestFocus();
        progressBar.setMaximum(maxNumber);
        progressBar.setValue(0);
        progressBar.setString("");
        
        // Update player indicator for multiplayer
        playerIndicator.setText("Player " + currentPlayer + "'s turn");
        playerIndicator.setVisible(isMultiplayer);
        
        // Reset hint button
        hintButton.setText("Get Hint (3 left)");
        hintButton.setEnabled(true);
        
        // Log new game
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String difficulty = (String) difficultyCombo.getSelectedItem();
        String gameInfo = timestamp + " - New game started. Difficulty: " + difficulty + 
                         (isMultiplayer ? " (Multiplayer)" : " (Single player)");
        gameHistory.add(gameInfo);
        updateHistoryArea();
        
        System.out.println("DEBUG: New game started with secret number: " + secretNumber);
    }
    
    private void checkGuess() {
        // Try to parse input
        int guess;
        try {
            guess = Integer.parseInt(guessField.getText());
            guessField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Check if guess is in valid range
        if (guess < 1 || guess > maxNumber) {
            JOptionPane.showMessageDialog(this, "Please enter a number between 1 and " + maxNumber, 
                                        "Out of Range", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Process the guess
        attempts++;
        if (isMultiplayer) {
            if (currentPlayer == 1) {
                player1Attempts++;
            } else {
                player2Attempts++;
            }
        }
        
        // Update attempts display
        attemptsLabel.setText("Attempts: " + attempts);
        
        // Check if guess is correct
        if (guess == secretNumber) {
            handleCorrectGuess();
        } else if (guess < secretNumber) {
            messageLabel.setText("Too low! Try a higher number.");
            progressBar.setValue(guess);
            progressBar.setString("Last guess: " + guess);
            playSound("low");
        } else {
            messageLabel.setText("Too high! Try a lower number.");
            progressBar.setValue(guess);
            progressBar.setString("Last guess: " + guess);
            playSound("high");
        }
        
        // Handle multiplayer turn change if guess was incorrect
        if (isMultiplayer && guess != secretNumber) {
            currentPlayer = (currentPlayer == 1) ? 2 : 1;
            playerIndicator.setText("Player " + currentPlayer + "'s turn");
        }
    }
    
    private void handleCorrectGuess() {
        // Update game statistics
        gamesPlayed++;
        gamesWon++;
        if (attempts < bestScore) {
            bestScore = attempts;
        }
        updateStats();
        
        // Show congratulations message
        String winner = isMultiplayer ? "Player " + currentPlayer : "You";
        String attemptsInfo = isMultiplayer ? 
                "Player 1: " + player1Attempts + " attempts, Player 2: " + player2Attempts + " attempts" :
                attempts + " attempts";
        
        JOptionPane.showMessageDialog(this, 
                winner + " won! The secret number was " + secretNumber + ".\n" + attemptsInfo,
                "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
        
        // Log the win
        String gameResult = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                          " - Game won! " + attemptsInfo;
        gameHistory.add(gameResult);
        updateHistoryArea();
        
        // Play victory sound
        playSound("win");
        
        // Start a new game
        startNewGame();
    }
    
    private void updateStats() {
        String bestScoreText = (bestScore == Integer.MAX_VALUE) ? "-" : String.valueOf(bestScore);
        statsLabel.setText("<html>Games Played: " + gamesPlayed + 
                          "<br>Games Won: " + gamesWon + 
                          "<br>Best Score: " + bestScoreText + "</html>");
    }
    
    private void updateHistoryArea() {
        StringBuilder history = new StringBuilder();
        int displayCount = Math.min(gameHistory.size(), 10); // Show last 10 entries
        
        for (int i = gameHistory.size() - displayCount; i < gameHistory.size(); i++) {
            history.append(gameHistory.get(i)).append("\n");
        }
        
        historyArea.setText(history.toString());
    }
    
    private void updateDifficulty() {
        String difficulty = (String) difficultyCombo.getSelectedItem();
        
        switch (difficulty) {
            case "Easy (1-50)":
                maxNumber = 50;
                break;
            case "Medium (1-100)":
                maxNumber = 100;
                break;
            case "Hard (1-200)":
                maxNumber = 200;
                break;
            case "Expert (1-500)":
                maxNumber = 500;
                break;
        }
        
        // Update UI for new difficulty
        messageLabel.setText("Guess a number between 1 and " + maxNumber);
        progressBar.setMaximum(maxNumber);
        
        // Start a new game with new difficulty
        startNewGame();
    }
    
    private void changeTheme() {
        String theme = (String) themeCombo.getSelectedItem();
        
        if ("Dark Theme".equals(theme)) {
            // Dark theme colors
            primaryColor = new Color(25, 118, 210);
            secondaryColor = new Color(200, 200, 200);
            backgroundColor = new Color(50, 50, 50);
            textColor = new Color(220, 220, 220);
        } else {
            // Light theme colors (default)
            primaryColor = new Color(70, 130, 180);
            secondaryColor = new Color(255, 255, 255);
            backgroundColor = new Color(240, 240, 240);
            textColor = new Color(50, 50, 50);
        }
        
        // Apply theme to components
        applyThemeToComponents();
    }
    
    private void applyThemeToComponents() {
        // Update main components
        getContentPane().setBackground(backgroundColor);
        
        // Update panels
        for (Component comp : getContentPane().getComponents()) {
            if (comp instanceof JPanel) {
                updatePanelTheme((JPanel) comp);
            }
        }
        
        // Update buttons
        styleButton(guessButton);
        styleButton(newGameButton);
        styleButton(hintButton);
        styleToggleButton(multiplayerToggle);
        
        // Update combo boxes
        styleComboBox(difficultyCombo);
        styleComboBox(themeCombo);
        
        // Update text components
        titleLabel.setForeground(primaryColor);
        messageLabel.setForeground(textColor);
        attemptsLabel.setForeground(textColor);
        statsLabel.setForeground(textColor);
        playerIndicator.setForeground(primaryColor);
        historyArea.setForeground(textColor);
        historyArea.setBackground(backgroundColor);
        
        // Update progress bar
        progressBar.setForeground(primaryColor);
        progressBar.setBackground(backgroundColor);
        
        // Force repaint
        repaint();
    }
    
    private void updatePanelTheme(JPanel panel) {
        panel.setBackground(backgroundColor);
        
        // Recursively update all child components
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                updatePanelTheme((JPanel) comp);
            }
        }
    }
    
    private void toggleMultiplayer() {
        isMultiplayer = multiplayerToggle.isSelected();
        multiplayerToggle.setText("Multiplayer: " + (isMultiplayer ? "ON" : "OFF"));
        
        // Update UI for multiplayer mode
        playerIndicator.setVisible(isMultiplayer);
        playerIndicator.setText("Player 1's turn");
        
        // Reset game state
        startNewGame();
    }
    
    private void giveHint() {
        // Parse remaining hints from button text
        String buttonText = hintButton.getText();
        int hintsLeft = Character.getNumericValue(buttonText.charAt(buttonText.indexOf('(') + 1));
        
        if (hintsLeft > 0) {
            // Generate hint
            String hint;
            Random random = new Random();
            int hintType = random.nextInt(3);
            
            switch (hintType) {
                case 0:
                    // Even/Odd hint
                    hint = "The secret number is " + (secretNumber % 2 == 0 ? "even" : "odd") + ".";
                    break;
                case 1:
                    // Range hint
                    int rangeSize = maxNumber / 5;
                    int lowerBound = Math.max(1, secretNumber - rangeSize);
                    int upperBound = Math.min(maxNumber, secretNumber + rangeSize);
                    hint = "The secret number is between " + lowerBound + " and " + upperBound + ".";
                    break;
                default:
                    // Digit sum hint
                    int digitSum = 0;
                    int num = secretNumber;
                    while (num > 0) {
                        digitSum += num % 10;
                        num /= 10;
                    }
                    hint = "The sum of the digits of the secret number is " + digitSum + ".";
                    break;
            }
            
            // Display hint
            JOptionPane.showMessageDialog(this, hint, "Hint", JOptionPane.INFORMATION_MESSAGE);
            
            // Update hints remaining
            hintsLeft--;
            hintButton.setText("Get Hint (" + hintsLeft + " left)");
            
            if (hintsLeft == 0) {
                hintButton.setEnabled(false);
            }
            
            // Log hint usage
            gameHistory.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                          " - Hint used: " + hint);
            updateHistoryArea();
        }
    }
    
    private void playSound(String soundType) {
        try {
            // Define sound file paths (you need to create these sound files)
            String soundPath;
            switch (soundType) {
                case "win":
                    soundPath = "/sounds/win.wav";
                    break;
                case "low":
                    soundPath = "/sounds/low.wav";
                    break;
                case "high":
                    soundPath = "/sounds/high.wav";
                    break;
                default:
                    return;
            }
            
            // Load sound file from resources (commented out as files may not exist)
            /*
            InputStream soundStream = getClass().getResourceAsStream(soundPath);
            if (soundStream == null) {
                System.err.println("Sound file not found: " + soundPath);
                return;
            }
            
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                new BufferedInputStream(soundStream));
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            */
            
            // For testing without sound files
            System.out.println("Playing sound: " + soundType);
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new UltimateGuessingGame();
        });
    }
}