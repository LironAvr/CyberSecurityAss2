import java.util.LinkedList;

/**
 * Created by liron on 29/04/2017.
 */
public class Main {

    private static String plainText = "";
    private static String key = "C:\\Users\\liron\\Desktop\\CyberSecurityAss2\\files\\ascii\\PartA\\key_example.txt";
    private static String IV = "C:\\Users\\liron\\Desktop\\CyberSecurityAss2\\files\\ascii\\PartA\\IV_example.txt";
    private static String cipher = "";
    private static String output = "C:\\Users\\liron\\Desktop\\CyberSecurityAss2\\files\\ascii\\PartA\\ENCRYPT.txt";
    private static String knownPlain = "";
    private static String knownCipher = "";
    private static String input = "C:\\Users\\liron\\Desktop\\CyberSecurityAss2\\files\\ascii\\PartA\\plainMsg_example.txt";
    private static String temp = "C:\\Users\\liron\\Desktop\\CyberSecurityAss2\\files\\ascii\\PartA\\DECRYPT.txt";

    public static void main(String[] args) throws Exception {
        //Utils.write(EncryptDecrypt.encrypt(plainText, IV, key), output);
        //Utils.write(EncryptDecrypt.decrypt(cipher, IV, key), output);
        Long start = System.nanoTime();
        //CipherTextAttack.hack(cipher, IV, output);
        Utils.write(EncryptDecrypt.encrypt(input, IV, key), output);
        Utils.write(EncryptDecrypt.decrypt(output, IV, key), temp);
        //KnownPlaintextAttack.hack(cipher, IV, knownPlain, knownCipher, output);
        Long end = System.nanoTime();
        System.out.println((end - start) / 600000000L);
        /*
        String key            = "";
        String IV             = "";
        String output         = "";
        String knownPlainText = "";
        String knownCipher    = "";
        String input          = "";
        String algorithm      = "";
        String command        = "";
        try {
            algorithm = args[1];
            command = args[3];
            input = args[5];
            switch (args[6]) {
                case "-kp":
                    knownPlainText = args[7];
                    break;
                case "-k":
                    key = args[7];
                    break;
                case "-v":
                    IV = args[7];
                    break;
            }
            switch (args[8]) {
                case "-kc":
                    knownCipher = args[9];
                    break;
                case "-v":
                    IV = args[9];
                    break;
                case "-o":
                    output = args[9];
                    break;
            }
            if (args[8] != "-o") {
                switch (args[10]) {
                    case "-v":
                        IV = args[11];
                        break;
                    case "-o":
                        output = args[11];
                        break;
                }
            }
            if ("sub_cbc_52" == algorithm & "attack" == command) {
                output = args[13];
            }
        } catch (Exception ex) {
            System.out.println("Invalid Arguments, Please try again.");
        }

        try {
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
            System.out.println("Something happend, please try again\nError: " + ex.toString());
        }*/
    }
}
