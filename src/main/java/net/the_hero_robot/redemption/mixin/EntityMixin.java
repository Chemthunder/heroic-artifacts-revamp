package net.the_hero_robot.redemption.mixin;

import net.minecraft.entity.Entity;
import net.the_hero_robot.redemption.impl.cca.entity.EnshroudedComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author AcoYT
 * @author Chemthunder
 */
@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "spawnSprintingParticles", at = @At("HEAD"), cancellable = true)
    private void redemption$disableSprintingParticles(CallbackInfo ci) {
        EnshroudedComponent.KEY.maybeGet(this).ifPresent(component -> {
            if (component.isShrouded()) ci.cancel();
        });
    }
}
