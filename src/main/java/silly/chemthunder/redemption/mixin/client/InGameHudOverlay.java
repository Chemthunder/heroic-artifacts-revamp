package silly.chemthunder.redemption.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import silly.chemthunder.redemption.impl.Redemption;
import silly.chemthunder.redemption.impl.cca.entity.JudgementComponent;
import silly.chemthunder.redemption.impl.cca.entity.flash.FlashComponent;
import silly.chemthunder.redemption.impl.cca.entity.flash.JudgementFlashComponent;

@Mixin(InGameHud.class)
public abstract class InGameHudOverlay {
    @Shadow protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

    @Unique private static final Identifier JUDGE_FLASH = Redemption.id("textures/gui/judge_flash.png");
    @Unique private static final Identifier FLASH = Redemption.id("textures/gui/flash.png");
    @Unique private static final Identifier CUSTOM_HOTBAR = Redemption.id("hud/custom_hotbar");
    @Unique private static final Identifier CUSTOM_HOTBAR_SELECTOR = Redemption.id("hud/custom_hotbar_selection");

    @Inject(method = "renderMiscOverlays", at = @At("TAIL"))
    private void redemption$flowerOverlay(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity player) {
            int judgeFlashTicks = JudgementFlashComponent.KEY.get(player).getFlashTicks();
            if (judgeFlashTicks > 0) {
                float opacity = judgeFlashTicks > 50 ? 1f : judgeFlashTicks / 50.0f;
                this.renderOverlay(context, JUDGE_FLASH, opacity);
            }

            int flashTicks = FlashComponent.KEY.get(player).getFlashTicks();
            if (flashTicks > 0) {
                float opacity = flashTicks > 50 ? 1f : flashTicks / 50.0f;
                this.renderOverlay(context, FLASH, opacity);
            }
        }
    }

    @WrapOperation(
            method = "renderHotbar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
                    ordinal = 0
            )
    )
    private void redemption$textureHotbar(DrawContext instance, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        original.call(instance, player != null && JudgementComponent.KEY.get(player).isJudgement() ? CUSTOM_HOTBAR : texture, x, y, width, height);
    }

    @WrapOperation(
            method = "renderHotbar",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V",
                    ordinal = 1
            )
    )
    private void redemption$textureSelector(DrawContext instance, Identifier texture, int x, int y, int width, int height, Operation<Void> original) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        original.call(instance, player != null && JudgementComponent.KEY.get(player).isJudgement() ? CUSTOM_HOTBAR_SELECTOR : texture, x, y, width, height);
    }
}
