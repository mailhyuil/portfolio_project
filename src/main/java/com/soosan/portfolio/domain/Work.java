package com.soosan.portfolio.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "works")
public class Work {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String content;
    private String category;

    @OneToMany(mappedBy = "work", fetch = FetchType.LAZY)
    private List<Designer> designerList = new ArrayList<>();

    @OneToMany(mappedBy = "work", fetch = FetchType.LAZY)
    private List<Client> clientList = new ArrayList<>();

    @OneToMany(mappedBy = "work", fetch = FetchType.LAZY)
    private List<Collaborator> collaboratorList = new ArrayList<>();

    @OneToMany(mappedBy = "work", fetch = FetchType.LAZY)
    private List<Image> imageList = new ArrayList<>();
}
