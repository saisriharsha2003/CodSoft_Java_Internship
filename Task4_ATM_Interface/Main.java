import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Initialize the user's bank account with an initial balance of 1000
        BankAccount userAccount = new BankAccount(1000);
        SwingUtilities.invokeLater(() -> new ATMGUI(userAccount).setVisible(true));
    }
}
