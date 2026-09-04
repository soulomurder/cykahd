package glowapples;

import glowapples.util.EntityUtil;
import glowapples.util.TextUtil;
import glowapples.util.WorldGuardUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public final class Timers {
    private Timers() {}

    private static final Map<UUID, CykaHDPlayer> players = EntityUtil.players;

    public static void worldGuardTimer() {
        Bukkit.getScheduler().runTaskTimer(CykaHD.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();
                CykaHDPlayer cykaHDPlayer = players.get(uuid);
                if (cykaHDPlayer == null) {
                    players.put(uuid, new CykaHDPlayer(player));
                    continue;
                }
                cykaHDPlayer.setPlayer(player);

                Location location = player.getLocation();
                String oldDistrict = cykaHDPlayer.getDistrict();
                if (location.getBlockX() != cykaHDPlayer.getX()
                || location.getBlockY() != cykaHDPlayer.getY()
                || location.getBlockZ() != cykaHDPlayer.getZ()) cykaHDPlayer.updateRegions();

                Set<String> regions = cykaHDPlayer.getRootParentRegions();
                String newDistrict = cykaHDPlayer.getDistrict();
                if (regions.contains("h")) giveFireworkStar(player);
                if (cykaHDPlayer.getMetroStation() != null) metro(cykaHDPlayer);
                if (newDistrict != null && !newDistrict.equals(oldDistrict)) district(cykaHDPlayer);
            }
        }, 0L, 10L);
    }

    private static void giveFireworkStar(Player player) {
        PlayerInventory inventory = player.getInventory();

        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() == Material.FIREWORK_STAR && item.hasItemMeta()) {
                if (item.getItemMeta().getPersistentDataContainer().has(TextUtil.STAR_KEY, PersistentDataType.BYTE)) {
                    return;
                }
            }
        }

        ItemStack star = inventory.getItemInMainHand();
        if (!star.isEmpty()) return;

        star = new ItemStack(Material.FIREWORK_STAR);
        FireworkEffectMeta meta = (FireworkEffectMeta) star.getItemMeta();

        if (meta != null) {
            Color randomColor = Color.fromRGB(TextUtil.random.nextInt(256), TextUtil.random.nextInt(256), TextUtil.random.nextInt(256));
            FireworkEffect effect = FireworkEffect.builder()
                    .withColor(randomColor)
                    .build();
            meta.setEffect(effect);

            net.kyori.adventure.text.format.TextColor textColor = net.kyori.adventure.text.format.TextColor.color(randomColor.asRGB());
            meta.displayName(net.kyori.adventure.text.Component.text("§rПКМ для взлёта").color(textColor));
            meta.getPersistentDataContainer().set(TextUtil.STAR_KEY, PersistentDataType.BYTE, (byte) 1);
            star.setItemMeta(meta);
        }
        inventory.setItemInMainHand(star);
    }

    private static void metro(CykaHDPlayer cykaHDPlayer) {
        Player player = cykaHDPlayer.getPlayer();

        player.sendActionBar(WorldGuardUtil.getMetroStationName(cykaHDPlayer.getMetroStation()));
        PlayerInventory inventory = player.getInventory();
        ItemStack minecart = new ItemStack(Material.MINECART);
        if (!inventory.contains(Material.MINECART)){
            if (inventory.getItemInMainHand().isEmpty()) inventory.setItemInMainHand(minecart);
            else inventory.addItem();
        }
    }

    private static void district(CykaHDPlayer cykaHDPlayer) {
        Player player = cykaHDPlayer.getPlayer();

        player.sendActionBar(WorldGuardUtil.getDistrictName(cykaHDPlayer.getDistrict()));
    }
}
