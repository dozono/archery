package dozono.archerymod.Renderer;

import dozono.archerymod.entity.AccurateArrowEntity;
import dozono.archerymod.entity.BounceArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.TippedArrowRenderer;
import net.minecraft.util.ResourceLocation;

public class AccurateArrowRenderer extends ArrowRenderer<AccurateArrowEntity> {

    public AccurateArrowRenderer(EntityRendererManager p_i46193_1_) {
        super(p_i46193_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(AccurateArrowEntity p_110775_1_) {
        return TippedArrowRenderer.NORMAL_ARROW_LOCATION;
    }
}
