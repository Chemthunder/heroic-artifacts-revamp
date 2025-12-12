package silly.chemthunder.redemption.index;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import silly.chemthunder.redemption.Redemption;
import silly.chemthunder.redemption.client.entity.BindingHexEntityModel;
import silly.chemthunder.redemption.client.entity.BindingHexRenderer;
import silly.chemthunder.redemption.entity.BindingHexEntity;

public interface RedemptionEntities {
    EntityType<BindingHexEntity> BINDING_HEX = create(
            "binding_hex",
            EntityType.Builder.create(
                    BindingHexEntity::new,
                    SpawnGroup.MISC
            ).dimensions(0.6f, 0.3f)
    );

    static <T extends Entity> EntityType<T> create(String name, EntityType.Builder<T> builder) {
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Redemption.id(name));
        return Registry.register(Registries.ENTITY_TYPE, key.getValue(), builder.build(String.valueOf(key)));
    }

    static void index() {
//        // Entities are Registered Statically
//        FabricDefaultAttributeRegistry.register(MOTH, MothEntity.createAttribute());
//        FabricDefaultAttributeRegistry.register(MOTH_SATIN, MothEntity.createAttribute());
//        FabricDefaultAttributeRegistry.register(DUSTY_MOTH, MothEntity.createAttribute());
//        FabricDefaultAttributeRegistry.register(MOTH_REDHEAD, MothEntity.createAttribute());

        FabricDefaultAttributeRegistry.register(BINDING_HEX, BindingHexEntity.createAttribute());
    }

    static void clientIndex() {
//        EntityModelLayerRegistry.registerModelLayer(MothModelEntity.MOTH_MODEL, MothModelEntity::getTexturedModelData);
//        EntityRendererRegistry.register(MOTH, MothEntityRenderer::new);
//        EntityRendererRegistry.register(MOTH_SATIN, MothEntityRenderer::new);
//        EntityRendererRegistry.register(MOTH_REDHEAD, MothEntityRenderer::new);
//        EntityRendererRegistry.register(DUSTY_MOTH, MothEntityRenderer::new);
        EntityRendererRegistry.register(BINDING_HEX, BindingHexRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BindingHexEntityModel.MOTH_MODEL, BindingHexEntityModel::getTexturedModelData);
    }
}
