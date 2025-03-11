package AplicationWeb.service;

import AplicationWeb.model.Producto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductosServiceImpl implements ProductosService{

    private static List<Producto> productos=new ArrayList<>(List.of(new Producto(100,"Azucar","Alimentación",1.10,20),
            new Producto(101,"Leche","Alimentación",1.20,15),
            new Producto(102,"Jabón","Limpieza",0.89,30),
            new Producto(103,"Mesa","Hogar",125,4),
            new Producto(104,"Televisión","Hogar",650,10),
            new Producto(105,"Huevos","Alimentación",2.20,30),
            new Producto(106,"Fregona","Limpieza",3.40,6),
            new Producto(107,"Detergente","Limpieza",8.7,12)));
    
    @Override
    public Flux<Producto> catalogo() {
        return Flux.fromIterable(productos)
                .delayElements(Duration.ofSeconds(1/2));
    }

    @Override
    public Flux<Producto> productosCategoria(String categoria) {
        return catalogo()
                .filter(producto -> producto.getCategoria().equals(categoria));
    }

    @Override
    public Mono<Producto> productoCodigo(int cod) {
        return catalogo()//Flux<Producto>
                .filter(producto -> producto.getCodProducto()==cod)//Flux<Producto>
                .next();//Mono<Producto>
    }

    @Override
    public Mono<Void> altaProducto(Producto producto) {
        return productoCodigo(producto.getCodProducto())//Mono<Producto>
                .switchIfEmpty(Mono.just(producto).map( producto1 -> {productos.add(producto);
                return producto1;
                }))//Mono<Producto>
                .then();//Mono<Void>
    }

    @Override
    public Mono<Producto> eliminarProducto(int cod) {
        return productoCodigo(cod)//Mono<Producto>
                .map(producto -> {productos.removeIf(producto1 -> producto1.getCodProducto()==cod);
                return producto;
                });//Mono<Producto>
    }

    @Override
    public Mono<Producto> actualizarPrecio(int cod, double precio) {
        
        return productoCodigo(cod) //MonoProducto
                .map( producto -> {producto.setPrecioUnitario(precio); 
                    return producto;
                });
    }
}
