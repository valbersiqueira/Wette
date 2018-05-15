package com.br.wetter.wetter.TO;

public class UsersTO {
    private String email;
    private long fechas;
    private String foto_perfil;
    private long meusPontos;
    private String nome;

    public UsersTO() {
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getFechas() {
        return fechas;
    }

    public void setFechas(long fechas) {
        this.fechas = fechas;
    }

    public String getFoto_perfil() {
        return foto_perfil;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public long getMeusPontos() {
        return meusPontos;
    }

    public void setMeusPontos(long meusPontos) {
        this.meusPontos = meusPontos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
