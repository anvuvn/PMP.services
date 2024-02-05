package cs545.property.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PropertyImage {
    @Id
    @GeneratedValue
    private int id;

    private String fileName;

    private String fileUri;

    private String fileDownloadUri;

    private long fileSize;

    private String uploaderName;

    public PropertyImage(String fileName, String fileUri, String fileDownloadUri, long fileSize, String uploaderName) {
        this.fileName = fileName;
        this.fileUri = fileUri;
        this.fileDownloadUri = fileDownloadUri;
        this.fileSize = fileSize;
        this.uploaderName = uploaderName;
    }
}
