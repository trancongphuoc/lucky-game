/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nh.repository;

import com.nh.bean.AdvertiseTmp;
import com.nh.database.DB;
import java.util.List;

/**
 *
 * @author hadc
 */
public class AdvertiseTmpDao extends GenericDao<AdvertiseTmp> {

    public List<AdvertiseTmp> get(String sql) {
        List<AdvertiseTmp> es = DB.query(sql, mapper);
        return es;
    }

    @Override
    public boolean update(AdvertiseTmp entity) {
        boolean ok = false;
        String sql = "UPDATE Advertise_tmp SET STATUS =?  WHERE ROWID =?";
        int i = DB.update(sql, AdvertiseTmp.STATUS_PROCESSED, entity.getId());
        ok = i == 1;

        return ok;
    }

}
