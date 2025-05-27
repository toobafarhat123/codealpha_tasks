import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class StockTradingPlatform extends JFrame {

    private JTable marketTable, portfolioTable;
    private DefaultTableModel marketModel, portfolioModel;
    private Map<String, Double> stockPrices = new HashMap<>();
    private Map<String, Integer> portfolio = new HashMap<>();

    public StockTradingPlatform() {
        setTitle("Simulated Stock Trading Platform");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        initializeMarketData();

       
        marketModel = new DefaultTableModel(new Object[]{"Stock", "Price"}, 0);
        marketTable = new JTable(marketModel);
        updateMarketTable();

       
        portfolioModel = new DefaultTableModel(new Object[]{"Stock", "Shares", "Current Value"}, 0);
        portfolioTable = new JTable(portfolioModel);
        updatePortfolioTable();

      
        JPanel marketPanel = new JPanel(new BorderLayout());
        marketPanel.setBorder(BorderFactory.createTitledBorder("Market Data"));
        marketPanel.add(new JScrollPane(marketTable), BorderLayout.CENTER);

        JPanel portfolioPanel = new JPanel(new BorderLayout());
        portfolioPanel.setBorder(BorderFactory.createTitledBorder("Your Portfolio"));
        portfolioPanel.add(new JScrollPane(portfolioTable), BorderLayout.CENTER);

        
        JPanel controlPanel = new JPanel(new FlowLayout());

        JTextField stockField = new JTextField(10);
        JTextField quantityField = new JTextField(5);
        JButton buyButton = new JButton("Buy");
        JButton sellButton = new JButton("Sell");
        JButton refreshButton = new JButton("Refresh Prices");

        controlPanel.add(new JLabel("Stock:"));
        controlPanel.add(stockField);
        controlPanel.add(new JLabel("Qty:"));
        controlPanel.add(quantityField);
        controlPanel.add(buyButton);
        controlPanel.add(sellButton);
        controlPanel.add(refreshButton);

       
        buyButton.addActionListener(e -> tradeStock(stockField.getText(), quantityField.getText(), true));
        sellButton.addActionListener(e -> tradeStock(stockField.getText(), quantityField.getText(), false));
        refreshButton.addActionListener(e -> {
            simulatePriceChanges();
            updateMarketTable();
            updatePortfolioTable();
        });

      
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, marketPanel, portfolioPanel);
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void initializeMarketData() {
        stockPrices.put("AAPL", 175.0);
        stockPrices.put("GOOGL", 135.0);
        stockPrices.put("TSLA", 210.0);
        stockPrices.put("AMZN", 120.0);
        stockPrices.put("MSFT", 310.0);
    }

    private void updateMarketTable() {
        marketModel.setRowCount(0);
        for (Map.Entry<String, Double> entry : stockPrices.entrySet()) {
            marketModel.addRow(new Object[]{entry.getKey(), String.format("$%.2f", entry.getValue())});
        }
    }

    private void updatePortfolioTable() {
        portfolioModel.setRowCount(0);
        for (String stock : portfolio.keySet()) {
            int shares = portfolio.get(stock);
            double value = shares * stockPrices.getOrDefault(stock, 0.0);
            portfolioModel.addRow(new Object[]{stock, shares, String.format("$%.2f", value)});
        }
    }

    private void tradeStock(String symbol, String qtyStr, boolean isBuy) {
        symbol = symbol.toUpperCase().trim();
        if (!stockPrices.containsKey(symbol)) {
            JOptionPane.showMessageDialog(this, "Stock not found!");
            return;
        }

        try {
            int qty = Integer.parseInt(qtyStr);
            if (qty <= 0) throw new NumberFormatException();

            int currentQty = portfolio.getOrDefault(symbol, 0);

            if (isBuy) {
                portfolio.put(symbol, currentQty + qty);
            } else {
                if (qty > currentQty) {
                    JOptionPane.showMessageDialog(this, "Not enough shares to sell!");
                    return;
                }
                if (qty == currentQty) {
                    portfolio.remove(symbol);
                } else {
                    portfolio.put(symbol, currentQty - qty);
                }
            }

            updatePortfolioTable();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity!");
        }
    }

    private void simulatePriceChanges() {
        Random rand = new Random();
        for (String stock : stockPrices.keySet()) {
            double currentPrice = stockPrices.get(stock);
            double changePercent = (rand.nextDouble() - 0.5) * 0.1; // ±5%
            double newPrice = currentPrice * (1 + changePercent);
            stockPrices.put(stock, Math.round(newPrice * 100.0) / 100.0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StockTradingPlatform app = new StockTradingPlatform();
            app.setVisible(true);
        });
    }
}
