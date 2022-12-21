package by.vsu.attendance.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "room")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ToString.Exclude
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Place> places = new ArrayList<>();

    @Positive
    @Column(name = "number", nullable = false, unique = true)
    private int number;

    @Column(name = "capacity")
    private int capacity;

    @Positive
    @Column(name = "floor", nullable = false)
    private int floor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Room room = (Room) o;
        return id != null && Objects.equals(id, room.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
