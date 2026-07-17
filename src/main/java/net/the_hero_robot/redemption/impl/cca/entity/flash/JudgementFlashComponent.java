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
public class JudgementFlashComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<JudgementFlashComponent> KEY = ComponentRegistry.getOrCreate(Redemption.id("judge_flash"), JudgementFlashComponent.class);
    private final PlayerEntity player;

    private int flashTicks = 0;

    public JudgementFlashComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.flashTicks = nbt.getInt("FlashTicks");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putInt("FlashTicks", this.flashTicks);
    }

    public void tick() {
        if (this.flashTicks > 0) {
            this.flashTicks--;
            if (this.flashTicks == 0) {
                sync();
            }
        }
    }

    public int getFlashTicks() {
        return this.flashTicks;
    }

    public void setFlashTicks(int flashTicks) {
        this.flashTicks = flashTicks;
        sync();
    }
}
