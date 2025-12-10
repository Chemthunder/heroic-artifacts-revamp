package silly.chemthunder.redemption.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
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

public class SheathItem extends Item implements ColorableItem {
    public SheathItem(Settings settings) {
        super(settings);
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
                        EntityAttributes.GENERIC_ARMOR,
                        new EntityAttributeModifier(Identifier.ofVanilla("generic_armor"), 2.5f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.OFFHAND
                )
                .add(
                        EntityAttributes.GENERIC_MOVEMENT_SPEED,
                        new EntityAttributeModifier(Identifier.ofVanilla("generic_movement_speed"), 0.1f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.OFFHAND
                )
                .build();
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }
}
