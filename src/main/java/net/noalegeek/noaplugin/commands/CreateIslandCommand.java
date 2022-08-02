package net.noalegeek.noaplugin.commands;

import fr.sunderia.sunderiautils.SunderiaUtils;
import fr.sunderia.sunderiautils.commands.CommandInfo;
import fr.sunderia.sunderiautils.commands.PluginCommand;
import net.noalegeek.noaplugin.objects.Structures;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.structure.Structure;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

/**
 * @author minemobs
 * Yes, again...
 */
@CommandInfo(name = "createisland", usage = "/createisland", permission = "noaplugin.createisland", description = "Create a Skyblock island to the center of the world at y 100")
public class CreateIslandCommand extends PluginCommand {

    public CreateIslandCommand(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void onCommand(Player player, String[] args) {
        var loc = new Location(player.getWorld(), 0, 100, 0);
        Structures.ISLAND.getStructure().place(loc, false, StructureRotation.NONE, Mirror.NONE, 0, 1, SunderiaUtils.getRandom());
        BlockVector vec = Structures.ISLAND.getStructure().getSize().clone();
        vec.multiply(.5);
        Location centerLoc = loc.clone().add(vec);
        centerLoc.getBlock().setType(Material.DIAMOND_BLOCK);
        player.teleport(loc.clone().add(0, 4, 0));
    }

    public Vector getCenter(Structure structure, boolean centerY) {
        return structure.getSize().multiply(new Vector(.5, centerY ? .5 : 0, .5));
    }
}
