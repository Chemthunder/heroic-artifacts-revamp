package net.the_hero_robot.redemption.impl.client;

import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostPipeline;
import foundry.veil.api.client.render.post.PostProcessingManager;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.util.Identifier;
import net.the_hero_robot.redemption.impl.Redemption;

/**
 * @author AcoYT
 */
public class RNShaders {
    private static final Identifier FRACTURED = Redemption.id("fractured");

    public static float shiftX = 0.0F;
    public static float shiftY = 0.0F;

    public static float prevShiftX = 0.0F;
    public static float prevShiftY = 0.0F;

    public static void init() {
        WorldRenderEvents.END.register(ctx -> {
            if (prevShiftX != shiftX || prevShiftY != shiftY) {
                runFracturedPipeline();
            }

            prevShiftX = shiftX;
            prevShiftY = shiftY;
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
