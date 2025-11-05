package model;

import java.io.*;
import java.util.*;

/*
 Name: Sadman Nahin
 Course: CNT 4714 – Summer 2025
 Assignment title: Project 1 – An Event-driven Enterprise Simulation
 Date: Sunday June 1, 2025
*/

public class InventoryManager {
    private Map<String, InventoryItem> inventoryMap = new HashMap<>();

    public InventoryManager(String inventoryFilePath) throws IOException {
        loadInventory(inventoryFilePath);
    }

    private void loadInventory(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = reader.readLine()) != null) {
            // Each line: ID, "Description", Status, Qty, Price
            // e.g. A123,"Wireless Mouse",InStock,12,19.99
            String[] tokens = parseCsvLine(line);
            if (tokens.length != 5) continue;

            String id = tokens[0].trim();
            String desc = tokens[1].trim();
            String status = tokens[2].trim();
            int qty;
            double price;
            try {
                qty = Integer.parseInt(tokens[3].trim());
                price = Double.parseDouble(tokens[4].trim());
            } catch (NumberFormatException e) {
                // Bad line → skip
                continue;
            }

            InventoryItem item = new InventoryItem(id, desc, status, qty, price);
            inventoryMap.put(id, item);
        }
        reader.close();
    }

    // Simple CSV parser that handles quoted descriptions
    private String[] parseCsvLine(String line) {
        List<String> list = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
                continue;
            }
            if (c == ',' && !inQuotes) {
                list.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        list.add(sb.toString());
        return list.toArray(new String[0]);
    }

    // Lookup by ID
    public InventoryItem findItemById(String id) {
        return inventoryMap.get(id);
    }

    // Update quantity on hand
    public void updateQuantity(String id, int newQty) {
        InventoryItem item = inventoryMap.get(id);
        if (item != null) {
            item.setQuantityOnHand(newQty);
        }
    }
}
