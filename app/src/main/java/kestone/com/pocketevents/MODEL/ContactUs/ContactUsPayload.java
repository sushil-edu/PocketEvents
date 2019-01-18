package kestone.com.pocketevents.MODEL.ContactUs;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by karan on 11/6/2017.
 */

public class ContactUsPayload implements Serializable {
    @SerializedName("Question")
    String Question;

    @SerializedName("QuestionId")
    String QuestionId;

    @SerializedName("Invoice_required")
    String Invoice_required;

    @SerializedName("Text_required")
    String Text_required;




    boolean expanded = false;

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(String questionId) {
        QuestionId = questionId;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getInvoice_required() {
        return Invoice_required;
    }

    public void setInvoice_required(String invoice_required) {
        Invoice_required = invoice_required;
    }

    public String getText_required() {
        return Text_required;
    }

    public void setText_required(String text_required) {
        Text_required = text_required;
    }
}
