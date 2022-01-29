package dozono.archerymod.Renderer;

import dozono.archerymod.entity.FreezeArrowEntity;
import dozono.archerymod.entity.LightningArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class FreezeArrowRenderer extends ArrowRenderer<FreezeArrowEntity> {
    public static final ResourceLocation NORMAL_ARROW_LOCATION = new ResourceLocation("minecraft:textures/entity/projectiles/arrow.png");

    public FreezeArrowRenderer(EntityRendererManager p_i46193_1_) {
        super(p_i46193_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(FreezeArrowEntity p_110775_1_) {
        return NORMAL_ARROW_LOCATION;
    }
}
