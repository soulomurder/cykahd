package glowapples.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.w3c.dom.Text;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public final class WorldGuardUtil {
    private WorldGuardUtil() {}

    private static final Map<String, net.kyori.adventure.text.Component> stationNames = new HashMap<>();
    private static final Map<String, net.kyori.adventure.text.Component> districtNames = new HashMap<>();

    static {
        stationNames.put("m_an", TextUtil.AN_STATION);
        stationNames.put("m_downtown", TextUtil.DOWNTOWN_STATION);
        stationNames.put("m_dv", TextUtil.DV_STATION);
        stationNames.put("m_ffi", TextUtil.FFI_STATION);
        stationNames.put("m_govn", TextUtil.GOVN_STATION);
        stationNames.put("m_hom", TextUtil.HOM_STATION);
        stationNames.put("m_hom_", TextUtil.HOM__STATION);
        stationNames.put("m_k", TextUtil.K_STATION);
        stationNames.put("m_kd", TextUtil.KD_STATION);
        stationNames.put("m_ki", TextUtil.KI_STATION);
        stationNames.put("m_kr", TextUtil.KR_STATION);
        stationNames.put("m_kr_", TextUtil.KR__STATION);
        stationNames.put("m_m", TextUtil.M_STATION);
        stationNames.put("m_nc", TextUtil.NC_STATION);
        stationNames.put("m_ni", TextUtil.NI_STATION);
        stationNames.put("m_nntnvls", TextUtil.NNTNVLS_STATION);
        stationNames.put("m_oobh", TextUtil.OOBH_STATION);
        stationNames.put("m_pd", TextUtil.PD_STATION);
        stationNames.put("m_pe", TextUtil.PE_STATION);
        stationNames.put("m_pvp", TextUtil.PVP_STATION);
        stationNames.put("m_pvp_", TextUtil.PVP__STATION);
        stationNames.put("m_s", TextUtil.S_STATION);
        stationNames.put("m_s_", TextUtil.S__STATION);
        stationNames.put("m_sk", TextUtil.SK_STATION);
        stationNames.put("m_tl", TextUtil.TL_STATION);
        stationNames.put("m_tl_", TextUtil.TL__STATION);
        stationNames.put("m_vg", TextUtil.VG_STATION);
        stationNames.put("m_vgz", TextUtil.VGZ_STATION);
        stationNames.put("m_zzl", TextUtil.ZZL_STATION);

        districtNames.put("zzl", TextUtil.ZZL_DISTRICT);
        districtNames.put("downtown", TextUtil.DOWNTOWN_DISTRICT);
        districtNames.put("govn", TextUtil.GOVN_DISTRICT);
        districtNames.put("vgz", TextUtil.VGZ_DISTRICT);
        districtNames.put("kd", TextUtil.KD_DISTRICT);
        districtNames.put("pe", TextUtil.PE_DISTRICT);
        districtNames.put("nc", TextUtil.NC_DISTRICT);
        districtNames.put("dv", TextUtil.DV_DISTRICT);
        districtNames.put("kr", TextUtil.KR_DISTRICT);
        districtNames.put("oobh", TextUtil.OOBH_DISTRICT);
        districtNames.put("nntnvls", TextUtil.NNTNVLS_DISTRICT);
        districtNames.put("sd", TextUtil.SD_DISTRICT);
        districtNames.put("nbn", TextUtil.NBN_DISTRICT);
        districtNames.put("ni", TextUtil.NI_DISTRICT);
        districtNames.put("pvp", TextUtil.PVP_DISTRICT);
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

    public static String getDistrict(List<ProtectedRegion> regions) {
        if (regions == null || regions.isEmpty()) return null;
        for (ProtectedRegion region : regions) {
            ProtectedRegion parent = region.getParent();
            if (parent != null && parent.getId().equals("district")) return region.getId();
        }
        return null;
    }

    public static Component getMetroStationName(String station) {
        return stationNames.get(station);
    }

    public static Component getDistrictName(String district) {
        return districtNames.get(district);
    }
}
