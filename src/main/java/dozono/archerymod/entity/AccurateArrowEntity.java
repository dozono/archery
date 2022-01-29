package dozono.archerymod.entity;

import dozono.archerymod.ArcheryMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class AccurateArrowEntity extends AbstractArrowEntity {
    public AccurateArrowEntity(EntityType<? extends AbstractArrowEntity> p_i48546_1_, World p_i48546_2_) {
        super(p_i48546_1_, p_i48546_2_);
        init();
    }

    public AccurateArrowEntity(LivingEntity shooter, World level) {
        super(ArcheryMod.ACCURATE_ARROW_ENTITY.get(), shooter, level);
        init();
    }

    private void init() {
        this.setBaseDamage(this.getBaseDamage() + 5f);
//        this.setNoPhysics(true);
        this.setNoGravity(true);
        this.setPierceLevel((byte) 3);
    }

    @Override
    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        this.remove();
    }

    public static AccurateArrowEntity createArrow(World world, LivingEntity shooter) {
        return new AccurateArrowEntity(shooter, world);
    }


    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ArcheryMod.ACCURATE_ARROW_ITEM.get());
    }


    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
