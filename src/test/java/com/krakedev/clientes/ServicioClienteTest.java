package com.krakedev.clientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.krakedev.clientes.entidades.Cliente;
import com.krakedev.clientes.services.ServicioCliente;

/**
 * Pruebas unitarias para ServicioCliente.
 * No se usa @BeforeEach: cada test crea su propia instancia de
 * ServicioCliente (su propio "directorio" en memoria) para garantizar
 * total independencia entre pruebas.
 */
class ServicioClienteTest {

    // ---------- crear() ----------

    @Test
    void crear_debeAgregarCliente_cuandoCedulaNoExiste() {
        // Qué se prueba: crear() con una cédula nueva debe agregar el cliente
        // y devolver el mismo objeto agregado.
        // Resultado esperado: el cliente retornado es igual al enviado y
        // la lista queda con exactamente 1 elemento.
        ServicioCliente servicio = new ServicioCliente();

        Cliente cliente = new Cliente();
        cliente.setCedula("1001");
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");
        cliente.setEmail("juan.perez@test.com");

        Cliente resultado = servicio.crear(cliente);

        assertEquals(cliente, resultado);
        assertEquals(1, servicio.listar().size());
    }

    @Test
    void crear_debeRetornarNull_cuandoCedulaYaExiste() {
        // Qué se prueba: crear() con una cédula que ya está registrada
        // no debe agregar un segundo cliente.
        // Resultado esperado: retorna null y la lista sigue con 1 elemento
        // (el original, sin modificar).
        ServicioCliente servicio = new ServicioCliente();

        Cliente original = new Cliente();
        original.setCedula("1002");
        original.setNombre("Maria");
        original.setApellido("Lopez");
        original.setEmail("maria.lopez@test.com");
        servicio.crear(original);

        Cliente duplicado = new Cliente();
        duplicado.setCedula("1002");
        duplicado.setNombre("Otro");
        duplicado.setApellido("Nombre");
        duplicado.setEmail("otro@test.com");

        Cliente resultado = servicio.crear(duplicado);

        assertNull(resultado);
        assertEquals(1, servicio.listar().size());
    }

    // ---------- buscarPorCedula() ----------

    @Test
    void buscarPorCedula_debeRetornarCliente_cuandoExiste() {
        // Qué se prueba: buscarPorCedula() con una cédula existente.
        // Resultado esperado: retorna el cliente correcto (mismos datos).
        ServicioCliente servicio = new ServicioCliente();

        Cliente cliente = new Cliente();
        cliente.setCedula("1003");
        cliente.setNombre("Carlos");
        cliente.setApellido("Ramirez");
        cliente.setEmail("carlos.ramirez@test.com");
        servicio.crear(cliente);

        Cliente encontrado = servicio.buscarPorCedula("1003");

        assertEquals(cliente, encontrado);
        assertEquals("Carlos", encontrado.getNombre());
    }

    @Test
    void buscarPorCedula_debeRetornarNull_cuandoNoExiste() {
        // Qué se prueba: buscarPorCedula() cuando la cédula no está registrada.
        // Resultado esperado: retorna null.
        ServicioCliente servicio = new ServicioCliente();

        Cliente resultado = servicio.buscarPorCedula("9999");

        assertNull(resultado);
    }

    // ---------- listar() ----------

    @Test
    void listar_debeRetornarListaVacia_cuandoNoHayClientes() {
        // Qué se prueba: listar() en un servicio recién creado, sin clientes.
        // Resultado esperado: la lista existe y está vacía (tamaño 0).
        ServicioCliente servicio = new ServicioCliente();

        List<Cliente> resultado = servicio.listar();

        assertEquals(0, resultado.size());
    }

    @Test
    void listar_debeRetornarTodosLosClientesAgregados() {
        // Qué se prueba: listar() luego de agregar varios clientes.
        // Resultado esperado: la lista contiene la misma cantidad de
        // clientes que fueron creados exitosamente.
        ServicioCliente servicio = new ServicioCliente();

        Cliente cliente1 = new Cliente();
        cliente1.setCedula("2001");
        cliente1.setNombre("Ana");
        cliente1.setApellido("Torres");
        cliente1.setEmail("ana.torres@test.com");

        Cliente cliente2 = new Cliente();
        cliente2.setCedula("2002");
        cliente2.setNombre("Luis");
        cliente2.setApellido("Gomez");
        cliente2.setEmail("luis.gomez@test.com");

        servicio.crear(cliente1);
        servicio.crear(cliente2);

        List<Cliente> resultado = servicio.listar();

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(cliente1));
        assertTrue(resultado.contains(cliente2));
    }

    // ---------- actualizar() ----------

    @Test
    void actualizar_debeModificarDatos_cuandoClienteExiste() {
        // Qué se prueba: actualizar() con una cédula existente debe
        // modificar nombre, apellido y email del cliente encontrado.
        // Resultado esperado: el cliente retornado (y el almacenado)
        // refleja los nuevos datos, y la cédula no cambia.
        ServicioCliente servicio = new ServicioCliente();

        Cliente cliente = new Cliente();
        cliente.setCedula("3001");
        cliente.setNombre("Pedro");
        cliente.setApellido("Diaz");
        cliente.setEmail("pedro.diaz@test.com");
        servicio.crear(cliente);

        Cliente datosActualizados = new Cliente();
        datosActualizados.setNombre("Pedro Antonio");
        datosActualizados.setApellido("Diaz Mora");
        datosActualizados.setEmail("pedro.nuevo@test.com");

        Cliente resultado = servicio.actualizar("3001", datosActualizados);

        assertEquals("Pedro Antonio", resultado.getNombre());
        assertEquals("Diaz Mora", resultado.getApellido());
        assertEquals("pedro.nuevo@test.com", resultado.getEmail());
        assertEquals("3001", resultado.getCedula());
    }

    @Test
    void actualizar_debeRetornarNull_cuandoClienteNoExiste() {
        // Qué se prueba: actualizar() con una cédula que no está registrada.
        // Resultado esperado: retorna null y no se agrega ningún cliente.
        ServicioCliente servicio = new ServicioCliente();

        Cliente datosActualizados = new Cliente();
        datosActualizados.setNombre("Nombre");
        datosActualizados.setApellido("Apellido");
        datosActualizados.setEmail("no.existe@test.com");

        Cliente resultado = servicio.actualizar("4004", datosActualizados);

        assertNull(resultado);
        assertEquals(0, servicio.listar().size());
    }

    // ---------- eliminar() ----------

    @Test
    void eliminar_debeRetornarTrueYQuitarCliente_cuandoExiste() {
        // Qué se prueba: eliminar() con una cédula existente.
        // Resultado esperado: retorna true y el cliente ya no aparece
        // en listar() ni en buscarPorCedula().
        ServicioCliente servicio = new ServicioCliente();

        Cliente cliente = new Cliente();
        cliente.setCedula("5001");
        cliente.setNombre("Sofia");
        cliente.setApellido("Vega");
        cliente.setEmail("sofia.vega@test.com");
        servicio.crear(cliente);

        boolean resultado = servicio.eliminar("5001");

        assertTrue(resultado);
        assertEquals(0, servicio.listar().size());
        assertNull(servicio.buscarPorCedula("5001"));
    }

    @Test
    void eliminar_debeRetornarFalse_cuandoClienteNoExiste() {
        // Qué se prueba: eliminar() con una cédula que no está registrada.
        // Resultado esperado: retorna false y la lista no se modifica.
        ServicioCliente servicio = new ServicioCliente();

        Cliente cliente = new Cliente();
        cliente.setCedula("6001");
        cliente.setNombre("Elena");
        cliente.setApellido("Rios");
        cliente.setEmail("elena.rios@test.com");
        servicio.crear(cliente);

        boolean resultado = servicio.eliminar("9999");

        assertFalse(resultado);
        assertEquals(1, servicio.listar().size());
    }
}