package com.bottle.common.constants;

import java.util.HashMap;
import java.util.Map;

public interface ILanguageConstants {
	String _ConnectionStatus_Connected_ = "\u8FDE\u63A5\u6210\u529F";
	String _ConnectionStatus_Disconnected_ = "\u672A\u8FDE\u63A5";
	
	String _SystemLogMessage_SerialCommandTimeout__ = "\u4E32\u53E3\u547D\u4EE4\u54CD\u5E94\u8D85\u65F6";
	String _SystemLogMessage_TemplateNotExisted__ = "\u4E32\u53E3\u547D\u4EE4\u54CD\u5E94\u8D85\u65F6";
	
	String _RealProductionInfoPanel_Order_ = "\u5E8F\u53F7";
	String _RealProductionInfoPanel_Timestamp_ = "\u65F6\u95F4";
	String _RealProductionInfoPanel_ModelName_ = "\u6A21\u677F\u540D\u79F0";
	String _RealProductionInfoPanel_ErrorCode_ = "\u9519\u8BEF\u7801";
	String _RealProductionInfoPanel_Price_ = "\u5355\u4EF7";
	String _RealProductionInfoPanel_Weight_ = "\u91CD\u91CF";
	
	String _CheckErrorMessage_ReadBarCode_ = "\u8BFB\u4E8C\u7EF4\u7801\u9519\u8BEF";
	String _CheckErrorMessage_Overweight_ = "\u8D85\u91CD";
	String _CheckErrorMessage_ImageMatch_ = "\u56FE\u50CF\u5339\u914D\u9519\u8BEF";
	String _CheckErrorMessage_MaterialMath_ = "\u6750\u8D28\u5339\u914D\u9519\u8BEF";
	String _CheckErrorMessage_UnknowTemplate_ = "\u6570\u636E\u5E93\u4E2D\u65E0\u6B64\u74F6\u4F53\u6863\u6848";
	String _CheckErrorMessage_Unknown_ = "\u672A\u77E5\u9519\u8BEF";
	
	String _TemplateManagementPanel_DownloadTemplate_Success_ = "\u4E0B\u8F7D\u6A21\u7248\u5230\u4E3B\u63A7\u6210\u529F";
	String _TemplateManagementPanel_DownloadTemplate_Failed_ = "\u4E0B\u8F7D\u6A21\u7248\u5230\u4E3B\u63A7\u5931\u8D25";
	String _TemplateManagementPanel_DeleteTemplate_Success_ = "\u5220\u9664\u4E3B\u63A7\u4E0A\u6307\u5B9A\u6A21\u7248\u6210\u529F";
	String _TemplateManagementPanel_DeleteTemplate_Failed_ = "\u5220\u9664\u4E3B\u63A7\u4E0A\u6307\u5B9A\u6A21\u7248\u5931\u8D25";
		
	String _TemplateManagementPanel_TemplateListTitle_Name_ = "\u540D\u79F0";
	String _TemplateManagementPanel_TemplateListTitle_BarCode_ = "\u4E8C\u7EF4\u7801";
	String _TemplateManagementPanel_TemplateListTitle_IsMetal_ = "\u662F\u5426\u91D1\u5C5E";
	String _TemplateManagementPanel_TemplateListTitle_Weight_ = "\u91CD\u91CF";
	String _TemplateManagementPanel_TemplateListTitle_Price_ = "\u4EF7\u683C(\u5143)";
		
	@SuppressWarnings("serial")
	Map<Long, String> errorCodeAndErroMessageMap = new HashMap<Long, String>(){
	{
		put(1L, _CheckErrorMessage_ReadBarCode_);
		put(2L, _CheckErrorMessage_Overweight_);
		put(3L, _CheckErrorMessage_ImageMatch_);
		put(4L, _CheckErrorMessage_MaterialMath_);
		put(5L, _CheckErrorMessage_UnknowTemplate_);
		put(6L, _CheckErrorMessage_Unknown_);
	}};
}
