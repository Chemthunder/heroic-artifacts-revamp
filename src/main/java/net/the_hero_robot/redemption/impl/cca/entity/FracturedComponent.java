package net.the_hero_robot.redemption.impl.cca.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.the_hero_robot.redemption.impl.Redemption;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author AcoYT
 */
public class FracturedComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<FracturedComponent> KEY = ComponentRegistry.getOrCreate(Redemption.id("fractured"), FracturedComponent.class);
    private final PlayerEntity player;

    private int progress = -1;
    private static final String PROGRESS_KEY = "Progress";

    private int maximum = 0;
    private static final String MAXIMUM_KEY = "Maximum";

    public FracturedComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void tick() {
        if (progress < maximum) {
            progress++;
            if (progress == maximum) {
                progress = -1;
                maximum = -1;
                sync();
            }
        }
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        progress = nbt.getInt(PROGRESS_KEY);
        maximum = nbt.getInt(MAXIMUM_KEY);
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putInt(PROGRESS_KEY, progress);
        nbt.putInt(MAXIMUM_KEY, maximum);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        sync();
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
        sync();
    }
}
