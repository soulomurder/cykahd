package glowapples.command;

import glowapples.util.EntityUtil;
import glowapples.util.TextUtil;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public final class ShopCommand implements BasicCommand {

    private final Component MERCHANT_NOT_FOUND_MESSAGE = TextUtil.mm.deserialize("<color:red>Не найдено ни одного торговца в пределах 10 метров.</color>");
    private final Component HELP_MESSAGE = TextUtil.mm.deserialize("""
            <gold><bold>=== РЕДАКТОР ТОРГОВЦА ===</bold></gold>
            <yellow>/shop add</yellow> <gray>- Добавить предмет из руки в торги (цена: 1 печенье)</gray>
            <yellow>/shop setprice <номер></yellow> <gray>- Изменить цену сделки</gray>
            <yellow>/shop setgood <номер></yellow> <gray>- Изменить продаваемый товар</gray>
            <yellow>/shop replace <номер> <номер></yellow> <gray>- Поменять две сделки местами</gray>
            <yellow>/shop remove <номер></yellow> <gray>- Удалить выбранную сделку</gray>
            <yellow>/shop close</yellow> <gray>- Полностью очистить все сделки торговца</gray>
            """);
    private final Component REPLACE_HELP_MESSAGE = TextUtil.mm.deserialize("""
            <red>Использование:</red> <yellow>/shop replace <номер_сделки_1> <номер_сделки_2></yellow>
            <gray>Позволяет поменять местами две указанные сделки в списке у торговца.</gray>
            """);
    private final Component SET_PRICE_HELP_MESSAGE = TextUtil.mm.deserialize("""
            <red>Использование:</red> <yellow>/shop setprice <номер_сделки></yellow>
            <gray>Устанавливает предмет из главной руки (и левой, если есть) как новую цену для этой сделки.</gray>
            """);
    private final Component SET_GOOD_HELP_MESSAGE = TextUtil.mm.deserialize("""
            <red>Использование:</red> <yellow>/shop setgood <номер_сделки></yellow>
            <gray>Заменяет финальный продаваемый товар в указанной сделке на предмет из вашей главной руки.</gray>
            """);
    private final Component REMOVE_HELP_MESSAGE = TextUtil.mm.deserialize("""
            <red>Использование:</red> <yellow>/shop remove <номер_сделки></yellow>
            <gray>Удаляет выбранную сделку у торговца.</gray>
            """);
    private final Component COMPLETE_MESSAGE = TextUtil.mm.deserialize("<color:green>Торги успешно обновлены. Изменённый троговец подсвечен на 5 секунд.</color>");
    private final Component INCORRECT_NUMBER_MESSAGE = TextUtil.mm.deserialize("<color:red>Введено некорректное число.</color>");
    private final Component ERROR_MESSAGE = TextUtil.mm.deserialize("<color:red>Произошла непредвиденная ошибка.</color>");
    private final PotionEffect GLOWING_EFFECT = new PotionEffect(PotionEffectType.GLOWING, 100, 0, false, false);
    private String[] args;
    private Player player;
    private Merchant merchant;

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (commandSourceStack.getSender() instanceof Player thePlayer) player = thePlayer;
        else return;
        if (!thePlayer.hasPermission(TextUtil.PERM_COMMAND_SHOP)) {
            player.sendMessage(TextUtil.COMMAND_REFUSE_MESSAGE);
            return;
        }

        merchant = EntityUtil.getNearestEntity(thePlayer.getLocation(), 10, Merchant.class);
        if (merchant == null) {
            thePlayer.sendMessage(MERCHANT_NOT_FOUND_MESSAGE);
            return;
        }
        this.args = args;

        switch (args.length) {
            case 1 -> oneArg();
            case 2 -> twoArgs();
            case 3 -> threeArgs();
            default -> thePlayer.sendMessage(HELP_MESSAGE);
        }
    }

    @Override
    public java.util.Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        if (args.length == 1) return List.of("add", "close", "setprice", "setgood", "replace", "remove");
        return List.of();
    }
// 1
    private void oneArg() {
        switch (args[0]) {
            case "add" -> add();
            case "close" -> close();
            case "remove" -> player.sendMessage(REMOVE_HELP_MESSAGE);
            case "setprice" -> player.sendMessage(SET_PRICE_HELP_MESSAGE);
            case "set" -> player.sendMessage(SET_GOOD_HELP_MESSAGE);
            case "replace" -> player.sendMessage(REPLACE_HELP_MESSAGE);
            default -> player.sendMessage(HELP_MESSAGE);
        }
    }

    private void add() {
        ItemStack item = new ItemStack(player.getInventory().getItemInMainHand());
        if (item.getType() == Material.AIR) {
            player.sendMessage(HELP_MESSAGE);
            return;
        }
        MerchantRecipe newRecipe = new MerchantRecipe(item, 0, 999999, false);
        newRecipe.addIngredient(new ItemStack(Material.COOKIE));

        List<MerchantRecipe> recipes = new ArrayList<>(merchant.getRecipes());
        recipes.add(newRecipe);
        merchant.setRecipes(recipes);

        complete();
    }

    private void close() {
        List<MerchantRecipe> recipes = new ArrayList<>();
        merchant.setRecipes(recipes);

        complete();
    }
// 2
    private void twoArgs() {
        int arg1;
        try {
            arg1 = Integer.parseInt(args[1]);
        } catch (Exception e) {
            player.sendMessage(INCORRECT_NUMBER_MESSAGE);
            return;
        }
        switch (args[0]) {
            case "setprice" -> setPrice(arg1);
            case "setgood" -> setGood(arg1);
            case "remove" -> remove(arg1);
            case "replace" -> player.sendMessage(REPLACE_HELP_MESSAGE);
            default -> player.sendMessage(HELP_MESSAGE);
        }
    }

    private MerchantRecipe getRecipe(List<MerchantRecipe> recipes, int n) {
        if (n-- < 1 || n >= recipes.size()) return null;
        return recipes.get(n);
    }

    private void setPrice(int n) {
        ItemStack firstIngredient = new ItemStack(player.getInventory().getItemInMainHand());
        if (firstIngredient.getType() == Material.AIR) {
            player.sendMessage(SET_PRICE_HELP_MESSAGE);
            return;
        }
        ItemStack secondIngredient = new ItemStack(player.getInventory().getItemInOffHand());

        List<MerchantRecipe> recipes = new ArrayList<>(merchant.getRecipes());
        MerchantRecipe recipe = getRecipe(recipes, n);
        if (recipe == null) {
            player.sendMessage(INCORRECT_NUMBER_MESSAGE);
            return;
        }

        if (secondIngredient.isEmpty()) recipe.setIngredients(List.of(firstIngredient));
        else recipe.setIngredients(List.of(firstIngredient, secondIngredient));
        merchant.setRecipes(recipes);

        complete();
    }

    private void setGood(int n) {
        ItemStack item = new ItemStack(player.getInventory().getItemInMainHand());
        if (item.getType() == Material.AIR) {
            player.sendMessage(SET_GOOD_HELP_MESSAGE);
            return;
        }

        List<MerchantRecipe> recipes = new ArrayList<>(merchant.getRecipes());
        MerchantRecipe recipe = getRecipe(recipes, n);
        if (recipe == null) {
            player.sendMessage(INCORRECT_NUMBER_MESSAGE);
            return;
        }

        MerchantRecipe newRecipe = new MerchantRecipe(item, 0, 999999, false);
        newRecipe.setIngredients(recipe.getIngredients());
        recipes.set(--n, newRecipe);
        merchant.setRecipes(recipes);

        complete();
    }

    private void remove(int n) {
        List<MerchantRecipe> recipes = new ArrayList<>(merchant.getRecipes());
        MerchantRecipe recipe = getRecipe(recipes, n);
        if (recipe == null) {
            player.sendMessage(INCORRECT_NUMBER_MESSAGE);
            return;
        }

        recipes.remove(--n);
        merchant.setRecipes(recipes);

        complete();
    }
// 3
    private void threeArgs() {
        if (!args[0].equals("replace")) {
            player.sendMessage(HELP_MESSAGE);
            return;
        }

        int arg1, arg2;
        try {
            arg1 = Integer.parseInt(args[1]);
            arg2 = Integer.parseInt(args[2]);
        } catch (Exception e) {
            player.sendMessage(INCORRECT_NUMBER_MESSAGE);
            return;
        }

        replace(arg1, arg2);
    }

    private void replace(int n1, int n2) {
        List<MerchantRecipe> recipes = new ArrayList<>(merchant.getRecipes());
        MerchantRecipe recipe1 = getRecipe(recipes, n1);
        if (recipe1 == null) { player.sendMessage(INCORRECT_NUMBER_MESSAGE); return; }
        MerchantRecipe recipe2 = getRecipe(recipes, n2);
        if (recipe2 == null) { player.sendMessage(INCORRECT_NUMBER_MESSAGE); return; }

        MerchantRecipe newRecipe1 = new MerchantRecipe(recipe1.getResult(), recipe1.getUses(), recipe1.getMaxUses(), false, recipe1.getVillagerExperience(), recipe1.getPriceMultiplier());
        newRecipe1.setIngredients(recipe1.getIngredients());

        MerchantRecipe newRecipe2 = new MerchantRecipe(recipe2.getResult(), recipe2.getUses(), recipe2.getMaxUses(), false, recipe2.getVillagerExperience(), recipe2.getPriceMultiplier());
        newRecipe2.setIngredients(recipe2.getIngredients());

        recipes.set(--n1, newRecipe2);
        recipes.set(--n2, newRecipe1);
        merchant.setRecipes(recipes);

        complete();
    }


    private void complete() {
        if (merchant instanceof LivingEntity trader) {
            trader.addPotionEffect(GLOWING_EFFECT);
            player.sendMessage(COMPLETE_MESSAGE);
        }
        else player.sendMessage(ERROR_MESSAGE);
    }
}
