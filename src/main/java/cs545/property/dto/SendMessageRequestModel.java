package cs545.property.dto;

import cs545.property.domain.Users;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class SendMessageRequestModel {
    String message;


    Long senderId;

    Long receiverId;
}
