package net.the_hero_robot.redemption.impl.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.the_hero_robot.redemption.impl.networking.c2s.IkirGameModeSwitchPayload;
import net.the_hero_robot.redemption.impl.networking.c2s.IkirImmolationPayload;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public interface RedemptionNetworking {
    static void registerTypes() {
        PayloadTypeRegistry.playC2S().register(IkirGameModeSwitchPayload.ID, IkirGameModeSwitchPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(IkirImmolationPayload.ID, IkirImmolationPayload.CODEC);
    }

    static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(IkirGameModeSwitchPayload.ID, new IkirGameModeSwitchPayload.Receiver());
        ServerPlayNetworking.registerGlobalReceiver(IkirImmolationPayload.ID, new IkirImmolationPayload.Receiver());
    }

    @Environment(EnvType.CLIENT)
    static void registerS2CPackets() {
        //
    }
}
