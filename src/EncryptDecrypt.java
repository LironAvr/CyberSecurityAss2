import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.File;

/**
 * Created by liron on 26/04/2017.
 */

public class EncryptDecrypt {

    public static LinkedList<byte[]> encrypt (String textPath, String ivPath, String keyPath) throws IOException {
        LinkedList<byte[]> cipher = new LinkedList<>();
        try{
            byte[] vector = Files.readAllBytes(new File(ivPath).toPath());
            byte[] text = Files.readAllBytes(new File(textPath).toPath());
            byte[] cipherBlock;
            LinkedList<byte[]> blocks = Utils.createBlocks(text, vector.length, true);
            HashMap<Byte, Byte> key = Utils.getKey(keyPath, 'e');

            for (byte[] block: blocks){
                vector = Utils.xor(block, vector);
                cipherBlock = encryptBlock(vector, key);
                cipher.add(cipherBlock);
                vector = cipherBlock;
            }

        }catch (Exception ex) {
            System.out.println(ex.toString());
            throw ex;
        }
        return cipher;
    }

    private static byte[] encryptBlock(byte[] vector, HashMap<Byte, Byte> key) {
        int length = vector.length;
        for (int i = 0; i < length; ++i){
            if (key.containsKey(vector[i])){
                vector[i] = key.get(vector[i]);
            }
        }
        return vector;
    }

    public static LinkedList<byte[]> decrypt(String cipherPath, String ivPath, String keyPath){
        LinkedList<byte[]> plainText = new LinkedList<>();
        try {
            byte[] vector = Files.readAllBytes(new File(ivPath).toPath());
            byte[] cipher = Files.readAllBytes(new File(cipherPath).toPath());
            LinkedList<byte[]> blocks = Utils.createBlocks(cipher, vector.length, true);
            HashMap<Byte, Byte> key = Utils.getKey(keyPath, 'd');
            plainText = decrypt(blocks, vector, key);
        }
        catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return plainText;
    }

    private static byte[] decryptBlock(byte[] cipher, HashMap<Byte, Byte> key) {
        byte[] result = cipher.clone();
        int length = result.length;
        for (int i = 0; i < length; ++i){
            if (key.containsKey(result[i])){
                result[i] = key.get(result[i]);
            }
        }
        return result;
    }

    public static LinkedList<byte[]> decrypt(LinkedList<byte[]> blocks, byte[] vector, HashMap<Byte, Byte> key) {
        byte[] plainTextBlock;
        LinkedList<byte[]> plainText = new LinkedList<>();

        for (byte[] block: blocks){
            plainTextBlock = decryptBlock(block, key);
            plainTextBlock = Utils.xor(plainTextBlock, vector);
            plainText.add(plainTextBlock);
            vector = block.clone();
        }
        return plainText;
    }
}
