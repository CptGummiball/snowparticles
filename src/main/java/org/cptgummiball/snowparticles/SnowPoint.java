package org.cptgummiball.snowparticles;

import org.bukkit.Location;

public class SnowPoint {
    private final Location location;
    private final int radius;
    private final int lowest;
    private final int highest;

    public SnowPoint(Location location, int radius, int lowest, int highest) {
        this.location = location;
        this.radius = radius;
        this.lowest = lowest;
        this.highest = highest;
    }

    public Location getLocation() {
        return location;
    }

    public int getRadius() {
        return radius;
    }

    public int getLowest() {
        return lowest;
    }

    public int getHighest() {
        return highest;
    }
}
