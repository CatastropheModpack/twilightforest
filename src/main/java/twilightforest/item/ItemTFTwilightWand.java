package twilightforest.item;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import twilightforest.entity.EntityTFTwilightWandBolt;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTFTwilightWand extends ItemTF {

	protected ItemTFTwilightWand() {
        this.maxStackSize = 1;
        this.setMaxDamage(99);
		this.setCreativeTab(TFItems.creativeTab);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World worldObj, EntityPlayer player, EnumHand hand) {
		if (par1ItemStack.getItemDamage() < this.getMaxDamage()) 
		{
			player.setActiveHand(hand);
		}
		else 
		{
			player.resetActiveHand();
		}
		
		return ActionResult.newResult(EnumActionResult.SUCCESS, par1ItemStack);
	}
	
    @Override
	public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {
		if (stack.getItemDamage() >= this.getMaxDamage()) {
			// do not use
			living.resetActiveHand();
			return;
		}
    	
    	if (count % 6 == 0) {
    		World worldObj = living.worldObj;
	    	
			worldObj.playSoundAtEntity(living, "mob.ghast.fireball", 1.0F, (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F + 1.0F);
			
			if (!worldObj.isRemote) {
				worldObj.spawnEntity(new EntityTFTwilightWandBolt(worldObj, living));
				
				stack.damageItem(1, living);
			}
    	}


	}

    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }
    
    @Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.BOW;
    }
    
    @Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
    	return EnumRarity.RARE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List<String> par3List, boolean par4) {
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		par3List.add((par1ItemStack.getMaxDamage() -  par1ItemStack.getItemDamage()) + " charges left");
	}
}
