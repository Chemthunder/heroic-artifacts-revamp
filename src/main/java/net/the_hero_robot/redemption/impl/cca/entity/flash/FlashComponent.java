package net.the_hero_robot.redemption.impl.cca.entity.flash;

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
 * @author Chemthunder
 */
public class FlashComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<FlashComponent> KEY = ComponentRegistry.getOrCreate(Redemption.id("flash"), FlashComponent.class);
    private final PlayerEntity player;

    private int flashTicks = 0;

    public FlashComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        flashTicks = nbt.getInt("FlashTicks");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putInt("FlashTicks", flashTicks);
    }

    public void tick() {
        if (flashTicks > 0) {
            flashTicks--;
            if (flashTicks == 0) {
                sync();
            }
        }
    }

    public int getFlashTicks() {
        return flashTicks;
    }

    public void setFlashTicks(int flashTicks) {
        this.flashTicks = flashTicks;
        sync();
    }
}
