package com.rcintra.rinhabackendasync.pessoa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.Collections.emptyList;

@Table(name = "people")
@Entity
public class Pessoa implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    @Size(message = "Apelido deve ter entre 1 e 32 caracteres", min = 1, max = 32)
    private String nickname;
    @NotNull @Size(message = "Nome deve ter entre 1 e 100 caracteres", min = 1, max = 100)
    private String name;

    @NotNull(message = "A data de nascimento tem que estar no formado AAAA-MM-DD")
    private LocalDate birthDate;
    @Convert(converter = StringListConverter.class)
    private List<@Size(min = 1, max=32) String> stack;

    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private String search;

    public Pessoa() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<String> getStack() {
        return stack;
    }

    public void setStack(List<String> stack) {
        this.stack = stack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", stack=" + stack +
                '}';
    }
}

@Converter
class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return stringList != null ? String.join(SPLIT_CHAR, stringList) : "";
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        return string != null ? Arrays.asList(string.split(SPLIT_CHAR)) : emptyList();
    }
}
