package app.tax;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DecimalFormat;


public class Main extends Activity {

    // Import Tax Rates (Irish Budget 2015)
    IncomeTaxRates t;

    // Form fields
    EditText grossSalary;
    EditText age;
    Spinner status;
    EditText pensionRate;
    EditText salarySacrifice;
    EditText bik;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = (IncomeTaxRates)getApplication();

        Button calculate = (Button) findViewById(R.id.calculate);
        grossSalary = (EditText) findViewById(R.id.gross_input);
        age = (EditText) findViewById(R.id.age_input);
        status = (Spinner) findViewById(R.id.status_input);
        pensionRate = (EditText) findViewById(R.id.pension_input);
        salarySacrifice = (EditText) findViewById(R.id.salary_sacrifice_input);
        bik = (EditText) findViewById(R.id.bik_input);

        SharedPreferences s = getPreferences(MODE_PRIVATE);
        grossSalary.setText(s.getString("grossSalary", null));
        age.setText(s.getString("age", null));
        pensionRate.setText(s.getString("pension", null));
        salarySacrifice.setText(s.getString("sacrifice", null));
        bik.setText(s.getString("bik", null));


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                int ageValue;
                double grossSalaryValue;
                double pensionRateValue;
                double salarySacrificeValue;
                double bikValue;
                String statusText = String.valueOf(status.getSelectedItem());

                try {
                    ageValue = Integer.parseInt(age.getText().toString());
                } catch (Exception e) {
                    ageValue = 30;
                }
                try {
                    grossSalaryValue = Double.parseDouble(grossSalary.getText().toString());
                } catch (Exception e) {
                    grossSalaryValue = 0;
                }
                try {
                    pensionRateValue = (Double.parseDouble(pensionRate.getText().toString())) / 100;
                } catch (Exception e) {
                    pensionRateValue = 0;
                }
                try {
                    salarySacrificeValue = Double.parseDouble(salarySacrifice.getText().toString());
                } catch (Exception e) {
                    salarySacrificeValue = 0;
                }
                try {
                    bikValue = Double.parseDouble(bik.getText().toString());
                } catch (Exception e) {
                    bikValue = 0;
                }

                double pension = Math.round(calcPension(grossSalaryValue, pensionRateValue));
                double taxCredits = getTaxCredits(statusText, ageValue, bikValue);
                double taxableSalary = Math.round(getTaxableSalary(grossSalaryValue, pensionRateValue, salarySacrificeValue, bikValue));
                double payeSalary = Math.round(getPayeSalary(grossSalaryValue, salarySacrificeValue, bikValue));
                double PRSI = Math.round(getPRSI(payeSalary, ageValue));
                double tax = Math.round(getTax(taxableSalary, statusText, ageValue));
                double netTax = Math.round(getNetTax(tax, taxCredits));
                double USC = Math.round(getUSC(payeSalary, grossSalaryValue, ageValue));
                double totalDeduction = Math.round(getTotalDeductions(netTax, PRSI, USC));
                double taxableSalaryWithoutBik = taxableSalary - bikValue;
                double netSalary = Math.round(getNetSalary(taxableSalaryWithoutBik, totalDeduction));
                double monthlyIncome = Math.round(getMonthlyIncome(netSalary));
                double weeklyIncome = Math.round(getWeeklyIncome(netSalary));
                double dailyIncome = Math.round(getDailyIncome(netSalary));


                String gs = String.valueOf(grossSalaryValue);
                gs = "€" + gs + "0";

                String ts = String.valueOf(taxableSalary);
                ts = "€" + ts + "0";

                String ns = String.valueOf(netSalary);
                ns = "€" + ns + "0";

                String p = String.valueOf(pension);
                p = "€" + p + "0";

                String s = String.valueOf(salarySacrificeValue);
                s = "€" + s + "0";

                String c = String.valueOf(taxCredits);
                c = "€" + c + "0";

                String prsi = String.valueOf(PRSI);
                prsi = "€" + prsi + "0";

                String usc = String.valueOf(USC);
                usc = "€" + usc + "0";

                String it = String.valueOf(tax);
                it = "€" + it + "0";

                String nit = String.valueOf(netTax);
                nit = "€" + nit + "0";

                String td = String.valueOf(totalDeduction);
                td = "€" + td + "0";

                String mi = String.valueOf(monthlyIncome);
                mi = "€" + mi + "0";

                String wi = String.valueOf(weeklyIncome);
                wi = "€" + wi + "0";

                String di = String.valueOf(dailyIncome);
                di = "€" + di + "0";

                String bik = String.valueOf(bikValue);
                bik = "€" + bik + "0";

                b.putString("grossSalary", gs);
                b.putString("taxableSalary", ts);
                b.putString("netSalary", ns);
                b.putString("pension", p);
                b.putString("sacrifice", s);
                b.putString("credits", c);
                b.putString("prsi", prsi);
                b.putString("usc", usc);
                b.putString("incomeTax", it);
                b.putString("netIncomeTax", nit);
                b.putString("totalDeduction", td);
                b.putString("monthlyIncome", mi);
                b.putString("weeklyIncome", wi);
                b.putString("dailyIncome", di);
                b.putString("bik", bik);

                Intent i = new Intent(Main.this, Result.class);
                i.putExtras(b);
                startActivity(i);

            }
        });
    }

    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor e = getPreferences(MODE_PRIVATE).edit();
        e.putString("grossSalary", grossSalary.getText().toString());
        e.putString("age", age.getText().toString());
        e.putString("pension", pensionRate.getText().toString());
        e.putString("sacrifice", salarySacrifice.getText().toString());
        e.putString("bik", bik.getText().toString());
        e.apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public double getTaxCredits(String status, int age, double bik) {
        bik = bik * .2;
        if (status.equals("Single")) {
            if (age >= 65) {
                return t.getTaxCreditsSingleOver65() + bik;
            }
            return t.getTaxCreditsSingle() + bik;
        } else if (status.equals("Married")) {
            if (age >= 65) {
                return t.getTaxCreditsMarriedOver65() + bik;
            }
            return t.getTaxCreditsMarried() + bik;
        } else if (status.equals("Widowed")) {
            if (age >= 65) {
                return t.getTaxCreditsWidowedOver65() + bik;
            }
            return t.getTaxCreditsWidowed() + bik;
        } else if (status.equals("Lone Parent")) {
            if (age >= 65) {
                return t.getTaxCreditsLoneParentOver65() + bik;
            }
            return t.getTaxCreditsLoneParent() + bik;
        } else {
            return 0;
        }
    }

    public double getMonthlyIncome(double netSalary) {
        return netSalary / 12;
    }

    public double getWeeklyIncome(double netSalary) {
        return netSalary / 52;
    }

    public double getDailyIncome(double netSalary) {
        return netSalary / 365;
    }

    public double getTaxableSalary(double salary, double pensionRate, double salarySacrifice, double bikValue) {
        if (bikValue <= 250) {
            return salary - calcPension(salary, pensionRate) - salarySacrifice;
        } else {
            return salary - calcPension(salary, pensionRate) - salarySacrifice + bikValue;
        }
    }

    public double getPayeSalary(double salary, double salarySacrifice, double bikValue) {
        if (bikValue <= 250) {
            return salary - salarySacrifice;
        } else {
            return salary - salarySacrifice + bikValue;
        }
    }

    public double getNetSalary(double taxableSalary, double netTax) {
        return taxableSalary - netTax;
    }

    public double getNetTax(double grossTax, double credits) {
        double netTax = grossTax - credits;
        if (netTax <= 0) {
            return 0;
        } else {
            return netTax;
        }
    }

    public double getTotalDeductions(double netTax, double PRSI, double USC) {
        return netTax + PRSI + USC;
    }

    public double getPRSI(double payeSalary, int age) {
        if (age > 66 || payeSalary <= t.getPrsiFreeThreshold()) {
            return 0;
        } else {
            return payeSalary * t.getPrsiRate();
        }
    }

    public double getUSC(double payeSalary, double grossSalary, int age) {
        if (grossSalary <= t.getUSCFreeThreshold()) {
            return 0;
        } else if (payeSalary <= t.getUscThreshold1()) {
            return calcUsc1(payeSalary);
        } else if (age >= 70) {
            double usc1 = calcUsc1(t.getUscThreshold1());
            double usc2 = calcUsc2(payeSalary - t.getUscThreshold1());
            return usc1 + usc2;
        } else if (payeSalary <= t.getUscThreshold2()) {
            double usc1 = calcUsc1(t.getUscThreshold1());
            double usc2 = calcUsc2(payeSalary - t.getUscThreshold1());
            return usc1 + usc2;
        } else if (payeSalary <= t.getUscThreshold3()) {
            double usc1 = calcUsc1(t.getUscThreshold1());
            double usc2 = calcUsc2(t.getUscThreshold2() - t.getUscThreshold1());
            double usc3 = calcUsc3(payeSalary - t.getUscThreshold2());
            return usc1 + usc2 + usc3;
        } else if (payeSalary > t.getUscThreshold3()) {
            double usc1 = calcUsc1(t.getUscThreshold1());
            double usc2 = calcUsc2(t.getUscThreshold2() - t.getUscThreshold1());
            double usc3 = calcUsc3(t.getUscThreshold3() - t.getUscThreshold2());
            double usc4 = calcUsc4(payeSalary - t.getUscThreshold3());
            return usc1 + usc2 + usc3 + usc4;
        } else {
            return 0;
        }
    }

    public double getTax(double taxableSalary, String status, int age) {
        if (status.equals("Single")) {
            if (age >= 65 && taxableSalary <= 18000.00) {
                return 0;
            }
            if (taxableSalary <= t.getStandardTaxThresholdSingle()) {
                return calcStandardTax(taxableSalary);
            } else {
                double standardSalary = t.getStandardTaxThresholdSingle();
                double higherSalary = taxableSalary - standardSalary;
                double standardTax = calcStandardTax(standardSalary);
                double higherTax = calcHigherTax(higherSalary);
                return standardTax + higherTax;
            }
        } else if(status.equals("Married")) {
            if (age >= 65 && taxableSalary <= 36000.00) {
                return 0;
            }
            if (taxableSalary <= t.getStandardTaxThresholdMarried()) {
                return calcStandardTax(taxableSalary);
            } else {
                double standardSalary = t.getStandardTaxThresholdMarried();
                double higherSalary = taxableSalary - standardSalary;
                double standardTax = calcStandardTax(standardSalary);
                double higherTax = calcHigherTax(higherSalary);
                return standardTax + higherTax;
            }
        } else if(status.equals("Widowed")) {
            if (age >= 65 && taxableSalary <= 18000.00) {
                return 0;
            }
            if (taxableSalary <= t.getStandardTaxThresholdWidowed()) {
                return calcStandardTax(taxableSalary);
            } else {
                double standardSalary = t.getStandardTaxThresholdWidowed();
                double higherSalary = taxableSalary - standardSalary;
                double standardTax = calcStandardTax(standardSalary);
                double higherTax = calcHigherTax(higherSalary);
                return standardTax + higherTax;
            }
        }  else if(status.equals("Lone Parent")) {
            if (taxableSalary <= t.getStandardTaxThresholdLoneParent()) {
                return calcStandardTax(taxableSalary);
            } else {
                double standardSalary = t.getStandardTaxThresholdLoneParent();
                double higherSalary = taxableSalary - standardSalary;
                double standardTax = calcStandardTax(standardSalary);
                double higherTax = calcHigherTax(higherSalary);
                return standardTax + higherTax;
            }
        } else {
            return 0;
        }
    }

    public double calcStandardTax(double amount) {
        return amount * t.getStandardTaxRate();
    }

    public double calcHigherTax(double amount) {
        return amount * t.getHigherTaxRate();
    }

    public double calcUsc1(double amount) {
        return amount * t.getUscRate1();
    }

    public double calcUsc2(double amount) {
        return amount * t.getUscRate2();
    }

    public double calcUsc3(double amount) {
        return amount * t.getUscRate3();
    }

    public double calcUsc4(double amount) {
        return amount * t.getUscRate4();
    }

    public double calcPension(double grossSalary, double pensionRate) {
        return grossSalary * pensionRate;
    }

}
