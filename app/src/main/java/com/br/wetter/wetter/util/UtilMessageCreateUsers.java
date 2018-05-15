package com.br.wetter.wetter.util;

import java.util.HashMap;

public class UtilMessageCreateUsers {
    private HashMap<Integer, String> msg;

    public UtilMessageCreateUsers() {
        this.msg = new HashMap<>();
    }

    public String getMessage(int id){
        String message= "";

        msg.put(0, "Infome o nome!");
        msg.put(1, "E-mail invalido!");
        msg.put(2, "Senha inválida!");
        msg.put(3, "Senhas não conferem!");

        return msg.get(id);
    }
}
