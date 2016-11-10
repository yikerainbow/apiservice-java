package org.huzhu.dao;

import cn.sina.api.commons.util.ApiLogger;
import cn.sina.api.data.dao.util.JdbcTemplate;
import org.huzhu.commons.Constants;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by guobao on 2016/7/26.
 */
public class WeixinListDao {

    public static class TotalNum {
        private int member = 0;
        public int getMember() {
            return member;
        }
        private int event = 0;
        public int getEvent() {
            return event;
        }
        public TotalNum(int memberNum, int eventNum) {
            this.member = memberNum;
            this.event = eventNum;
        }
    }

    public static class Row {
        private String openid;
        private String nickname;
        private String personname;
        private String personid;
        private double balance;
        private String update;
        public Row(String openid, String nickname, String personname, String personid, double balance, String update) {
            this.openid = openid;
            this.nickname = nickname;
            this.personname = personname;
            this.personid = personid;
            this.balance = balance;
            this.update = update;
        }

        public String getOpenid() {
            return openid;
        }
        public String getNickname() {
            return nickname;
        }
        public String getPersonname() {
            return personname;
        }
        public String getPersonid() {
            return personid;
        }
        public double getBalance() {
            return balance;
        }
        public String getUpdate() {
            return update;
        }
    }

    public class VcodeRow {
        private String vcode;
        private int status;
        private String update;
        public VcodeRow(String vcode, int status, String update) {
            this.vcode = vcode;
            this.status = status;
            this.update = update;
        }
        public String getVcode() {
            return vcode;
        }
        public int getStatus() {
            return status;
        }
        public String getupdate() {
            return update;
        }
    }

    public class MemberRow {
        private String personname;
        private String personid;
        private double balance;
        private String update;
        public MemberRow(String personname, String personid, double balance, String update) {
            this.personname = personname;
            this.personid = personid;
            this.balance = balance;
            this.update = update;
        }
        public String getPersonname() {
            return personname;
        }
        public String getPersonid() {
            return personid;
        }
        public double getBalance() {
            return balance;
        }
        public String getUpdate() { return update; }
    }

    private static final String DB_NAME = "weixin";
    private static final String REGISTER_TABLE_NAME = "register_vcode_table";
    private static final String MEMBER_TABLE_NAME = "huzhu_member_table";
    private static final String SQL_QUERY_NUM = "select count(1) from `" + DB_NAME + "`.`" + MEMBER_TABLE_NAME + "`";
    private static final String SQL_RM_REGISTER = "delete from `"+DB_NAME+"`.`" + REGISTER_TABLE_NAME + "` where `openid` = ? and `mobile` = ?";
    private static final String SQL_INSERT_REGISTER_VCODE = "INSERT INTO `"+DB_NAME+"`.`"+ REGISTER_TABLE_NAME +"` (`openid`, `nickname`, `mobile`, `vcode`, `status`) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_QUERY_VERIFY_MSG = "select `vcode`, `status`, `update` from `"+DB_NAME+"`.`"+ REGISTER_TABLE_NAME +"` where `openid` = ? and `mobile` = ?";
    private static final String SQL_UPDATE_VERIFY_STATUS = "update `"+DB_NAME+"`.`"+ REGISTER_TABLE_NAME +"` set `status` = 1 where `openid` = ? and `mobile` = ?";
    private static final String SQL_INSERT_MEMBER_INFO = "INSERT INTO `"+DB_NAME+"`.`"+ MEMBER_TABLE_NAME +"` (`openid`, `nickname`, `personname`, `personid`, `balance`) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_QUERY_MEMBERS = "select `openid`, `nickname`, `personname`, `personid`, `balance`, `update` from `"+DB_NAME+"`.`"+ MEMBER_TABLE_NAME +"` where `openid` = ?";
    private static final String SQL_QUERY_MEMBER_INFO = "select `personname`, `personid`, `balance`, `update` from `"+DB_NAME+"`.`"+ MEMBER_TABLE_NAME +"` where `personid` = ?";

    private JdbcTemplate jt;
    public void setJdbcTemplate(JdbcTemplate jt) {
        this.jt = jt;
    }


    public Boolean insertRegisterCode(String openid, String nickname, String mobile, String vcode, int state) {
        try {
            long t1 = System.currentTimeMillis();
            boolean ret =
                    jt.update(SQL_INSERT_REGISTER_VCODE,
                            new Object[]{openid, nickname, mobile, vcode, state}) > 0;

            long t2 = System.currentTimeMillis();
            long t = t2 - t1;
            if (t > Constants.OP_DB_TIMEOUT) {
                ApiLogger.warn(new StringBuilder(64).append("").toString());//TODO
            }
            return ret;
        } catch (RuntimeException e) {
            ApiLogger.error(new StringBuilder(128).append("").toString());//TODO
            throw e;
        }
    }

    public boolean rmRegiter(String openid, String mobile) {
        try {
            long t1 = System.currentTimeMillis();
            boolean ret =
                    jt.update(SQL_RM_REGISTER,
                            new Object[]{openid, mobile}) > 0;
            long t2 = System.currentTimeMillis();
            long t = t2 - t1;
            if (t > Constants.OP_DB_TIMEOUT) {
                ApiLogger.warn(new StringBuilder(64).append("").toString());//TODO
            }
            return ret;
        } catch (RuntimeException e) {
            ApiLogger.error(new StringBuilder(128).append("").toString());//TODO
            throw e;
        }
    }

    public int getMembers() {
        int  memberNum = 0;
        try {
            memberNum = jt.queryForInt(SQL_QUERY_NUM);
        } catch (DataAccessException e) {
            ApiLogger.error(new StringBuilder(128).append("").toString());//TODO
            throw e;
        }

        return memberNum;
    }

    public VcodeRow queryMsgInfo(String openid, String mobile) {
        final List<VcodeRow> list = new LinkedList<VcodeRow>();
        jt.query(SQL_QUERY_VERIFY_MSG, new Object[]{openid, mobile}, new RowMapper() {
            // @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                VcodeRow row = null;
                row = new VcodeRow(resultSet.getString(1),   //vcode
                        resultSet.getInt(2),  //status
                        resultSet.getString(3));  // update
                list.add(row);
                return null;
            }
        });
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Boolean updateRegisterStatus(String openid, String mobile) {
        try {
            long t1 = System.currentTimeMillis();
            boolean ret =
                    jt.update(SQL_UPDATE_VERIFY_STATUS,
                            new Object[]{openid, mobile}) > 0;

            long t2 = System.currentTimeMillis();
            long t = t2 - t1;
            if (t > Constants.OP_DB_TIMEOUT) {
                ApiLogger.warn(new StringBuilder(64).append("").toString());//TODO
            }
            return ret;
        } catch (RuntimeException e) {
            ApiLogger.error(new StringBuilder(128).append("").toString());//TODO
            throw e;
        }
    }

    public boolean isRegistered(String openid) {
        boolean  ret = false;

        int count = 0;
        try {
            String sql = "select count(1) from `" + DB_NAME + "`.`" + REGISTER_TABLE_NAME + "` where `status` = 1 and `openid` = '" + openid + "'";
            count = jt.queryForInt(sql);
        } catch (DataAccessException e) {
            ApiLogger.error(new StringBuilder(128).append("").toString());//TODO
            throw e;
        }

        if(count > 0) {
            ret = true;
        }

        return ret;
    }

    public Boolean insertMemberInfo(String openid, String nickname, String personName, String personId) {
        try {
            int membershipFee = Constants.membershipFee;
            long t1 = System.currentTimeMillis();
            boolean ret =
                    jt.update(SQL_INSERT_MEMBER_INFO,
                            new Object[]{openid, nickname, personName, personId, membershipFee}) > 0;

            long t2 = System.currentTimeMillis();
            long t = t2 - t1;
            if (t > Constants.OP_DB_TIMEOUT) {
                ApiLogger.warn(new StringBuilder(64).append("").toString());//TODO
            }
            return ret;
        } catch (RuntimeException e) {
            ApiLogger.error(new StringBuilder(128).append("").toString());//TODO
            throw e;
        }
    }

    public List<Row> getMemberList(String openid) {
        final List<Row> list = new LinkedList<Row>();
        jt.query(SQL_QUERY_MEMBERS, new Object[]{openid}, new RowMapper() {
            // @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                Row row = null;
                row = new Row(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getDouble(5),
                        resultSet.getString(6));
                list.add(row);
                return null;
            }
        });
        return list;
    }

    public MemberRow queryMemberInfo(String personId) {
        final List<MemberRow> list = new LinkedList<MemberRow>();
        jt.query(SQL_QUERY_MEMBER_INFO, new Object[]{personId}, new RowMapper() {
            // @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                MemberRow row = null;
                row = new MemberRow(resultSet.getString(1),   //personname
                        resultSet.getString(2),  //personid
                        resultSet.getDouble(3),  //balance
                        resultSet.getString(4));  // update
                list.add(row);
                return null;
            }
        });
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

}
