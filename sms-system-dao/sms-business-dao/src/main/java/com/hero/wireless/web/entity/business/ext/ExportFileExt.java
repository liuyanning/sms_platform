package com.hero.wireless.web.entity.business.ext;

import com.hero.wireless.web.entity.business.ExportFile;

public class ExportFileExt extends ExportFile {

    private boolean isBlur;

    public boolean isBlur() {
        return isBlur;
    }

    public void setBlur(boolean blur) {
        isBlur = blur;
    }

}