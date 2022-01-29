package dozono.archerymod.Renderer;

import dozono.archerymod.entity.LightningArrowEntity;
import dozono.archerymod.entity.TeleportArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class TeleportArrowRenderer extends ArrowRenderer<TeleportArrowEntity> {
    public static final ResourceLocation NORMAL_ARROW_LOCATION = new ResourceLocation("minecraft:textures/entity/projectiles/arrow.png");

    public TeleportArrowRenderer(EntityRendererManager p_i46193_1_) {
        super(p_i46193_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(TeleportArrowEntity p_110775_1_) {
        return NORMAL_ARROW_LOCATION;
    }
}
