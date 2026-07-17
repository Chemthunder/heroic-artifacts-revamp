package net.the_hero_robot.redemption.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.World;
import net.minecraft.world.World.ExplosionSourceType;
import net.minecraft.world.explosion.Explosion;
import net.the_hero_robot.redemption.impl.entity.DragonSwordFireballEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(FireballEntity.class)
public abstract class FireballEntityMixin extends AbstractFireballEntity {
    public FireballEntityMixin(EntityType<? extends AbstractFireballEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapOperation(
            method = "onCollision",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"
            )
    )
    private Explosion redemption$allowFireballJumping(World instance, Entity entity, double x, double y, double z, float power, boolean createFire, ExplosionSourceType explosionSourceType, Operation<Explosion> original) {
        if ((FireballEntity) (Object) this instanceof DragonSwordFireballEntity swordFireball) {
            return instance.createExplosion(
                    entity,
                    Explosion.createDamageSource(instance, entity),
                    new DragonSwordFireballEntity.SwordExplosionBehavior(swordFireball),
                    x, y, z,
                    power,
                    false,
                    ExplosionSourceType.NONE
            );
        }

        return original.call(instance, entity, x, y, z, power, createFire, explosionSourceType);
    }
}
