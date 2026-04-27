package silly.chemthunder.redemption.impl.cca.entity.flash;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import silly.chemthunder.redemption.impl.Redemption;

public class JudgementFlashComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<JudgementFlashComponent> KEY = ComponentRegistry.getOrCreate(Redemption.id("judge_flash"), JudgementFlashComponent.class);
    private final LivingEntity living;

    private int flashTicks = 0;

    public JudgementFlashComponent(LivingEntity living) {
        this.living = living;
    }

    public void sync() {
        KEY.sync(this.living);
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
                this.sync();
            }
        }
    }

    public int getFlashTicks() {
        return this.flashTicks;
    }

    public void setFlashTicks(int flashTicks) {
        this.flashTicks = flashTicks;
        this.sync();
    }
}
