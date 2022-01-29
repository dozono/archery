package dozono.archerymod.entity;

import dozono.archerymod.ArcheryMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class TeleportArrowEntity extends AbstractArrowEntity {
    Vector3d vec3d = this.getDeltaMovement();
    double d1 = vec3d.x;
    double d2 = vec3d.y;
    double d0 = vec3d.z;

    public TeleportArrowEntity(EntityType<? extends AbstractArrowEntity> p_i48546_1_, World p_i48546_2_) {
        super(p_i48546_1_, p_i48546_2_);
    }

    public TeleportArrowEntity(LivingEntity p_i48548_2_, World p_i48548_3_) {
        super(ArcheryMod.TELEPORT_ARROW_ENTITY.get(), p_i48548_2_, p_i48548_3_);
    }

    public static TeleportArrowEntity createArrow(World world, LivingEntity shooter) {
        return new TeleportArrowEntity(shooter, world);
    }

    @Override
    protected void onHit(RayTraceResult result) {
        super.onHit(result);
        BlockPos pos = new BlockPos(result.getLocation());
        Entity shooter = this.getOwner();
        if (!level.isClientSide && shooter != null) {
            shooter.teleportTo(pos.getX(), pos.getY(), pos.getZ());
            level.addParticle(ParticleTypes.ASH, this.getX() + d1 * 0.25D, this.getY() + d2 * 0.25D, this.getZ() + d0 * 0.25D, -d1, -d2 + 0.2D, -d0);
        }

    }

    @Override
    public void tick() {
        super.tick();
        if (!this.onGround) {

            this.level.addParticle(ParticleTypes.PORTAL, this.getX() + d1 * 0.25D, this.getY() + d2 * 0.25D, this.getZ() + d0 * 0.25D, -d1, -d2 + 0.2D, -d0);
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ArcheryMod.TELEPORT_ARROW_ITEM.get());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
