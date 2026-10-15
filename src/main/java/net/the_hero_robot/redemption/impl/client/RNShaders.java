package net.the_hero_robot.redemption.impl.client;

import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostPipeline;
import foundry.veil.api.client.render.post.PostProcessingManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.cca.entity.FracturedComponent;

/**
 * @author AcoYT
 */
public class RNShaders {
    private static final Identifier FRACTURED = Redemption.id("fractured");

    public static float shiftX = 0.0F;
    public static float shiftY = 0.0F;

    public static float prevShiftX = 0.0F;
    public static float prevShiftY = 0.0F;

    public static FracturedComponent component = null;

    public static void init() {
        WorldRenderEvents.END.register(ctx -> {
            if (prevShiftX != shiftX || prevShiftY != shiftY) {
                runFracturedPipeline();
            }

            prevShiftX = shiftX;
            prevShiftY = shiftY;
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ClientPlayerEntity player = client.player;

            if (player == null && component != null) {
                component = null;
            }

            if (player != null) {
                component = FracturedComponent.KEY.get(player);
            }

            if (component != null) {
                shiftX = MathHelper.sin(component.getProgress());
            }
        });
    }

    private static void runFracturedPipeline() {
        PostProcessingManager postProcessingManager = VeilRenderSystem.renderer().getPostProcessingManager();
        PostPipeline postPipeline = postProcessingManager.getPipeline(FRACTURED);
        if (postPipeline == null) return;
        postPipeline.getUniformSafe("ShiftX").setFloat(shiftX);
        postPipeline.getUniformSafe("ShiftY").setFloat(shiftY);

        postProcessingManager.runPipeline(postPipeline);
    }
}
