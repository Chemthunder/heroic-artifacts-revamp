package net.the_hero_robot.redemption.impl.cca.entity;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import net.the_hero_robot.redemption.impl.Redemption;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public class ImmolationHolderComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<ImmolationHolderComponent> KEY = MiscUtils.getOrCreateKey(Redemption.id("immolation_holder"), ImmolationHolderComponent.class);
    private final PlayerEntity player;

    private int delayTicks = 0;

    public ImmolationHolderComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void tick() {
        if (delayTicks > 0) {
            delayTicks--;
            if (delayTicks == 0) {
                sync();
            }
        }
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        delayTicks = nbt.getInt("DelayTicks");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putInt("DelayTicks", delayTicks);
    }

    public int getDelayTicks() {
        return delayTicks;
    }

    public void setDelayTicks(int delayTicks) {
        this.delayTicks = delayTicks;
        sync();
    }
}
