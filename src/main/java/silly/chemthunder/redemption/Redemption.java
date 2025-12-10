package silly.chemthunder.redemption;

import net.acoyt.acornlib.api.ALib;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import silly.chemthunder.redemption.cca.EnshroudedPlayerComponent;
import silly.chemthunder.redemption.index.RedemptionItemGroups;
import silly.chemthunder.redemption.index.RedemptionItems;
import silly.chemthunder.redemption.index.RedemptionParticles;
import silly.chemthunder.redemption.index.RedemptionSoundEvents;

public class Redemption implements ModInitializer {
	public static final String MOD_ID = "redemption";

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



	@Override
	public void onInitialize() {
        RedemptionItems.index();
        RedemptionItemGroups.index();
        RedemptionSoundEvents.index();
        RedemptionParticles.index();

		LOGGER.info("Redemption is running!");

        // alib
        ALib.registerModMenu(MOD_ID, 0xe95050);
	}
}