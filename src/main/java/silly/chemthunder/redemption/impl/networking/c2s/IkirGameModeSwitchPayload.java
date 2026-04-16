package silly.chemthunder.redemption.impl.networking.c2s;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import silly.chemthunder.redemption.impl.Redemption;
import silly.chemthunder.redemption.impl.util.IkirPowerManager;

public record IkirGameModeSwitchPayload() implements CustomPayload {
    public static final CustomPayload.Id<IkirGameModeSwitchPayload> ID = new CustomPayload.Id<>(Redemption.id("ikir_switch_gamemode"));
    public static final PacketCodec<RegistryByteBuf, IkirGameModeSwitchPayload> CODEC = PacketCodec.unit(new IkirGameModeSwitchPayload());

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send() {
        ClientPlayNetworking.send(new IkirGameModeSwitchPayload());
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<IkirGameModeSwitchPayload> {
        public void receive(IkirGameModeSwitchPayload payload, ServerPlayNetworking.Context context) {
            PlayerEntity player = context.player();
            if (player != null) {
                if (IkirPowerManager.isHero(player)) {
                    IkirPowerManager.switchGameMode(player);
                }
            }
        }
    }
}
