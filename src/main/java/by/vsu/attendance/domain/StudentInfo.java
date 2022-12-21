package by.vsu.attendance.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "studentInfo")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ToString.Exclude
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @Range(min = 1, max = 4)
    @Column(name = "course", nullable = false)
    private int course;

    @NotEmpty
    @Column(name = "account_id", nullable = false, unique = true)
    private String accountId;

    @NotEmpty
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotEmpty
    @Column(name = "faculty")
    private String faculty;

    @Positive
    @Column(name = "group", nullable = false)
    private int group;

    @NotNull
    @Past
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StudentInfo student = (StudentInfo) o;
        return id != null && Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}