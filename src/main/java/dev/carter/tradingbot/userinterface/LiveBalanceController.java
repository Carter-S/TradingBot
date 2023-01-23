package dev.carter.tradingbot.userinterface;

import dev.carter.tradingbot.marketdata.CryptoMarket;
import dev.carter.tradingbot.bot.Bot;
import dev.carter.tradingbot.marketdata.Coin;
import dev.carter.tradingbot.userdata.Wallet;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LiveBalanceController implements Initializable {

    @FXML
    private CategoryAxis time = new CategoryAxis();
    @FXML
    private NumberAxis balance = new NumberAxis();
    @FXML
    private LineChart<String, Number> graph = new LineChart<>(time,balance);
    @FXML
    private Label balanceLabel = new Label();

    private Wallet wallet;
    private CryptoMarket btcgbp;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btcgbp = new CryptoMarket("BTCGBP");

        wallet = new Wallet();


        time.setAnimated(false);
        balance.setAnimated(false);
        graph.setAnimated(false);
        graph.setCreateSymbols(false);

        graph.setTitle("Spot Wallet Balance");
        balance.setLabel("Balance (£)");
        time.setLabel("Time");

        XYChart.Series<String, Number> balance = new XYChart.Series<>();

        balance.setName("Current balance (£)");

        graph.getData().add(balance);

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        Bot bot = new Bot(wallet);
        bot.start();

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                wallet.updateBalances();
                float totalBalance = 0;
                float btcPrice = btcgbp.getMarketUpdate();
                for(Coin coin: wallet.getBalances()){
                    totalBalance+=coin.getBtcValue()*btcPrice;
                }
                balanceLabel.setText("BALANCE: £"+df.format(totalBalance));
                Date now = new Date();
                if(balance.getData().size() > 60){
                    balance.getData().remove(0);
                }
                balance.getData().add(new XYChart.Data<>(simpleDateFormat.format(now), totalBalance));
            });
        }, 0, 60, TimeUnit.SECONDS);

    }

}