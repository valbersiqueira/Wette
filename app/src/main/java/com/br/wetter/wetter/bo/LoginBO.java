package com.br.wetter.wetter.bo;

import android.widget.TextView;

import com.br.wetter.wetter.util.UtilMessageCreateUsers;

import java.util.List;

public class LoginBO {

    public boolean isValid(List<TextView> texts) {
        TextView emailTxt, senhaTxt;
        emailTxt = texts.get(0);
        senhaTxt = texts.get(1);
        String email = emailTxt.getText().toString();
        String senha = senhaTxt.getText().toString();


        if (email.equalsIgnoreCase("") || !email.contains("@") ||
                !email.contains(".com")) {
            emailTxt.setError(new UtilMessageCreateUsers().getMessage(1));
            emailTxt.setText("");
            return false;
        } else if(senha.equalsIgnoreCase("") || senha.length() < 6){
            senhaTxt.setError(new UtilMessageCreateUsers().getMessage(2));
            senhaTxt.setText("");
            return  false;
        }

        return true;
    }
}
