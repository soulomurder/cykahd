package glowapples.util;

import glowapples.CykaHD;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.NamespacedKey;

import java.util.Random;

public final class TextUtil {
    private TextUtil() {}

/* OBJECT FIELDS */
    public static final MiniMessage mm = MiniMessage.miniMessage();
    public static final Random random = new Random();

/* TEXT FIELDS */
    // Permissions
    public static final String PERM_COMMAND_NV = "cykahd.command.nv";
    public static final String PERM_COMMAND_CIG = "cykahd.command.cig";
    public static final String PERM_COMMAND_SHOP = "cykahd.command.shop";
    // Keys
    public static final NamespacedKey CIG_KEY = new NamespacedKey(CykaHD.getInstance(), "cig");
    public static final NamespacedKey STAR_KEY = new NamespacedKey(CykaHD.getInstance(), "star");
    // Messages
    public static final Component COMMAND_REFUSE_MESSAGE = mm.deserialize("<red>У тебя недостаточно прав для использования этой команды.</red>");

}
