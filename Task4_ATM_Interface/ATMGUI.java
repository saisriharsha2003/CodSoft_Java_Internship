import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMGUI extends JFrame {
    private BankAccount userAccount;
    private JLabel balanceLabel;
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton checkBalanceButton;
    private JButton exitButton;

    public ATMGUI(BankAccount account) {
        userAccount = account;

        setTitle("ATM GUI");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Balance Label
        balanceLabel = new JLabel("Current Balance: " + userAccount.getBalance());
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setHorizontalAlignment(JLabel.CENTER);
        balanceLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(balanceLabel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        add(buttonPanel, BorderLayout.CENTER);

        // Withdraw Button
        withdrawButton = new JButton("Withdraw");
        customizeButton(withdrawButton, Color.BLUE);
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(ATMGUI.this, "Enter the amount to withdraw:");
                try {
                    double withdrawAmount = Double.parseDouble(input);
                    boolean withdrawalSuccess = userAccount.withdraw(withdrawAmount);
                    if (withdrawalSuccess) {
                        JOptionPane.showMessageDialog(ATMGUI.this, "Withdrawal successful. Current balance: " + userAccount.getBalance());
                        balanceLabel.setText("Current Balance: " + userAccount.getBalance());
                    } else {
                        JOptionPane.showMessageDialog(ATMGUI.this, "Insufficient balance. Cannot withdraw.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ATMGUI.this, "Invalid amount. Please enter a valid number.");
                }
            }
        });

        // Deposit Button
        depositButton = new JButton("Deposit");
        customizeButton(depositButton, Color.GREEN);
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(ATMGUI.this, "Enter the amount to deposit:");
                try {
                    double depositAmount = Double.parseDouble(input);
                    userAccount.deposit(depositAmount);
                    JOptionPane.showMessageDialog(ATMGUI.this, "Deposit successful. Current balance: " + userAccount.getBalance());
                    balanceLabel.setText("Current Balance: " + userAccount.getBalance());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ATMGUI.this, "Invalid amount. Please enter a valid number.");
                }
            }
        });

        // Check Balance Button
        checkBalanceButton = new JButton("Check Balance");
        customizeButton(checkBalanceButton, Color.YELLOW);
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ATMGUI.this, "Current balance: " + userAccount.getBalance());
            }
        });

        // Exit Button
        exitButton = new JButton("Exit");
        customizeButton(exitButton, Color.RED);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Adding buttons to the panel
        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(checkBalanceButton);
        buttonPanel.add(exitButton);
    }

    private void customizeButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedSoftBevelBorder(),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
    }

    public static void main(String[] args) {
        // Initialize the user's bank account with an initial balance of 1000
        BankAccount userAccount = new BankAccount(1000);
        SwingUtilities.invokeLater(() -> new ATMGUI(userAccount).setVisible(true));
    }
}
