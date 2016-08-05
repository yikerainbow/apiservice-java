package org.huzhu.dao;

import cn.sina.api.data.dao.util.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jihui2 on 2015/3/11.
 */
public class InsuranceListDao {

    public class Row {
        private int id;
        private String name;
        private String company;
        private String type;
        private String insured_content;
        private String rate;
        private String insured_sum;
        private String admin;
        public Row(int id, String name, String company, String type, String insured_content, String rate, String insured_sum, String admin) {
            this.id = id;
            this.name = name;
            this.company = company;
            this.type = type;
            this.insured_content = insured_content;
            this.rate = rate;
            this.insured_sum = insured_sum;
            this.admin = admin;
        }
        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getCompany() {
            return company;
        }
        public String getType() {
            return type;
        }
        public String getInsured_content() {
            return insured_content;
        }
        public String getRate() {
            return rate;
        }
        public String getInsured_sum() {
            return insured_sum;
        }
        public String getAdmin() {
            return admin;
        }
    }

    private static final String DB_NAME = "insurance";
    private static final String TABLE_NAME = "insurance_product_table";
    private static final String SQL_QUERY_ALL = "select `id`,`name`, `company`,`type`,`insured_content`, `rate`, `insured_sum`, `admin` from `" + DB_NAME + "`.`" + TABLE_NAME + "`";
    //private static final String SQL_QUERY_ALL = "select * from `" + DB_NAME + "`.`" + TABLE_NAME + "`";
    //private static final String SQL_RM_INSURANCE = "delete from `"+DB_NAME+"`.`"+TABLE_NAME+"` where `appid` = ?";
    //private static final String SQL_INSERT_INSURANCE = "INSERT INTO `"+DB_NAME+"`.`"+TABLE_NAME+"` (`appid`, `appkey`, `name`, `url`, `status`, `info`) VALUES (?, ?, ?, ?, ?, ?)";

    private JdbcTemplate jt;
    public void setJdbcTemplate(JdbcTemplate jt) {
        this.jt = jt;
    }

    public List<Row> getInsuranceList() {
        final List<Row> list = new LinkedList<Row>();
        jt.query(SQL_QUERY_ALL, new RowMapper() {
            // @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                Row row = null;
                row = new Row(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8));

                list.add(row);
                return null;
            }
        });
        return list;
    }
}
