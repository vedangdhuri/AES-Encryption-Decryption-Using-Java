import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class EDcrypt extends JFrame {

    // Class variables for encryption
    private static SecretKeySpec secretKey; // Stores the secret key for encryption/decryption
    private static byte[] key; // Stores the raw key bytes
    // private static Scanner scanner = new Scanner(System.in); // Scanner for user input

    /*
     * Generates a secret key from the provided string using SHA-1 and AES
     * 
     * @param myKey The string to generate the key from
     */
    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8"); // Convert the key string to bytes
            sha = MessageDigest.getInstance("SHA-1"); // Create SHA-1 digest instance
            key = sha.digest(key); // Generate hash of the key
            key = Arrays.copyOf(key, 16); // Use first 16 bytes for AES key
            secretKey = new SecretKeySpec(key, "AES");// Create AES secret key specification
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /*
     * Encrypts a string using AES encryption
     * 
     * @param strToEncrypt String to encrypt
     * 
     * @param secret Secret key for encryption
     * 
     * @return Base64 encoded encrypted string
     */
    private static String encrypt(String strToEncrypt, String secret) {
        try {
            setKey(secret); // Generate key from secret
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // Create AES(Advance Encryption string) cipher
            cipher.init(Cipher.ENCRYPT_MODE, secretKey); // Initialize for encryption
            return Base64.getEncoder().encodeToString( // Encode encrypted bytes
                    cipher.doFinal(strToEncrypt.getBytes("UTF-8"))); // Encrypt the string
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
            return null;
        }
    }

    /*
     * Decrypts a string using AES decryption
     * 
     * @param strToDecrypt Base64 encoded string to decrypt
     * 
     * @param secret Secret key for decryption
     * 
     * @return Decrypted string
     */
    private static String decrypt(String strToDecrypt, String secret) {
        try {
            setKey(secret); // Generate key from secret
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING"); // Create AES cipher
            cipher.init(Cipher.DECRYPT_MODE, secretKey); // Initialize for decryption
            return new String(cipher.doFinal( // Convert decrypted bytes to string
                    Base64.getDecoder().decode(strToDecrypt))); // Decode and decrypt
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
            return null;
        }
    }

    // --- Swing UI components ---
    private JTextArea inputArea;
    private JTextField keyField;
    private JTextArea outputArea;
    private JButton encryptButton;
    private JButton decryptButton;
    private JButton copyencrypt;
    private JButton copydecrypt;

    public EDcrypt() {
        // Colors and fonts for dark blue theme
        Color background = new Color(10, 25, 47); // deep navy
        Color panelBlue = new Color(17, 34, 64); // slightly lighter
        Color accent = new Color(33, 150, 243); // blue accent
        Color text = new Color(220, 230, 240); // soft white
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 18);

        setTitle("AES Encrypt / Decrypt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));
        getContentPane().setBackground(background);

        // Header
        JLabel title = new JLabel("AES Encryption & Decryption", JLabel.CENTER);
        title.setForeground(text);
        title.setFont(titleFont);
        title.setBorder(BorderFactory.createEmptyBorder(16, 16, 8, 16));
        add(title, BorderLayout.NORTH);

        // Center panel
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(background);
        center.setBorder(BorderFactory.createEmptyBorder(8, 16, 16, 16));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        // Input label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0;
        JLabel inputLabel = new JLabel("Plaintext / Ciphertext:");
        inputLabel.setFont(labelFont);
        inputLabel.setForeground(text);
        center.add(inputLabel, gbc);

        // Input area
        gbc.gridy = 1;
        gbc.weighty = 0.5;
        inputArea = new JTextArea(6, 40);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setBackground(panelBlue);
        inputArea.setForeground(text);
        inputArea.setCaretColor(text);
        inputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        inputArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accent, 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        JScrollPane inputScroll = new JScrollPane(inputArea);
        inputScroll.setBorder(BorderFactory.createEmptyBorder());
        center.add(inputScroll, gbc);

        // Key label
        gbc.gridy = 2;
        gbc.weighty = 0;
        JLabel keyLabel = new JLabel("Key (passphrase):");
        keyLabel.setFont(labelFont);
        keyLabel.setForeground(text);
        center.add(keyLabel, gbc);

        // Key field
        gbc.gridy = 3;
        gbc.weighty = 0;
        keyField = new JTextField();
        keyField.setBackground(panelBlue);
        keyField.setForeground(text);
        keyField.setCaretColor(text);
        keyField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accent, 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        center.add(keyField, gbc);

        // Buttons panel
        gbc.gridy = 4;
        gbc.weighty = 0;
        JPanel buttons = new JPanel();
        buttons.setBackground(background);
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        copyencrypt = new JButton("Copy Encrypted");
        copydecrypt = new JButton("Copy Decrypted");
        styleButton(encryptButton, accent, text, panelBlue);
        styleButton(decryptButton, accent, text, panelBlue);
        styleButton(copyencrypt, accent, text, panelBlue);
        styleButton(copydecrypt, accent, text, panelBlue);
        buttons.add(encryptButton);
        buttons.add(decryptButton);
        buttons.add(copyencrypt);
        buttons.add(copydecrypt);
        center.add(buttons, gbc);

        // Output label
        gbc.gridy = 5;
        gbc.weighty = 0;
        JLabel outputLabel = new JLabel("Result:");
        outputLabel.setFont(labelFont);
        outputLabel.setForeground(text);
        center.add(outputLabel, gbc);

        // Output area
        gbc.gridy = 6;
        gbc.weighty = 0.5;
        outputArea = new JTextArea(6, 40);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBackground(panelBlue);
        outputArea.setForeground(text);
        outputArea.setCaretColor(text);
        outputArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        outputArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accent, 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setBorder(BorderFactory.createEmptyBorder());
        center.add(outputScroll, gbc);

        add(center, BorderLayout.CENTER);

        // Wire actions
        encryptButton.addActionListener(e -> doEncrypt());
        decryptButton.addActionListener(e -> doDecrypt());
        copyencrypt.addActionListener(e -> copyToClipboard(outputArea.getText()));
        copydecrypt.addActionListener(e -> copyToClipboard(outputArea.getText()));

        setPreferredSize(new Dimension(820, 640));
        pack();
        setLocationRelativeTo(null);
    }

    private void styleButton(JButton button, Color accent, Color text, Color hoverBg) {
        button.setBackground(accent);
        button.setForeground(text);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    private void doEncrypt() {
        String input = inputArea.getText();
        String key = keyField.getText();
        if (input == null || input.isEmpty() || key == null || key.isEmpty()) {
            outputArea.setText("Please enter both text and key.");
            return;
        }
        String result = encrypt(input, key);
        outputArea.setText(result != null ? result : "Encryption failed. Check input/key.");
    }

    private void doDecrypt() {
        String input = inputArea.getText();
        String key = keyField.getText();
        if (input == null || input.isEmpty() || key == null || key.isEmpty()) {
            outputArea.setText("Please enter both text and key.");
            return;
        }
        String result = decrypt(input, key);
        outputArea.setText(result != null ? result : "Decryption failed. Check input/key.");
    }

    private void copyToClipboard(String text) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(text == null ? "" : text), null);
    }

    /*
     * Main method - launches Swing UI
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EDcrypt().setVisible(true));
    }
}
