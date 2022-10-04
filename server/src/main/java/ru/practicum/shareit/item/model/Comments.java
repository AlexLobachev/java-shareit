package ru.practicum.shareit.item.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "comments")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@AllArgsConstructor
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comments")
    private String text;
    @Column(name = "author_name")
    private String authorName;
    @ManyToOne
    private Item item;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comments comments = (Comments) o;
        return Objects.equals(id, comments.id) && Objects.equals(text, comments.text) && Objects.equals(authorName, comments.authorName) && Objects.equals(item, comments.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, authorName, item);
    }
}
