package com.br.wetter.wetter.util;

public class UtilMensageResultFirebase {

    public static String getMsg(String result) {
        if (result.contains("no user record corresponding")) {
            return "Usuário não cadastrado";
        } else if (result.contains("is no user record corresponding")) {
            return "Usuário não cadastrado!";
        } else if (result.contains("password is invalid")) {
            return "Senha inválida ou Usuário não cadastrado!";
        }
        return "";
    }


}
