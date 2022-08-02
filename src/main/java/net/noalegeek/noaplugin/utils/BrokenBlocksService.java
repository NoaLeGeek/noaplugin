package net.noalegeek.noaplugin.utils;

import net.noalegeek.noaplugin.objects.BrokenBlock;
import org.bukkit.Location;

import java.util.HashMap;

public class BrokenBlocksService {

    private static final HashMap<Location, BrokenBlock> brokenBlocks = new HashMap<>();

    public static void createBrokenBlock(Location blockLocation) {
        createBrokenBlock(blockLocation, -1);
    }

    public static void createBrokenBlock(Location blockLocation, int time) {
        if (isBrokenBlock(blockLocation)) return;
        brokenBlocks.put(blockLocation, new BrokenBlock(blockLocation, time == -1 ? 0 : time));
    }

    public static void removeBrokenBLock(Location blockLocation) {
        brokenBlocks.remove(blockLocation);
    }

    public static BrokenBlock getBrokenBlock(Location blockLocation) {
        createBrokenBlock(blockLocation);
        return brokenBlocks.get(blockLocation);
    }

    public static boolean isBrokenBlock(Location blockLocation) {
        return brokenBlocks.containsKey(blockLocation);
    }
}
