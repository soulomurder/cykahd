package glowapples.listener;

import glowapples.util.ItemUtil;
import glowapples.util.TextUtil;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public final class PlayerInteractListener implements Listener {

    private final Sound BREAK_SOUND = Sound.sound(
        org.bukkit.Sound.BLOCK_FIRE_EXTINGUISH,
        Sound.Source.PLAYER,
        1.0F,
        2.0F
    );

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() != EquipmentSlot.HAND) return;

        if (onSpyglassUse(event)) return;
        if (onStarClick(event)) return;
    }

    public boolean onSpyglassUse(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.SPYGLASS || !item.hasItemMeta()) return false;

        ItemMeta itemMeta = item.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        if (!pdc.has(TextUtil.CIG_KEY)) return false;

        Player player = event.getPlayer();
        ItemUtil.damageItem(item, 1, BREAK_SOUND, player);
        player.getWorld().spawnParticle(
                Particle.CAMPFIRE_COSY_SMOKE,
                player.getLocation().add(0, 1.5, 0),
                10,
                0.1, 0.1, 0.1,
                0.01
        );
        List<PotionEffect> effects = player.getActivePotionEffects().stream().toList();
        boolean effectChanged = false;
        for (PotionEffect effect : effects) {
            if (effect.getType() == PotionEffectType.NAUSEA) {
                int duration = effect.getDuration();
                int calculatedDuration = (int) Math.round(15.5 * Math.pow(duration, 0.5));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, calculatedDuration, 0, true, true, false));
            }
        }
        if (!effectChanged) player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 80, 0, true, true, false));

        return true;
    }

    public boolean onStarClick(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.FIREWORK_STAR || !item.hasItemMeta()) return false;
        if (!item.getItemMeta().getPersistentDataContainer().has(TextUtil.STAR_KEY, PersistentDataType.BYTE)) return false;

        event.setCancelled(true);

        Player player = event.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 79));
        player.getWorld().spawnParticle(
                Particle.CAMPFIRE_SIGNAL_SMOKE,
                player.getLocation(),
                1000,
                0, 0, 0,
                0.1
        );
        item.subtract();

        ItemStack elytra = new ItemStack(Material.ELYTRA);
        ItemStack oldChestplate = player.getInventory().getChestplate();
        player.getInventory().setChestplate(elytra);
        if (oldChestplate.getType() != Material.AIR && !player.getInventory().addItem(oldChestplate).isEmpty()) player.getWorld().dropItemNaturally(player.getLocation(), oldChestplate);

        return true;
    }
}
