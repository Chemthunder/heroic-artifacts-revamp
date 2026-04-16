package silly.chemthunder.redemption.impl.item.katana;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.acoyt.acornlib.api.item.CustomKillSourceItem;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import silly.chemthunder.redemption.impl.Redemption;
import silly.chemthunder.redemption.impl.cca.entity.JudgementComponent;
import silly.chemthunder.redemption.impl.index.data.RedemptionDamageTypes;
import silly.chemthunder.redemption.impl.util.KatanaType;

import java.util.Arrays;
import java.util.List;

public class KatanaItem extends SwordItem implements ColorableItem, CustomKillSourceItem, ModelVaryingItem {
    public KatanaItem(Settings settings) {
        super(ToolMaterials.NETHERITE, settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 6.5f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.3f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(Identifier.ofVanilla("base_entity_interaction_range"), 0.5f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public DamageSource getKillSource(LivingEntity living) {
        return RedemptionDamageTypes.create(living.getWorld(), RedemptionDamageTypes.KATANA);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (JudgementComponent.KEY.get(user).isJudgement()) {
            user.setVelocity(user.getRotationVec(0).multiply(3));
            user.velocityModified = true;

            user.useRiptide(10, 7f, user.getStackInHand(user.getActiveHand()));
            user.getItemCooldownManager().set(this, 400);

            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return super.use(world, user, hand);
    }

    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity entity) {
        Identifier id = Redemption.id(KatanaType.getForItem(this).id + "_katana");
        return id.withSuffixedPath(MiscUtils.isGui(renderMode) ? "" : "_in_hand");
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
                Redemption.id("lapis_katana_in_hand")
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
