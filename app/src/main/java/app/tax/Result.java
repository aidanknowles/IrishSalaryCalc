package app.tax;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Result extends Activity {

    TextView gross;
    TextView pension;
    TextView sacrifice;
    TextView taxableSalary;
    TextView incomeGross;
    TextView credits;
    TextView incomePayable;
    TextView prsi;
    TextView usc;
    TextView deductions;
    TextView netSalary;
    TextView monthlyIncome;
    TextView weeklyIncome;
    TextView dailyIncome;
    TextView bik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        gross = (TextView) findViewById(R.id.gross_value);
        pension = (TextView) findViewById(R.id.pension_value);
        sacrifice = (TextView) findViewById(R.id.sacrifice_value);
        taxableSalary = (TextView) findViewById(R.id.taxable_value);
        incomeGross = (TextView) findViewById(R.id.incomeGross_value);
        credits = (TextView) findViewById(R.id.credits_value);
        incomePayable = (TextView) findViewById(R.id.incomePayable_value);
        prsi = (TextView) findViewById(R.id.prsi_value);
        usc = (TextView) findViewById(R.id.usc_value);
        deductions = (TextView) findViewById(R.id.deductions_value);
        netSalary = (TextView) findViewById(R.id.net_value);
        monthlyIncome = (TextView) findViewById(R.id.monthly_value);
        weeklyIncome = (TextView) findViewById(R.id.weekly_value);
        dailyIncome = (TextView) findViewById(R.id.daily_value);
        bik = (TextView) findViewById(R.id.bik_value);

        Bundle b = getIntent().getExtras();
        gross.setText(b.getString("grossSalary"));
        pension.setText(b.getString("pension"));
        sacrifice.setText(b.getString("sacrifice"));
        bik.setText(b.getString("bik"));
        taxableSalary.setText(b.getString("taxableSalary"));
        incomeGross.setText(b.getString("incomeTax"));
        credits.setText(b.getString("credits"));
        incomePayable.setText(b.getString("netIncomeTax"));
        prsi.setText(b.getString("prsi"));
        usc.setText(b.getString("usc"));
        deductions.setText(b.getString("totalDeduction"));
        netSalary.setText(b.getString("netSalary"));
        monthlyIncome.setText(b.getString("monthlyIncome"));
        weeklyIncome.setText(b.getString("weeklyIncome"));
        dailyIncome.setText(b.getString("dailyIncome"));

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
}
