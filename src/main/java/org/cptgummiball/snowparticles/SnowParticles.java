package org.cptgummiball.snowparticles;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SnowParticles extends JavaPlugin {

    private static SnowParticles instance;
    private PointManager pointManager;
    private Set<Material> ignoredBlocks;

    @Override
    public void onEnable() {
        instance = this;

        // Config laden oder erstellen
        saveDefaultConfig();
        loadIgnoredBlocks();

        pointManager = new PointManager(this);
        pointManager.loadPoints();

        new ParticleTask(this, pointManager).start();

        // Command und Tab-Completer registrieren
        getCommand("snowparticle").setExecutor(new SnowParticleCommand(pointManager));
        getCommand("snowparticle").setTabCompleter(new SnowParticleTabCompleter(pointManager));
    }

    @Override
    public void onDisable() {
        pointManager.savePoints();
    }

    public static SnowParticles getInstance() {
        return instance;
    }

    public Set<Material> getIgnoredBlocks() {
        return ignoredBlocks;
    }

    private void loadIgnoredBlocks() {
        ignoredBlocks = new HashSet<>();
        List<String> blockNames = getConfig().getStringList("ignored-blocks");
        for (String blockName : blockNames) {
            try {
                Material material = Material.valueOf(blockName.toUpperCase());
                ignoredBlocks.add(material);
            } catch (IllegalArgumentException e) {
                getLogger().warning("Ungültiger Blockname in der Config: " + blockName);
            }
        }
    }
}

