import java.io.File;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by liron on 27/04/2017.
 */
public class CipherTextAttack {

    public static Set<List<Byte>> dictionary;

    public static void hack(String cipherPath, String ivPath, String outputPath) {
        try {
            byte[] vector = Files.readAllBytes(new File(ivPath).toPath());
            byte[] cipher = Files.readAllBytes(new File(cipherPath).toPath());
            LinkedList<byte[]> blocks = Utils.createFirstBlocks(cipher, vector.length);
            dictionary = Utils.getDictionary();

            HashMap<Byte, Byte> key = findKey(blocks, vector);
            Utils.writeKey(key, outputPath);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private static HashMap<Byte, Byte> findKey (LinkedList <byte[]> blocks, byte[] vector){
        int maxMatch = 0;
        HashMap<Byte, Byte> key = new HashMap<>();
        Set<HashMap<Byte, Byte>> allKeyOptions = Utils.getAllKeys("abcdefgh");

        for (HashMap<Byte, Byte> currentKey: allKeyOptions){
            List<byte[]> plainText = EncryptDecrypt.decrypt(blocks, vector, currentKey);
            int matches = countKeyMatches(plainText, vector.length);
            if (matches > maxMatch){
                maxMatch = matches;
                key = currentKey;
            }
        }
        return key;
    }

    private static int countKeyMatches(List<byte[]> plainText, int size) {
        int matches = 0;
        byte[] bytes = new byte[5000];
        byte[] current;

        for (int i = 0; i < 500; ++i){
            try{
                current = plainText.get(i);
            }catch (Exception ex) { break; }
            int length = current.length;
            for (int j = 0; j < length; ++j){
                bytes[i * size + j] = current[j];
                if (current[j] > 64 & current[j] < 91){
                    bytes[i * size + j] += 32;
                }
            }
        }

        for (int i = 0; i < 5000; ++i){
            List<Byte> word = new LinkedList<>();
            int length = bytes.length;
            while (i < length && (bytes[i] != 10 | bytes[i] != 32)){
                if (bytes[i] > 64){
                    word.add(bytes[i]);
                    ++i;
                }
                else{
                    ++i;
                    break;
                }
            }
            //String temp = word.toString();
            if (dictionary.contains(word)){
                ++matches;
            }
            word.clear();
        }
        return matches;
    }
}