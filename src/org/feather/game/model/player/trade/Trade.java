package org.feather.game.model.player.trade;

import org.feather.game.model.item.ItemHolder;
import org.feather.game.model.player.Player;

public class Trade {
	
	public Trade(Player trader, Player tradee) {
		this.trader = trader;
		this.tradee = tradee;
	}
	
	private Player trader;
	private Player tradee;
	
	private ItemHolder traderOffer = new ItemHolder(28);
	
	private TradeState tradeState = TradeState.SENDING_TRADE;
	
	public void sendTrade() {
		trader.addTempVariable("tradee", tradee);
		trader.addTempVariable("tradeSession", this);
		if (tradee.getTempVariable("tradee") == trader) {
			enterTrade();
		} else {
			trader.getPacketSender().sendMessage("Sending trade request...");
			tradee.getPacketSender().sendMessage(trader.details().getUsername() + ":tradereq:");
		}
	}
	
	public void enterTrade() {
		tradeState = TradeState.TRADING;
	}
	
	public void declineTrade() {
		
	}

}
