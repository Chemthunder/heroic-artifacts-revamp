package net.the_hero_robot.redemption.impl.networking.c2s;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.util.IkirPowerManager;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public record IkirImmolationPayload() implements CustomPayload {
    public static final Id<IkirImmolationPayload> ID = new Id<>(Redemption.id("ikir_immolation"));

    public static final PacketCodec<RegistryByteBuf, IkirImmolationPayload> CODEC = PacketCodec.unit(new IkirImmolationPayload());

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<IkirImmolationPayload> {
        public void receive(IkirImmolationPayload payload, ServerPlayNetworking.Context context) {
            ServerPlayerEntity player = context.player();
            if (IkirPowerManager.isHero(player)) {
                IkirPowerManager.useImmolation(player);
            }
        }
    }
}
