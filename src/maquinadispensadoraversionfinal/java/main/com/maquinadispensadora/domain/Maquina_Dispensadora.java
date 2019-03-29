package com.maquinadispensadora.domain;

import java.util.ArrayList;

import com.maquinadispensadora.domain.Producto;

public class Maquina_Dispensadora {

	private String fabricante;
	private String nroSerie;
	private String tipoMaquina;
	private double cambioRestante;
	private ArrayList<Producto> productos = new ArrayList<Producto>();

	public Maquina_Dispensadora(String fabricante, String nroSerie, String tipoMaquina, double cambioRestante) {
		this.fabricante = fabricante;
		this.nroSerie = nroSerie;
		this.tipoMaquina = tipoMaquina;
		this.cambioRestante = cambioRestante;
	}

	public ArrayList<String> verProductosAgotados() {
		ArrayList<String> listaProductosAgotados = new ArrayList<String>();
		for (int i = 0; i < this.productos.size(); i++) {
			if (this.productos.get(i).getCantidad() == (byte) 0) {
				listaProductosAgotados.add(this.productos.get(i).getNombre());
				listaProductosAgotados.add(this.productos.get(i).getCodigo());
			}
		}
		return listaProductosAgotados;
	}

	public double comprarProductoNombre(String nombre, double dineroIngresado) {
		boolean seCompra = true;
		double cambio = 0;
		ArrayList<String> listaAgotados = verProductosAgotados();
		for (int i = 0; i < listaAgotados.size(); i++) {
			if (i % 2 != 0) {
				if (listaAgotados.get(i).contentEquals(nombre)) {
					seCompra = false;
				}
			}

		}
		if (seCompra) {
			for (int i = 0; i < this.productos.size(); i++) {
				if (this.productos.get(i).getNombre().contentEquals(nombre)) {
					if (dineroIngresado < this.productos.get(i).getPrecio()) {
						cambio = 0;
					} else {
						this.productos.get(i).retirarProducto();
						cambio = calcularCambio(dineroIngresado, i);
					}
				}
			}
		}
		return cambio;
	}

	public double comprarProductoCodigo(String codigo, double dineroIngresado) {
		boolean seCompra = true;
		double cambio = 0;
		ArrayList<String> listaAgotados = verProductosAgotados();
		for (int i = 0; i < listaAgotados.size(); i++) {
			if (i % 2 == 0) {
				if (listaAgotados.get(i).contentEquals(codigo)) {
					seCompra = false;
				}
			}
		}
		if (seCompra) {
			for (int i = 0; i < this.productos.size(); i++) {
				if (this.productos.get(i).getCodigo().contentEquals(codigo)) {
					if (dineroIngresado < this.productos.get(i).getPrecio()) {
						cambio = 0;
					} else {
						this.productos.get(i).retirarProducto();
						cambio = calcularCambio(dineroIngresado, i);
					}
				}
			}
		}
		return cambio;
	}

	public byte agregarProducto(String nombre, String codigo, byte cantidad) {
		byte sobrante = 0;
		for (int i = 0; i < this.productos.size(); i++) {
			if (this.productos.get(i).getCodigo().contentEquals(codigo)) {
				sobrante = this.productos.get(i).agregarProducto(cantidad);
			}
		}
		return sobrante;
	}

	public byte verTotalProductosEnMaquina() {
		byte totalProductos = 0;
		for (int i = 0; i < this.productos.size(); i++) {
			totalProductos += this.productos.get(i).getCantidad();
		}
		return totalProductos;
	}

	private double calcularCambio(double dineroIngresado, int nroProducto) {
		double cambio = dineroIngresado - this.productos.get(nroProducto).getPrecio();
		cambioRestante -= cambio;
		return cambio;
	}

	public double getCambioRestante() {
		return cambioRestante;
	}

	public void setCambioRestante(double cambioRestante) {
		this.cambioRestante = cambioRestante;
	}

	public String getFabricante() {
		return fabricante;
	}

	public String getNroSerie() {
		return nroSerie;
	}

	public String getTipoMaquina() {
		return tipoMaquina;
	}

	public void setProductos(ArrayList<Producto> productos) {
		this.productos = productos;
	}

	public ArrayList<Producto> getProductos() {
		return productos;
	}
}
