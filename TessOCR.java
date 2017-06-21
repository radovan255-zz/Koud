package com.example.ubuntu.whyyes;

import android.content.Context;
import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;

/**
 * Created by zakovacr on 10.4.2017.
 */

public class TessOCR {

    private final TessBaseAPI mTess;

    public TessOCR(Context context, String language) {
        mTess = new TessBaseAPI();
        String datapath = context.getFilesDir() + "/tesseract/";
        mTess.init(datapath, language);
    }

    public String getOCRResult(Bitmap bitmap) {
        mTess.setImage(bitmap);
        return mTess.getUTF8Text();
    }

    public void onDestroy() {
        if (mTess != null) mTess.end();
    }

}
