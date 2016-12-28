package com.bottle.business.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bottle.business.common.vo.OperationVO;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants.OperationTypeEnum;

@Service
public class OperationRecorder extends AbstractBaseBean implements IOperationRecorder {
	private List<OperationVO> operationList = new ArrayList<OperationVO>();
	
	@Override
	public void log(OperationVO vo) {
		super.validateObject(vo);
		
		operationList.add(vo);
	}

	public List<OperationVO> getOperationList() {
		return operationList;
	}

	public void setOperationList(List<OperationVO> operationList) {
		this.operationList = operationList;
	}

	@Override
	public List<OperationVO> getOperationListByType(OperationTypeEnum type) {
		final List<OperationVO> rtnList = new ArrayList<OperationVO>();
		
		for (final OperationVO vo : operationList) {
			super.validateObject(vo);
			if (true == type.equals(vo.getType())) {
				rtnList.add(vo);
			}
		}
		
		return rtnList;
	}
}
