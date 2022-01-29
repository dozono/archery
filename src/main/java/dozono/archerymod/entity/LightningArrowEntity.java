package dozono.archerymod.entity;

import dozono.archerymod.ArcheryMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class LightningArrowEntity extends AbstractArrowEntity {
    public LightningArrowEntity(EntityType<? extends AbstractArrowEntity> p_i48546_1_, World p_i48546_2_) {
        super(p_i48546_1_, p_i48546_2_);
    }

    protected LightningArrowEntity(LivingEntity p_i48548_2_, World p_i48548_3_) {
        super(ArcheryMod.LIGHTNING_ARROW_ENTITY.get(), p_i48548_2_, p_i48548_3_);
    }

    public static LightningArrowEntity createArrow(World world, LivingEntity shooter) {
        return new LightningArrowEntity(shooter, world);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ArcheryMod.LIGHTNING_ARROW_ITEM.get());
    }

    @Override
    protected void onHit(RayTraceResult result) {
        super.onHit(result);
        if(!level.isClientSide&&this.getOwner()!=null){
            LightningBoltEntity entity = EntityType.LIGHTNING_BOLT.create(this.level);
            entity.moveTo(result.getLocation());
            entity.setVisualOnly(false);
            this.level.addFreshEntity(entity);
            this.remove();
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
