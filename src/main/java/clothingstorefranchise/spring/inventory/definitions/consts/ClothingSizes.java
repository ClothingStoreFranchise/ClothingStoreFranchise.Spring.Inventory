package clothingstorefranchise.spring.inventory.definitions.consts;

import clothingstorefranchise.spring.inventory.definitions.enums.ClothingSizeType;

public final class ClothingSizes {
	//{"XS","S","M","L","XL","2XL","3XL"};
	public static final int[] TSHIRST_PANTS_SIZES = {0,1,2,3,4,5,6};
	public static final int[] FOOTWEAR_SIZES = {36,37,38,39,40,41,42,43,44,45,46,47,48};
	
	private ClothingSizes() { }
	
	public static int[] getClothingShizes(int clothingSizeId) {
		return clothingSizeId == ClothingSizeType.TSHIRTS_PANTS ? TSHIRST_PANTS_SIZES : FOOTWEAR_SIZES;
	}
}
