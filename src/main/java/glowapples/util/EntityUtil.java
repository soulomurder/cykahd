package glowapples.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import java.util.Collection;

public final class EntityUtil {
    private EntityUtil() {}

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
