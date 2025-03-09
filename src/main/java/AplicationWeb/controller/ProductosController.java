package AplicationWeb.controller;

import AplicationWeb.model.Producto;
import AplicationWeb.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductosController {
    @Autowired
    private ProductosService productosService;

    @GetMapping(value = "productos")
    public Flux<Producto> productos(){
        return productosService.catalogo();
    }
    
    @GetMapping(value = "prodcutos/{categoria}")
    public Flux<Producto> producttsCategoria(@PathVariable("categoria") String categoria){
        return productosService.productosCategoria(categoria);
    }
    
    @GetMapping(value = "producto")
    public Mono<Producto> productoCodigo(@RequestParam("cod") int cod){
        return productosService.productoCodigo(cod);
    }
    
    @PostMapping(value = "alta", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> altaProducto(@RequestBody Producto producto){
        return productosService.altaProducto(producto);
    }
    
    @DeleteMapping(value = "eliminar")
    public Mono<Producto> eliminarProducto(@RequestParam("cod") int cod){
        return productosService.eliminarProducto(cod);
    }
    
    @PutMapping(value = "actualizar")
    public Mono<Producto> actualizarProducto(@RequestParam("cod") int cod,@RequestParam("precio") Double precio){
        return productosService.actualizarPrecio(cod,precio);
    }
}
