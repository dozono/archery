package dozono.archerymod.entity;

import com.google.common.collect.Lists;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AreaOfEffectEntity extends AreaEffectCloudEntity {
    private Consumer<AreaOfEffectEntity> tickHook;
    private BiConsumer<AreaOfEffectEntity, LivingEntity> onVictim;

    public AreaOfEffectEntity(EntityType<? extends AreaEffectCloudEntity> p_i50389_1_, World p_i50389_2_) {
        super(p_i50389_1_, p_i50389_2_);
    }

    protected AreaOfEffectEntity(World p_i46810_1_, double p_i46810_2_, double p_i46810_4_, double p_i46810_6_) {
        super(p_i46810_1_, p_i46810_2_, p_i46810_4_, p_i46810_6_);
    }

    public static AreaOfEffectEntity create(World world, double x, double y, double z) {
        return new AreaOfEffectEntity(world, x, y, z);
    }

    public void setOnVictim(BiConsumer<AreaOfEffectEntity, LivingEntity> onVictim) {
        this.onVictim = onVictim;
    }

    public void setPostHook(Consumer<AreaOfEffectEntity> tickHook) {
        this.tickHook = tickHook;
    }

    @Override
    public void tick() {
        boolean waiting = this.isWaiting();
        float radius = this.getRadius();
        if (this.level.isClientSide) {
            IParticleData lvt_3_1_ = this.getParticle();
            float lvt_6_1_;
            float lvt_7_1_;
            float lvt_8_1_;
            int lvt_10_1_;
            int lvt_11_1_;
            int lvt_12_1_;
            if (waiting) {
                if (this.random.nextBoolean()) {
                    for (int lvt_4_1_ = 0; lvt_4_1_ < 2; ++lvt_4_1_) {
                        float lvt_5_1_ = this.random.nextFloat() * 6.2831855F;
                        lvt_6_1_ = MathHelper.sqrt(this.random.nextFloat()) * 0.2F;
                        lvt_7_1_ = MathHelper.cos(lvt_5_1_) * lvt_6_1_;
                        lvt_8_1_ = MathHelper.sin(lvt_5_1_) * lvt_6_1_;
                        if (lvt_3_1_.getType() == ParticleTypes.ENTITY_EFFECT) {
                            int lvt_9_1_ = this.random.nextBoolean() ? 16777215 : this.getColor();
                            lvt_10_1_ = lvt_9_1_ >> 16 & 255;
                            lvt_11_1_ = lvt_9_1_ >> 8 & 255;
                            lvt_12_1_ = lvt_9_1_ & 255;
                            this.level.addAlwaysVisibleParticle(lvt_3_1_, this.getX() + (double) lvt_7_1_, this.getY(), this.getZ() + (double) lvt_8_1_, (double) ((float) lvt_10_1_ / 255.0F), (double) ((float) lvt_11_1_ / 255.0F), (double) ((float) lvt_12_1_ / 255.0F));
                        } else {
                            this.level.addAlwaysVisibleParticle(lvt_3_1_, this.getX() + (double) lvt_7_1_, this.getY(), this.getZ() + (double) lvt_8_1_, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            } else {
                float lvt_4_2_ = 3.1415927F * radius * radius;

                for (int lvt_5_2_ = 0; (float) lvt_5_2_ < lvt_4_2_; ++lvt_5_2_) {
                    lvt_6_1_ = this.random.nextFloat() * 6.2831855F;
                    lvt_7_1_ = MathHelper.sqrt(this.random.nextFloat()) * radius;
                    lvt_8_1_ = MathHelper.cos(lvt_6_1_) * lvt_7_1_;
                    float lvt_9_2_ = MathHelper.sin(lvt_6_1_) * lvt_7_1_;
                    if (lvt_3_1_.getType() == ParticleTypes.ENTITY_EFFECT) {
                        lvt_10_1_ = this.getColor();
                        lvt_11_1_ = lvt_10_1_ >> 16 & 255;
                        lvt_12_1_ = lvt_10_1_ >> 8 & 255;
                        int lvt_13_1_ = lvt_10_1_ & 255;
                        this.level.addAlwaysVisibleParticle(lvt_3_1_, this.getX() + (double) lvt_8_1_, this.getY(), this.getZ() + (double) lvt_9_2_, (double) ((float) lvt_11_1_ / 255.0F), (double) ((float) lvt_12_1_ / 255.0F), (double) ((float) lvt_13_1_ / 255.0F));
                    } else {
                        this.level.addAlwaysVisibleParticle(lvt_3_1_, this.getX() + (double) lvt_8_1_, this.getY(), this.getZ() + (double) lvt_9_2_, (0.5D - this.random.nextDouble()) * 0.15D, 0.009999999776482582D, (0.5D - this.random.nextDouble()) * 0.15D);
                    }
                }
            }
        } else {
            if (this.tickCount >= this.waitTime + this.duration) {
                this.remove();
                return;
            }

            boolean b = this.tickCount < this.waitTime;
            if (waiting != b) {
                this.setWaiting(b);
            }

            if (b) {
                return;
            }

            if (this.radiusPerTick != 0.0F) {
                radius += this.radiusPerTick;
                if (radius < 0.5F) {
                    this.remove();
                    return;
                }

                this.setRadius(radius);
            }

            // apply effect
            if (this.tickCount % 5 == 0) {
                this.victims.entrySet().removeIf(entry -> this.tickCount >= entry.getValue());

                List<EffectInstance> effects = Lists.newArrayList();

                for (EffectInstance ef : this.potion.getEffects()) {
                    effects.add(new EffectInstance(ef.getEffect(), ef.getDuration() / 4, ef.getAmplifier(), ef.isAmbient(), ef.isVisible()));
                }

                effects.addAll(this.effects);
                if (effects.isEmpty()) {
                    this.victims.clear();
                } else {
                    LivingEntity owner = this.getOwner();
                    List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox(), (e) -> e != owner);
                    if (!entities.isEmpty()) {
                        Iterator<LivingEntity> itr = entities.iterator();

                        while (true) {
                            LivingEntity victim;
                            double distance;
                            do {
                                do {
                                    do {
                                        if (!itr.hasNext()) {
                                            return;
                                        }

                                        victim = itr.next();
                                    } while (this.victims.containsKey(victim));
                                } while (!victim.isAffectedByPotions());

                                double xOffset = victim.getX() - this.getX();
                                double zOffset = victim.getZ() - this.getZ();
                                distance = xOffset * xOffset + zOffset * zOffset;
                            } while (!(distance <= (double) (radius * radius)));

                            this.victims.put(victim, this.tickCount + this.reapplicationDelay);
                            if (onVictim != null) {
                                onVictim.accept(this, victim);
                            }

                            for (EffectInstance instance : effects) {
                                if (instance.getEffect().isInstantenous()) {
                                    instance.getEffect().applyInstantenousEffect(this, owner, victim, instance.getAmplifier(), 0.5D);
                                } else {
                                    victim.addEffect(new EffectInstance(instance));
                                }
                            }

                            if (this.radiusOnUse != 0.0F) {
                                radius += this.radiusOnUse;
                                if (radius < 0.5F) {
                                    this.remove();
                                    return;
                                }

                                this.setRadius(radius);
                            }

                            if (this.durationOnUse != 0) {
                                this.duration += this.durationOnUse;
                                if (this.duration <= 0) {
                                    this.remove();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
            if (this.tickHook != null) {
                this.tickHook.accept(this);
            }
        }
    }
}
