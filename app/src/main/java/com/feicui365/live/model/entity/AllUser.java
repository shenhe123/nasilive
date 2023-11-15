package com.feicui365.live.model.entity;

import java.io.Serializable;
import java.util.List;

public class AllUser implements Serializable {


    /**
     * list : [{"Avatar":"https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83ep3Z9ECeBK3S853biaTTIZoPyRDrEqrnwqQ8EsOqrqupr8OpuCgI9J2jiblFaABOgwjYic6R3IjRfBqA/132","JoinTime":1666099680,"Member_Account":"63621701","NickName":"土豆","Status":"Online"}]
     * count : 1
     */

    private Integer count;
    private List<ListBean> list;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * Avatar : https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83ep3Z9ECeBK3S853biaTTIZoPyRDrEqrnwqQ8EsOqrqupr8OpuCgI9J2jiblFaABOgwjYic6R3IjRfBqA/132
         * JoinTime : 1666099680
         * Member_Account : 63621701
         * NickName : 土豆
         * Status : Online
         */

        private String Avatar;
        private Integer JoinTime;
        private String Member_Account;
        private String NickName;
        private String Status;

        public String getAvatar() {
            return Avatar;
        }

        public void setAvatar(String Avatar) {
            this.Avatar = Avatar;
        }

        public Integer getJoinTime() {
            return JoinTime;
        }

        public void setJoinTime(Integer JoinTime) {
            this.JoinTime = JoinTime;
        }

        public String getMember_Account() {
            return Member_Account;
        }

        public void setMember_Account(String Member_Account) {
            this.Member_Account = Member_Account;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }
    }
}
