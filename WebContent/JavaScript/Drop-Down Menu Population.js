
/* This Function populates the Dates for a Drop-Down Menu */
function populateDateMenu(DayFieldParameter, MonthFieldParameter, YearFieldParameter){

	/* Function Variables */
	var Today = new Date();
	var Months = ['January','February','March','April','May','June','July','August','September','October','November','December'];
	var MonthNumber = ['01','02','03','04','05','06','07','08','09','10','11','12'];
	var ThisYear = Today.getFullYear();
    
	/* Reference Variables */
    var DayField = document.getElementById(DayFieldParameter);
    var MonthField = document.getElementById(MonthFieldParameter);
    var YearField = document.getElementById(YearFieldParameter);

	/* Set the Initial Menu Items with Empty Values */
	DayField.options[0] = new Option("Day", "");
	MonthField.options[0] = new Option("Month", "");
	YearField.options[0] = new Option("Year", "");
	
	/* Populate the Days */
    for (var D = 1; D < 32; D++){
    DayField.options[D] = new Option(D, D);
	}
	
	/* Select Today's Day */
    /* DayField.options[Today.getDate()] = new Option(Today.getDate(), Today.getDate(), true, true); */
    
	/* Populate the Months */
	for (var M = 1; M < Months.length + 1; M++){
    MonthField.options[M] = new Option(Months[M - 1], MonthNumber[M - 1]);
	}
	
	/* Select Today's Month */
    /* MonthField.options[Today.getMonth()] = new Option(Months[Today.getMonth()], Months[Today.getMonth()], true, true); */
    
	/* Populate the Years */
	for (var Y = 1; Y < 101; Y++){
        YearField.options[Y]=new Option(ThisYear, ThisYear);
        ThisYear -= 1;
    }
	
	/* Select Today's Year */
    /* YearField.options[0] = new Option(Today.getFullYear(), Today.getFullYear(), true, true); */
	
	
	
	/* Usage Example:
	<!-- Include the Contact Me JavaScript File -->
	<script language="javascript" type="text/javascript" src="JavaScript Files/Drop-Down Menu Population.js" > </script>

	<!-- Create the Day, Month, and Year Drop-Down Menu -->
	<SELECT id ="Day" name = "DD"></SELECT>
	<SELECT id ="Month" name = "MM"></SELECT>
	<SELECT id ="Year" name = "YYYY"></SELECT>

	<!-- Call the (PopulateDateMenu) to populate the Day, Month, and Year Drop-Down Menu-->
	<script type="text/javascript">PopulateDateMenu("Day", "Month", "Year");</script> 
	
	*/
}


/* This Function populates the list of countries for a Drop-Down Menu */
function populateCountryMenu(CountryFieldParameter){

	/* Function Variables */
	var CountryList = ["United States of America","Andorra","United Arab Emirates","Afghanistan","Antigua & Barbuda","Anguilla","Albania","Armenia","Netherlands Antilles","Angola","Antarctica","Argentina","American Samoa","Austria","Australia","Aruba","Azerbaijan","Bosnia and Herzegovina","Barbados","Bangladesh","Belgium","Burkina Faso","Bulgaria","Bahrain","Burundi","Benin","Bermuda","Brunei Darussalam","Bolivia","Brazil","Bahama","Bhutan","Bouvet Island","Botswana","Belarus","Belize","Canada","Cocos (Keeling) Islands","Central African Republic","Congo","Switzerland","Côte D'ivoire (Ivory Coast)","Cook Iislands","Chile","Cameroon","China","Colombia","Costa Rica","Cuba","Cape Verde","Christmas Island","Cyprus","Czech Republic","Germany","Djibouti","Denmark","Dominica","Dominican Republic","Algeria","Ecuador","Estonia","Egypt","Western Sahara","Eritrea","Spain","Ethiopia","Finland","Fiji","Falkland Islands (Malvinas)","Micronesia","Faroe Islands","France","France, Metropolitan","Gabon","United Kingdom (Great Britain)","Grenada","Georgia","French Guiana","Ghana","Gibraltar","Greenland","Gambia","Guinea","Guadeloupe","Equatorial Guinea","Greece","South Georgia and the South Sandwich Islands","Guatemala","Guam","Guinea-Bissau","Guyana","Hong Kong","Heard & McDonald Islands","Honduras","Croatia","Haiti","Hungary","Indonesia","Ireland","Israel","India","British Indian Ocean Territory","Iraq","Islamic Republic of Iran","Iceland","Italy","Jamaica","Jordan","Japan","Kenya","Kyrgyzstan","Cambodia","Kiribati","Comoros","St. Kitts and Nevis","Korea, Democratic People's Republic of","Korea, Republic of","Kuwait","Cayman Islands","Kazakhstan","Lao People's Democratic Republic","Lebanon","Saint Lucia","Liechtenstein","Sri Lanka","Liberia","Lesotho","Lithuania","Luxembourg","Latvia","Libyan Arab Jamahiriya","Morocco","Monaco","Moldova, Republic of","Madagascar","Marshall Islands","Mali","Mongolia","Myanmar","Macau","Northern Mariana Islands","Martinique","Mauritania","Monserrat","Malta","Mauritius","Maldives","Malawi","Mexico","Malaysia","Mozambique","Namibia","New Caledonia","Niger","Norfolk Island","Nigeria","Nicaragua","Netherlands","Norway","Nepal","Nauru","Niue","New Zealand","Oman","Panama","Peru","French Polynesia","Papua New Guinea","Philippines","Pakistan","Poland","St. Pierre & Miquelon","Pitcairn","Puerto Rico","Portugal","Palau","Paraguay","Qatar","Réunion","Romania","Russian Federation","Rwanda","Saudi Arabia","Solomon Islands","Seychelles","Sudan","Sweden","Singapore","St. Helena","Slovenia","Svalbard & Jan Mayen Islands","Slovakia","Sierra Leone","San Marino","Senegal","Somalia","Suriname","Sao Tome & Principe","El Salvador","Syrian Arab Republic","Swaziland","Turks & Caicos Islands","Chad","French Southern Territories","Togo","Thailand","Tajikistan","Tokelau","Turkmenistan","Tunisia","Tonga","East Timor","Turkey","Trinidad & Tobago","Tuvalu","Taiwan, Province of China","Tanzania, United Republic of","Ukraine","Uganda","United States Minor Outlying Islands","Uruguay","Uzbekistan","Vatican City State (Holy See)","St. Vincent & the Grenadines","Venezuela","British Virgin Islands","United States Virgin Islands","Viet Nam","Vanuatu","Wallis & Futuna Islands","Samoa","Yemen","Mayotte","Yugoslavia","South Africa","Zambia","Zaire","Zimbabwe","Unknown or unspecified country"];
	var CountryCodeList = ["US","AD","AE","AF","AG","AI","AL","AM","AN","AO","AQ","AR","AS","AT","AU","AW","AZ","BA","BB","BD","BE","BF","BG","BH","BI","BJ","BM","BN","BO","BR","BS","BT","BV","BW","BY","BZ","CA","CC","CF","CG","CH","CI","CK","CL","CM","CN","CO","CR","CU","CV","CX","CY","CZ","DE","DJ","DK","DM","DO","DZ","EC","EE","EG","EH","ER","ES","ET","FI","FJ","FK","FM","FO","FR","FX","GA","GB","GD","GE","GF","GH","GI","GL","GM","GN","GP","GQ","GR","GS","GT","GU","GW","GY","HK","HM","HN","HR","HT","HU","ID","IE","IL","IN","IO","IQ","IR","IS","IT","JM","JO","JP","KE","KG","KH","KI","KM","KN","KP","KR","KW","KY","KZ","LA","LB","LC","LI","LK","LR","LS","LT","LU","LV","LY","MA","MC","MD","MG","MH","ML","MN","MM","MO","MP","MQ","MR","MS","MT","MU","MV","MW","MX","MY","MZ","NA","NC","NE","NF","NG","NI","NL","NO","NP","NR","NU","NZ","OM","PA","PE","PF","PG","PH","PK","PL","PM","PN","PR","PT","PW","PY","QA","RE","RO","RU","RW","SA","SB","SC","SD","SE","SG","SH","SI","SJ","SK","SL","SM","SN","SO","SR","ST","SV","SY","SZ","TC","TD","TF","TG","TH","TJ","TK","TM","TN","TO","TP","TR","TT","TV","TW","TZ","UA","UG","UM","UY","UZ","VA","VC","VE","VG","VI","VN","VU","WF","WS","YE","YT","YU","ZA","ZM","ZR","ZW","ZZ"];
	
	/* Reference Variables */
    var CountryField = document.getElementById(CountryFieldParameter);

	/* Populate the Countries */
	for (var M=0; M<CountryList.length; M++){
    CountryField.options[M]=new Option(CountryList[M], CountryCodeList[M]);
	}
}


/* This Function populates the list of Event Categories for a Drop-Down Menu */
function populateEventCategoryMenu(EventCategoryFieldParameter){

	/* Function Variables */
	var EventCategoryList = ["For Sale","Services","Housing","Gigs","Community","Resumes","Personals","Jobs"];
	
	/* Reference Variables */
    var EventCategoryField = document.getElementById(EventCategoryFieldParameter);

	/* Set the Initial Menu Item with an Empty Value */
	EventCategoryField.options[0]=new Option("All Categories", "");
	
	/* Populate the Countries */
	for (var M=1; M<EventCategoryList.length; M++){
    EventCategoryField.options[M]=new Option(EventCategoryList[M - 1], EventCategoryList[M - 1]);
	}

}

/* This Function populates the list of Event Sub Categories for a Drop-Down Menu */
function populateEventSubCategoryMenu(EventSubCategoryFieldParameter){

	/* Function Variables */
	var EventSubCategoryList = ["For Sale","Services","Housing","Gigs","Community","Resumes","Personals","Jobs"];
	
	/* Reference Variables */
    var EventSubCategoryField = document.getElementById(EventSubCategoryFieldParameter);

	/* Set the Initial Menu Item with an Empty Value */
	EventSubCategoryField.options[0]=new Option("All Sub Categories", "");
	
	/* Populate the Countries */
	for (var M=1; M<EventSubCategoryList.length; M++){
    EventSubCategoryField.options[M]=new Option(EventSubCategoryList[M - 1], EventSubCategoryList[M - 1]);
	}

}

/* This Function populates the list of Event Timezones for a Drop-Down Menu */
function populateEventTimezoneMenu(EventTimezoneFieldParameter){

	/* Define the default Drop-Down Menu Index */
	var DropDownMenuDefaultIndex = 25;

	/* Function Variables */
	var EventTimezoneNameList = ["GMT - (GMT+0:00) Greenwich Mean Time","UTC - (GMT+0:00) Universal Coordinated Time","ECT - (GMT+1:00) European Central Time","EET - (GMT+2:00) Eastern European Time","ART - (GMT+2:00) (Arabic) Egypt Standard Time","EAT - (GMT+3:00) Eastern African Time","MET - (GMT+3:30) Middle East Time","NET - (GMT+4:00) Near East Time","PLT - (GMT+5:00) Pakistan Lahore Time","IST - (GMT+5:30) India Standard Time","BST - (GMT+6:00) Bangladesh Standard Time","VST - (GMT+7:00) Vietnam Standard Time","CTT - (GMT+8:00) China Taiwan Time","JST - (GMT+9:00) Japan Standard Time","ACT - (GMT+9:30) Australia Central Time","AET - (GMT+10:00) Australia Eastern Time","SST - (GMT+11:00) Solomon Standard Time","NST - (GMT+12:00) New Zealand Standard Time","MIT - (GMT-11:00) Midway Islands Time","HST - (GMT-10:00) Hawaii Standard Time","AST - (GMT-9:00) Alaska Standard Time","PST - (GMT-8:00) Pacific Standard Time","PNT - (GMT-7:00) Phoenix Standard Time","MST - (GMT-7:00) Mountain Standard Time","CST - (GMT-6:00) Central Standard Time","EST - (GMT-5:00) Eastern Standard Time","IET - (GMT-5:00) Indiana Eastern Standard Time","PRT - (GMT-4:00) Puerto Rico and US Virgin Islands Time","CNT - (GMT-3:30) Canada Newfoundland Time","AGT - (GMT-3:00) Argentina Standard Time","BET - (GMT-3:00) Brazil Eastern Time","CAT - (GMT-1:00) Central African Time"];
	var EventTimezoneCodeList = ["GMT","UTC","ECT","EET","ART","EAT","MET","NET","PLT","IST","BST","VST","CTT","JST","ACT","AET","SST","NST","MIT","HST","AST","PST","PNT","MST","CST","EST","IET","PRT","CNT","AGT","BET","CAT"];
	
	/* Reference Variables */
    var EventTimezoneField = document.getElementById(EventTimezoneFieldParameter);

	/* Populate the Countries */
	for (var M=0; M<EventTimezoneNameList.length; M++){
    EventTimezoneField.options[M]=new Option(EventTimezoneNameList[M], EventTimezoneCodeList[M]);
	}

	/* Set the default Index for the Drop-Down Menu */
	EventTimezoneField.options[DropDownMenuDefaultIndex] = new Option(EventTimezoneNameList[DropDownMenuDefaultIndex], EventTimezoneCodeList[DropDownMenuDefaultIndex], true, true); 
	
}

/* This Function populates the list of Event Miles for a Drop-Down Menu */
function populateEventMilesMenu(EventMilesFieldParameter, SelectedIndex){

	/* Function Variables */
	var EventMilesDisplayList = ["5 miles","8 miles","10 miles","15 miles","25 miles","35 miles","50 miles","75 miles","100 miles","150 miles","200 miles"];
	var EventMilesValueList = ["5","8","10","15","25","35","50","75","100","150","200"];
	
	/* Reference Variables */
    var EventMilesField = document.getElementById(EventMilesFieldParameter);

	/* Populate the Miles */
	for (var M=0; M<EventMilesDisplayList.length; M++){
    EventMilesField.options[M]=new Option(EventMilesDisplayList[M], EventMilesValueList[M]);
	}

	/* Select The desired Index */
	if(SelectedIndex != null && SelectedIndex > -1){ 
    EventMilesField.options[SelectedIndex] = new Option(EventMilesDisplayList[SelectedIndex], EventMilesValueList[SelectedIndex], true, true); 
	}
	
}