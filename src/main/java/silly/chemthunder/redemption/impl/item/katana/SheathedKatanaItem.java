package silly.chemthunder.redemption.impl.item.katana;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import silly.chemthunder.redemption.impl.Redemption;
import silly.chemthunder.redemption.impl.index.RedemptionSoundEvents;
import silly.chemthunder.redemption.impl.util.KatanaType;

import java.util.Arrays;
import java.util.List;

public class SheathedKatanaItem extends Item implements ColorableItem, ModelVaryingItem {
    public SheathedKatanaItem(Settings settings) {
        super(settings);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        KatanaType katanaType = KatanaType.getForItem(stack.getItem());

        ItemStack mainStack = new ItemStack(katanaType.katana);
        ItemStack offStack = new ItemStack(katanaType.sheath);
        List<StatusEffectInstance> effects = katanaType.effectInstances;

        user.setStackInHand(Hand.MAIN_HAND, mainStack);
        user.getInventory().insertStack(PlayerInventory.OFF_HAND_SLOT, offStack);
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
        user.playSound(RedemptionSoundEvents.UNSHEATHE);

        return TypedActionResult.success(user.getStackInHand(hand), world.isClient);
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

    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity entity) {
        Identifier id = Redemption.id(KatanaType.getForItem(this).id + "_sheathed");
        return id.withSuffixedPath(MiscUtils.isGui(renderMode) ? "" : "_in_hand");
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Redemption.id("amethyst_sheathed"),
                Redemption.id("amethyst_sheathed_in_hand"),

                Redemption.id("redstone_sheathed"),
                Redemption.id("redstone_sheathed_in_hand"),

                Redemption.id("sculk_sheathed"),
                Redemption.id("sculk_sheathed_in_hand"),

                Redemption.id("quartz_sheathed"),
                Redemption.id("quartz_sheathed_in_hand"),

                Redemption.id("emerald_sheathed"),
                Redemption.id("emerald_sheathed_in_hand"),

                Redemption.id("copper_sheathed"),
                Redemption.id("copper_sheathed_in_hand"),

                Redemption.id("netherite_sheathed"),
                Redemption.id("netherite_sheathed_in_hand"),

                Redemption.id("lapis_sheathed"),
                Redemption.id("lapis_sheathed_in_hand")
        );
    }

    public int startColor(ItemStack itemStack) {
        return 0xFF6e5353;
    }

    public int endColor(ItemStack itemStack) {
        return 0xFF271e1e;
    }

    public int backgroundColor(ItemStack itemStack) {
        return 0xFF1d1212;
    }
}
