package app.tax;

import android.app.Application;


public class IncomeTaxRates extends Application {

    public double getStandardTaxRate() {
        return 0.20;
    }

    public double getHigherTaxRate() {
        return 0.40;
    }

    public double getStandardTaxThresholdSingle() {
        return 33800.00;
    }

    public double getStandardTaxThresholdMarried() {
        return 42800.00;
    }

    public double getStandardTaxThresholdWidowed() {
        return 37800.00;
    }

    public double getStandardTaxThresholdLoneParent() {
        return 37800.00;
    }

    public double getTaxCreditsSingle() {
        return 3300.00;
    }

    public double getTaxCreditsSingleOver65() {
        return 3545.00;
    }

    public double getTaxCreditsMarried() {
        return 4950.00;
    }

    public double getTaxCreditsMarriedOver65() {
        return 5440.00;
    }

    public double getTaxCreditsWidowed() {
        return 3840.00;
    }

    public double getTaxCreditsWidowedOver65() {
        return 4085.00;
    }

    public double getTaxCreditsLoneParent() {
        return 4950.00;
    }

    public double getTaxCreditsLoneParentOver65() {
        return 5195.00;
    }

    public double getPrsiFreeThreshold() {
        return 18303.00;
    }

    public double getPrsiRate() {
        return 0.04;
    }

    public double getUscRate1() {
        return 0.015;
    }

    public double getUscRate2() {
        return 0.035;
    }

    public double getUscRate3() {
        return 0.07;
    }

    public double getUscRate4() {
        return 0.08;
    }

    public double getUSCFreeThreshold() {
        return 12012.00;
    }


    public double  getUscThreshold1() {
        return 12012.00;
    }

    public double getUscThreshold2() {
        return 17576.00;
    }

    public double getUscThreshold3() {
        return 70044.00;
    }

}
