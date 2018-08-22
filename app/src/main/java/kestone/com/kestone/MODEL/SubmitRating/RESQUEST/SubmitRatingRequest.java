package kestone.com.kestone.MODEL.SubmitRating.RESQUEST;

/**
 * Created by Karan Juneja on 11/28/2017.
 */

public class SubmitRatingRequest {
    String UserID;
    String Rating;
    String InvoiceNo;
    String RatingText;

    public SubmitRatingRequest(String userID, String rating, String invoiceNo, String ratingText) {
        UserID = userID;
        Rating = rating;
        InvoiceNo = invoiceNo;
        RatingText = ratingText;
    }
}
