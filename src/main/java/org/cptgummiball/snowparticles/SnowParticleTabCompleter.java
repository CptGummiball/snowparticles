package org.cptgummiball.snowparticles;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SnowParticleTabCompleter implements TabCompleter {

    private final PointManager pointManager;

    public SnowParticleTabCompleter(PointManager pointManager) {
        this.pointManager = pointManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // Haupt-Subcommands anbieten
            if ("setpoint".startsWith(args[0].toLowerCase())) {
                completions.add("setpoint");
            }
            if ("removepoint".startsWith(args[0].toLowerCase())) {
                completions.add("removepoint");
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("removepoint")) {
            // Existierende Punkte für den Befehl "removepoint" vorschlagen
            for (String pointName : pointManager.getPoints().keySet()) {
                if (pointName.toLowerCase().startsWith(args[1].toLowerCase())) {
                    completions.add(pointName);
                }
            }
        }

        return completions;
    }
}
