package silly.chemthunder.redemption.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import silly.chemthunder.redemption.index.RedemptionItems;
import silly.chemthunder.redemption.index.RedemptionSoundEvents;

import java.util.List;

public class SheathedKatanaItem extends Item implements ColorableItem {
    public SheathedKatanaItem(Settings settings) {
        super(settings);
    }

    public static SheathedKatanaItem.KatanaType getKatanaType(Item item) {
        SheathedKatanaItem.KatanaType type = null;
        if (item == RedemptionItems.AMETHYST_SHEATHED) {
            type = KatanaType.AMETHYST;
        } else if (item == RedemptionItems.REDSTONE_SHEATHED) {
            type = KatanaType.REDSTONE;
        } else if (item == RedemptionItems.SCULK_SHEATHED) {
            type = KatanaType.SCULK;
        } else if (item == RedemptionItems.QUARTZ_SHEATHED) {
            type = KatanaType.QUARTZ;
        } else if (item == RedemptionItems.EMERALD_SHEATHED) {
            type = KatanaType.EMERALD;
        } else if (item == RedemptionItems.COPPER_SHEATHED) {
            type = KatanaType.COPPER;
        } else if (item == RedemptionItems.NETHERITE_SHEATHED) {
            type = KatanaType.NETHERITE;
        } else if (item == RedemptionItems.LAPIS_SHEATHED) {
            type = KatanaType.LAPIS;
        }

        return type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        Item item = stack.getItem();
        int duration = 400;

        if (user.getOffHandStack().isEmpty()) {
            ItemStack mainStack = null;
            ItemStack offStack = null;
            StatusEffectInstance effect = null;

            if (getKatanaType(item) == KatanaType.AMETHYST) {
                mainStack = RedemptionItems.AMETHYST_KATANA.getDefaultStack();
                offStack = RedemptionItems.AMETHYST_SHEATH.getDefaultStack();
                effect = new StatusEffectInstance(StatusEffects.SPEED, duration);
            }
            if (getKatanaType(item) == KatanaType.LAPIS) {
                mainStack = RedemptionItems.LAPIS_KATANA.getDefaultStack();
                offStack = RedemptionItems.LAPIS_SHEATH.getDefaultStack();
                effect = new StatusEffectInstance(StatusEffects.LUCK, duration);

            }
            if (getKatanaType(item) == KatanaType.NETHERITE) {
                mainStack = RedemptionItems.NETHERITE_KATANA.getDefaultStack();
                offStack = RedemptionItems.NETHERITE_SHEATH.getDefaultStack();

            }
            if (getKatanaType(item) == KatanaType.EMERALD) {
                mainStack = RedemptionItems.EMERALD_KATANA.getDefaultStack();
                offStack = RedemptionItems.EMERALD_SHEATH.getDefaultStack();
                effect = new StatusEffectInstance(StatusEffects.HASTE, duration);
            }
            if (getKatanaType(item) == KatanaType.REDSTONE) {
                mainStack = RedemptionItems.REDSTONE_KATANA.getDefaultStack();
                offStack = RedemptionItems.REDSTONE_SHEATH.getDefaultStack();
                effect = new StatusEffectInstance(StatusEffects.STRENGTH, duration);
            }
            if (getKatanaType(item) == KatanaType.SCULK) {
                mainStack = RedemptionItems.SCULK_KATANA.getDefaultStack();
                offStack = RedemptionItems.SCULK_SHEATH.getDefaultStack();

            }
            if (getKatanaType(item) == KatanaType.QUARTZ) {
                mainStack = RedemptionItems.QUARTZ_KATANA.getDefaultStack();
                offStack = RedemptionItems.QUARTZ_SHEATH.getDefaultStack();
                effect = new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, duration);
            }
            if (getKatanaType(item) == KatanaType.COPPER) {
                mainStack = RedemptionItems.COPPER_KATANA.getDefaultStack();
                offStack = RedemptionItems.COPPER_SHEATH.getDefaultStack();
                effect = new StatusEffectInstance(StatusEffects.TRIAL_OMEN, duration);
            }

            stack.decrement(1);
            user.equipStack(EquipmentSlot.MAINHAND, mainStack);
            user.equipStack(EquipmentSlot.OFFHAND, offStack);

            if (!(getKatanaType(item) == KatanaType.NETHERITE || getKatanaType(item) == KatanaType.SCULK)) {
                user.addStatusEffect(effect);
            } else if (getKatanaType(item) == KatanaType.NETHERITE) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, duration, 1));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, duration));
            } else if (getKatanaType(item) == KatanaType.SCULK) {
                net.minecraft.util.math.Box box = new Box(user.getBlockPos()).expand(10, 10, 10);
                List<LivingEntity> entities = user.getWorld().getEntitiesByClass(
                        LivingEntity.class, box,
                        entity -> true
                );

                for (LivingEntity entity : entities) {
                    if (!(entity == user)) {
                        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 600));
                    }
                    entity.playSound(RedemptionSoundEvents.HUNTER_BLACKOUT);
                }
            }
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 400));

            // sounds
            user.playSound(RedemptionSoundEvents.UNSHEATHE);

        }
        return super.use(world, user, hand);
    }

    @Override
    public int startColor(ItemStack itemStack) {
        return 0xFF6e5353;
    }

    @Override
    public int endColor(ItemStack itemStack) {
        return 0xFF271e1e;
    }

    @Override
    public int backgroundColor(ItemStack itemStack) {
        return 0xF01d1212;
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 0f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -1f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }



    public enum KatanaType {
        REDSTONE,
        EMERALD,
        QUARTZ,
        NETHERITE,
        COPPER,
        AMETHYST,
        LAPIS,
        SCULK
    }
}
