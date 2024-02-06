package cs545.property.dto;

public class ThumbsImageDataResponse {
    private Long pid;
    private byte[] image;
    public ThumbsImageDataResponse(Long id, byte[] image){
        this.pid = id;
        this.image = image;
    }
}
