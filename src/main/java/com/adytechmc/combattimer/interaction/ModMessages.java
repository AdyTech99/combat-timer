package com.adytechmc.combattimer.interaction;

import com.adytechmc.combattimer.config.ModConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ModMessages {
    public static void inCombatMessage(PlayerEntity player){
        player.sendMessage(Text.literal(ModConfig.HANDLER.instance().combat_tagged_message).formatted(Formatting.RED));
    }

    public static void outOfCombatMessage(PlayerEntity player){
        player.sendMessage(Text.literal(ModConfig.HANDLER.instance().no_more_combat_tagged_message).formatted(Formatting.GREEN));
    }
}
