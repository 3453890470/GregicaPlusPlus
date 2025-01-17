package gregica.loader.recipes.xkball.laser;

import gregica.api.GCValues;
import gregica.common.tileentities.mte.multipart.MTELaserHatch;
import gregica.api.utils.GCRecipeUtils;
import gregtech.api.GTValues;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.common.blocks.BlockMachineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregica.modules.gcys.common.metablock.GCYSMetaBlocks;
import gregica.modules.gcys.common.metablock.BlockTransparentCasing;
import gregica.common.item.CommonItems;
import gregica.common.item.metaitems.GCMetaItems;
import gregica.common.tileentities.mte.GCMetaEntities;

public class LaserRecipes {
    
    public static void init(){
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(GTValues.VA[8])
                .duration(200)
                .input(GCMetaItems.OPTICAL_FIBER,16)
                .input(OrePrefix.ring, Materials.Iridium,2)
                .input(OrePrefix.pipeNormalItem,Materials.Osmiridium)
                .fluidInputs(Materials.Polytetrafluoroethylene.getFluid(288))
                .output(CommonItems.OPAQUE_ITEM_LASER_VACUUM_BLOCK,2)
                .buildAndRegister();
        
        RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                .EUt(GTValues.VA[8])
                .duration(100)
                .input(CommonItems.OPAQUE_ITEM_LASER_VACUUM_BLOCK)
                .inputs(GCYSMetaBlocks.TRANSPARENT_CASING.getItemVariant(BlockTransparentCasing.CasingType.PMMA))
                .output(CommonItems.TRANSPARENT_ITEM_LASER_VACUUM_BLOCK)
                .buildAndRegister();
        
        for(MTELaserHatch hatch : GCMetaEntities.LASER_HATCH_INPUT){
            int tier = hatch.getTier();
            if(GCRecipeUtils.getComponent(GCRecipeUtils.Component.SENSOR,tier) != null){
                int amps = hatch.getAmperage();
                int a_tier = GCValues.AMPERAGE_TIER.applyAsInt(amps);
                a_tier = a_tier == 0? 1: a_tier;
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                        .EUt(GTValues.VA[tier])
                        .duration(3600*a_tier)
                        .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.values()[tier]))
                        .input(OrePrefix.circuit, GCRecipeUtils.intToMaterialsTier.apply(tier),a_tier)
                        .input(GCRecipeUtils.getComponent(GCRecipeUtils.Component.SENSOR,tier),1<<a_tier)
                        .input(GCRecipeUtils.getComponent(GCRecipeUtils.Component.PUMP,tier))
                        .input(OrePrefix.lens,tier<=6 ? Materials.Diamond : Materials.NetherStar)
                        .circuitMeta(a_tier)
                        .output(hatch)
                        .buildAndRegister();
            }
            
        }
        
        for(MTELaserHatch hatch : GCMetaEntities.LASER_HATCH_OUTPUT) {
            int tier = hatch.getTier();
            if (GCRecipeUtils.getComponent(GCRecipeUtils.Component.SENSOR, tier) != null) {
                int amps = hatch.getAmperage();
                int a_tier = GCValues.AMPERAGE_TIER.applyAsInt(amps);
                a_tier = a_tier == 0 ? 1 : a_tier;
                RecipeMaps.ASSEMBLER_RECIPES.recipeBuilder()
                        .EUt(GTValues.VA[tier])
                        .duration(3600* a_tier)
                        .inputs(MetaBlocks.MACHINE_CASING.getItemVariant(BlockMachineCasing.MachineCasingType.values()[tier]))
                        .input(OrePrefix.circuit, GCRecipeUtils.intToMaterialsTier.apply(tier), a_tier)
                        .input(GCRecipeUtils.getComponent(GCRecipeUtils.Component.EMITTER, tier), 1<<a_tier)
                        .input(GCRecipeUtils.getComponent(GCRecipeUtils.Component.PUMP, tier))
                        .input(OrePrefix.lens,tier<=6 ? Materials.Diamond : Materials.NetherStar)
                        .circuitMeta(a_tier)
                        .output(hatch)
                        .buildAndRegister();
    
            }
        }
    }
    
    
}
