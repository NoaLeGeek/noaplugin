package net.noalegeek.noaplugin.objects;

import net.noalegeek.noaplugin.NoaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.structure.Structure;

import java.io.IOException;
import java.util.Objects;

/**
 * @author minemobs
 */

public enum Structures {

    ISLAND(loadStructure("island.nbt")),
    ;

    private final Structure structure;

    Structures(Structure structure) {
        this.structure = structure;
    }

    private static Structure loadStructure(String path) {
        try {
            return Bukkit.getStructureManager().loadStructure(Objects.requireNonNull(NoaPlugin.getInstance().getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Structure getStructure() {
        return structure;
    }

}
