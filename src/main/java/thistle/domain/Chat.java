package thistle.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "chats")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Chat {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User admin;

    /**
     * Collection of all members of {@link Chat}.
     * Includes {@link #admin}.
     * Each member is included precisely once.
     */
    @ManyToMany
    @UniqueElements
    private List<User> members;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String name;

    public Chat(User admin, String name, List<User> members) {
        this.id = randomUUID();
        this.admin = admin;
        this.name = name;
        this.members = adapt(members);
    }

    public void update(String name, List<User> members) {
        this.name = name;
        this.members = adapt(members);
    }

    private List<User> adapt(List<User> members) {
        members.add(admin);
        return members.stream().distinct().collect(toList());
    }
}
