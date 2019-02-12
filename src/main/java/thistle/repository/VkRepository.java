package thistle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import thistle.domain.User;
import thistle.domain.Vk;

import java.util.List;

public interface VkRepository extends JpaRepository<Vk, Long> {

    List<Vk> findAllByUser(User user);
}
