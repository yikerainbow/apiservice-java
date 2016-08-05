package org.huzhu.service;

import org.huzhu.dao.InsuranceListDao;

import java.util.List;

/**
 * Created by jihui2 on 2015/3/12.
 */
public class InsuranceListService {
    private InsuranceListDao dao;
    public void setDao(InsuranceListDao dao) {
        this.dao = dao;
    }

    public List<InsuranceListDao.Row> getAllFromDb() {
        return dao.getInsuranceList();
    }
}
