package net.the_hero_robot.redemption.impl.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameMode;
import net.the_hero_robot.redemption.impl.entity.ImmolationFlamesEntity;
import net.the_hero_robot.redemption.impl.index.RedemptionEntities;

import java.util.UUID;

public class IkirPowerManager {
    public static void switchGameMode(ServerPlayerEntity player) {
        player.changeGameMode(player.isCreative() ? GameMode.SURVIVAL : GameMode.CREATIVE);
    }

    public static void useImmolation(PlayerEntity player) {
        ImmolationFlamesEntity flames = new ImmolationFlamesEntity(RedemptionEntities.IMMOLATION_FLAMES, player.getWorld());

        flames.setPosition(player.getX(), player.getEyeY() - 0.10000000149011612, player.getZ());
        flames.setVelocity(player, player.getPitch(), player.getHeadYaw(), 0.0f, 1.5f, 7.0f);

        player.getWorld().spawnEntity(flames);
    }

    public static boolean isHero(Entity entity) {
        if (entity == null) return false;
        return entity.getUuid().equals(UUID.fromString("ba9c2526-bf12-4705-9051-5886e41aad0d"));
    }
}
