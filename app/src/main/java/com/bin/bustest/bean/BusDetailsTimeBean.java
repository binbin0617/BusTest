package com.bin.bustest.bean;

import java.util.List;

public class BusDetailsTimeBean {

    /**
     * return_code : ok
     * error_code : 0
     * returl_list : {"lineinfo":{"bus_staname":"849","sta_sta":"世纪天乐","end_sta":"师范大学公交站","fir_time":"06:15","end_time":"22:00","bus_money":"2元","other_lineid":"MDIyLTg0OS0w"},"stations":[{"bus_staname":"世纪天乐"},{"bus_staname":"金钢桥"},{"bus_staname":"金纬路"},{"bus_staname":"狮子林大街"},{"bus_staname":"新都市百货"},{"bus_staname":"望海楼"},{"bus_staname":"东北角"},{"bus_staname":"鼓楼东"},{"bus_staname":"南门"},{"bus_staname":"西南角"},{"bus_staname":"天津商场"},{"bus_staname":"南开五马路"},{"bus_staname":"南开花园"},{"bus_staname":"广开四马路"},{"bus_staname":"西市大街"},{"bus_staname":"二十五中学"},{"bus_staname":"南开文化宫"},{"bus_staname":"世通大厦"},{"bus_staname":"鞍山西道"},{"bus_staname":"照湖西里"},{"bus_staname":"科贸大厦"},{"bus_staname":"荣迁东里"},{"bus_staname":"一中心医院"},{"bus_staname":"王顶堤立交桥"},{"bus_staname":"迎水西里"},{"bus_staname":"迎水北里"},{"bus_staname":"久华里"},{"bus_staname":"榕苑路"},{"bus_staname":"鑫茂科技园"},{"bus_staname":"地华里"},{"bus_staname":"中孚路"},{"bus_staname":"华苑雅士道"},{"bus_staname":"雅士道长华里"},{"bus_staname":"莹华里"},{"bus_staname":"居华里"},{"bus_staname":"理工大学北门"},{"bus_staname":"师范大学北门"},{"bus_staname":"天津教育招生考试院"},{"bus_staname":"工业大学新校区东门"},{"bus_staname":"师范大学西门"},{"bus_staname":"师范大学公交站"}],"buses":[{"distance":680,"dis_stat":2},{"distance":806,"dis_stat":7},{"distance":449,"dis_stat":16},{"distance":0,"dis_stat":20},{"distance":0,"dis_stat":32},{"distance":820,"dis_stat":39}]}
     */

    private String return_code;
    private String error_code;
    private ReturlListBean returl_list;

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

    public ReturlListBean getReturl_list() {
        return returl_list;
    }

    public void setReturl_list(ReturlListBean returl_list) {
        this.returl_list = returl_list;
    }

    public static class ReturlListBean {
        /**
         * lineinfo : {"bus_staname":"849","sta_sta":"世纪天乐","end_sta":"师范大学公交站","fir_time":"06:15","end_time":"22:00","bus_money":"2元","other_lineid":"MDIyLTg0OS0w"}
         * stations : [{"bus_staname":"世纪天乐"},{"bus_staname":"金钢桥"},{"bus_staname":"金纬路"},{"bus_staname":"狮子林大街"},{"bus_staname":"新都市百货"},{"bus_staname":"望海楼"},{"bus_staname":"东北角"},{"bus_staname":"鼓楼东"},{"bus_staname":"南门"},{"bus_staname":"西南角"},{"bus_staname":"天津商场"},{"bus_staname":"南开五马路"},{"bus_staname":"南开花园"},{"bus_staname":"广开四马路"},{"bus_staname":"西市大街"},{"bus_staname":"二十五中学"},{"bus_staname":"南开文化宫"},{"bus_staname":"世通大厦"},{"bus_staname":"鞍山西道"},{"bus_staname":"照湖西里"},{"bus_staname":"科贸大厦"},{"bus_staname":"荣迁东里"},{"bus_staname":"一中心医院"},{"bus_staname":"王顶堤立交桥"},{"bus_staname":"迎水西里"},{"bus_staname":"迎水北里"},{"bus_staname":"久华里"},{"bus_staname":"榕苑路"},{"bus_staname":"鑫茂科技园"},{"bus_staname":"地华里"},{"bus_staname":"中孚路"},{"bus_staname":"华苑雅士道"},{"bus_staname":"雅士道长华里"},{"bus_staname":"莹华里"},{"bus_staname":"居华里"},{"bus_staname":"理工大学北门"},{"bus_staname":"师范大学北门"},{"bus_staname":"天津教育招生考试院"},{"bus_staname":"工业大学新校区东门"},{"bus_staname":"师范大学西门"},{"bus_staname":"师范大学公交站"}]
         * buses : [{"distance":680,"dis_stat":2},{"distance":806,"dis_stat":7},{"distance":449,"dis_stat":16},{"distance":0,"dis_stat":20},{"distance":0,"dis_stat":32},{"distance":820,"dis_stat":39}]
         */

        private LineinfoBean lineinfo;
        private List<StationsBean> stations;
        private List<BusesBean> buses;

        public LineinfoBean getLineinfo() {
            return lineinfo;
        }

        public void setLineinfo(LineinfoBean lineinfo) {
            this.lineinfo = lineinfo;
        }

        public List<StationsBean> getStations() {
            return stations;
        }

        public void setStations(List<StationsBean> stations) {
            this.stations = stations;
        }

        public List<BusesBean> getBuses() {
            return buses;
        }

        public void setBuses(List<BusesBean> buses) {
            this.buses = buses;
        }

        public static class LineinfoBean {
            /**
             * bus_staname : 849
             * sta_sta : 世纪天乐
             * end_sta : 师范大学公交站
             * fir_time : 06:15
             * end_time : 22:00
             * bus_money : 2元
             * other_lineid : MDIyLTg0OS0w
             */

            private String bus_staname;
            private String sta_sta;
            private String end_sta;
            private String fir_time;
            private String end_time;
            private String bus_money;
            private String other_lineid;

            public String getBus_staname() {
                return bus_staname;
            }

            public void setBus_staname(String bus_staname) {
                this.bus_staname = bus_staname;
            }

            public String getSta_sta() {
                return sta_sta;
            }

            public void setSta_sta(String sta_sta) {
                this.sta_sta = sta_sta;
            }

            public String getEnd_sta() {
                return end_sta;
            }

            public void setEnd_sta(String end_sta) {
                this.end_sta = end_sta;
            }

            public String getFir_time() {
                return fir_time;
            }

            public void setFir_time(String fir_time) {
                this.fir_time = fir_time;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getBus_money() {
                return bus_money;
            }

            public void setBus_money(String bus_money) {
                this.bus_money = bus_money;
            }

            public String getOther_lineid() {
                return other_lineid;
            }

            public void setOther_lineid(String other_lineid) {
                this.other_lineid = other_lineid;
            }
        }

        public static class StationsBean {
            /**
             * bus_staname : 世纪天乐
             */

            private String bus_staname;
            private int juli;
            private boolean dao;

            public int getJuli() {
                return juli;
            }

            public void setJuli(int juli) {
                this.juli = juli;
            }

            public boolean isDao() {
                return dao;
            }

            public void setDao(boolean dao) {
                this.dao = dao;
            }

            public String getBus_staname() {
                return bus_staname;
            }

            public void setBus_staname(String bus_staname) {
                this.bus_staname = bus_staname;
            }
        }

        public static class BusesBean {
            /**
             * distance : 680
             * dis_stat : 2
             */

            private int distance;
            private int dis_stat;

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }

            public int getDis_stat() {
                return dis_stat;
            }

            public void setDis_stat(int dis_stat) {
                this.dis_stat = dis_stat;
            }
        }
    }
}
