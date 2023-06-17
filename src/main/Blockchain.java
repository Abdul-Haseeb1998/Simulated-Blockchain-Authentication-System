/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

// Blockchain.java
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Blockchain {
    private List<Block> blocks;
    private int blockCount;

    public Blockchain() {
        blocks = new ArrayList<>();
        blockCount = 0;
    }

    public void addBlock(Block block) {
        block.setBlockNumber(blockCount + 1);
        block.setBlockAddress(generateBlockAddress());
        blocks.add(block);
        blockCount++;
    }

    public Block getBlock(int index) {
        if (index >= 0 && index < blocks.size()) {
            return blocks.get(index);
        }
        return null;
    }

    public int getSize() {
        return blocks.size();
    }

    public void saveBlockchainData(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Block block : blocks) {
                writer.write("========== Block Start ==========");
                writer.newLine();
                writer.write("Block Number: " + block.getBlockNumber());
                writer.newLine();
                writer.write("Block Address: " + block.getBlockAddress());
                writer.newLine();
                writer.write("Username: " + block.getUsername());
                writer.newLine();
                writer.write("Encrypted Password: " + block.getEncryptedPassword());
                writer.newLine();
                writer.write("========== Block End ============");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBlockchainData(String fileName) {
        blocks.clear();
        blockCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int blockNumber = 0;
            String blockAddress = null;
            String username = null;
            String encryptedPassword = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Block Number: ")) {
                    blockNumber = Integer.parseInt(line.substring("Block Number: ".length()));
                } else if (line.startsWith("Block Address: ")) {
                    blockAddress = line.substring("Block Address: ".length());
                } else if (line.startsWith("Username: ")) {
                    username = line.substring("Username: ".length());
                } else if (line.startsWith("Encrypted Password: ")) {
                    encryptedPassword = line.substring("Encrypted Password: ".length());
                } else if (line.equals("========== Block End ============")) {
                    if (blockNumber > 0 && blockAddress != null && username != null && encryptedPassword != null) {
                        Block block = new Block(username, encryptedPassword);
                        block.setBlockNumber(blockNumber);
                        block.setBlockAddress(blockAddress);
                        blocks.add(block);
                        blockCount = Math.max(blockCount, blockNumber);
                        blockNumber = 0;
                        blockAddress = null;
                        username = null;
                        encryptedPassword = null;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Ignore if the file doesn't exist (it will be created later)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateBlockAddress() {
        int length = 16;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}
