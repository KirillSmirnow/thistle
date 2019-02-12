package thistle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import thistle.domain.Audio;
import thistle.domain.User;

public interface AudioRepository extends JpaRepository<Audio, Integer> {

    Page<Audio> findAllByOwner(User owner, Pageable pageable);
}
