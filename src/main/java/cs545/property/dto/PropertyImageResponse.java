package cs545.property.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyImageResponse {
    private int id;
    private String fileName;
    private String fileUri;
    private String fileDownloadUri;
    private long fileSize;
    private String uploaderName;
}
