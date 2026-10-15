package net.the_hero_robot.redemption.impl.networking;

import net.acoyt.acornlib.api.NetworkingInitializer;
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
    Impl IMPL = new Impl();

    static void registerCommon() {
        IMPL.registerTypes();
        IMPL.registerServerboundPackets();
    }

    @Environment(EnvType.CLIENT)
    static void registerClient() {
        IMPL.registerClientboundPackets();
    }

    class Impl implements NetworkingInitializer {
        public void registerTypes() {
            PayloadTypeRegistry.playC2S().register(IkirGameModeSwitchPayload.ID, IkirGameModeSwitchPayload.CODEC);
            PayloadTypeRegistry.playC2S().register(IkirImmolationPayload.ID, IkirImmolationPayload.CODEC);
        }

        public void registerServerboundPackets() {
            ServerPlayNetworking.registerGlobalReceiver(IkirGameModeSwitchPayload.ID, new IkirGameModeSwitchPayload.Receiver());
            ServerPlayNetworking.registerGlobalReceiver(IkirImmolationPayload.ID, new IkirImmolationPayload.Receiver());
        }

        @Environment(EnvType.CLIENT)
        public void registerClientboundPackets() {
            //
        }
    }
}
