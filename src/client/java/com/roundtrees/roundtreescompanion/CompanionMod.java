package com.roundtrees.roundtreescompanion;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class CompanionMod implements ClientModInitializer {    @Override
    public void onInitializeClient() {
        FabricLoader.getInstance().getModContainer("roundtreescompanion").ifPresent(modContainer -> {
            ResourceManagerHelper.registerBuiltinResourcePack(
                new Identifier("roundtreescompanion", "shifted_ladders"),
                modContainer,
                ResourcePackActivationType.DEFAULT_ENABLED
            );
        });
    }
}
