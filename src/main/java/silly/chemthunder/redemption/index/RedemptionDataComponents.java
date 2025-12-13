package silly.chemthunder.redemption.index;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import silly.chemthunder.redemption.Redemption;

import java.util.LinkedHashMap;
import java.util.Map;

public interface RedemptionDataComponents {
    Map<ComponentType<?>, Identifier> COMPONENTS = new LinkedHashMap<>();

    ComponentType<BlockPos> EYE_OF_THE_STORM = create("eye_of_the_storm", new ComponentType.Builder<BlockPos>()
            .codec(BlockPos.CODEC)
            .build());

    ComponentType<Integer> IS_AWAKENED = create("is_awakened", new ComponentType.Builder<Integer>()
            .codec(Codec.INT)
            .build()
    );

    static <T extends ComponentType<?>> T create(String name, T component) {
        COMPONENTS.put(component, Redemption.id(name));
        return component;
    }

    static void index() {
        COMPONENTS.keySet().forEach(component -> {
            Registry.register(Registries.DATA_COMPONENT_TYPE, COMPONENTS.get(component), component);
        });
    }
}
