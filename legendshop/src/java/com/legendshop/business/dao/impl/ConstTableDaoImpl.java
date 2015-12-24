package com.legendshop.business.dao.impl;

import java.util.List;

import com.legendshop.business.dao.ConstTableDao;
import com.legendshop.core.dao.impl.BaseDaoImpl;
import com.legendshop.model.entity.ConstTable;

public class ConstTableDaoImpl extends BaseDaoImpl implements ConstTableDao {

	@Override
	public List<ConstTable> loadAllConstTable() {
		return findByHQL("from ConstTable c order by c.id.type, c.seq");
	}

}
