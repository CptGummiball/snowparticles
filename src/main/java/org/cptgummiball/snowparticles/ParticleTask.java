package org.cptgummiball.snowparticles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.Set;

public class ParticleTask {

    private final SnowParticles plugin;
    private final PointManager pointManager;

    public ParticleTask(SnowParticles plugin, PointManager pointManager) {
        this.plugin = plugin;
        this.pointManager = pointManager;
    }

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<String, SnowPoint> entry : pointManager.getPoints().entrySet()) {
                    SnowPoint point = entry.getValue();
                    Location center = point.getLocation();
                    int radius = point.getRadius();
                    int lowest = point.getLowest();
                    int highest = point.getHighest();

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        Location playerLoc = player.getLocation();
                        double distance = playerLoc.distance(center);

                        // Spieler muss im Radius und Höhenbereich sein
                        if (distance <= radius && playerLoc.getY() >= lowest && playerLoc.getY() <= highest) {
                            // Überprüfen, ob Blöcke über dem Spieler sind
                            if (!isCovered(player)) {
                                // Schneepartikel in einem Bereich um den Spieler spawnen
                                for (int i = 0; i < 20; i++) { // Mehrere Partikel erzeugen
                                    double offsetX = (Math.random() * 2 - 1) * 5; // Zufällige X-Position (-5 bis 5)
                                    double offsetZ = (Math.random() * 2 - 1) * 5; // Zufällige Z-Position (-5 bis 5)
                                    double offsetY = Math.random() * 3 + 2; // Zufällige Y-Position (2 bis 5 über dem Spieler)

                                    Location particleLocation = playerLoc.clone().add(offsetX, offsetY, offsetZ);
                                    player.spawnParticle(org.bukkit.Particle.SNOWFLAKE, particleLocation, 1, 0, 0, 0, 0);
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 5L);
    }

    private boolean isCovered(Player player) {
        Location loc = player.getLocation();
        Set<Material> ignoredBlocks = plugin.getIgnoredBlocks();

        // Überprüfen, ob Blöcke 1–4 über dem Spieler existieren und relevant sind
        for (int y = 1; y <= 200; y++) {
            Location checkLoc = loc.clone().add(0, y, 0);
            Material blockType = checkLoc.getBlock().getType();

            // Wenn der Block keine Luft ist und nicht in der Ignorierliste, wird der Spieler als überdeckt betrachtet
            if (blockType != Material.AIR && !ignoredBlocks.contains(blockType)) {
                return true;
            }
        }

        return false; // Keine relevanten Blöcke über dem Spieler
    }
}
