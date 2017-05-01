
/**
 * Created by liron on 29/04/2017.
 */
public class Main {

    private static final String plainText = "";
    private static final String key = "";
    private static final String IV = "C:\\Users\\liron\\Desktop\\CyberSecurityAss2\\PartB\\IV_short.txt";
    private static final String cipher = "C:\\Users\\liron\\Desktop\\CyberSecurityAss2\\PartB\\cipher.txt";
    private static final String output = "C:\\Users\\liron\\Desktop\\CyberSecurityAss2\\PartB\\outputKey.txt";

    public static void main(String[] args) throws Exception{
        //Utils.write(EncryptDecrypt.encrypt(plainText, IV, key), output);
        //Utils.write(EncryptDecrypt.decrypt(cipher, IV, key), output);
        Long start = System.nanoTime();
        CipherTextAttack.hack(cipher, IV, output);
        Long end = System.nanoTime();
        System.out.println((end - start) / 600000000L);
    }
}
