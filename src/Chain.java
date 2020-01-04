import java.util.ArrayList;
import com.google.gson.*;

public class Chain {
    public static ArrayList<Block> blockChain = new ArrayList<>();
    public static int difficulty = 6;
    public static void main(String[] args) {
        blockChain.add(new Block("Genesis Block", Sha256.useSha256("NULL")));
        blockChain.get(0).mineBlock(difficulty);

        blockChain.add(new Block("Second Block", blockChain.get(blockChain.size() - 1).hash));
        blockChain.get(1).mineBlock(difficulty);

        System.out.println("Blockchain is valid: " + isChainValid());

        String blockChainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);

        System.out.println(blockChainJson);
    }

    public static Boolean isChainValid(){
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blockChain.size(); i++){
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i - 1);
            if (!currentBlock.hash.equals(currentBlock.calHash())){
                System.out.println("Data has been changed");
                return false;
            }
            if (!previousBlock.hash.equals(currentBlock.previousHash)){
                System.out.println("Chain was broken");
                return false;
            }
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)){
                System.out.println("Block hasn't been mined");
                return false;
            }
        }
        System.out.println("Chain is unbroken");
        return true;
    }
}
