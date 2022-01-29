package dozono.archerymod.entity;

import dozono.archerymod.ArcheryMod;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class FlameArrowEntity extends AbstractArrowEntity {
    public FlameArrowEntity(EntityType<? extends FlameArrowEntity> flameArrowEntity, World world) {
        super(flameArrowEntity, world);
        this.pickup = PickupStatus.DISALLOWED;
    }

    @Override
    protected void tickDespawn() {
        ++this.life;
        if (this.life >= 200) {
            this.remove();
        }
    }

    protected FlameArrowEntity(World world, LivingEntity shooter) {
        super(ArcheryMod.FLAME_ARROW_ENTITY.get(), shooter, world);
        this.pickup = PickupStatus.DISALLOWED;
    }

    public static FlameArrowEntity createArrow(World world, LivingEntity shooter) {
        return new FlameArrowEntity(world, shooter);

    }

    private void particleEffect() {
        Vector3d vec = this.getDeltaMovement();
        double d1 = vec.x;
        double d2 = vec.y;
        double d0 = vec.z;
        AreaOfEffectEntity entity = AreaOfEffectEntity.create(level, getX() + d1 * 0.25, getY() + d2 * 0.25, getZ() + d0 * 0.25);
        entity.setOnVictim((e, v) -> v.setSecondsOnFire(10));
        entity.setParticle(ParticleTypes.FLAME);
        Entity owner = this.getOwner();
        if (owner instanceof LivingEntity) {
            entity.setOwner(((LivingEntity) owner));
        }
        entity.setDuration(200);
        entity.setWaitTime(0);
        entity.setRadius(1);
        level.addFreshEntity(entity);
    }


    @Override
    public void tick() {
        super.tick();
        Vector3d vec = this.getDeltaMovement();
        double d1 = vec.x;
        double d2 = vec.y;
        double d0 = vec.z;
        level.addParticle(ParticleTypes.LAVA, this.getX() + d1 * 0.25D, this.getY() + d2 * 0.25D, this.getZ() + d0 * 0.25D, -d1, -d2 + 0.2D, -d0);

        if (!level.isClientSide && !this.isInWater() && inGround && inGroundTime < 200 && this.getOwner() != null) {
            List<Entity> entities = level.getEntities(this, new AxisAlignedBB(this.getOnPos()).inflate(-3), (e) -> {
                if (e == this.getOwner()) {
                    return false;
                }
                if (!(e instanceof LivingEntity)) {
                    return false;
                }
                return true;
            });
            for (Entity entity : entities) {
                entity.setSecondsOnFire(10);
            }
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ArcheryMod.FLAME_ARROW_ITEM.get());
    }


    @Override
    protected void onHit(RayTraceResult result) {
        super.onHit(result);
        if (!level.isClientSide && !this.isInWater() && this.getOwner() != null) {
            particleEffect();
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
