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

    private static final Map<UUID, СykaHDPlayer> players = EntityUtil.players;

    public static void worldGuardTimer() {
        Bukkit.getScheduler().runTaskTimer(CykaHD.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();
                СykaHDPlayer wgPlayer = players.get(uuid);
                if (wgPlayer == null) {
                    players.put(uuid, new СykaHDPlayer(player));
                    continue;
                }
                wgPlayer.setPlayer(player);

                Location location = player.getLocation();
                if (location.getBlockX() != wgPlayer.getX()
                || location.getBlockY() != wgPlayer.getY()
                || location.getBlockZ() != wgPlayer.getZ()) wgPlayer.updateRootParentRegions();

                Set<String> regions = wgPlayer.getRootParentRegions();
                if (regions.contains("h")) giveFireworkStar(player);
                if (wgPlayer.getMetroStation() != null) metro(wgPlayer);
            }
        }, 0L, 10L);
    }

    private static void giveFireworkStar(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.getType() == Material.FIREWORK_STAR && item.hasItemMeta()) {
                if (item.getItemMeta().getPersistentDataContainer().has(TextUtil.STAR_KEY, PersistentDataType.BYTE)) {
                    return;
                }
            }
        }

        ItemStack star = new ItemStack(Material.FIREWORK_STAR);
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

        if (!player.getInventory().addItem(star).isEmpty()) {
            player.getWorld().dropItemNaturally(player.getLocation(), star);
        }
    }

    private static void metro(СykaHDPlayer wgPlayer) {
        Player player = wgPlayer.getPlayer();

        player.sendActionBar(TextUtil.mm.deserialize(WorldGuardUtil.getMetroStationName(wgPlayer.getMetroStation())));
        PlayerInventory inventory = player.getInventory();
        ItemStack minecart = new ItemStack(Material.MINECART);
        if (!inventory.contains(Material.MINECART)){
            if (inventory.getItemInMainHand().isEmpty()) inventory.setItemInMainHand(minecart);
            else inventory.addItem();
        }
    }
}
