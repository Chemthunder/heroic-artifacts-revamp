package net.the_hero_robot.redemption.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.the_hero_robot.redemption.impl.cca.entity.EnshroudedComponent;
import net.the_hero_robot.redemption.impl.cca.entity.JudgementComponent;
import net.the_hero_robot.redemption.impl.component.KatanaComponent;
import net.the_hero_robot.redemption.impl.index.RNDataComponents;
import net.the_hero_robot.redemption.impl.index.RNParticles;
import net.the_hero_robot.redemption.impl.index.tag.RNItemTags;
import net.the_hero_robot.redemption.impl.util.RNUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author AcoYT
 * @author Chemthunder
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "playStepSound", at = @At("RETURN"), cancellable = true)
    private void redemption$cancelStepSounds(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (EnshroudedComponent.isShrouded(this)) ci.cancel();
    }

    @Inject(method = "attack", at = @At("TAIL"))
    private void redemption$disableShroudUponAttack(Entity target, CallbackInfo ci) {
        redemption$disableCloak((PlayerEntity)(Object)this);
    }

    @Inject(method = "damage", at = @At("TAIL"))
    private void redemption$disableShroudUponDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        redemption$disableCloak((PlayerEntity)(Object)this);
    }

    @Unique
    public void redemption$disableCloak(PlayerEntity player) {
        EnshroudedComponent component = EnshroudedComponent.KEY.get(player);
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        if (component.isShrouded()) {
            component.set(false, 100);
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(RNParticles.HUNTER_OMEN, x, y + 0.5f, z, 15, 0, 0, 0, 0.03f);
                serverWorld.spawnParticles(ParticleTypes.SQUID_INK, x, y + 0.5f, z, 15, 0, 0, 0, 0.03f);
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void redemption$playerTicker(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        if (player.getStackInHand(player.getActiveHand()).isIn(RNItemTags.KATANAS) && player.isUsingItem() && JudgementComponent.isJudgement(player)) {
            this.getWorld().addParticle(ParticleTypes.SCULK_SOUL, true, player.getX(), player.getY(), player.getZ(), 0, 0, 0);
        }
    }

    @Inject(method = "disableShield", at = @At("HEAD"))
    private void redemption$disableKatanaSheath(CallbackInfo ci) {
        if (KatanaComponent.get(getActiveItem()) != null && KatanaComponent.get(getActiveItem()).bladeType().isSheath()) {
            RNUtil.cooldownAllSheath((PlayerEntity) (Object) this, 100);
        }
    }

    @ModifyVariable(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Entity;getVelocity()Lnet/minecraft/util/math/Vec3d;",
                    ordinal = 0
            ),
            ordinal = 3
    )
    private boolean redemption$allowSweeping(boolean bl) {
        ItemStack itemStack = this.getMainHandStack();
        KatanaComponent component = itemStack.get(RNDataComponents.KATANA);
        if (component != null && component.bladeType() == KatanaComponent.BladeType.KATANA) {
            return true;
        }

        return bl;
    }

    @WrapOperation(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;spawnSweepAttackParticles()V"
            )
    )
    private void redemption$noOriginalSweepParticle(PlayerEntity instance, Operation<Void> original) {
        if (instance.getMainHandStack().contains(RNDataComponents.KATANA)) return;
        original.call(instance);
    }
}
