package descorp.jpa.validation;

import descorp.jpa.Administrador;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 *
 * @author MASC
 */
public class AdministradorValidationTest extends Teste {
    @Test(expected = ConstraintViolationException.class)
    public void persistirAdministradorInvalido() {
        Administrador adm = null;
        try {
            adm = new Administrador();
            adm.setCpf("257"); //CPF inválido                       
            adm.setEmail("email_invalido"); //E-mail inválido
          
           
            em.persist(adm);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.jpa.Administrador: Não é um endereço de e-mail"),
                                startsWith("class descorp.jpa.Administrador: CPF inválido"),
                                startsWith("class descorp.jpa.Administrador: Administrador precisa da permissão")
                                
                        )
                );
            });

            assertEquals(3, constraintViolations.size());
            assertNull(adm.getId());
            throw ex;
        }
    }
  
   @Test(expected = ConstraintViolationException.class)
    public void atualizarEmailAdministradorInvalido() {
        TypedQuery<Administrador> query = em.createQuery("SELECT a FROM UsuarioGeral a WHERE a.cpf like :cpf", Administrador.class);
        query.setParameter("cpf", "438.727.770-09");
        Administrador adm = query.getSingleResult();
        adm.setEmail("testeADM");

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {    
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("Não é um endereço de e-mail", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
     @Test(expected = ConstraintViolationException.class)
    public void atualizarCPFAdministradorInvalido() {
        TypedQuery<Administrador> query = em.createQuery("SELECT a FROM UsuarioGeral a WHERE a.cpf like :cpf", Administrador.class);
        query.setParameter("cpf", "438.727.770-09");
        Administrador adm = query.getSingleResult();
        adm.setCpf("438.727.770-0");

        try {
            em.flush();
        } catch (ConstraintViolationException ex) {    
            ConstraintViolation violation = ex.getConstraintViolations().iterator().next();
            assertEquals("CPF inválido", violation.getMessage());
            assertEquals(1, ex.getConstraintViolations().size());
            throw ex;
        }
    }
}
