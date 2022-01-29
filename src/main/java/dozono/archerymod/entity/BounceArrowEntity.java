package dozono.archerymod.entity;

import dozono.archerymod.ArcheryMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BounceArrowEntity extends AbstractArrowEntity {
    public BounceArrowEntity(EntityType<? extends AbstractArrowEntity> p_i48546_1_, World p_i48546_2_) {
        super(p_i48546_1_, p_i48546_2_);
    }

    public BounceArrowEntity(LivingEntity shooter, World level) {
        super(ArcheryMod.BOUNCE_ARROW_ENTITY.get(), shooter, level);
    }

    public static BounceArrowEntity createArrow(World world, LivingEntity shooter) {
        return new BounceArrowEntity(shooter, world);
    }

    @Override
    protected void onHit(RayTraceResult result) {
        super.onHit(result);
    }

    @Override
    protected void onHitEntity(EntityRayTraceResult target) {
        super.onHitEntity(target);
        if (target.getEntity() instanceof LivingEntity && !this.level.isClientSide&&this.getOwner()!=null) {
            Vector3d d = this.getDeltaMovement();
            ((LivingEntity) target.getEntity()).knockback(7f,-d.x,-d.z);
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ArcheryMod.BOUNCE_ARROW_ITEM.get());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
