package com.bin.bustest.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ShiBusBean {
    /**
     * return_code : ok
     * error_code : 0
     * returl_list : [{"bus_endstan":"东山总站（龟岗）","bus_linestrid":"MDIwLTAwMDMwLTE=","bus_linenum":"00030","bus_staname":"3"}]
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

    public static class ReturlListBean implements Parcelable {
        /**
         * bus_endstan : 东山总站（龟岗）
         * bus_linestrid : MDIwLTAwMDMwLTE=
         * bus_linenum : 00030
         * bus_staname : 3
         */

        private String bus_endstan;
        private String bus_linestrid;
        private String bus_linenum;
        private String bus_staname;

        protected ReturlListBean(Parcel in) {
            bus_endstan = in.readString();
            bus_linestrid = in.readString();
            bus_linenum = in.readString();
            bus_staname = in.readString();
        }

        public static final Creator<ReturlListBean> CREATOR = new Creator<ReturlListBean>() {
            @Override
            public ReturlListBean createFromParcel(Parcel in) {
                return new ReturlListBean(in);
            }

            @Override
            public ReturlListBean[] newArray(int size) {
                return new ReturlListBean[size];
            }
        };

        public String getBus_endstan() {
            return bus_endstan;
        }

        public void setBus_endstan(String bus_endstan) {
            this.bus_endstan = bus_endstan;
        }

        public String getBus_linestrid() {
            return bus_linestrid;
        }

        public void setBus_linestrid(String bus_linestrid) {
            this.bus_linestrid = bus_linestrid;
        }

        public String getBus_linenum() {
            return bus_linenum;
        }

        public void setBus_linenum(String bus_linenum) {
            this.bus_linenum = bus_linenum;
        }

        public String getBus_staname() {
            return bus_staname;
        }

        public void setBus_staname(String bus_staname) {
            this.bus_staname = bus_staname;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(bus_endstan);
            dest.writeString(bus_linestrid);
            dest.writeString(bus_linenum);
            dest.writeString(bus_staname);
        }
    }
}
