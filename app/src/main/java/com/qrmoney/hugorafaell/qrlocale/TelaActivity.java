package com.qrmoney.hugorafaell.qrlocale;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TelaActivity extends AppCompatActivity {

    private static ImageView btn_ver, btn_listar;
    int id;
    String debito, status;
    String cidade;
    String latitude;
    String longitude;
    ArrayList<String> locais = new ArrayList<>();
    ArrayList<String> latitude2 = new ArrayList<>();
    ArrayList<String> longitude2 = new ArrayList<>();
    ArrayList<String> dataCompras = new ArrayList<>();
    ArrayList<String> valorCompras = new ArrayList<>();
    ArrayList<String> valorCreditado = new ArrayList<>();
    ArrayList<String> statusConta = new ArrayList<>();
    String id_cliente;
    String foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela);
        final Intent intent = getIntent();
        id_cliente = intent.getStringExtra("id_cliente");
        foto = intent.getStringExtra("foto_perfil");

        btn_ver = (ImageView) findViewById(R.id.botaoVer);
        btn_ver.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new FetchSQLgetpassword().execute();
                    }
                }
        );

        btn_listar = (ImageView) findViewById(R.id.botaoListar);
        btn_listar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new FetchSQLgetpassword2().execute();
                    }
                }
        );
    }

    private class FetchSQLgetpassword extends AsyncTask<Object, Object, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Object... params) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                cidade = e.toString();
            }
            Connection conn;
            try {
                DriverManager.setLoginTimeout(5);
                conn = DriverManager.getConnection("jdbc:postgresql://192.168.0.107/carteiraBD","postgres","root");
                Statement st = conn.createStatement();
                String sql;
                sql = "SELECT * FROM \"vendedor\"";
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    locais.add(rs.getString("endereco"));
                    latitude2.add(rs.getString("latitude"));
                    longitude2.add(rs.getString("longitude"));
                }
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                cidade = e.toString();
            }
            return locais;
        }

        @Override
        protected void onPostExecute(ArrayList<String> value) {
            if (value != null) {
                Intent intent = new Intent(TelaActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("locais", locais);
                bundle.putStringArrayList("latitudes", latitude2);
                bundle.putStringArrayList("longitudes", longitude2);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Log.e("TELA", "Não entrou no banco");
            }
        }
    }

    private class FetchSQLgetpassword2 extends AsyncTask<Object, Object, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Object... params) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                cidade = e.toString();
            }
            Connection conn;
            try {
                DriverManager.setLoginTimeout(5);
                conn = DriverManager.getConnection("jdbc:postgresql://192.168.0.107/carteiraBD","postgres","root");
                Statement st = conn.createStatement();
                String sql;
                sql = "SELECT * FROM \"conta\" WHERE cliente_id = '" + Long.parseLong(id_cliente) + "';";
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    dataCompras.add(rs.getString("data"));
                    valorCompras.add(rs.getString("debito"));
                    valorCreditado.add(rs.getString("creditado"));
                    statusConta.add(rs.getString("status"));
                    status = rs.getString("status");
                }
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                cidade = e.toString();
            }
            return dataCompras;
        }

        @Override
        protected void onPostExecute(ArrayList<String> value) {
            if (value != null) {
                if (status.equals("COMPRA")) {
                    Intent intent = new Intent(TelaActivity.this, ListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("dataCompras", dataCompras);
                    bundle.putStringArrayList("valorCompras", valorCompras);
                    bundle.putStringArrayList("valorCreditado", valorCreditado);
                    bundle.putStringArrayList("statusCompras", statusConta);
                    bundle.putString("foto_perfil", foto);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    Log.e("TELA", "Não entrou no banco");
                }
            } else {
                Log.e("TELA", "Não entrou no banco");
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public String setCidade(String cidade) {
        this.cidade = cidade;
        return cidade;
    }

    public String getLatitude() {
        return latitude;
    }

    public String setLatitude(String latitude) {
        this.latitude = latitude;
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String setLongitude(String longitude) {
        this.longitude = longitude;
        return longitude;
    }
}
