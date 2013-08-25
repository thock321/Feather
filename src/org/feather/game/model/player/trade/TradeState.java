package org.feather.game.model.player.trade;

public enum TradeState {
	
	SENDING_TRADE,
	TRADING,
	AWAITING_CONFIRMATION,
	AWAITING_SECOND_CONFIRMATION,
	COMPLETE,
	DECLINED,
	DECLINING;

}
