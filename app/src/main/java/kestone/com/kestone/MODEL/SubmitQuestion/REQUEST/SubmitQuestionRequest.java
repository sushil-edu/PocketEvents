package kestone.com.kestone.MODEL.SubmitQuestion.REQUEST;

/**
 * Created by Karan Juneja on 11/27/2017.
 */

public class SubmitQuestionRequest {
    String UserID;
    String QuestionID;
    String InvoiceNo;
    String textValue;

    public SubmitQuestionRequest(String userID, String questionID, String invoiceNo, String textValue) {
        UserID = userID;
        QuestionID = questionID;
        InvoiceNo = invoiceNo;
        this.textValue = textValue;
    }
}
