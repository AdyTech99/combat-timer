package com.adytechmc.combattimer.events;

import com.adytechmc.combattimer.CombatHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ToolItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ModEventsHandler {

    public static ActionResult CombatEvent(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        if(entity instanceof PlayerEntity && !(player.getStackInHand(hand).getItem() instanceof ToolItem)){
            CombatEventHandler(player, (PlayerEntity) entity);
        }
        return null;
    }

    public static void CombatEventHandler(PlayerEntity attacker, PlayerEntity victim){
        CombatHandler.addToCombat(attacker);
        CombatHandler.addToCombat(victim);
    }

    public static void TickEvent(MinecraftServer server) {
        if(server.getTicks() % 20 == 0) CombatHandler.tick();
    }

    public static void DisconnectEventHandler(ServerPlayNetworkHandler serverPlayNetworkHandler, MinecraftServer server) {
        if(CombatHandler.isInCombat(serverPlayNetworkHandler.getPlayer())) serverPlayNetworkHandler.player.kill();
        CombatHandler.removeFromCombat(serverPlayNetworkHandler.getPlayer());
    }
}
