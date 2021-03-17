/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import static javax.persistence.DiscriminatorType.STRING;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;


/**
 *
 * @author marcosbrasileiro
 */
@Entity
@Table (name="TB_USUARIOGERAL")
@Inheritance(strategy = InheritanceType.JOINED)
@Access(AccessType.FIELD)
@DiscriminatorColumn(name = "USUARIO_TIPO",discriminatorType = STRING, length = 1)

public abstract class UsuarioGeral implements Serializable {

@Id
@GeneratedValue (strategy = GenerationType.IDENTITY)
@Column (name="USUARIO_ID")
private Long id;


@Size(max=40)
@Column (name="USUARIO_NOME", length=40)
private String nome;


 @NotNull
 @Email
 @Column(name = "USUARIO_EMAIL", length = 30, nullable = false)
 protected String email;

@NotNull
@CPF
@Column(name="USUARIO_CPF")
protected String cpf;

    
    public boolean possui(String nome){
        return nome.contains(nome);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

 

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    
  @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UsuarioGeral)) {
            return false;
        }
        UsuarioGeral other = (UsuarioGeral) object;

        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }


   
  

}
