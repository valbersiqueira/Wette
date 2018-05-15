package com.br.wetter.wetter.bo;

import android.widget.TextView;

import com.br.wetter.wetter.util.UtilMessageCreateUsers;

import java.util.List;

public class CreateUsersBO {

    public boolean isValid(List<TextView> texts) {
        TextView nomeTxt, emailTxt, senhaTxt, conf_senhaTxt;
        nomeTxt = texts.get(0);
        emailTxt = texts.get(1);
        senhaTxt = texts.get(2);
        conf_senhaTxt = texts.get(3);

        String email = emailTxt.getText().toString();
        String senha = senhaTxt.getText().toString();
        String conf_senha = conf_senhaTxt.getText().toString();

        if (nomeTxt.getText().toString().equalsIgnoreCase("")) {
            nomeTxt.setError(new UtilMessageCreateUsers().getMessage(0));
            return false;
        } else if (email.equalsIgnoreCase("") || !email.contains("@") ||
                !email.contains(".com")) {
            emailTxt.setError(new UtilMessageCreateUsers().getMessage(1));
            return false;
        } else if (senha.equalsIgnoreCase("") && senha.length() < 6 ){
            senhaTxt.setError(new UtilMessageCreateUsers().getMessage(2));
            return false;
        } else if(  conf_senha.equalsIgnoreCase("") ||
                !senha.equals(conf_senha)) {
            conf_senhaTxt.setError(new UtilMessageCreateUsers().getMessage(3));
            conf_senhaTxt.setText("");
            return false;
        }
        return true;
    }
}
