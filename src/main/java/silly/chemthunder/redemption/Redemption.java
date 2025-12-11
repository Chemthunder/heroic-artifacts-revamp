package silly.chemthunder.redemption;

import net.acoyt.acornlib.api.ALib;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import silly.chemthunder.redemption.cca.EnshroudedPlayerComponent;
import silly.chemthunder.redemption.index.*;

import java.util.*;

public class Redemption implements ModInitializer {
	public static final String MOD_ID = "redemption";

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final ArrayList<BlockPos> STORM_EYES = new ArrayList<>();


	@Override
	public void onInitialize() {
        RedemptionItems.index();
        RedemptionItemGroups.index();
        RedemptionSoundEvents.index();
        RedemptionParticles.index();
        RedemptionDataComponents.index();
        RedemptionBlocks.index();
        RedemptionBlockEntities.index();

		LOGGER.info("Redemption is running!");

        // alib
        ALib.registerModMenu(MOD_ID, 0xe95050);
	}
}