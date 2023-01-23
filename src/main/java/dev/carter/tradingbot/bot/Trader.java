package dev.carter.tradingbot.bot;

import com.binance.connector.client.impl.SpotClientImpl;
import dev.carter.tradingbot.privatedata.PrivateConfig;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;

public class Trader {

    public boolean newBuyOrder(float quantity){
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        parameters.put("symbol", "BTCBUSD");
        parameters.put("side", "BUY");
        parameters.put("type", "MARKET");
        parameters.put("quoteOrderQty", (int)quantity);
        try {
            client.createTrade().newOrder(parameters);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to complete buy order: " + e.getMessage());
            return false;
        }
    }

    DecimalFormat df = new DecimalFormat("0.00000");

    public boolean newSellOrder(float quantity){

        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        parameters.put("symbol", "BTCBUSD");
        parameters.put("side", "SELL");
        parameters.put("type", "MARKET");
        parameters.put("quantity", quantity);
        try {
            client.createTrade().newOrder(parameters);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to complete sell order: " + e.getMessage());
            return false;
        }

    }

}
