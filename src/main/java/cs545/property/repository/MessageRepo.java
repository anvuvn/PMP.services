package cs545.property.repository;

import cs545.property.controller.MessageController;
import cs545.property.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


public interface MessageRepo extends JpaRepository<Message, Long> {

    List<Message> findByReceiverId(Long userId);
    List<Message> findBySenderId(Long userId);
}
