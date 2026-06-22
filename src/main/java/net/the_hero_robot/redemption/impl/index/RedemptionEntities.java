package net.the_hero_robot.redemption.impl.index;

import net.acoyt.acornlib.api.registrants.EntityTypeRegistrant;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.entity.DragonSwordFireballEntity;
import net.the_hero_robot.redemption.impl.entity.ImmolationFlamesEntity;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public interface RedemptionEntities {
    EntityTypeRegistrant ENTITIES = new EntityTypeRegistrant(Redemption.MOD_ID);

    EntityType<ImmolationFlamesEntity> IMMOLATION_FLAMES = ENTITIES.register("immolation_flames",
            EntityType.Builder.<ImmolationFlamesEntity>create(ImmolationFlamesEntity::new, SpawnGroup.MISC)
                    .dimensions(0.7f, 0.7f)
                    .maxTrackingRange(4)
                    .trackingTickInterval(10));

    EntityType<DragonSwordFireballEntity> DRAGON_SWORD_FIREBALL = ENTITIES.register("dragon_sword_fireball",
            EntityType.Builder.<DragonSwordFireballEntity>create(DragonSwordFireballEntity::new, SpawnGroup.MISC)
                    .dimensions(1.0F, 1.0F)
                    .maxTrackingRange(4)
                    .trackingTickInterval(10));

    static void init() {}

    static void clientInit() {
        EntityRendererRegistry.register(IMMOLATION_FLAMES, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(DRAGON_SWORD_FIREBALL, context -> new FlyingItemEntityRenderer<>(context, 3.0F, true));
    }
}
