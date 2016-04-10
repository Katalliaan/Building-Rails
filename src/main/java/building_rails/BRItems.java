package building_rails;

import net.minecraft.item.Item;
import building_rails.item.ItemBlastingCap;
import building_rails.item.ItemDynamite;
import building_rails.item.ItemBlastingCap.CapMaterial;

public class BRItems {
	public static Item itemDynamite = new ItemDynamite();
	public static Item blastingCap_iron = new ItemBlastingCap(CapMaterial.IRON).setTextureName(BuildingRails.modid + ":blastingCap_iron");
}
