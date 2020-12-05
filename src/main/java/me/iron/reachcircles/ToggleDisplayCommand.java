//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9 stable mappings"!

package me.iron.reachcircles;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class ToggleDisplayCommand extends CommandBase {

  Minecraft mc = ReachCirclesMod.mc;

  public String getCommandName() {
    return "circles";
  }
  
  public String getCommandUsage(ICommandSender sender) {
    return "/circles <modifier>";
  }

  public void processCommand(ICommandSender sender, String[] args) {

    if (args.length < 1) {

      sender.addChatMessage(new ChatComponentText("Current circles modifiers:" + " All - "+ ReachCirclesMod.showAllRendered + " Self - " + ReachCirclesMod.showOnSelf + " Others - " + ReachCirclesMod.showOthers + " Invis. - " + ReachCirclesMod.showInvisible));
      sender.addChatMessage(new ChatComponentText("Player Passes:" + " All - "+ ReachCirclesMod.getAllRendered(mc.thePlayer) + " Self - " + ReachCirclesMod.getOnSelfState(mc.thePlayer) + " Others - " + ReachCirclesMod.getOthersState(mc.thePlayer)+ " Invis. - " + ReachCirclesMod.getInvisibleState(mc.thePlayer)));
      mc.thePlayer.addChatMessage(new ChatComponentText("Current circles state: " + ReachCirclesMod.show));
      sender.addChatMessage(new ChatComponentText(""));
      sender.addChatMessage(new ChatComponentText("Change state by doing " + getCommandUsage(sender)));
      return;

    }

    if (args[0].toLowerCase().equals("all") || args[0].toLowerCase().equals("esp")) {

      ReachCirclesMod.showAllRendered = !ReachCirclesMod.showAllRendered;
      mc.thePlayer.addChatMessage(new ChatComponentText("Circles on all rendered players: " + Boolean.toString(ReachCirclesMod.showAllRendered) + "."));
      ReachCirclesMod.instance.saveConfig();

      return;

    }

    if (args[0].toLowerCase().equals("me") || args[0].toLowerCase().equals("self")) {

      ReachCirclesMod.showOnSelf = !ReachCirclesMod.showOnSelf;
      sender.addChatMessage(new ChatComponentText("Circles on self: " + Boolean.toString(ReachCirclesMod.showOnSelf) + "."));
      ReachCirclesMod.instance.saveConfig();

      return;

    }

    if (args[0].toLowerCase().equals("invis") || args[0].toLowerCase().equals("invisible")) {

      ReachCirclesMod.showInvisible = !ReachCirclesMod.showInvisible;
      sender.addChatMessage(new ChatComponentText("Circles on invisibles: " + Boolean.toString(ReachCirclesMod.showInvisible) + "."));
      ReachCirclesMod.instance.saveConfig();

      return;

    }

    if (args[0].toLowerCase().equals("other") || args[0].toLowerCase().equals("others")) {

      ReachCirclesMod.showOthers = !ReachCirclesMod.showOthers;
      sender.addChatMessage(new ChatComponentText("Circles on other players: " + Boolean.toString(ReachCirclesMod.showOthers) + "."));
      ReachCirclesMod.instance.saveConfig();

      return;

    }

    if (args[0].toLowerCase().equals("toggle")) {

      mc.thePlayer.addChatMessage(new ChatComponentText(Boolean.toString(ReachCirclesMod.toggle())));
      ReachCirclesMod.instance.saveConfig();

      return;

    }

    sender.addChatMessage(new ChatComponentText("Acceptable Modifiers:"));
    sender.addChatMessage(new ChatComponentText("-    all | esp"));
    sender.addChatMessage(new ChatComponentText("-    me | self"));
    sender.addChatMessage(new ChatComponentText("-    other | others"));
    sender.addChatMessage(new ChatComponentText("-    invis | invisible"));

  }
  
  public int getRequiredPermissionLevel() {
    return 0;
  }
  
  public boolean canCommandSenderUseCommand(ICommandSender sender) {
    return true;
  }
}
