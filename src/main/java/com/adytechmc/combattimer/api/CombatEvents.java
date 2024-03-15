package com.adytechmc.combattimer.api;

import com.adytechmc.combattimer.CombatHandler;
import net.minecraft.entity.player.PlayerEntity;

public class CombatEvents {
    private void addToCombat(PlayerEntity player){
        CombatHandler.addToCombat(player);
    }

}
