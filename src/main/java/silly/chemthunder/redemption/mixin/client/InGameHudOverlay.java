package silly.chemthunder.redemption.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
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
import silly.chemthunder.redemption.cca.FlashComponent;
import silly.chemthunder.redemption.cca.JudgementFlashComponent;
import silly.chemthunder.redemption.cca.JudgementPlayerComponent;

@Mixin(InGameHud.class)
public abstract class InGameHudOverlay {
    @Shadow
    protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);
    @Unique
    private static final Identifier JUDGE_FLASH = Redemption.id("textures/gui/judge_flash.png");
    @Unique
    private static final Identifier FLASH = Redemption.id("textures/gui/flash.png");

    @Unique private static final Identifier CUSTOM_HOTBAR = Redemption.id("hud/custom_hotbar");
    @Unique private static final Identifier CUSTOM_HOTBAR_SELECTOR = Redemption.id("hud/custom_hotbar_selection");

    @Inject(method = "renderMiscOverlays", at = @At("TAIL"))
    private void flowerOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity player) {
            int enfloweredTicks = JudgementFlashComponent.KEY.get(player).flashTicks;
            if (enfloweredTicks > 0) {
                float opacity = enfloweredTicks > 50 ? 1f : enfloweredTicks / 50.0f;
                this.renderOverlay(context, JUDGE_FLASH, opacity);
            }

            int flashTicksYay = FlashComponent.KEY.get(player).flashTicks;
            if (flashTicksYay > 0) {
                float flashopacity = flashTicksYay > 50 ? 1f : flashTicksYay / 50.0f;
                this.renderOverlay(context, FLASH, flashopacity);
            }
        }
    }

    @WrapOperation(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0))
    private void textureHotbar(DrawContext instance, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
        assert MinecraftClient.getInstance().player != null;
        if (JudgementPlayerComponent.KEY.get(MinecraftClient.getInstance().player).isJudgement) {
            original.call(instance, CUSTOM_HOTBAR, x, y, width, height);
        } else {
            original.call(instance, texture, x, y, width, height);
        }
    }

    @WrapOperation(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1))
    private void textureSelector(DrawContext instance, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
        assert MinecraftClient.getInstance().player != null;
        if (JudgementPlayerComponent.KEY.get(MinecraftClient.getInstance().player).isJudgement) {
            original.call(instance, CUSTOM_HOTBAR_SELECTOR, x, y, width, height);
        } else {
            original.call(instance, texture, x, y, width, height);
        }
    }
}
