package glowapples.worldguard;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public final class WorldGuardUtil {
    private WorldGuardUtil() {}

    private static final Map<String, String> stationNames = new HashMap<>();

    static {
        stationNames.put("m_an", "<gray>Станция </gray><gold>Агентство недвижимости</gold>");
        stationNames.put("m_downtown", "<gray>Станция </gray><green>Верхний город</green>");
        stationNames.put("m_dv", "<gray>Станция </gray><green>Долина времени</green>");
        stationNames.put("m_ffi", "<gray>Станция </gray><gold>Фуфелшмерц Фарма inc.</gold>");
        stationNames.put("m_govn", "<gray>Станция </gray><red>Грибной овраг водоструйного насоса</red>");
        stationNames.put("m_hom", "<gray>Станция </gray><gold>House of memories</gold>");
        stationNames.put("m_hom_", "<gray>Станция </gray><aqua>House of memories</aqua>");
        stationNames.put("m_k", "<gray>Станция </gray><gold>Кикомару</gold>");
        stationNames.put("m_kd", "<gray>Станция </gray><gold>Конвейерная деревня</gold>");
        stationNames.put("m_ki", "<gray>Станция </gray><green>Купол игроков<green>");
        stationNames.put("m_kr", "<gray>Станция </gray><red>Конец радуги</red>");
        stationNames.put("m_kr_", "<gray>Станция </gray><green>Конец радуги</green>");
        stationNames.put("m_m", "<gray>Станция </gray><aqua>Монополия</aqua>");
        stationNames.put("m_nc", "<gray>Станция </gray><gold>Набережные члены</gold>");
        stationNames.put("m_ni", "<gray>Станция </gray><green>Нищая интеллигенция</green>");
        stationNames.put("m_nntnvls", "<gray>Станция </gray><green>Ну нихуя ты настя в лагерь съездила</green>");
        stationNames.put("m_oobh", "<gray>Станция </gray><red>Университет ОГБ</red>");
        stationNames.put("m_pd", "<gray>Станция </gray><gold>Пятая деревня</gold>");
        stationNames.put("m_pe", "<gray>Станция </gray><red>Пизда Елены</red>");
        stationNames.put("m_pvp", "<gray>Станция </gray><red>Парк вредных привычек</red>");
        stationNames.put("m_pvp_", "<gray>Станция </gray><green>Парк вредных привычек</green>");
        stationNames.put("m_s", "<gray>Станция </gray><green>Сплиф</green>");
        stationNames.put("m_s_", "<gray>Станция </gray><aqua>Сплиф</aqua>");
        stationNames.put("m_sk", "<gray>Станция </gray><aqua>Смешная кошка</aqua>");
        stationNames.put("m_tl", "<gray>Станция </gray><red>Тёмный лес</red>");
        stationNames.put("m_tl_", "<gray>Станция </gray><gold>Тёмный лес</gold>");
        stationNames.put("m_vg", "<gray>Станция </gray><green>Врата города</green>");
        stationNames.put("m_vgz", "<gray>Станция </gray><green>В Германии жарко</green>");
        stationNames.put("m_zzl", "<gray>Станция </gray><gold>Замкадье заходящей луны</gold>");
    }

    public static List<ProtectedRegion> getRegionsAtPlayerLocation(Player player) {
        List<ProtectedRegion> regions = new ArrayList<>();

        BlockVector3 position = BukkitAdapter.asBlockVector(player.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regionManager = container.get(BukkitAdapter.adapt(player.getWorld()));
        if (regionManager == null) return regions;
        ApplicableRegionSet regionSet = regionManager.getApplicableRegions(position);
        for (ProtectedRegion region : regionSet) regions.add(region);

        return regions;
    }

    public static List<ProtectedRegion> filterOnlyParentRegions(List<ProtectedRegion> originalRegions) {
        return originalRegions.stream()
                .filter(region -> region.getParent() == null)
                .collect(Collectors.toList());
    }

    public static Set<String> getRootParentRegions(List<ProtectedRegion> regions) {
        Set<String> rootNames = new HashSet<>();
        if (regions == null || regions.isEmpty()) return rootNames;
        for (ProtectedRegion region : regions) {
            ProtectedRegion current = region;
            while (current.getParent() != null) current = current.getParent();
            rootNames.add(current.getId());
        }

        return rootNames;
    }

    public static String getMetroStation(List<ProtectedRegion> regions) {
        if (regions == null || regions.isEmpty()) return null;
        for (ProtectedRegion region : regions) {
            ProtectedRegion parent = region.getParent();
            if (parent != null && parent.getId().equals("m")) return region.getId();
        }
        return null;
    }

    public static String getMetroStationName(String station) {
        return stationNames.get(station);
    }
}
