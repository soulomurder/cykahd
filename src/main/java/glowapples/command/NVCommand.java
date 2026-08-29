package glowapples.command;

import glowapples.util.TextUtil;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public final class NVCommand implements BasicCommand {

    private final Component effectGiveMsg = TextUtil.mm.deserialize("<color:gold>Режим ночного зрения включён</color>");
    private final Component effectClearMsg = TextUtil.mm.deserialize("<color:gray>Режим ночного зрения выключен</color>");
    private final PotionEffect effect = new PotionEffect(
            PotionEffectType.NIGHT_VISION,
            PotionEffect.INFINITE_DURATION,
            0,
            false,
            false,
            false
    );

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player;
        if (commandSourceStack.getSender() instanceof Player thePlayer) player = thePlayer;
        else return;
        if (!thePlayer.hasPermission(TextUtil.PERM_COMMAND_NV)) {
            player.sendMessage(TextUtil.COMMAND_REFUSE_MESSAGE);
            return;
        }

        List<PotionEffect> effects = player.getActivePotionEffects().stream().toList();
        boolean effectCleared = false;
        for (PotionEffect effect : effects) {
            if (effect.getType() == PotionEffectType.NIGHT_VISION && effect.isInfinite() && !effect.hasParticles()) {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.sendMessage(effectClearMsg);
                effectCleared = true;
                break;
            }
        }
        if (!effectCleared) {
            player.addPotionEffect(effect);
            player.sendMessage(effectGiveMsg);
        }
    }
}
