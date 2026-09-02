package glowapples.command;

import glowapples.util.EntityUtil;
import glowapples.util.TextUtil;
import glowapples.СykaHDPlayer;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public final class PCommand implements BasicCommand {

    private final Map<UUID, СykaHDPlayer> players = EntityUtil.players;
    private final Component P_SET_MESSAGE = TextUtil.mm.deserialize("<gold>Точка возвращения установлена</gold>");

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player;
        if (commandSourceStack.getSender() instanceof Player thePlayer) player = thePlayer;
        else return;
        if (!thePlayer.hasPermission(TextUtil.PERM_COMMAND_P)) {
            player.sendMessage(TextUtil.COMMAND_REFUSE_MESSAGE);
            return;
        }

        СykaHDPlayer cykaHDPlayer = players.get(player.getUniqueId());
        if (cykaHDPlayer.isAbleToReturn()) {
            player.getWorld().spawnParticle(
                    Particle.PALE_OAK_LEAVES,
                    player.getLocation().add(0, 1, 0),
                    1000,
                    0.5, 0.5, 0.5,
                    1
            );
            cykaHDPlayer.returnPlayer();
            player.playSound(
                    player.getLocation(),
                    Sound.ENTITY_ENDERMAN_TELEPORT,
                    SoundCategory.PLAYERS,
                    1.0f,
                    1.0f
            );
        } else {
            cykaHDPlayer.updateLocationToReturn();
            player.sendMessage(P_SET_MESSAGE);
        }
    }
}
