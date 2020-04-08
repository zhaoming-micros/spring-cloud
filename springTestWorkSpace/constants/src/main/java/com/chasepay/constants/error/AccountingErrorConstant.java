package com.chasepay.constants.error;

public class AccountingErrorConstant {

	public static final int MAX_PASSWORD_RETRY = 10;
	//public static final String ERROR_TYPE_WEB = "A";
	public static final String ERROR_TYPE_COMMONS = "B";
	public static final String ERROR_TYPE_PARSER = "C";  //0
	public static final String ERROR_TYPE_DATABASE = "D"; //1
	public static final String ERROR_TYPE_WEB_PROCESS = "E"; //2
	public static final String ERROR_TYPE_VALIDATE_FAIL = "F"; //3
	
	public static final String ERROR_LEVEL_ERROR = "0";
	public static final String ERROR_LEVEL_INFO = "1";
	
	public static final String PROCESSING_CODE = "PCODE";
	
	public static final String CODE_0000 = "0000";
	public static final String CODE_0001 = "0001"; //can not find file
	public static final String CODE_0002 = "0002"; //can not parse pdf file
	public static final String CODE_0003 = "0003"; //not valid format
	public static final String CODE_0004 = "0004"; //parse throw exception

	public static final String CODE_0005 = "0005"; //Statement not recognized
	public static final String CODE_0006 = "0006"; //process pdf error
	public static final String CODE_0007 = "0007"; //file already processed
	public static final String CODE_0008 = "0008"; //create processed file folder fail
	public static final String CODE_0009 = "0009"; //Back up original file failed
	public static final String CODE_0010 = "0010"; //Back up error file failed
	public static final String CODE_0011 = "0011"; //Unrecognized statement file
	public static final String CODE_0012 = "0012"; //Runtime Exception
	public static final String CODE_0013 = "0013"; //Extract zip file failed;
	public static final String CODE_0014 = "0014"; //Remove unzip folder failed
	public static final String CODE_0015 = "0015"; //not valid file name
	public static final String CODE_0016 = "0016"; //set bank account in chart of account error
	public static final String CODE_0017 = "0017"; //set bank account error
	public static final String CODE_0018 = "0018"; //delete error
	public static final String CODE_0019 = "0019"; //already processed or id not found, not allow to delete
	public static final String CODE_0020 = "0020"; //delete statement id not found error
	public static final String CODE_0021 = "0021"; //statement not in current fiscal year
	public static final String CODE_0022 = "0022"; //insert statement SQL error
	public static final String CODE_0023 = "0023"; //insert statement record SQL error
	public static final String CODE_0024 = "0024"; //insert statement record SQL error
	
	public static final String CODE_1005 = "1005"; //get database connection error
	public static final String CODE_1006 = "1006"; //database parameter not correct
	public static final String CODE_1007 = "1007"; //database sql error
	public static final String CODE_1008 = "1008"; //record exist
	public static final String CODE_1009 = "1009"; //no record
	public static final String CODE_1010 = "1010"; //update default company failed
	public static final String CODE_1011 = "1011"; //update user authorization failed
	public static final String CODE_1012 = "1012"; //update zero record
	public static final String CODE_1013 = "1013"; //get records not correct
	public static final String CODE_1014 = "1014"; //table not exist
	public static final String CODE_1015 = "1015"; //db process exception
	
	public static final String CODE_2001 = "2001"; //username / passwd is null
	public static final String CODE_2002 = "2002"; //function process return null
	public static final String CODE_2003 = "2003"; //servlet process exception
	public static final String CODE_2004 = "2004"; //username / passwd is empty or username is not valid
	public static final String CODE_2005 = "2005"; //user need login
	public static final String CODE_2006 = "2006"; //file upload exception
	public static final String CODE_2007 = "2007"; //file upload empty file
	public static final String CODE_2008 = "2008"; //user register : user exist
	public static final String CODE_2009 = "2009"; //tell user check mail reset password
	public static final String CODE_2010 = "2010"; //activation link invalid parameter
	public static final String CODE_2011 = "2011"; //reset password activation code expired or used
	public static final String CODE_2012 = "2012"; //user activation code expired or used
	public static final String CODE_2013 = "2013"; //activation link user not exist
	public static final String CODE_2014 = "2014"; //password error
	public static final String CODE_2015 = "2015"; //user not exist
	public static final String CODE_2016 = "2016"; //get user email fail
	public static final String CODE_2017 = "2017"; //user login, not sign up
	public static final String CODE_2018 = "2018"; //user session expired
	public static final String CODE_2019 = "2019"; //reset password email already sent
	public static final String CODE_2020 = "2020"; //reset password email is null or empty
	public static final String CODE_2021 = "2021"; //missing required fields
	public static final String CODE_2022 = "2022"; //reset password code is not match up
	public static final String CODE_2023 = "2023"; //reset password status is not correct
	public static final String CODE_2024 = "2024"; //user company not correct
	public static final String CODE_2025 = "2025"; //reset password link does not have email
	public static final String CODE_2026 = "2026"; //get username link does not have back email/first name/last name/company name
	public static final String CODE_2027 = "2027"; //invalid input in register form
	public static final String CODE_2028 = "2028"; //insert/update company/user company/user company function/company account chart failed
	public static final String CODE_2029 = "2029"; //request_type is null or empty or invalid request_type
	public static final String CODE_2030 = "2030"; //no record found in user company
	public static final String CODE_2031 = "2031"; //get company invitation list error
	public static final String CODE_2032 = "2032"; //get authzed user list error
	public static final String CODE_2033 = "2033"; //user is not administrator to authorize user
	public static final String CODE_2034 = "2034"; //Send email failed
	public static final String CODE_2035 = "2035"; //user authorization email is null or empty
	public static final String CODE_2036 = "2036"; //request has been approved or rejected
	public static final String CODE_2037 = "2037"; //request is in processing
	public static final String CODE_2038 = "2038"; //request company is not exist
	public static final String CODE_2039 = "2039"; //no administrator for the company
	public static final String CODE_2040 = "2040"; //user has been activated
	public static final String CODE_2041 = "2041"; //find company failed
	public static final String CODE_2042 = "2042"; //not statement found
	public static final String CODE_2043 = "2043"; //Servlet request message is null

	
	public static final String CODE_2044 = "2044"; //doGetInternal exception
	public static final String CODE_2045 = "2045"; //doPostInternal exception
	
	public static final String CODE_2046 = "2046"; //doGetInternal null
	public static final String CODE_2047 = "2047"; //doPostInternal null
	
	public static final String CODE_2050 = "2050"; //Servlet response general error
	public static final String CODE_2051 = "2051"; //Servlet request no data error
	public static final String CODE_2052 = "2052"; //no business class
	public static final String CODE_2053 = "2053"; //invoke business class error
	public static final String CODE_2054 = "2054"; //unsupport request type
	public static final String CODE_2055 = "2055"; //user has joined to the company
	public static final String CODE_2058 = "2058"; //url query is not correct
	
	public static final String CODE_2059 = "2059"; //update transaction line error
	
	public static final String CODE_2060 = "2060"; //generate report failed
	public static final String CODE_2061 = "2061"; //account has transactions
	public static final String CODE_2062 = "2062"; //invalid action (status not correct)
	public static final String CODE_2063 = "2063"; //get chart of account error
	public static final String CODE_2064 = "2064"; //user has owned a company
	public static final String CODE_2065 = "2065"; //insert code error
	
	public static final String CODE_2066 = "2066"; //statement duplicate
	public static final String CODE_2067 = "2067"; //statement activity no transaction in current year
	public static final String CODE_2068 = "2068"; //internal transfer, find multiple transactions
	public static final String CODE_2069 = "2069"; //internal transfer, can not find link transaction
	public static final String CODE_2070 = "2070"; //close year failed
	
	public static final String CODE_2080 = "2080"; //generate invoice pdf failed
	
	public static final String CODE_3000 = "3000"; //user has no permission to do action
	
	public static final String CODE_3001 = "3001"; //validate reg fail
	public static final String CODE_3002 = "3002"; //validate function fail
	public static final String CODE_3003 = "3003"; //field not exist
	public static final String CODE_3004 = "3004"; //validate fail
	
	public static final String CODE_3005 = "3005"; //max invalid password
	public static final String CODE_3006 = "3006"; //status not correct
	
	public static final String CODE_4000 = "4000"; //move attached file error
	public static final String CODE_4001 = "4001"; //query attached file error
	
	public static final String CODE_4100 = "4100"; //no suck sub action type error in csv
	public static final String CODE_4101 = "4101"; //CSV format error
	public static final String CODE_4102 = "4102"; //update description failed
	
	public static final String CODE_4103 = "4103"; //can not upload more than 1 attach file for a refenence transaction
	
	public static final String CODE_DEFAULT = "9999"; 
	public static final String CODE_MSG_DEFAULT = "9998";
	
	public static final String CODE_DEFAULT_2 = "9999_2"; 
	public static final String CODE_MSG_DEFAULT_2 = "9998_2";
	
	public static final String CODE_TEXT = "File not exist ";
	
	
	public static final String TEXT_SYSTEM_ERROR = "System Error";

	
}
