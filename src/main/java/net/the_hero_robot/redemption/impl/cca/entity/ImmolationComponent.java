package net.the_hero_robot.redemption.impl.cca.entity;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.index.data.RedemptionDamageTypes;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public class ImmolationComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<ImmolationComponent> KEY = MiscUtils.getOrCreateKey(Redemption.id("immolation"), ImmolationComponent.class);
    private final LivingEntity living;

    private boolean burning = false;

    public ImmolationComponent(LivingEntity living) {
        this.living = living;
    }

    public void sync() {
        KEY.sync(living);
    }

    public void tick() {
        if (burning) {
            Vec3d pos = living.getPos();

            living.damage(RedemptionDamageTypes.create(living.getWorld(), RedemptionDamageTypes.IMMOLATION), 1.0f);

            if (living.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        ParticleTypes.FLAME,
                        pos.x + 0.5f, pos.y + 0.5f, pos.z + 0.5f,
                        6,
                        1, 2, 1,
                        0.1f
                );
            }
        }
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        burning = nbt.getBoolean("Burning");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putBoolean("Burning", burning);
    }

    public boolean isBurning() {
        return burning;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
        sync();
    }
}
