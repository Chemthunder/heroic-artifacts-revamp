package net.the_hero_robot.redemption.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.StringIdentifiable;
import net.the_hero_robot.redemption.impl.index.RedemptionDataComponents;
import net.the_hero_robot.redemption.impl.util.KatanaType;

/**
 * @author AcoYT
 */
public record KatanaComponent(BladeType bladeType, KatanaType type, Ingredient material) {
    public static final KatanaComponent DEFAULT = new KatanaComponent(BladeType.KATANA, KatanaType.AMETHYST, Ingredient.ofItems(Items.AMETHYST_SHARD));

    public static final Codec<KatanaComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BladeType.CODEC.optionalFieldOf("bladeType", BladeType.KATANA).forGetter(KatanaComponent::bladeType),
            KatanaType.CODEC.optionalFieldOf("type", KatanaType.AMETHYST).forGetter(KatanaComponent::type),
            Ingredient.ALLOW_EMPTY_CODEC.optionalFieldOf("material", Ingredient.ofItems(Items.AMETHYST_SHARD)).forGetter(KatanaComponent::material)
    ).apply(instance, KatanaComponent::new));

    public static final PacketCodec<ByteBuf, KatanaComponent> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public static KatanaComponent get(ItemStack stack) {
        return stack.getOrDefault(RedemptionDataComponents.KATANA, DEFAULT);
    }

    public KatanaComponent withBladeType(BladeType bladeType) {
        return new KatanaComponent(bladeType, this.type, this.material);
    }

    public enum BladeType implements StringIdentifiable {
        KATANA("katana", false, false),
        SHEATH("sheath", false, true),
        SHEATHED("sheathed", true, false);

        public static final Codec<BladeType> CODEC = StringIdentifiable.createCodec(BladeType::values);

        private final String id;
        private final boolean sheathed;
        private final boolean sheath;

        BladeType(String id, boolean sheathed, boolean sheath) {
            this.id = id;
            this.sheathed = sheathed;
            this.sheath = sheath;
        }

        public boolean isSheathed() {
            return this.sheathed;
        }

        public boolean isSheath() {
            return this.sheath;
        }

        public String asString() {
            return this.id;
        }
    }
}
