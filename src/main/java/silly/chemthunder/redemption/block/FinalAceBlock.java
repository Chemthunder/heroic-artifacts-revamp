package silly.chemthunder.redemption.block;

import com.mojang.serialization.MapCodec;
import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import silly.chemthunder.redemption.block.entity.FinalAceBlockEntity;
import silly.chemthunder.redemption.cca.FlashComponent;
import silly.chemthunder.redemption.index.RedemptionSoundEvents;

public class FinalAceBlock extends BlockWithEntity {
    public static final MapCodec<FinalAceBlock> CODEC = createCodec(FinalAceBlock::new);
    public FinalAceBlock(Settings settings) {
        super(settings);
    }

    public static int ticks = 30;
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
            placer.playSound(RedemptionSoundEvents.SONAR_PING);
            ticks = 30;
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

        if (ticks == 0) {
            // idk
        } else {
            ticks--;
            radiusTicks -= 0.09f;
        }

        world.scheduleBlockTick(pos, this, 0); // 1 tick later
        super.scheduledTick(state, world, pos, random);
    }
}
