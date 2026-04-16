package silly.chemthunder.redemption.impl.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import silly.chemthunder.redemption.impl.entity.ImmolationFlamesEntity;
import silly.chemthunder.redemption.impl.index.RedemptionEntities;

import java.util.UUID;

public class IkirPowerManager {
    public static void switchGameMode(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity serverPlayer) {
            if (serverPlayer.isCreative()) {
                serverPlayer.changeGameMode(GameMode.SURVIVAL);
            } else {
                serverPlayer.changeGameMode(GameMode.CREATIVE);
            }
        }
    }

    public static void useImmolation(PlayerEntity player) {
        ImmolationFlamesEntity flames = new ImmolationFlamesEntity(RedemptionEntities.IMMOLATION_FLAMES, player.getWorld());

        flames.setPosition(player.getX(), player.getEyeY() - 0.10000000149011612, player.getZ());
        flames.setVelocity(player, player.getPitch(), player.getHeadYaw(), 0.0f, 1.5f, 7.0f);

        player.getWorld().spawnEntity(flames);
    }

    public static boolean isHero(Entity entity) {
        return entity != null && entity.getUuid().equals(UUID.fromString("ba9c2526-bf12-4705-9051-5886e41aad0d"));
    }
}
