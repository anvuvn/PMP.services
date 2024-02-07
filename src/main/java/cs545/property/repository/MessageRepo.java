package cs545.property.repository;

import cs545.property.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MessageRepo extends JpaRepository<Message, Long> {

    List<Message> findByReceiverIdOrderByCreatedDateDesc(Long userId);
    List<Message> findBySenderId(Long userId);
}
