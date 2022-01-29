package dozono.archerymod;

import dozono.archerymod.entity.*;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BiFunction;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArcheryMod.MODID)
public class ArcheryMod {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "archery";

    private static final DeferredRegister<Item> itemRegister = DeferredRegister.create(Item.class, MODID);
    private static final DeferredRegister<EntityType<?>> entityRegister = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);

    public static Item createArrowItem(BiFunction<World, LivingEntity, AbstractArrowEntity> factory) {
        return new ArrowItem(new Item.Properties().tab(ItemGroup.TAB_COMBAT)) {
            @Override
            public AbstractArrowEntity createArrow(World p_200887_1_, ItemStack p_200887_2_, LivingEntity p_200887_3_) {
                return factory.apply(p_200887_1_, p_200887_3_);
            }
        };
    }

    public static final RegistryObject<Item> FLAME_ARROW_ITEM = itemRegister.register("flame_arrow_item",
            () -> createArrowItem(FlameArrowEntity::createArrow));
    public static final RegistryObject<EntityType<FlameArrowEntity>> FLAME_ARROW_ENTITY = entityRegister.register("flame_arrow_entity",
            () -> EntityType.Builder.<FlameArrowEntity>of(FlameArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).updateInterval(20).clientTrackingRange(4)
                    .build("flame_arrow_entity"));


    public static final RegistryObject<Item> LIGHTNING_ARROW_ITEM = itemRegister.register("lightning_arrow_item",
            () -> createArrowItem(LightningArrowEntity::createArrow));

    public static final RegistryObject<EntityType<LightningArrowEntity>> LIGHTNING_ARROW_ENTITY = entityRegister.register("lightning_arrow_entity",
            () -> EntityType.Builder.<LightningArrowEntity>of(LightningArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).updateInterval(20).clientTrackingRange(4)
                    .build("lightning_arrow_entity"));

    public static final RegistryObject<Item> EXPLOSIVE_ARROW_ITEM = itemRegister.register("explosive_arrow_item",
            () -> createArrowItem(ExplosiveArrowEntity::createArrow));
    public static final RegistryObject<EntityType<ExplosiveArrowEntity>> EXPLOSIVE_ARROW_ENTITY = entityRegister.register("explosive_arrow_entity",
            () -> EntityType.Builder.<ExplosiveArrowEntity>of(ExplosiveArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).updateInterval(20).clientTrackingRange(4)
                    .build("explosive_arrow_entity"));


    public static final RegistryObject<Item> BOUNCE_ARROW_ITEM = itemRegister.register("bounce_arrow_item",
            () -> createArrowItem(BounceArrowEntity::createArrow));
    public static final RegistryObject<EntityType<BounceArrowEntity>> BOUNCE_ARROW_ENTITY = entityRegister.register("bounce_arrow_entity",
            () -> EntityType.Builder.<BounceArrowEntity>of(BounceArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).updateInterval(20).clientTrackingRange(4)
                    .build("bounce_arrow_entity"));

    public static final RegistryObject<Item> ACCURATE_ARROW_ITEM = itemRegister.register("accurate_arrow_item",
            () -> createArrowItem(AccurateArrowEntity::createArrow));
    public static final RegistryObject<EntityType<AccurateArrowEntity>> ACCURATE_ARROW_ENTITY = entityRegister.register("accurate_arrow_entity",
            () -> EntityType.Builder.<AccurateArrowEntity>of(AccurateArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).updateInterval(20).clientTrackingRange(4)
                    .build("accurate_arrow_entity"));

    public static final RegistryObject<Item> TELEPORT_ARROW_ITEM = itemRegister.register("teleport_arrow_item",
            () -> createArrowItem(TeleportArrowEntity::createArrow));
    public static final RegistryObject<EntityType<TeleportArrowEntity>> TELEPORT_ARROW_ENTITY = entityRegister.register("teleport_arrow_entity",
            () -> EntityType.Builder.<TeleportArrowEntity>of(TeleportArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).updateInterval(20).clientTrackingRange(4)
                    .build("teleport_arrow_entity"));

    public static final RegistryObject<Item> FREEZE_ARROW_ITEM = itemRegister.register("freeze_arrow_item",
            () -> createArrowItem(FreezeArrowEntity::createArrow));
    public static final RegistryObject<EntityType<FreezeArrowEntity>> FREEZE_ARROW_ENTITY = entityRegister.register("freeze_arrow_entity",
            () -> EntityType.Builder.<FreezeArrowEntity>of(FreezeArrowEntity::new, EntityClassification.MISC)
                    .sized(0.5F, 0.5F).updateInterval(20).clientTrackingRange(4)
                    .build("freeze_arrow_entity"));

    public static final RegistryObject<EntityType<AreaOfEffectEntity>> AOE_ENTITY = entityRegister.register("area_of_effect_entity", () ->
            EntityType.Builder.of(AreaOfEffectEntity::new, EntityClassification.MISC)
                    .fireImmune().sized(6.0F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE)
                    .build("area_of_effect_entity"));

    public ArcheryMod() {

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        itemRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
        entityRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
//        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
//        InterModComms.sendTo("examplemod", "helloworld", () -> {
//            LOGGER.info("Hello world from the MDK");
//            return "Hello world";
//        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

}
