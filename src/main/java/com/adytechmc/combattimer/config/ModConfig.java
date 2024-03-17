package com.adytechmc.combattimer.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.ConfigEntry;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class ModConfig {
    public static final ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("CombatTimerConfig.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public int timer_length_in_seconds = 15;

    @SerialEntry
    public String combat_tagged_message = "[CombatTimer] You have been combat tagged! Do not log off!";

    @SerialEntry
    public String no_more_combat_tagged_message = "[CombatTimer] Combat Tag has expired. You may log off.";

    @SerialEntry(comment = "If someone punches someone using their fist (no tool/weapon), should they be combat tagged?")
    public boolean allow_friendly_fire = true;

}
