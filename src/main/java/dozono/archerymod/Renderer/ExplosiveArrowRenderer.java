package dozono.archerymod.Renderer;

import dozono.archerymod.entity.ExplosiveArrowEntity;
import dozono.archerymod.entity.FlameArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TippedArrowRenderer;
import net.minecraft.util.ResourceLocation;

public class ExplosiveArrowRenderer extends ArrowRenderer<ExplosiveArrowEntity> {
    public ExplosiveArrowRenderer(EntityRendererManager p_i46193_1_) {
        super(p_i46193_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(ExplosiveArrowEntity p_110775_1_) {
        return TippedArrowRenderer.NORMAL_ARROW_LOCATION;
    }
}
