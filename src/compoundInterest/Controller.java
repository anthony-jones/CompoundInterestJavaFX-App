package compoundInterest;

import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Controller {
    private ArrayList<Double> resultValues;
    public BarChart<Integer, Double> compBarChart;
    public TextField initInvestmentField;
    public TextField rateField;
    public TextField growthPeriodField;
    public TextField monthlyPmtField;
    public Label finalAmount;

    public Controller() {
        this.resultValues = new ArrayList<>();
    }

    // Updates information for every key typed into the text fields
    public void update() {
        double initial = 0, rate = 0, monthly = 0;
        int period = 0;

        // Sets respective values to 0 by default if text field is empty
        if (!this.initInvestmentField.getText().isEmpty()) {
            initial = Double.parseDouble(this.initInvestmentField.getText());
        }
        if (!this.rateField.getText().isEmpty()) {
            rate = Double.parseDouble(this.rateField.getText());
        }
        if (!this.monthlyPmtField.getText().isEmpty()) {
            monthly = Double.parseDouble(this.monthlyPmtField.getText());
        }
        if (!this.growthPeriodField.getText().isEmpty()) {
            period = Integer.parseInt(this.growthPeriodField.getText());
        }

        this.valueComputer(initial, rate, monthly, period);

        // Converts array list to chart compatible data
        XYChart.Series data = new XYChart.Series();
        for (int i = 0; i < this.resultValues.size(); i++) {
            data.getData().add(new XYChart.Data(String.valueOf((i + 1)), this.resultValues.get(i)));
        }

        // Plots chart data
        this.compBarChart.getData().clear();
        this.compBarChart.setAnimated(true); // animation turned on and off due to JavaFX animation 'buggy-ness' affecting x-axis
        this.compBarChart.getData().addAll(data);
        this.compBarChart.setAnimated(false);
    }

    // Populates array list of annual compound interest data and displays final amount
    private void valueComputer(double initial, double rate, double monthly, int period) {
        this.resultValues = new ArrayList<>();
        double yearAmount = initial;
        if (period == 0) {
            yearAmount = 0;
            this.resultValues.add(yearAmount);
        } else {
            if (rate == 0) {
                for (int year = 1; year <= period; year++) {
                    for (int month = 0; month < 12; month++) {
                        yearAmount += monthly;
                    }
                    this.resultValues.add(yearAmount);
                }
            } else {
                for (int year = 1; year <= period; year++) {
                    for (int month = 0; month < 12; month++) {
                        double temp = yearAmount + monthly;
                        yearAmount = temp * ((rate * .01) / 12) + temp;
                    }
                    this.resultValues.add(yearAmount);
                }
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("$###,###,##0.00");
        this.finalAmount.setText(decimalFormat.format(yearAmount));
    }
}