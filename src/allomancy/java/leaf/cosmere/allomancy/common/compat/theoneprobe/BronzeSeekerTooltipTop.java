package leaf.cosmere.allomancy.common.compat.theoneprobe;

import leaf.cosmere.allomancy.common.Allomancy;
import leaf.cosmere.allomancy.common.manifestation.AllomancyBronze;
import leaf.cosmere.allomancy.common.registries.AllomancyManifestations;
import leaf.cosmere.api.Metals;
import leaf.cosmere.api.manifestation.Manifestation;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import mcjty.theoneprobe.api.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public class BronzeSeekerTooltipTop implements IProbeInfoEntityProvider, Function<ITheOneProbe, Void> {

    static final BronzeSeekerTooltipTop INSTANCE = new BronzeSeekerTooltipTop();

    @Override
    public String getID() {
        return Allomancy.rl("bronze_seeker_tooltip").toString();
    }

    @Override
    public void addProbeEntityInfo(ProbeMode probeMode, IProbeInfo probeInfo, Player player, Level level, Entity entity, IProbeHitEntityData probeHitEntityData)
    {
        SpiritwebCapability.get(player).ifPresent(clientPlayer ->
        {
            AllomancyBronze allomancyBronze = (AllomancyBronze) AllomancyManifestations.ALLOMANCY_POWERS.get(Metals.MetalType.BRONZE).get();
            if (allomancyBronze.isMetalBurning(clientPlayer) || player.isCreative())
            {
                final LivingEntity targetEntity = (LivingEntity) entity;

                if (!AllomancyBronze.isValidSeekTarget(clientPlayer, targetEntity))
                {
                    return;
                }

                SpiritwebCapability.get(targetEntity).ifPresent(targetSpiritWeb ->
                {
                    final boolean targetIsPlayer = targetSpiritWeb.getLiving() instanceof Player;

                    for (Manifestation manifestation : targetSpiritWeb.getAvailableManifestations())
                    {
                        if (player.isCreative() || !targetIsPlayer || manifestation.isActive(targetSpiritWeb))
                        {
                            probeInfo.text(manifestation.getTextComponent().getString());
                        }
                    }
                });
            }
        });
    }

    @Override
    public Void apply(ITheOneProbe theOneProbe) {
        theOneProbe.registerEntityProvider(INSTANCE);
        return null;
    }

}
