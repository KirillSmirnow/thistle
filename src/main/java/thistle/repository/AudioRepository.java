package thistle.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import thistle.domain.Audio;
import thistle.domain.User;

import java.util.Optional;

public interface AudioRepository extends JpaRepository<Audio, Integer> {

    Optional<Audio> findByOwnerAndMd5(User owner, String md5);

    Page<Audio> findAllByOwner(User owner, Pageable pageable);
}
