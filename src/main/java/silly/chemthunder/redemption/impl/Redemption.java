package silly.chemthunder.redemption.impl;

import net.acoyt.acornlib.api.ALib;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import silly.chemthunder.redemption.impl.index.*;

public class Redemption implements ModInitializer {
    public static final String MOD_ID = "redemption";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public void onInitialize() {
        /* AcornLib */
        ALib.registerModMenu(MOD_ID, 0xFFe95050);

        /* Initialization */
        RedemptionItems.init();
        RedemptionItemGroups.init();
        RedemptionSounds.init();
        RedemptionParticles.init();
        RedemptionDataComponents.init();
        RedemptionEntities.init();

        /* Networking */
        RedemptionNetworking.registerTypes();
        RedemptionNetworking.registerC2SPackets();
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
