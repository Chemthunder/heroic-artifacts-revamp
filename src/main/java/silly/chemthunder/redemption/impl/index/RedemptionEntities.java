package silly.chemthunder.redemption.impl.index;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import silly.chemthunder.redemption.impl.Redemption;
import silly.chemthunder.redemption.impl.entity.ImmolationFlamesEntity;

public interface RedemptionEntities {
    EntityType<ImmolationFlamesEntity> IMMOLATION_FLAMES = register("immolation_flames",
            EntityType.Builder.<ImmolationFlamesEntity>create(ImmolationFlamesEntity::new, SpawnGroup.MISC)
                    .dimensions(0.7f, 0.7f));

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, Redemption.id(name), builder.build());
    }

    static void init() {}

    static void clientInit() {
        EntityRendererRegistry.register(IMMOLATION_FLAMES, EmptyEntityRenderer::new);
    }
}
