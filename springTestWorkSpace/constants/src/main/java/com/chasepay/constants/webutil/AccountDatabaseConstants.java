package com.chasepay.constants.webutil;

public class AccountDatabaseConstants 
{
	public static final int ACCOUNT_IMPORT_DATE_DIFF = 5;
	public static final int MAX_USER_NUMBER_TO_CHECK = 3;
	//commons
	//public static final String REVERT_DESCRIPTION = "Revert";
	public static final String status = "status";
	
	public static final String status_init = "3";
	public static final String status_active = "0";
	public static final String status_pending = "1";
	public static final String status_inactive = "2";
	public static final String status_freeze = "4";
	public static final String status_revert = "1";
	
	public static final String note = "note";
	public static final String user_id = "user_id";
	public static final String processor_id = "processor_id";
	public static final String login_id = "login_id";
	public static final String company_id = "company_id";
	public static final String email = "email";
	public static final String emails = "emails";
	public static final String first_name = "first_name";
	public static final String last_name = "last_name";
	public static final String middle_name = "middle_name";
	public static final String mobile = "mobile";
	public static final String phone = "phone";
	public static final String phone_ext = "phone_ext";
	public static final String psswd = "psswd";
	public static final String date = "date";
	public static final String time = "time";
	
	public static final String create_date = "create_date";

	//public static final String COMPARE_LEFT_KEY = "COMPARE_LEFT_KEY";
	//public static final String COMPARE_RIGHT_KEY = "COMPARE_RIGHT_KEY";
                                   
	public static final String description = "description";
	public static final String description2 = "description2";

	public static final String credit_amount = "credit_amount";
	public static final String debit_amount = "debit_amount";
	public static final String credit_balance = "credit_balance";
	public static final String debit_balance = "debit_balance";
	public static final String amount = "amount";
	public static final String fee = "fee";
	public static final String tax = "tax";
	public static final String net = "net";
	public static final String currency = "currency";
	public static final String rate = "rate";
	
	//company
	public static final String table_name_company = "company"; 
	public static final String company_company_name = "company_name";
	public static final String company_company_type = "company_type";
	public static final String company_address_number = "address_number";
	public static final String company_street_line1 = "street_line1";
	public static final String company_street_line2 = "street_line2";
	public static final String company_street_line3 = "street_line3";
	public static final String company_city = "city";
	public static final String company_company_state = "company_state";
	public static final String company_zip = "zip";
	public static final String company_zip2 = "zip2";
	public static final String company_contactor = "contactor";
	public static final String company_fiscal_month = "fiscal_month";
	
	//bank
	public static final String table_name_bank = "bank";
	public static final String bank_id = "bank_id";
	public static final String bank_name = "bank_name";
	
	//statement file
	public static final String table_name_attache_file = "attache_file";
	public static final String file_id = "file_id";
	public static final String statement_file_name = "file_name";

	//company account info
	/*
	public static final String table_name_account_info = "company_account_info";
	public static final String company_account_info_account_id = "account_id";
	public static final String company_account_info_bank_id = "bank_id";
	public static final String company_account_info_account_number = "account_number";
	public static final String company_account_info_account_type = "account_type";
	public static final String company_account_info_account_currency = "account_currency";
	public static final String company_account_info_account_name = "account_name";
	public static final String company_account_info_account_name2 = "account_name2";
	*/

	/*
	 * status: 0 
	 *         1 - imported but no record, has file
	 *         2 - no record, bank does not provide file
	 *         3 - missing -- no file name
	 *         4 - missing, manual entry
	 */
	
	//statement table
	//statement table
	public static final String table_name_statement = "statement";
	public static final String statement_statement_id = "statement_id";
	public static final String statement_bank_id = "bank_id";
	//public static final String statement_file_id = "file_id";
	//public static final String statement_file_name = "file_name";
	public static final String statement_account_number = "account_number";
	public static final String statement_account_type = "account_type";
	public static final String statement_statement_year = "statement_year";
	public static final String statement_statement_month = "statement_month";
	public static final String statement_statement_start_date = "statement_start_date";
	public static final String statement_statement_end_date = "statement_end_date";

	public static final String statement_account_status = "account_status";
	public static final String statement_another_year = "another_year";
	public static final String statement_overlap_year = "overlap_year";
	public static final String statement_withdraw_charges_amount = "withdraw_charges_amount";
	public static final String statement_deposit_payment_amount = "deposit_payment_amount";
	public static final String statement_deposit_payment_amount_total = "deposit_payment_amount_total";
	public static final String statement_withdraw_charges_amount_total = "withdraw_charges_amount_total";
	public static final String statement_withdraw_charges_count = "withdraw_charges_count";
	public static final String statement_deposit_payment_count = "deposit_payment_count";
	public static final String statement_deposit_payment_count_total = "deposit_payment_count_total";
	public static final String statement_withdraw_charges_count_total = "withdraw_charges_count_total";
	
	public static final String status_statement_no_record = "1";
	public static final String status_statement_no_record_no_file = "2";
	public static final String status_statement_missing = "3";
	public static final String status_statement_account_not_open = "4";
	public static final String status_statement_account_closed = "5";
	
	public static final String statement_account_status_first_statement = "1";
	public static final String statement_account_status_last_statement = "2";
	public static final String statement_account_status_missing_manual_entry = "3";
	
	public static final String statement_bank_name = "bank_name";
	
	public static final String account_type_saving = "1";
	public static final String account_type_checking = "2";
	public static final String account_type_creditcard = "3";
	public static final String account_type_cd = "4";
	public static final String account_type_paypal = "5";

	//statement record
	//public static final String table_name_statement_record = "statement_record";
	public static final String statement_record_id = "statement_record_id";
	public static final String statement_record_date = "record_date";
	public static final String statement_record_reference_number = "record_reference_number";
	public static final String statement_record_check_number = "check_number";
	public static final String statement_record_credit_amount = "credit_amount";
	public static final String statement_record_debit_amount = "debit_amount";
	public static final String statement_record_amount = "amount";
	public static final String statement_record_rate = "rate";
	public static final String statement_record_foreign_amount = "foreign_amount";
	public static final String statement_record_foreign_currency = "foreign_currency";
	public static final String statement_record_balance = "balance";
	public static final String statement_record_type = "record_type";
	
	//transaction
	public static final String table_name_transaction_record = "transaction_line";
	public static final String transaction_id = "transaction_id";
	public static final String transaction_account2 = "chart_no2";	
	public static final String account2_is_debit = "chart_no2_debit";	
	public static final String account_entry_type = "account_entry_type";
	public static final String transaction_id2 = "transaction_id2";
	
	public static final String transaction_status_import = "1";
	public static final String transaction_status_overlap = "2";
	
	public static final String subject_id = "subject_id";
	
	//company account chart
	public static final String table_name_chart_of_account = "chart_of_account";
	public static final String chart_of_account_subject_id = "subject_id";
	public static final String chart_of_account_no = "chart_no";
	public static final String chart_of_account_name = "chart_name";
	public static final String chart_of_account_attribute = "attribute";
	public static final String chart_of_account_parent_no = "parent_no";
	public static final String chart_of_account_balance = "balance";
	public static final String chart_of_account_keyword = "keyword";
	public static final String alias_chart_of_account_no = "alias_chart_no";
	public static final String alias_chart_of_account_name = "alias_chart_name";
	
	public static final String description_add_by_statement = "from bank statement";
	
	public static final String chart_of_account_status_init = "0";
	public static final String chart_of_account_status_editable = "1";
	public static final String chart_of_account_status_bankaccount = "2";
	
	/*
	public static final String table_name_account_book_note = "account_book_note";
	public static final String account_book_note_id = "account_book_note_id";
	public static final String key_name = "key_name";
	public static final String key_value = "key_value";
	*/
	
	
	//user company
	public static final String table_name_user_company = "user_company";
	public static final String user_company_role_id = "role_id";
	public static final String is_editable = "OptionFlag1";
	//public static final String user_company_is_default_company = "is_default_company";
	
	//user info
	public static final String table_name_user_info = "user_info";
	
	//company account book
	public static final String table_name_company_account_book = "company_account_book";
	public static final String company_account_book_id = "company_account_book_id";
	public static final String company_account_book_date = "book_date";
	public static final String company_account_book_reference_id = "reference_id";   
	//public static final String company_account_book_reference_id2 = "reference_id2"; 
	public static final String company_account_book_type = "type";    
	public static final String company_account_book_note = "note";
	
	public static final String company_account_book_type_statement = "1";
	public static final String company_account_book_type_manual = "2";
	public static final String company_account_book_type_invoice = "3";
	
	public static final String company_account_book_type_statement_str = "S";
	public static final String company_account_book_type_manual_str = "M";
	public static final String company_account_book_type_invoice_str = "I";
	public static final String company_account_book_type_business_activity_str = "B";
	
	public static final String key_name_list = "key_name_list";
	public static final String value_list = "value_list";
	//public static final String new_value_list = "new_value_list";
	
	public static final String company_account_book_status_complete = "0";
	public static final String company_account_book_status_adjust = "1";
	public static final String company_account_book_status_incomplete = "2";
	public static final String company_account_book_status_back = "3";
	
	//invoice
	public static final String table_name_invoice = "invoice";
	public static final String invoice_invoice_id = "invoice_id";
	public static final String invoice_invoice_no = "invoice_no";
	public static final String invoice_invoice_date = "invoice_date";
	public static final String invoice_invoice_due_date = "invoice_due_date";
	public static final String invoice_from_to = "from_to";
	public static final String invoice_tax_amount = "tax_amount";
	public static final String invoice_subtotal_amount = "subtotal_amount";
	public static final String invoice_total_amount = "total_amount";
	public static final String invoice_balance_amount = "balance_amount";
	public static final String invoice_invoice_description = "invoice_description";
	
	//invoice_record
	public static final String table_name_invoice_record = "invoice_record";
	public static final String invoice_record_id = "invoice_record_id";
	public static final String invoice_record_item_name = "item_name";
	public static final String invoice_record_description= "description";
	public static final String invoice_record_unit_cost = "unit_cost";
	public static final String invoice_record_quantity = "quantity";
	public static final String invoice_record_tax_pct = "tax_pct";
	public static final String invoice_record_line_amount = "line_amount";
	
	//client
	public static final String table_name_client = "client";
	public static final String client_id = "client_id";
	
	//account_balance
	public static final String table_name_account_balance = "account_balance";
	public static final String table_name_account_start_balance = "account_start_balance";
	public static final String account_balance_id = "account_balance_id";
	public static final String account_balance_credit_balance = "credit_balance";
	public static final String account_balance_debit_balance = "debit_balance";
	
	public static final String table_name_account_ending_balance = "account_ending_balance";
	
	public static final String table_name_menu_info = "sys_menu";
	public static final String menu_id = "menu_id";
	
	public static final String table_name_role_template = "role_template";
	public static final String table_name_role_menu_template = "role_menu_template";
	
	public static final String table_name_autdit_log = "audit_log";
	public static final String audit_log_audit_id = "audit_id";
	public static final String audit_log_user_id = "audit_user_id";
	public static final String audit_log_table_name = "table_name";
	public static final String audit_log_audit_record_id = "audit_record_id";
	public static final String audit_log_date = "date";
	public static final String audit_log_time = "time";
	public static final String audit_log_timezone = "timezone";
	public static final String audit_log_operation_id = "operation_id"; //1 - add 2 - update 3 - delete
	public static final String audit_log_old_value = "old_value";
	
	public static final String table_name_role = "role";
	public static final String role_id = "role_id";
	public static final String role_name = "role_name";
	public static final String invite_role_id = "invite_role_id";
	
	public static final String table_name_code = "user_code";
	public static final String code = "code";
	public static final String code_date = "code_date";
	public static final String code_type = "code_type";
	public static final String code_data = "code_data";
	
	public static final String CODE_TYPE_INVITE = "1";
	public static final String CODE_TYPE_JOIN = "2";
	public static final String CODE_TYPE_REGISTER = "3";
	public static final String CODE_TYPE_RESETPW = "4";
	
	public static final String audit_log_operation_insert = "1";
	public static final String audit_log_operation_update = "2";
	public static final String audit_log_operation_delete = "3";
	
	public static final String AUDIT_ACTION_LOGIN_OK_CODE = "1";
	public static final String AUDIT_ACTION_LOGOUT_CODE = "2";
	public static final String AUDIT_ACTION_LOGIN_FAIL_CODE = "3";
	public static final String AUDIT_ACTION_LOGIN_WITHOUT_LOGOUT_CODE = "4";
	public static final String AUDIT_ACTION_LOGIN_FAIL_SYSTEM_ERR_CODE = "5";
	
	public static final String AUDIT_ACTION_LOGIN_WITHOUT_LOGOUT = "login without logout";
	public static final String AUDIT_ACTION_LOGIN_OK = "login";
	public static final String AUDIT_ACTION_LOGOUT = "logout";
	public static final String AUDIT_ACTION_LOGIN_FAIL = "login fail : ";
	public static final String AUDIT_ACTION_LOGIN_FAIL_SYSTEM_ERR = "system error";
	
	public static final String book_status_OK = "0";
	public static final String book_status_rejected = "1";
	
	public static final String journal_entry_status_uncomplete = "0";
	public static final String journal_entry_status_import = "1";
	public static final String journal_entry_status_returned = "2";
	
	public static final int DB_INSERT_ERROR = -1;
	
	public static final String USER_SYSTEM = "-1";
	public static final String USER_ID_UNKNOWN = "-2";
	
	public static final String INSERT_GET_ID_SQL = "SELECT LAST_INSERT_ID()";
	
	public static final String NO_DEFAULT_COMPANY_ID = "-1";
	public static final String NO_PARENT = "-1";
	public static final int TREE_LEVEL_ROOT  = 0;
	public static final int TREE_DEF_SAME  = -1;
	
	public static final String NO_RECORD = "-1";
	
	public static final String YES = "1";
	public static final String NO = "0";
	
	public static final String ERROR_STATEMENT_DIR = "error";
	public static final String UNKNOWN_STATEMENT_DIR = "unknown";
	public static final String DELETED_FILES_DIR = "deletedFiles";
	
	public static final String ID = "id";
	
	public static final String PSSD_SLT = "CPayAccWebLuca";
	public static final String LOGIN_SLT = "CPayAccWebLogslt";
	public static final String ACTIVATE_SLT = "CPayAccWebActslt";
	public static final String RESET_SLT = "CPayAccWebResslt";
	public static final String REQ_SLT = "CPayAccWebReqest";
	
	public static final String ROLE_ADMIN2 = "0";
	public static final String ROLE_ADMIN = "1";
	public static final String ROLE_EDITOR = "2";
	public static final String ROLE_AUDIT = "3";
	public static final String ROLE_EMPOLYEES = "4";
	public static final String ROLE_ID_DEFAULT = "3";
	
	public static final String COMPANY_TYPE_BUSINESS = "0";
	public static final String COMPANY_TYPE_ACCOUNTING = "1";
	public static final String COMPANY_TYPE_CLIENT = "2";
	public static final String COMPANY_TYPE_VENDOR = "3";
	
	public static final String SUBJECT = "subject";
	
	public static final String DEFAULT_INVOICE_NO = "00000001";
	
	public static final String GTE = ">=";
	public static final String LTE = "<=";
	
	public static final String TO = "0";
	
	public static final String marketplace_mid = "marketplace_mid";
	
	/*
	public static Map<String, String> TOP_MENU_MAP = new HashMap<String, String>();
	public static Map<String, String> DEFAULT_FUNCTION_MAP = new HashMap<String, String>();
	public static Map<String, String> BASIC_FUNCTION_MAP = new HashMap<String, String>();
	public static Map<String, String> MEDIUM_FUNCTION_MAP = new HashMap<String, String>();
	public static Map<String, String> ADMINISTRATOR_EXCLUSIVE_FUNCTION_MAP = new HashMap<String, String>();
	
	static {
		
		TOP_MENU_MAP.put("1", "Book");
		TOP_MENU_MAP.put("2", "Tax");
		TOP_MENU_MAP.put("3", "Company");
		TOP_MENU_MAP.put("4", "Setting");
		
		DEFAULT_FUNCTION_MAP.put("15", "New Company");
		DEFAULT_FUNCTION_MAP.put("16", "Request");
		
		BASIC_FUNCTION_MAP.putAll(DEFAULT_FUNCTION_MAP);
		BASIC_FUNCTION_MAP.put("7", "Statements");
		BASIC_FUNCTION_MAP.put("8", "Transactions");
		BASIC_FUNCTION_MAP.put("9", "Journalists");
		BASIC_FUNCTION_MAP.put("5", "Vendors");
		BASIC_FUNCTION_MAP.put("6", "Customers");
		BASIC_FUNCTION_MAP.put("13", "Employees");
		BASIC_FUNCTION_MAP.put("11", "Users");
		BASIC_FUNCTION_MAP.put("10", "Chart of Accounts");
		BASIC_FUNCTION_MAP.put("12", "System");
		
		MEDIUM_FUNCTION_MAP.putAll(BASIC_FUNCTION_MAP);
		MEDIUM_FUNCTION_MAP.put("10", "Chart of Accounts");
		MEDIUM_FUNCTION_MAP.put("12", "System");
		
		ADMINISTRATOR_EXCLUSIVE_FUNCTION_MAP.put("11", "Users");
		ADMINISTRATOR_EXCLUSIVE_FUNCTION_MAP.put("15", "New Company");
	}
	*/
	
	public static final int column_chartno_company_account_book = 1;
	public static final int column_credit_amount_company_account_book = 3;
	public static final int column_debit_amount_company_account_book = 4;
	public static final int column_reference_id_company_account_book = 8;
	public static final int column_description_company_account_book = 5;
	public static final int column_note_company_account_book = 7;
	
	public static final int column_reference_id_account_book_note = 2;

	
	public static final int column_chartno_account_balance = 1;
	public static final int column_credit_balance_account_balance = 2;
	public static final int column_debit_balance_account_balance = 3;
	
	public static final String table_name_company_fiscal_year = "company_fiscal_year";
	public static final String company_fiscal_year_year = "year";
	public static final String company_fiscal_year_start_date = "start_date";
	public static final String company_fiscal_year_end_date = "end_date";
	public static final String company_fiscal_year_freeze_date = "freeze_date";
	public static final String company_fiscal_year_in_use = "in_use";
	
	public static final String status_inuse = "1";
	public static final String status_not_inuse = "0";
	
	public static final String table_name_statement_keywords = "statement_keywords";
	public static final String table_name_statement_keywords_system = "statement_keywords_system";
	
	public static final String keywords = "keywords";
	
	public static final String table_name_statement_file = "statement_file";
	public static final String table_name_statement2 = "statement2";
	public static final String statement_date = "statement_date";
	
	public static final String table_name_share = "share";
	
	public static final String attachment = "attachment";
	
	public static String[] company_deletable_tables = {
		
		table_name_company_fiscal_year,
		table_name_account_ending_balance,
		table_name_account_start_balance
	};
	
	public static String[] company_related_tables = {
		
		table_name_chart_of_account,
		table_name_statement,
		table_name_statement_file,
		table_name_statement2,
		//table_name_statement_record,
		table_name_company_account_book,
		table_name_transaction_record,
		table_name_attache_file,
		//table_name_account_book_note,
		table_name_invoice,
		table_name_invoice_record,
		table_name_client,
		table_name_company_fiscal_year,
		table_name_account_start_balance,
		table_name_account_ending_balance,
		table_name_statement_keywords
		//,
		//table_name_statement_history,
		//table_name_statement_record_history
	};
	
	public static final String ACCOUNT_YEAR_START = "account_book_year_start";
	public static final String ACCOUNT_YEAR_END = "account_book_year_end";
	public static final String ACCOUNT_YEAR = "account_book_year";
	public static final String ACCOUNT_MONTH = "account_book_month";
	public static final String ACCOUNT_YEAR_STATUS = "account_book_year_status";

	public static final String status_import_match = "0";
	public static final String status_import_match_two_years = "1";
	public static final String status_import_wrong_two_years = "2";
	public static final String status_import_wrong = "3";
	public static final String status_import_statement_not_in_fiscal_year = "4";
	
	public static final String fiscal_year = "fiscal_year";
	public static final String linked_transaction = "Internal Transfers ";
	
	public static final String SQL_COND_IN = " IN ";
	public static final String SQL_NULL = "NULL";
	
	public static String[] company_account_related_tables = {
		
		table_name_account_balance,
		table_name_statement,
		table_name_statement2,
		//table_name_statement_record,
		table_name_company_account_book,
		table_name_transaction_record,
		table_name_attache_file,
		//table_name_account_book_note,
		table_name_invoice,
		table_name_invoice_record,
		table_name_chart_of_account,
		table_name_account_start_balance,
		table_name_account_ending_balance
		//,
		//table_name_statement_history,
		//table_name_statement_record_history
		
	};
	
	public static final String EXPORT_FILE_PREFIX = "statement_";
	public static final String EXPORT_FILE_PREFIX2 = "QB_";
	
	public static final String KEY_SAVE_KEYWORD = "save_key_word";
	public static final String KEY_FOR_KEY_WORD = "key_for_keyword";
	public static final String DETAIL_RECORD_TYPE = "record_type";
}
