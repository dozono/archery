package dozono.archerymod;

import dozono.archerymod.Renderer.*;
import dozono.archerymod.entity.AccurateArrowEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArcheryMod.MODID)
public class ClientSetup {
    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ArcheryMod.FLAME_ARROW_ENTITY.get(), FlameArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ArcheryMod.LIGHTNING_ARROW_ENTITY.get(), LightningArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ArcheryMod.BOUNCE_ARROW_ENTITY.get(), BounceArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ArcheryMod.EXPLOSIVE_ARROW_ENTITY.get(), ExplosiveArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ArcheryMod.ACCURATE_ARROW_ENTITY.get(), AccurateArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ArcheryMod.TELEPORT_ARROW_ENTITY.get(), TeleportArrowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ArcheryMod.FREEZE_ARROW_ENTITY.get(), FreezeArrowRenderer::new);
    }
}





