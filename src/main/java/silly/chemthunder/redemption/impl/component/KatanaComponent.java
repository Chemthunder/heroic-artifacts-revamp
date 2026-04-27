package silly.chemthunder.redemption.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import silly.chemthunder.redemption.impl.index.RedemptionDataComponents;
import silly.chemthunder.redemption.impl.util.KatanaType;

public record KatanaComponent(boolean sheathed, boolean sheath, KatanaType type, Ingredient material) {
    public static final KatanaComponent DEFAULT = new KatanaComponent(false, false, KatanaType.AMETHYST, Ingredient.ofItems(Items.AMETHYST_SHARD));

    public static final Codec<KatanaComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.optionalFieldOf("sheathed", false).forGetter(KatanaComponent::sheathed),
            Codec.BOOL.optionalFieldOf("sheath", false).forGetter(KatanaComponent::sheath),
            KatanaType.CODEC.optionalFieldOf("type", KatanaType.AMETHYST).forGetter(KatanaComponent::type),
            Ingredient.ALLOW_EMPTY_CODEC.optionalFieldOf("material", Ingredient.ofItems(Items.AMETHYST_SHARD)).forGetter(KatanaComponent::material)
    ).apply(instance, KatanaComponent::new));

    public static final PacketCodec<ByteBuf, KatanaComponent> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public static KatanaComponent get(ItemStack stack) {
        return stack.getOrDefault(RedemptionDataComponents.KATANA, DEFAULT);
    }

    public KatanaComponent withBladeType(BladeType bladeType) {
        return new KatanaComponent(bladeType.sheathed, bladeType.sheath, this.type, this.material);
    }

    public BladeType getBladeType() {
        for (BladeType bladeType : BladeType.values()) {
            if (bladeType.sheathed == this.sheathed && bladeType.sheath == this.sheath) {
                return bladeType;
            }
        }

        return BladeType.KATANA;
    }

    public enum BladeType {
        KATANA(false, false),
        SHEATH(false, true),
        SHEATHED(true, false);

        private final boolean sheathed;
        private final boolean sheath;

        BladeType(boolean sheathed, boolean sheath) {
            this.sheathed = sheathed;
            this.sheath = sheath;
        }

        public boolean isSheathed() {
            return this.sheathed;
        }

        public boolean isSheath() {
            return this.sheath;
        }
    }
}
