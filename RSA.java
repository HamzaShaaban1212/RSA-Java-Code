import java.util.Scanner;
import java.util.Random;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Random;

public class RSA extends JFrame implements ActionListener {
    private JTextField pField;
    private JTextField qField;
    private JTextArea plainTextField;
    private JTextArea cipherArea,decrptArea;
    private JCheckBox choice;
    private JButton encryptButton;


    public RSA() {
        setTitle("RSA");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel pLabel = new JLabel("P value: ");
        pLabel.setBounds(20, 20, 150, 25);
        panel.add(pLabel);

          pField = new JTextField();
        pField.setBounds(80, 20, 150, 25);
        panel.add(pField);

        JLabel CipherLabel = new JLabel("Cipher Text");
        CipherLabel.setBounds(150, 130, 150, 25);
        panel.add(CipherLabel);

         cipherArea = new JTextArea();
        cipherArea.setBounds(20, 160, 350, 100);
        cipherArea.setLineWrap(true); 
        cipherArea.setWrapStyleWord(true); 
        cipherArea.setEditable(false); 
        panel.add(cipherArea);


        JLabel decryptLabel = new JLabel("Decrypted Text");
        decryptLabel.setBounds(1690, 130, 150, 25);
        panel.add(decryptLabel);

         decrptArea = new JTextArea();
        decrptArea.setBounds(1560, 160, 350, 100);
        decrptArea.setLineWrap(true); 
        decrptArea.setWrapStyleWord(true); 
        decrptArea.setEditable(false); 
        panel.add(decrptArea);



        JLabel qLabel = new JLabel("q value:");
        qLabel.setBounds(1690, 20, 150, 25);
        panel.add(qLabel);

         qField = new JTextField();
        qField.setBounds(1750, 20, 150, 25);
        panel.add(qField);

        JLabel plainTextLabel = new JLabel("Enter the plain text:");
        plainTextLabel.setBounds(885, 20, 150, 25);
        panel.add(plainTextLabel);

         plainTextField = new JTextArea();
        plainTextField.setBounds(800, 50, 350, 100);
        plainTextField.setLineWrap(true); 
        plainTextField.setWrapStyleWord(true);
        panel.add(plainTextField);

         choice = new JCheckBox("Generate random");
        choice.setBounds(885, 150, 150, 25);
        panel.add(choice);
        choice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean selected = choice.isSelected();
                qField.setOpaque(!selected);
                pField.setOpaque(!selected);
                qField.setEditable(!selected);
                pField.setEditable(!selected);
                panel.repaint();
            }
        });
       
         encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(885, 200, 100, 25);
        encryptButton.addActionListener(this);
        panel.add(encryptButton);
       

        add(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        String ptext =pField.getText();
        String qtext = qField.getText();
        if(!choice.isSelected() && e.getSource()==encryptButton){
        if(!(ptext.matches("\\d+") && qtext.matches("\\d+"))){
            JOptionPane.showMessageDialog(encryptButton, "Enter only Numbers");
            return;
        }
        else if(!(isPrime(Integer.parseInt(qtext))&&isPrime(Integer.parseInt(ptext)))){
            JOptionPane.showMessageDialog(encryptButton, "Numbers are not prime");
            return;
        }
        int p = Integer.parseInt(ptext);
        int q = Integer.parseInt(qtext);;
        
        int n = p * q;
        int phi_N = (p - 1) * (q - 1);
        int eRSA = GET_e(phi_N);
        int[] K_D = DK_val(eRSA, phi_N);
        int k = K_D[0];
        int d = K_D[1];
        String plainText = plainTextField.getText();
        StringBuilder cipherText = new StringBuilder();
     for (int i = 0; i < plainText.length(); i++) {
    char character = plainText.charAt(i);
    int asciiValue = (int) character;
    int encryptedChar = encrypt(asciiValue, eRSA, n);
    cipherText.append(encryptedChar).append(" ");
    }
    cipherArea.setText(convertToLetters(cipherText.toString()));
    StringBuilder decryptedText = new StringBuilder();
        String[] encryptedValues = cipherText.toString().split(" ");
        for (String encryptedValue : encryptedValues) {
            if (!encryptedValue.isEmpty()) {
                int encryptedChar = Integer.parseInt(encryptedValue.trim());
                int decryptedChar = decrypt(encryptedChar, d, n);
                decryptedText.append((char) decryptedChar);
            }
        }
    decrptArea.setText(decryptedText.toString());
    return;
}
if(choice.isSelected() && e.getSource()==encryptButton){
    Random rand = new Random();
    int p = Gen_primes(rand, 100, 200);
    int q = Gen_primes(rand, 200, 300);
    String p1 = String.valueOf(p);
    String q1 = String.valueOf(q);
    pField.setText(p1);
    qField.setText(q1);
    int n = p * q;
         int phi_N = (p - 1) * (q - 1);
         int eRSA = GET_e(phi_N);
         int[] K_D = DK_val(eRSA, phi_N);
         int k = K_D[0];
         int d = K_D[1];
         String plainText = plainTextField.getText();
         StringBuilder cipherText = new StringBuilder();
      for (int i = 0; i < plainText.length(); i++) {
     char character = plainText.charAt(i);
     int asciiValue = (int) character;
     int encryptedChar = encrypt(asciiValue, eRSA, n);
     cipherText.append(encryptedChar).append(" ");
     }
     cipherArea.setText(convertToLetters(cipherText.toString()));
     StringBuilder decryptedText = new StringBuilder();
        String[] encryptedValues = cipherText.toString().split(" ");
        for (String encryptedValue : encryptedValues) {
            if (!encryptedValue.isEmpty()) {
                int encryptedChar = Integer.parseInt(encryptedValue.trim());
                int decryptedChar = decrypt(encryptedChar, d, n);
                decryptedText.append((char) decryptedChar);
            }
        }
     decrptArea.setText(decryptedText.toString());
     return;
}
}


    public static void main(String[] args) {
    /*     Scanner scanner = new Scanner(System.in);

        System.out.println("Choose 1 to enter two prime numbers or 2 to generate automatically: ");
        int choice = scanner.nextInt();

        int p, q;
        if (choice == 1) {
            System.out.print("Enter the first prime number (p): ");
            p = scanner.nextInt();
            while (!isPrime(p)) {
                System.out.print(p + " is not prime. Please enter p again: ");
                p = scanner.nextInt();
            }

            System.out.print("Enter the second prime number (q): ");
            q = scanner.nextInt();
            while (!isPrime(q)) {
                System.out.print(q + " is not prime. Please enter q again: ");
                q = scanner.nextInt();
            }
        } else {
            Random rand = new Random();
            p = Gen_primes(rand, 100, 1000);
            q = Gen_primes(rand, 200, 3000);
        }

        System.out.println("p = " + p + ", q = " + q);
        int n = p * q;
        System.out.println("n = " + n);

        int phi_N = (p - 1) * (q - 1);
        System.out.println("phi(n) = " + phi_N);

        int e = GET_e(phi_N);
        System.out.println("e = " + e);

        int[] K_D = DK_val(e, phi_N);
        int k = K_D[0];
        int d = K_D[1];
        System.out.println("d = " + d);

        scanner.nextLine();
        System.out.print("Please enter the plain text: ");
        String plainText = scanner.nextLine();
        */
        new RSA();
      /*   StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            char character = plainText.charAt(i);
            int asciiValue = (int) character;
            int encryptedChar = encrypt(asciiValue, e, n);
            cipherText.append(encryptedChar).append(" ");
        }

        System.out.println("The cipher text = " + convertToLetters(cipherText.toString()));


        StringBuilder decryptedText = new StringBuilder();
        String[] encryptedValues = cipherText.toString().split(" ");
        for (String encryptedValue : encryptedValues) {
            if (!encryptedValue.isEmpty()) {
                int encryptedChar = Integer.parseInt(encryptedValue.trim());
                int decryptedChar = decrypt(encryptedChar, d, n);
                decryptedText.append((char) decryptedChar);
            }
        }

        System.out.println("The plain text = " + decryptedText);
        scanner.close();
        */
    }

    
 public static boolean isPrime(int number) {
    if (number <= 1) return false;
    for (int i = 2; i <= Math.sqrt(number); i++) {
        if (number % i == 0) return false;
    }
    return true;
}

    public static int Gen_primes(Random rand, int min, int max) {
        int num;
        do {
            num = rand.nextInt((max - min) + 1) + min;
        } while (!isPrime(num));
        return num;
    }

    public static int GET_e(int phi_N) {
        Random rand = new Random();
        int e;
        do {
            e = rand.nextInt(phi_N - 2) + 2;
        } while (gcd(e, phi_N) != 1);
        return e;
    }

    public static int[] DK_val(int e, int phi_N) {
        int k = 1;
        int d;
        while (true) {
            d = (1 + (k * phi_N)) / e;
            if (e * d % phi_N == 1) {
                break;
            }
            k++;
        }
        int[] K_D = {k, d};
        return K_D;
    }

    public static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public static int encrypt(int plaintext, int e, int n) {
        return powAB_ModC(plaintext, e, n);
    }

    public static int decrypt(int ciphertext, int d, int n) {
        return powAB_ModC(ciphertext, d, n);
    }

    public static int powAB_ModC(int x, int y, int p) {
        int Reslt = 1;
        x = x % p;

        while (y > 0) {
            if (y % 2 == 1)
                Reslt = (Reslt * x) % p;

            y = y >> 1;
            x = (x * x) % p;
        }
        return Reslt;
    }

    static final String CHAR_MAP = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";

    public static String convertToLetters(String cipherText) {
        StringBuilder plainText = new StringBuilder();
        String[] cipherBlocks = cipherText.split(" ");

        for (String block : cipherBlocks) {
            int num = Integer.parseInt(block);
            char character = mapNumberToChar(num);
            plainText.append(character);
        }

        return plainText.toString();
    }

    // Map number to character using CHAR_MAP
    static char mapNumberToChar(int num) {
        return CHAR_MAP.charAt(num % CHAR_MAP.length());
    }
}
