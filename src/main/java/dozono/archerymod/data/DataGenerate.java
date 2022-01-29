package dozono.archerymod.data;

import dozono.archerymod.ArcheryMod;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ArcheryMod.MODID)
public class DataGenerate {
    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        System.out.println("GATHER DATA");
        if (event.includeServer()) {
            registerServerProviders(event);
        }
        if (event.includeClient()) {
            registerClientProviders(event);
        }
    }

    private static void registerServerProviders(GatherDataEvent event) {
        System.out.println("GATHER DATA SERVER");
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper exFileHelper = event.getExistingFileHelper();

        BlockTagsProvider blockTagsProvider = new BlockTagsProvider(gen, ArcheryMod.MODID, exFileHelper);
        ItemTagsProvider provider = new ItemTagsProvider(gen, blockTagsProvider, ArcheryMod.MODID, exFileHelper) {
            @Override
            public String getName() {
                return "Archery Mod: Item Tags";
            }

            @Override
            protected void addTags() {
                tag(ItemTags.ARROWS).add(
                        ArcheryMod.FLAME_ARROW_ITEM.get(),
                        ArcheryMod.LIGHTNING_ARROW_ITEM.get(),
                        ArcheryMod.EXPLOSIVE_ARROW_ITEM.get(),
                        ArcheryMod.BOUNCE_ARROW_ITEM.get(),
                        ArcheryMod.ACCURATE_ARROW_ITEM.get(),
                        ArcheryMod.TELEPORT_ARROW_ITEM.get(),
                        ArcheryMod.FREEZE_ARROW_ITEM.get()
                );
            }
        };

        gen.addProvider(blockTagsProvider);
        gen.addProvider(provider);
    }

    private static void registerClientProviders(GatherDataEvent event) {
//        DataGenerator gen = event.getGenerator();
//        ExistingFileHelper exFileHelper = event.getExistingFileHelper();
//
//        gen.addProvider(new APItemModelProvider(gen, exFileHelper));
    }
}
