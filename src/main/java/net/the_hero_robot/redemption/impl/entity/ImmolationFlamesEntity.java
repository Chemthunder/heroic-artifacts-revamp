package net.the_hero_robot.redemption.impl.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.the_hero_robot.redemption.impl.index.RedemptionEntities;
import net.the_hero_robot.redemption.impl.index.data.RedemptionDamageTypes;
import org.jetbrains.annotations.Nullable;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public class ImmolationFlamesEntity extends ThrownEntity {
    private static final int MAX_AGE = 300;

    public ImmolationFlamesEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    public ImmolationFlamesEntity(World world) {
        super(RedemptionEntities.IMMOLATION_FLAMES, world);
    }

    public ImmolationFlamesEntity(LivingEntity owner, World world) {
        super(RedemptionEntities.IMMOLATION_FLAMES, owner, world);
    }

    public void initDataTracker(DataTracker.Builder builder) {}

    public void tick() {
        super.tick();

        if (this.getWorld() instanceof ServerWorld serverWorld) {
            for (int i = 0; i < 3; i++) {
                serverWorld.spawnParticles(ParticleTypes.FLAME,
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        5,
                        0.3,
                        0.3,
                        0.3,
                        0
                );
            }
        }

        for (PlayerEntity player : this.getWorld().getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(0.2), player -> EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(player) && !isOwner(player))) {
            player.damage(RedemptionDamageTypes.create(player.getWorld(), RedemptionDamageTypes.IMMOLATION, this.getOwner()), Float.MAX_VALUE);
            this.discard();
            break;
        }

        if (this.age > MAX_AGE) {
            this.discard();
        }
    }

    public boolean hasNoGravity() {
        return true;
    }

    public boolean canUsePortals(boolean allowVehicles) {
        return false;
    }

    public boolean deflect(ProjectileDeflection deflection, @Nullable Entity deflector, @Nullable Entity owner, boolean fromAttack) {
        return false;
    }

    public ProjectileDeflection hitOrDeflect(HitResult hitResult) {
        this.onCollision(hitResult);
        return ProjectileDeflection.NONE;
    }
}
