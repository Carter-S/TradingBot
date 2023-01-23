package dev.carter.tradingbot.bot;

import dev.carter.tradingbot.marketdata.Coin;
import dev.carter.tradingbot.marketdata.CryptoMarket;
import dev.carter.tradingbot.tradingalgorithm.RocAlgorithm;
import dev.carter.tradingbot.userdata.Wallet;
import javafx.application.Platform;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Bot extends Thread{

    private Wallet wallet; //Store balances of different coins in wallet
    private Trader trader; //Used for making buy and sell orders
    private CryptoMarket cryptoMarket; //Used for getting price updates
    private RocAlgorithm rocAlgorithm; //Used to calculate buy signal

    private boolean openOrder = false; //Used to check for an open order

    public Bot(Wallet wallet){
        this.wallet = wallet;
        cryptoMarket = new CryptoMarket("BTCBUSD");
        trader = new Trader();
        rocAlgorithm = new RocAlgorithm();
    }

    public void run(){

        for(Coin c : wallet.getBalances()){
            if (c.getName().equals("BTC")) {
                openOrder = true;
                break;
            }
        }

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                float roc = rocAlgorithm.getRoc("BTCBUSD");
                System.out.println(roc);
                if(roc > 0 && !openOrder){
                    try{
                        Thread.sleep(10000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    roc = rocAlgorithm.getRoc("BTCBUSD");
                    if(roc > 0){
                        System.out.println("BUY SIGNAL");
                        float quantity = 0;
                        for(Coin c: wallet.getBalances()){
                            if(c.getName().equals("BUSD")){
                                quantity = c.getQuantity();
                                break;
                            }
                        }
                        boolean order = trader.newBuyOrder(quantity);
                        if(order){
                            System.out.println("BUY ORDER SUCCESSFUL!");
                            openOrder = true;
                        }
                        wallet.updateBalances();
                    }
                }else if(roc <= 0 && openOrder){
                    try{
                        Thread.sleep(10000);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    roc = rocAlgorithm.getRoc("BTCBUSD");
                    if(roc <= 0){
                        System.out.println("SELL SIGNAL");
                        float quantity = 0;
                        for(Coin c: wallet.getBalances()){
                            if(c.getName().equals("BTC")){
                                quantity = c.getQuantity();
                                break;
                            }
                        }
                        boolean order = trader.newSellOrder(wallet.getBalances().get(0).getQuantity());
                        if(order){
                            System.out.println("SELL ORDER SUCCESSFUL!");
                            openOrder = false;
                        }
                        wallet.updateBalances();
                    }
                }
            });
        }, 0, 1, TimeUnit.SECONDS);
    }
}
