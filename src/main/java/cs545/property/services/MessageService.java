package cs545.property.services;

import cs545.property.domain.Message;
import cs545.property.dto.MessageDto;
import cs545.property.dto.SendMessageRequestModel;
import cs545.property.repository.MessageRepo;
import cs545.property.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageService {
    @Autowired
    MessageRepo messageRepo;

    @Autowired
    UserRepository userRepository;

    public List<MessageDto> getMessageByReceiver(Long receiverId) {
        return messageRepo.findByReceiverIdOrderByCreatedDateDesc(receiverId).stream().map(m -> new MessageDto(m)).toList();
    }

    public MessageDto sendMessage(SendMessageRequestModel model) {
        var msg = new Message();
        if (model.getReceiverId().equals(model.getSenderId())) {
            throw new RuntimeException("Sender must be differ from receiver");
        }
        var sender = userRepository.getReferenceById(model.getSenderId());
        var receiver = userRepository.getReferenceById(model.getReceiverId());
        if (sender == null) {
            throw new RuntimeException("Sender not exist");
        }
        if (receiver == null) {
            throw new RuntimeException("receiver not exist");
        }
        msg.setReceiver(receiver);
        msg.setSender(sender);
        msg.setMessage(model.getMessage());
        messageRepo.save(msg);
        return new MessageDto(msg);
    }
}
