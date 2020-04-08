package com.chasepay.constants.webutil;

public class AccountChartConstant {
	
	public static final String[][] ROOT_ACCOUNTS = {
		{"A", "1000", "Assets"},
		{"L", "2000", "Liability"},
		{"Q", "3000", "Equity"},
		{"R", "4000", "Revenue"},
		{"E", "5000", "Expense"}
		
	};

	public static final String[][] ASSETS = {
		{"1010", "Petty Cash"},
		{"1100", "Cash in Bank"},
		{"1210", "Accounts Receivable"},
		{"1220", "Other Receivable"},
		{"1270", "Allowance for Doubtful Accts"},
		{"1290", "Inventory"},
		{"1310", "Prepaid Expenses"},
		{"1320", "Prepaid payroll taxes"},
		{"1330", "Prepaid State Income Tax"},
		{"1340", "Perpaid Federal Income Tax"},
		{"1380", "Due From Others"},
		{"1390", "Loans to Stockholders"},
		{"1410", "Other Current Assets"},
		{"1510", "Land"},
		{"1520", "Building"},
		{"1530", "Leasehold Improvements"},
		{"1540", "Furniture & Fixtures"},
		{"1550", "Computers & Equipment"},
		{"1560", "Trucks & Automobiles"},
		{"1570", "Service Equipment"},
		{"1590", "Assets Sold"},
		{"1620", "Accum Depr - Building"},
		{"1630", "Accum Depr - Leasehold Improvements"},
		{"1640", "Accum Depr - Furniture & Fixtures"},
		{"1650", "Accum Depr - Computers & Equipment"},
		{"1660", "Accum Depr - Trucks & Automobiles"},
		{"1670", "Accum Depr - Service Equipment"},
		{"1690", "Accum Depr - Assets Sold"},
		{"1710", "Other Assets"},
		{"1720", "Rental Deposit"},
		{"1810", "Goodwill"},
		{"1820", "Organization Costs"},
		{"1830", "Loan Fee"},
		{"1920", "Accum Amort - Organiz. Costs"}
	};
	
	public static final String[][] LIABILITY = {
		
		{"2010", "Accounts Payable - Trade"},
		{"2020", "Credit Card Payable"},
		{"2110", "Notes Payable - Current"},
		{"2210", "Accrued Salaries & Wages"},
		{"2220", "Federal W/H Payable"},
		{"2230", "Federal MED W/H PAYABLE"},
		{"2240", "FICA W/H Payable"},
		{"2250", "State W/H Payable"},
		{"2260", "Local W/H Payable"},
		{"2270", "Other W/H Payable"},
		{"2280", "FUTA Payable"},
		{"2290", "SUTA Payable"},
		{"2310", "State Income Tax Payable"},
		{"2320", "Federal Income Tax Payable"},
		{"2330", "Other Taxes Payable"},
		{"2410", "Accrued Other Expenses"},
		{"2610", "Notes Payable - Long Term"},
		{"2650", "Mortgage Payable"},
		{"2710", "Other Liabilities"},
		{"2810", "Capital Stock"},
		{"2820", "Additional Paid-in Capital"},
		{"2840", "Loan Payable"},
		{"2850", "Shareholder Contributions"}
		
	};
	

	public static final String[][] EQUITY = {
		{"3010", "Common Stock, No Par"},
		{"3020", "Retained Earnings"},
		{"3030", "Treasury Stock"},
		{"3040", "Shareholder Distributions"}
	};
	
	public static final String[][] REVENUE = {
		{"4010", "Sales - Products"},
		{"4020", "Sales - Service"},
		{"4030", "Sales Returns & Allowances"},
		{"4040", "Sales Discounts"},
		{"4050", "Gain/Loss on Asset Sales"},
		{"4060", "Interest Income"},
		{"4070", "Other Income"}
	};
	
	public static final String[][] EXPENSES_1 = {
		{"51000", "Advertising"},
		{"52000", "Car & Truck Expenses"},
		{"53000", "Contractors"},
		{"54000", "Education and Training"},
		{"55000", "Employee Benefits"},
		{"56000", "Meals & Entertainment"},
		{"57000", "Office Expenses & Postage"},
		{"58000", "Other Expenses"},
		{"59000", "Personal"},
		{"60000", "Professional Services"},
		{"61000", "Rent or Lease"},
		{"62000", "Supplies"},
		{"63000", "Travel"},
		{"64000", "Utilities"},
		{"9999", "Temporary"}
	};
	
	public static final String[][] EXPENSES_2 = {
		{"52010", "Gas"},
		{"52020", "Mileage"},
		{"52030", "Repairs"},
		{"52040", "Vehicle Insurance"},
		{"52050", "Vehicle Licensing"},
		{"55010", "Accident Insurance"},
		{"55020", "Health Insurance"},
		{"55030", "Life Insurance"},
		{"56010", "Entertainment"},
		{"56020", "Restaurants/Dining"},
		{"57010", "Hardware"},
		{"57020", "Office Supplies"},
		{"57030", "Packaging"},
		{"57040", "Postage"},
		{"57050", "Printing"},
		{"57060", "Shipping & Couriers"},
		{"57070", "Software"},
		{"57080", "Stationery"},
		{"58010", "Bank Fees"},
		{"58020", "Business Insurance"},
		{"58030", "Commissions"},
		{"58040", "Depreciation"},
		{"58050", "Interest - Mortgage"},
		{"58060", "Interest - Other"},
		{"58070", "Online Services"},
		{"58080", "Reference Materials"},
		{"58090", "Repairs & Maintenance"},
		{"58100", "Subscriptions/Dues/Memberships"},
		{"58110", "Taxes & Licenses"},
		{"58120", "Wages"},
		{"60010", "Accounting"},
		{"60020", "Legal Fees"},
		{"61010", "Equipment"},
		{"61020", "Machinery"},
		{"61030", "Office Space"},
		{"61040", "Vehicles"},
		{"63010", "Airfare"},
		{"63020", "Hotel/Lodging/Accommodation"},
		{"64010", "Gas & Electrical"},
		{"64020", "Phone"}
	};
	
	public static final String[] TYPES = {
		"Assets",
		"Liabilities",
		"Equity",
		"Revenues",
		"Expense",
		"Expense",
		"Expense",
		"Revenues",
		"Revenues"
	};
	
	public static final String ACCOUNT_STATUS_BASIC = "0";
	public static final String ACCOUNT_STATUS_EDITABLE = "1";
	
	public static final String ROOT_PARENT_NO = "-1";
	
	public static final String TYPE_CREDITCARD_PAYMENT = "0";
	public static final String TYPE_CREDITCARD_FEES = "1";
	public static final String TYPE_CREDITCARD_INTEREST_CHARGS = "2"; 
	public static final String TYPE_CREDITCARD_CHARGES = "3";
	public static final String TYPE_CREDITCARD_CREDIT = "4";

	public static final String TYPE_BANK_WITHDRAW = "5"; 
	public static final String TYPE_BANK_DEPOSIT = "6";
	public static final String TYPE_BANK_FEES = "7";
	public static final String TYPE_BANK_INTEREST = "8"; 
	public static final String TYPE_BANK_CHECK = "9";
	public static final String TYPE_BANK_ATM = "10";
	
	public static final String TYPE_BANK_INTERNAL_TRANSFER = "11";
	public static final String TYPE_BANK_CREDIT_PAYMENT = "12";
	public static final String TYPE_BANK_INTERNAL_TRANSFER_DEPOSIT = "13";
	
	public static final String TYPE_PAYPAL_CHARGE = "14";
	public static final String TYPE_PAYPAL_DEPOSIT = "15";
	
	public static final String TYPE_EBAY_CREDIT = "16";
	
	public static final String RECORD_TYPE_WITHDRAW = "IN ('9','10','5') ";
	public static final String TYPE_MARKETPLACE = "17";
	
	public static final String ACCOUNT_TYPE_CREDITCARD = "3";
	
	public static int INDEX_CREDITCARD_PAYABLE_CHART_NO = 1;
	public static int INDEX_BANK_ACCOUNT_CHART_NO = 2;
	public static int INDEX_ACCOUNT_RECEIVABLE_NO = 3;

	//public static final String INTERNAL_REF = "Ref: ";
	
	public static final String INTERNAL_NOTE = "Internal funds transfer ";
	public static final String REVERT_NOTE = "Revert journal entry ";
	
	public static final String BANK_FEE = "Bank Fees";
	public static final String INTERSET = "Interest - Other";
	public static final String CURRENT_INCOME = "Current Income";
	public static final String OTHER_INCOME = "Other Income";
	public static final String COST_GODDS_SALE = "Cost of Goods Sold";
	public static final String RETAIN_EARNINGS = "Retained Earnings";
	public static final String SHAREHOLDER_DISTRIBUTES = "Shareholder Distributions";
	
	public static final String INTEREST_INCOME = "Interest Income";
	public static final String INTEREST = "Interest - Other";
	public static final String CHECK = "Check Paid #";
	public static final String CHECK2 = "Check Paid";
	public static final String ATM = "ATM Withdraw ";
	
	public static final String CREDITCARD_PAYABLE_SUBJECT = "L";
	public static final String BANK_ACCOUNT_SUBJECT = "A";
	public static final String UNKNOWN_CHART_SUBJECT = "U";
	public static final String EXPENSE_SUBJECT = "E";
	public static final String ASSETS_SUBJECT = "A";
	public static final String REVENUE_SUBJECT = "R";
	
	//public static final String DESC_INTERNAL_TRANSFER_FROM = "INTERNAL TRANSFER FROM ";
	//public static final String DESC_INTERNAL_TRANSFER_TO = "INTERNAL TRANSFER TO ";
	
	//public static final String DESC_PAYMENT_TO = "CREDIT CARD PAYMENT TO ";
	//public static final String DESC_PAYMENT_FROM = "CREDIT CARD PAYMENT FROM ";
	
	//public static final String DESC_PAYMENT = "CREDIT CARD PAYMENT";
	//public static final String DESC_INTERNAL_TRANSFER = "INTERNAL TRANSFER";
	
	public static final String CHART_DEFAULT_ATTRIBUTE = "0000000000000000";
	
	public  static final String DEF_CHART_NO = "chart_no";
	public  static final String DEF_CHART_NAME = "chart_name";
	public  static final String DEF_CHART_KEYWORD = "keyword";
	public  static final String DEF_CHART_NO_PARENT = "parent_no";
	public static final String DEF_SUBJECT_ID = "subject_id";
	
	public static final String CHART_NO_SALES_TAX = "2330";
	public static final String CHART_NO_BANK_FEE = "6810";
	public static final String CHART_NO_SALES_INCOME = "4010";
	public static final String CHART_NO_INVENTORY = "1290";
	public static final String CHART_NO_SALES_REFUND = "4030";
	public static final String CHART_NO_VENDOR = "5100";
	
	public static final String PAYPAL_FEE = "Paypal Fee";
	public static final String PAYPAL_TAX = "EBay Sales Tax";
	
}
