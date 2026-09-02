package glowapples.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.Location;

public class EntityListener implements Listener {
    private final Location reuseLocation = new Location(null, 0, 0, 0);

    @EventHandler
    public void onMinecartMove(VehicleMoveEvent event) {
        if (!(event.getVehicle() instanceof Minecart minecart)) {
            return;
        }

        if (!minecart.getPassengers().isEmpty()) {
            boolean hasPlayer = minecart.getPassengers().stream().anyMatch(entity -> entity instanceof Player);
            if (hasPlayer) {
                return;
            }
        }

        minecart.getLocation(reuseLocation);
        Block currentBlock = reuseLocation.getBlock();

        if (currentBlock.getType() != Material.DETECTOR_RAIL) {
            return;
        }

        reuseLocation.setY(reuseLocation.getY() - 1);
        Block blockUnder = reuseLocation.getBlock();

        if (blockUnder.getType() == Material.BLACK_CONCRETE) {
            minecart.remove();
        }
    }
}
