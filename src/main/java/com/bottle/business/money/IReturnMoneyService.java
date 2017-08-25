package com.bottle.business.money;

import com.bottle.common.constants.ICommonConstants;

public interface IReturnMoneyService {
	void pay(final String phoneNumberStr, final ICommonConstants.CashModeEnum cachModeEnum);
}
