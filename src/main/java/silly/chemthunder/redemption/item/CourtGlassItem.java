package silly.chemthunder.redemption.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import silly.chemthunder.redemption.cca.JudgementFlashComponent;
import silly.chemthunder.redemption.cca.JudgementPlayerComponent;
import silly.chemthunder.redemption.index.RedemptionSoundEvents;

import java.util.List;

public class CourtGlassItem extends Item implements ColorableItem {
    public CourtGlassItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }


    @Override
    public int startColor(ItemStack itemStack) {
        return 0xFF00fbff;
    }

    @Override
    public int endColor(ItemStack itemStack) {
        return 0xFF153030;
    }

    @Override
    public int backgroundColor(ItemStack itemStack) {
        return 0xF0040a0a;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        int color = 0x26bdbd;
        tooltip.add(Text.translatable("lore.court_glass_1").withColor(color));
        tooltip.add(Text.translatable("lore.court_glass_2").withColor(color));
        tooltip.add(Text.translatable("lore.court_glass_3").withColor(color));
        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getOffHandStack().isOf(this)) {
            if (user.isSneaking()) {
                becomeJudgement(user, world, user.getOffHandStack());
            }
        }

        return super.use(world, user, hand);
    }

    public void becomeJudgement(PlayerEntity player, World world, ItemStack offStack) {
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        JudgementPlayerComponent judge = JudgementPlayerComponent.KEY.get(player);


        judge.isJudgement = true;
        judge.sync();
        player.setHealth(player.getMaxHealth());

        net.minecraft.util.math.Box box = new Box(player.getBlockPos()).expand(100, 100, 100);
        List<LivingEntity> entities = player.getWorld().getEntitiesByClass(
                LivingEntity.class, box,
                entity -> true
        );

        for (LivingEntity entity : entities) {
            JudgementFlashComponent flash = JudgementFlashComponent.KEY.get(entity);
            flash.flashTicks = 20;
            flash.sync();

            entity.playSound(SoundEvents.BLOCK_CONDUIT_AMBIENT_SHORT);
            entity.playSound(SoundEvents.ENTITY_WARDEN_HEARTBEAT);
            entity.playSound(SoundEvents.ENTITY_WARDEN_EMERGE);
            entity.playSound(RedemptionSoundEvents.BECOME_JUDGE);
        }
        if (!player.isInCreativeMode()) offStack.decrement(1);

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.SOUL, x, y + 0.6f, z, 50, 0.3f, 0.5f, 0.3f, 0.3f);
            serverWorld.spawnParticles(ParticleTypes.SCULK_SOUL, x, y + 0.6f, z, 50, 0.3f, 0.5f, 0.3f, 0.3f);
            serverWorld.spawnParticles(ParticleTypes.GLOW, x, y + 0.6f, z, 50, 0.3f, 0.5f, 0.3f, 0.3f);
            if (player instanceof ScreenShaker screenShaker) {
                screenShaker.addScreenShake(5, 1);
            }
        }
    }
}
