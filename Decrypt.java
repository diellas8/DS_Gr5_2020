import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class Decrypt {


    public static void readMessage(String message) throws Exception {
        String[] messageArray = message.split("\\.");
        String name = readKeyName(messageArray[0]);
        System.out.println("Marresi: " + name);
        readDESKey(name, messageArray[2], messageArray[3]);
    }

    public static void readFile(String path) throws Exception {
        try {
            StringBuffer response = new StringBuffer();
            String inputLine;
            BufferedReader Buff = new BufferedReader(new FileReader(path));
            while ((inputLine = Buff.readLine()) != null) {
                response.append(inputLine);
            }
            String message = response.toString();
            String[] messageArray = message.split("\\.");
            String name = readKeyName(messageArray[0]);
            System.out.println("Marresi: " + name);
            readDESKey(name, messageArray[2], messageArray[3]);
        } catch (FileNotFoundException e) {
            System.out.println("Ju lutem shkruani nje path valid.");
        }
    }
    public static String readKeyName(String encryptedName) throws UnsupportedEncodingException {
        byte[] bytes = Base64.getDecoder().decode(encryptedName);
        String value = new String(bytes, "UTF-8");
        return value;
    }

    public static void readDESKey(String name, String message, String desMessage) throws Exception  {
        String privateKeyString = readPrivateKey(name);
        if(privateKeyString.equals("nuk ekziston celesi")){
            System.exit(-1);
        }
        byte[] decoded = Base64.getDecoder().decode(privateKeyString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(keySpec);
        Cipher dcipher = Cipher.getInstance("RSA");
        dcipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] dec = dcipher.doFinal(Base64.getDecoder().decode(message));
        SecretKey DESKey = new SecretKeySpec(dec,0, dec.length, "DES");
        decryptMessage(DESKey, desMessage);

    }

    public static String readPrivateKey(String name) throws IOException {
        String privateFileName = "keys/" + name.replaceAll("[^A-Za-z0-9_]", "") + ".key";

        BufferedReader Buff = new BufferedReader(new FileReader(privateFileName));
        String firstLine = Buff.readLine();
        if(firstLine.equals("-----BEGIN RSA PRIVATE KEY-----")) {
            String secondLine = Buff.readLine();
            Buff.close();
            return secondLine;
        }
        else {
            System.out.println("Celesi privat '" + name + "' nuk eksizton.");
            Buff.close();
            return "nuk ekziston celesi";
        }

    }
    public static void decryptMessage(SecretKey key, String message) throws Exception{
        Cipher dcipher = Cipher.getInstance("DES");
        dcipher.init(Cipher.DECRYPT_MODE, key);
        byte[] dec = Base64.getDecoder().decode(message);
        byte[] utf8 = dcipher.doFinal(dec);
        System.out.println("Mesazhi: " + new String(utf8, "UTF8"));
    }

}