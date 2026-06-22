package net.the_hero_robot.redemption.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion;
import net.the_hero_robot.redemption.impl.entity.DragonSwordFireballEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(Explosion.class)
public abstract class ExplosionMixin {
    @Shadow public abstract Vec3d getPosition();

    @WrapOperation(
            method = "collectBlocksAndDamageEntities",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
            )
    )
    private boolean redemption$applyVelocityIfOwner(Entity instance, DamageSource source, float amount, Operation<Boolean> original) {
        boolean value = original.call(instance, source, amount);
        if (source.getSource() instanceof DragonSwordFireballEntity swordFireball && swordFireball.getKnockbackAgainst(instance) > 0.0F) {
            Vec3d pos = this.getPosition();
            float multiplier = amount * -swordFireball.getKnockbackAgainst(instance);

            if (instance instanceof LivingEntity target) {
                target.setVelocity(pos.subtract(target.getPos()).multiply(multiplier));
                target.velocityModified = true;
                if (target instanceof PlayerEntity player) {
                    player.currentExplosionImpactPos = this.getPosition();
                }
            }
        }

        return value;
    }
}
