package silly.chemthunder.redemption.cca;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;
import silly.chemthunder.redemption.Redemption;

public class JudgementFlashComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<JudgementFlashComponent> KEY = ComponentRegistry.getOrCreate(Redemption.id("flash"), JudgementFlashComponent.class);
    private final LivingEntity player;
    public int flashTicks = 0;

    public JudgementFlashComponent(LivingEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.flashTicks = nbtCompound.getInt("flashTicks");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("flashTicks", flashTicks);
    }

    @Override
    public void tick() {
        if (flashTicks > 0) {
            flashTicks--;
            if (flashTicks == 0) {
                sync();
            }
        }
    }
}
