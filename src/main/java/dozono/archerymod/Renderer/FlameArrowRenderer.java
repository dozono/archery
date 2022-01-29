package dozono.archerymod.Renderer;

import dozono.archerymod.entity.FlameArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TippedArrowRenderer;
import net.minecraft.util.ResourceLocation;

public class FlameArrowRenderer extends ArrowRenderer<FlameArrowEntity> {

    public FlameArrowRenderer(EntityRendererManager p_i46193_1_) {
        super(p_i46193_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(FlameArrowEntity p_110775_1_) {
        return TippedArrowRenderer.NORMAL_ARROW_LOCATION;
    }
}
