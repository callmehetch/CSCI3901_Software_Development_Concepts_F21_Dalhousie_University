import java.util.ArrayList;
import java.util.List;

public class FileIdentifier {
    //Declaring the attributes required for a media file and reporting.
    private int mediaID;
    private String mediaFileLocation;
    private List<PersonIdentity> peopleInMedia;
    private String dateTaken;
    private String locationOfMedia;
    private List<String> tagsOfMedia;

    //FileIdentifier constructor to initialize fileLocation, peopleinMedia, tagsOfmedia.
    public FileIdentifier(String mediaFileLocation) {
        setMediaFileLocation(mediaFileLocation);
        peopleInMedia = new ArrayList<>();
        tagsOfMedia = new ArrayList<>();
    }

    //---------------------Getters And Setters For Encapsulation Of the Data Attributes---------------------------------
    public int getMediaID() {
        return mediaID;
    }

    public void setMediaID(int mediaID) {
        this.mediaID = mediaID;
    }

    public String getMediaFileLocation() {
        return mediaFileLocation;
    }

    public void setMediaFileLocation(String mediaFileLocation) {
        this.mediaFileLocation = mediaFileLocation;
    }

    public List<PersonIdentity> getPeopleInMedia() {
        return peopleInMedia;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getLocationOfMedia() {
        return locationOfMedia;
    }

    public void setLocationOfMedia(String locationOfMedia) {
        this.locationOfMedia = locationOfMedia;
    }

    public List<String> getTagsOfMedia() {
        return tagsOfMedia;
    }
    //---------------------Getters And Setters For Encapsulation Of the Data Attributes---------------------------------
}