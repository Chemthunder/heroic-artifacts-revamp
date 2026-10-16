package net.the_hero_robot.arcane_kings.impl.cca.entity;

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
public class PetrificationComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<PetrificationComponent> KEY = ComponentRegistry.getOrCreate(Redemption.id("petrification"), PetrificationComponent.class);
    private final PlayerEntity player;

    private boolean petrifying = false;
    private static final String PETRIFYING_KEY = "Petrifying";

    private int timePetrifying = 0;
    private static final String TIME_PETRIFYING_KEY = "TimePetrifying";

    public PetrificationComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void tick() {
        if (petrifying) {
            timePetrifying++;
            if (timePetrifying % 20 == 0) {
                sync();
                Redemption.LOGGER.info("Progress: {}", timePetrifying);
            }
        } else {
            if (timePetrifying > 0) {
                timePetrifying = 0;
                sync();
            }
        }
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        petrifying = nbt.getBoolean(PETRIFYING_KEY);
        timePetrifying = nbt.getInt(TIME_PETRIFYING_KEY);
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putBoolean(PETRIFYING_KEY, petrifying);
        nbt.putInt(TIME_PETRIFYING_KEY, timePetrifying);
    }

    public boolean isPetrifying() {
        return petrifying;
    }

    public void setPetrifying(boolean petrifying) {
        this.petrifying = petrifying;
        sync();
    }

    public int getTimePetrifying() {
        return timePetrifying;
    }

    public void setTimePetrifying(int timePetrifying) {
        this.timePetrifying = timePetrifying;
        sync();
    }
}
