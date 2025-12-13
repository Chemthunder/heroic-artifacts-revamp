package silly.chemthunder.redemption.index;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import silly.chemthunder.redemption.Redemption;

public class RedemptionModelPredicates {
    public static void index() {
        ModelPredicateProviderRegistry.register(RedemptionItems.SILENT_KEY, Redemption.id("awakened"),
                ((stack, world, entity, seed) -> stack.get(RedemptionDataComponents.IS_AWAKENED) != null ? -1f : 0f));
    }
}
