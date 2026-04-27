package silly.chemthunder.redemption.impl.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.acoyt.acornlib.api.item.CustomKillSourceItem;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Box;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4i;
import silly.chemthunder.redemption.impl.Redemption;
import silly.chemthunder.redemption.impl.cca.entity.JudgementComponent;
import silly.chemthunder.redemption.impl.component.AshiroComponent;
import silly.chemthunder.redemption.impl.component.KatanaComponent;
import silly.chemthunder.redemption.impl.index.RedemptionDataComponents;
import silly.chemthunder.redemption.impl.index.RedemptionSounds;
import silly.chemthunder.redemption.impl.index.data.RedemptionDamageTypes;
import silly.chemthunder.redemption.impl.util.KatanaType;
import silly.chemthunder.redemption.impl.util.ModUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author AcoYT
 * Serves to act as a universal standin for all the classes, meaning it will contain the functionalities of all three of the classes
 * Don't forget to add a method in the class for getting the BladeType
 */
public class KatanaItem extends Item implements ColorableItem, ModelVaryingItem, CustomKillSourceItem {
    public KatanaItem(Settings settings) {
        super(settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers(KatanaComponent.BladeType bladeType) {
        if (bladeType == KatanaComponent.BladeType.KATANA) {
            return AttributeModifiersComponent.builder()
                    .add(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 6.5F, Operation.ADD_VALUE),
                            AttributeModifierSlot.MAINHAND
                    ).add(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.3F, Operation.ADD_VALUE),
                            AttributeModifierSlot.MAINHAND
                    ).add(
                            EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                            new EntityAttributeModifier(Identifier.ofVanilla("base_entity_interaction_range"), 0.5F, Operation.ADD_VALUE),
                            AttributeModifierSlot.MAINHAND
                    ).build();
        } else if (bladeType == KatanaComponent.BladeType.SHEATHED) {
            return AttributeModifiersComponent.builder()
                    .add(
                            EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 0.0F, Operation.ADD_VALUE),
                            AttributeModifierSlot.MAINHAND
                    ).add(
                            EntityAttributes.GENERIC_ATTACK_SPEED,
                            new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -1.0F, Operation.ADD_VALUE),
                            AttributeModifierSlot.MAINHAND
                    ).build();
        } else {
            return AttributeModifiersComponent.builder()
                    .add(
                            EntityAttributes.GENERIC_ARMOR,
                            new EntityAttributeModifier(Identifier.ofVanilla("generic_armor"), 1.5F, Operation.ADD_VALUE),
                            AttributeModifierSlot.OFFHAND
                    ).add(
                            EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            new EntityAttributeModifier(Identifier.ofVanilla("generic_movement_speed"), 0.02F, Operation.ADD_VALUE),
                            AttributeModifierSlot.OFFHAND
                    ).build();
        }
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        KatanaComponent component = KatanaComponent.get(stack);
        KatanaComponent.BladeType bladeType = component.getBladeType();
        if (stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS) != createAttributeModifiers(bladeType)) {
            stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, createAttributeModifiers(bladeType));
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public Text getName(ItemStack stack) {
        Vector4i colors = stack.getOrDefault(RedemptionDataComponents.COLORS, new Vector4i());
        return Text.translatable(this.getTranslationKey(stack)).withColor(colors.x);
    }

    public String getTranslationKey(ItemStack stack) {
        return Util.createTranslationKey("item", ModUtil.formatKatanaId(stack, false));
    }

    public DamageSource getKillSource(LivingEntity living, @Nullable Entity attacker, float amount) {
        ItemStack stack = living.getMainHandStack();
        KatanaComponent component = KatanaComponent.get(stack);
        return component.getBladeType() == KatanaComponent.BladeType.KATANA
                ? RedemptionDamageTypes.create(living.getWorld(), RedemptionDamageTypes.KATANA)
                : null;
    }

    public UseAction getUseAction(ItemStack stack) {
        KatanaComponent component = KatanaComponent.get(stack);
        return component.getBladeType() == KatanaComponent.BladeType.SHEATH
                ? UseAction.BLOCK
                : UseAction.NONE;
    }

    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        KatanaComponent component = KatanaComponent.get(stack);
        return component.getBladeType() == KatanaComponent.BladeType.SHEATH
                ? 72000
                : super.getMaxUseTime(stack, user);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        KatanaComponent component = KatanaComponent.get(stack);
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            if (component.getBladeType() == KatanaComponent.BladeType.SHEATH) {
                super.use(world, user, hand);
                user.setCurrentHand(hand);
                return TypedActionResult.consume(user.getStackInHand(hand));
            } else if (component.getBladeType() == KatanaComponent.BladeType.SHEATHED && user.getOffHandStack().isEmpty()) {
                KatanaType katanaType = component.type();

                user.getItemCooldownManager().set(stack.getItem(), 20);

                ItemStack mainStack = ModUtil.copy(stack, RedemptionDataComponents.KATANA, component.withBladeType(KatanaComponent.BladeType.KATANA));
                ItemStack offStack = ModUtil.copy(stack, RedemptionDataComponents.KATANA, component.withBladeType(KatanaComponent.BladeType.SHEATH));
                List<StatusEffectInstance> effects = katanaType.effectInstances;

                user.setStackInHand(Hand.MAIN_HAND, mainStack);
                user.setStackInHand(Hand.OFF_HAND, offStack);
                stack.decrement(1);

                if (!effects.isEmpty()) {
                    for (StatusEffectInstance instance : effects) {
                        user.addStatusEffect(instance);
                    }
                }

                if (katanaType == KatanaType.NETHERITE) {
                    user.damage(user.getDamageSources().magic(), 4.0F);
                }

                if (katanaType == KatanaType.SCULK) {
                    Box box = new Box(user.getBlockPos()).expand(10);

                    for (LivingEntity living : world.getEntitiesByClass(LivingEntity.class, box, EntityPredicates.EXCEPT_SPECTATOR.and(entity -> entity != user))) {
                        living.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 400));
                    }
                }

                user.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 400));
                user.playSound(RedemptionSounds.KATANA_UNSHEATHE, 1.0F, (float) (1.0F + user.getRandom().nextGaussian() / 10.0F));

                return TypedActionResult.success(user.getStackInHand(hand), world.isClient);
            } else if (component.getBladeType() == KatanaComponent.BladeType.KATANA && JudgementComponent.KEY.get(user).isJudgement()) {
                user.setVelocity(user.getRotationVector().multiply(3));
                user.velocityModified = true;

                user.useRiptide(10, 7.0F, user.getStackInHand(user.getActiveHand()));
                user.getItemCooldownManager().set(this, 400);

                return TypedActionResult.success(user.getStackInHand(hand));
            } else if (component.type() == KatanaType.ASHIRO && component.getBladeType() == KatanaComponent.BladeType.KATANA && stack.contains(RedemptionDataComponents.ASHIRO)) {
                AshiroComponent ashiroComponent = AshiroComponent.get(stack);

                if (user.isSneaking()) {
                    stack.set(RedemptionDataComponents.ASHIRO, new AshiroComponent(user.getWorld().getRegistryKey(), user.getPos()));
                } else if (user.getServer() != null && user.getServer().getWorld(ashiroComponent.world()) instanceof ServerWorld serverWorld) {
                    user.teleportTo(new TeleportTarget(serverWorld, ashiroComponent.pos(), user.getVelocity(), user.getYaw(), user.getPitch(), TeleportTarget.NO_OP));
                }

                user.getItemCooldownManager().set(stack.getItem(), 20);
                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }

        return super.use(world, user, hand);
    }

    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity living) {
        KatanaComponent component = KatanaComponent.get(stack);
        Identifier base = Redemption.id(component.type().id);
        Identifier katanaId = base.withSuffixedPath("_katana");
        Identifier sheathedId = base.withPrefixedPath("sheathed_").withSuffixedPath("_katana");
        Identifier sheathId = base.withSuffixedPath("_sheath");

        return component.getBladeType() == KatanaComponent.BladeType.KATANA
                ? MiscUtils.isGui(renderMode) ? katanaId : katanaId.withSuffixedPath("_in_hand")
                : component.getBladeType() == KatanaComponent.BladeType.SHEATHED
                ? MiscUtils.isGui(renderMode) ? sheathedId : sheathedId.withSuffixedPath("_in_hand")
                : MiscUtils.isGui(renderMode) ? sheathId : sheathId.withSuffixedPath("_in_hand");
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        ItemStack cursorStack = cursorStackReference.get();

        KatanaComponent component = KatanaComponent.get(stack);
        KatanaComponent cursorComponent = KatanaComponent.get(cursorStack);

        if (clickType == ClickType.RIGHT
                && component.getBladeType() == KatanaComponent.BladeType.SHEATH
                && cursorComponent.getBladeType() == KatanaComponent.BladeType.KATANA
        ) {
            if (player.getInventory().contains(itemStack -> component.material().isEmpty() || component.material().test(itemStack))) {
                cursorStack.decrement(1);
                stack.set(RedemptionDataComponents.KATANA, component.withBladeType(KatanaComponent.BladeType.SHEATHED));

                for (int i = 0; i < player.getInventory().size(); i++) {
                    ItemStack itemStack = player.getInventory().getStack(i);
                    if (component.material().isEmpty() || component.material().test(itemStack)) {
                        itemStack.decrementUnlessCreative(1, player);
                        break;
                    }
                }

                if (player.getWorld().isClient) {
                    player.playSound(RedemptionSounds.KATANA_SHEATHE, 1.0F, (float) (1.0F + player.getRandom().nextGaussian() / 10.0F));
                }
            } else {
                if (player.getWorld().isClient) {
                    player.playSound(SoundEvents.ITEM_BUNDLE_DROP_CONTENTS);
                }
            }

            return true;
        }

        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Redemption.id("amethyst_katana"),
                Redemption.id("amethyst_katana_in_hand"),

                Redemption.id("redstone_katana"),
                Redemption.id("redstone_katana_in_hand"),

                Redemption.id("sculk_katana"),
                Redemption.id("sculk_katana_in_hand"),

                Redemption.id("quartz_katana"),
                Redemption.id("quartz_katana_in_hand"),

                Redemption.id("emerald_katana"),
                Redemption.id("emerald_katana_in_hand"),

                Redemption.id("copper_katana"),
                Redemption.id("copper_katana_in_hand"),

                Redemption.id("netherite_katana"),
                Redemption.id("netherite_katana_in_hand"),

                Redemption.id("lapis_katana"),
                Redemption.id("lapis_katana_in_hand"),

                Redemption.id("ashiro_katana"),
                Redemption.id("ashiro_katana_in_hand"),


                Redemption.id("sheathed_amethyst_katana"),
                Redemption.id("sheathed_amethyst_katana_in_hand"),

                Redemption.id("sheathed_redstone_katana"),
                Redemption.id("sheathed_redstone_katana_in_hand"),

                Redemption.id("sheathed_sculk_katana"),
                Redemption.id("sheathed_sculk_katana_in_hand"),

                Redemption.id("sheathed_quartz_katana"),
                Redemption.id("sheathed_quartz_katana_in_hand"),

                Redemption.id("sheathed_emerald_katana"),
                Redemption.id("sheathed_emerald_katana_in_hand"),

                Redemption.id("sheathed_copper_katana"),
                Redemption.id("sheathed_copper_katana_in_hand"),

                Redemption.id("sheathed_netherite_katana"),
                Redemption.id("sheathed_netherite_katana_in_hand"),

                Redemption.id("sheathed_lapis_katana"),
                Redemption.id("sheathed_lapis_katana_in_hand"),

                Redemption.id("sheathed_ashiro_katana"),
                Redemption.id("sheathed_ashiro_katana_in_hand"),


                Redemption.id("amethyst_sheath"),
                Redemption.id("amethyst_sheath_in_hand"),

                Redemption.id("redstone_sheath"),
                Redemption.id("redstone_sheath_in_hand"),

                Redemption.id("sculk_sheath"),
                Redemption.id("sculk_sheath_in_hand"),

                Redemption.id("quartz_sheath"),
                Redemption.id("quartz_sheath_in_hand"),

                Redemption.id("emerald_sheath"),
                Redemption.id("emerald_sheath_in_hand"),

                Redemption.id("copper_sheath"),
                Redemption.id("copper_sheath_in_hand"),

                Redemption.id("netherite_sheath"),
                Redemption.id("netherite_sheath_in_hand"),

                Redemption.id("lapis_sheath"),
                Redemption.id("lapis_sheath_in_hand"),

                Redemption.id("ashiro_sheath"),
                Redemption.id("ashiro_sheath_in_hand")
        );
    }

    public int startColor(ItemStack stack) {
        Vector4i colors = stack.getOrDefault(RedemptionDataComponents.COLORS, new Vector4i());
        return colors.y;
    }

    public int endColor(ItemStack stack) {
        Vector4i colors = stack.getOrDefault(RedemptionDataComponents.COLORS, new Vector4i());
        return colors.z;
    }

    public int backgroundColor(ItemStack stack) {
        Vector4i colors = stack.getOrDefault(RedemptionDataComponents.COLORS, new Vector4i());
        return colors.w;
    }

    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem() || KatanaComponent.get(oldStack).getBladeType() != KatanaComponent.get(newStack).getBladeType();
    }
}
