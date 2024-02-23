import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RockPaperScissorsFrame extends JFrame {
    private JLabel titleLabel;
    private JButton rockButton;
    private JButton paperButton;
    private JButton scissorsButton;
    private JButton quitButton;
    private JPanel buttonPanel;
    private JLabel playerWinsLabel;
    private JLabel computerWinsLabel;
    private JLabel tiesLabel;
    private JTextField playerWinsField;
    private JTextField computerWinsField;
    private JTextField tiesField;
    private JTextArea resultsTextArea;

    private int playerWins;
    private int computerWins;
    private int ties;

    private Strategy computerStrategy;

    public RockPaperScissorsFrame(Strategy strategy) {
        setTitle("Rock Paper Scissors Game");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        titleLabel = new JLabel("Rock Paper Scissors Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);


        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Select Your Move"));
        rockButton = new JButton(new ImageIcon(new ImageIcon("src/rock.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        paperButton = new JButton(new ImageIcon(new ImageIcon("src/paper.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        scissorsButton = new JButton(new ImageIcon(new ImageIcon("src/scissors.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        quitButton = new JButton("Quit");
        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.CENTER);


        JPanel statsPanel = new JPanel(new GridLayout(3, 2));
        playerWinsLabel = new JLabel("Player Wins:");
        computerWinsLabel = new JLabel("Computer Wins:");
        tiesLabel = new JLabel("Ties:");
        playerWinsField = new JTextField(5);
        computerWinsField = new JTextField(5);
        tiesField = new JTextField(5);
        playerWinsField.setEditable(false);
        computerWinsField.setEditable(false);
        tiesField.setEditable(false);
        statsPanel.add(playerWinsLabel);
        statsPanel.add(playerWinsField);
        statsPanel.add(computerWinsLabel);
        statsPanel.add(computerWinsField);
        statsPanel.add(tiesLabel);
        statsPanel.add(tiesField);
        add(statsPanel, BorderLayout.SOUTH);


        resultsTextArea = new JTextArea(10, 40);
        resultsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsTextArea);
        add(scrollPane, BorderLayout.EAST);


        playerWins = 0;
        computerWins = 0;
        ties = 0;


        this.computerStrategy = strategy;


        rockButton.addActionListener(e -> playGame("rock"));
        paperButton.addActionListener(e -> playGame("paper"));
        scissorsButton.addActionListener(e -> playGame("scissors"));
        quitButton.addActionListener(e -> System.exit(0));
    }

    private void playGame(String playerMove) {
        String[] moves = {"rock", "paper", "scissors"};
        Random random = new Random();
        String computerMove = computerStrategy.determineMove(playerMove, moves);

        if (playerMove.equals(computerMove)) {
            ties++;
            resultsTextArea.append("It's a tie!\n");
        } else if ((playerMove.equals("rock") && computerMove.equals("scissors"))
                || (playerMove.equals("scissors") && computerMove.equals("paper"))
                || (playerMove.equals("paper") && computerMove.equals("rock"))) {
            playerWins++;
            resultsTextArea.append("You won! (" + playerMove + " beats " + computerMove + ")\n");
        } else {
            computerWins++;
            resultsTextArea.append("Computer wins! (" + computerMove + " beats " + playerMove + ")\n");
        }

        updateStats();
    }

    private void updateStats() {
        playerWinsField.setText(String.valueOf(playerWins));
        computerWinsField.setText(String.valueOf(computerWins));
        tiesField.setText(String.valueOf(ties));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Strategy leastUsedStrategy = new LeastUsedStrategy();
            RockPaperScissorsFrame frame = new RockPaperScissorsFrame(leastUsedStrategy);
            frame.setVisible(true);
        });
    }
}

interface Strategy {
    String determineMove(String playerMove, String[] moves);
}

class LeastUsedStrategy implements Strategy {
    @Override
    public String determineMove(String playerMove, String[] moves) {
        Random random = new Random();
        return moves[random.nextInt(moves.length)];
    }
}

class MostUsedStrategy implements Strategy {
    @Override
    public String determineMove(String playerMove, String[] moves) {
        Random random = new Random();
        return moves[random.nextInt(moves.length)];
    }
}

class LastUsedStrategy implements Strategy {
    private String lastPlayerMove;

    @Override
    public String determineMove(String playerMove, String[] moves) {
        if (lastPlayerMove == null) {
            lastPlayerMove = playerMove;
            Random random = new Random();
            return moves[random.nextInt(moves.length)];
        } else {
            return lastPlayerMove;
        }
    }
}

class RandomStrategy implements Strategy {
    @Override
    public String determineMove(String playerMove, String[] moves) {
        Random random = new Random();
        return moves[random.nextInt(moves.length)];
    }
}

class CheatStrategy implements Strategy {
    @Override
    public String determineMove(String playerMove, String[] moves) {
        Random random = new Random();
        if (random.nextInt(100) < 10) {
            if (playerMove.equals("rock")) {
                return "paper";
            } else if (playerMove.equals("paper")) {
                return "scissors";
            } else {
                return "rock";
            }
        } else {
            return moves[random.nextInt(moves.length)];
        }
    }
}

