package com.softuni.bloodcentre.data.models;

import com.softuni.bloodcentre.data.models.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "departments")
public class Department extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToMany(mappedBy = "departments", cascade = CascadeType.ALL)
    private List<User> users;
}
