package ro.example.lab.offers;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

public class Offer {

    @SerializedName(Constants.TITLE_API)
    private String title;

    @SerializedName(Constants.DESCRIPTION_API)
    private String description;

    @SerializedName(Constants.IMAGE_API)
    private String imageUrl;

    @SerializedName(Constants.INFO_POINTS_API)
    private String infoPoints;

    @SerializedName(Constants.REQUIRED_POINTS_API)
    private Integer requiredPoints;

    @SerializedName(Constants.GAINED_POINTS_API)
    private Integer gainedPoints;

    @SerializedName(Constants.START_DATE_API)
    private Calendar startDate;

    @SerializedName(Constants.END_DATE_API)
    private Calendar endDate;

    public Offer(String title, String description, String imageUrl, String infoPoints, Integer requiredPoints,
                 Integer gainedPoints, Calendar startDate, Calendar endDate) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.infoPoints = infoPoints;
        this.requiredPoints = requiredPoints;
        this.gainedPoints = gainedPoints;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getInfoPoints() {
        return infoPoints;
    }

    public void setInfoPoints(String infoPoints) {
        this.infoPoints = infoPoints;
    }

    public Integer getRequiredPoints() {
        return requiredPoints;
    }

    public void setRequiredPoints(Integer requiredPoints) {
        this.requiredPoints = requiredPoints;
    }

    public Integer getGainedPoints() {
        return gainedPoints;
    }

    public void setGainedPoints(Integer gainedPoints) {
        this.gainedPoints = gainedPoints;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }
}
