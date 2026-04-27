package silly.chemthunder.redemption.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.CommandManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author AcoYT
 */
@Mixin(CommandManager.class)
public class CommandManagerMixin {
    @ModifyExpressionValue(
            method = "<init>",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/SharedConstants;isDevelopment:Z"
            )
    )
    private boolean redemption$whyTheFuckIsThisHere(boolean original) {
        return original || FabricLoader.getInstance().isDevelopmentEnvironment();
    }
}
