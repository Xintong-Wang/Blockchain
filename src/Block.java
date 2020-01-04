import java.security.MessageDigest;
import java.util.Date;

public class Block{
    private String data;
    public String hash;
    public String previousHash;
    private long timeStamp;
    private int nouce;

    public Block(String data, String previousHash) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calHash();
    }

    public String calHash(){
        String dataToHash = data + previousHash + Long.toString(timeStamp) + Integer.toString(nouce);
        return Sha256.useSha256(dataToHash);
    }

    public void mineBlock(int difficulty){
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)){
            nouce++;
            hash = calHash();
        }
        System.out.println("Block Mined : " + hash);
    }
}

class Sha256{
    public static String useSha256(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(data.getBytes("UTF-8"));
            StringBuffer buffer = new StringBuffer(); // This will contain hash as hexidecimal
            for (byte b : bytes) {
                buffer.append(String.format("%02x", b));
            }
            return buffer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}