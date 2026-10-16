package net.the_hero_robot.arcane_kings.impl.networking.c2s;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.the_hero_robot.arcane_kings.impl.cca.entity.PetrificationComponent;
import net.the_hero_robot.redemption.impl.Redemption;

/**
 * @author AcoYT
 */
public record SetVertigoTargetPayload(int targetId) implements CustomPayload {
    public static final Id<SetVertigoTargetPayload> ID = new Id<>(Redemption.id("set_vertigo_target"));

    public static final PacketCodec<RegistryByteBuf, SetVertigoTargetPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, SetVertigoTargetPayload::targetId,
            SetVertigoTargetPayload::new
    );

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SetVertigoTargetPayload> {
        public void receive(SetVertigoTargetPayload payload, ServerPlayNetworking.Context context) {
            ServerPlayerEntity player = context.player();
            ServerWorld world = player.getServerWorld();

            Entity entity = world.getEntityById(payload.targetId);
            if (entity instanceof ServerPlayerEntity target) {
                PetrificationComponent.KEY.get(target).setPetrifying(true);
            }
        }
    }
}
