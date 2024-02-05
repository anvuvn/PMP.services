package cs545.property.dto;

import cs545.property.domain.Message;
import lombok.Data;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MessageDto {

    private String senderName;
    private Long senderId;
    private String receiverName;
    private Long receiverId;
    private String message;
    private LocalDateTime createdDate;
    public MessageDto(Message message){
        this.message = message.getMessage();
        senderName = message.getSender().getName();
        senderId = message.getSender().getId();

        receiverName = message.getReceiver().getName();
        receiverId = message.getReceiver().getId();
        createdDate = message.getCreatedDate();
    }

}
