/**
 * Created by liron on 29/04/2017.
 */
public class Main {

    private static final String plainText = "C:\\Users\\liron\\Desktop\\CyberSecurityAss2\\files\\plainMsg_example.txt";
    private static final String key = "C:\\Users\\liron\\Desktop\\CyberSecurityAss2\\files\\key_example.txt";
    private static final String IV = "C:\\Users\\liron\\Desktop\\CyberSecurityAss2\\files\\IV_example.txt";
    private static final String cipher = "C:\\Users\\liron\\Desktop\\CyberSecurityAss2\\files\\cipherMsg_example.txt";
    private static final String output = "C:\\Users\\liron\\Desktop\\CyberSecurityAss2\\files\\outKey.txt";

    public static void main(String[] args) throws Exception{
        //Utils.write(EncryptDecrypt.encrypt(plainText, IV, key), output);
        //Utils.write(EncryptDecrypt.decrypt(cipher, IV, key), output);
        CipherTextAttack.hack(cipher, IV, output);
    }
}
