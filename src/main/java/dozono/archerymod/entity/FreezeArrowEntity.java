package dozono.archerymod.entity;

import dozono.archerymod.ArcheryMod;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class FreezeArrowEntity extends AbstractArrowEntity {
    public FreezeArrowEntity(EntityType<? extends AbstractArrowEntity> p_i48546_1_, World p_i48546_2_) {
        super(p_i48546_1_, p_i48546_2_);
    }

    public FreezeArrowEntity(LivingEntity p_i48548_2_, World p_i48548_3_) {
        super(ArcheryMod.FREEZE_ARROW_ENTITY.get(), p_i48548_2_, p_i48548_3_);
    }

    public static FreezeArrowEntity createArrow(World world, LivingEntity shooter) {
        return new FreezeArrowEntity(shooter, world);
    }

    @Override
    protected void onHit(RayTraceResult result) {
        super.onHit(result);

        if (!level.isClientSide && this.getOwner() != null && !this.isInWater()) {
            spawnAOE();
        }
        if (!level.isClientSide && this.getOwner() != null && this.isInWater()) {
            for (int i = -2; i < 5; i++) {
                for (int j = -2; j < 5; j++) {
                    BlockPos pos = new BlockPos(result.getLocation().x + i, result.getLocation().y, result.getLocation().z + j);
                    level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
                }
            }
        }
    }

    @Override
    protected void tickDespawn() {
        ++this.life;
        if (this.life >= 200) {
            this.remove();
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!level.isClientSide) {
            Vector3d mov = this.getDeltaMovement();
            Vector3d pos = this.position();
            double d1 = mov.x;
            double d2 = mov.y;
            double d0 = mov.z;
            if (!inGround) {
                double x = pos.x() + d1 * 0.25D;
                double y = pos.y() + d2 * 0.25D;
                double z = pos.z() + d0 * 0.25D;
                level.addParticle(ParticleTypes.ITEM_SNOWBALL, x, y, z, -d1, -d2 + 0.2D, -d0);
            }

            if (!this.isInWater() && !this.inGround) {
                Vector3d vector3d3 = pos.add(mov);
                BlockRayTraceResult result = this.level.clip(new RayTraceContext(pos, vector3d3, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.SOURCE_ONLY, this));
                BlockPos mutable = result.getBlockPos();

                if (level.getBlockState(mutable).is(Blocks.WATER)) {
                    for (int i = -2; i < 5; i++) {
                        for (int j = -2; j < 5; j++) {
                            BlockPos offset = mutable.offset(i, 0, j);
                            if (level.getBlockState(offset).is(Blocks.WATER)) {
                                level.setBlockAndUpdate(offset, Blocks.ICE.defaultBlockState());
                            }
                        }
                    }
                }
            }
        }
    }

    private void spawnAOE() {
        // this should only call in server
        Vector3d vec = this.getDeltaMovement();
        double d1 = vec.x;
        double d2 = vec.y;
        double d0 = vec.z;
        AreaOfEffectEntity entity = AreaOfEffectEntity.create(level, getX() + d1 * 0.25, getY() + d2 * 0.25, getZ() + d0 * 0.25);
        entity.setPostHook((e) -> {
            AxisAlignedBB boundingBox = this.getBoundingBox();
            BlockPos.betweenClosedStream(boundingBox.inflate(2)).forEach((pos) -> {
                if (level.getBlockState(pos).is(Blocks.WATER)) {
                    level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
                }
            });
        });
        entity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 10));
        Entity owner = this.getOwner();
        if (owner instanceof LivingEntity) {
            entity.setOwner(((LivingEntity) owner));
        }
        entity.setParticle(ParticleTypes.ITEM_SNOWBALL);
        entity.setDuration(200);
        entity.setWaitTime(0);
        entity.setRadius(2);
        level.addFreshEntity(entity);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ArcheryMod.FREEZE_ARROW_ITEM.get());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
