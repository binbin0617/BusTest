package com.bin.bustest.bean;

import java.util.List;

public class CityBean {
    /**
     * return_code : ok
     * error_code : 0
     * returl_list : [{"cityid":"132","city":"阿勒泰地区阿勒泰市"},{"cityid":"76","city":"安阳市"}]
     */

    private String return_code;
    private String error_code;
    private List<ReturlListBean> returl_list;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public List<ReturlListBean> getReturl_list() {
        return returl_list;
    }

    public void setReturl_list(List<ReturlListBean> returl_list) {
        this.returl_list = returl_list;
    }

    public static class ReturlListBean {
        /**
         * cityid : 132
         * city : 阿勒泰地区阿勒泰市
         */

        private String cityid;
        private String city;

        public String getCityid() {
            return cityid;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
