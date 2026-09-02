package glowapples.util;

import glowapples.СykaHDPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class EntityUtil {
    private EntityUtil() {}

    public static final Map<UUID, СykaHDPlayer> players = new HashMap<>();

    public static <T> T getNearestEntity(Location loc, double radius, Class<T> type) {
        if (loc == null || loc.getWorld() == null || type == null) return null;

        Collection<Entity> entities = loc.getWorld().getNearbyEntities(
                loc, radius, radius, radius,
                type::isInstance
        );

        T nearestEntity = null;
        double closestDistanceSq = Double.MAX_VALUE;

        for (Entity entity : entities) {
            double distanceSq = loc.distanceSquared(entity.getLocation());

            if (distanceSq < closestDistanceSq) {
                closestDistanceSq = distanceSq;
                nearestEntity = type.cast(entity);
            }
        }

        return nearestEntity;
    }

}
