package silly.chemthunder.redemption.impl.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import silly.chemthunder.redemption.impl.index.RedemptionEntities;

public class ImmolationFlamesEntity extends ThrownEntity {
    private final int maxAge = 300;

    public ImmolationFlamesEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    public ImmolationFlamesEntity(World world) {
        super(RedemptionEntities.IMMOLATION_FLAMES, world);
    }

    public void initDataTracker(DataTracker.Builder builder) {}

    public void tick() {
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

        super.tick();
    }

    public boolean hasNoGravity() {
        return true;
    }
}
