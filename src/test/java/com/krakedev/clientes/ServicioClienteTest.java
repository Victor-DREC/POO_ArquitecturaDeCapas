package com.krakedev.clientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.krakedev.clientes.entidades.Cliente;
import com.krakedev.clientes.services.ServicioCliente;

public class ServicioClienteTest {

    @Test
    public void crearClienteCorrectamente() {
        // Se prueba que un cliente nuevo se agregue correctamente.
        // Resultado esperado: el método crear retorna el mismo cliente.
        ServicioCliente servicio = new ServicioCliente();

        Cliente cliente = new Cliente();
        cliente.setCedula("0102030405");
        cliente.setNombre("Alexander");
        cliente.setApellido("Pilachanga");

        Cliente resultado = servicio.crear(cliente);

        assertEquals(cliente, resultado);
    }

    @Test
    public void crearClienteConCedulaDuplicada() {
        // Se prueba que no se permita crear otro cliente con la misma cédula.
        // Resultado esperado: el segundo intento retorna null.
        ServicioCliente servicio = new ServicioCliente();

        Cliente cliente1 = new Cliente();
        cliente1.setCedula("0102030405");
        cliente1.setNombre("Alexander");
        cliente1.setApellido("Pilachanga");

        Cliente cliente2 = new Cliente();
        cliente2.setCedula("0102030405");
        cliente2.setNombre("Juan");
        cliente2.setApellido("Perez");

        servicio.crear(cliente1);
        Cliente resultado = servicio.crear(cliente2);

        assertNull(resultado);
    }

    @Test
    public void buscarClientePorCedulaExistente() {
        // Se prueba la búsqueda de un cliente que sí existe en la lista.
        // Resultado esperado: retorna el cliente encontrado.
        ServicioCliente servicio = new ServicioCliente();

        Cliente cliente = new Cliente();
        cliente.setCedula("0102030405");
        cliente.setNombre("Alexander");
        cliente.setApellido("Pilachanga");

        servicio.crear(cliente);

        Cliente resultado = servicio.buscarPorCedula("0102030405");

        assertEquals(cliente, resultado);
    }

    @Test
    public void buscarClientePorCedulaInexistente() {
        // Se prueba la búsqueda de una cédula que no existe.
        // Resultado esperado: retorna null.
        ServicioCliente servicio = new ServicioCliente();

        Cliente cliente = new Cliente();
        cliente.setCedula("0102030405");
        cliente.setNombre("Alexander");
        cliente.setApellido("Pilachanga");

        servicio.crear(cliente);

        Cliente resultado = servicio.buscarPorCedula("9999999999");

        assertNull(resultado);
    }

    @Test
    public void listarClientesCorrectamente() {
        // Se prueba que listar() retorne todos los clientes registrados.
        // Resultado esperado: la lista contiene los dos clientes agregados.
        ServicioCliente servicio = new ServicioCliente();

        Cliente cliente1 = new Cliente();
        cliente1.setCedula("0102030405");
        cliente1.setNombre("Alexander");
        cliente1.setApellido("Pilachanga");

        Cliente cliente2 = new Cliente();
        cliente2.setCedula("0102030406");
        cliente2.setNombre("Juan");
        cliente2.setApellido("Perez");

        servicio.crear(cliente1);
        servicio.crear(cliente2);

        assertEquals(2, servicio.listar().size());
        assertEquals(cliente1, servicio.listar().get(0));
        assertEquals(cliente2, servicio.listar().get(1));
    }

    @Test
    public void actualizarNombreYApellidoClienteExistente() {
        // Se prueba que un cliente existente pueda actualizar su nombre y apellido.
        // Resultado esperado: conserva la cédula y cambia nombre y apellido.
        ServicioCliente servicio = new ServicioCliente();

        Cliente cliente = new Cliente();
        cliente.setCedula("0102030405");
        cliente.setNombre("Alexander");
        cliente.setApellido("Pilachanga");

        servicio.crear(cliente);

        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setCedula("0102030405");
        clienteActualizado.setNombre("Carlos");
        clienteActualizado.setApellido("Gomez");

        Cliente resultado = servicio.actualizar(
                "0102030405",
                clienteActualizado
        );

        assertEquals("0102030405", resultado.getCedula());
        assertEquals("Carlos", resultado.getNombre());
        assertEquals("Gomez", resultado.getApellido());
    }

    @Test
    public void actualizarClienteInexistente() {
        // Se prueba la actualización de un cliente que no existe.
        // Resultado esperado: el método retorna null.
        ServicioCliente servicio = new ServicioCliente();

        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setCedula("0102030405");
        clienteActualizado.setNombre("Carlos");
        clienteActualizado.setApellido("Gomez");

        Cliente resultado = servicio.actualizar(
                "9999999999",
                clienteActualizado
        );

        assertNull(resultado);
    }

    @Test
    public void eliminarClienteExistente() {
        // Se prueba la eliminación de un cliente que existe.
        // Resultado esperado: retorna true y el cliente deja de estar en la lista.
        ServicioCliente servicio = new ServicioCliente();

        Cliente cliente = new Cliente();
        cliente.setCedula("0102030405");
        cliente.setNombre("Alexander");
        cliente.setApellido("Pilachanga");

        servicio.crear(cliente);

        boolean resultado = servicio.eliminar("0102030405");

        assertTrue(resultado);
        assertNull(servicio.buscarPorCedula("0102030405"));
    }

    @Test
    public void eliminarClienteInexistente() {
        // Se prueba la eliminación de un cliente que no existe.
        // Resultado esperado: retorna false.
        ServicioCliente servicio = new ServicioCliente();

        boolean resultado = servicio.eliminar("9999999999");

        assertFalse(resultado);
    }
}