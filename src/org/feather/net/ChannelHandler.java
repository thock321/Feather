package org.feather.net;


import org.feather.net.packet.GamePacket;
import org.feather.threads.LoginLogoutThread;
import org.feather.game.GameWorld;
import org.feather.game.model.player.Player;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class ChannelHandler extends SimpleChannelHandler {
	
	private Player player;
	
	@Override
	public void messageReceived(ChannelHandlerContext context, MessageEvent event) {
		if (!event.getChannel().isConnected())
			return;
		if (event.getMessage() instanceof Player) {
			player = (Player) event.getMessage();
			LoginLogoutThread.getInstance().login((Player) event.getMessage());
		} else if (event.getMessage() instanceof GamePacket) {
			if (player == null) {
				for (Player tmp : GameWorld.getWorld().getPlayers()) {
					if (tmp.getChannel().equals(event.getChannel()))
						player = tmp;
				}
			}
			if (player != null) {
				player.sendPacket((GamePacket) event.getMessage());
			}
		}
	}
	
	@Override
	public void channelClosed(ChannelHandlerContext context, ChannelStateEvent event) {
		if (player == null)
			return;
		LoginLogoutThread.getInstance().logout(player);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		
	}

}
