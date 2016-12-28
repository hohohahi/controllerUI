package com.bottle.business.common.service;

import java.util.List;

import com.bottle.business.common.vo.OperationVO;
import com.bottle.common.constants.ICommonConstants;

public interface IOperationRecorder {
	void log(final OperationVO vo);
	List<OperationVO> getOperationListByType(ICommonConstants.OperationTypeEnum type);
}
