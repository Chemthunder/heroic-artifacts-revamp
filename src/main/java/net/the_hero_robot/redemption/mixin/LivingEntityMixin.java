package net.the_hero_robot.redemption.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.the_hero_robot.redemption.impl.cca.entity.JudgementComponent;
import net.the_hero_robot.redemption.impl.index.data.RNDamageTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author AcoYT
 * @author Chemthunder
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tryUseTotem", at = @At("RETURN"), cancellable = true)
    private void redemption$deathEffect(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living = (LivingEntity)(Object)this;

        if (living instanceof PlayerEntity player) {
            JudgementComponent judge = JudgementComponent.KEY.get(player);

            if (JudgementComponent.isJudgement(player) && !cir.getReturnValue()) {
                if (!source.isOf(RNDamageTypes.DESCEND)) {
                    player.setHealth(player.getMaxHealth());
                    player.setVelocity(0, 0.3, 0);
                    player.velocityModified = true;
                    player.setNoGravity(true);
                    player.setInvulnerable(true);
                    player.noClip = true;

                    judge.setMonologueTicks(200);
                    cir.setReturnValue(true);
                }
            }
        }
    }



    @ModifyReturnValue(method = "getMaxHealth", at = @At("RETURN"))
    private float redemption$judgeMaxHealth(float original) {
        return JudgementComponent.isJudgement(this) ? 40.0F : original;
    }

    @WrapMethod(method = "heal")
    private void redemption$judgeBoostedHeal(float amount, Operation<Void> original) {
        original.call(JudgementComponent.isJudgement(this) ? amount * 1.8F : amount);
    }

    @WrapMethod(method = "canTarget(Lnet/minecraft/entity/LivingEntity;)Z")
    private boolean redemption$canTarget(LivingEntity target, Operation<Boolean> original) {
        if (this.getCommandTags().contains("courtling") && JudgementComponent.isJudgement(target)) {
            return false;
        }

        return original.call(target);
    }
}
