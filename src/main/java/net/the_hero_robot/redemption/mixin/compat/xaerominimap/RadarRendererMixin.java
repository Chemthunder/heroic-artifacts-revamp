package net.the_hero_robot.redemption.mixin.compat.xaerominimap;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.entity.Entity;
import net.the_hero_robot.redemption.impl.cca.entity.EnshroudedComponent;
import org.spongepowered.asm.mixin.Mixin;
import xaero.hud.minimap.element.render.MinimapElementRenderInfo;
import xaero.hud.minimap.radar.render.element.RadarRenderer;

/**
 * @author AcoYT
 */
@Environment(EnvType.CLIENT)
@Mixin(value = RadarRenderer.class, remap = false)
public abstract class RadarRendererMixin {
    @WrapMethod(method = "renderElement(Lnet/minecraft/entity/Entity;ZZDFDDLxaero/hud/minimap/element/render/MinimapElementRenderInfo;Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;)Z")
    private boolean redemption$cancelHunterIconRender(Entity e, boolean highlighted, boolean outOfBounds, double optionalDepth, float optionalScale, double partialX, double partialY, MinimapElementRenderInfo renderInfo, DrawContext guiGraphics, VertexConsumerProvider.Immediate vanillaBufferSource, Operation<Boolean> original) {
        if (EnshroudedComponent.isShrouded(e)) return false;
        return original.call(e, highlighted, outOfBounds, optionalDepth, optionalScale, partialX, partialY, renderInfo, guiGraphics, vanillaBufferSource);
    }
}
