package com.qrmoney.hugorafaell.qrlocale;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugorafaell on 19/03/17.
 */

public class ListActivity extends AppCompatActivity {

    List<Lista> listas = null;
    ListView lstResultados;
    EditText txtPesquisa;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compras);
        ArrayList<String> dataCompras = getIntent().getStringArrayListExtra("dataCompras");
        ArrayList<String> valorCompras = getIntent().getStringArrayListExtra("valorCompras");
        ArrayList<String> valorCreditados = getIntent().getStringArrayListExtra("valorCreditado");
        ArrayList<String> statusCompras = getIntent().getStringArrayListExtra("statusCompras");
        String foto_perfil = getIntent().getStringExtra("foto_perfil");
        Log.e("Lista", String.valueOf(dataCompras));
        Log.e("Valor", String.valueOf(valorCompras));
        lstResultados = (ListView)findViewById(R.id.lstResultados);
        listas = new ArrayList<>();
        for (int i = 0; i < dataCompras.size(); i++) {
            if (valorCompras.get(i) != null){
                listas.add(new Lista(dataCompras.get(i), valorCompras.get(i), valorCreditados.get(i), statusCompras.get(i), R.drawable.carrinho));
            }else{
                listas.add(new Lista(dataCompras.get(i), valorCompras.get(i), valorCreditados.get(i), statusCompras.get(i), R.drawable.credito));
            }
        }
        txtPesquisa = (EditText)findViewById(R.id.txtPesquisa);
        btnBuscar = (Button)findViewById(R.id.btnBuscar);
        lstResultados.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lista lista = (Lista)lstResultados.getItemAtPosition(position);
                Toast.makeText(getBaseContext(), "Item " + lista.getData(), Toast.LENGTH_LONG).show();
            }
        });
        txtPesquisa.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        ListaAdapter adapter =
                new ListaAdapter(getBaseContext(), R.layout.item_lista, listas);
        lstResultados.setAdapter(adapter);
        //ListView listaDeCompras = (ListView) findViewById(R.id.lista);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataCompras);
        //listaDeCompras.setAdapter(adapter);
        //ListView valorDeCompras = (ListView) findViewById(R.id.valor);
        //ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, valorCompras);
        //valorDeCompras.setAdapter(adapter1);
    }

    public void btnBuscar_OnClick(View v){
        String busca = txtPesquisa.getText().toString();
        List<Lista> encontradas = new ArrayList<>();
        for(Lista lista : listas){
            if(lista.getStatus().contains(busca)) encontradas.add(lista);
        }
        ListaAdapter adapter =
                new ListaAdapter(getBaseContext(), R.layout.item_lista, encontradas);
        lstResultados.setAdapter(adapter);
    }
}
