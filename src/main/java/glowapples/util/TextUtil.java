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
    public static final String PERM_COMMAND_P = "cykahd.command.p";
    // Keys
    public static final NamespacedKey CIG_KEY = new NamespacedKey(CykaHD.getInstance(), "cig");
    public static final NamespacedKey STAR_KEY = new NamespacedKey(CykaHD.getInstance(), "star");
    // Messages
    public static final Component COMMAND_REFUSE_MESSAGE = mm.deserialize("<red>У тебя недостаточно прав для использования этой команды.</red>");
    // Metro
    public static final Component AN_STATION = mm.deserialize("<gray>Станция </gray><gold>Агентство недвижимости</gold>");
    public static final Component DOWNTOWN_STATION = mm.deserialize("<gray>Станция </gray><green>Верхний город</green>");
    public static final Component DV_STATION = mm.deserialize("<gray>Станция </gray><green>Долина времени</green>");
    public static final Component FFI_STATION = mm.deserialize("<gray>Станция </gray><gold>Фуфелшмерц Фарма inc.</gold>");
    public static final Component GOVN_STATION = mm.deserialize("<gray>Станция </gray><red>Грибной овраг водоструйного насоса</red>");
    public static final Component HOM_STATION = mm.deserialize("<gray>Станция </gray><gold>House of memories</gold>");
    public static final Component HOM__STATION = mm.deserialize("<gray>Станция </gray><aqua>House of memories</aqua>");
    public static final Component K_STATION = mm.deserialize("<gray>Станция </gray><gold>Кикомару</gold>");
    public static final Component KD_STATION = mm.deserialize("<gray>Станция </gray><gold>Конвейерная деревня</gold>");
    public static final Component KI_STATION = mm.deserialize("<gray>Станция </gray><green>Купол игроков<green>");
    public static final Component KR_STATION = mm.deserialize("<gray>Станция </gray><red>Конец радуги</red>");
    public static final Component KR__STATION = mm.deserialize("<gray>Станция </gray><green>Конец радуги</green>");
    public static final Component M_STATION = mm.deserialize("<gray>Станция </gray><aqua>Монополия</aqua>");
    public static final Component NC_STATION = mm.deserialize("<gray>Станция </gray><gold>Набережные члены</gold>");
    public static final Component NI_STATION = mm.deserialize("<gray>Станция </gray><green>Нищая интеллигенция</green>");
    public static final Component NNTNVLS_STATION = mm.deserialize("<gray>Станция </gray><green>Ну нихуя ты настя в лагерь съездила</green>");
    public static final Component OOBH_STATION = mm.deserialize("<gray>Станция </gray><red>Университет ОГБ</red>");
    public static final Component PD_STATION = mm.deserialize("<gray>Станция </gray><gold>Пятая деревня</gold>");
    public static final Component PE_STATION = mm.deserialize("<gray>Станция </gray><red>Пизда Елены</red>");
    public static final Component PVP_STATION = mm.deserialize("<gray>Станция </gray><red>Парк вредных привычек</red>");
    public static final Component PVP__STATION = mm.deserialize("<gray>Станция </gray><green>Парк вредных привычек</green>");
    public static final Component S_STATION = mm.deserialize("<gray>Станция </gray><green>Сплиф</green>");
    public static final Component S__STATION = mm.deserialize("<gray>Станция </gray><aqua>Сплиф</aqua>");
    public static final Component SK_STATION = mm.deserialize("<gray>Станция </gray><aqua>Смешная кошка</aqua>");
    public static final Component TL_STATION = mm.deserialize("<gray>Станция </gray><red>Тёмный лес</red>");
    public static final Component TL__STATION = mm.deserialize("<gray>Станция </gray><gold>Тёмный лес</gold>");
    public static final Component VG_STATION = mm.deserialize("<gray>Станция </gray><green>Врата города</green>");
    public static final Component VGZ_STATION = mm.deserialize("<gray>Станция </gray><green>В Германии жарко</green>");
    public static final Component ZZL_STATION = mm.deserialize("<gray>Станция </gray><gold>Замкадье заходящей луны</gold>");
    // REGIONS
    public static final Component ZZL_DISTRICT = mm.deserialize("<green>Замкадье заходящей луны</green>");
    public static final Component DOWNTOWN_DISTRICT = mm.deserialize("<green>Верхний город</green>");
    public static final Component GOVN_DISTRICT = mm.deserialize("<green>Грибной овраг водоструйного насоса</green>");
    public static final Component VGZ_DISTRICT = mm.deserialize("<green>В германии жарко</green>");
    public static final Component KD_DISTRICT = mm.deserialize("<green>Конвейерная деревня</green>");
    public static final Component PE_DISTRICT = mm.deserialize("<green>Пизда Елены</green>");
    public static final Component NC_DISTRICT = mm.deserialize("<green>Набережные члены</green>");
    public static final Component DV_DISTRICT = mm.deserialize("<green>Долина времени</green>");
    public static final Component KR_DISTRICT = mm.deserialize("<green>Конец радуги</green>");
    public static final Component OOBH_DISTRICT = mm.deserialize("<green>Университет ОГБ</green>");
    public static final Component NNTNVLS_DISTRICT = mm.deserialize("<green>Ну нихуя ты настя в лагерь съездила</green>");
    public static final Component SD_DISTRICT = mm.deserialize("<green>Spiral Drive</green>");
    public static final Component NBN_DISTRICT = mm.deserialize("<green>Неизвестен - Без названия</green>");
    public static final Component NI_DISTRICT = mm.deserialize("<green>Нищая интеллигенция</green>");
    public static final Component PVP_DISTRICT = mm.deserialize("<green>Парк вредных привычек</green>");
}
