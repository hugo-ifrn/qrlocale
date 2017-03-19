package com.qrmoney.hugorafaell.qrlocale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hugorafaell on 19/03/17.
 */

public class ListaAdapter extends ArrayAdapter<Lista> {
    private List<Lista> items;

    public ListaAdapter(Context context, int textViewResourceId, List<Lista> items) {
        super(context, textViewResourceId, items);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            Context ctx = getContext();
            LayoutInflater vi = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.item_lista, null);
        }
        Lista lista = items.get(position);
        if (lista != null) {
            ((TextView) v.findViewById(R.id.lblData)).setText(lista.getData());
            ((TextView) v.findViewById(R.id.lblDebitado)).setText("Valor debitado: " + lista.getDebito());
            ((TextView) v.findViewById(R.id.lblStatus)).setText(lista.getStatus());
            ((TextView) v.findViewById(R.id.lblCreditado)).setText("Valor creditado: " + lista.getCredito());
            ((ImageView) v.findViewById(R.id.imgCidade)).setImageResource((int) lista.getIdImagem());
        }
        return v;
    }
}
