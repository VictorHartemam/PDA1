package com.example.vhartemam.trabalhofinalpda1;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vhartemam.trabalhofinalpda1.ui.TransacaoModeloTransacaoUi;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionHolder> {
    private List<TransacaoModeloTransacaoUi> dataSet;

    public static class TransactionHolder extends RecyclerView.ViewHolder{
        public TextView textViewDesc;
        public TextView textViewValue;

        public TransactionHolder(View view){
            super(view);

            textViewDesc = view.findViewById(R.id.textViewDesc);
            textViewValue = view.findViewById(R.id.textViewValue);
        }
    }

    public TransactionAdapter(List<TransacaoModeloTransacaoUi> dataSet){
        this.dataSet = dataSet;
    }

    @Override
    public TransactionHolder onCreateViewHolder(ViewGroup parent, int i){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_transaction, parent, false);
        return new TransactionHolder(view);
    }

    @Override
    public void onBindViewHolder(final TransactionHolder holder, int i){
        TransacaoModeloTransacaoUi transacao = this.dataSet.get(i);
        try {
            holder.textViewDesc.setText(transacao.getDescricao());
            holder.textViewValue.setText(String.valueOf(transacao.getValor()));

        }catch (Exception e){
            Log.i("RECYCLER","so pode ser aqui");
        }
    }

    @Override
    public int getItemCount(){
        try {
            return dataSet.size();
        }catch (Exception e){
            return 0;
        }
    }
}
