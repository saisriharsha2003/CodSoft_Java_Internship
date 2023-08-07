import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
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
        getContentPane().setBackground(Color.WHITE);

        // Balance Label
        balanceLabel = new JLabel("Current Balance: " + userAccount.getBalance());
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        balanceLabel.setHorizontalAlignment(JLabel.CENTER);
        balanceLabel.setForeground(Color.BLACK);
        balanceLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(balanceLabel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        add(buttonPanel, BorderLayout.CENTER);

        // Button style
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);
        int arcSize = 15;

        // Withdraw Button
        withdrawButton = new JButton("Withdraw");
        customizeButton(withdrawButton, Color.BLUE, buttonFont, arcSize);
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(ATMGUI.this, "Enter withdrawal amount:");
                try {
                    double amount = Double.parseDouble(input);
                    boolean withdrawalSuccess = userAccount.withdraw(amount);
                    if (withdrawalSuccess) {
                        updateBalanceLabel();
                        JOptionPane.showMessageDialog(ATMGUI.this, "Withdrawal successful. Current balance: " + userAccount.getBalance());
                    } else {
                        JOptionPane.showMessageDialog(ATMGUI.this, "Insufficient balance. Cannot withdraw.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ATMGUI.this, "Invalid amount entered.");
                }
            }
        });

        // Deposit Button
        depositButton = new JButton("Deposit");
        customizeButton(depositButton, Color.GREEN, buttonFont, arcSize);
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(ATMGUI.this, "Enter deposit amount:");
                try {
                    double amount = Double.parseDouble(input);
                    userAccount.deposit(amount);
                    updateBalanceLabel();
                    JOptionPane.showMessageDialog(ATMGUI.this, "Deposit successful. Current balance: " + userAccount.getBalance());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ATMGUI.this, "Invalid amount entered.");
                }
            }
        });

        // Check Balance Button
        checkBalanceButton = new JButton("Check Balance");
        customizeButton(checkBalanceButton, Color.YELLOW, buttonFont, arcSize);
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ATMGUI.this, "Current balance: " + userAccount.getBalance());
            }
        });

        // Exit Button
        exitButton = new JButton("Exit");
        customizeButton(exitButton, Color.RED, buttonFont, arcSize);
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

        // Add component listener to handle frame size changes
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int frameWidth = getWidth();
                int frameHeight = getHeight();

                // Update button sizes based on frame size
                int buttonSize = Math.min(frameWidth / 6, frameHeight / 6);
                Dimension buttonDimension = new Dimension(buttonSize, buttonSize);
                withdrawButton.setPreferredSize(buttonDimension);
                depositButton.setPreferredSize(buttonDimension);
                checkBalanceButton.setPreferredSize(buttonDimension);
                exitButton.setPreferredSize(buttonDimension);

                // Update font size for the balance label
                int balanceFontSize = Math.min(frameWidth / 30, frameHeight / 30);
                balanceLabel.setFont(new Font("Arial", Font.BOLD, balanceFontSize));
            }
        });
    }

    private void customizeButton(JButton button, Color color, Font font, int arcSize) {
        button.setFont(font);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton btn = (AbstractButton) c;
                ButtonModel model = btn.getModel();
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (model.isPressed()) {
                    g2d.setColor(color.darker());
                } else if (model.isRollover()) {
                    g2d.setColor(color.brighter());
                } else {
                    g2d.setColor(color);
                }
                g2d.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), arcSize, arcSize);
                super.paint(g2d, c);
            }
        });
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setBorderPainted(false);
        button.setBackground(color);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.setBorder(BorderFactory.createEmptyBorder());

        // Create custom rounded border
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setBorder(BorderFactory.createEmptyBorder());
            }
        });
        button.addPropertyChangeListener("border", evt -> {
            if (button.isFocusOwner()) {
                button.setBorder(BorderFactory.createEmptyBorder());
            }
        });

        button.setPreferredSize(new Dimension(150, 40));
        button.setMaximumSize(new Dimension(150, 40));

        // Animation on hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                animateBackgroundColor(button, button.getBackground(), button.getBackground().brighter(), 200);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                animateBackgroundColor(button, button.getBackground(), button.getBackground().darker(), 200);
            }
        });
    }

    public void animateBackgroundColor(Component component, Color fromColor, Color toColor, int duration) {
        int steps = 50;
        int delay = duration / steps;
        float[] fromRGBA = fromColor.getRGBComponents(null);
        float[] toRGBA = toColor.getRGBComponents(null);

        Timer timer = new Timer(delay, null);
        timer.addActionListener(new ActionListener() {
            private int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (step >= steps) {
                    ((Timer) e.getSource()).stop();
                } else {
                    float[] currentRGBA = new float[4];
                    for (int i = 0; i < 4; i++) {
                        currentRGBA[i] = fromRGBA[i] + (toRGBA[i] - fromRGBA[i]) * (float) step / steps;
                    }
                    component.setBackground(new Color(currentRGBA[0], currentRGBA[1], currentRGBA[2], currentRGBA[3]));
                    step++;
                }
            }
        });

        timer.start();
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Current Balance: " + userAccount.getBalance());
    }

    public static void main(String[] args) {
        // Initialize the user's bank account with an initial balance of 1000
        BankAccount userAccount = new BankAccount(1000);
        SwingUtilities.invokeLater(() -> new ATMGUI(userAccount).setVisible(true));
    }
}
