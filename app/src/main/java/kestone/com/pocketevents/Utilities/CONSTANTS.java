package kestone.com.pocketevents.Utilities;

import kestone.com.pocketevents.MODEL.More.RESPONSE.MoreResponse;

/**
 * Created by karan on 7/22/2017.
 */

public class CONSTANTS {


    private String BASEURL="http://pocketevents.in/eiab/RestServiceImpl.svc";
    public static final int RESULTCODE_COMPARE = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 99;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 101;
    private static String HOST_URL="http://pocketevents.in/eiab/";

    //URL  http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/GetContactUS
    //URL2 http://pocketevents.in/eiab/RestServiceImpl.svc/getTheme

    //URLS
    public  static final String URL_GET_VENUE_FILTERS = HOST_URL + "RestServiceImpl.svc/getVenueFilters";
    public  static final String URL_GET_SAVED_EVENT_LIST = HOST_URL + "RestServiceImpl.svc/getSavedEventList";
    public  static final String URL_FORGOT_PASSWORD = HOST_URL + "RestServiceImpl.svc/sendForgotLink";
    public  static final String URL_LOGIN = HOST_URL + "RestServiceImpl.svc/login";
    public  static final String URL_GET_VENUE_LIST = HOST_URL + "RestServiceImpl.svc/getVenueList";
    public  static final String URL_INSERT_SIGNUP = HOST_URL + "RestServiceImpl.svc/InsertSignUP";
    public  static final String URL_SAVE_EVENT = HOST_URL + "RestServiceImpl.svc/saveEvent";
    public  static final String URL_SAVED_EVENT_FILTERS = HOST_URL + "RestServiceImpl.svc/SavedEventList";
    public  static final String URL_LOGOUT = HOST_URL + "RestServiceImpl.svc/logout";
    public  static final String URL_DELETE_EVENT = HOST_URL + "RestServiceImpl.svc/DeleteEvent";
    public  static final String URL_SETUP = HOST_URL + "RestServiceImpl.svc/SetupAPI";
    public  static final String URL_DESIGN = HOST_URL + "RestServiceImpl.svc/DesignAPI";
    public  static final String URL_DESIGN_THEME = HOST_URL + "RestServiceImpl.svc/getTheme";
    public  static final String URL_DESIGN_EMAIL = HOST_URL + "RestServiceImpl.svc/DesignEmail";
    public  static final String URL_EVENTSEARCH = HOST_URL + "RestServiceImpl.svc/EventSearch";
    public  static final String URL_MORE = HOST_URL + "RestServiceImpl.svc/MoreAPI";
    public  static final String URL_CONSULTING_PRICE = HOST_URL + "RestServiceImpl.svc/GetPriceDetails";
    public  static final String URL_MYORDERS = HOST_URL + "RestServiceImpl.svc/MyOrder";
    public  static final String URL_EMAIL_INVOICE = HOST_URL + "RestServiceImpl.svc/InvoiceEmail";
    public  static final String URL_EMAIL_ALLEVENTS = HOST_URL + "RestServiceImpl.svc/EventDesignEmail";
    public  static final String URL_PAYMENT_API = HOST_URL + "RestServiceImpl.svc/PaymentDetails";
    public  static final String URL_CONTACT_US = HOST_URL + "RestServiceImpl.svc/GetContactUS";
    public  static final String URL_SUBMIT_QUERY = HOST_URL + "RestServiceImpl.svc/SubmitQuestion";
    public  static final String URL_SUBMIT_RATING = HOST_URL + "RestServiceImpl.svc/SubmitRating";
    public  static final String URL_GET_CITY = HOST_URL + "RestServiceImpl.svc/getCity";
    public  static final String URL_GET_PAYMENT_QUOTE = HOST_URL + "RestServiceImpl.svc/GetPaymentQuote";
    public  static final String URL_PAYMENT_SUCCESS = HOST_URL + "RestServiceImpl.svc/InsertInvoiceDetails";
    public  static final String URL_REWARDS_REFERRALS = HOST_URL + "RestServiceImpl.svc/ReferralReward";
    public  static final String URL_REFERRAL_REWARD_LIST = HOST_URL + "RestServiceImpl.svc/MyReferralRewardList";
    public  static final String URL_FOR_CHANGEPASSWORD = HOST_URL + "RestServiceImpl.svc/ChangePassword";
    public  static final String URL_FLOORPLAN = HOST_URL + "RestServiceImpl.svc/FlorePlanMail";
    public  static final String URL_FOR_REFFERAL_VALIDATION = HOST_URL + "RestServiceImpl.svc/RefferalValidation";
    public static  final String URL_Get_Consultancy_Info = HOST_URL + "RestServiceImpl.svc/getConsultancyinfo";
    public static  final String URL_Event_Search_Id = HOST_URL + "RestServiceImpl.svc/EventSearchId";
    public static  final String url_event_configuration = HOST_URL + "M_EmailView.aspx";
    //Fragments

    public static final String FRAGMENT_FILTERS = "filters";
    public static final String FRAGMENT_VENUE = "venue";
    public static final String FRAGMENT_SELECTED_VENUE = "selectedVenue";
    public static final String FRAGMENT_SETUP = "setup";
    public static final String FRAGMENT_DESIGN = "design";
    public static final String FRAGMENT_MORE = "more";

    //Intents
    public  static final int VENUESETUP_VALUE = 1010;
    public  static final int VENUEDESIGN_VALUE = 1011;
    public  static final int VENUEMORE_VALUE = 1012;
    public  static final String VENUESETUP_TAG = "setupIntent";
    public  static final String VENUEDESIGN_TAG = "designIntent";
    public  static final String VENUEMORE_TAG = "moreIntent";



    public static final String authorizedEntity = "kestone-57a68";
    public static final String scope = "GCM";


    public static MoreResponse moreResponse;
    public static int MorePosition;
    public static boolean isDebug = true;
}
