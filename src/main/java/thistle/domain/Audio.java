package thistle.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "audios", uniqueConstraints = @UniqueConstraint(columnNames = {"owner_id", "md5"}))
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Audio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User owner;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    @NotBlank
    @Size(max = 32)
    private String md5;

    public Audio(User owner, String name, String md5) {
        this.owner = owner;
        this.name = name;
        this.md5 = md5;
    }

    public void update(String name) {
        this.name = name;
    }
}
