package silly.chemthunder.redemption.cca;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import silly.chemthunder.redemption.Redemption;

public class EnshroudedPlayerComponent implements AutoSyncedComponent {
    public static final ComponentKey<EnshroudedPlayerComponent> KEY = ComponentRegistry.getOrCreate(Redemption.id("shrouded"), EnshroudedPlayerComponent.class);
    private final PlayerEntity player;
    public boolean isShrouded = false;

    public EnshroudedPlayerComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }


    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.isShrouded = nbtCompound.getBoolean("isShrouded");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putBoolean("isShrouded", isShrouded);
    }
}
