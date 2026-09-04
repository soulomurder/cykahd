package glowapples.util;

import io.papermc.paper.datacomponent.DataComponentTypes;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class ItemUtil {
    private ItemUtil() {}

    public static void damageItem(ItemStack item, int damage, Sound breakSound, Player player) {
        if (!item.hasData(DataComponentTypes.MAX_DAMAGE)) return;

        int maxDamage = item.getData(DataComponentTypes.MAX_DAMAGE);
        int currentDamage = item.getDataOrDefault(DataComponentTypes.DAMAGE, 0);
        currentDamage += damage;
        if (currentDamage > maxDamage) {
            item.subtract();
            player.playSound(breakSound);
            return;
        }
        else if (currentDamage < 0) {
            item.resetData(DataComponentTypes.DAMAGE);
            return;
        }
        item.setData(DataComponentTypes.DAMAGE, currentDamage);
    }
}
