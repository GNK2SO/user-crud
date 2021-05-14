package br.com.manager.phone.model;

import static java.lang.String.format;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "phones")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phone {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private Integer ddd;
	
	private String number; 
	
	@Enumerated(EnumType.STRING)
    private PhoneType type;
    
    public enum PhoneType {
    	PERSONAL, RESIDENTIAL;
    }
    
    public String getFormatedNumber() {
    	return format("(%d) %s", ddd, number);
    }
}
