package com.hero.wireless.web.entity.business.ext;


import com.hero.wireless.web.entity.business.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductExt extends Product {

    /**
     *
     */
    private static final long serialVersionUID = -8839620891417788465L;

    private String signature;

    private String channel_No;

    private List<String> productNos = new ArrayList<String>();

    public String getChannel_No() {
        return channel_No;
    }

    public void setChannel_No(String channel_No) {
        this.channel_No = channel_No;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<String> getProductNos() {
        return productNos;
    }

    public void setProductNos(List<String> productNos) {
        this.productNos = productNos;
    }

}