package leaf.cosmere.allomancy.common.compat.theoneprobe;

import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public class TheOneProbeCompat {
    public static void interModEnqueue(InterModEnqueueEvent event) {
        InterModComms.sendTo("theoneprobe", "getTheOneProbe", () -> BronzeSeekerTooltipTop.BRONZE_SEEKER_TOP);
    }
}
