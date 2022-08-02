package net.noalegeek.noaplugin.utils;

import fr.sunderia.sunderiautils.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtils {

    private InventoryUtils() {
    }

    public static void fillAll(Inventory inventory, ItemStack itemStack, int rows) {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (int i = 0; i < rows * 9; i++) {
            itemStackList.add(itemStack);
        }
        fillAll(inventory, itemStackList, rows);
    }

    public static void fillAll(Inventory inventory, List<ItemStack> itemStackList, int rows) {
        if (rows > 6 || rows < 1)
            throw new IllegalArgumentException("The rows has to be greater than 0 and lower than 7");
        if (itemStackList.size() != rows * 9)
            throw new ArrayIndexOutOfBoundsException("The ItemStack list should have a length of" + rows * 9 + "but the list has a length of " + itemStackList.size());
        for (int index = 0; index < rows * 9; index++) {
            inventory.setItem(index, itemStackList.get(index));
        }
    }

    public static void fillRow(Inventory inventory, ItemStack itemStack, int row) {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            itemStackList.add(itemStack);
        }
        fillRow(inventory, itemStackList, row);
    }

    public static void fillRow(Inventory inventory, List<ItemStack> itemStackList, int row) {
        if (row > 6 || row < 1)
            throw new IllegalArgumentException("The row has to be greater than 0 and lower than 7");
        if (itemStackList.size() != 9) {
            throw new ArrayIndexOutOfBoundsException("The ItemStack list should have a length of 9 but the list has a length of " + itemStackList.size());
        }
        for (int index = row * 9 - 9; index < row * 9; index++) {
            inventory.setItem(index, itemStackList.get(index - 9 * (row - 1)));
        }
    }

    public static void fillColumn(Inventory inventory, ItemStack itemStack, int rows, int column) {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            itemStackList.add(itemStack);
        }
        fillColumn(inventory, itemStackList, rows, column);
    }

    public static void fillColumn(Inventory inventory, List<ItemStack> itemStackList, int rows, int column) {
        if (rows > 6 || rows < 1)
            throw new IllegalArgumentException("The rows has to be greater than 0 and lower than 7");
        if (column > 9 || column < 1)
            throw new IllegalArgumentException("The column has to be greater than 0 and lower than 10");
        if (itemStackList.size() != rows)
            throw new ArrayIndexOutOfBoundsException("The ItemStack list should have a length of " + rows + " but the list has a length of " + itemStackList.size());
        for (int row = 0; row < rows; row++) {
            inventory.setItem((row * 9) + column - 1, itemStackList.get(row));
        }
    }

    public static void fillBorders(Inventory inventory, ItemStack itemStack, int rows) {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (int i = 0; i < (rows == 1 ? 11 : 18) + ((rows - 2) * 2); i++) {
            itemStackList.add(itemStack);
        }
        fillBorders(inventory, itemStackList, rows);
    }

    public static void fillBorders(Inventory inventory, List<ItemStack> itemStackList, int rows) {
        if (rows > 6 || rows < 1)
            throw new IllegalArgumentException("The rows has to be greater than 0 and lower than 7");
        if (itemStackList.size() != ((rows == 1 ? 11 : 18) + ((rows - 2) * 2)))
            throw new ArrayIndexOutOfBoundsException("The ItemStack list should have a length of " + ((rows == 1 ? 11 : 18) + ((rows - 2) * 2)) + " but the list has a length of " + itemStackList.size());
        fillRow(inventory, itemStackList.subList(0, 9), 1);
        if (rows >= 2) fillRow(inventory, itemStackList.subList(itemStackList.size() - 9, itemStackList.size()), rows);
        if (rows >= 3) {
            for (int row = 1, count = 9; row < rows - 1; row++) {
                inventory.setItem((row * 9), itemStackList.get(row + count).getType() != Material.AIR && (!itemStackList.get(row + count).hasItemMeta() || itemStackList.get(row + count).getItemMeta().getDisplayName().isEmpty()) ? new ItemBuilder(itemStackList.get(row + count)).setDisplayName(itemStackList.get(row + count).getType().name()).build() : itemStackList.get(row + count));
                inventory.setItem((row * 9) + 8, itemStackList.get(row + ++count).getType() != Material.AIR && (!itemStackList.get(row + count).hasItemMeta() || itemStackList.get(row + count).getItemMeta().getDisplayName().isEmpty()) ? new ItemBuilder(itemStackList.get(row + count)).setDisplayName(itemStackList.get(count + row).getType().name()).build() : itemStackList.get(row + count));
            }
        }
    }

    public static void fillRectangle(Inventory inventory, List<ItemStack> itemStackList, int fromRow, int fromColumn, int toRow, int toColumn) {
        if (fromRow > 6 || fromRow < 1)
            throw new IllegalArgumentException("The fromRow has to be greater than 0 and lower than 7");
        if (fromColumn > 9 || fromColumn < 1)
            throw new IllegalArgumentException("The fromColumn has to be greater than 0 and lower than 10");
        if (itemStackList.size() != ((toRow - fromRow + 1) * (toColumn - fromColumn + 1)))
            throw new ArrayIndexOutOfBoundsException("The ItemStack list should have a length of " + ((toRow - fromRow + 1) * (toColumn - fromColumn + 1)) + " but the list has a length of " + itemStackList.size());
        for (int row = fromRow - 1; row < toRow; row++) {
            for (int column = fromColumn - 1; column < toColumn; column++) {
                inventory.setItem((row * 9) + column, itemStackList.get((row - fromRow + 1 + (row - fromRow + 1) * (toColumn - fromColumn + 1))));
            }
        }
    }

    public static void fillRectangle(Inventory inventory, ItemStack itemStack, int fromRow, int fromColumn, int toRow, int toColumn) {
        List<ItemStack> itemStackList = new ArrayList<>();
        for (int i = 0; i < ((toRow - fromRow + 1) * (toColumn - fromColumn + 1)); i++) {
            itemStackList.add(itemStack);
        }
        fillRectangle(inventory, itemStackList, fromRow, fromColumn, toRow, toColumn);
    }

    public static void setItem(Inventory inventory, ItemStack itemStack, int row, int column) {
        if (row > 6 || row < 1) throw new IllegalArgumentException("The row has to be greater than 0 and lower than 7");
        if (column > 9 || column < 1)
            throw new IllegalArgumentException("The column has to be greater than 0 and lower than 10");
        inventory.setItem(row * 9 - 10 + column, itemStack);
    }

    public static ItemStack getItem(Inventory inventory, int row, int column) {
        if (row > 6 || row < 1) throw new IllegalArgumentException("The row has to be greater than 0 and lower than 7");
        if (column > 9 || column < 1)
            throw new IllegalArgumentException("The column has to be greater than 0 and lower than 10");
        return inventory.getItem(row * 9 - 10 + column);
    }

    public static boolean isSameSlot(int slot, int row, int column) {
        if (row > 6 || row < 1) throw new IllegalArgumentException("The row has to be greater than 0 and lower than 7");
        if (column > 9 || column < 1)
            throw new IllegalArgumentException("The column has to be greater than 0 and lower than 10");
        return row * 9 - 10 + column == slot;
    }

    public static void clearAll(Inventory inventory, int rows) {
        fillAll(inventory, new ItemStack(Material.AIR), rows);
    }
}