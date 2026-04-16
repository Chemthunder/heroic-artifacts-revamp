package silly.chemthunder.redemption.impl.cca.entity;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import silly.chemthunder.redemption.impl.Redemption;
import silly.chemthunder.redemption.impl.index.data.RedemptionDamageTypes;

public class ImmolationComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<ImmolationComponent> KEY = MiscUtils.getOrCreateKey(Redemption.id("immolation"), ImmolationComponent.class);
    private final LivingEntity living;

    private boolean burning = false;

    public ImmolationComponent(LivingEntity living) {
        this.living = living;
    }

    public void sync() {
        KEY.sync(this.living);
    }

    public void tick() {
        if (this.burning) {
            this.living.damage(RedemptionDamageTypes.create(this.living.getWorld(), RedemptionDamageTypes.IMMOLATION), 1.0f);

            if (this.living.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.FLAME,
                        this.living.getX() + 0.5f,
                        this.living.getY() + 0.5f,
                        this.living.getZ() + 0.5f,
                        6,
                        1,
                        2,
                        1,
                        0.1f
                );
            }
        }
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.burning = nbt.getBoolean("Burning");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putBoolean("Burning", this.burning);
    }

    public boolean isBurning() {
        return this.burning;
    }

    public void setBurning(boolean burning) {
        this.burning = burning;
        this.sync();
    }
}
