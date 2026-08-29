package glowapples.command;

import glowapples.util.TextUtil;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class CigCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player;
        if (commandSourceStack.getSender() instanceof Player thePlayer) player = thePlayer;
        else return;
        if (!thePlayer.hasPermission(TextUtil.PERM_COMMAND_CIG)) {
            player.sendMessage(TextUtil.COMMAND_REFUSE_MESSAGE);
            return;
        }

        ItemStack cig = new ItemStack(Material.SPYGLASS);
        cig.setData(DataComponentTypes.MAX_DAMAGE, 13);
        ItemMeta meta = cig.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(TextUtil.CIG_KEY, PersistentDataType.INTEGER, 1);
            cig.setItemMeta(meta);
        }
        player.getInventory().setItemInMainHand(cig);
    }
}
