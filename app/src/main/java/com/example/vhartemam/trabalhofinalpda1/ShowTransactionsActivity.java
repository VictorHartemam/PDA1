package com.example.vhartemam.trabalhofinalpda1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.vhartemam.trabalhofinalpda1.entity.CategoriaTransacao;
import com.example.vhartemam.trabalhofinalpda1.entity.TipoTransacao;
import com.example.vhartemam.trabalhofinalpda1.ui.TransacaoModeloTransacaoUi;

import java.util.List;

public class ShowTransactionsActivity extends AppCompatActivity {
    public static final String KEY_ACCOUNT_NUMBER = "KEY_ACCOUNT_NUMBER";

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private List<TransacaoModeloTransacaoUi> transacaoList;
    private int idConta;
    private TransactionAdapter transactionAdapter;

    private RadioGroup radioGroupQuery;
    private LinearLayout linearLayoutPeriod;
    private Spinner spinnerCategoires;
    private Button buttonSearch;
    private EditText editTextDtIni;
    private EditText editTextDtFim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_transactions);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radioGroupQuery = findViewById(R.id.radioGroupQuery);
        radioGroupQuery.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                handleRadioButton(group, checkedId);
            }
        });

        linearLayoutPeriod = findViewById(R.id.linearLayoutPeriod);
        spinnerCategoires = findViewById(R.id.spinnerCategoires);
        spinnerCategoires.setAdapter(MainActivity.spinnerAdapter);
        buttonSearch = findViewById(R.id.buttonSearch);
        editTextDtIni = findViewById(R.id.editTextDtIni);
        editTextDtFim = findViewById(R.id.editTextDtFim);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonSearch();
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        idConta = Integer.parseInt(getIntent().getExtras().get(KEY_ACCOUNT_NUMBER).toString());

        showAllTransactions();
    }

    private void showAllTransactions(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                transacaoList = MainActivity.db.transacaoDao().selectByIdConta(idConta);
                return null;
            }
        }.execute();

        try {
            Thread.sleep(100);
        } catch (Exception e) {
            Log.e("ERRO_SLEEP", e.getMessage());
        }

        transactionAdapter = new TransactionAdapter(transacaoList);
        recyclerView.setAdapter(transactionAdapter);
    }

    private void showDebitTransactions(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                transacaoList = MainActivity.db.transacaoDao().selectDebit(idConta, MainActivity.db.tipoTransacaoDao().selectOneDesc("DEBITO").getIdTipoTransacao());
                return null;
            }
        }.execute();

        try {
            Thread.sleep(100);
        } catch (Exception e) {
            Log.e("ERRO_SLEEP", e.getMessage());
        }

        transactionAdapter = new TransactionAdapter(transacaoList);
        recyclerView.setAdapter(transactionAdapter);
    }

    private void showCreditTransactions(){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                transacaoList = MainActivity.db.transacaoDao().selectDebit(idConta, MainActivity.db.tipoTransacaoDao().selectOneDesc("CREDITO").getIdTipoTransacao());
                return null;
            }
        }.execute();

        try {
            Thread.sleep(100);
        } catch (Exception e) {
            Log.e("ERRO_SLEEP", e.getMessage());
        }

        transactionAdapter = new TransactionAdapter(transacaoList);
        recyclerView.setAdapter(transactionAdapter);
    }

    private void showPeriodTransaction(){
        String dataIni = editTextDtIni.getText().toString();
        String dataFim = editTextDtFim.getText().toString();
        /*
        "03/04/1991"
        */
        try {
            final int anoIni = Integer.parseInt((dataIni.substring(0, 2)));
            final int mesIni = Integer.parseInt((dataIni.substring(3, 5)));
            final int diaIni = Integer.parseInt((dataIni.substring(6, 10)));

            final int anoFim = Integer.parseInt((dataFim.substring(0, 2)));
            final int mesFim = Integer.parseInt((dataFim.substring(3, 5)));
            final int diaFim = Integer.parseInt((dataFim.substring(6, 10)));

            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    transacaoList = MainActivity.db.transacaoDao().selectByPeriod(idConta, anoIni, mesIni, diaIni, anoFim, mesFim, diaFim);
                    return null;
                }
            }.execute();

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                Log.e("ERRO_SLEEP", e.getMessage());
            }

            transactionAdapter = new TransactionAdapter(transacaoList);
            recyclerView.setAdapter(transactionAdapter);

            Toast.makeText(getApplicationContext(), "" + anoIni + "-" + mesIni + "-" + diaIni + "\n"
                    + anoFim + "-" + mesFim + "-" + diaFim, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), R.string.msg_4, Toast.LENGTH_SHORT).show();
        }
    }

    private void showCategoryTransaction(){
        final String categoriaDesc = spinnerCategoires.getSelectedItem().toString();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                transacaoList = MainActivity.db.transacaoDao().selectByCategoria(idConta, MainActivity.db.categoriaTransacaoDao().selectOneDesc(categoriaDesc).getIdCategoriaTransacao());
                return null;
            }
        }.execute();

        try {
            Thread.sleep(100);
        } catch (Exception e) {
            Log.e("ERRO_SLEEP", e.getMessage());
        }

        transactionAdapter = new TransactionAdapter(transacaoList);
        recyclerView.setAdapter(transactionAdapter);
    }

    private void handleButtonSearch(){
        int idRadioButtonChecked = radioGroupQuery.getCheckedRadioButtonId();

        switch (idRadioButtonChecked) {
            case R.id.radioButtonPeriod:
                showPeriodTransaction();
                break;
            case R.id.radioButtonCategory:
                showCategoryTransaction();
                break;
        }
    }

    private void handleRadioButton(RadioGroup group, int checkedId){
        switch (checkedId){
            case R.id.radioButtonAll:
                linearLayoutPeriod.setVisibility(View.GONE);
                spinnerCategoires.setVisibility(View.GONE);
                buttonSearch.setVisibility(View.GONE);
                showAllTransactions();
                break;
            case R.id.radioButtonPeriod:
                linearLayoutPeriod.setVisibility(View.VISIBLE);
                spinnerCategoires.setVisibility(View.GONE);
                buttonSearch.setVisibility(View.VISIBLE);
                break;
            case R.id.radioButtonDebit:
                linearLayoutPeriod.setVisibility(View.GONE);
                spinnerCategoires.setVisibility(View.GONE);
                buttonSearch.setVisibility(View.GONE);
                showDebitTransactions();
                break;
            case R.id.radioButtonCredit:
                linearLayoutPeriod.setVisibility(View.GONE);
                spinnerCategoires.setVisibility(View.GONE);
                buttonSearch.setVisibility(View.GONE);
                showCreditTransactions();
                break;
            case R.id.radioButtonCategory:
                linearLayoutPeriod.setVisibility(View.GONE);
                spinnerCategoires.setVisibility(View.VISIBLE);
                buttonSearch.setVisibility(View.VISIBLE);
                break;
        }
    }
}
