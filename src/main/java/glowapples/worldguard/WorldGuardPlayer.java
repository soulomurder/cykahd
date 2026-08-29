package glowapples.worldguard;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class WorldGuardPlayer {
    public WorldGuardPlayer(Player player) {
        this.player = player;
        setLocation(player.getLocation());
        updateRootParentRegions();
    }

    private Player player;
    private int x;
    private int y;
    private int z;
    private Set<String> rootParentRegions;
    private String metroStation;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setLocation(Location location) {
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public Set<String> getRootParentRegions() {
        return rootParentRegions;
    }

    public void updateRootParentRegions() {
        List<ProtectedRegion> regions = WorldGuardUtil.getRegionsAtPlayerLocation(player);
        this.rootParentRegions = WorldGuardUtil.getRootParentRegions(regions);
        if (rootParentRegions.contains("m")) metroStation = WorldGuardUtil.getMetroStation(regions);
        else metroStation = null;
    }

    public String getMetroStation() {
        return metroStation;
    }
}
