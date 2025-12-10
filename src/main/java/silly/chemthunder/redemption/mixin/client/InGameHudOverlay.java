package silly.chemthunder.redemption.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import silly.chemthunder.redemption.Redemption;
import silly.chemthunder.redemption.cca.JudgementFlashComponent;

@Mixin(InGameHud.class)
public abstract class InGameHudOverlay {
    @Shadow
    protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);
    @Unique
    private static final Identifier FLASH = Redemption.id("textures/gui/flash.png");

    @Inject(method = "renderMiscOverlays", at = @At("TAIL"))
    private void flowerOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity player) {
            int enfloweredTicks = JudgementFlashComponent.KEY.get(player).flashTicks;
            if (enfloweredTicks > 0) {
                float opacity = enfloweredTicks > 50 ? 1f : enfloweredTicks / 50.0f;
                this.renderOverlay(context, FLASH, opacity);
            }
        }
    }
}
