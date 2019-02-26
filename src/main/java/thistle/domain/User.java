package thistle.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String lastName;

    @Size(max = 128)
    private String accessToken;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void update(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String updateAccessToken() {
        RandomValueStringGenerator stringGenerator = new RandomValueStringGenerator(128);
        accessToken = stringGenerator.generate();
        return accessToken;
    }

    protected void setId(Integer id) {
        this.id = id;
    }
}
