package com.qrmoney.hugorafaell.qrlocale;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Tela_login extends AppCompatActivity {

    private static EditText username;
    private static EditText password;
    private static Button login_btn;
    private static String mastername;
    private static String masterpassword;
    private TextInputLayout inputLayoutUser;
    private TextInputLayout inputLayoutPassword;
    String id_cliente = "";
    String foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
        LoginButton();
    }

    public void LoginButton(){

        inputLayoutUser = (TextInputLayout) findViewById(R.id.inputLayoutUser);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.inputLayoutPassword);
        username = (EditText)findViewById(R.id.editNome1);
        password = (EditText)findViewById(R.id.editSenha1);
        login_btn = (Button)findViewById(R.id.login_btn);

        login_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mastername = username.getText().toString();
                        masterpassword = password.getText().toString();
                        if (mastername.isEmpty()){
                            username.setError("Digite um usuário");
                        } else if (masterpassword.isEmpty()){
                            password.setError("Digite uma senha");
                        } else {
                            new FetchSQLgetpassword().execute(mastername, masterpassword);
                        }
                    }
                }
        );
    }

    private class FetchSQLgetpassword extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String retval = "";
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                retval = e.toString();
            }
            Connection conn;
            try {
                DriverManager.setLoginTimeout(5);
                conn = DriverManager.getConnection("jdbc:postgresql://192.168.0.107/carteiraBD","postgres","root");
                Statement st = conn.createStatement();
                String sql;
                sql = "SELECT * FROM \"cliente\" WHERE usuario = '" + mastername +"';";
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()) {
                    retval = rs.getString("senha");
                    id_cliente = rs.getString("id");
                    foto = rs.getString("foto");
                }
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                retval = e.toString();
            }
            return retval;
        }

        @Override
        protected void onPostExecute(String value) {
            if (masterpassword.equals(value) && value!="") {
                Intent intent = new Intent(Tela_login.this,TelaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_cliente", id_cliente);
                bundle.putString("foto_perfil", foto);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Toast.makeText(Tela_login.this, "Usuário ou senha inválidos", Toast.LENGTH_LONG).show();
            }
        }
    }
}
