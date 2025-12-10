package silly.chemthunder.redemption.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import silly.chemthunder.redemption.cca.EnshroudedPlayerComponent;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "spawnSprintingParticles", at = @At("HEAD"), cancellable = true)
    private void disableSprintingParticles(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;

        if (entity instanceof PlayerEntity player) {
            if (EnshroudedPlayerComponent.KEY.get(player).isShrouded) {
                ci.cancel();
            }
        }
    }
}
