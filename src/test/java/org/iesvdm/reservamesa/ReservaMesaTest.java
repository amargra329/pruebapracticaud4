package org.iesvdm.reservamesa;

//import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class ReservaMesaTest {

    @Test
    void rellenarMesas_Test() {
        ReservaMesa reserva = new ReservaMesa();
        int tamano = 2;
        int mesas = 2;

        reserva.setMesas(new int[mesas]);
        reserva.setTamanioMesa(tamano);

        reserva.rellenarMesas();

        for (int i = 0; i < mesas; i++ ) {
            assertThat(reserva.getMesas()[i] > 0);
        }
    }

    @Test
    void imprimir_Test() {

    }

    @Test
    void buscarPrimeraMesaVacia_Test() {
        ReservaMesa reserva = new ReservaMesa();
        int tamano = 5;
        int mesas = 10;
        Random random = new Random();
        int mesaAVaciar = random.nextInt(mesas);

        reserva.setMesas(new int[mesas]);
        reserva.setTamanioMesa(tamano);

        reserva.rellenarMesas();

        int[] mesasTPM = reserva.getMesas();
        mesasTPM[mesaAVaciar] = 0;

        assertThat(reserva.buscarPrimeraMesaVacia()).isEqualTo(mesaAVaciar);
    }

    @Test
    void buscarMesaParaCompartir_Test() {
        ReservaMesa reserva = new ReservaMesa();
        int tamano = 5;
        int nMesas = 10;
        int nPersonas = 2;

        reserva.setMesas(new int[nMesas]);
        reserva.setTamanioMesa(tamano);
        reserva.rellenarMesas();

        int[] mesasTPM = reserva.getMesas();
        mesasTPM[2] = 3; // creo la posibilidad de añadir 2 en almenos una mesa

        assertThat(reserva.buscarMesaParaCompartir(nPersonas)).isPositive();

        // Ocupo todas las mesas
        int[] mesasCompetas = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
        reserva.setMesas(mesasCompetas);

        assertThat(reserva.buscarMesaParaCompartir(nPersonas)).isNegative();
    }

    @Test
    void buscarMesaCompartirMasCercaDe_Test() {
        ReservaMesa reserva = new ReservaMesa();
        int tamano = 5;
        int nMesas = 5;
        int nPersonas = 2;
        int mesaBuscada = 2;

        int[] mesas = new int[nMesas];
        mesas[0] = 5;
        mesas[1] = 3; // espacio objetivo
        mesas[2] = 4; // buscando
        mesas[3] = 4;
        mesas[4] = 3; // espacio
        reserva.setMesas(mesas);
        reserva.setTamanioMesa(tamano);

        assertThat(reserva.buscarMesaCompartirMasCercaDe(mesaBuscada, nPersonas)).isEqualTo(1);

        mesas[0] = 3; // espacio
        mesas[1] = 5;
        mesas[2] = 4; // buscando
        mesas[3] = 3; // espacio objetivo
        mesas[4] = 5;
        reserva.setMesas(mesas);

        assertThat(reserva.buscarMesaCompartirMasCercaDe(mesaBuscada, nPersonas)).isEqualTo(3);

        mesas[0] = 4;
        mesas[1] = 5;
        mesas[2] = 4; // buscando
        mesas[3] = 5;
        mesas[4] = 5;
        reserva.setMesas(mesas);

        assertThat(reserva.buscarMesaCompartirMasCercaDe(mesaBuscada, nPersonas)).isNegative();
    }

    @Test
    void buscarMesaCompartirMasAlejadaDe_Test() {
        ReservaMesa reserva = new ReservaMesa();
        int tamano = 5;
        int nMesas = 5;
        int nPersonas = 2;
        int mesaBuscada = 2;

        int[] mesas = new int[nMesas];
        mesas[0] = 5;
        mesas[1] = 3; // espacio
        mesas[2] = 3; // buscando
        mesas[3] = 4;
        mesas[4] = 3; // espacio objetivo
        reserva.setMesas(mesas);
        reserva.setTamanioMesa(tamano);

        assertThat(reserva.buscarMesaCompartirMasAlejadaDe(mesaBuscada, nPersonas)).isEqualTo(4);

        mesas[0] = 3; // espacio objetivo
        mesas[1] = 5;
        mesas[2] = 4; // buscando
        mesas[3] = 3; // espacio
        mesas[4] = 5;
        reserva.setMesas(mesas);
        reserva.setTamanioMesa(tamano);

        assertThat(reserva.buscarMesaCompartirMasAlejadaDe(mesaBuscada, nPersonas)).isEqualTo(0);

        mesas[0] = 4;
        mesas[1] = 5;
        mesas[2] = 4; // buscando
        mesas[3] = 5;
        mesas[4] = 5;
        reserva.setMesas(mesas);

        assertThat(reserva.buscarMesaCompartirMasCercaDe(mesaBuscada, nPersonas)).isNegative();
    }

    @Test
    void buscarCompartirNMesasConsecutivas() {
        ReservaMesa reserva = new ReservaMesa();
        int tamano = 5;
        int nMesas = 5;
        int nPersonas = 5;

        int[] mesas = new int[nMesas];
        mesas[0] = 3; // espacio
        mesas[1] = 3; // espacio
        mesas[2] = 4; // espacio
        mesas[3] = 5;
        mesas[4] = 5;
        reserva.setMesas(mesas);
        reserva.setTamanioMesa(tamano);

        assertThat(reserva.buscarCompartirNMesasConsecutivas(3, nPersonas)).isEqualTo(0);

        mesas[0] = 5;
        mesas[1] = 3; // espacio
        mesas[2] = 4; // espacio
        mesas[3] = 3; // espacio
        mesas[4] = 5;
        reserva.setMesas(mesas);
        reserva.setTamanioMesa(tamano);

        assertThat(reserva.buscarCompartirNMesasConsecutivas(3, nPersonas)).isEqualTo(1);

        mesas[0] = 5;
        mesas[1] = 3; // espacio
        mesas[4] = 5;
        reserva.setMesas(mesas);
        reserva.setTamanioMesa(tamano);

        assertThat(reserva.buscarCompartirNMesasConsecutivas(3, nPersonas)).isEqualTo(1);

        mesas[0] = 5;
        mesas[1] = 4;
        mesas[4] = 5;
        reserva.setMesas(mesas);
        reserva.setTamanioMesa(tamano);

        assertThat(reserva.buscarCompartirNMesasConsecutivas(3, nPersonas)).isNegative();
    }
}
