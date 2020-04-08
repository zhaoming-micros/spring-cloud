package com.chasepay.constants.webutil;

public class AccountServletConstant {

	public static final String SERVLET_REQUEST_TYPE = "CPAcct_Srv_Type";
	
	public static final int CODE_EXIPRE = 7;
	
	public static final String SERVLET_REQUEST_TYPE_DISPLAY = "1";
	public static final String SERVLET_REQUEST_TYPE_BUSINESS = "2";
	public static final String SERVLET_REQUEST_TYPE_CONFIG = "3";
	
	public static final String SERVLET_REQUEST_SUB_TYPE = "CPAcct_Srv_SubType";
	
	public static final String SERVLET_REQUEST_SUB_TYPE_CONFIG_ROUTER = "1"; 
	public static final String SERVLET_REQUEST_SUB_TYPE_UPLOAD_STATEMENT = "5";
	public static final String SERVLET_REQUEST_SUB_TYPE_REGISTER1 = "4";
	public static final String SERVLET_REQUEST_SUB_TYPE_REGISTER2 = "19";
	public static final String SERVLET_REQUEST_SUB_TYPE_REQAUTHZ = "2";
	public static final String SERVLET_REQUEST_SUB_TYPE_JOINCOMPANYAPPROVAL = "3";
	public static final String SERVLET_REQUEST_SUB_TYPE_JOINCOMPANY = "21";
	public static final String SERVLET_REQUEST_SUB_TYPE_REPORT = "25";
	public static final String SERVLET_REQUEST_SUB_TYPE_REPORT_JOURNAL_ENTRIES = "41";
	
	public static final String SERVLET_REQUEST_SUB_TYPE_ACTION = "CPAcct_Srv_SubType_Action";
	public static final String SERVLET_REQUEST_SUB_TYPE_ACTION_INSERT = "1";
	public static final String SERVLET_REQUEST_SUB_TYPE_ACTION_UPDATE = "2";
	public static final String SERVLET_REQUEST_SUB_TYPE_ACTION_DELETE = "3";
	public static final String SERVLET_REQUEST_SUB_TYPE_ACTION_SELECT = "4";
	

	public static final String SERVLET_REQUEST_SUB_TYPE_COMPANY_JOIN = "5";
	public static final String SERVLET_REQUEST_SUB_TYPE_COMPANY_INVITE = "6";
	public static final String SERVLET_REQUEST_SUB_TYPE_GET_LIST = "7";
	public static final String SERVLET_REQUEST_SUB_TYPE_ACTION_DOWNLOAD = "8";
	public static final String SERVLET_REQUEST_SUB_TYPE_ACTION_REVERT = "9";
	
	public static final String SERVLET_REQUEST_SUB_TYPE_SHARE = "10";
	public static final String SERVLET_REQUEST_SUB_TYPE_NOTIFY = "11";
	
	public static final String SERVLET_REQUEST_DATA_STATUS = "CPAcct_Srv_Data_Status";
	public static final String SERVLET_REQUEST_DATA_ORDER = "CPAcct_Srv_Data_Order";
	
	public static final String SERVLET_AUTHZUSER_SEND_EMAIL = "sendemail";

	public static final String SERVLET_REPORT_REPORT_BALANCE_SHEET = "balancesheet";
	public static final String SERVLET_REPORT_REPORT_PL_STATEMENT = "plstatement";
	
	public static final String LOGIN_ID = "login_id";
	public static final String CURRENT_COMPANY_ID = "default_company_id";
	public static final String CURRENT_COMPANY_NAME = "default_company_name";
	public static final String OLDPSSWD = "oldpsswd";
	
	public static final String COOKIE = "cookie";
	
	public static final String HEADER = "req_header";

	public static final String REMOTE_INFO = "remote_info";
	public static final String DATE = "date";
	public static final String TIME = "time";
	public static final String TIMEZONE = "timezone";
	
	public static final int PASSWORD_RETRY_TIMES = 3;

	public static final String DATE_FORMAT = "yyyyMMddHHmmss Z";
	public static final String DATE_FORMAT2 = "yyyyMMdd";
	
	public static final String ACTIVATE_VALID_TIME = "72"; //hours
	
	public static final int MAX_REQ_LEN = 1024;
	
	public static final String GET_USERNAME_BACKUPEMAIL = "backupemail";
	
	public static final String EMAIL_TO_RECIPIENTS = "recipients_to";
	public static final String EMAIL_CC_RECIPIENTS = "recipients_cc";
	public static final String EMAIL_BCC_RECIPIENTS = "recipients_bcc";
	
	//opening balance / closing entries
	public static final String DEFAULT_DESCRITPION = "ending balance of ";
	public static final String BALANCE_DESCRIPTION = "balance_description";
	
	//URL
	public static final String URL = "url";
	public static final String URL_PARA_SEPARATOR = "?";
	public static final String URL_PARA_DELIMETER = "&";
	public static final String URL_PARA_ERRORCODE = "errorcode";
	
	public static final String ERROR_HTML = "Error.html";
	public static final String APP_HTML = "App.html";
	//public static final String SESSION_EXPIRED_HTML = "App2.html";
	public static final String LOGIN_PAGE = "login.html";
	public static final String DOGET_EXCEPTION = "App.html#dogetError";
	
	//Hash tage
	public static final String HASH_TAG = "#";
	public static final String HASH_TAG_LOGIN = "#login";
	public static final String HASH_TAG_DASHBOARD = "#dashboard";
	public static final String HASH_TAG_RESET_PW_CLICK_LINK_FAIL = "#notifypage/email/resetpw/fail";
	public static final String HASH_TAG_RESET_PW_CLICK_LINK_SUCC = "#resetpw";
	public static final String HASH_TAG_AUTH_USER_NEW_PW = "#newpw";
	public static final String HASH_TAG_USER_ACTIVATION_FAIL = "#notifypage/email/useractivation/fail";
	public static final String HASH_TAG_USER_REGISTER2 = "#register2";
	public static final String HASH_TAG_USER_INVITATION_FAIL = "#notifypage/email/authzuser/fail";
	public static final String HASH_TAG_USER_INVITATION_SUCC = "#notifypage/email/authzuser/succ";
	public static final String HASH_TAG_JOIN_ACPT_FAIL = "#notifypage/email/joincompanyacpt/fail";
	public static final String HASH_TAG_JOIN_ACPT_SUCC = "#notifypage/email/joincompanyacpt/succ";
	
	public static final String PACKAGE = "com.chasepay.accounting.business.impl.";
	
	public static final String CHARSET_UTF8 = "utf-8";
	
	//Company
	public static final String COMPANY_JOIN_REQ_EMAIL = "jc_email";
	public static final String COMPANY_JOIN_REQ_FIRST_NAME = "jc_first_name";
	public static final String COMPANY_JOIN_REQ_LAST_NAME = "jc_last_name";
	public static final String REVOKE_AUTHZED_USERS_LIST = "revokeAuthzedUserList";
	/*
	public static final String IS_FIRST_COMPANY = "isFirstCompany";

	public static final String ACPT_COMPANY_INVITATIONS_LIST = "acptCompanyInvitationList";
	
	//Auth User
	public static final String IS_REGISTERTED = "isRegistered"; 

	*/

	
	public static final String DATE_FORMAT3 = "MM/dd/yyyy";
	public static final String DATE_FORMAT4 = "MM/dd/yy";
	
	public static final String STATEMENT_INFO = "statement_info";
	public static final String JOUNRA_DATA = "journal_data";
	public static final String JOUNRA_IDS = "journal_ids";
	public static final String JOUNRA_DELETE_FLAG = "DE";
	public static final String JOURNAL_UPLOAD_FILE_PREFIX = "Journal_";
	public static final int MAX_FILE_NAME_LEN = 40;
	public static final String FILE_NAME_ABBREV = "...";
	
	//report
	public static final String REPORT_DATE_FROM = "report_date_from";
	public static final String REPORT_DATE_TO = "report_date_to";
	
	public static final String REPORT_TYPE = "report_type";
	public static final String REPORT_TYPE_BALANCE_SHEET = "1";
	public static final String REPORT_TYPE_BALANCE_SHEET_COMPARISON = "2";
	public static final String REPORT_TYPE_PL_STATEMENT = "3";
	public static final String REPORT_TYPE_PL_STATEMENT_COMPARISON = "4";
	public static final String REPORT_TYPE_CUMULATIVE_GENERAL_LEDGER = "5";
	public static final String REPORT_TYPE_JOURNAL_ENTRIES = "6";
	public static final String REPORT_TYPE_TRIAL_BALANCE = "7";
	
	public static final String Check_User_Name = "checkUserName";

	//invoice
	public static final String INVOICE_DATA = "invoice_data";
	public static final String INVOCIE_IDS = "invoice_ids";	
	public static final String INVOICE_DEFAULT_DESC = "Invoice";
	public static final String INVOICE_NUMBER_ZERO = "00000000";
	public static final String CRDIT_AMOUNT = "0";
	public static final String DEBIT_AMOUNT = "1";
	
	//csv
	public static final String CSV_UPLOAD_FILE_PREFIX = "csv_";
	
	//unknown statement
	public static final String UNKNOWN_UPLOAD_FILE_PREFIX = "unknown_";

	public static final String ERROR_CODE = "ErrorCode";
	
	
	public static final String FLAG_DOWNLOAD_STATEMENT_CSV = "flag_download_statement_csv";
	public static final String STATEMENT_CSV_HEAD = "Statement";
	
	public static final String PAGE = "page";
	
	public static final String SERVLET_REQUEST_SUB_TYPE_UPLOAD_SKU_IMAGE = "66";
	public static final String SERVLET_REQUEST_SUB_TYPE_UPLOAD = "71";
}
