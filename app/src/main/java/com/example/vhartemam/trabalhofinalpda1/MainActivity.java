package com.example.vhartemam.trabalhofinalpda1;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.vhartemam.trabalhofinalpda1.entity.CategoriaTransacao;
import com.example.vhartemam.trabalhofinalpda1.entity.Conta;
import com.example.vhartemam.trabalhofinalpda1.entity.ModeloTransacao;
import com.example.vhartemam.trabalhofinalpda1.entity.TipoTransacao;
import com.example.vhartemam.trabalhofinalpda1.entity.Transacao;
import com.example.vhartemam.trabalhofinalpda1.ui.TransacaoModeloTransacaoUi;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private final int ADD_NEW_CATEGORY = 1;

    HorizontalScrollView horizontalScrollView;
    LinearLayout linearLayoutScrowView;
    List<CardView> cardViewList = new LinkedList<>();
    static AppDataBase db;

    // tests
    List<CategoriaTransacao> categoriaTransacaoList;
    List<TipoTransacao> tipoTransacaoList;
    List<String> itensList = new ArrayList<>();
    static List<String> newItensList = new ArrayList<>();
    static ArrayAdapter<String> spinnerAdapter;
    List<Conta> contaList;
    List<TransacaoModeloTransacaoUi> transacaoList;
    TextView textViewTotalBalance;
    // end tests

    private void startFromZero(final AppDataBase db){

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    db.modeloTransacaoDao().deleteNoWhere();
                    db.transacaoDao().deleteNoWhere();
                    db.tipoTransacaoDao().deleteNoWhere();
                    db.categoriaTransacaoDao().deleteNoWhere();
                    db.contaDao().deleteNoWhere();
                } catch (Exception e){
                    Log.i("FINAL", "<AQUI>" + e.getMessage());
                }
                return null;
            }
        }.execute();
        try{
            Thread.sleep(1000);
        }
        catch(Exception e){
            Log.e("DEBUG",e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar suportActionBar = getSupportActionBar();
        suportActionBar.setTitle(R.string.FINANCIAL_CONTROL);
        //suportActionBar.hide();

        db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, AppDataBase.DB_NAME).build();

        //startFromZero(db);

        textViewTotalBalance = findViewById(R.id.textViewTotalBalance);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                double valor = db.contaDao().selectSaldoTotal();
                textViewTotalBalance.setText(String.valueOf(valor));
                return null;
            }
        }.execute();


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                tipoTransacaoList = db.tipoTransacaoDao().select();
                if (tipoTransacaoList.size() == 0){
                    db.tipoTransacaoDao().insert(new TipoTransacao("CREDITO"));
                    db.tipoTransacaoDao().insert(new TipoTransacao("DEBITO"));
                    tipoTransacaoList = db.tipoTransacaoDao().select();
                }
                return null;
            }
        }.execute();


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                categoriaTransacaoList = db.categoriaTransacaoDao().select();
                if (categoriaTransacaoList.size() == 0){
                    db.categoriaTransacaoDao().insert(new CategoriaTransacao("MERCADO"));
                    db.categoriaTransacaoDao().insert(new CategoriaTransacao("PASSEIO"));
                    db.categoriaTransacaoDao().insert(new CategoriaTransacao("COMBUSTIVEL"));
                    categoriaTransacaoList = db.categoriaTransacaoDao().select();
                }

                for (CategoriaTransacao tipo : categoriaTransacaoList) {
                    itensList.add(tipo.getDescricao());
                }

                spinnerAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, itensList);

                spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);

                return null;
            }
        }.execute();

        try{
            Thread.sleep(500);
        }
        catch(Exception e){
            Log.e("DEBUG",e.getMessage());
        }

        // ScrollView to show the CardsViews in horizontal line
        horizontalScrollView = findViewById(R.id.horizontalScrollView);
        // Layout manager to hold the card views
        linearLayoutScrowView = horizontalScrollView.findViewById(R.id.linearLayoutScrollView);

        createCardViewsAccount();
    }

    private void createCardViewsAccount(){

        linearLayoutScrowView.removeAllViews();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                contaList = db.contaDao().select();
                return null;
            }
        }.execute();

        try{
            Thread.sleep(500);
        }
        catch(Exception e){
            Log.e("DEBUG",e.getMessage());
        }

        if(contaList.size()>0){

            for(final Conta conta:contaList){

                final CardView cardView = (CardView) LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.show_account_info,
                        horizontalScrollView,
                        false);

                final TextView textViewAccountNumber = cardView.findViewById(R.id.textViewAccountNumber);

                final TextView textViewAccountBalance = cardView.findViewById(R.id.textViewAccountBalance);

                TextView textViewAccountName = cardView.findViewById(R.id.textViewAccountName);

                textViewAccountName.setText(conta.getDescricao());

                textViewAccountNumber.setText(String.valueOf(conta.getIdConta()));

                final Spinner spinnerCategories = cardView.findViewById(R.id.spinnerCategories);
                spinnerCategories.setAdapter(spinnerAdapter);

                // recover balance and three last transactions
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        // balance
                        double saldo = db.contaDao().selectSaldo(conta.getIdConta());

                        textViewAccountBalance.setText(String.valueOf(saldo));

                        // three last transactions
                        List<TransacaoModeloTransacaoUi> transacaoList;
                        transacaoList = db.transacaoDao().selectLastThree(conta.getIdConta());

                        showLastTransactions(transacaoList, cardView);

                        return null;
                    }
                }.execute();

                final EditText editTextAmount = cardView.findViewById(R.id.editTextAmount);
                final EditText editTextDescription = cardView.findViewById(R.id.editTextDescription);
                final EditText editTextMonth = cardView.findViewById(R.id.editTextMonth);
                final EditText editTextYear = cardView.findViewById(R.id.editTextYear);

                // configure cehckbox recurrency
                final CheckBox checkBoxRecurrency = cardView.findViewById(R.id.checkBoxrecorrency);
                checkBoxRecurrency.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            editTextMonth.setVisibility(View.VISIBLE);
                            editTextYear.setVisibility(View.VISIBLE);
                            cardView.findViewById(R.id.textViewBar).setVisibility(View.VISIBLE);
                        } else {
                            editTextMonth.setVisibility(View.GONE);
                            editTextYear.setVisibility(View.GONE);
                            cardView.findViewById(R.id.textViewBar).setVisibility(View.GONE);
                        }
                        cardView.requestFocus();
                    }
                });

                // configure button show more transactions
                Button buttonShowMoreTransactions = cardView.findViewById(R.id.buttonShowMoreTransactions);

                buttonShowMoreTransactions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            Intent intent = new Intent(getApplicationContext(), ShowTransactionsActivity.class);
                            intent.putExtra(ShowTransactionsActivity.KEY_ACCOUNT_NUMBER, textViewAccountNumber.getText().toString());
                            startActivity(intent);
                        }catch (Exception e){
                            Log.i("RECYCLER", "1" + e.getMessage());
                        }
                    }
                });

                // configure button launcher new transaction
                Button buttonNewTransaction = cardView.findViewById(R.id.buttonNewTransaction);

                buttonNewTransaction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Date todayDate = Calendar.getInstance().getTime();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String todayString = formatter.format(todayDate);
                        final String diaInicio = todayString.substring(8,10);
                        final String mesInicio = todayString.substring(5,7);
                        final String anoInicio = todayString.substring(0,4);
                        final String mesFim;
                        final String anoFim;
                        final String tipoTransacao;
                        final String amount = editTextAmount.getText().toString();
                        final String transactionDescription = editTextDescription.getText().toString();
                        final String categorie = spinnerCategories.getSelectedItem().toString();
                        final int idConta = Integer.parseInt(textViewAccountNumber.getText().toString());

                        if(amount.isEmpty() || transactionDescription.isEmpty()){
                            Toast.makeText(getApplicationContext(), R.string.msg_1, Toast.LENGTH_LONG).show();
                        }
                        else {
                            if (Integer.parseInt(amount == null ? "0" : amount) >= 0) {
                                tipoTransacao = "CREDITO";
                            } else {
                                tipoTransacao = "DEBITO";
                            }

                            if (checkBoxRecurrency.isChecked()) {
                                mesFim = editTextMonth.getText().toString();
                                anoFim = editTextYear.getText().toString();
                            } else {
                                mesFim = mesInicio;
                                anoFim = anoInicio;
                            }

                            if( (Integer.valueOf(mesFim) < 1 || Integer.valueOf(mesFim) > 12)
                                    || (Integer.valueOf(anoFim) < Integer.valueOf(anoInicio) ) ){
                                Toast.makeText(getApplicationContext(), R.string.msg_2, Toast.LENGTH_LONG).show();
                            }
                            else {
                                new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected Void doInBackground(Void... voids) {
                                        try {

                                            db.modeloTransacaoDao().insert(new ModeloTransacao(
                                                    db.tipoTransacaoDao().selectOneDesc(tipoTransacao).getIdTipoTransacao(),
                                                    db.categoriaTransacaoDao().selectOneDesc(categorie).getIdCategoriaTransacao(),
                                                    Double.parseDouble(amount),
                                                    transactionDescription,
                                                    Integer.parseInt(diaInicio),
                                                    Integer.parseInt(mesInicio),
                                                    Integer.parseInt(anoInicio),
                                                    Integer.parseInt(diaInicio),
                                                    Integer.parseInt(mesFim),
                                                    Integer.parseInt(anoFim)));

                                            db.transacaoDao().insert(new Transacao(
                                                    db.modeloTransacaoDao().selectLastID().getIdModeloTransacao(),
                                                    idConta));

                                            // three last transactions
                                            //List<TransacaoModeloTransacaoUi> transacaoList;
                                            transacaoList = db.transacaoDao().selectLastThree(idConta);

                                            //showLastTransactions(transacaoList, cardView);
                                        } catch (Exception e) {
                                            Log.e("ERRO_BANCO", e.getMessage());
                                        }
                                        return null;
                                    }
                                }.execute();

                                textViewTotalBalance.setText( String.valueOf(Double.parseDouble(textViewTotalBalance.getText().toString()) + Double.parseDouble(amount) ));
                                textViewAccountBalance.setText( String.valueOf(Double.parseDouble(textViewAccountBalance.getText().toString()) + Double.parseDouble(amount) ));

                                editTextAmount.setText("");
                                editTextDescription.setText("");

                                try {
                                    Thread.sleep(100);
                                } catch (Exception e) {
                                    Log.e("ERRO_SLEEP", e.getMessage());
                                }

                                showLastTransactions(transacaoList, cardView);
                            }
                        }
                    }
                });

                linearLayoutScrowView.addView(cardView);
            }
        }

        createCarViewNewAccount();
    }

    private void showLastTransactions(List<TransacaoModeloTransacaoUi> transacaoList, CardView cardView){
        TransacaoModeloTransacaoUi transacao;

        if(transacaoList.size() == 1) {
            transacao = transacaoList.remove(0);
            TextView textViewDescTransaction1 = cardView.findViewById(R.id.textViewDescTransaction1);
            TextView textViewValueTransaction1 = cardView.findViewById(R.id.textViewValueTransaction1);

            textViewDescTransaction1.setText(transacao.getDescricao());
            textViewValueTransaction1.setText(String.valueOf(transacao.getValor()));
            textViewDescTransaction1.setVisibility(View.VISIBLE);
            textViewValueTransaction1.setVisibility(View.VISIBLE);
        }
        else if(transacaoList.size() == 2){

            TextView textViewDescTransaction1 = cardView.findViewById(R.id.textViewDescTransaction1);
            TextView textViewValueTransaction1 = cardView.findViewById(R.id.textViewValueTransaction1);
            TextView textViewDescTransaction2 = cardView.findViewById(R.id.textViewDescTransaction2);
            TextView textViewValueTransaction2 = cardView.findViewById(R.id.textViewValueTransaction2);

            transacao = transacaoList.remove(0);
            textViewDescTransaction1.setText(transacao.getDescricao());
            textViewValueTransaction1.setText(String.valueOf(transacao.getValor()));
            textViewDescTransaction1.setVisibility(View.VISIBLE);
            textViewValueTransaction1.setVisibility(View.VISIBLE);

            transacao = transacaoList.remove(0);
            textViewDescTransaction2.setText(transacao.getDescricao());
            textViewValueTransaction2.setText(String.valueOf(transacao.getValor()));
            textViewDescTransaction2.setVisibility(View.VISIBLE);
            textViewValueTransaction2.setVisibility(View.VISIBLE);
        }
        else if(transacaoList.size() == 3){

            TextView textViewDescTransaction1 = cardView.findViewById(R.id.textViewDescTransaction1);
            TextView textViewValueTransaction1 = cardView.findViewById(R.id.textViewValueTransaction1);
            TextView textViewDescTransaction2 = cardView.findViewById(R.id.textViewDescTransaction2);
            TextView textViewValueTransaction2 = cardView.findViewById(R.id.textViewValueTransaction2);
            TextView textViewDescTransaction3 = cardView.findViewById(R.id.textViewDescTransaction3);
            TextView textViewValueTransaction3 = cardView.findViewById(R.id.textViewValueTransaction3);

            transacao = transacaoList.remove(0);
            textViewDescTransaction1.setText(transacao.getDescricao());
            textViewValueTransaction1.setText(String.valueOf(transacao.getValor()));
            textViewDescTransaction1.setVisibility(View.VISIBLE);
            textViewValueTransaction1.setVisibility(View.VISIBLE);

            transacao = transacaoList.remove(0);
            textViewDescTransaction2.setText(transacao.getDescricao());
            textViewValueTransaction2.setText(String.valueOf(transacao.getValor()));
            textViewDescTransaction2.setVisibility(View.VISIBLE);
            textViewValueTransaction2.setVisibility(View.VISIBLE);

            transacao = transacaoList.remove(0);
            textViewDescTransaction3.setText(transacao.getDescricao());
            textViewValueTransaction3.setText(String.valueOf(transacao.getValor()));
            textViewDescTransaction3.setVisibility(View.VISIBLE);
            textViewValueTransaction3.setVisibility(View.VISIBLE);
        }

    }

    private void createCarViewNewAccount(){
        final CardView cardViewInsetNewAccount = (CardView) LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.cardview_insert_new_account,horizontalScrollView,false);
        linearLayoutScrowView.addView(cardViewInsetNewAccount);

        final CheckBox checkBoxRecurrency = cardViewInsetNewAccount.findViewById(R.id.checkBoxrecorrency);
        final EditText editTextMonth = cardViewInsetNewAccount.findViewById(R.id.editTextMonth);
        final EditText editTextYear = cardViewInsetNewAccount.findViewById(R.id.editTextYear);
        final EditText editTextAccountName = cardViewInsetNewAccount.findViewById(R.id.editTextAccountName);
        final EditText editTextAmount = cardViewInsetNewAccount.findViewById(R.id.editTextAmount);
        final EditText editTextTransactionDescription = cardViewInsetNewAccount.findViewById(R.id.editTextTransactionDescription);
        final Spinner spinnerCategories = cardViewInsetNewAccount.findViewById(R.id.spinnerCategories);

        try {
            spinnerCategories.setAdapter(spinnerAdapter);

        }catch (Exception e){
            Log.i("DEBUG", "ERRO 2 "+e.getMessage());
        }
        // checkbox recurrent event
        checkBoxRecurrency.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    editTextMonth.setVisibility(View.VISIBLE);
                    editTextYear.setVisibility(View.VISIBLE);
                    cardViewInsetNewAccount.findViewById(R.id.textViewBar).setVisibility(View.VISIBLE);
                } else {
                    editTextMonth.setVisibility(View.GONE);
                    editTextYear.setVisibility(View.GONE);
                    cardViewInsetNewAccount.findViewById(R.id.textViewBar).setVisibility(View.GONE);
                }
                cardViewInsetNewAccount.requestFocus();
            }
        });

        Button buttonCreateAccount = cardViewInsetNewAccount.findViewById(R.id.buttonCreateAccount);

        // button create account event
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date todayDate = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String todayString = formatter.format(todayDate);
                final String diaInicio = todayString.substring(8, 10);
                final String mesInicio = todayString.substring(5, 7);
                final String anoInicio = todayString.substring(0, 4);
                final String mesFim;
                final String anoFim;
                final String tipoTransacao;
                final String accountName = editTextAccountName.getText().toString();
                final String amount = editTextAmount.getText().toString();
                final String transacionDescription = editTextTransactionDescription.getText().toString();
                final String categorie = spinnerCategories.getSelectedItem().toString();

                if (accountName.isEmpty() || amount.isEmpty() || transacionDescription.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.msg_3, Toast.LENGTH_LONG).show();
                }
                else {
                    if (Integer.parseInt(amount == null ? "0" : amount) >= 0) {
                        tipoTransacao = "CREDITO";
                    } else {
                        tipoTransacao = "DEBITO";
                    }

                    if (checkBoxRecurrency.isChecked()) {
                        mesFim = editTextMonth.getText().toString();
                        anoFim = editTextYear.getText().toString();
                    } else {
                        mesFim = mesInicio;
                        anoFim = anoInicio;
                    }

                    if( (Integer.valueOf(mesFim) < 1 || Integer.valueOf(mesFim) > 12)
                            || (Integer.valueOf(anoFim) < Integer.valueOf(anoInicio) ) ){
                        Toast.makeText(getApplicationContext(), R.string.msg_2, Toast.LENGTH_LONG).show();
                    }
                    else {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                try {
                                    db.contaDao().insert(new Conta(accountName));

                                    db.modeloTransacaoDao().insert(new ModeloTransacao(
                                            db.tipoTransacaoDao().selectOneDesc(tipoTransacao).getIdTipoTransacao(),
                                            db.categoriaTransacaoDao().selectOneDesc(categorie).getIdCategoriaTransacao(),
                                            Double.parseDouble(amount),
                                            transacionDescription,
                                            Integer.parseInt(diaInicio),
                                            Integer.parseInt(mesInicio),
                                            Integer.parseInt(anoInicio),
                                            Integer.parseInt(diaInicio),
                                            Integer.parseInt(mesFim),
                                            Integer.parseInt(anoFim)));

                                    db.transacaoDao().insert(new Transacao(
                                            db.modeloTransacaoDao().selectLastID().getIdModeloTransacao(),
                                            db.contaDao().selectLastId().getIdConta()));
                                } catch (Exception e) {
                                    Log.e("ERRO_BANCO", e.getMessage());
                                }
                                return null;
                            }
                        }.execute();

                        textViewTotalBalance.setText( String.valueOf(Double.parseDouble(textViewTotalBalance.getText().toString()) + Double.parseDouble(amount) ));

                        try{
                            Thread.sleep(100);
                        }
                        catch(Exception e){
                            Log.e("ERRO_SLEEP",e.getMessage());
                        }

                        createCardViewsAccount();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        try{
            getMenuInflater().inflate(R.menu.menu_action_bar_main, menu);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == R.id.itemNewCategory){
            Intent intent = new Intent(this,AddNewCategoryActivity.class);
            startActivityForResult(intent, ADD_NEW_CATEGORY);
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCOde, Intent data){
        if(requestCode == ADD_NEW_CATEGORY){
            for(String item:newItensList){
                itensList.add(item);
                spinnerAdapter.notifyDataSetChanged();
            }
        }
    }
}
