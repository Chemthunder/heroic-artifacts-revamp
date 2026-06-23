package net.the_hero_robot.redemption.impl;

import net.acoyt.acornlib.api.event.CustomRiptideEvent;
import net.acoyt.acornlib.api.event.PlayerOpacityEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.the_hero_robot.redemption.impl.event.client.JudgementRiptideEvent;
import net.the_hero_robot.redemption.impl.event.client.SelfTransparencyEvent;
import net.the_hero_robot.redemption.impl.index.RedemptionEntities;
import net.the_hero_robot.redemption.impl.index.RedemptionParticles;
import net.the_hero_robot.redemption.impl.networking.RedemptionNetworking;
import net.the_hero_robot.redemption.impl.util.RedemptionKeyBindings;

/**
 * @author AcoYT
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class RedemptionClient implements ClientModInitializer {
    public void onInitializeClient() {
        /* Initialization */
        ParticleFactoryRegistry.getInstance().register(RedemptionParticles.HUNTER_OMEN, EndRodParticle.Factory::new);

        EntityRendererRegistry.register(RedemptionEntities.IMMOLATION_FLAMES, EmptyEntityRenderer::new);
        EntityRendererRegistry.register(RedemptionEntities.DRAGON_SWORD_FIREBALL, context -> new FlyingItemEntityRenderer<>(context, 3.0F, true));

        RedemptionKeyBindings.register();

        /* Networking */
        RedemptionNetworking.registerS2CPackets();

        /* Events */
        CustomRiptideEvent.EVENT.register(new JudgementRiptideEvent());
        PlayerOpacityEvent.EVENT.register(new SelfTransparencyEvent());
    }
}
