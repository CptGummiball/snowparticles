package com.cptgummiball.snowparticles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SnowParticles extends JavaPlugin {

    private File pointsFile;
    private FileConfiguration pointsConfig;

    @Override
    public void onEnable() {
        createPointsFile();
        startParticleTask();
        getCommand("snowparticle").setExecutor(new SnowParticleCommand());
    }

    @Override
    public void onDisable() {
        savePointsFile();
    }

    private void createPointsFile() {
        pointsFile = new File(getDataFolder(), "points.yml");
        if (!pointsFile.exists()) {
            pointsFile.getParentFile().mkdirs();
            saveResource("points.yml", false);
        }
        pointsConfig = YamlConfiguration.loadConfiguration(pointsFile);
    }

    private void savePointsFile() {
        try {
            pointsConfig.save(pointsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startParticleTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (pointsConfig.getConfigurationSection("points") == null) return;

                Set<String> pointKeys = pointsConfig.getConfigurationSection("points").getKeys(false);
                for (String key : pointKeys) {
                    double x = pointsConfig.getDouble("points." + key + ".x");
                    double y = pointsConfig.getDouble("points." + key + ".y");
                    double z = pointsConfig.getDouble("points." + key + ".z");
                    int radius = pointsConfig.getInt("points." + key + ".radius");
                    int lowest = pointsConfig.getInt("points." + key + ".lowest");
                    int highest = pointsConfig.getInt("points." + key + ".highest");

                    Location center = new Location(Bukkit.getWorld(pointsConfig.getString("points." + key + ".world")), x, y, z);

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        Location playerLoc = player.getLocation();
                        if (playerLoc.getWorld().equals(center.getWorld()) &&
                                playerLoc.distance(center) <= radius &&
                                playerLoc.getY() >= lowest &&
                                playerLoc.getY() <= highest) {
                            player.spawnParticle(Particle.SNOWFLAKE, playerLoc, 10, 0.5, 0.5, 0.5, 0);
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0, 20);
    }

    private class SnowParticleCommand implements TabExecutor {

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Dieser Befehl kann nur von einem Spieler verwendet werden.");
                return true;
            }

            Player player = (Player) sender;

            if (!player.hasPermission("snowparticles.admin")) {
                player.sendMessage("Du hast keine Berechtigung, diesen Befehl zu verwenden.");
                return true;
            }

            if (args.length != 4 || !args[0].equalsIgnoreCase("setpoint")) {
                player.sendMessage("Verwendung: /snowparticle setpoint <radius> <lowestpoint> <highestpoint>");
                return true;
            }

            try {
                int radius = Integer.parseInt(args[1]);
                int lowest = Integer.parseInt(args[2]);
                int highest = Integer.parseInt(args[3]);

                Location loc = player.getLocation();
                String pointKey = "point_" + System.currentTimeMillis();

                pointsConfig.set("points." + pointKey + ".x", loc.getX());
                pointsConfig.set("points." + pointKey + ".y", loc.getY());
                pointsConfig.set("points." + pointKey + ".z", loc.getZ());
                pointsConfig.set("points." + pointKey + ".world", loc.getWorld().getName());
                pointsConfig.set("points." + pointKey + ".radius", radius);
                pointsConfig.set("points." + pointKey + ".lowest", lowest);
                pointsConfig.set("points." + pointKey + ".highest", highest);

                savePointsFile();

                player.sendMessage("Schneepartikelpunkt erfolgreich gesetzt!");
            } catch (NumberFormatException e) {
                player.sendMessage("Stelle sicher, dass Radius, niedrigster Punkt und höchster Punkt Zahlen sind.");
            }

            return true;
        }

        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            if (args.length == 1) {
                List<String> completions = new ArrayList<>();
                completions.add("setpoint");
                return completions;
            }
            return null;
        }
    }
                  }
