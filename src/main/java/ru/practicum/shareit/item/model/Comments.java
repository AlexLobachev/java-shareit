package ru.practicum.shareit.item.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.exteption.NullAllowed;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "comments")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "comments")
    @NotBlank
    private String text;
    @Column(name = "author_name")
    private String authorName;
    @ManyToOne
    private Item item;
}
