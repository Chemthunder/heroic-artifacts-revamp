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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import silly.chemthunder.redemption.impl.Redemption;
import silly.chemthunder.redemption.impl.util.KatanaType;

import java.util.Arrays;
import java.util.List;

public class SheathItem extends Item implements ColorableItem, ModelVaryingItem {
    public SheathItem(Settings settings) {
        super(settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ARMOR,
                        new EntityAttributeModifier(Identifier.ofVanilla("generic_armor"), 1.5f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.OFFHAND
                )
                .add(
                        EntityAttributes.GENERIC_MOVEMENT_SPEED,
                        new EntityAttributeModifier(Identifier.ofVanilla("generic_movement_speed"), 0.02f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.OFFHAND
                )
                .build();
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity entity) {
        Identifier id = Redemption.id(KatanaType.getForItem(this).id + "_sheath");
        return id.withSuffixedPath(MiscUtils.isGui(renderMode) ? "" : "_in_hand");
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
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
                Redemption.id("lapis_sheath_in_hand")
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
