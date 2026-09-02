package glowapples;

import glowapples.command.CigCommand;
import glowapples.command.NVCommand;
import glowapples.command.PCommand;
import glowapples.command.ShopCommand;
import glowapples.listener.PlayerListener;
import glowapples.listener.EntityListener;
import glowapples.util.TextUtil;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

public final class CykaHD extends JavaPlugin {

    private static CykaHD instance;

    @Override
    public void onEnable() {
        instance = this;
        initPermissions();
        initCommands();
        initListeners();
        initTimers();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void initPermissions() {
        this.getServer().getPluginManager().addPermission(new Permission(
                TextUtil.PERM_COMMAND_NV,
                "nv",
                PermissionDefault.OP)
        );
        this.getServer().getPluginManager().addPermission(new Permission(
                TextUtil.PERM_COMMAND_CIG,
                "cig",
                PermissionDefault.OP)
        );
        this.getServer().getPluginManager().addPermission(new Permission(
                TextUtil.PERM_COMMAND_SHOP,
                "shop",
                PermissionDefault.OP)
        );
        this.getServer().getPluginManager().addPermission(new Permission(
                TextUtil.PERM_COMMAND_P,
                "shop",
                PermissionDefault.OP)
        );
    }

    private void initCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands registrar = event.registrar();
            registrar.register("nv", new NVCommand());
        });
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands registrar = event.registrar();
            registrar.register("cig", new CigCommand());
        });
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands registrar = event.registrar();
            registrar.register("shop", new ShopCommand());
        });
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands registrar = event.registrar();
            registrar.register("p", new PCommand());
        });
    }

    private void initListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
    }

    private void initTimers() {
        Timers.worldGuardTimer();
    }

    public static CykaHD getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Плагин еще не загружен или уже выключен!");
        }
        return instance;
    }
}
