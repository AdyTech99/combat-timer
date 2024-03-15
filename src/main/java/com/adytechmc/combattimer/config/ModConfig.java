package com.adytechmc.combattimer.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;

public class ModConfig {
    public static final ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("CombatTimerConfig.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry()
    public final int timer_length_in_seconds = 15;

    @SerialEntry
    public final String combat_tagged_message = "[CombatTimer] You have been combat tagged! Do not log off!";

    @SerialEntry
    public final String no_more_combat_tagged_message = "[CombatTimer] Combat Tag has expired You may log off.";

    @SerialEntry("If someone punches someone, should they be combat tagged?")
    public final boolean combat_tag_if_punched = true;
}
