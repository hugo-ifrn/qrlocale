package com.qrmoney.hugorafaell.qrlocale;

/**
 * Created by hugorafaell on 19/03/17.
 */

public class Lista {

    private String data;
    private String debito;
    private String status;
    private String credito;
    private long resIdImagem;

    public Lista(String data, String debito, String credito, String status, long resIdImagem){
        this.data = data;
        this.debito = debito;
        this.status = status;
        this.credito = credito;
        this.resIdImagem = resIdImagem;
    }

    public String getData(){ return this.data; }
    public String getDebito(){ return this.debito; }
    public String getCredito(){return this.credito; }
    public String getStatus(){return this.status;}
    public long getIdImagem(){ return this.resIdImagem; }

}
