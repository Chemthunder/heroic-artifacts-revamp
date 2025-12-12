package silly.chemthunder.redemption.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import silly.chemthunder.redemption.cca.JudgementFlashComponent;
import silly.chemthunder.redemption.cca.JudgementPlayerComponent;
import silly.chemthunder.redemption.index.RedemptionItems;
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
        if (user.isSneaking()) {
            if (user.getOffHandStack().isOf(this)) {
                becomeJudgement(user, world, user.getOffHandStack());
            } else if (user.getMainHandStack().isOf(this)) {
                summonCourt(world, user);
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

            // msg
            for (ServerPlayerEntity sPlayer : serverWorld.getPlayers()) {
                sPlayer.sendMessage(Text.translatable(player.getNameForScoreboard()).append(Text.literal(" was crowned for Judgement")), false);
            }
        }
    }

    public void summonCourt(World world, PlayerEntity user) {
        for (int i = 0; i < 15; i++) {
            WitherSkeletonEntity glassCannon = new WitherSkeletonEntity(EntityType.WITHER_SKELETON, world);

            glassCannon.equipStack(EquipmentSlot.MAINHAND, RedemptionItems.SCULK_KATANA.getDefaultStack());
            glassCannon.equipStack(EquipmentSlot.OFFHAND, RedemptionItems.SCULK_SHEATH.getDefaultStack());
            glassCannon.equipStack(EquipmentSlot.HEAD, Items.DIAMOND_HELMET.getDefaultStack());
            glassCannon.equipStack(EquipmentSlot.CHEST, Items.DIAMOND_CHESTPLATE.getDefaultStack());
            glassCannon.equipStack(EquipmentSlot.LEGS, Items.DIAMOND_LEGGINGS.getDefaultStack());
            glassCannon.equipStack(EquipmentSlot.FEET, Items.DIAMOND_BOOTS.getDefaultStack());

            glassCannon.updatePosition(user.getX(), user.getY(), user.getZ());
            glassCannon.setCustomNameVisible(true);
            glassCannon.setCustomName(Text.translatable("lore.courtling").formatted(Formatting.BLUE));
            glassCannon.setGlowing(true);


            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnEntity(glassCannon);
            }
        }
    }
}
