package dev.carter.tradingbot.marketdata;

import com.binance.connector.client.impl.SpotClientImpl;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.carter.tradingbot.privatedata.PrivateConfig;

import java.util.LinkedHashMap;

public class CryptoMarket {
    LinkedHashMap<String, Object> parameters;
    String result;
    SpotClientImpl client;

    public CryptoMarket(String symbol) {
        parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
    }

    public float getMarketUpdate(){
        result = client.createMarket().tickerSymbol(parameters);
        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        return jsonObject.get("price").getAsFloat();
    }
}
