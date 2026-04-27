package silly.chemthunder.redemption.impl.index;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.joml.Vector4i;
import silly.chemthunder.redemption.impl.Redemption;
import silly.chemthunder.redemption.impl.component.AshiroComponent;
import silly.chemthunder.redemption.impl.component.KatanaComponent;
import silly.chemthunder.redemption.impl.util.ModUtil;

public interface RedemptionDataComponents {
    ComponentType<AshiroComponent> ASHIRO = register("ashiro", AshiroComponent.CODEC, AshiroComponent.PACKET_CODEC);
    ComponentType<KatanaComponent> KATANA = register("katana", KatanaComponent.CODEC, KatanaComponent.PACKET_CODEC);
    ComponentType<Vector4i> COLORS = register("colors", ModUtil.VECTOR_4I_CODEC, ModUtil.VECTOR_4I_PACKET_CODEC);

    static <T> ComponentType<T> register(String id, Codec<T> codec, PacketCodec<? super RegistryByteBuf, T> packetCodec) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Redemption.id(id), ComponentType.<T>builder()
                .codec(codec)
                .packetCodec(packetCodec)
                .build());
    }

    static void init() {}
}
