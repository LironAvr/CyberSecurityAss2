import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by liron on 01/05/2017.
 */

public class KnownPlaintextAttack {

    private static HashMap<Byte, Byte> key = new HashMap<>();
    private static HashMap<Byte, HashMap<Byte, Integer>> letters = new HashMap<>();
    private static HashSet<Byte> ignoreChars = new HashSet<>();
    private static Set<List<Byte>> dictionary;
    private static Set<Byte> optionalValues;
    private static HashSet<Byte> optionalKeys;
    private static LinkedList<byte[]> blocks;
    private static byte[] vector;
    private static int size;
    private static boolean iteration = false;

    public static void hack(String cipherPath, String ivPath, String knownPlainPath, String knownCipherPath, String outputPath) throws IOException{
        init(cipherPath, ivPath);
        resolveKnownText(knownPlainPath, knownCipherPath);
        if (key.size() < 52) findKey();
        Utils.writeKey(key, outputPath);
    }

    private static void findKey() {
        byte[] IV = vector.clone();
        for (byte[] block: blocks){
            keyOptimizer(block, vector);
            vector = block;
        }
        if (key.size() < 52){
            iteration = true;
            vector = IV;
            findKey();
        }
    }

    private static void keyOptimizer(byte[] block, byte[] vector) {
        int wordIndex = 0, blockIndex = 0, unknownLetters = 0;
        byte currentLetter, unknownLetter = 0;
        LinkedList<Byte> currentWord = new LinkedList<>();
        for (int i = 0; i < size; ++i){
            currentLetter = block[i];
            if (key.containsKey(currentLetter)){
                currentLetter = key.get(currentLetter);
            }
            else if (optionalKeys.contains(currentLetter)){
                blockIndex = i;
                wordIndex = currentWord.size();
                unknownLetter = currentLetter;
                unknownLetters++;
            }

            currentLetter = (byte) (currentLetter ^ vector[i]);
            if (! ignoreChars.contains(currentLetter)){
                currentWord.add(currentLetter);
            }
            else{
                if (wordIndex == currentWord.size()){
                    currentWord.add(currentLetter);
                }
                if (1 == unknownLetters & currentWord.size() > 1){
                    varifyWithDictionary(vector[blockIndex] ,wordIndex, unknownLetter, currentWord);
                }
                unknownLetter = 0;
                unknownLetters = 0;
                wordIndex = 0;
                blockIndex = 0;
                currentWord.clear();
            }
        }
    }

    private static void varifyWithDictionary(byte vectorByte, int wordIndex, byte unknownLetter, List<Byte> word){
        int matches = 0;
        byte optionalValue = 0, xorProduct = 0;
        LinkedList<Byte> currentWord = new LinkedList<>(word);
        for (byte current: optionalValues){
            xorProduct = (byte) (current ^ vectorByte);
            if (letters.containsKey(xorProduct)) continue;
            currentWord.set(wordIndex, xorProduct);
            if (dictionaryContains(currentWord)){
                ++matches;
                optionalValue = current;
            }
            currentWord = new LinkedList<>(word);
        }
        if (!iteration & 1 == matches){
            key.put(unknownLetter, optionalValue);
            optionalValues.remove(optionalValue);
            optionalKeys.remove(unknownLetter);
        }
        else if (2 == matches){
            boolean capital = true;
            int length = currentWord.size();
            for (int i = 0; i < length; ++i){
                if (i != wordIndex & (currentWord.get(i) > 96 & currentWord.get(i) < 123)){
                    capital = false;
                    break;
                }
            }
            if (!capital){
                optionalValue -= 32;
                if (!(optionalValues.contains(optionalValue) & vectorByte > 32)){
                    return;
                }
            }
            key.put(unknownLetter, optionalValue);
            optionalValues.remove(optionalValue);
            optionalKeys.remove(unknownLetter);
        }
    }

    private static boolean dictionaryContains(LinkedList<Byte> currentWord) {
        int length = currentWord.size();
        LinkedList<Byte> word = new LinkedList<>(currentWord);
        for (int i = 0; i < length; ++i){
            if (word.get(i) > 64 & word.get(i) < 91){
                byte letter = word.get(i);
                letter += 32;
                word.set(i, letter);
            }
        }
        return dictionary.contains(word);
    }

    private static void resolveKnownText(String knownPlainPath, String knownCipherPath) throws IOException {
        byte[] knownPlainText = Files.readAllBytes(new File(knownPlainPath).toPath());
        byte[] knownCipherText = Files.readAllBytes(new File(knownCipherPath).toPath());
        if (0 == knownCipherText.length | 0 == knownPlainText.length) return;
        byte[] xorProduct = Utils.xor(knownPlainText, vector);
        int length = xorProduct.length;
        for (int i = 0; i < length; ++i){
            if (( (xorProduct[i] > 64 & xorProduct[i] < 91)
                | (xorProduct[i] > 96 & xorProduct[i] < 123))
                & ((knownCipherText[i] > 64 & knownCipherText[i] < 91)
                | (knownCipherText[i] > 96 & knownCipherText[i] < 123))){

                key.put(knownCipherText[i], xorProduct[i]);
                letters.remove(knownCipherText[i]);
                optionalKeys.remove(knownCipherText[i]);
                optionalValues.remove(xorProduct[i]);
            }
        }
    }

    private static void init(String cipherPath, String ivPath) throws IOException{

        key = new HashMap<>();

        //Init Letter Map
        letters = new HashMap<>();
        letters.put((byte)65, new HashMap<>());
        letters.put((byte)66, new HashMap<>());
        letters.put((byte)67, new HashMap<>());
        letters.put((byte)68, new HashMap<>());
        letters.put((byte)69, new HashMap<>());
        letters.put((byte)70, new HashMap<>());
        letters.put((byte)71, new HashMap<>());
        letters.put((byte)72, new HashMap<>());
        letters.put((byte)73, new HashMap<>());
        letters.put((byte)74, new HashMap<>());
        letters.put((byte)75, new HashMap<>());
        letters.put((byte)76, new HashMap<>());
        letters.put((byte)77, new HashMap<>());
        letters.put((byte)78, new HashMap<>());
        letters.put((byte)79, new HashMap<>());
        letters.put((byte)80, new HashMap<>());
        letters.put((byte)81, new HashMap<>());
        letters.put((byte)82, new HashMap<>());
        letters.put((byte)83, new HashMap<>());
        letters.put((byte)84, new HashMap<>());
        letters.put((byte)85, new HashMap<>());
        letters.put((byte)86, new HashMap<>());
        letters.put((byte)87, new HashMap<>());
        letters.put((byte)88, new HashMap<>());
        letters.put((byte)89, new HashMap<>());
        letters.put((byte)90, new HashMap<>());
        letters.put((byte)97, new HashMap<>());
        letters.put((byte)98, new HashMap<>());
        letters.put((byte)99, new HashMap<>());
        letters.put((byte)100, new HashMap<>());
        letters.put((byte)101, new HashMap<>());
        letters.put((byte)102, new HashMap<>());
        letters.put((byte)103, new HashMap<>());
        letters.put((byte)104, new HashMap<>());
        letters.put((byte)105, new HashMap<>());
        letters.put((byte)106, new HashMap<>());
        letters.put((byte)107, new HashMap<>());
        letters.put((byte)108, new HashMap<>());
        letters.put((byte)109, new HashMap<>());
        letters.put((byte)110, new HashMap<>());
        letters.put((byte)111, new HashMap<>());
        letters.put((byte)112, new HashMap<>());
        letters.put((byte)113, new HashMap<>());
        letters.put((byte)114, new HashMap<>());
        letters.put((byte)115, new HashMap<>());
        letters.put((byte)116, new HashMap<>());
        letters.put((byte)117, new HashMap<>());
        letters.put((byte)118, new HashMap<>());
        letters.put((byte)119, new HashMap<>());
        letters.put((byte)120, new HashMap<>());
        letters.put((byte)121, new HashMap<>());
        letters.put((byte)122, new HashMap<>());

        //Init Ignore Chars Set
        ignoreChars = new HashSet<>();
        ignoreChars.add((byte)',');
        ignoreChars.add((byte)'.');
        ignoreChars.add((byte)';');
        ignoreChars.add((byte)':');
        ignoreChars.add((byte)';');
        ignoreChars.add((byte)'/');
        ignoreChars.add((byte)'\\');
        ignoreChars.add((byte)'@');
        ignoreChars.add((byte)'.');
        ignoreChars.add((byte)'+');
        ignoreChars.add((byte)'=');
        ignoreChars.add((byte)'*');
        ignoreChars.add((byte)'&');
        ignoreChars.add((byte)'#');
        ignoreChars.add((byte)'$');
        ignoreChars.add((byte)'!');
        ignoreChars.add((byte)'%');
        ignoreChars.add((byte)'^');
        ignoreChars.add((byte)'(');
        ignoreChars.add((byte)')');
        ignoreChars.add((byte)'-');
        ignoreChars.add((byte)'`');
        ignoreChars.add((byte)'~');
        ignoreChars.add((byte)' ');
        ignoreChars.add((byte)'\n');
        ignoreChars.add((byte)'\r');

        //Init Dictionary
        dictionary = Utils.getDictionary();

        //Optionals Key and Values
        optionalKeys = new HashSet<>(letters.keySet());
        optionalValues = new HashSet<>(letters.keySet());

        //Init Cipher Blocks and Initial Vector
        vector = Files.readAllBytes(new File(ivPath).toPath());
        size = vector.length;
        blocks = Utils.createBlocks(Files.readAllBytes(new File(cipherPath).toPath()), size, true);
    }
}
