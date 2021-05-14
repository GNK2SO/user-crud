package br.com.manager.user.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.manager.phone.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 24)
    private String name;

    @Column(nullable = false, length = 64)
    private String email;

    @JsonIgnore
    @Column(nullable = false, length = 64)
    private String password;
    
    @OneToMany(fetch = FetchType.EAGER)
    private List<Phone> phones;

	public void add(Phone phone) {
		this.phones.add(phone);
	}

	public void remove(Phone phone) {
		this.phones.remove(phone);
	}

}
