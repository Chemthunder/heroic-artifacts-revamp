package net.the_hero_robot.redemption.impl.index;

import net.acoyt.acornlib.api.registrants.DataComponentTypeRegistrant;
import net.minecraft.component.ComponentType;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.arcane_kings.evocation.EvocationSpell;
import net.the_hero_robot.redemption.impl.component.AshiroComponent;
import net.the_hero_robot.redemption.impl.component.KatanaComponent;
import net.the_hero_robot.redemption.impl.util.RNUtil;
import org.joml.Vector4i;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public interface RNDataComponents {
    DataComponentTypeRegistrant COMPONENTS = new DataComponentTypeRegistrant(Redemption.MOD_ID);

    ComponentType<AshiroComponent> ASHIRO = COMPONENTS.register("ashiro", AshiroComponent.CODEC, AshiroComponent.PACKET_CODEC);
    ComponentType<KatanaComponent> KATANA = COMPONENTS.register("katana", KatanaComponent.CODEC, KatanaComponent.PACKET_CODEC);
    ComponentType<Vector4i> COLORS = COMPONENTS.register("colors", RNUtil.VECTOR_4I_CODEC, RNUtil.VECTOR_4I_PACKET_CODEC);
    ComponentType<EvocationSpell> EVOCATION_SPELL = COMPONENTS.register("evocation_spell", EvocationSpell.CODEC, EvocationSpell.PACKET_CODEC);

    static void init() {}
}
