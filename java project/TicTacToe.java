import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {

    private static final int BOARD_SIZE = 9;
    private static final int[] WINNING_COMBINATIONS = { 0, 1, 2, 3, 4, 5, 6, 7, 8,
            0, 3, 6, 1, 4, 7, 2, 5, 8,
            0, 4, 8, 2, 4, 6 };

    private Random random = new Random();
    private JFrame frame = new JFrame();
    private JPanel titlePanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JLabel textField = new JLabel();
    private JButton[] buttons = new JButton[BOARD_SIZE];
    private boolean player1Turn;

    public TicTacToe() {
        initializeFrame();
        initializeTitlePanel();
        initializeButtonPanel();
        initializeButtons();
        firstTurn();
    }

    private void initializeFrame() {
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initializeTitlePanel() {
        textField.setBackground(new Color(25, 25, 25));
        textField.setForeground(new Color(25, 255, 0));
        textField.setFont(new Font("Ink freed", Font.BOLD, 75));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Tic-Tac-toe");
        textField.setOpaque(true);

        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 800, 100);
        titlePanel.add(textField);

        frame.add(titlePanel, BorderLayout.NORTH);
    }

    private void initializeButtonPanel() {
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttonPanel.setBackground(new Color(150, 150, 150));

        frame.add(buttonPanel);
    }

    private void initializeButtons() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            buttons[i] = new JButton();
            buttonPanel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (e.getSource() == buttons[i] && buttons[i].getText().isEmpty()) {
                handleButtonClick(i);
            }
        }
    }

    private void handleButtonClick(int i) {
        if (player1Turn) {
            markButton(buttons[i], "X", "O turn");
        } else {
            markButton(buttons[i], "O", "X turn");
        }
        player1Turn = !player1Turn;
        check();
    }

    private void markButton(JButton button, String mark, String nextTurnText) {
        button.setForeground(player1Turn ? Color.RED : Color.BLUE);
        button.setText(mark);
        textField.setText(nextTurnText);
    }

    private void firstTurn() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        player1Turn = random.nextInt(2) == 0;
        textField.setText(player1Turn ? "X turn" : "O turn");
    }

    private void check() {
        for (int i = 0; i < WINNING_COMBINATIONS.length; i += 3) {
            if (checkCombination(WINNING_COMBINATIONS[i], WINNING_COMBINATIONS[i + 1], WINNING_COMBINATIONS[i + 2])) {
                if (player1Turn) {
                    gameOver("X wins");
                } else {
                    gameOver("O wins");
                }
                return;
            }
        }
    }

    private boolean checkCombination(int a, int b, int c) {
        return buttons[a].getText().equals(buttons[b].getText()) && buttons[b].getText().equals(buttons[c].getText())
                && !buttons[a].getText().isEmpty();
    }

    private void gameOver(String message) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            buttons[i].setEnabled(false);
        }
        textField.setText(message);
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
