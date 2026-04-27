package silly.chemthunder.redemption.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import silly.chemthunder.redemption.impl.cca.entity.EnshroudedComponent;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "spawnSprintingParticles", at = @At("HEAD"), cancellable = true)
    private void redemption$disableSprintingParticles(CallbackInfo ci) {
        EnshroudedComponent.KEY.maybeGet(this).ifPresent(component -> {
            if (component.isShrouded()) ci.cancel();
        });
    }
}
