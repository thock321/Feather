package org.feather.game.model.player.trade;

import org.feather.game.model.player.Player;

public class TradeHandler {
	
	public void sendTrade(Player trader, Player tradee) {
		new Trade(trader, tradee).sendTrade();
	}

}
