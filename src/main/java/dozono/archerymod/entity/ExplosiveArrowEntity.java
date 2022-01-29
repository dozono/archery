package dozono.archerymod.entity;

import dozono.archerymod.ArcheryMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkHooks;


public class ExplosiveArrowEntity extends AbstractArrowEntity {
    static DamageSource damageSource = new DamageSource("explosive_arrow");

    public ExplosiveArrowEntity(EntityType<? extends AbstractArrowEntity> p_i48546_1_, World p_i48546_2_) {
        super(p_i48546_1_, p_i48546_2_);
    }

    public ExplosiveArrowEntity(LivingEntity shooter, World world) {
        super(ArcheryMod.EXPLOSIVE_ARROW_ENTITY.get(), shooter, world);
    }

    public static ExplosiveArrowEntity createArrow(World world, LivingEntity shooter) {
        return new ExplosiveArrowEntity(shooter, world);
    }

    @Override
    protected void onHit(RayTraceResult result) {
        super.onHit(result);
        if (!level.isClientSide && !this.isInWater()&&this.getOwner()!=null) {
            BlockPos blockPos = new BlockPos(result.getLocation());
            level.explode(this.getOwner(), damageSource, new ExplosionContext(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), 10f, false, Explosion.Mode.NONE);
//            level.explode(this,blockPos.getX(),blockPos.getY(),blockPos.getZ(),10f,false, Explosion.Mode.DESTROY);
            this.remove();
        }
    }



    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ArcheryMod.EXPLOSIVE_ARROW_ITEM.get());
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
