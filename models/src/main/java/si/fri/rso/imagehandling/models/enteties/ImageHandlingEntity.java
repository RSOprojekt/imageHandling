package si.fri.rso.imagehandling.models.enteties;


import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "image_data")
@NamedQueries(value = {
        @NamedQuery(name = "ImageHandlingEntity.getAll", query = "SELECT i from  ImageHandlingEntity i")
})

public class ImageHandlingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "image_title")
    private String imageTitle;

    @Column(name = "image_description")
    private String imageDescription;

    @Column(name = "image_height")
    private Integer imageHeight;

    @Column(name = "image_width")
    private Integer imageWidth;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "time_created")
    private Instant timeCreated;

    @Column(name = "time_changed")
    private Instant timeChanged;

    @Column(name = "image_file_format")
    private String imageFileFormat;

    /*@Column(name = "image_binary")
    private byte[] imageBinary;
    */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Instant getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Instant timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Instant getTimeChanged() {
        return timeChanged;
    }

    public void setTimeChanged(Instant timeChanged) {
        this.timeChanged = timeChanged;
    }

    public String getImageFileFormat() {
        return imageFileFormat;
    }

    public void setImageFileFormat(String imageFileFormat) {
        this.imageFileFormat = imageFileFormat;
    }

    /*public byte[] getImageBinary() {
        return imageBinary;
    }

    public void setImageBinary(byte[] imageBinary) {
        this.imageBinary = imageBinary;
    }*/
}
