package org.feather.net;

import org.feather.net.codec.*;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.DefaultChannelPipeline;


public class PipelineFactory implements ChannelPipelineFactory {
	
	private ChannelPipeline pipeline;

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		pipeline = new DefaultChannelPipeline();
		pipeline.addLast("encoder", new Encoder());
		pipeline.addLast("decoder", new LoginDecoder());
		pipeline.addLast("handler", new ChannelHandler());
		return pipeline;
	}

}
