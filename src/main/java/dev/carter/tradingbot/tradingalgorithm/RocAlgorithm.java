package dev.carter.tradingbot.tradingalgorithm;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.spot.Market;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import dev.carter.tradingbot.privatedata.PrivateConfig;

import java.util.LinkedHashMap;

public final class RocAlgorithm{

    public float getRoc(String symbol){
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        Market market = client.createMarket();
        parameters.put("symbol", symbol);
        parameters.put("interval", "4h");
        parameters.put("limit", "10");
        String result = market.klines(parameters);
        JsonArray candles = new JsonParser().parse(result).getAsJsonArray();
        float currentClose = candles.get(9).getAsJsonArray().get(4).getAsFloat();
        float periodClose = candles.get(0).getAsJsonArray().get(4).getAsFloat();
        return ((currentClose - periodClose)/periodClose) * 100;
    }

}

