package kestone.com.kestone.Utilities;

import kestone.com.kestone.MODEL.More.RESPONSE.MoreResponse;

/**
 * Created by karan on 7/22/2017.
 */

public class CONSTANTS {


    private String BASEURL="http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc";
    public static final int RESULTCODE_COMPARE = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_STORAGE = 99;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 100;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 101;

    //URL  http://mobileapps.kestoneapps.com/eiab
    //URL2 http://mobileapps.kestoneapps.com/eiab

    //URLS
    public  static final String URL_GET_VENUE_FILTERS = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/getVenueFilters";
    public  static final String URL_GET_SAVED_EVENT_LIST = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/getSavedEventList";
    public  static final String URL_FORGOT_PASSWORD = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/sendForgotLink";
    public  static final String URL_LOGIN = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/login";
    public  static final String URL_GET_VENUE_LIST = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/getVenueList";
    public  static final String URL_INSERT_SIGNUP = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/InsertSignUP";
    public  static final String URL_SAVE_EVENT = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/saveEvent";
    public  static final String URL_SAVED_EVENT_FILTERS = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/SavedEventList";
    public  static final String URL_LOGOUT = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/logout";
    public  static final String URL_DELETE_EVENT = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/DeleteEvent";
    public  static final String URL_SETUP = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/SetupAPI";
    public  static final String URL_DESIGN = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/DesignAPI";
    public  static final String URL_DESIGN_EMAIL = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/DesignEmail";
    public  static final String URL_EVENTSEARCH = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/EventSearch";
    public  static final String URL_MORE = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/MoreAPI";
    public  static final String URL_CONSULTING_PRICE = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/GetPriceDetails";
    public  static final String URL_MYORDERS = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/MyOrder";
    public  static final String URL_EMAIL_INVOICE = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/InvoiceEmail";
    public  static final String URL_EMAIL_ALLEVENTS = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/EventDesignEmail";
    public  static final String URL_PAYMENT_API = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/PaymentDetails";
    public  static final String URL_CONTACT_US = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/GetContactUS";
    public  static final String URL_SUBMIT_QUERY = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/SubmitQuestion";
    public  static final String URL_SUBMIT_RATING = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/SubmitRating";
    public  static final String URL_GET_CITY = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/getCity";
    public  static final String URL_GET_PAYMENT_QUOTE = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/GetPaymentQuote";
    public  static final String URL_PAYMENT_SUCCESS = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/InsertInvoiceDetails";
    public  static final String URL_REWARDS_REFERRALS = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/ReferralReward";
    public  static final String URL_REFERRAL_REWARD_LIST = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/MyReferralRewardList";
    public  static final String URL_FOR_CHANGEPASSWORD = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/ChangePassword";
    public  static final String URL_FLOORPLAN = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/FlorePlanMail";
    public  static final String URL_FOR_REFFERAL_VALIDATION = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/RefferalValidation";
    public static  final String URL_Get_Consultancy_Info = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/getConsultancyinfo";
    public static  final String URL_Event_Search_Id = "http://mobileapps.kestoneapps.com/eiab/RestServiceImpl.svc/EventSearchId";
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
