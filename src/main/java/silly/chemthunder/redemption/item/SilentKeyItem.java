package silly.chemthunder.redemption.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.acoyt.acornlib.api.item.CustomHitParticleItem;
import net.acoyt.acornlib.api.item.CustomKillSourceItem;
import net.acoyt.acornlib.api.item.KillEffectItem;
import net.acoyt.acornlib.api.util.ParticleUtils;
import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.FishingHookPredicate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import silly.chemthunder.redemption.cca.FlashComponent;
import silly.chemthunder.redemption.entity.BindingHexEntity;
import silly.chemthunder.redemption.index.RedemptionDamageSources;
import silly.chemthunder.redemption.index.RedemptionDataComponents;
import silly.chemthunder.redemption.index.RedemptionEntities;
import silly.chemthunder.redemption.index.RedemptionModelPredicates;

public class SilentKeyItem extends Item implements ColorableItem, CustomHitParticleItem, KillEffectItem, CustomKillSourceItem {
    public SilentKeyItem(Settings settings) {
        super(settings);
    }

    @Override
    public int startColor(ItemStack itemStack) {
        return 0xFFd70048;
    }

    @Override
    public int endColor(ItemStack itemStack) {
        return 0xFF8e1a41;
    }

    @Override
    public int backgroundColor(ItemStack itemStack) {
        return 0xF01c0810;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.getStackInHand(hand).set(RedemptionDataComponents.IS_AWAKENED, 1);

        return super.use(world, user, hand);
    }

    public static final SweepParticleEffect[] EFFECTS = new SweepParticleEffect[]{new SweepParticleEffect(0x513854, 0x160514), new SweepParticleEffect(0xd70048, 0x0c0105)};
    @Override
    public void spawnHitParticles(PlayerEntity playerEntity, Entity entity) {
        ParticleUtils.spawnSweepParticles(EFFECTS[playerEntity.getRandom().nextInt(EFFECTS.length)], playerEntity);
    }

    @Override
    public void killEntity(World world, ItemStack itemStack, LivingEntity user, LivingEntity victim) {
        victim.setHealth(victim.getMaxHealth());
        BindingHexEntity hex = new BindingHexEntity(RedemptionEntities.BINDING_HEX, world);

        hex.updatePosition(victim.getX(), victim.getY(), victim.getZ());
        victim.startRiding(hex);

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnEntity(hex);

            for (ServerPlayerEntity serverPlayer : serverWorld.getPlayers()) {
                FlashComponent flash = FlashComponent.KEY.get(serverPlayer);
                flash.flashTicks = 20;
                flash.sync();

                if (serverPlayer instanceof ScreenShaker screenShaker) {
                    screenShaker.addScreenShake(2, 1);
                }
            }
        }
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 8.5f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.7f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(Identifier.ofVanilla("base_entity_interaction_range"), 0.5f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }


    @Override
    public DamageSource getKillSource(LivingEntity living) {
        return RedemptionDamageSources.enkey(living);
    }
}
