/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package descorp.jpa.validation;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author masc1
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({descorp.jpa.validation.ClienteValidationTest.class,descorp.jpa.validation.AdministradorValidationTest.class,descorp.jpa.validation.CorProdutoValidationTest.class,
descorp.jpa.validation.TamanhoProdutoValidationTest.class,descorp.jpa.validation.PedidoValidationTest.class,descorp.jpa.validation.TipoProdutoValidationTest.class,descorp.jpa.validation.CartaoCreditoValidationTest.class
})
public class TesteSuite {
    
}
