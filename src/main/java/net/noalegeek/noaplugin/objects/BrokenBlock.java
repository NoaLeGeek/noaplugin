package net.noalegeek.noaplugin.objects;

import net.minecraft.network.protocol.game.ClientboundBlockDestructionPacket;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.block.CraftBlock;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class BrokenBlock {

    private final int time;
    private final Location blockLocation;
    private int oldAnimation;
    private double damage = -1;

    public BrokenBlock(Location blockLocation, int time) {
        this.blockLocation = blockLocation;
        this.time = time;
    }

    public void sendBreakPacket(int animation, Location blockLocation) {
        Bukkit.getServer().getOnlinePlayers().stream().filter(player -> player.getLocation().distanceSquared(blockLocation) < 121)
                .map(player -> ((CraftPlayer) player).getHandle().connection)
                .forEach(connection -> connection.send(new ClientboundBlockDestructionPacket(connection.player.getId(), ((CraftBlock) blockLocation.getBlock()).getPosition(), animation)));
    }

    public void sendBreakBlock(Player player, Location blockLocation) {
        ((CraftPlayer) player).getHandle().connection.getCraftPlayer().breakBlock(blockLocation.getBlock());
    }

    public void incrementDamage(Player from, double multiplier) {
        if (isBroken()) return;
        damage += multiplier;
        int animation = (int) ((damage / time) * 11);
        if (animation != oldAnimation) {
            if (animation < 10)
                sendBreakPacket(animation);
            else {
                breakBlock(from);
                return;
            }
        }
        oldAnimation = animation;
    }

    public boolean isBroken() {
        return (int) ((damage / time) * 11) >= 10;
    }

    public void breakBlock(Player breaker) {
        destroyBlockObject();
        blockLocation.getBlock().getWorld().playSound(blockLocation, blockLocation.getBlock().getBlockData().getSoundGroup().getBreakSound(), blockLocation.getBlock().getBlockData().getSoundGroup().getVolume(), blockLocation.getBlock().getBlockData().getSoundGroup().getPitch());
        if (breaker == null) return;
        sendBreakBlock(breaker, blockLocation);
    }

    public void destroyBlockObject() {
        sendBreakPacket(-1);
    }

    public void sendBreakPacket(int animation) {
        sendBreakPacket(animation, blockLocation);
    }
}
