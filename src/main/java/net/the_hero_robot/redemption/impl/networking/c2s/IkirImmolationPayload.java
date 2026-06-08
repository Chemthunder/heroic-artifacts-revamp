package net.the_hero_robot.redemption.impl.networking.c2s;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.util.IkirPowerManager;

public record IkirImmolationPayload() implements CustomPayload {
    public static final Id<IkirImmolationPayload> ID = new Id<>(Redemption.id("ikir_immolation"));
    public static final PacketCodec<RegistryByteBuf, IkirImmolationPayload> CODEC = PacketCodec.unit(new IkirImmolationPayload());

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send() {
        ClientPlayNetworking.send(new IkirGameModeSwitchPayload());
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<IkirImmolationPayload> {
        public void receive(IkirImmolationPayload payload, ServerPlayNetworking.Context context) {
            PlayerEntity player = context.player();
            if (player != null) {
                if (IkirPowerManager.isHero(player)) {
                    IkirPowerManager.useImmolation(player);
                }
            }
        }
    }
}
