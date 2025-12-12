package silly.chemthunder.redemption.block;

import com.mojang.serialization.MapCodec;
import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import silly.chemthunder.redemption.block.entity.FinalAceBlockEntity;
import silly.chemthunder.redemption.cca.FlashComponent;
import silly.chemthunder.redemption.index.RedemptionBlocks;
import silly.chemthunder.redemption.index.RedemptionDamageSources;
import silly.chemthunder.redemption.index.RedemptionSoundEvents;

import java.util.List;

public class FinalAceBlock extends BlockWithEntity {
    public static final MapCodec<FinalAceBlock> CODEC = createCodec(FinalAceBlock::new);
    public FinalAceBlock(Settings settings) {
        super(settings);
    }

    public static float radiusTicks = 30.0f;

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FinalAceBlockEntity(pos, state);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (placer != null) {
            world.scheduleBlockTick(pos, this, 0);
            placer.playSound(RedemptionSoundEvents.ERADICATE);
            placer.playSound(RedemptionSoundEvents.ERADICATE_2);
            radiusTicks = 30.0f;

            FlashComponent flash = FlashComponent.KEY.get(placer);
            flash.flashTicks = 20;

//            if (placer instanceof ScreenShaker screenShaker) {
//                screenShaker.addScreenShake(2, 1);
//            }
        }
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (radiusTicks >= 7) {
            radiusTicks -= 0.01f;
        } else {
            net.minecraft.util.math.Box box = new Box(pos).expand(7, 7, 7);
            List<LivingEntity> entities = world.getEntitiesByClass(
                    LivingEntity.class, box,
                    entity -> true
            );

            for (LivingEntity entity : entities) {
                   rapture(entity, world);
                    radiusTicks -= 0.05f;
            }

            for (ServerPlayerEntity sPlayer : world.getPlayers()) {
                FlashComponent flash = FlashComponent.KEY.get(sPlayer);

                flash.flashTicks = 20;

                flash.sync();

                sPlayer.playSoundToPlayer(RedemptionSoundEvents.ERADICATE, SoundCategory.MASTER, 1, 1);
                sPlayer.playSoundToPlayer(RedemptionSoundEvents.ERADICATE_1, SoundCategory.MASTER, 1, 1);
                sPlayer.playSoundToPlayer(RedemptionSoundEvents.ERADICATE_2, SoundCategory.MASTER, 1, 1);
                sPlayer.playSoundToPlayer(RedemptionSoundEvents.HOWL, SoundCategory.MASTER, 1, 1);

                if (sPlayer instanceof ScreenShaker screenShaker) {
                    screenShaker.addScreenShake(4, 5);
                }
            }

            world.spawnParticles(ParticleTypes.END_ROD, pos.getX(), pos.getY(), pos.getZ(), 50, 0, 0, 0, 0.3);

            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }

        world.scheduleBlockTick(pos, this, 0); // 1 tick later
        super.scheduledTick(state, world, pos, random);
    }

    public void rapture(LivingEntity player, World world) {
        player.damage(RedemptionDamageSources.consumed(player), player.getMaxHealth() * 50);

        radiusTicks = easeOutCubic(radiusTicks);
    }

    private float easeOutCubic(float t) {
        return 1 - (float) Math.pow(1 - t, 3);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
