package com.br.wetter.wetter.util;

import android.content.Context;
import android.graphics.Typeface;

public class UtilIcon {

    public static Typeface getTypeface(Context mContext){
        return Typeface.createFromAsset(mContext.getAssets(), "fonts/fontawesome-webfont.ttf");
    }

}
