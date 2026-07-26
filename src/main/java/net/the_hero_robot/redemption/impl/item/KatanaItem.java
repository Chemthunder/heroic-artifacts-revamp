package net.the_hero_robot.redemption.impl.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.acoyt.acornlib.api.item.CustomHitParticleItem;
import net.acoyt.acornlib.api.item.CustomKillSourceItem;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.acoyt.acornlib.api.util.ParticleUtils;
import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Box;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.cca.entity.JudgementComponent;
import net.the_hero_robot.redemption.impl.component.AshiroComponent;
import net.the_hero_robot.redemption.impl.component.KatanaComponent;
import net.the_hero_robot.redemption.impl.index.RNDataComponents;
import net.the_hero_robot.redemption.impl.index.RNSounds;
import net.the_hero_robot.redemption.impl.index.data.RNDamageTypes;
import net.the_hero_robot.redemption.impl.util.KatanaType;
import net.the_hero_robot.redemption.impl.util.RNUtil;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;
import org.joml.Vector4i;

import java.util.Arrays;
import java.util.List;

/**
 * @author AcoYT
 * Serves to act as a universal standin for all the classes, meaning it will contain the functionalities of all three of the classes
 * Don't forget to add a method in the class for getting the BladeType
 */
public class KatanaItem extends Item implements ColorableItem, ModelVaryingItem, CustomKillSourceItem, CustomHitParticleItem {
    public KatanaItem(Settings settings) {
        super(settings.component(DataComponentTypes.TOOL, createToolComponent())
                .maxDamage(ToolMaterials.NETHERITE.getDurability()));
    }

    private static ToolComponent createToolComponent() {
        return new ToolComponent(
                List.of(ToolComponent.Rule.ofAlwaysDropping(List.of(Blocks.COBWEB), 15.0F), ToolComponent.Rule.of(BlockTags.SWORD_EFFICIENT, 1.5F)), 1.0F, 2
        );
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
                            new EntityAttributeModifier(Identifier.ofVanilla("base_entity_interaction_range"), 0.15F, Operation.ADD_VALUE),
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
        KatanaComponent.BladeType bladeType = component.bladeType();
        if (stack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS) != createAttributeModifiers(bladeType)) {
            stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, createAttributeModifiers(bladeType));
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    public Text getName(ItemStack stack) {
        Vector4i colors = stack.getOrDefault(RNDataComponents.COLORS, new Vector4i());
        return Text.translatable(this.getTranslationKey(stack)).withColor(colors.x);
    }

    public String getTranslationKey(ItemStack stack) {
        return Util.createTranslationKey("item", RNUtil.formatKatanaId(stack, false));
    }

    public void spawnHitParticles(PlayerEntity player, Entity target) {
        KatanaComponent katana = KatanaComponent.get(player.getMainHandStack());
        Vector2i color = katana.bladeType() == KatanaComponent.BladeType.KATANA ? switch (katana.type()) {
            case AMETHYST -> new Vector2i(0xFFffffff, 0xFFc1c2c2);
            case COPPER -> new Vector2i(0xFFea8770, 0xFF904931);
            case EMERALD -> new Vector2i(0xFF20d64b, 0xFF096a31);
            case LAPIS -> new Vector2i(0xFFc9edf9, 0xFF8bcadd);
            case NETHERITE -> new Vector2i(0xFF1a1a1a, 0xFF0e0e0e);
            case QUARTZ -> new Vector2i(0xFFc1c1d2, 0xFF535373);
            case REDSTONE -> new Vector2i(0xFFe95050, 0xFF63374a);
            case SCULK -> new Vector2i(0xFF111b21, 0xFF034150);
            case ASHIRO -> null;
        } : null;

        if (color != null) {
            ParticleUtils.spawnSweepParticles(new SweepParticleEffect(color.x, color.y), player);
        }
    }

    public DamageSource getKillSource(LivingEntity living, @Nullable Entity attacker, float amount) {
        ItemStack stack = living.getMainHandStack();
        KatanaComponent component = KatanaComponent.get(stack);
        return component.bladeType() == KatanaComponent.BladeType.KATANA
                ? RNDamageTypes.create(living.getWorld(), RNDamageTypes.KATANA)
                : null;
    }

    public UseAction getUseAction(ItemStack stack) {
        KatanaComponent component = KatanaComponent.get(stack);
        return component.bladeType() == KatanaComponent.BladeType.SHEATH
                ? UseAction.BLOCK
                : UseAction.NONE;
    }

    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        KatanaComponent component = KatanaComponent.get(stack);
        return component.bladeType() == KatanaComponent.BladeType.SHEATH
                ? 72000
                : super.getMaxUseTime(stack, user);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        KatanaComponent component = KatanaComponent.get(stack);
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            if (component.bladeType() == KatanaComponent.BladeType.SHEATH) {
                super.use(world, user, hand);
                user.setCurrentHand(hand);
                return TypedActionResult.consume(user.getStackInHand(hand));
            } else if (component.bladeType() == KatanaComponent.BladeType.SHEATHED && user.getOffHandStack().isEmpty()) {
                KatanaType katanaType = component.type();

                user.getItemCooldownManager().set(stack.getItem(), 200);

                ItemStack mainStack = RNUtil.copy(stack.copy(), RNDataComponents.KATANA, component.withBladeType(KatanaComponent.BladeType.KATANA));
                ItemStack offStack = RNUtil.copy(stack.copy(), RNDataComponents.KATANA, component.withBladeType(KatanaComponent.BladeType.SHEATH));
                List<RegistryEntry<StatusEffect>> effects = katanaType.effectInstances;

                user.setStackInHand(Hand.MAIN_HAND, mainStack);
                user.setStackInHand(Hand.OFF_HAND, offStack);
                stack.decrement(1);

                if (!effects.isEmpty()) {
                    for (RegistryEntry<StatusEffect> effect : effects) {
                        user.addStatusEffect(new StatusEffectInstance(effect, effect == StatusEffects.STRENGTH ? 200 : effect == StatusEffects.RESISTANCE ? 120 : 400));
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
                user.playSound(RNSounds.KATANA_UNSHEATHE, 1.0F, (float) (1.0F + user.getRandom().nextGaussian() / 10.0F));

                return TypedActionResult.success(user.getStackInHand(hand), world.isClient);
            } else if (component.bladeType() == KatanaComponent.BladeType.KATANA && JudgementComponent.isJudgement(user)) {
                user.setVelocity(user.getRotationVector().multiply(3));
                user.velocityModified = true;

                user.useRiptide(10, 7.0F, user.getStackInHand(user.getActiveHand()));
                for (int i = 0; i < user.getInventory().size(); i++) {
                    ItemStack itemStack = user.getInventory().getStack(i);
                    if (itemStack.getItem() instanceof KatanaItem) {
                        user.getItemCooldownManager().set(itemStack.getItem(), 220);
                    }
                }

                return TypedActionResult.success(user.getStackInHand(hand));
            } else if (component.type() == KatanaType.ASHIRO && component.bladeType() == KatanaComponent.BladeType.KATANA) {
                AshiroComponent ashiroComponent = AshiroComponent.get(stack);

                boolean success = false;

                if (stack.contains(RNDataComponents.ASHIRO) && user.getServer() != null && user.getServer().getWorld(ashiroComponent.world()) instanceof ServerWorld serverWorld) {
                    user.teleportTo(new TeleportTarget(
                            serverWorld,
                            ashiroComponent.pos(), user.getVelocity(),
                            user.getYaw(), user.getPitch(),
                            TeleportTarget.NO_OP
                    ));

                    success = true;
                } else if (user.isSneaking()) {
                    stack.set(RNDataComponents.ASHIRO, new AshiroComponent(user.getWorld().getRegistryKey(), user.getPos()));

                    success = true;
                }

                if (success) {
                    user.getItemCooldownManager().set(stack.getItem(), 20);
                    return TypedActionResult.success(user.getStackInHand(hand));
                }
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

        return component.bladeType() == KatanaComponent.BladeType.KATANA
                ? MiscUtils.isGui(renderMode) ? katanaId : katanaId.withSuffixedPath("_in_hand")
                : component.bladeType() == KatanaComponent.BladeType.SHEATHED
                  ? MiscUtils.isGui(renderMode) ? sheathedId : sheathedId.withSuffixedPath("_in_hand")
                  : MiscUtils.isGui(renderMode) ? sheathId : sheathId.withSuffixedPath("_in_hand");
    }

    public List<Identifier> getModelsToLoad() {
        Identifier itemId = Registries.ITEM.getId(this);
        ItemStack stack = this.getDefaultStack();
        KatanaComponent component = KatanaComponent.get(stack);

        return Arrays.asList(
                itemId.withPath(component.type().id + "_katana"),
                itemId.withPath(component.type().id + "_katana_in_hand"),

                itemId.withPath("sheathed_" + component.type().id + "_katana"),
                itemId.withPath("sheathed_" + component.type().id + "_katana_in_hand"),

                itemId.withPath(component.type().id + "_sheath"),
                itemId.withPath(component.type().id + "_sheath_in_hand")
        );
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        ItemStack cursorStack = cursorStackReference.get();

        KatanaComponent component = KatanaComponent.get(stack);
        KatanaComponent cursorComponent = KatanaComponent.get(cursorStack.copy());

        if (clickType == ClickType.RIGHT
                && component.bladeType() == KatanaComponent.BladeType.SHEATH
                && cursorStack.contains(RNDataComponents.KATANA) && cursorComponent.bladeType() == KatanaComponent.BladeType.KATANA
        ) {
            if (player.getInventory().contains(itemStack -> component.material().isEmpty() || component.material().test(itemStack))) {
                cursorStack.decrement(1);
                stack.set(RNDataComponents.KATANA, component.withBladeType(KatanaComponent.BladeType.SHEATHED));

                for (int i = 0; i < player.getInventory().size(); i++) {
                    ItemStack itemStack = player.getInventory().getStack(i);
                    if (component.material().isEmpty() || component.material().test(itemStack)) {
                        itemStack.decrementUnlessCreative(1, player);
                        break;
                    }
                }

                if (player.getWorld().isClient) {
                    player.playSound(RNSounds.KATANA_SHEATHE, 1.0F, (float) (1.0F + player.getRandom().nextGaussian() / 10.0F));
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

    public int startColor(ItemStack stack) {
        Vector4i colors = stack.getOrDefault(RNDataComponents.COLORS, new Vector4i());
        return colors.y;
    }

    public int endColor(ItemStack stack) {
        Vector4i colors = stack.getOrDefault(RNDataComponents.COLORS, new Vector4i());
        return colors.z;
    }

    public int backgroundColor(ItemStack stack) {
        Vector4i colors = stack.getOrDefault(RNDataComponents.COLORS, new Vector4i());
        return colors.w;
    }

    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem() || KatanaComponent.get(oldStack).bladeType() != KatanaComponent.get(newStack).bladeType();
    }
}
