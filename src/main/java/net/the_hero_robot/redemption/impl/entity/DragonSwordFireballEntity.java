package net.the_hero_robot.redemption.impl.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.the_hero_robot.redemption.impl.index.RNEntities;

/**
 * @author AcoYT
 */
public class DragonSwordFireballEntity extends FireballEntity {
    public DragonSwordFireballEntity(EntityType<? extends FireballEntity> entityType, World world) {
        super(entityType, world);
    }

    public DragonSwordFireballEntity(World world, LivingEntity owner, Vec3d velocity, int explosionPower) {
        super(RNEntities.DRAGON_SWORD_FIREBALL, world);
        this.refreshPositionAndAngles(owner.getX(), owner.getY(), owner.getZ(), this.getYaw(), this.getPitch());
        this.refreshPosition();
        this.setVelocityWithAcceleration(velocity, this.accelerationPower);
        this.setOwner(owner);
        this.setRotation(owner.getYaw(), owner.getPitch());
        this.explosionPower = explosionPower;
    }

    public float getKnockbackAgainst(Entity entity) {
        if (entity == null || this.getOwner() == null) return 0.0F;
        return this.isOwner(entity) ? 0.075F : 0.0F;
    }

    public static class SwordExplosionBehavior extends ExplosionBehavior {
        private final DragonSwordFireballEntity swordFireball;

        public SwordExplosionBehavior(DragonSwordFireballEntity swordFireball) {
            this.swordFireball = swordFireball;
        }

        public float calculateDamage(Explosion explosion, Entity entity) {
            return super.calculateDamage(explosion, entity) * (swordFireball.isOwner(entity) ? 0.3F : 1.0F);
        }
    }
}
