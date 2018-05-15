package com.br.wetter.wetter.TO;

import java.io.Serializable;

public class GruposTO implements Serializable{

    private String bandeira1;
    private String bandeira2;
    private String stadio;
    private String horario;
    private String placar;
    private boolean pode_apostar;
    private String time1;
    private String time2;
    private String vencedor;

    public GruposTO() {
    }

    public String getBandeira1() {
        return bandeira1;
    }

    public void setBandeira1(String bandeira1) {
        this.bandeira1 = bandeira1;
    }

    public String getBandeira2() {
        return bandeira2;
    }

    public void setBandeira2(String bandeira2) {
        this.bandeira2 = bandeira2;
    }

    public String getStadio() {
        return stadio;
    }

    public void setStadio(String stadio) {
        this.stadio = stadio;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getPlacar() {
        return placar;
    }

    public void setPlacar(String placar) {
        this.placar = placar;
    }

    public boolean isPode_apostar() {
        return pode_apostar;
    }

    public void setPode_apostar(boolean pode_apostar) {
        this.pode_apostar = pode_apostar;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getVencedor() {
        return vencedor;
    }

    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }
}
