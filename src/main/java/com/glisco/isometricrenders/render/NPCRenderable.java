package com.glisco.isometricrenders.render;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.session.telemetry.TelemetrySender;
import net.minecraft.client.session.telemetry.WorldSession;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.server.ServerLinks;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Map;

public class NPCRenderable extends ClientPlayerEntity {

    protected SkinTextures skinTextures;

    protected NPCRenderable(GameProfile profile) {
        super(MinecraftClient.getInstance(),
                MinecraftClient.getInstance().world,
                new ClientPlayNetworkHandler(MinecraftClient.getInstance(),
                        new ClientConnection(NetworkSide.CLIENTBOUND),
                        new ClientConnectionState(
                                profile, new WorldSession(TelemetrySender.NOOP, false, Duration.ZERO, ""),
                                MinecraftClient.getInstance().world.getRegistryManager().toImmutable(),
                                MinecraftClient.getInstance().world.getEnabledFeatures(),
                                "Wisp Forest Enterprises", null, null, Map.of(), null, Map.of(), ServerLinks.EMPTY
                        )),
                null, null, false, false
        );

        this.skinTextures = DefaultSkinHelper.getSkinTextures(profile.getId());
        this.client.getSkinProvider().fetchSkinTextures(profile).thenAccept(texture -> {
            texture.ifPresent($ -> this.skinTextures = $);
        });
    }

    @Override
    public SkinTextures getSkinTextures() {
        return this.skinTextures;
    }

    @Override
    public boolean isPartVisible(PlayerModelPart modelPart) {
        return true;
    }

    @Nullable
    @Override
    protected PlayerListEntry getPlayerListEntry() {
        return null;
    }

    public static NPCRenderable createRenderablePlayer(GameProfile profile) {
        return new NPCRenderable(profile);
    }

}