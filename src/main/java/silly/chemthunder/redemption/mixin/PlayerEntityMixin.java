package silly.chemthunder.redemption.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import silly.chemthunder.redemption.cca.EnshroudedPlayerComponent;
import silly.chemthunder.redemption.index.RedemptionParticles;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }



    @Unique @Final
    PlayerEntity player = (PlayerEntity) (Object) this;

    @Unique
    double x = player.getX();
    @Unique
    double y = player.getY();
    @Unique
    double z = player.getZ();

    @Inject(method = "playStepSound", at = @At("RETURN"), cancellable = true)
    private void cancelStepSounds(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (EnshroudedPlayerComponent.KEY.get(player).isShrouded) {
            ci.cancel();
        }
    }

    @Inject(method = "attack", at = @At("TAIL"))
    private void disableShroudUponAttack(Entity target, CallbackInfo ci) {
        if (EnshroudedPlayerComponent.KEY.get(player).isShrouded) {
            EnshroudedPlayerComponent.KEY.get(player).isShrouded = false;
            EnshroudedPlayerComponent.KEY.get(player).sync();
            if (getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(RedemptionParticles.HUNTER_OMEN, x, y + 0.5f, z, 15, 0, 0, 0, 0.03f);
                serverWorld.spawnParticles(ParticleTypes.SQUID_INK, x, y + 0.5f, z, 15, 0, 0, 0, 0.03f);
            }
        }
    }

    @Inject(method = "damage", at = @At("TAIL"))
    private void disableShroudUponDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (EnshroudedPlayerComponent.KEY.get(player).isShrouded) {
            EnshroudedPlayerComponent.KEY.get(player).isShrouded = false;
            EnshroudedPlayerComponent.KEY.get(player).sync();
            if (getWorld() instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(RedemptionParticles.HUNTER_OMEN, x, y + 0.5f, z, 15, 0, 0, 0, 0.03f);
                serverWorld.spawnParticles(ParticleTypes.SQUID_INK, x, y + 0.5f, z, 15, 0, 0, 0, 0.03f);
            }
        }
    }

}
