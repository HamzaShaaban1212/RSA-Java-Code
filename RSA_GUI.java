import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.math.BigInteger;

public class RSA_GUI extends JFrame {

    private JTextField plainTextField;
    private JTextArea outputTextArea;

    public RSA_GUI() {
        setTitle("RSA Encryption");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel instructionLabel = new JLabel("Choose 1 to enter prime numbers manually, or any other number to generate automatically:");
        mainPanel.add(instructionLabel);

        JTextField choiceField = new JTextField();
        mainPanel.add(choiceField);

        JButton processButton = new JButton("Process");
        processButton.setBackground(Color.decode("#4CAF50"));
        processButton.setForeground(Color.WHITE);
        mainPanel.add(processButton);

        plainTextField = new JTextField();
        mainPanel.add(plainTextField);

        outputTextArea = new JTextArea(10, 30);
        outputTextArea.setEditable(false);
        outputTextArea.setBackground(Color.decode("#F0F0F0"));
        mainPanel.add(outputTextArea);

        JScrollPane scrollPane = new JScrollPane(outputTextArea);
        mainPanel.add(scrollPane);

        add(mainPanel);

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userChoice = Integer.parseInt(choiceField.getText());

                int firstPrime, secondPrime;
                if (userChoice == 1) {
                    firstPrime = Integer.parseInt(JOptionPane.showInputDialog("Enter the first prime number (p):"));
                    secondPrime = Integer.parseInt(JOptionPane.showInputDialog("Enter the second prime number (q):"));
                } else {
                    Random rand = new Random();
                    firstPrime = generatePrime(rand, 100, 200);
                    secondPrime = generatePrime(rand, 200, 300);
                }

                int modulus = firstPrime * secondPrime;
                int phi = (firstPrime - 1) * (secondPrime - 1);
                int publicKey = findPublicKey(phi);
                int[] privateKey = findPrivateKey(publicKey, phi);
                int privateExponent = privateKey[0];
                int decryptionKey = privateKey[1];

                String plainText = plainTextField.getText();

                StringBuilder cipherText = new StringBuilder();
                for (int i = 0; i < plainText.length(); i++) {
                    char character = plainText.charAt(i);
                    int asciiValue = (int) character;
                    int encryptedChar = encrypt(asciiValue, publicKey, modulus);
                    cipherText.append(encryptedChar).append(" ");
                }

                outputTextArea.setText("First prime (p) = " + firstPrime + "\n");
                outputTextArea.append("Second prime (q) = " + secondPrime + "\n");
                outputTextArea.append("Modulus (n) = " + modulus + "\n");
                outputTextArea.append("Public key (e) = " + publicKey + "\n");
                outputTextArea.append("Private exponent (k) = " + privateExponent + "\n");
                outputTextArea.append("Decryption key (d) = " + decryptionKey + "\n");
                outputTextArea.append("Cipher text = " + cipherText.toString() + "\n");

                StringBuilder decryptedText = new StringBuilder();
                String[] encryptedValues = cipherText.toString().split(" ");
                for (String encryptedValue : encryptedValues) {
                    if (!encryptedValue.isEmpty()) {
                        int encryptedChar = Integer.parseInt(encryptedValue.trim());
                        int decryptedChar = decrypt(encryptedChar, decryptionKey, modulus);
                        decryptedText.append((char) decryptedChar);
                    }
                }
                outputTextArea.append("Decrypted text = " + decryptedText.toString() + "\n");
            }
        });
    }

    public static boolean isPrime(int number) {
        return BigInteger.valueOf(number).isProbablePrime(100);
    }

    public static int generatePrime(Random rand, int min, int max) {
        int num;
        do {
            num = rand.nextInt((max - min) + 1) + min;
        } while (!isPrime(num));
        return num;
    }

    public static int findPublicKey(int phi) {
        Random rand = new Random();
        int publicKey;
        do {
            publicKey = rand.nextInt(phi - 2) + 2;
        } while (BigInteger.valueOf(publicKey).gcd(BigInteger.valueOf(phi)).intValue() != 1);
        return publicKey;
    }

    public static int[] findPrivateKey(int publicKey, int phi) {
        int k = 1;
        int privateExponent;
        while (true) {
            privateExponent = (1 + (k * phi)) / publicKey;
            if ((publicKey * privateExponent) % phi == 1) {
                break;
            }
            k++;
        }
        int[] privateKey = {k, privateExponent};
        return privateKey;
    }

    public static int encrypt(int plaintext, int publicKey, int modulus) {
        return modularExponentiation(plaintext, publicKey, modulus);
    }

    public static int decrypt(int ciphertext, int privateExponent, int modulus) {
        return modularExponentiation(ciphertext, privateExponent, modulus);
    }

    public static int modularExponentiation(int base, int exponent, int modulus) {
        int result = 1;
        base = base % modulus;

        while (exponent > 0) {
            if (exponent % 2 == 1)
                result = (result * base) % modulus;

            exponent = exponent >> 1;
            base = (base * base) % modulus;
        }
        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RSA_GUI().setVisible(true);
            }
        });
    }
}