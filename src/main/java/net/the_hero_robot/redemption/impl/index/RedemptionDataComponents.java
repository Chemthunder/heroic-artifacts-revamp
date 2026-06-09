package net.the_hero_robot.redemption.impl.index;

import net.acoyt.acornlib.api.registrants.ComponentTypeRegistrant;
import net.minecraft.component.ComponentType;
import net.the_hero_robot.redemption.impl.Redemption;
import net.the_hero_robot.redemption.impl.component.AshiroComponent;
import net.the_hero_robot.redemption.impl.component.KatanaComponent;
import net.the_hero_robot.redemption.impl.util.ModUtil;
import org.joml.Vector4i;

/**
 * @author AcoYT
 * @author Chemthunder
 */
public interface RedemptionDataComponents {
    ComponentTypeRegistrant COMPONENTS = new ComponentTypeRegistrant(Redemption.MOD_ID);

    ComponentType<AshiroComponent> ASHIRO = COMPONENTS.register("ashiro", AshiroComponent.CODEC, AshiroComponent.PACKET_CODEC);
    ComponentType<KatanaComponent> KATANA = COMPONENTS.register("katana", KatanaComponent.CODEC, KatanaComponent.PACKET_CODEC);
    ComponentType<Vector4i> COLORS = COMPONENTS.register("colors", ModUtil.VECTOR_4I_CODEC, ModUtil.VECTOR_4I_PACKET_CODEC);

    static void init() {}
}
