package net.theherorobot.redemption.impl.util;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.joml.Vector4i;
import net.theherorobot.redemption.impl.component.KatanaComponent;

import java.util.List;

/**
 * @author AcoYT
 */
public class ModUtil {
    public static final Codec<Vector4i> VECTOR_4I_CODEC = Codec.INT.listOf()
            .comapFlatMap(list ->
                    Util.decodeFixedLengthList(list, 4).map(listX -> new Vector4i(
                            listX.getFirst(), listX.get(1), listX.get(2), listX.get(3))
                    ), vec4i -> List.of(vec4i.x(), vec4i.y(), vec4i.z(), vec4i.w())
            );

    public static final PacketCodec<ByteBuf, Vector4i> VECTOR_4I_PACKET_CODEC = PacketCodecs.codec(VECTOR_4I_CODEC);

    public static <T> ItemStack copy(ItemStack stack, ComponentType<T> type, T value) {
        ItemStack itemStack = stack.copy();
        itemStack.set(type, value);
        return itemStack;
    }

    public static Identifier formatKatanaId(ItemStack stack, boolean itemPrefix) {
        KatanaComponent component = KatanaComponent.get(stack);
        return formatKatanaId(stack.getItem(), component.getBladeType(), component.type(), itemPrefix);
    }

    public static Identifier formatKatanaId(Item item, KatanaComponent.BladeType bladeType, KatanaType katanaType, boolean itemPrefix) {
        Identifier itemId = Registries.ITEM.getId(item);

        if (bladeType == KatanaComponent.BladeType.KATANA) {
            return itemId.withPath(katanaType.id + "_katana").withPrefixedPath(itemPrefix ? "item/" : "");
        } else if (bladeType == KatanaComponent.BladeType.SHEATH) {
            return itemId.withPath(katanaType.id + "_sheath").withPrefixedPath(itemPrefix ? "item/" : "");
        } else {
            return itemId.withPath("sheathed_" + katanaType.id + "_katana").withPrefixedPath(itemPrefix ? "item/" : "");
        }
    }
}
