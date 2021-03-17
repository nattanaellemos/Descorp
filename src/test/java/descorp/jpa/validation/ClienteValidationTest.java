package descorp.jpa.validation;

import descorp.jpa.ClienteUsuario;
import descorp.jpa.EnderecoCliente;
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
 * @author NATANAEL.JUNIOR
 */
public class ClienteValidationTest extends Teste
{
    @Test(expected = ConstraintViolationException.class)
    public void persistirClienteInvalido() {
        ClienteUsuario cliente = null;
        Calendar calendar = new GregorianCalendar();
        try {
            cliente = new ClienteUsuario();
            cliente.setCpf("258"); //CPF inválido
            calendar.set(2025, Calendar.JANUARY, 1);
            //Data de nascimento inválida            
            cliente.setDataNascimento(calendar.getTime());
            cliente.setEmail("email_invalido"); //E-mail inválido
            EnderecoCliente endereco = cliente.criarEndereco();
            endereco.setBairro("Janga");
            endereco.setCep("33");
            endereco.setPais("ZAS");//País inválido
            endereco.setEstado("PP");
            endereco.setNumero("33");
            endereco.setNome("Rua do teste");
            endereco.setCidade("Recife");
            endereco.setComplemento("Casa");
            em.persist(cliente);
            em.flush();
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

            constraintViolations.forEach(violation -> {
                assertThat(violation.getRootBeanClass() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class descorp.jpa.ClienteUsuario: Não é um endereço de e-mail"),
                                startsWith("class descorp.jpa.ClienteUsuario: Celular não pode ser null"),
                                startsWith("class descorp.jpa.ClienteUsuario: Telefone fixo não pode ser null/vazio"),
                                startsWith("class descorp.jpa.ClienteUsuario: CPF inválido"),
                                startsWith("class descorp.jpa.ClienteUsuario: deve estar no passado"),
                                startsWith("class descorp.jpa.ClienteUsuario: Endereço não pode ser null"),
                                startsWith("class descorp.jpa.ClienteUsuario: tamanho deve estar entre 0 e 2"),
                                startsWith("class descorp.jpa.ClienteUsuario: País inválido")
                        )
                );
            });

            assertEquals(7, constraintViolations.size());
            assertNull(cliente.getId());
            throw ex;
        }
    }
   @Test(expected = ConstraintViolationException.class)
    public void atualizarEmailClienteInvalido() {
        TypedQuery<ClienteUsuario> query = em.createQuery("SELECT c FROM UsuarioGeral c WHERE c.cpf like :cpf", ClienteUsuario.class);
        query.setParameter("cpf", "931.176.830-89");
        ClienteUsuario cliente = query.getSingleResult();
        cliente.setEmail("teste");

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
    public void atualizarCPFClienteInvalido() {
        TypedQuery<ClienteUsuario> query = em.createQuery("SELECT c FROM UsuarioGeral c WHERE c.cpf like :cpf", ClienteUsuario.class);
        query.setParameter("cpf", "931.176.830-89");
        ClienteUsuario cliente = query.getSingleResult();
        cliente.setCpf("931.176.830-8");

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
