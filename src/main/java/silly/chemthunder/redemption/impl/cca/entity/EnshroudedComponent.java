package silly.chemthunder.redemption.impl.cca.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import silly.chemthunder.redemption.impl.Redemption;

public class EnshroudedComponent implements AutoSyncedComponent {
    public static final ComponentKey<EnshroudedComponent> KEY = ComponentRegistry.getOrCreate(Redemption.id("shrouded"), EnshroudedComponent.class);
    private final PlayerEntity player;

    private boolean shrouded = false;

    public EnshroudedComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.shrouded = nbt.getBoolean("Shrouded");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putBoolean("Shrouded", this.shrouded);
    }

    public boolean isShrouded() {
        return this.shrouded;
    }

    public void setShrouded(boolean shrouded) {
        this.shrouded = shrouded;
        this.sync();
    }
}
