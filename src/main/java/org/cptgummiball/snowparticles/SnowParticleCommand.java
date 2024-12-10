package org.cptgummiball.snowparticles;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SnowParticleCommand implements CommandExecutor {

    private final PointManager pointManager;

    public SnowParticleCommand(PointManager pointManager) {
        this.pointManager = pointManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von Spielern verwendet werden.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("snowparticles.admin")) {
            player.sendMessage("Du hast keine Berechtigung, diesen Befehl zu nutzen.");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage("Verwendung: /snowparticle setpoint <name> <radius> <lowestpoint> <highestpoint> oder /snowparticle removepoint <name>");
            return true;
        }

        if (args[0].equalsIgnoreCase("setpoint")) {
            if (args.length != 5) {
                player.sendMessage("Verwendung: /snowparticle setpoint <name> <radius> <lowestpoint> <highestpoint>");
                return true;
            }

            String name = args[1];
            try {
                int radius = Integer.parseInt(args[2]);
                int lowest = Integer.parseInt(args[3]);
                int highest = Integer.parseInt(args[4]);

                if (pointManager.getPoints().containsKey(name)) {
                    player.sendMessage("Ein Punkt mit diesem Namen existiert bereits. Bitte wähle einen anderen Namen.");
                    return true;
                }

                Location loc = player.getLocation();
                SnowPoint point = new SnowPoint(loc, radius, lowest, highest);
                pointManager.addPoint(name, point);

                player.sendMessage("Schneepartikelpunkt '" + name + "' erfolgreich gesetzt! Der Effekt ist jetzt aktiv.");
            } catch (NumberFormatException e) {
                player.sendMessage("Fehler: Radius, niedrigster und höchster Punkt müssen Zahlen sein.");
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("removepoint")) {
            if (args.length != 2) {
                player.sendMessage("Verwendung: /snowparticle removepoint <name>");
                return true;
            }

            String name = args[1];
            if (!pointManager.getPoints().containsKey(name)) {
                player.sendMessage("Es existiert kein Schneepartikelpunkt mit dem Namen '" + name + "'.");
                return true;
            }

            pointManager.removePoint(name);
            player.sendMessage("Schneepartikelpunkt '" + name + "' wurde entfernt.");
            return true;
        }

        player.sendMessage("Unbekannter Befehl. Verwendung: /snowparticle setpoint oder /snowparticle removepoint.");
        return true;
    }
}
