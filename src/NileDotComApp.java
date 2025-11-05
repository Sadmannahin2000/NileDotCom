import javax.swing.*;
import java.io.IOException;
import model.InventoryManager;
import view.MainGUI;

/**
 * NileDotComApp.java
 * Launches the application
 */
public class NileDotComApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                InventoryManager invMgr = new InventoryManager("inventory.csv");
                new MainGUI(invMgr);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error loading inventory: " + e.getMessage(),
                        "File Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
