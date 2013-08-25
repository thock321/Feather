package org.feather;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.feather.net.PipelineFactory;
import org.feather.tick.impl.GameTick;
import org.feather.game.Configuration;
import org.feather.game.GameWorld;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;


public class Server {
	
	private static final Logger logger = Logger.getLogger(Server.class.getName());
	
	public static void main(String[] args) throws IOException {
		logger.info("Starting up server.");
		logger.info("Loading configuration.");
		Configuration.loadConfigurationFile();
		logger.info("Adding shutdown hook.");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			
			@Override
			public void run() {
				GameWorld.getWorld().saveAllPlayers();
			}
			
		});
		logger.info("Binding to port " + Configuration.PORT);
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		bootstrap.setPipelineFactory(new PipelineFactory());
		bootstrap.bind(new InetSocketAddress(Configuration.PORT));
		logger.info("Scheduling server game tick.");
		Engine.getEngine().addTickUnit(new GameTick());
	}

}
