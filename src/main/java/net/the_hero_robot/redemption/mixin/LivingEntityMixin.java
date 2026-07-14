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
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.cca.entity.JudgementComponent;
import net.the_hero_robot.redemption.impl.cca.entity.UndeadComponent;
import net.the_hero_robot.redemption.impl.index.data.RedemptionDamageTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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

    @Unique
    protected boolean isAffectedByDaylight(LivingEntity player) {

        if (player.getWorld().isDay() && !player.getWorld().isClient) {
            float f = player.getBrightnessAtEyes();
            BlockPos blockPos = BlockPos.ofFloored(player.getX(), player.getEyeY(), player.getZ());
            if (f > 0.5F && player.getRandom().nextFloat() * 30.0F < (f - 0.4F) * 2.0F && player.getWorld().isSkyVisible(blockPos)) {
                return true;
            }
        }
        return false;
    }

    @Inject(method = "tickMovement", at = @At("HEAD"), cancellable = true)
    public void redemption$undeadIsBurnedInDaylightOrDissolvedInWater(CallbackInfo ci) {
        if ((LivingEntity) (Object) this instanceof PlayerEntity) {
            if (UndeadComponent.KEY.get(this).isSunWeak() && isAffectedByDaylight((LivingEntity) (Object) this)) {
                this.setOnFireFor(8.0F);
            }
            if (UndeadComponent.KEY.get(this).isVampire() && this.getWorld().getFluidState(this.getBlockPos()).getFluid() == Fluids.WATER.getFlowing()) {

                this.damage(this.getWorld().getDamageSources().magic(), 1);
            }
        }
    }




    @WrapMethod(method = "damage")
    private boolean redemption$damageNullifyConditions(DamageSource source, float amount, Operation<Boolean> original) {
        if (source.getAttacker() instanceof PlayerEntity attackerPlayer) {
            if (UndeadComponent.KEY.maybeGet(attackerPlayer).isPresent()) {

                if (UndeadComponent.KEY.get(attackerPlayer).isAnyGreyed()) {
                    return original.call(source, amount * 0);
                }

                if ((LivingEntity) (Object) this instanceof PlayerEntity player) {
                    if (player.getHealth() <= amount && UndeadComponent.KEY.get(attackerPlayer).isVampire() && !UndeadComponent.KEY.get(player).isUndead()) {
                        UndeadComponent.KEY.get(player).setUndead(2);
                        // add some sort of visual effect for eye candy

                        return original.call(source, amount * 0);
                    }



                    if (attackerPlayer.getMainHandStack().getItem() == Items.WOODEN_SWORD) {
                        if (UndeadComponent.KEY.get(this).isVampire()) {

                            return original.call(source, amount + 4);
                        }
                    }
                }
            }
        }
        return original.call(source, amount);
    }

    @Inject(method = "tryUseTotem", at = @At("RETURN"), cancellable = true)
    private void redemption$deathEffect(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living = (LivingEntity)(Object)this;

        if (living instanceof PlayerEntity player) {
            JudgementComponent judge = JudgementComponent.KEY.get(player);

            if (JudgementComponent.isJudgement(player) && !cir.getReturnValue()) {
                if (!source.isOf(RedemptionDamageTypes.DESCEND)) {
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
