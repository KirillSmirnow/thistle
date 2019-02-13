package thistle.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "vks")
@Getter
@NoArgsConstructor
public class Vk {

    @Id
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String lastName;

    @Size(max = 255)
    private String photo;

    public Vk(long id, User user, String firstName, String lastName, String photo) {
        this.id = id;
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo = photo;
    }
}
