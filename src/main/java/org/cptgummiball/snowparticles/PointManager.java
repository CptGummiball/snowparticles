package org.cptgummiball.snowparticles;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PointManager {

    private final SnowParticles plugin;
    private final File pointsFile;
    private final FileConfiguration pointsConfig;

    private final Map<String, SnowPoint> points = new HashMap<>();

    public PointManager(SnowParticles plugin) {
        this.plugin = plugin;
        this.pointsFile = new File(plugin.getDataFolder(), "points.yml");
        if (!pointsFile.exists()) {
            try {
                pointsFile.getParentFile().mkdirs();
                pointsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.pointsConfig = YamlConfiguration.loadConfiguration(pointsFile);
    }

    public void loadPoints() {
        if (pointsConfig.getConfigurationSection("points") == null) return;
        for (String key : pointsConfig.getConfigurationSection("points").getKeys(false)) {
            double x = pointsConfig.getDouble("points." + key + ".x");
            double y = pointsConfig.getDouble("points." + key + ".y");
            double z = pointsConfig.getDouble("points." + key + ".z");
            String world = pointsConfig.getString("points." + key + ".world");
            int radius = pointsConfig.getInt("points." + key + ".radius");
            int lowest = pointsConfig.getInt("points." + key + ".lowest");
            int highest = pointsConfig.getInt("points." + key + ".highest");

            Location location = new Location(plugin.getServer().getWorld(world), x, y, z);
            points.put(key, new SnowPoint(location, radius, lowest, highest));
        }
    }

    public void savePoints() {
        pointsConfig.set("points", null); // Löscht die alte Liste
        for (Map.Entry<String, SnowPoint> entry : points.entrySet()) {
            String name = entry.getKey();
            SnowPoint point = entry.getValue();
            Location loc = point.getLocation();

            pointsConfig.set("points." + name + ".x", loc.getX());
            pointsConfig.set("points." + name + ".y", loc.getY());
            pointsConfig.set("points." + name + ".z", loc.getZ());
            pointsConfig.set("points." + name + ".world", loc.getWorld().getName());
            pointsConfig.set("points." + name + ".radius", point.getRadius());
            pointsConfig.set("points." + name + ".lowest", point.getLowest());
            pointsConfig.set("points." + name + ".highest", point.getHighest());
        }

        try {
            pointsConfig.save(pointsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPoint(String name, SnowPoint point) {
        points.put(name, point);
    }

    public void removePoint(String name) {
        points.remove(name);
    }

    public Map<String, SnowPoint> getPoints() {
        return points;
    }
}

