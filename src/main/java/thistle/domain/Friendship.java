package thistle.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thistle.exception.ThistleException;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Each object of {@link Friendship} class represents a relationship between two {@link User}s. <br/>
 *
 * <ul>
 * Consistency requirements:
 * <li>uniqueness of pair ({@link #leftUser}, {@link #rightUser})</li>
 * <li>{@link #leftUser}.id < {@link #rightUser}.id</li>
 * </ul>
 *
 * <ul>
 * Depending on {@link #bias}, {@link Friendship} can be in one of 3 states:
 * <li>
 * {@link Bias#LEFT} <br/>
 * {@link #leftUser} follows {@link #rightUser}, {@link #rightUser} doesn't follow {@link #leftUser}
 * </li>
 * <li>
 * {@link Bias#RIGHT} <br/>
 * {@link #rightUser} follows {@link #leftUser}, {@link #leftUser} doesn't follow {@link #rightUser}
 * </li>
 * <li>
 * {@link Bias#MEDIUM} <br/>
 * {@link #leftUser} follows {@link #rightUser}, {@link #rightUser} follows {@link #leftUser},
 * in other words they are friends
 * </li>
 * </ul>
 */

@Entity
@Table(name = "friendships", uniqueConstraints = @UniqueConstraint(columnNames = {"left_user_id", "right_user_id"}))
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private User leftUser;

    @ManyToOne(optional = false)
    private User rightUser;

    @NotNull
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Bias bias;

    private Friendship(User leftUser, User rightUser, Bias bias) {
        this.leftUser = leftUser;
        this.rightUser = rightUser;
        this.bias = bias;
    }

    public static Friendship create(User follower, User followee) {
        if (follower.getId() < followee.getId()) {
            return new Friendship(follower, followee, Bias.LEFT);
        }
        if (follower.getId() > followee.getId()) {
            return new Friendship(followee, follower, Bias.RIGHT);
        }
        throw new ThistleException("Self-follow is not allowed");
    }

    public void followBy(User follower) {
        if (follower.equals(leftUser) && bias == Bias.RIGHT ||
                follower.equals(rightUser) && bias == Bias.LEFT) {
            bias = Bias.MEDIUM;
        }
    }

    /**
     * @return true if there is no relationship between users left and the object must be destroyed,
     * false otherwise
     */
    public boolean unfollowBy(User unfollower) {
        if (bias == Bias.MEDIUM) {
            bias = unfollower.equals(leftUser) ? Bias.RIGHT : Bias.LEFT;
            return false;
        }
        return unfollower.equals(leftUser) && bias == Bias.LEFT ||
                unfollower.equals(rightUser) && bias == Bias.RIGHT;
    }

    public User getUserExcluding(User exclusion) {
        return leftUser.equals(exclusion) ? rightUser : leftUser;
    }

    public enum Bias {
        LEFT,
        MEDIUM,
        RIGHT
    }
}
