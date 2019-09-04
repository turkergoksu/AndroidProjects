package com.turkergoksu.cookieclickernew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Thread thread;

    private ImageButton monsterButton;
    private Button resetButton;
    private Button saveButton;


    private TextView cookiesTextView;
    private TextView cookiesPerSecondTextView;
    private double cookies = 0;
    private double cookiesPerSecond = 0;

    private final int CURSOR_BASE_PRICE = 15;
    //CPS = Cookies per second
    private final double CURSOR_CPS = 0.1;
    private ImageButton cursorButton;
    private TextView cursorTextView;
    private TextView cursorPriceTextView;
    private double cursorNthPrice = CURSOR_BASE_PRICE;
    private int totalCursor = 0;

    private final int GRANDMA_BASE_PRICE = 100;
    private final int GRANDMA_CPS = 1;
    private ImageButton grandmaButton;
    private TextView grandmaTextView;
    private TextView grandmaPriceTextView;
    private double grandmaNthPrice = GRANDMA_BASE_PRICE;
    private int totalGrandma = 0;

    private final int FARM_BASE_PRICE = 1100;
    private final int FARM_CPS = 8;
    private ImageButton farmButton;
    private TextView farmTextView;
    private TextView farmPriceTextView;
    private double farmNthPrice = FARM_BASE_PRICE;
    private int totalFarm = 0;

    private final int MINE_BASE_PRICE = 12000;
    private final int MINE_CPS = 47;
    private ImageButton mineButton;
    private TextView mineTextView;
    private TextView minePriceTextView;
    private double mineNthPrice = MINE_BASE_PRICE;
    private int totalMine = 0;

    private final int FACTORY_BASE_PRICE = 130000;
    private final int FACTORY_CPS = 260;
    private ImageButton factoryButton;
    private TextView factoryTextView;
    private TextView factoryPriceTextView;
    private double factoryNthPrice = FACTORY_BASE_PRICE;
    private int totalFactory = 0;

    private final int BANK_BASE_PRICE = 1400000;
    private final int BANK_CPS = 1400;
    private ImageButton bankButton;
    private TextView bankTextView;
    private TextView bankPriceTextView;
    private double bankNthPrice = BANK_BASE_PRICE;
    private int totalBank = 0;

    private final int TEMPLE_BASE_PRICE = 20000000;
    private final int TEMPLE_CPS = 7800;
    private ImageButton templeButton;
    private TextView templeTextView;
    private TextView templePriceTextView;
    private double templeNthPrice = TEMPLE_BASE_PRICE;
    private int totalTemple = 0;

    private final int LAB_BASE_PRICE = 330000000;
    private final int LAB_CPS = 44000;
    private ImageButton labButton;
    private TextView labTextView;
    private TextView labPriceTextView;
    private double labNthPrice = LAB_BASE_PRICE;
    private int totalLab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPreferences();
        addCookiesPerSecond();
        monsterButton();
        resetButton();
        saveButton();

        cookies = 2315235;
        cursor();
        grandma();
        farm();
        mine();
        factory();
        bank();
        temple();
        lab();
    }

    @Override
    protected void onStop() {
        super.onStop();
        savePreferences();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        savePreferences();
    }

    protected void monsterButton(){
        monsterButton = (ImageButton)findViewById(R.id.monsterButton);
        cookiesTextView = (TextView)findViewById(R.id.cookiesTextView);
        cookiesPerSecondTextView = (TextView)findViewById(R.id.cookiesPerSecondTextView);
        View.OnClickListener monsterClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookies++;
                refreshCookies();
            }
        };
        monsterButton.setOnClickListener(monsterClickListener);
    }

    protected void resetButton(){
        resetButton = (Button)findViewById(R.id.resetButton);
        View.OnClickListener resetClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch(i){
                            case DialogInterface.BUTTON_POSITIVE:
                                resetCookies();
                                resetCursor();
                                resetGrandma();
                                resetFarm();
                                resetMine();
                                resetFactory();
                                resetBank();
                                resetTemple();
                                resetLab();
                                refreshItems();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", dialogClickListener);
                builder.setNegativeButton("No", dialogClickListener);
                builder.show();
            }
        };
        resetButton.setOnClickListener(resetClickListener);
    }

    protected void saveButton(){
        saveButton = (Button)findViewById(R.id.saveButton);
        View.OnClickListener saveClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                savePreferences();
                Toast.makeText(getApplicationContext(), "Successfully saved!", Toast.LENGTH_SHORT).show();
            }
        };
        saveButton.setOnClickListener(saveClickListener);
    }

    protected void refreshItems(){
        refreshCookies();
        refreshCursor();
        refreshGrandma();
        refreshFarm();
        refreshMine();
        refreshFactory();
        refreshBank();
        refreshTemple();
        refreshLab();
    }

    protected void addCookiesPerSecond(){
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!thread.isInterrupted()) {
                        Thread.sleep(1000);
                        cookies += cookiesPerSecond;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshCookies();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    protected void refreshCookies(){
        cookiesPerSecondTextView.setText(new DecimalFormat("##.#").format(cookiesPerSecond) + " cookies per second");
        cookiesTextView.setText(Integer.toString((int)cookies) + " Cookies!");
    }

    protected void resetCookies(){
        cookies = 0;
        cookiesPerSecond = 0;
    }

    protected void cursor(){
        cursorButton = (ImageButton)findViewById(R.id.cursorButton);
        cursorTextView = (TextView)findViewById(R.id.cursorTextView);
        cursorPriceTextView = (TextView)findViewById(R.id.cursorPriceTextView);
        refreshCursor();

        View.OnClickListener cursorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((int)cookies >= (int)cursorNthPrice){
                    totalCursor++;
                    cookies -= cursorNthPrice;
                    cookiesPerSecond += CURSOR_CPS;
                    cursorNthPrice = Math.ceil(CURSOR_BASE_PRICE*Math.pow(1.15, totalCursor));

                    refreshCursor();
                    refreshCookies();
                } else {
                    Toast.makeText(getApplicationContext(), "You don't have enough cookies!", Toast.LENGTH_LONG).show();
                }
            }
        };
        cursorButton.setOnClickListener(cursorClickListener);

    }

    protected void refreshCursor(){
        cursorTextView.setText("Cursor: " + totalCursor + " * " + CURSOR_CPS);
        cursorPriceTextView.setText("Price: " + (int)cursorNthPrice);
    }

    protected void resetCursor(){
        totalCursor = 0;
        cursorNthPrice = CURSOR_BASE_PRICE;
    }

    protected void grandma(){
        grandmaButton = (ImageButton)findViewById(R.id.grandmaButton);
        grandmaTextView = (TextView)findViewById(R.id.grandmaTextView);
        grandmaPriceTextView = (TextView)findViewById(R.id.grandmaPriceTextView);
        refreshGrandma();

        View.OnClickListener grandmaClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((int)cookies >= (int)grandmaNthPrice){
                    totalGrandma++;
                    cookies -= grandmaNthPrice;
                    cookiesPerSecond += GRANDMA_CPS;
                    grandmaNthPrice = Math.ceil(GRANDMA_BASE_PRICE*Math.pow(1.15, totalGrandma));

                    refreshGrandma();
                } else {
                    Toast.makeText(getApplicationContext(), "You don't have enough cookies!", Toast.LENGTH_LONG).show();
                }
            }
        };
        grandmaButton.setOnClickListener(grandmaClickListener);
    }

    protected void refreshGrandma(){
        grandmaTextView.setText("Grandma: " + totalGrandma + " * " + GRANDMA_CPS);
        grandmaPriceTextView.setText("Price: " + (int)grandmaNthPrice);
    }

    protected void resetGrandma(){
        totalGrandma = 0;
        grandmaNthPrice = GRANDMA_BASE_PRICE;
    }

    protected void farm(){
        farmButton = (ImageButton)findViewById(R.id.farmButton);
        farmTextView = (TextView)findViewById(R.id.farmTextView);
        farmPriceTextView = (TextView)findViewById(R.id.farmPriceTextView);
        refreshFarm();

        View.OnClickListener farmClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((int)cookies >= (int)farmNthPrice){
                    totalFarm++;
                    cookies -= farmNthPrice;
                    cookiesPerSecond += FARM_CPS;
                    farmNthPrice = Math.ceil(FARM_BASE_PRICE*Math.pow(1.15, totalFarm));

                    refreshFarm();
                } else {
                    Toast.makeText(getApplicationContext(), "You don't have enough cookies!", Toast.LENGTH_LONG).show();
                }
            }
        };
        farmButton.setOnClickListener(farmClickListener);
    }

    protected void refreshFarm(){
        farmTextView.setText("Farm: " + totalFarm + " * " + FARM_CPS);
        farmPriceTextView.setText("Price: " + (int)farmNthPrice);
    }

    protected void resetFarm(){
        totalFarm = 0;
        farmNthPrice = FARM_BASE_PRICE;
    }

    protected void mine(){
        mineButton = (ImageButton)findViewById(R.id.mineButton);
        mineTextView = (TextView)findViewById(R.id.mineTextView);
        minePriceTextView = (TextView)findViewById(R.id.minePriceTextView);
        refreshMine();

        View.OnClickListener mineClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((int)cookies >= (int)mineNthPrice){
                    totalMine++;
                    cookies -= mineNthPrice;
                    cookiesPerSecond += MINE_CPS;
                    mineNthPrice = Math.ceil(MINE_BASE_PRICE*Math.pow(1.15, totalMine));

                    refreshMine();
                } else {
                    Toast.makeText(getApplicationContext(), "You don't have enough cookies!", Toast.LENGTH_LONG).show();
                }
            }
        };
        mineButton.setOnClickListener(mineClickListener);
    }

    protected void refreshMine(){
        mineTextView.setText("Mine: " + totalMine + " * " + MINE_CPS);
        minePriceTextView.setText("Price: " + (int)mineNthPrice);
    }

    protected void resetMine(){
        totalMine = 0;
        mineNthPrice = MINE_BASE_PRICE;
    }

    protected void factory(){
        factoryButton = (ImageButton)findViewById(R.id.factoryButton);
        factoryTextView = (TextView)findViewById(R.id.factoryTextView);
        factoryPriceTextView = (TextView)findViewById(R.id.factoryPriceTextView);
        refreshFactory();

        View.OnClickListener factoryClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((int)cookies >= (int)factoryNthPrice){
                    totalFactory++;
                    cookies -= factoryNthPrice;
                    cookiesPerSecond += FACTORY_CPS;
                    factoryNthPrice = Math.ceil(FACTORY_BASE_PRICE*Math.pow(1.15, totalFactory));

                    refreshFactory();
                } else {
                    Toast.makeText(getApplicationContext(), "You don't have enough cookies!", Toast.LENGTH_LONG).show();
                }
            }
        };
        factoryButton.setOnClickListener(factoryClickListener);
    }

    protected void refreshFactory(){
        factoryTextView.setText("Factory: " + totalFactory + " * " + FACTORY_CPS);
        factoryPriceTextView.setText("Price: " + (int)factoryNthPrice);
    }

    protected void resetFactory(){
        totalFactory = 0;
        factoryNthPrice = FACTORY_BASE_PRICE;
    }

    protected void bank(){
        bankButton = (ImageButton)findViewById(R.id.bankButton);
        bankTextView = (TextView)findViewById(R.id.bankTextView);
        bankPriceTextView = (TextView)findViewById(R.id.bankPriceTextView);
        refreshBank();

        View.OnClickListener bankClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((int)cookies >= (int)bankNthPrice){
                    totalBank++;
                    cookies -= bankNthPrice;
                    cookiesPerSecond += BANK_CPS;
                    bankNthPrice = Math.ceil(BANK_BASE_PRICE*Math.pow(1.15, totalBank));

                    refreshBank();
                } else {
                    Toast.makeText(getApplicationContext(), "You don't have enough cookies!", Toast.LENGTH_LONG).show();
                }
            }
        };
        bankButton.setOnClickListener(bankClickListener);
    }

    protected void refreshBank(){
        bankTextView.setText("Bank: " + totalBank + " * " + BANK_CPS);
        bankPriceTextView.setText("Price: " + (int)bankNthPrice);
    }

    protected void resetBank(){
        totalBank = 0;
        bankNthPrice = BANK_BASE_PRICE;
    }

    protected void temple(){
        templeButton = (ImageButton)findViewById(R.id.templeButton);
        templeTextView = (TextView)findViewById(R.id.templeTextView);
        templePriceTextView = (TextView)findViewById(R.id.templePriceTextView);
        refreshTemple();

        View.OnClickListener templeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((int)cookies >= (int)templeNthPrice){
                    totalTemple++;
                    cookies -= templeNthPrice;
                    cookiesPerSecond += TEMPLE_CPS;
                    templeNthPrice = Math.ceil(TEMPLE_BASE_PRICE*Math.pow(1.15, totalTemple));

                    refreshTemple();
                } else {
                    Toast.makeText(getApplicationContext(), "You don't have enough cookies!", Toast.LENGTH_LONG).show();
                }
            }
        };
        templeButton.setOnClickListener(templeClickListener);
    }

    protected void refreshTemple(){
        templeTextView.setText("Temple: " + totalTemple + " * " + TEMPLE_CPS);
        templePriceTextView.setText("Price: " + (int)templeNthPrice);
    }

    protected void resetTemple(){
        totalTemple = 0;
        templeNthPrice = TEMPLE_BASE_PRICE;
    }

    protected void lab(){
        labButton = (ImageButton)findViewById(R.id.labButton);
        labTextView = (TextView)findViewById(R.id.labTextView);
        labPriceTextView = (TextView)findViewById(R.id.labPriceTextView);
        refreshLab();

        View.OnClickListener labClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((int)cookies >= (int)labNthPrice){
                    totalLab++;
                    cookies -= labNthPrice;
                    cookiesPerSecond += LAB_CPS;
                    labNthPrice = Math.ceil(LAB_BASE_PRICE*Math.pow(1.15, totalLab));

                    refreshLab();
                } else {
                    Toast.makeText(getApplicationContext(), "You don't have enough cookies!", Toast.LENGTH_LONG).show();
                }
            }
        };
        labButton.setOnClickListener(labClickListener);
    }

    protected void refreshLab(){
        labTextView.setText("Lab: " + totalLab + " * " + LAB_CPS);
        labPriceTextView.setText("Price: " + (int)labNthPrice);
    }

    protected void resetLab(){
        totalLab = 0;
        labNthPrice = LAB_BASE_PRICE;
    }

    protected void savePreferences(){
        SharedPreferences pref = this.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putFloat("cookies_per_second", (float)cookiesPerSecond);
        editor.putInt("total_cookies", (int)cookies);

        editor.putInt("total_cursor", totalCursor);
        editor.putInt("cursor_price", (int)cursorNthPrice);

        editor.putInt("total_grandma", totalGrandma);
        editor.putInt("grandma_price", (int)grandmaNthPrice);

        editor.putInt("total_farm", totalFarm);
        editor.putInt("farm_price", (int)farmNthPrice);

        editor.putInt("total_mine", totalMine);
        editor.putInt("mine_price", (int)mineNthPrice);

        editor.putInt("total_factory", totalFactory);
        editor.putInt("factory_price", (int)factoryNthPrice);

        editor.putInt("total_bank", totalBank);
        editor.putInt("bank_price", (int)bankNthPrice);

        editor.putInt("total_temple", totalTemple);
        editor.putInt("temple_price", (int)templeNthPrice);

        editor.putInt("total_lab", totalLab);
        editor.putInt("lab_price", (int)labNthPrice);

        editor.apply();
    }

    protected void setPreferences(){
        SharedPreferences pref = this.getPreferences(MODE_PRIVATE);

        cookiesPerSecond = pref.getFloat("cookies_per_second", 0);
        cookies = pref.getInt("total_cookies", 0);

        totalCursor = pref.getInt("total_cursor", 0);
        cursorNthPrice = (double)pref.getInt("cursor_price", CURSOR_BASE_PRICE);

        totalGrandma = pref.getInt("total_grandma", 0);
        grandmaNthPrice = (double)pref.getInt("grandma_price", GRANDMA_BASE_PRICE);

        totalFarm = pref.getInt("total_farm", 0);
        farmNthPrice = (double)pref.getInt("farm_price", FARM_BASE_PRICE);

        totalMine = pref.getInt("total_mine", 0);
        mineNthPrice = (double)pref.getInt("mine_price", MINE_BASE_PRICE);

        totalFactory = pref.getInt("total_factory", 0);
        factoryNthPrice = (double)pref.getInt("factory_price", FACTORY_BASE_PRICE);

        totalBank = pref.getInt("total_bank", 0);
        bankNthPrice = (double)pref.getInt("bank_price", BANK_BASE_PRICE);

        totalTemple = pref.getInt("total_temple", 0);
        templeNthPrice = (double)pref.getInt("temple_price", TEMPLE_BASE_PRICE);

        totalLab = pref.getInt("total_lab", 0);
        labNthPrice = (double)pref.getInt("lab_price", LAB_BASE_PRICE);
    }
}
