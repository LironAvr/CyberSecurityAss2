import java.util.LinkedList;

/**
 * Created by liron on 29/04/2017.
 */
public class Main {

    private static String key;
    private static String IV;
    private static String output;
    private static String knownPlainText;
    private static String knownCipher;
    private static String input;
    private static String algorithm;
    private static String command;

    public static void main(String[] args) throws Exception {
        try {
            parametersHandler(args);
            switch (algorithm) {
                case "sub_cbc_10":
                    switch (command) {
                        case "encryption":
                            LinkedList<byte[]> cipherText = EncryptDecrypt.encrypt(input, IV, key);
                            Utils.write(cipherText, output);
                            break;
                        case "decryption":
                            LinkedList<byte[]> plainText = EncryptDecrypt.encrypt(input, IV, key);
                            Utils.write(plainText, output);
                            break;
                        case "attack":
                            CipherTextAttack.hack(input, IV, output);
                            break;
                    }
                    break;
                case "sub_cbc_52":
                    switch (command) {
                        case "encryption":
                            LinkedList<byte[]> cipherText = EncryptDecrypt.encrypt(input, IV, key);
                            Utils.write(cipherText, output);
                            break;
                        case "decryption":
                            LinkedList<byte[]> plainText = EncryptDecrypt.decrypt(input, IV, key);
                            Utils.write(plainText, output);
                            break;
                        case "attack":
                            KnownPlaintextAttack.hack(input, IV, knownPlainText, knownCipher, output);
                            break;
                    }
                    break;
            }
        } catch (Exception ex) {
            System.out.println("Something happend, please try again (Error Details: " + ex.toString() + ")");
        }
    }

    public static void parametersHandler(String[] args){
        for (int i = 0; i < args.length; i = i + 2){
            if      (args[i].equals("-a")) { algorithm = args[i + 1]; }
            else if (args[i].equals("-c")) { command = args[i + 1]; }
            else if (args[i].equals("-t")) { input = args[i + 1]; }
            else if (args[i].equals("-k")) { key = args[i + 1]; }
            else if (args[i].equals("-v")) { IV = args[i + 1]; }
            else if (args[i].equals("-o")) { output = args[i + 1]; }
            else if (args[i].equals("-kp")){ knownPlainText = args[i + 1]; }
            else if (args[i].equals("-kc")){ knownCipher = args[i + 1]; }
        }
    }
}
