package net.the_hero_robot.arcane_kings.impl.networking;

import net.acoyt.acornlib.api.NetworkingInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.the_hero_robot.arcane_kings.impl.networking.c2s.SetVertigoTargetPayload;

/**
 * @author AcoYT
 */
public interface AKNetworking {
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
            PayloadTypeRegistry.playC2S().register(SetVertigoTargetPayload.ID, SetVertigoTargetPayload.CODEC);
        }

        public void registerServerboundPackets() {
            ServerPlayNetworking.registerGlobalReceiver(SetVertigoTargetPayload.ID, new SetVertigoTargetPayload.Receiver());
        }

        @Environment(EnvType.CLIENT)
        public void registerClientboundPackets() {
            //
        }
    }
}
