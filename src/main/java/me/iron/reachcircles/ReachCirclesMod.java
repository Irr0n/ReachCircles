//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9 stable mappings"!

package me.iron.reachcircles;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.io.File;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, acceptedMinecraftVersions = "[1.8.9]")
public class ReachCirclesMod {

  public static Minecraft mc = Minecraft.getMinecraft();
  
  public static boolean show = false;
  public static boolean showOnSelf = false;
  public static boolean showOthers = false;
  public static boolean showInvisible = false;
  public static boolean showAllRendered = false;

  public static boolean toggle() {
    show = !show;
    return show;
  }

  @Instance(Reference.MOD_ID)
  public static ReachCirclesMod instance;

  private File configFile;

  public static boolean getOnSelfState(Entity entity) {
    boolean onSelfState = true;
    if (entity == mc.thePlayer) {
      if (showOnSelf) {
        onSelfState = true;
      }
      else {
        onSelfState = false;
      }
    }
    return onSelfState;
  }

  public static boolean getOthersState(Entity entity) {
    boolean othersState = true;
    if (!(entity == mc.thePlayer)) {
      if (showOthers) {
        othersState = true;
      }
      else {
        othersState = false;
      }
    }
    return othersState;
  }

  public static boolean getInvisibleState(Entity entity) {
    boolean invisibleState = true;
    if (entity.isInvisible()) {
      if (showInvisible) {
        invisibleState = true;
      }
      else {
        invisibleState = false;
      }
    }
    return invisibleState;
  }

  public static boolean getAllRendered(Entity entity) {
    boolean allRenderedState = true;
    if (!((EntityLivingBase)entity).canEntityBeSeen(mc.thePlayer)) {
      if (showAllRendered) {
        allRenderedState = true;
      }
      else {
        allRenderedState = false;
      }
    }
    return allRenderedState;
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    this.configFile = new File("./config/ReachCircles.cfg");
    FMLCommonHandler.instance().bus().register(this);
    MinecraftForge.EVENT_BUS.register(this);
    ClientCommandHandler.instance.registerCommand((ICommand)new ToggleDisplayCommand());
    loadConfig();
  }

  @SubscribeEvent
  public void onRender(RenderWorldLastEvent event) {
    GL11.glPushMatrix();
    //mc.entityRenderer.disableLightmap();
    GL11.glDisable(3553);
    GL11.glEnable(3042);
    GL11.glBlendFunc(770, 771);
    GL11.glDisable(2929);
    GL11.glEnable(2848);
    GL11.glDepthMask(false);
    for (Object o : mc.theWorld.loadedEntityList) {
      Entity entity = (Entity)o;
      if (
              entity instanceof EntityLivingBase &&
              getInvisibleState(entity) &&
              getOnSelfState(entity) &&
              getOthersState(entity) &&
              getAllRendered(entity) &&
              entity instanceof net.minecraft.entity.player.EntityPlayer &&
              show
      ) {

        double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.partialTicks - (mc.getRenderManager()).viewerPosX;
        double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.partialTicks - (mc.getRenderManager()).viewerPosY;
        double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.partialTicks - (mc.getRenderManager()).viewerPosZ;
        circle(posX, posY, posZ, mc.playerController.isInCreativeMode() ? 4.7D : 3.4D);

      } 
    } 
    GL11.glDepthMask(true);
    GL11.glDisable(2848);
    GL11.glEnable(2929);
    GL11.glDisable(3042);
    GL11.glEnable(3553);
    //mc.entityRenderer.enableLightmap();
    GL11.glPopMatrix();
  }
  
  private void circle(double x, double y, double z, double rad) {
    GL11.glPushMatrix();
    double tau = 6.283185307179586D;
    double fans = 45.0D;
    GL11.glLineWidth(1.0F);
    GL11.glColor3f(255.0F, 255.0F, 255.0F);
    GL11.glBegin(1);
    for (int i = 0; i <= 90; i++) {
      GL11.glColor4f(255.0F, 255.0F, 255.0F, 60.0F);
      GL11.glVertex3d(x + rad * Math.cos(i * 6.283185307179586D / 45.0D), y, z + rad * Math.sin(i * 6.283185307179586D / 45.0D));
    } 
    GL11.glEnd();
    GL11.glPopMatrix();
  }


  /*
    # Configuration file

    general {
        I:amount=0
        B:enabled=false
}
   */

  public void saveConfig() {
    Configuration configuration = new Configuration(this.configFile);
    updateConfig(configuration, false);
    configuration.save();
  }

  private void loadConfig() {
    Configuration configuration = new Configuration(this.configFile);
    configuration.load();
    updateConfig(configuration, true);
    configuration.save();
  }

  private void updateConfig(Configuration config, boolean load) {

    Property property = config.get("general", "show", false);
    if (load) {
      show = property.getBoolean();
    } else {
      property.set(show);
    }

    property = config.get("general", "showOnSelf", false);
    if (load) {
      showOnSelf = property.getBoolean();
    } else {
      property.set(showOnSelf);
    }

    property = config.get("general", "showOthers", false);
    if (load) {
      showOthers = property.getBoolean();
    } else {
      property.set(showOthers);
    }

    property = config.get("general", "showInvisible", false);
    if (load) {
      showInvisible = property.getBoolean();
    } else {
      property.set(showInvisible);
    }

    property = config.get("general", "showAllRendered", false);
    if (load) {
      showAllRendered = property.getBoolean();
    } else {
      property.set(showAllRendered);
    }
  }
}
