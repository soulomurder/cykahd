package glowapples;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import glowapples.util.WorldGuardUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class CykaHDPlayer {
    public CykaHDPlayer(Player player) {
        this.player = player;
        setLocation(player.getLocation());
        updateRegions();
    }

    private Player player;
    private Location locationToReturn;
    private int x;
    private int y;
    private int z;
    private Set<String> rootParentRegions;
    private String metroStation;
    private String district;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public void setLocation(Location location) {
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public Set<String> getRootParentRegions() {
        return this.rootParentRegions;
    }

    public void updateRegions() {
        setLocation(player.getLocation());
        List<ProtectedRegion> regions = WorldGuardUtil.getRegionsAtPlayerLocation(player);
        this.rootParentRegions = WorldGuardUtil.getRootParentRegions(regions);
        if (this.rootParentRegions.contains("m")) this.metroStation = WorldGuardUtil.getMetroStation(regions);
        else this.metroStation = null;
        if (this.rootParentRegions.contains("district")) this.district = WorldGuardUtil.getDistrict(regions);
        else this.district = null;
    }

    public void returnPlayer() {
        this.player.teleport(this.locationToReturn);
        this.locationToReturn = null;
    }

    public boolean isAbleToReturn() {
        return this.locationToReturn != null;
    }

    public String getMetroStation() {
        return this.metroStation;
    }

    public String getDistrict() {
        return district;
    }

    public void updateLocationToReturn() {
        this.locationToReturn = this.player.getLocation();
    }
}
