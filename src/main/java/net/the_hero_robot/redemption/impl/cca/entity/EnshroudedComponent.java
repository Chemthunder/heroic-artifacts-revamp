package net.the_hero_robot.redemption.impl.cca.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.the_hero_robot.redemption.impl.Redemption;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public class EnshroudedComponent implements AutoSyncedComponent {
    public static final ComponentKey<EnshroudedComponent> KEY = ComponentRegistry.getOrCreate(Redemption.id("shrouded"), EnshroudedComponent.class);
    private final PlayerEntity player;

    private boolean shrouded = false;

    public EnshroudedComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        shrouded = nbt.getBoolean("Shrouded");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putBoolean("Shrouded", shrouded);
    }

    public boolean isShrouded() {
        return shrouded;
    }

    public void setShrouded(boolean shrouded) {
        this.shrouded = shrouded;
        sync();
    }
}
