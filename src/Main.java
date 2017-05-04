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

    private static void parametersHandler(String[] args){
        for (int i = 0; i < args.length; i = i + 2){
            switch (args[i]) {
                case "-a":
                    algorithm = args[i + 1];
                    break;
                case "-c":
                    command = args[i + 1];
                    break;
                case "-t":
                    input = args[i + 1];
                    break;
                case "-k":
                    key = args[i + 1];
                    break;
                case "-v":
                    IV = args[i + 1];
                    break;
                case "-o":
                    output = args[i + 1];
                    break;
                case "-kp":
                    knownPlainText = args[i + 1];
                    break;
                case "-kc":
                    knownCipher = args[i + 1];
                    break;
            }
        }
    }
}
