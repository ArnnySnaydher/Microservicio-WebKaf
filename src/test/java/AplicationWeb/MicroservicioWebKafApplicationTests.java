package AplicationWeb;

import AplicationWeb.model.Producto;
import AplicationWeb.service.ProductosService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class MicroservicioWebKafApplicationTests {

	@Autowired
	ProductosService productosService;
	
	@Test
	@Order(1)
	void testProductosCategoria() {
		StepVerifier.create(productosService.productosCategoria("Alimentación"))
				.expectNextMatches(product -> product.getNombre().equals("Azucar"))
				.expectNextMatches(product -> product.getNombre().equals("Leche"))
				.expectNextMatches(product -> product.getNombre().equals("Huevos"))
				.verifyComplete()
		;
	}

	@Test
	@Order(2)
	void testEliminarProducto() {
		StepVerifier.create(productosService.eliminarProducto(103))
				.expectNextMatches(product -> product.getNombre().equals("Mesa"))
				.verifyComplete();
	}

	@Test
	@Order(3)
	void testAltaProducto() {
		Producto producto = new Producto(250,"ptest","cat1",10,2);
		StepVerifier.create(productosService.altaProducto(producto))
				.expectComplete()
				.verify();
	}
	
	@Test
	@Order(4)
	void testCatalogo(){
		StepVerifier.create(productosService.catalogo())
				.expectNextCount(8)
				.verifyComplete();
	}

}
