package thistle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import thistle.domain.Friendship;
import thistle.domain.User;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {

    @Query("SELECT f FROM Friendship f " +
            "WHERE f.leftUser = :user1 AND f.rightUser = :user2 OR " +
            "f.leftUser = :user2 AND f.rightUser = :user1")
    Optional<Friendship> find(@Param("user1") User user1, @Param("user2") User user2);

    @Query("SELECT f FROM Friendship f " +
            "WHERE (f.leftUser = :user OR f.rightUser = :user) " +
            "AND f.bias = 'MEDIUM'")
    List<Friendship> findFriends(@Param("user") User user);

    @Query("SELECT f FROM Friendship f " +
            "WHERE f.leftUser = :user AND f.bias = 'RIGHT' OR " +
            "f.rightUser = :user AND f.bias = 'LEFT'")
    List<Friendship> findIncomingRequests(@Param("user") User user);

    @Query("SELECT f FROM Friendship f " +
            "WHERE f.leftUser = :user AND f.bias = 'LEFT' OR " +
            "f.rightUser = :user AND f.bias = 'RIGHT'")
    List<Friendship> findOutgoingRequests(@Param("user") User user);
}
